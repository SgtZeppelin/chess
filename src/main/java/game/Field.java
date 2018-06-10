/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author gersc
 */
public class Field extends JButton {
    
    private boolean isBlack;
    private Figure figure = null;
    private Board board;
    private int xCord;
    private int yCord;
    
    public Field( Board board, boolean black, int xCord, int yCord ) {
        this.setPreferredSize( new Dimension( 100, 100 ) );
        this.xCord = xCord;
        this.yCord = yCord;
        this.board = board;
        isBlack = black;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
        this.figure.setField(this);
        //figure.setField(this);
        this.figure.setTexture();
    }

    public Figure getFigure() {
        return this.figure;
    }

    public Board getBoard() {
        return board;
    }
    
    public void removeFigure() {
        this.figure = null;
        //this.figure = null;
    }

    public int getXCord() {
        return xCord;
    }

    public int getYCord() {
        return yCord;
    }
    
    public void highlightOn() {
        this.setBackground(Color.red);
    }
    
    public void highlightOff() {
        if ( this.isBlack ) {
            this.setBackground(new Color(76, 175, 80));
        } else {
            this.setBackground(new Color(139, 195, 74));
        }
    }
}
