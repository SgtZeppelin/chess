/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author gersc
 */
public class Bishop extends Figure {

    public Bishop(int x, int y, boolean black, Field field) {
        super(x, y, black, field);
        if (black) {
            imagePath = "/bishop_black.png";

        } else {
            imagePath = "/bishop_white.png";
        }
    }


    @Override
    protected boolean checkTarget(Field field) {
        int lastX = this.field.getXCord();
        int lastY = this.field.getYCord();
        int newX = field.getXCord();
        int newY = field.getYCord();
        int resultX;
        int resultY;

        if (lastX < newX) {
            resultX = newX - lastX;
        } else {
            resultX = lastX - newX;
        }

        if (lastY < newY) {
            resultY = newY - lastY;
        } else {
            resultY = lastY - newY;
        }

        if (resultX == resultY) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean checkCollision(Field field) {
        
        int lastX = this.field.getXCord();
        int lastY = this.field.getYCord();
        int newX = field.getXCord();
        int newY = field.getYCord();

        Field[][] arrayField = this.getField().getBoard().getArrayChessBoard();

        if ( lastY > newY ) {
            if ( lastX > newX ) {
                int x = lastX -1;
                for ( int y = lastY-1; y > newY; y-- ) {
                        Field localField = arrayField[x][y];
                        if ( localField.getFigure() != null ) {
                            return false;
                        }
                        x--;
                }
            } else {
                int x = lastX +1;
                for ( int y = lastY -1; y > newY; y-- ) {
                        Field localField = arrayField[x][y];
                        if ( localField.getFigure() != null ) {
                            return false;
                        }
                        x++;
                }
            } 
        } else {
            if ( lastX > newX ) {
                int x = lastX -1;
                for ( int y = lastY +1; y < newY; y++ ) {
                        Field localField = arrayField[x][y];
                        if ( localField.getFigure() != null ) {
                            return false;
                        }
                        x--;
                }
            } else {
                int x = lastX +1;
                for ( int y = lastY +1; y < newY; y++ ) {
                        Field localField = arrayField[x][y];
                        if ( localField.getFigure() != null ) {
                            return false;
                        }
                        x++;
                }
            }
        }
        return true;
    }
 }