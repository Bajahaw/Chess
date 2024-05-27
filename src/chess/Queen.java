package chess;
import javax.swing.ImageIcon;

public class Queen extends Piece{
    
    public Queen(int square, boolean whiteColor) {
        super(square);
        isWhite = whiteColor;
        if(isWhite)
            img = new ImageIcon("wq.png").getImage();
        else
            img = new ImageIcon("bq.png").getImage();
    }
    //Not to go out of the board
    boolean checkCondition(int x, int i){
        boolean[] Conditions = {x%8 < 7, x%8 > 0, x/8 < 7, x/8 > 0,
                                    x%8<7 && x/8<7, x%8 > 0 && x/8 > 0,
                                    x%8>0 && x/8<7, x%8 < 7 && x/8 > 0};
        return Conditions[i];
    }
    @Override
    void calculateValidMoves(int initialSquare, Piece[] position) {
        validMoves.clear();
        int pointer;
        int[] Directions = {1,-1,8,-8,9,-9,7,-7};
        for(int i=0; i<8; i++){
            pointer = initialSquare;
            while(checkCondition(pointer, i)){
                pointer+=Directions[i];
                if(position[pointer] == null)
                    validMoves.add(pointer);
                else{
                    if(position[pointer].isWhite != isWhite){
                        validMoves.add(pointer);
                    }
                    break;
                }
            }
        }
    }
    @Override
    public String toString() {
        return "Q";
    }
}
