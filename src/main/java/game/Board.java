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
                    field.setIcon(new ImageIcon(pawn.getImagePath()));
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
                    field.setIcon(new ImageIcon(pawn.getImagePath()));
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
                black = !black;
            }
            black = !black;
        }

    }

    private class BoardListener implements java.awt.event.ActionListener {

        public void actionPerformed(java.awt.event.ActionEvent event) {

            for (int yCoord = 0; yCoord < arrayBoard.length; yCoord++) {
                for (int xCoord = 0; xCoord < arrayBoard.length; xCoord++) {
                    if (event.getSource() == arrayBoard[xCoord][yCoord]) {
                        System.out.println("Field " + yCoord + ", " + xCoord + "pushed");
                        field = arrayBoard[xCoord][yCoord];
                        saveXCoord = xCoord;
                        saveYCoord = yCoord;
                        System.out.println("X: " + xCoord + ", Y: " + yCoord);
                        break;
                    }
                }

            }

            if (isWhitesTurn) {
                System.out.println("It's White's turn!");
            } else {
                System.out.println("It's Black's turn!");
            }

            if (field.getFigure() != null && isWhitesTurn != field.getFigure().getIsBlack()) {
                this.removeMarker();
                oldField = field;
                isSelected = true;

                for (int yCoord = 0; yCoord < arrayBoard.length; yCoord++) {
                    for (int xCoord = 0; xCoord < arrayBoard.length; xCoord++) {
                        Field localField = arrayBoard[xCoord][yCoord];
                        if (field.getFigure().isMoveValid(localField)) {
                            if (localField.getFigure() == null) {
                                localField.possibleHighlightOn();
                            } else if (localField.getFigure().getIsBlack() != field.getFigure().getIsBlack()) {
                                localField.collisionHighlightOn();
                            }
                        }
                    }
                }
                oldField.selectionHighlightOn();

            } else if (isSelected && oldField.getFigure().isMoveValid(field) /*&& ( field.getFigure().getIsBlack() != isWhitesTurn ) */) {
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
                isWhitesTurn = !isWhitesTurn;
                System.out.println(Arrays.toString(destroyedFiguresList.toArray()));
            }
        }

        private void removeMarker() {
            for (int yCoord = 0; yCoord < arrayBoard.length; yCoord++) {
                for (int xCoord = 0; xCoord < arrayBoard.length; xCoord++) {
                    Field localField = arrayBoard[xCoord][yCoord];

                    localField.setStandartColor();
                    

                }
            }

        }

    }

    public Field[][] getArrayChessBoard() {
        return arrayBoard;
    }
}
