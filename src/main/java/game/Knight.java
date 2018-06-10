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
public class Knight extends Figure {

    public Knight(int x, int y, boolean black, Field field) {
        super(x, y, black, field);
        if (black) {
            imagePath = "C:\\Users\\gersc\\Desktop\\NetBeansProjects\\ideal-winner\\chess_game\\src\\main\\java\\game\\knight_black.png";

        } else {
            imagePath = "C:\\Users\\gersc\\Desktop\\NetBeansProjects\\ideal-winner\\chess_game\\src\\main\\java\\game\\knight_white.png";
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
        
        //System.out.println( "oldX: " + oldX + ", oldY: " + oldY + ", newX: " + newX + ", newY : " + newY );
        if ( (resultX == 2 && resultY == 1) || resultX == 1 && resultY == 2) {
            return true;
        }
        return false;
    }
    
    @Override
    protected boolean checkCollision( Field field ) {
        return true;
    }

}
