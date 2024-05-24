package chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Board extends JPanel implements MouseListener{

    JPanel containor;
    JLabel[] square = new JLabel[64];
    Piece[] pieces = new Piece[64];
    Piece selectedPiece;
//    int[] blackPieces = new int[16];
//    int[] whitePieces = new int[16];
    boolean wKingSideCastle = true;
    boolean bKingSideCastle = true;
    boolean wQueenSideCastle = true;
    boolean bQueenSideCastle = true;
    boolean firstTouch = false;
    public static boolean isWhiteTurn = true;
    public Board() {
        
        containor = new JPanel();
        containor.setPreferredSize(new Dimension(600,600));
        containor.setLayout(null);
        
        this.setBounds(0,0,600, 600);
        this.setLayout(new GridLayout(8,8));
        
        setBoard();
        
        containor.add(this);
    }

    public void setBoard() {
        Color firstColor = Color.WHITE;//new Color(233,215,180);//
        Color secondColor = Color.GRAY;//new Color(177,137,103)//
        setSquare(0,firstColor);
        for(int i=1; i<64; i++){
            if(i%8==0)
                setSquare(i, square[i-1].getBackground()==firstColor? firstColor : secondColor);
            else
                setSquare(i, square[i-1].getBackground()==firstColor? secondColor : firstColor);
        }
        setFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1");
        setPiecesMouseListener();
        
    }
    private void setPiecesMouseListener() {
        for(int i=0; i<64; i++){
            if(pieces[i]!=null){
                pieces[i].addMouseListener(this);
            }
        }
    }

    private void setSquare(int index, Color color) {
        square[index] = new JLabel();
        square[index].setBackground(color);
        square[index].setFont(new Font("Castellar",Font.BOLD,70));
        square[index].setForeground(new Color(185,185,185));//new Color(205,176,141)
        square[index].setHorizontalAlignment(JLabel.CENTER);
        square[index].setOpaque(true);
        square[index].addMouseListener(this);
        this.add(square[index]);
    }

    private void setFEN(String FEN) {
        int localBKSquare = 0;
        int localWKSquare = 0;
        int line = 0;
        int i = 0;
        int squarePointer = 0;
        while(line<=7){
            
            if(FEN.charAt(i)<57&&FEN.charAt(i)>47)
                squarePointer+=Integer.parseInt(String.valueOf(FEN.charAt(i)))-1;
            
            if(FEN.charAt(i)=='P'){
                pieces[squarePointer] = new Pawn(squarePointer, true);
                containor.add(pieces[squarePointer]);
            }
            if(FEN.charAt(i)=='p'){
                pieces[squarePointer] = new Pawn(squarePointer, false);
                containor.add(pieces[squarePointer]);
            }
            if(FEN.charAt(i)=='q'){
                pieces[squarePointer] = new Queen(squarePointer, false);
                containor.add(pieces[squarePointer]);
            }
            if(FEN.charAt(i)=='Q'){
                pieces[squarePointer] = new Queen(squarePointer, true);
                containor.add(pieces[squarePointer]);
            }
            if(FEN.charAt(i)=='r'){
                pieces[squarePointer] = new Rook(squarePointer, false);
                containor.add(pieces[squarePointer]);
            }
            if(FEN.charAt(i)=='R'){
                pieces[squarePointer] = new Rook(squarePointer, true);
                containor.add(pieces[squarePointer]);
            }
            if(FEN.charAt(i)=='k'){
                pieces[squarePointer] = new King(squarePointer, false);
                containor.add(pieces[squarePointer]);
                localBKSquare = squarePointer;
            }
            if(FEN.charAt(i)=='K'){
                pieces[squarePointer] = new King(squarePointer, true);
                containor.add(pieces[squarePointer]);
                localWKSquare = squarePointer;
            }
            if(FEN.charAt(i)=='n'){
                pieces[squarePointer] = new Knight(squarePointer, false);
                containor.add(pieces[squarePointer]);
            }
            if(FEN.charAt(i)=='N'){
                pieces[squarePointer] = new Knight(squarePointer, true);
                containor.add(pieces[squarePointer]);
            }
            if(FEN.charAt(i)=='b'){
                pieces[squarePointer] = new Bishop(squarePointer, false);
                containor.add(pieces[squarePointer]);
            }
            if(FEN.charAt(i)=='B'){
                pieces[squarePointer] = new Bishop(squarePointer, true);
                containor.add(pieces[squarePointer]);
            }
            
            if(FEN.charAt(i)=='/'){
                line++;
                squarePointer--;
            }
            
            squarePointer++;
            i++;
            if(FEN.charAt(i)==' ')
                break;
        }
        King.bKSquare = localBKSquare;
        King.wKSquare = localWKSquare;
    }
    private void hideHints() {
        for(int i=0; i<64; i++){
            square[i].setText("");
        }
        containor.repaint();
    }
    public void showHints(int sqr) {
        //neeeeeeeed some optimization // not anymore :D at least for now :D
        hideHints();
        pieces[sqr].isValidMove(sqr, sqr, pieces);
        for(int i=0; i<pieces[sqr].validMoves.size(); i++){
            if(!isWhiteTurn^pieces[sqr].isWhite){
                square[pieces[sqr].validMoves.get(i)].setText("O");
            }
        }
        //pieces[sqr].calculateValidMoves(sqr, pieces);
        //for(int i=0; i<pieces[sqr].validMoves.size(); i++){
        //    if(isValidMove(sqr,pieces[sqr].validMoves.get(i))){
        //        square[pieces[sqr].validMoves.get(i)].setText("O");
        //    }
        //}
        
        containor.repaint();
    }
    public boolean isValidMove( int sqr1, int sqr2){
//        if(pieces[sqr2]!=null){
//            if(pieces[sqr1].isWhite&&pieces[sqr2].isWhite)
//                return false;
//        }
        //System.out.println("BK: "+King.bKSquare);
        //System.out.println("WH: "+King.wKSquare);
        if(isWhiteTurn^pieces[sqr1].isWhite)
            return false;
        return pieces[sqr1]!=null? pieces[sqr1].isValidMove(sqr1, sqr2, pieces): false;
    }
    public static boolean isSquareInCheck(int square, Piece[] board){
        //System.out.println("Checking square: "+square);
        for(Piece piece : board){
            if(piece != null && piece.isWhite != isWhiteTurn ){
                piece.calculateValidMoves(piece.square, board);
                for(int move : piece.validMoves) if(move == square){
                    System.out.println("Check!!");
                    return true;
                }
            }
        }
        System.out.println("no check!!");
        return false;
    }
    private void promotePawn(Pawn pawn,int sqr1, int square){
        containor.remove(pawn);
        pieces[sqr1] = new Queen(square, pawn.isWhite);
        pieces[sqr1].addMouseListener(this);
        containor.add(pieces[sqr1]);
        containor.setComponentZOrder(pieces[sqr1], 0);
        containor.repaint();
    }
    
    private void movePiece(Piece selectedPiece,int sqr1, int square) {
        if(selectedPiece instanceof Pawn){
            if(((sqr1>7 && sqr1<16)&&(((Pawn) selectedPiece).isWhite))||((sqr1>47 && sqr1<56)&&!(((Pawn) selectedPiece).isWhite))){
                promotePawn((Pawn)selectedPiece, sqr1, square);
                movePiece(pieces[sqr1], sqr1, square);
                return;
            }
            else if(((Pawn)selectedPiece).firstMove){
                ((Pawn)selectedPiece).firstMove = false;
                if(square==sqr1-16||square==sqr1+16){
                    ((Pawn)selectedPiece).isEnPassan = true;
                    if(pieces[Pawn.enPassant] instanceof Pawn)
                        ((Pawn)pieces[Pawn.enPassant]).isEnPassan = false;
                    Pawn.enPassant = square;
                }
                movePiece(selectedPiece, sqr1, square);
                return;
            }
            else if((square-sqr1)%8 != 0 && pieces[square]==null){
                if(selectedPiece.isWhite){
                    containor.remove(pieces[square+8]);
                    pieces[square+8]=null;
                }
                else {
                    containor.remove(pieces[square-8]);
                    pieces[square-8]=null;
                }
                /*if(square==sqr1-9){
                    containor.remove(pieces[sqr1-1]);
                    pieces[sqr1-1]=null;
                }
                else if(square==sqr1-7){
                    containor.remove(pieces[sqr1+1]);
                    pieces[sqr1+1]=null;
                }
                else if(square==sqr1+7){
                    containor.remove(pieces[sqr1-1]);
                    pieces[sqr1-1]=null;
                }
                else if(square==sqr1+9){
                    containor.remove(pieces[sqr1+1]);
                    pieces[sqr1+1]=null;
                }*/
                /*else{
                    if(pieces[sqr1+1] instanceof Pawn )
                        ((Pawn)pieces[sqr1+1]).isEnPassan = false;
                    if(pieces[sqr1-1] instanceof Pawn )
                        ((Pawn)pieces[sqr1-1]).isEnPassan = false;
                }*/
            }
        }
        
        System.out.println(Pawn.enPassant + " enpassant");
        if( pieces[Pawn.enPassant] instanceof Pawn ){
            ((Pawn)pieces[Pawn.enPassant]).isEnPassan = false;
            Pawn.enPassant = 0;
        }
        
        if(pieces[square]!=null){
            containor.remove(pieces[square]);
            pieces[square] = selectedPiece;
            containor.repaint();
        }
        
        else{
            pieces[square] = selectedPiece;
        }
        
        pieces[sqr1]=null;
        pieces[square].setSquare(square);
        
        if(isWhiteTurn){
            isWhiteTurn = false;
                
            if(isSquareInCheck(King.bKSquare, pieces))
                ((King)pieces[King.bKSquare]).inCheck = true;
            else if(((King)pieces[King.bKSquare]).inCheck == true)
                ((King)pieces[King.bKSquare]).inCheck = false;
        }
        else{
            isWhiteTurn = true;
            
            if(isSquareInCheck(King.wKSquare, pieces))
                ((King)pieces[King.wKSquare]).inCheck = true;
            else if(((King)pieces[King.wKSquare]).inCheck == true)
                ((King)pieces[King.wKSquare]).inCheck = false;
        }
        
        System.out.println("moved");
        hideHints();
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        
        for(int i=0; i<64; i++){
            if(e.getSource()==pieces[i]){
                containor.setComponentZOrder(pieces[i], 0);
                if(selectedPiece!=null&&selectedPiece!=pieces[i]){
                    if(isValidMove(selectedPiece.square,i)){
                        movePiece(selectedPiece,selectedPiece.square,i);
                        selectedPiece=null;
                    }
                    else{
                        if(pieces[i]!=null){
                            selectedPiece=pieces[i];
                            
                            firstTouch = true;
                            showHints(i);
                        }
                    }
                }
                else if(selectedPiece!=null&&selectedPiece==pieces[i]){
                            firstTouch = false;
                        }
                else{
                    selectedPiece=pieces[i];
                    
                    firstTouch = true;
                    showHints(i);
                }
                
            }
            
            else if(e.getSource()==square[i]){
                if(selectedPiece!=null&&pieces[i]==null){
                    if(isValidMove(selectedPiece.square,i)){
                        movePiece(selectedPiece,selectedPiece.square,i);
                        selectedPiece=null;
                    }
                    else{
                        hideHints();
                    }
                }
            }
        }
        
        
        
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        for(int i=0; i<64; i++){
            if(e.getSource()==pieces[i]){
                if(selectedPiece!=null){
                    System.out.println("i:"+i);
                    System.out.println("to: " + selectedPiece.square);
                    if(isValidMove(i,selectedPiece.square)){
                        movePiece(selectedPiece,i,selectedPiece.square);
                        selectedPiece=null;
                    }
                    else{
                        System.out.println("not valid");
                        if(selectedPiece!=null&&selectedPiece.square==i&&!firstTouch){
                            selectedPiece=null;
                            hideHints();
                        }
                        else if(selectedPiece!=null&&selectedPiece.square==i&&firstTouch){
                            firstTouch = false;
                        }
                        pieces[i].setSquare(i);
                    }
                }
            }
        }
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
