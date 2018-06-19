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
public class Pawn extends Figure implements java.io.Serializable {

    private boolean wasMoved = false;

    public Pawn(int x, int y, boolean black, Field field) {
        super(x, y, black, field);
        if (isBlack) {
            imagePath = "/pawn_black.png";
        } else {
            imagePath = "/pawn_white.png";
        }
    }

    public void setWasMoved(boolean wasMoved) {
        this.wasMoved = wasMoved;
    }

    public boolean isWasMoved() {
        return wasMoved;
    }

    @Override
    protected boolean checkTarget(int lastX, int newX, int lastY, int newY) {
        int resultX = lastX - newX;

        if (isBlack) {
            if (field.getFigure() != null && field.getFigure().getIsBlack() != this.getIsBlack()) {
                if ((newY == lastY + 1) && (this.checkEnemyField(lastX, newX, field))) {
                    return true;
                }
            } else {
                if (wasMoved) {
                    if ((newY - lastY) == 1 && resultX == 0) {
                        return true;
                    }
                } else {
                    if ((newY - lastY) <= 2 && (newY - lastY) > 0 && resultX == 0) {
                        return true;
                    }
                }
            }
        } else {
            if (field.getFigure() != null && field.getFigure().getIsBlack() != this.getIsBlack()) {
                if ((newY == lastY + -1) && (this.checkEnemyField(lastX, newX, field))) {
                    return true;
                }
            } else {
                if (wasMoved) {
                    if ((lastY - newY) == 1 && resultX == 0) {
                        return true;
                    }
                } else {
                    if ((lastY - newY) <= 2 && (lastY - newY) > 0 && resultX == 0) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    @Override
    protected boolean checkCollision(int lastX, int newX, int lastY, int newY) {

        if ((newY - lastY == 2) || (newY - lastY == -2)) {
            Field[][] arrayField = this.getField().getBoard().getArrayChessBoard();
            if (isBlack) {
                Field localField = arrayField[lastX][lastY + 1];
                if (localField.getFigure() != null) {
                    return false;
                }
            } else {
                Field localField = arrayField[lastX][lastY - 1];
                if (localField.getFigure() != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkEnemyField(int lastX, int newX, Field newField) {
        if (newField.getFigure() != null) {
            if ((newX == lastX + 1 || newX == lastX - 1) 
                    && newField.getFigure().getIsBlack() != this.field.getFigure().getIsBlack()) {
                return true;
            }
        }
        return false;
    }

}
