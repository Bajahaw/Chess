package chess;
import javax.swing.ImageIcon;

public class Rook extends Piece{
    public boolean notMoved = true;
    public Rook(int square, boolean whiteColor) {
        super(square);
        isWhite = whiteColor;
        if(isWhite)
            img = new ImageIcon("wr.png").getImage();
        else
            img = new ImageIcon("br.png").getImage();
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
//        for(int i=0; i<validMoves.size(); i++){
//            temp = Arrays.copyOf(position, position.length);
//            temp[square] = null;
//            temp[validMoves.get(i)] = this;
//            //if(((King)temp[isWhite? King.wKSquare:King.bKSquare]).inCheck)// not here
//            if(Board.isSquareInCheck(isWhite? King.wKSquare:King.bKSquare, temp)){
//                validMoves.remove(i);
//            }
//        }
    }
}
