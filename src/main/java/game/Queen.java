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
public class Queen extends Figure {

    boolean isOdd;

    public Queen(int x, int y, boolean black, Field field) {
        super(x, y, black, field);
        if (black) {
            imagePath = "/queen_black.png";

        } else {
            imagePath = "/queen_white.png";
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

        //System.out.println( "oldX: " + oldX + ", oldY: " + oldY + ", newX: " + newX + ", newY : " + newY );
        if ((lastX == newX && lastY != newY) || (lastY == newY && lastX != newX)) {
            isOdd = false;
            return true;
        }

        if (resultX == resultY && resultX + resultY != 0) {
            isOdd = true;
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

        if (isOdd) {
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

        } else {

            if (lastX == newX) {
                if (lastY < newY) {
                    for (int i = lastY + 1; i < newY; i++) {
                        Field localField = arrayField[lastX][i];
                        if (localField.getFigure() != null) {
                            return false;
                        }
                    }
                } else {
                    for (int i = newY + 1; i < lastY; i++) {
                        Field localField = arrayField[lastX][i];
                        if (localField.getFigure() != null) {
                            return false;
                        }
                    }
                }
            } else {
                if (lastX < newX) {
                    for (int i = lastX + 1; i < newX; i++) {
                        Field localField = arrayField[i][lastY];
                        if (localField.getFigure() != null) {
                            return false;
                        }
                    }
                } else {
                    for (int i = newX + 1; i < lastX; i++) {
                        Field localField = arrayField[i][lastY];
                        if (localField.getFigure() != null) {
                            return false;
                        }
                    }

                }
            }

        }
        return true;
    }
}
