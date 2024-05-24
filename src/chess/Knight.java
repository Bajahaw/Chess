package chess;
import javax.swing.ImageIcon;

public class Knight extends Piece{
    
    public Knight(int square, boolean whiteColor) {
        super(square);
        isWhite = whiteColor;
        if(isWhite)
            img = new ImageIcon("wn.png").getImage();
        else
            img = new ImageIcon("bn.png").getImage();
    }

    @Override
    void calculateValidMoves(int initialSquare, Piece[] position) {
        validMoves.clear();
        if(initialSquare%8 > 0){
            if(initialSquare-17>=0 && (position[initialSquare-17] == null || position[initialSquare-17].isWhite != isWhite)){
                validMoves.add(initialSquare-17);
            }
            if(initialSquare+15<64 && (position[initialSquare+15] == null || position[initialSquare+15].isWhite != isWhite)){
                validMoves.add(initialSquare+15);
            }
            if(initialSquare%8 > 1){
                if(initialSquare-10>=0 && (position[initialSquare-10] == null || position[initialSquare-10].isWhite != isWhite)){
                    validMoves.add(initialSquare-10);
                }
                if(initialSquare+6<64 && (position[initialSquare+6] == null || position[initialSquare+6].isWhite != isWhite)){
                    validMoves.add(initialSquare+6);
                }
            }
        }
        if(initialSquare%8 < 7){
            if(initialSquare-15>=0 && (position[initialSquare-15] == null || position[initialSquare-15].isWhite != isWhite)){
                validMoves.add(initialSquare-15);
            }
            if(initialSquare+17<64 && (position[initialSquare+17] == null || position[initialSquare+17].isWhite != isWhite)){
                validMoves.add(initialSquare+17);
            }
            if(initialSquare%8 < 6){
                if(initialSquare-6>=0 && (position[initialSquare-6] == null || position[initialSquare-6].isWhite != isWhite)){
                    validMoves.add(initialSquare-6);
                }
                if(initialSquare+10<64 && (position[initialSquare+10] == null || position[initialSquare+10].isWhite != isWhite)){
                    validMoves.add(initialSquare+10);
                }
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
