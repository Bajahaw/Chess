package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ControlPanel extends JPanel implements ActionListener, MouseListener {
    Board board = new Board();
    JPanel controls;
    JPanel gameState;
    JButton newGameButton;
    JButton setFENButton;
    JTextField fenText;
    JTextArea moves;
    JLabel boardState;
    public ControlPanel(){

        controls = new JPanel();
        controls.setSize(new Dimension(400, 200));
        controls.setBackground(new java.awt.Color(50,50,50));
        controls.setLayout(new GridLayout(4,1,0,10));

        gameState = new JPanel();
        gameState.setSize(400, 400);
        gameState.setBackground(new java.awt.Color(50,50,50));
        gameState.setLayout(null);

        this.setSize(400, 600);
        this.setBackground(new java.awt.Color(50,50,50));
        this.setLayout(new GridLayout(2,1,0,10));

        newGameButton = new JButton("New Game");
        newGameButton.setPreferredSize(new Dimension(200, 10));
        newGameButton.setBackground(new java.awt.Color(150,150,150));
        newGameButton.setForeground(new java.awt.Color(255,255,255));
        newGameButton.setFocusPainted(false);
        newGameButton.setBorderPainted(false);
        newGameButton.addActionListener(this);

        setFENButton = new JButton("Set FEN");
        setFENButton.setPreferredSize(new Dimension(200, 50));
        setFENButton.setBackground(new Color(150,150,150));
        setFENButton.setForeground(new Color(255,255,255));
        setFENButton.setFocusPainted(false);
        setFENButton.setBorderPainted(false);
        setFENButton.addActionListener(this);

        fenText = new JTextField();
        fenText.setPreferredSize(new Dimension(300, 50));
        fenText.setBackground(new Color(100,100,100));
        fenText.setForeground(new Color(200,200,200));
        fenText.setText("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        moves = new JTextArea();
        moves.setBounds(0, 10,300, 150);
        moves.setBackground(new Color(50,50,50));
        moves.setForeground(new Color(200,200,200));
        moves.setBorder(null);
        moves.setFont(new Font("Arial", Font.PLAIN, 18));
        moves.setLineWrap(true);
        moves.setEditable(false);

        boardState = new JLabel(Board.isWhiteTurn ? "White Turn": "Black Turn");
        boardState.setBounds(0, 160, 300, 50);
        boardState.setBackground(new Color(50,50,50));
        boardState.setForeground(new Color(255,255,255));
        boardState.setFont(new Font("Arial", Font.PLAIN, 20));
        boardState.setHorizontalAlignment(SwingConstants.CENTER);

        addMouseListenerToPieces();

        controls.add(newGameButton);
        controls.add(setFENButton);
        controls.add(fenText);
        gameState.add(boardState);
        gameState.add(moves);
        this.add(controls);
        this.add(gameState);
    }

    private void addMouseListenerToPieces(){
        for(Piece piece: board.pieces) if(piece!=null)piece.addMouseListener(this);
    }

    private boolean isFENValid(String FEN){
        int count = 0;
        for(int i=0; i<FEN.length(); i++){
            if(FEN.charAt(i)=='/')
                count++;
        }
        return count > 6;
    }

    private void updateBoardState(){
        boardState.setText(Board.isWhiteTurn ? "White Turn": "Black Turn");
        if(board.isCheckmate){
            boardState.setText(Board.isWhiteTurn ? "Black Wins": "White Wins");
        }
        if(board.isDraw){
            boardState.setText("Draw");
        }
        moves.setText(board.getPGN());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == setFENButton){
            if(isFENValid(fenText.getText())){
                board.resetGame();
                board.setFEN(fenText.getText());
                addMouseListenerToPieces();
                updateBoardState();
            }
        }
        if(e.getSource() == newGameButton){
            board.resetGame();
            board.setFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
            addMouseListenerToPieces();
            updateBoardState();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        updateBoardState();
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
