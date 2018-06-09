/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author gersc
 */
public class Board extends JPanel {

    private Field[][] arrayBoard;
    
    Field field = null;
    Field oldField = null;
    int saveYCoord;
    int saveXCoord;
    boolean isWhitesTurn = true;
    InformationBoard infoBoard;

    private boolean isSelected = false;

    public Board() throws IOException {

        this.initBoard();
    }

    private void initBoard() {
        
        this.setSize(800, 800);

        BoardListener boardlistener = new BoardListener();
        arrayBoard = new Field[8][8];
        this.setLayout(new java.awt.GridLayout(8, 8));
        boolean black = true;

        for (int yCord = 0; yCord < arrayBoard.length; yCord++) {
            for (int xCord = 0; xCord < arrayBoard.length; xCord++) {

                Field field = new Field(this, black, xCord, yCord );

                field.addActionListener(boardlistener);
                field.setBorder(null);
                arrayBoard[xCord][yCord] = field;

                if (black) {
                    field.setBackground(new Color(76, 175, 80));
                } else {
                    field.setBackground(new Color(139, 195, 74));
                }

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
                        System.out.println( "X: " + xCoord + ", Y: " + yCoord );
                        break;
                    }
                }

            }
            
            if (isWhitesTurn) {
                System.out.println( "It's White's turn!" );
            } else {
                System.out.println( "It's Black's turn!" );
            }
            
            if (!isSelected && field.getFigure() != null && isWhitesTurn != field.getFigure().getIsBlack()) {
                oldField = field;
                isSelected = true;
                
            } else if ( isSelected && oldField.getFigure().validateMove(field) /*&& ( field.getFigure().getIsBlack() != isWhitesTurn ) */) {
                System.out.println( "OLDfield: " + oldField.getFigure().getImagePath() );
                oldField.getFigure().removeTexture();
                field.setFigure(oldField.getFigure());
                System.out.println( "field: " + field.getFigure().getImagePath() );
                oldField.removeFigure();
                isSelected = false;
                isWhitesTurn = !isWhitesTurn;
            }
        }

    }

    public Field[][] getArrayChessBoard() {
        return arrayBoard;
    }
    
    

}
