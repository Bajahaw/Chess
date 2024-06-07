package chess;
import javax.swing.ImageIcon;

public class Pawn extends Piece{
    boolean firstMove;
    boolean isEnPassan = false;
    public static int enPassant;
    public Pawn(int square, boolean whiteColor) {
        super(square);
        isWhite = whiteColor;
        if(isWhite)
            img = new ImageIcon("pngs/wp.png").getImage();
        else
            img = new ImageIcon("pngs/bp.png").getImage();
    }
    
    @Override
    void calculateValidMoves(int initialSquare, Piece[] position) {
        validMoves.clear();
        if(isWhite){
            firstMove = initialSquare>47&&initialSquare<56; //another way was implemented in king and rook not yet here

            if(initialSquare-8>=0&&position[initialSquare-8]==null){
                validMoves.add(initialSquare-8);
                if(firstMove&&position[initialSquare-16]==null){
                   validMoves.add(initialSquare-16);
                }
            }

            if(initialSquare-7>=0&&(initialSquare-7)%8!=0&&position[initialSquare-7]!=null){
                if(position[initialSquare-7].isWhite!=this.isWhite){
                    validMoves.add(initialSquare-7);
                }
            }

            if(initialSquare-9>=0&&(initialSquare)%8!=0&&position[initialSquare-9]!=null){
                if(position[initialSquare-9].isWhite!=this.isWhite){
                    validMoves.add(initialSquare-9);
                }
            }
            //En-Passant:
            if(initialSquare>23&&initialSquare<32){
                if(initialSquare>24 && position[initialSquare-1] instanceof Pawn && position[initialSquare-1].isWhite!=isWhite && ((Pawn)position[initialSquare-1]).isEnPassan){
                    validMoves.add(initialSquare-9);
                }
                if(initialSquare<32 && position[initialSquare+1] instanceof Pawn && position[initialSquare+1].isWhite!=isWhite && ((Pawn)position[initialSquare+1]).isEnPassan){
                    validMoves.add(initialSquare-7);
                }
            }
        }
        else{
            firstMove = initialSquare>7&&initialSquare<16;
            
            //move one square - or - two incase first move
            if(initialSquare+8<64&&position[initialSquare+8]==null){
                validMoves.add(initialSquare+8);
                if(firstMove&&position[initialSquare+16]==null){
                   validMoves.add(initialSquare+16);
                }
            }
            //enables taking
            if(initialSquare+7<64&&(initialSquare)%8!=0&&position[initialSquare+7]!=null){
                if(position[initialSquare+7].isWhite!=this.isWhite){
                    validMoves.add(initialSquare+7);
                }
            }
            //potential bug in herrrrreeeeeeeeee|
            if(initialSquare+9<64&&(initialSquare+9)%8!=0&&position[initialSquare+9]!=null){
                if(position[initialSquare+9].isWhite!=this.isWhite){
                    validMoves.add(initialSquare+9);
                }
            }
            //En-Passant:
            if(initialSquare>31&&initialSquare<40){
                if(initialSquare>32 && position[initialSquare-1] instanceof Pawn && position[initialSquare-1].isWhite!=isWhite && ((Pawn)position[initialSquare-1]).isEnPassan){
                    validMoves.add(initialSquare+7);
                }
                //potential bug here
                if(initialSquare<39 && position[initialSquare+1] instanceof Pawn && position[initialSquare+1].isWhite!=isWhite && ((Pawn)position[initialSquare+1]).isEnPassan){
                    validMoves.add(initialSquare+9);
                }
            }
        }
    }
    @Override
    public String toString() {
        return "";
    }
}
