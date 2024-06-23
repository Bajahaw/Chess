package chess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Engine {
    Board board;
    Piece[] currentBoard;
    Piece[] temp;
    public Engine(Board board){
        this.board = board;
        currentBoard = board.pieces;
        temp = Arrays.copyOf(currentBoard, currentBoard.length);
    }
    public Map<Integer,Integer> getAllMoves(Piece[] position){
        Map<Integer,Integer> moves = new HashMap<>();
        for(int i=0; i<64; i++){
            if(position[i] != null && position[i].isWhite == Board.isWhiteTurn){
                position[i].calculateValidMoves(i, position);
                for(Integer move : position[i].validMoves){
                    moves.put(i, move);
                }
            }
        }
        return moves;
    }
    public int evaluate(){
        return 0;
    }
    public void search(Piece[] position, int depth){
        int initialSquare = 0;
        int bestMove = 0;
        makeMove(initialSquare, bestMove);
    }
    public void makeMove(int sqr1, int sqr2){
        board.movePiece(currentBoard[sqr1], sqr1, sqr2);
    }

    public void test(Piece[] position){
        //TODO
    }
}
