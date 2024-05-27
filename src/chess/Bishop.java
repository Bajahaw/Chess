package chess;
import javax.swing.ImageIcon;

public class Bishop extends Piece{
    
    public Bishop(int square, boolean whiteColor) {
        super(square);
        isWhite = whiteColor;
        if(isWhite)
            img = new ImageIcon("wb.png").getImage();
        else
            img = new ImageIcon("bb.png").getImage();
    }

    @Override
    void calculateValidMoves(int initialSquare, Piece[] position) {
        validMoves.clear();
        //four while loops to calculate moves in each direction
        //this can be optemized
        int pointer = initialSquare;
        while(pointer%8 < 7 && pointer/8 < 7){
            pointer+=9;
            if(position[pointer] == null)
                validMoves.add(pointer);
            else{
                if(position[pointer].isWhite != isWhite){
                    validMoves.add(pointer);
                }
                break;
            }
        }
        pointer = initialSquare;
        while(pointer%8 > 0 && pointer/8 > 0){
            pointer-=9;
            if(position[pointer] == null)
                validMoves.add(pointer);
            else{
                if(position[pointer].isWhite != isWhite){
                    validMoves.add(pointer);
                }
                break;
            }
        }
        pointer = initialSquare;
        while(pointer%8 > 0 && pointer/8 < 7){
            pointer+=7;
            if(position[pointer] == null)
                validMoves.add(pointer);
            else{
                if(position[pointer].isWhite != isWhite){
                    validMoves.add(pointer);
                }
                break;
            }
        }
        pointer = initialSquare;
        while(pointer%8 < 7 && pointer/8 > 0){
            pointer-=7;
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
    @Override
    public String toString() {
        return "B";
    }
}
