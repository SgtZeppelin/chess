/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
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
        destroyedFiguresList = new ArrayList<>();
        blackFiguresList = new ArrayList<>();
        whiteFiguresList = new ArrayList<>();

        this.setLayout(new java.awt.GridLayout(8, 8));
        boolean black = true;

        for (int yCord = 0; yCord < arrayBoard.length; yCord++) {
            for (int xCord = 0; xCord < arrayBoard.length; xCord++) {

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
                            Rook rook = new Rook(xCord, yCord, true, field);
                            field.setFigure(rook);
                            break;
                        case 1:
                        case 6:
                            Knight knight = new Knight(xCord, yCord, true, field);
                            field.setFigure(knight);
                            break;
                        case 2:
                        case 5:
                            Bishop bishop = new Bishop(xCord, yCord, true, field);
                            field.setFigure(bishop);
                            //Laeufer
                            break;
                        case 3:
                            Queen queen = new Queen(xCord, yCord, true, field);
                            field.setFigure(queen);
                            //Queen
                            break;
                        default:
                            King king = new King(xCord, yCord, true, field);
                            field.setFigure(king);
                            // King
                            break;
                    }
                } else if (yCord == 6) {
                    Pawn pawn = new Pawn(xCord, yCord, false, field);
                    field.setFigure(pawn);
                } else if (yCord == 7) {
                    switch (xCord) {
                        case 0:
                        case 7:
                            Rook rook = new Rook(xCord, yCord, false, field);
                            field.setFigure(rook);
                            break;
                        case 1:
                        case 6:
                            Knight knight = new Knight(xCord, yCord, false, field);
                            field.setFigure(knight);
                            break;
                        case 2:
                        case 5:
                            Bishop bishop = new Bishop(xCord, yCord, false, field);
                            field.setFigure(bishop);
                            //Laeufer
                            break;
                        case 3:
                            Queen queen = new Queen(xCord, yCord, false, field);
                            field.setFigure(queen);
                            //Queen
                            break;
                        default:
                            King king = new King(xCord, yCord, false, field);
                            field.setFigure(king);
                            // King
                            break;
                    }
                }

                this.add(field);

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
             * Executed when a pushed friendly unit is on it or the field is empty and no unit is selected
             */
            if (field.getFigure() != null && isWhitesTurn != field.getFigure().getIsBlack()) {
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
                            } else if (localField.getFigure().getIsBlack() != field.getFigure().getIsBlack()) {
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
            } /*
             * Executed when a unit is selected and the pushed field is valid
             */ else if (isSelected && oldField.getFigure().isMoveValid(field)) {
                this.removeMarker();
                if (field.getFigure() != null) {
                    destroyedFiguresList.add(field.getFigure());
                }
                oldField.getFigure().removeTexture();
                if (oldField.getFigure() instanceof Pawn) {
                    Pawn localPawn = (Pawn) oldField.getFigure();
                    localPawn.setWasMoved(true);
                }

                field.setFigure(oldField.getFigure());
                oldField.removeFigure();
                isSelected = false;

                System.out.println(Arrays.toString(destroyedFiguresList.toArray()));

                this.setCheckedFields(whiteFiguresList, false);
                this.setCheckedFields(blackFiguresList, true);
                if (isWhitesTurn) {
                    if (!this.checkCheckmated(true)) {
                        System.out.println("Player White won!");
                        System.exit(0);
                    }
                } else {
                    if (!this.checkCheckmated(false)) {
                        System.out.println("Player Black won!");
                        System.exit(0);
                    }
                }
                isWhitesTurn = !isWhitesTurn;
            }
        }

        /*
         * Helpermethod, which removes all markers
         */
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

        private boolean checkCheckmated(boolean blackKing) {
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

}
