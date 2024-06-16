
package chess;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.*;


public class Frame extends JFrame {
    Board board;
    Frame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200 , 650);
        this.setLocation(200, 50);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 100, 10));

        //controlPanel = new ControlPanel();
        board = new Board();

        this.add(board.containor);
        this.add(board.controlPanel);
        
        this.getContentPane().setBackground(new Color(50,50,50));
        this.setVisible(true);
    }
    
}
