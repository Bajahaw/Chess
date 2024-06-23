package chess;

import javax.swing.plaf.IconUIResource;
import java.util.*;

public class Engine {
    Board board;
    Piece[] currentBoard;
    Piece[] temp;
    ArrayList<Piece> ownPieces;
    boolean isPlayingWhite = false;
    Random random;
    public Engine(Board board){
        this.board = board;
        currentBoard = board.pieces;
        random = new Random();
        temp = Arrays.copyOf(currentBoard, currentBoard.length);
        ownPieces = new ArrayList<>();
        for(int i=0; i<64; i++){
            //System.out.println(currentBoard[i] != null && !currentBoard[i].isWhite);
            if(currentBoard[i] != null && !currentBoard[i].isWhite){
                ownPieces.add(currentBoard[i]);
                System.out.println(i);
            }
        }
    }
    public void updateOwnPieces(){
        for(int i=0; i<64; i++){
            //System.out.println(currentBoard[i] != null && !currentBoard[i].isWhite);
            if(currentBoard[i] != null && !currentBoard[i].isWhite){
                ownPieces.add(currentBoard[i]);
                System.out.println(i);
            }
        }
    }
    public ArrayList<String> getAllMoves(Piece[] position){
        ArrayList<String> moves = new ArrayList<>();
        //System.out.println("own"+ownPieces.size());
        for(int i=0; i<ownPieces.size(); i++){
            ownPieces.get(i).isValidMove(ownPieces.get(i).square,i,position);
            for(int j=0; j<ownPieces.get(i).validMoves.size(); j++){
                if(!Board.isWhiteTurn ^ ownPieces.get(i).isWhite) {
                    if(!board.isValidMove(ownPieces.get(i).square, ownPieces.get(i).validMoves.get(j)))
                        System.out.println("wrong one!!!");
                    moves.add(ownPieces.get(i).square + "-" + ownPieces.get(i).validMoves.get(j));
                    System.out.println(ownPieces.get(i).square + "-" + ownPieces.get(i).validMoves.get(j));
                }
            }
        }
        return moves;
    }
    public int evaluate(){
        return 0;
    }
    public void search(Piece[] position, int depth){
        Random random = new Random();
        int initialSquare = 0;
        int bestMove = 0;
        int movesCount = 0;
        for(int i=0; i<ownPieces.size(); i++){
            ownPieces.get(i).isValidMove(i,i,currentBoard);
            movesCount += ownPieces.get(i).validMoves.size();
        }
        for(int i=0; i<ownPieces.size(); i++){

        }

    }
    public void makeMove(){
        if((board.isDraw || board.isCheckmate))
            return;
        currentBoard = board.pieces;
        ArrayList<String> moves = getAllMoves(currentBoard);
        System.out.println(moves.size());
        int moveNum = random.nextInt(moves.size());
        //splitting the string move
        String[] move = moves.get(moveNum).split("-",2);
        System.out.println("ownPieces -> " + ownPieces.size());
        System.out.println(move[0] + " -> "+ move[1]);
        if(board.isValidMove(Integer.parseInt(move[0]), Integer.parseInt(move[1])))
            board.movePiece(currentBoard[Integer.parseInt(move[0])], Integer.parseInt(move[0]), Integer.parseInt(move[1]));
        else{
            if(!(board.isDraw || board.isCheckmate))
                System.out.println("not Valid !!!");
        }
    }

    public void test(Piece[] position){
        //TODO
    }
}
