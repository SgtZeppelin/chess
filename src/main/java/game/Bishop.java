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
    
    Field collisionField;

    public Bishop(int x, int y, boolean black, Field field) {
        super(x, y, black, field);
        if (black) {
            imagePath = "C:\\Users\\gersc\\Desktop\\NetBeansProjects\\ideal-winner\\chess_game\\src\\main\\java\\game\\bishop_black.png";

        } else {
            imagePath = "C:\\Users\\gersc\\Desktop\\NetBeansProjects\\ideal-winner\\chess_game\\src\\main\\java\\game\\bishop_white.png";
        }
    }

    @Override
    public boolean isMoveValid(Field field) {

        if (this.checkTarget(field)) {
            return this.checkCollision(field);
        }
        return false;
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
        
        
        if ( collisionField != null ) {
            collisionField.highlightOff();
        }

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
                            localField.highlightOn();
                            collisionField = localField;
                            return false;
                        }
                        x--;
                }
            } else {
                int x = lastX +1;
                for ( int y = lastY -1; y > newY; y-- ) {
                        Field localField = arrayField[x][y];
                        if ( localField.getFigure() != null ) {
                            localField.highlightOn();
                            System.out.println(x + " , " + y);
                            collisionField = localField;
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
                            localField.highlightOn();
                            collisionField = localField;
                            return false;
                        }
                        x--;
                }
            } else {
                int x = lastX +1;
                for ( int y = lastY +1; y < newY; y++ ) {
                        Field localField = arrayField[x][y];
                        if ( localField.getFigure() != null ) {
                            localField.highlightOn();
                            collisionField = localField;
                            return false;
                        }
                        x++;
                }
            }
        }
        return true;
    }
 }