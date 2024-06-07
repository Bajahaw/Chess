package chess;
import javax.swing.ImageIcon;

public class Rook extends Piece{
    public boolean notMoved = true;
    public Rook(int square, boolean whiteColor) {
        super(square);
        isWhite = whiteColor;
        if(isWhite)
            img = new ImageIcon("pngs/wr.png").getImage();
        else
            img = new ImageIcon("pngs/br.png").getImage();
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
    }
    
    @Override
    void calculateValidMoves(int initialSquare, Piece[] position) {
        validMoves.clear();
        int pointer = initialSquare;
        while(pointer%8 < 7){
            pointer+=1;
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
        while(pointer%8 > 0){
            pointer-=1;
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
        while(pointer/8 > 0){
            pointer-=8;
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
        while(pointer/8 < 7){
            pointer+=8;
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
        return "R";
    }
}
