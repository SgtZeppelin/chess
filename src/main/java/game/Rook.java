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
public class Rook extends Figure {

    boolean moved = false;

    public Rook(int x, int y, boolean black, Field field) {
        super(x, y, black, field);
        if (black) {
            imagePath = "/rook_black.png";

        } else {
            imagePath = "/rook_white.png";
        }
    }
    @Override
    protected boolean checkTarget( Field field ) {
        
        int lastX = this.field.getXCord();
        int lastY = this.field.getYCord();
        int newX = field.getXCord();
        int newY = field.getYCord();
        
        if ( ( lastX == newX && lastY != newY ) || ( lastY == newY && lastX != newX ) ) {
            return true;
        }
        
        return false;
    }
    
    @Override
    protected boolean checkCollision( Field field ) {
        
        int lastX = this.field.getXCord();
        int lastY = this.field.getYCord();
        int newX = field.getXCord();
        int newY = field.getYCord();
        
        Field[][] arrayField = this.getField().getBoard().getArrayChessBoard();
        
        if ( lastX == newX ) {
            if ( lastY < newY ) {
                for ( int i = lastY +1; i < newY; i++ ) {
                    Field localField = arrayField[lastX][i];
                    if ( localField.getFigure() != null ) {
                        return false;
                    }
                }
            } else {
                for ( int i = lastY -1; i > newY; i-- ) {
                    Field localField = arrayField[lastX][i];
                    if ( localField.getFigure() != null ) {
                        return false;
                    }
                }
            }
        } else if ( lastY == newY ) {
            if ( lastX < newX ) {
                for ( int i = lastX +1 ; i < newX; i++ ) {
                    Field localField = arrayField[i][lastY];
                    if ( localField.getFigure() != null ) {
                        return false;
                    }
                }
            } else {
                for ( int i = lastX -1; i > newX; i-- ) {
                    Field localField = arrayField[i][lastY];
                    if ( localField.getFigure() != null ) {
                        return false;
                    }
                }
            }
            
        }
        return true;
    }

}
