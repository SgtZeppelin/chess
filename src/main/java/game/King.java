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
public class King extends Figure {

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
        if (this.checkTarget(field) && field != this.field ) {
            if (field.getFigure() != null) {
                if (field.getFigure().getIsBlack() != this.getIsBlack()) {
                    if (this.getIsBlack()) {
                        if (!field.isCheckedByWhite()) {
                            return this.checkCollision(field);
                        }
                    } else {
                        if (!field.isCheckedByBlack()) {
                            return this.checkCollision(field);
                        }
                    }
                }
            } else {
                if (this.getIsBlack()) {
                    if (!field.isCheckedByWhite()) {
                        return this.checkCollision(field);
                    }
                } else {
                    if (!field.isCheckedByBlack()) {
                        return this.checkCollision(field);
                    }
                }
            }

        }
        return false;
    }

    @Override
    public boolean isMoveValid(Field field) {
        if (this.checkTarget(field) && !(field.getFigure() instanceof King)) {
            if (this.getIsBlack()) {
                if (!field.isCheckedByWhite()) {
                    return this.checkCollision(field);
                }
            } else {
                if (!field.isCheckedByBlack()) {
                    return this.checkCollision(field);
                }
            }
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

        if (resultX <= 1 && resultY <= 1 && resultX + resultY != 0) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean checkCollision(Field field) {
        return true;
    }
}
