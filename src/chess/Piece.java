package chess;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JLabel;

public abstract class Piece extends JLabel implements MouseListener,MouseMotionListener {
    int x;
    int y;
    int square;
    int DEFAULT_SQUARE;
    boolean isWhite;
    Piece[] temp = new Piece[64];
    public static int NumberOfPieces;
    public MouseListener pieceListener = this;
    public MouseMotionListener pieceMotionListener = this;
    ArrayList<Integer> validMoves = new ArrayList<>();
    Image img;
    Piece(int square){
        DEFAULT_SQUARE = square;
        setSquare(square);
        this.setBounds(x, y, 75, 75);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        
        NumberOfPieces++;
    }
    
    public void setSquare(int square){
        this.square = square;
        this.setLocation(cordinatesFromSquare(square));
        fixXY(cordinatesFromSquare(square));
    }
    
    abstract void calculateValidMoves(int initialSquare, Piece[] position);
    
    boolean isValidMove(int sqr1,int sqr2,Piece[] position) {
        calculateValidMoves(sqr1, position);
        if(position[sqr1] != null){
            for(int i=0; i<validMoves.size(); i++){
                temp = Arrays.copyOf(position, position.length);
                temp[sqr1] = null;
                temp[validMoves.get(i)] = this;
                //if(((King)temp[isWhite? King.wKSquare:King.bKSquare]).inCheck)// not here
                
                if(Board.isSquareInCheck(isWhite? King.wKSquare:King.bKSquare, temp)){
                    validMoves.remove(i);
                    i--;
                }
            }
            for(Integer move : validMoves){
                //System.out.println("Piece moves: "+move + " " );
                if(sqr2==move)
                    return true;
            }
        }
        return false;
    }
    
    private Point cordinatesFromSquare(int square){
        Point point = new Point((square%8)*75,square/8*75);
        return point;
    }
    private void fixXY(Point point){
        this.x = cordinatesFromSquare(square).x;
        this.y = cordinatesFromSquare(square).y;
    }
    @Override
    protected void paintComponent(Graphics g){
        g.drawImage(img, 0, 0,75,75, null);
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        //this.setLocation(x-35+e.getX(),y-35+e.getY());
        //checking the edge of the board
        if(x+e.getX() > 0 && x+e.getX() < 600) {
            this.setLocation(x-35+e.getX(),y);
            x = x - 35 + e.getX();
        }
        if(y+e.getY() > 0 && y+e.getY() < 600) {
            this.setLocation(x,y-35+e.getY());
            y = y - 35 + e.getY();
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
        this.setLocation(x-35+e.getX(),y-35+e.getY());
        x = x-35+e.getX();
        y = y-35+e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        square = (y+35)/75*8+(x+35)/75;
        this.setLocation(cordinatesFromSquare(square));
        fixXY(cordinatesFromSquare(square));
    }
    
    
    @Override
    public void mouseMoved(MouseEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }


    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    
}

