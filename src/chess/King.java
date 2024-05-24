package chess;

import java.util.Arrays;

import javax.swing.ImageIcon;

public class King extends Piece{
    public static int wKSquare;
    public static int bKSquare;
    public boolean notMoved = true;
    public boolean inCheck = false; // need to check in case it is not the start position
    public King(int square, boolean whiteColor) {
        super(square);
        isWhite = whiteColor;
        if(isWhite)
            img = new ImageIcon("wk.png").getImage();
        else
            img = new ImageIcon("bk.png").getImage();
    }
    
    void isFirstMove(int square){
        if(square!=DEFAULT_SQUARE){
            notMoved = false;
        }
    }
    
    @Override
    public void setSquare(int square){
        super.setSquare(square);
        if(notMoved){
            isFirstMove(square);
        }
        if(isWhite)
            wKSquare = square;
        else
            bKSquare = square;
    }
    
    boolean checkCondition(int x, int i){
        boolean[] Conditions = {x%8 < 7, x%8 > 0, x/8 < 7, x/8 > 0,
                                    x%8<7 && x/8<7, x%8 > 0 && x/8 > 0,
                                    x%8>0 && x/8<7, x%8 < 7 && x/8 > 0};
        return Conditions[i];
    }
    
    @Override
    public void calculateValidMoves(int initialSquare, Piece[] position) {
        validMoves.clear();
        int pointer;
        int[] Directions = {1,-1,8,-8,9,-9,7,-7};
        for(int i=0; i<8; i++){
            pointer = initialSquare;
            //same as queen but if instead of while
            if(checkCondition(pointer, i)){
                pointer+=Directions[i];
                if(position[pointer] == null)
                    validMoves.add(pointer);
                else{
                    if(position[pointer].isWhite != isWhite){
                        validMoves.add(pointer);
                    }
                }
            }
        }
//        for(int i=0; i<validMoves.size(); i++){
//            temp = Arrays.copyOf(position, position.length);
//            temp[square] = null;
//            temp[validMoves.get(i)] = this;
//            //if(inCheck)
//            if(Board.isSquareInCheck(validMoves.get(i), temp)){
//                validMoves.remove(i);
//            }
//        }
    }
    @Override
    boolean isValidMove(int sqr1,int sqr2,Piece[] position) {
        if(position[sqr1] != null){
            calculateValidMoves(sqr1, position);
            for(int i=0; i<validMoves.size(); i++){
                temp = Arrays.copyOf(position, position.length);
                temp[sqr1] = null;
                temp[validMoves.get(i)] = this;
                if(Board.isSquareInCheck(validMoves.get(i), temp)){
                    validMoves.remove(i);
                    i--;
                }
            }
            for(Integer move : validMoves){
                System.out.println("King moves: "+move + " " );
                if(sqr2==move)
                    return true;
            }
        }
        return false;
    }
}
