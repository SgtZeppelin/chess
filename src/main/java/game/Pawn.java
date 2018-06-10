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
public class Pawn extends Figure {
    
    boolean moved = false;
    
    public Pawn( int x, int y, boolean black, Field field ) {
        super( x, y, black, field );
        if( isBlack ) {
            imagePath = "C:\\Users\\gersc\\Desktop\\NetBeansProjects\\ideal-winner\\chess_game\\src\\main\\java\\game\\pawn_black.png";
        } else {
            imagePath = "C:\\Users\\gersc\\Desktop\\NetBeansProjects\\ideal-winner\\chess_game\\src\\main\\java\\game\\pawn_white.png";
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
        int resultX = lastX - newX;
        
        if( isBlack ) {
            if ( moved ) {
                if ( (newY - lastY) == 1 && resultX == 0 ) {
                    return true;
                }
            } else {
                if ( (newY - lastY) <= 2 && (newY - lastY) > 0 && resultX == 0 ){
                    moved = true;
                    return true;
                }
            }
        } else {
            if ( moved ) {
                if ( (lastY - newY) == 1 && resultX == 0 ){
                    return true;
                }
            } else {
                if ( (lastY - newY) <= 2 && (lastY - newY) > 0  && resultX == 0 ){
                    moved = true;
                    return true;
                }
            }
        }
        return false;
        
    }
    
    @Override
    protected boolean checkCollision( Field field ) {
        return true;
    }
    
}
