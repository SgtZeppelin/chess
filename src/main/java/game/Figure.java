/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import javax.swing.*;

/**
 *
 * @author gersc
 */
public abstract class Figure {
    
    public int oldX;
    public int oldY;
    public String imagePath;
    public boolean isBlack;
  
    public Field field;
    
    public Figure( int x, int y, boolean black, Field field ) {
        
        oldX = x;
        oldY = y;
        isBlack = black;
        
        this.field = field;
    }

    public boolean getIsBlack() {
        return isBlack;
    }
    
    public String getImagePath() {
        return imagePath;
    }

    public Field getField() {
        return field;
    } 

    public void setField(Field field) {
        this.field = field;

    }
    
    public void setTexture() {
        field.setIcon(new ImageIcon(imagePath));
    }
    
    public void removeTexture() {
        field.setIcon(null);
    }
    
    abstract boolean validateMove( Field field );
    
    abstract boolean checkTarget( Field field );
    
    abstract boolean checkCollision( Field field );
    
}
