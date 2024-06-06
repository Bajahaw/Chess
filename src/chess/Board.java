package chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Board extends JPanel implements MouseListener{

    JPanel containor;
    JLabel[] square = new JLabel[64];
    Piece[] pieces = new Piece[64];
    ArrayList<String> moves = new ArrayList<>();
    Piece selectedPiece;
//    int[] blackPieces = new int[16];
//    int[] whitePieces = new int[16];
    static boolean wKingSideCastle = false;
    static boolean bKingSideCastle = false;
    static boolean wQueenSideCastle = false;
    static boolean bQueenSideCastle = false;
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
        String startPosition = "rnbqkbnr/pppppp1p/8/8/8/8/PPPPPPpP/RNBQKBNR w KQkq - 0 1";
        setFEN(startPosition);
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
        square[index].setFont(new Font("Castellar",Font.BOLD,120));
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
                square[pieces[sqr].validMoves.get(i)].setText("•");
            }
        }
        containor.repaint();
    }

    public boolean isValidMove( int sqr1, int sqr2){
        //System.out.println("BK: "+King.bKSquare);
        //System.out.println("WH: "+King.wKSquare);
        if(isCheckmate(isWhiteTurn? King.wKSquare:King.bKSquare, pieces))
            System.out.println("Checkmate!!");
        if(isWhiteTurn^pieces[sqr1].isWhite)
            return false;
        return pieces[sqr1]!=null? pieces[sqr1].isValidMove(sqr1, sqr2, pieces): false;
    }

    public static boolean isSquareInCheck(int square, Piece[] board){
        //System.out.println("Checking square: " + square);
        for(Piece piece : board){
            if(piece != null && piece.isWhite != isWhiteTurn ){
                piece.calculateValidMoves(piece.square, board);
                for(int move : piece.validMoves) if(move == square){
                    //System.out.println("Check!!");
                    return true;
                }
            }
        }
        //System.out.println("no check!!");
        return false;
    }
    private void checkCastling(Piece[] position){
        wQueenSideCastle = false;
        wKingSideCastle = false;
        bQueenSideCastle = false;
        bKingSideCastle = false;
        if(isWhiteTurn){
            if(((King)position[King.wKSquare]).notMoved && !((King)position[King.wKSquare]).inCheck){
                if( position[56] instanceof Rook &&
                        ((Rook)position[56]).notMoved &&
                        position[57] == null &&
                        position[58] == null &&
                        position[59] == null &&
                        !isSquareInCheck(58 , position) &&
                        !isSquareInCheck(59 , position) ){
                    wQueenSideCastle = true;
                }
                else wQueenSideCastle = false;
                if(position[63] instanceof Rook &&
                        ((Rook)position[63]).notMoved &&
                        position[61]==null&&position[62]==null &&
                        !isSquareInCheck(62 , position) &&
                        !isSquareInCheck(61 , position) ){
                    wKingSideCastle = true;
                }
                else wKingSideCastle = false;
            }
        }
        else{
            if(((King)position[King.bKSquare]).notMoved && !((King)position[King.bKSquare]).inCheck){
                if( position[0] instanceof Rook &&
                        ((Rook)position[0]).notMoved &&
                        position[1] == null &&
                        position[2] == null &&
                        position[3] == null &&
                        !isSquareInCheck(2 , position) &&
                        !isSquareInCheck(3 , position) ){
                    bQueenSideCastle = true;
                }
                else bQueenSideCastle = false;
                if(position[7] instanceof Rook &&
                        ((Rook)position[7]).notMoved &&
                        position[6]==null&&position[5]==null &&
                        !isSquareInCheck(6 , position) &&
                        !isSquareInCheck(5 , position) ){
                    bKingSideCastle = true;
                }
                else bKingSideCastle = false;
            }
        }
    }

    public boolean isCheckmate(int kingSquare, Piece[] position){
        boolean checkmate = ((King)position[kingSquare]).inCheck;
        if(((King)position[kingSquare]).inCheck){
            position[kingSquare].isValidMove(kingSquare, kingSquare, position);
            if(position[kingSquare].validMoves.size()>0){
                checkmate = false;
            }
            else{
                for(Piece piece : position){
                    if(piece != null && piece.isWhite == isWhiteTurn){
                        piece.isValidMove(piece.square, piece.square, position);
                        if(piece.validMoves.size()>0){
                            checkmate = false;
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("Checkmate: " + checkmate);
        return checkmate;
    }

    public void isDraw(int kSquare, Piece[] position) {
        boolean draw = true;
        if(((King)position[kSquare]).inCheck){
            draw = false;
        }
        else{
            for(Piece piece : position){
                if(piece != null && piece.isWhite == isWhiteTurn){
                    piece.isValidMove(piece.square, piece.square, position);
                    if(piece.validMoves.size()>0){
                        draw = false;
                        break;
                    }
                }
            }
        }
        System.out.println("Draw: " + draw);
    }

    private String squareToString(int square){
        return "" + (char)(square%8+97) + (char)(8-square/8+48);
    }

    private String moveToString(Piece selectedPiece, int sqr1, int square) {
        String move = "";
        if(selectedPiece instanceof King && Math.abs(sqr1-square)==2){
            if(square==sqr1+2){
                move = "O-O";
            }
            if(square==sqr1-2){
                move = "O-O-O";
            }
        }
        else{
            move = selectedPiece.toString();
            move += isMoreThanOnePiece(selectedPiece, sqr1, square);
            if(selectedPiece instanceof Pawn && sqr1%8 != square%8){
                move += (char)(sqr1%8+97);
            }
            if(pieces[square]!=null){
                move += "x";
            }

            move += squareToString(square);
        }
        return move;
    }

    private String isMoreThanOnePiece(Piece movingPiece, int sqr1, int square2) {
        String move = "";
        ArrayList<Integer> squares = new ArrayList<>();
        for(Piece piece : pieces){
            if(piece != null && piece.isWhite == movingPiece.isWhite &&
                    piece.toString()==movingPiece.toString() && piece != movingPiece){

                piece.isValidMove(piece.square, piece.square, pieces);
                for(int move2 : piece.validMoves){
                    if(move2 == square2){
                        //System.out.println("found one hereeeeeeeeeeeeeeeeeeeeeeeee!!! :" + piece.square);
                        squares.add(piece.square);
                    }
                }
            }
        }
        if(squares.size() > 0){
            for(int sqr : squares){
                if(sqr%8 != sqr1%8){
                    move += (char)(sqr1%8+97);
                    break;
                }
                else{
                    move += (char)(8-sqr1/8+48);
                    break;
                }
            }
        }
        return move;
    }

    private void promotePawn(Pawn pawn,int sqr1, int square){
        containor.remove(pawn);
        freezeGame();
        newPiece(sqr1, square, pawn.isWhite);
    }

    private void freezeGame() {
        for(int i=0; i<64; i++){
            if(pieces[i]!=null){
                System.out.println(pieces[i].getMouseListeners().length);
                pieces[i].removeMouseMotionListener(pieces[i].getMouseMotionListeners()[0]);
                pieces[i].removeMouseListener(pieces[i].getMouseListeners()[1]);
                pieces[i].removeMouseListener(pieces[i].getMouseListeners()[0]);
            }
        }
    }

    private void unFreezeGame() {
        for(int i=0; i<64; i++){
            if(pieces[i]!=null){
                pieces[i].addMouseListener(pieces[i].pieceListener);
                pieces[i].addMouseMotionListener(pieces[i].pieceMotionListener);
                pieces[i].addMouseListener(Board.this);
            }
        }
    }

    private void newPiece(int square1 ,int square2, boolean isWhite) {
        //freezing the game

        Piece[] promotionPieces = new Piece[4];
        JPanel promotionPanel = new JPanel();
        promotionPanel.setBounds(square2%8*75,isWhite? 0:300,75,300);
        promotionPanel.setLayout(new GridLayout(4,1));
        promotionPanel.setBackground(new Color(255,255,255));
        promotionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        promotionPanel.setOpaque(true);
        containor.validate();
        containor.add(promotionPanel);

        for(int i=0; i<4; i++){
            promotionPieces[i] = new Rook(square2, isWhite);
            if(i==1)
                promotionPieces[i] = new Queen(square2, isWhite);
            if(i==2)
                promotionPieces[i] = new Bishop(square2, isWhite);
            if(i==3)
                promotionPieces[i] = new Knight(square2, isWhite);
            promotionPanel.add(promotionPieces[i]);
            promotionPanel.setComponentZOrder(promotionPieces[i], 0);
            promotionPieces[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}
                @Override
                public void mousePressed(MouseEvent e) {
                    unFreezeGame();
                    if ((e.getSource()) instanceof Rook) {
                        pieces[square1] = new Rook(square1, isWhite);
                    } else if ((e.getSource()) instanceof Queen) {
                        pieces[square1] = new Queen(square1, isWhite);
                    } else if ((e.getSource()) instanceof Bishop) {
                        pieces[square1] = new Bishop(square1, isWhite);
                    } else if ((e.getSource()) instanceof Knight) {
                        pieces[square1] = new Knight(square1, isWhite);
                    }
                    containor.remove(promotionPanel);
                    pieces[square1].setSquare(square1);
                    containor.add(pieces[square1]);
                    containor.setComponentZOrder(pieces[square1], 0);
                    pieces[square1].addMouseListener(Board.this);
                    moves.set(moves.size()-1 , moves.get(moves.size()-1) + "="+pieces[square1].toString());
                    movePiece(pieces[square1], square1, square2);
                    moves.remove(moves.size()-1);
                    containor.validate();
                    containor.repaint();
                }
                @Override
                public void mouseReleased(MouseEvent e) {}
                @Override
                public void mouseEntered(MouseEvent e) {}
                @Override
                public void mouseExited(MouseEvent e) {}
                
            });
        }
        containor.setComponentZOrder(promotionPanel, 0);
        containor.validate();
        containor.repaint();
    }

    private void movePiece(Piece selectedPiece,int sqr1, int square) {
        moves.add(moveToString(selectedPiece, sqr1, square));
        if(selectedPiece instanceof Pawn){
            if(((sqr1>7 && sqr1<16)&&(((Pawn) selectedPiece).isWhite))||((sqr1>47 && sqr1<56)&&!(((Pawn) selectedPiece).isWhite))){
                promotePawn((Pawn)selectedPiece, sqr1, square);
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
                moves.remove(moves.size()-1);
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
            }
        }
        
        //System.out.println(Pawn.enPassant + " enpassant");
        if( pieces[Pawn.enPassant] instanceof Pawn ){
            ((Pawn)pieces[Pawn.enPassant]).isEnPassan = false;
            Pawn.enPassant = 0;
        }

        if(selectedPiece instanceof King){
            if(square==sqr1+2){
                pieces[square-1] = pieces[sqr1+3];
                pieces[sqr1+3]=null;
                pieces[square-1].setSquare(square-1);
            }
            if(square==sqr1-2){
                pieces[square+1] = pieces[sqr1-4];
                pieces[sqr1-4]=null;
                pieces[square+1].setSquare(square+1);
            }
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
            
            if(isSquareInCheck(King.bKSquare, pieces) ){
                ((King)pieces[King.bKSquare]).inCheck = true;
                isCheckmate(King.bKSquare, pieces);
                if(isCheckmate(King.bKSquare, pieces))
                    moves.set(moves.size()-1, moves.get(moves.size()-1)+"#");
                else
                    moves.set(moves.size()-1, moves.get(moves.size()-1)+"+");
            }
            else if(((King)pieces[King.bKSquare]).inCheck && !isSquareInCheck(King.bKSquare, pieces))
                ((King)pieces[King.bKSquare]).inCheck = false;
            isDraw(King.bKSquare, pieces);
        }
        else{
            isWhiteTurn = true;
            
            if(isSquareInCheck(King.wKSquare, pieces) ){
                ((King)pieces[King.wKSquare]).inCheck = true;
                isCheckmate(King.wKSquare, pieces);
                if(isCheckmate(King.wKSquare, pieces))
                    moves.set(moves.size()-1, moves.get(moves.size()-1)+"#");
                else
                    moves.set(moves.size()-1, moves.get(moves.size()-1)+"+");
            }
            else if(((King)pieces[King.wKSquare]).inCheck && !isSquareInCheck(King.wKSquare, pieces))
                ((King)pieces[King.wKSquare]).inCheck = false;
            isDraw(King.wKSquare, pieces);
        }
        
        checkCastling(pieces);
        System.out.println("moved");
        getPGN();
        hideHints();
    }

    public void getPGN(){
        for(int i=0; i<moves.size(); i++)
            System.out.print((i%2==0? i/2+1 + ".":"")  + moves.get(i) + " ");
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
