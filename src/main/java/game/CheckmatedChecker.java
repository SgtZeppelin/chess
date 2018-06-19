/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import static game.DeepCopy.deepCopy;
import java.util.ArrayList;

/**
 *
 * @author gersc
 */
public class CheckmatedChecker {

    Figure[][] arrayBoardCopy;

    public CheckmatedChecker(Figure[][] virtualBoard) {
        this.arrayBoardCopy = (Figure[][]) deepCopy(virtualBoard);
    }

    public boolean isCheckmated(boolean black) {

        for (int y = 0; y < arrayBoardCopy.length; y++) {
            for (int x = 0; x < arrayBoardCopy.length; x++) {
                Figure figure = arrayBoardCopy[x][y];
                if (figure != null
                        && figure.getIsBlack() == black) {

                }
            }
        }

        return false;
    }
/*
    private boolean placeholder(Figure figure) {
        for (int y = 0; y < arrayBoardCopy.length; y++) {
            for (int x = 0; x < arrayBoardCopy.length; x++) {
                Figure figure = virtualBoard[x][y];
                if (figure.isMoveValid(figure)) {

                }
            }
        }
        return false;
    }
    */
}
