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
            imagePath = "C:\\Users\\gersc\\Desktop\\NetBeansProjects\\ideal-winner\\chess_game\\src\\main\\java\\game\\bishop_black.png";

        } else {
            imagePath = "C:\\Users\\gersc\\Desktop\\NetBeansProjects\\ideal-winner\\chess_game\\src\\main\\java\\game\\bishop_white.png";
        }
    }

    @Override
    public boolean isMoveValid( Field field ) {
        
        if ( this.checkTarget( field ) ) {
            return this.checkCollision( field );
        }
        return false;
    }
    
    @Override
    protected boolean checkTarget( Field field ) {
        int lastX = this.field.getXCord();
        int lastY = this.field.getYCord();
        int newX = field.getXCord();
        int newY = field.getYCord();
        int resultX;
        int resultY;
        
        if( lastX < newX ) {
            resultX = newX - lastX;
        } else {
            resultX = lastX - newX;
        }
        
        if( lastY < newY ) {
            resultY = newY - lastY;
        } else {
            resultY = lastY - newY;
        }
        
        if ( resultX  == resultY && resultX + resultY != 0 ) {
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
        
        if ( lastY < newY ) {
            if ( lastX < newX ) {
                
                for ( int y = lastY +1; y < newY; y++ ) {
                    for ( int x = lastX +1; x < newX; x++) {
                        
                        Field localField = arrayField[x][y];
                        System.out.println("err1");
                        if ( localField.getFigure() != null ) {
                            System.out.println( "Collision1" );
     
                            return false;
                        }
                    }
                }
            } else {
                for ( int y = lastY +1; y < newY; y++ ) {
                    for ( int x = newX +1; x < lastX; x++) {
                        
                        Field localField = arrayField[x][y];
                        System.out.println("err1");
                        if ( localField.getFigure() != null ) {
                            System.out.println( "Collision1" );
     
                            return false;
                        }
                    }
                }
            }
        } else {
            if ( lastX < newX ) {
                
                for ( int y = newY +1; y < lastY; y++ ) {
                    for ( int x = lastX +1; x < newX; x++) {
                        
                        Field localField = arrayField[x][y];
                        System.out.println("err1");
                        if ( localField.getFigure() != null ) {
                            System.out.println( "Collision1" );
     
                            return false;
                        }
                    }
                }
            } else {
                for ( int y = newY +1; y < lastY; y++ ) {
                    for ( int x = newX +1; x < lastX; x++) {
                        
                        Field localField = arrayField[x][y];
                        System.out.println("err1");
                        if ( localField.getFigure() != null ) {
                            System.out.println( "Collision1" );
     
                            return false;
                        }
                    }
                }
            }
        }
        
        return true;
    }

}
