/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.*;

/**
 *
 * @author gersc
 */
public class InformationBoard extends JPanel {
    
    public JLabel statusLabel;
    
    public void InformationBoard() {
        this.initBoard();
        
    }
    
    
    private void initBoard() {
        this.setSize(200, 800);
        statusLabel = new JLabel( "It's White's turn!" );
        this.add( statusLabel );
        this.setBackground(Color.red);

    }
    
    public void setStatusLabel( ) {
            statusLabel.setText( "only null" );
    }
}

