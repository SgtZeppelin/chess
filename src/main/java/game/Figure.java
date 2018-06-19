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
    public Field collisionField;
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
        try {
            field.setIcon(new ImageIcon(getClass().getResource(imagePath)));
        } catch (NullPointerException e) {
            System.out.println("Bild nicht gefunden");
        }
    }
    
    public void removeTexture() {
        field.setIcon(null);
    }
    
    public void removeCollisionMarker() {
        if ( collisionField != null ) {
            collisionField.setStandartColor();
        }
    }
    
    public void markCollisionField( Field field ) {
        if ( field.getFigure().getIsBlack() != this.isBlack ) {
            field.collisionHighlightOn();
            collisionField = field;
        }
    }
    
    public boolean isMovePossible( Field field ) {
        int lastX = this.field.getXCord();
        int lastY = this.field.getYCord();
        int newX = field.getXCord();
        int newY = field.getYCord();
        if (this.checkTarget(lastX, newX, lastY, newY) && field != this.field ) {
            if ( field.getFigure() != null ) {
                if (field.getFigure().getIsBlack() != this.getIsBlack()) {
                    return this.checkCollision(lastX, newX, lastY, newY);
                }
            } else {
                return this.checkCollision(lastX, newX, lastY, newY);
            }
               
        }
        return false;
    }
    
    public boolean isMoveValid( Field field ) {
        int lastX = this.field.getXCord();
        int lastY = this.field.getYCord();
        int newX = field.getXCord();
        int newY = field.getYCord();
        
        if (this.checkTarget(lastX, newX, lastY, newY) && !(field.getFigure() instanceof King) ) {
                return this.checkCollision(lastX, newX, lastY, newY);
        }
        return false;
    }

    abstract boolean checkTarget( int lastX, int newX, int lastY, int newY );
    
    abstract boolean checkCollision( int lastX, int newX, int lastY, int newY );
    
}
