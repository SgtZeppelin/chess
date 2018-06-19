/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;

/**
 *
 * @author gersc
 */
public class Board extends JPanel {

    private Field[][] arrayBoard;
    private Figure[][] virtualBoard;
    private ArrayList<Figure> destroyedFiguresList;
    private ArrayList<Figure> whiteFiguresList;
    private ArrayList<Figure> blackFiguresList;
    Field field = null;
    Field oldField = null;
    int saveYCoord;
    int saveXCoord;
    boolean isWhitesTurn = true;
    InformationBoard infoBoard;
    Field collisionField;
    private boolean isSelected = false;

    public Board() throws IOException {

        this.initBoard();
    }

    private void initBoard() {

        this.setSize(800, 800);

        BoardListener boardlistener = new BoardListener();
        arrayBoard = new Field[8][8];
        virtualBoard = new Figure[8][8];
        destroyedFiguresList = new ArrayList<>();
        blackFiguresList = new ArrayList<>();
        whiteFiguresList = new ArrayList<>();

        this.setLayout(new java.awt.GridLayout(8, 8));
        boolean black = true;

        for (int yCord = 0; yCord < arrayBoard.length; yCord++) {
            for (int xCord = 0; xCord < arrayBoard.length; xCord++) {
                Figure figure = null;
                Field field = new Field(this, black, xCord, yCord);

                field.addActionListener(boardlistener);
                field.setBorder(null);
                arrayBoard[xCord][yCord] = field;

                field.setStandartColor();

                if (yCord == 1) {
                    Pawn pawn = new Pawn(xCord, yCord, true, field);
                    field.setFigure(pawn);
                } else if (yCord == 0) {
                    switch (xCord) {
                        case 0:
                        case 7:
                            figure = new Rook(xCord, yCord, true, field);
                            break;
                        case 1:
                        case 6:
                            figure = new Knight(xCord, yCord, true, field);
                            break;
                        case 2:
                        case 5:
                            figure = new Bishop(xCord, yCord, true, field);
                            break;
                        case 3:
                            figure = new Queen(xCord, yCord, true, field);
                            break;
                        default:
                            figure = new King(xCord, yCord, true, field);
                            break;
                    }
                } else if (yCord == 6) {
                    Pawn pawn = new Pawn(xCord, yCord, false, field);
                    field.setFigure(pawn);
                } else if (yCord == 7) {
                    switch (xCord) {
                        case 0:
                        case 7:
                            figure = new Rook(xCord, yCord, false, field);
                            break;
                        case 1:
                        case 6:
                            figure = new Knight(xCord, yCord, false, field);
                            break;
                        case 2:
                        case 5:
                            figure = new Bishop(xCord, yCord, false, field);
                            break;
                        case 3:
                            figure = new Queen(xCord, yCord, false, field);
                            break;
                        default:
                            figure = new King(xCord, yCord, false, field);
                            break;
                    }
                }
                this.add(field);
                if (figure != null) {
                    virtualBoard[xCord][yCord] = figure;
                    field.setFigure(figure);
                }
                
                figure = null;
                if (field.getFigure() != null) {
                    if (field.getFigure().getIsBlack()) {
                        blackFiguresList.add(field.getFigure());
                    } else {
                        whiteFiguresList.add(field.getFigure());
                    }
                }

                black = !black;
            }
            black = !black;
        }

        this.printActivePlayer();

    }

    private class BoardListener implements java.awt.event.ActionListener {

        /*
         * Executed when a field is pressed
         */
        public void actionPerformed(java.awt.event.ActionEvent event) {

            for (int yCoord = 0; yCoord < arrayBoard.length; yCoord++) {
                for (int xCoord = 0; xCoord < arrayBoard.length; xCoord++) {
                    if (event.getSource() == arrayBoard[xCoord][yCoord]) {
                        field = arrayBoard[xCoord][yCoord];
                        saveXCoord = xCoord;
                        saveYCoord = yCoord;
                        System.out.println("X: " + xCoord + ", Y: " + yCoord);
                        break;
                    }
                }
            }

            printActivePlayer();

            /*
             * Executed when a pushed friendly unit is on it
             * or the field is empty and no unit is selected
             */
            if (field.getFigure() != null && isWhitesTurn 
                    != field.getFigure().getIsBlack()) {
                this.setCheckedFalse();
                this.removeMarker();
                oldField = field;
                isSelected = true;

                if (field.getFigure() instanceof King) {
                    if (field.getFigure().getIsBlack()) {
                        this.setCheckedFieldsKing(whiteFiguresList, false);
                    } else {
                        this.setCheckedFieldsKing(blackFiguresList, true);
                    }
                }

                /*
                 * Checks all fields and marks them with the right color
                 */
                for (int yCoord = 0; yCoord < arrayBoard.length; yCoord++) {
                    for (int xCoord = 0; xCoord < arrayBoard.length; xCoord++) {
                        Field localField = arrayBoard[xCoord][yCoord];
                        
                        if (field.getFigure().isMovePossible(localField)) {
                            if (localField.getFigure() == null) {
                                localField.possibleHighlightOn();
                            } else if (localField.getFigure().getIsBlack() 
                                    != field.getFigure().getIsBlack()) {
                                if (localField.getFigure() instanceof King) {
                                    localField.checkHighlightOn();
                                } else {
                                    localField.collisionHighlightOn();
                                }
                            }
                        }
                    }
                }
                oldField.selectionHighlightOn();
            }
            
            /*
             * Executed when a unit is selected and the pushed field is valid
             */  
            else if (isSelected && oldField.getFigure().isMoveValid(field)) {
                
                this.removeMarker();
                isSelected = false;
                if (field.getFigure() != null) {
                    destroyedFiguresList.add(field.getFigure());
                }
                if (oldField.getFigure() instanceof Pawn) {
                    Pawn localPawn = (Pawn) oldField.getFigure();
                    localPawn.setWasMoved(true);
                }
                
                this.moveFigure(oldField, field);

                System.out.println(Arrays.toString(destroyedFiguresList.toArray()));

                if (isWhitesTurn) {
                    this.setCheckedFields(whiteFiguresList, false);
                    if (!this.checkChecked(true)) {
                        System.out.println("Player White won!");
                        pauseGame("Player White won!");
                    }
                } else {
                    this.setCheckedFields(blackFiguresList, true);
                    if (!this.checkChecked(false)) {
                        System.out.println("Player Black won!");
                        pauseGame("Player Black won!");
                    }
                }
                isWhitesTurn = !isWhitesTurn;
                CheckmatedChecker cmc = new CheckmatedChecker(virtualBoard);
            }
        }

        /*
         * Helpermethod, which removes all markers
         */
        
        private void moveFigure(Field oldField, Field newField) {
            oldField.getFigure().removeTexture();
            newField.setFigure(oldField.getFigure());
            oldField.removeFigure();
        }
        private void removeMarker() {
            for (int yCoord = 0; yCoord < arrayBoard.length; yCoord++) {
                for (int xCoord = 0; xCoord < arrayBoard.length; xCoord++) {
                    Field localField = arrayBoard[xCoord][yCoord];

                    localField.setStandartColor();

                }
            }
        }

        public void setCheckedFieldsKing(ArrayList teamList, boolean black) {
            ArrayList<Figure> localteamList = teamList;

            for (Figure figure : localteamList) {
                for (int yCoord = 0; yCoord < arrayBoard.length; yCoord++) {
                    for (int xCoord = 0; xCoord < arrayBoard.length; xCoord++) {
                        Field field = arrayBoard[xCoord][yCoord];
                        if (figure.isMovePossible(field)) {
                            field.checkHighlightOn();
                            if (black) {
                                field.setCheckedByBlack(true);
                            } else {
                                field.setCheckedByWhite(true);
                            }
                        }
                    }
                }
            }
        }

        public void setCheckedFields(ArrayList teamList, boolean black) {
            ArrayList<Figure> localteamList = teamList;

            for (Figure figure : localteamList) {
                for (int yCoord = 0; yCoord < arrayBoard.length; yCoord++) {
                    for (int xCoord = 0; xCoord < arrayBoard.length; xCoord++) {
                        Field field = arrayBoard[xCoord][yCoord];
                        if (figure.isMovePossible(field)) {
                            if (black) {
                                field.setCheckedByBlack(true);
                            } else {
                                field.setCheckedByWhite(true);
                            }
                        }
                    }
                }
            }
        }

        private void setCheckedFalse() {
            for (int y = 0; y < arrayBoard.length; y++) {
                for (int x = 0; x < arrayBoard.length; x++) {
                    Field field = arrayBoard[x][y];
                    field.setCheckedByBlack(false);
                    field.setCheckedByWhite(false);
                }
            }
        }

        private boolean checkChecked(boolean blackKing) {
            King king = null;
            if (blackKing) {
                for (Figure figure : blackFiguresList) {
                    if (figure instanceof King) {
                        king = (King) figure;
                    }
                }
                for (int y = 0; y < arrayBoard.length; y++) {
                    for (int x = 0; x < arrayBoard.length; x++) {
                        Field field = arrayBoard[x][y];
                        if (king.isMovePossible(field)) {
                            System.out.println("black can move");
                            return true;
                        }
                    }
                }

                if (king.getField().isCheckedByWhite()) {
                    return false;
                }
            } else {
                for (Figure figure : whiteFiguresList) {
                    if (figure instanceof King) {
                        king = (King) figure;
                    }
                }
                for (int y = 0; y < arrayBoard.length; y++) {
                    for (int x = 0; x < arrayBoard.length; x++) {
                        Field field = arrayBoard[x][y];
                        if (king.isMovePossible(field)) {
                            System.out.println("white can move");
                            return true;
                        }
                    }
                }

                if (king.getField().isCheckedByBlack()) {
                    return false;
                }
            }

            return true;
        }
    }

    public Field[][] getArrayChessBoard() {
        return arrayBoard;
    }

    private void printActivePlayer() {
        if (isWhitesTurn) {
            System.out.println("It's White's turn!");
        } else {
            System.out.println("It's Black's turn!");
        }
    }
    
    public void pauseGame(String text) {
            for (int y = 0; y < arrayBoard.length; y++) {
                for (int x = 0; x < arrayBoard.length; x++) {
                    Field field = arrayBoard[x][y];
                    field.setEnabled(false);
                }
            }
        }

}
