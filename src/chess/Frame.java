
package chess;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JFrame;


public class Frame extends JFrame {
    Board board;
    Frame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800 , 650);
        this.setLocation(200, 50);
        this.setLayout(new FlowLayout());
        
        board = new Board();
        this.add(board.containor);
        
        this.getContentPane().setBackground(new Color(50,50,50));
        this.setVisible(true);
    }
    
}
