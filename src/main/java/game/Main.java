/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author gersc
 */
public class Main extends JFrame {
    
        public Main() {
            
            /// \ref T7_1 Popup will ask users to choose the number of players
            boolean onePlayer = isOnePlayerGame();
            
            try {
                this.add( new Board(onePlayer));
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            this.pack();
            this.setResizable(false);
            this.setTitle( "CHESS" );
            this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            this.setLocationRelativeTo( null );
            
        }
        
        /// \ref T7_2 Popup for users to choose number of players 
        //will return true for One player, false for Two player
        public boolean isOnePlayerGame() {
            String[] options = {"1 Player", "2 Players"};
            int playerChoice = JOptionPane.showOptionDialog(null, "Please choose number of Players",
                "How Many Players?",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (playerChoice == 0) {
                return true;
            }
            else {
                return false;
            }
        }

    
        public static void main(String[] args) {
            
            EventQueue.invokeLater(() -> {
                Main main = new Main();
                main.setVisible( true );
            });
            
        }
    
}
