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
//        for(int i=0; i<validMoves.size(); i++){
//            temp = Arrays.copyOf(position, position.length);
//            temp[square] = null;
//            temp[validMoves.get(i)] = this;
//            //if(((King)temp[isWhite? King.wKSquare:King.bKSquare]).inCheck)// not here
//            if(Board.isSquareInCheck(isWhite? King.wKSquare:King.bKSquare, temp)){
//                validMoves.remove(i);
//            }
//        }
//        if(initialSquare%8!=0){
//            while(pointer >= 0){
//                if(position[pointer] == null){
//                    validMoves.add(pointer);
//                    if(pointer%8 == 0){
//                        break;
//                    }
//                }
//                else{
//                    if(position[pointer].isWhite != isWhite){
//                        validMoves.add(pointer);
//                    }
//                    break;
//                }
//                pointer -= 9;
//            }
//            pointer = initialSquare+7;
//            while(pointer < 64){
//                if(position[pointer] == null){
//                    validMoves.add(pointer);
//                    if(pointer%8 == 0){
//                        break;
//                    }
//                }
//                else{
//                    if(position[pointer].isWhite != isWhite){
//                        validMoves.add(pointer);
//                    }
//                    break;
//                }
//                pointer += 7;
//            }
//        }
//        if(initialSquare%8!=7){
//            pointer = initialSquare-7;
//            while(pointer >= 0){
//                if(position[pointer] == null){
//                    validMoves.add(pointer);
//                    if(pointer%8 == 7){
//                        break;
//                    }
//                }
//                else{
//                    if(position[pointer].isWhite != isWhite){
//                        validMoves.add(pointer);
//                    }
//                    break;
//                }
//                pointer -= 7;
//            }
//            pointer = initialSquare+9;
//            while(pointer < 64){
//                if(position[pointer] == null){
//                    validMoves.add(pointer);
//                    if(pointer%8 == 7){
//                        break;
//                    }
//                }
//                else{
//                    if(position[pointer].isWhite != isWhite){
//                        validMoves.add(pointer);
//                    }
//                    break;
//                }
//                pointer += 9;
//            }
//        }
    }
}
