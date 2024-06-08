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
            img = new ImageIcon("pngs/wk.png").getImage();
        else
            img = new ImageIcon("pngs/bk.png").getImage();
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
    }
    @Override
    boolean isValidMove(int sqr1,int sqr2,Piece[] position) {
        if(position[sqr1] != null){
            calculateValidMoves(sqr1, position);
            checkCastling(position);
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
                //System.out.println("King moves: "+move + " Not moved: " + notMoved );
                if(sqr2==move)
                    return true;
            }
        }
        return false;
    }

    private void checkCastling(Piece[] position){
        if(isWhite){
            if(notMoved && !inCheck){
                if(  Board.wQueenSideCastle &&
                        position[56] instanceof Rook &&
                        ((Rook)position[56]).notMoved &&
                        position[57] == null &&
                        position[58] == null &&
                        position[59] == null &&
                        !Board.isSquareInCheck(58 , position) &&
                        !Board.isSquareInCheck(59 , position) ){
                    validMoves.add(58);
                }
                else if(!notMoved || !(position[56] instanceof Rook) || !((Rook)position[56]).notMoved){
                    Board.wQueenSideCastle = false;
                }
                if(  Board.wKingSideCastle &&
                        position[63] instanceof Rook &&
                        ((Rook)position[63]).notMoved &&
                        position[61]==null&&position[62]==null &&
                        !Board.isSquareInCheck(62 , position) &&
                        !Board.isSquareInCheck(61 , position) ){
                    validMoves.add(62);
                }
                else if(!notMoved || !(position[63] instanceof Rook) || !((Rook)position[63]).notMoved){
                    Board.wKingSideCastle = false;
                }
            }
        }
        else{
            if(notMoved && !inCheck){
                if( Board.bQueenSideCastle &&
                        position[0] instanceof Rook &&
                        ((Rook)position[0]).notMoved &&
                        position[1] == null &&
                        position[2] == null &&
                        position[3] == null &&
                        !Board.isSquareInCheck(2 , position) &&
                        !Board.isSquareInCheck(3 , position) ){
                    validMoves.add(2);
                }
                else if(!notMoved || !(position[0] instanceof Rook) || !((Rook)position[0]).notMoved){
                    Board.bQueenSideCastle = false;
                }
                if( Board.bKingSideCastle &&
                        position[7] instanceof Rook &&
                        ((Rook)position[7]).notMoved &&
                        position[6]==null&&position[5]==null &&
                        !Board.isSquareInCheck(6 , position) &&
                        !Board.isSquareInCheck(5 , position) ){
                    validMoves.add(6);
                }
                else if(!notMoved || !(position[7] instanceof Rook) || !((Rook)position[7]).notMoved){
                    Board.bKingSideCastle = false;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "K";
    }
}
