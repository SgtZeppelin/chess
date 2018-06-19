/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;

/**
 *
 * @author gersc
 */
public class King extends Figure implements java.io.Serializable {

    public King(int x, int y, boolean black, Field field) {
        super(x, y, black, field);
        if (black) {
            imagePath = "/king_black.png";

        } else {
            imagePath = "/king_white.png";
        }
    }

    @Override
    public boolean isMovePossible(Field field) {
        int lastX = this.field.getXCord();
        int lastY = this.field.getYCord();
        int newX = field.getXCord();
        int newY = field.getYCord();
        if (this.checkTarget(lastX, newX, lastY, newY) && field != this.field ) {
            if (field.getFigure() != null) {
                if (field.getFigure().getIsBlack() != this.getIsBlack()) {
                    if (this.getIsBlack()) {
                        if (!field.isCheckedByWhite()) {
                            return this.checkCollision(lastX, newX, lastY, newY);
                        }
                    } else {
                        if (!field.isCheckedByBlack()) {
                            return this.checkCollision(lastX, newX, lastY, newY);
                        }
                    }
                }
            } else {
                if (this.getIsBlack()) {
                    if (!field.isCheckedByWhite()) {
                        return this.checkCollision(lastX, newX, lastY, newY);
                    }
                } else {
                    if (!field.isCheckedByBlack()) {
                        return this.checkCollision(lastX, newX, lastY, newY);
                    }
                }
            }

        }
        return false;
    }

    @Override
    public boolean isMoveValid(Field field) {
        int lastX = this.field.getXCord();
        int lastY = this.field.getYCord();
        int newX = field.getXCord();
        int newY = field.getYCord();
        if (this.checkTarget(lastX, newX, lastY, newY) 
            && !(field.getFigure() instanceof King)) {
            if (this.getIsBlack()) {
                if (!field.isCheckedByWhite()) {
                    return this.checkCollision(lastX, newX, lastY, newY);
                }
            } else {
                if (!field.isCheckedByBlack()) {
                    return this.checkCollision(lastX, newX, lastY, newY);
                }
            }
        }
        return false;
    }

    @Override
    protected boolean checkTarget(int lastX, int newX, int lastY, int newY) {
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

        if (resultX <= 1 && resultY <= 1 && resultX + resultY != 0) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean checkCollision(int lastX, int newX, int lastY, int newY) {
        return true;
    }
}
