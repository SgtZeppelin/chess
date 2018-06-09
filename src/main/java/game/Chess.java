/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.io.IOException;

/**
 *
 * @author gersc
 */
public class Chess extends JPanel {
    
    private Image imageFun;
    public Board board;
    
    public Chess() throws IOException {
        
  
        
        this.loadImage();
        
        int w = imageFun.getWidth( this );
        int h = imageFun.getHeight( this );
        this.setPreferredSize( new Dimension( w, h ) );
    }
    
    private void loadImage() {
        
        ImageIcon ii = new ImageIcon( "C:/Users/gersc/Desktop/NetBeansProjects/ideal-winner/chess_game/src/main/java/game/spaceX.png" );
        imageFun = ii.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        
        g.drawImage( imageFun, 0, 0, null );
    }
    
}
