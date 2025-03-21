package chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Board extends JPanel implements MouseListener {

    JPanel containor;
    ControlPanel controlPanel;
    JLabel[] square = new JLabel[64];
    Piece[] pieces = new Piece[64];
    ArrayList<String> moves = new ArrayList<>();
    Engine engine;
    Piece selectedPiece;
    //    int[] blackPieces = new int[16];
//    int[] whitePieces = new int[16];
    static boolean wKingSideCastle = false;
    static boolean bKingSideCastle = false;
    static boolean wQueenSideCastle = false;
    static boolean bQueenSideCastle = false;
    boolean firstTouch = false;
    public static boolean isWhiteTurn = true;
    public boolean isCheckmate = false;
    public boolean isDraw = false;
    public boolean isGameFrozen = false;
    int[] lastMove = new int[4];
    Color firstColor = new Color(227, 236, 245);//new Color(233,215,180);//
    Color secondColor = new Color(112, 144, 167);//new Color(177,137,103);//
    public static String startPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    public Board() {

        containor = new JPanel();
        containor.setPreferredSize(new Dimension(600, 600));
        containor.setLayout(null);

        controlPanel = new ControlPanel(this);

        this.setBounds(0, 0, 600, 600);
        this.setLayout(new GridLayout(8, 8));

        setBoard();
        engine = new Engine(this);
        containor.add(this);
    }

    public void setBoard() {
        setSquare(0, firstColor);
        for (int i = 1; i < 64; i++) {
            if (i % 8 == 0)
                setSquare(i, square[i - 1].getBackground() == firstColor ? firstColor : secondColor);
            else
                setSquare(i, square[i - 1].getBackground() == firstColor ? secondColor : firstColor);
        }
        setFEN(startPosition);
    }

    private void setPiecesMouseListener() {
        for (int i = 0; i < 64; i++) {
            if (pieces[i] != null) {
                pieces[i].addMouseListener(this);
            }
        }
    }

    private void setSquare(int index, Color color) {
        square[index] = new JLabel();
        square[index].setBackground(color);
        square[index].setFont(new Font("Castellar", Font.BOLD, 120));
        square[index].setForeground(new Color(
                (firstColor.getRed() + secondColor.getRed()) / 2,
                (firstColor.getGreen() + secondColor.getGreen()) / 2,
                (firstColor.getBlue() + secondColor.getBlue()) / 2));//new Color(205,176,141)
        square[index].setHorizontalAlignment(JLabel.CENTER);
        square[index].setOpaque(true);
        square[index].addMouseListener(this);
        this.add(square[index]);
    }

    public void setFEN(String FEN) {
        //not full support of all rules of fen yet
        int localBKSquare = 0;
        int localWKSquare = 0;
        String[] splitFEN = FEN.split("\\s");
        for (int part = 0; part < splitFEN.length; part++) {
            int i = 0;
            if (part == 0) {
                int line = 0;
                int squarePointer = 0;
                while (line <= 7) {

                    if (splitFEN[part].charAt(i) < 57 && splitFEN[part].charAt(i) > 47)
                        squarePointer += Integer.parseInt(String.valueOf(splitFEN[part].charAt(i))) - 1;

                    if (splitFEN[part].charAt(i) == 'P') {
                        pieces[squarePointer] = new Pawn(squarePointer, true);
                        containor.add(pieces[squarePointer], 0);
                    }
                    if (splitFEN[part].charAt(i) == 'p') {
                        pieces[squarePointer] = new Pawn(squarePointer, false);
                        containor.add(pieces[squarePointer], 0);
                    }
                    if (splitFEN[part].charAt(i) == 'q') {
                        pieces[squarePointer] = new Queen(squarePointer, false);
                        containor.add(pieces[squarePointer], 0);
                    }
                    if (splitFEN[part].charAt(i) == 'Q') {
                        pieces[squarePointer] = new Queen(squarePointer, true);
                        containor.add(pieces[squarePointer], 0);
                    }
                    if (splitFEN[part].charAt(i) == 'r') {
                        pieces[squarePointer] = new Rook(squarePointer, false);
                        containor.add(pieces[squarePointer], 0);
                    }
                    if (splitFEN[part].charAt(i) == 'R') {
                        pieces[squarePointer] = new Rook(squarePointer, true);
                        containor.add(pieces[squarePointer], 0);
                    }
                    if (splitFEN[part].charAt(i) == 'k') {
                        pieces[squarePointer] = new King(squarePointer, false);
                        containor.add(pieces[squarePointer], 0);
                        localBKSquare = squarePointer;
                    }
                    if (splitFEN[part].charAt(i) == 'K') {
                        pieces[squarePointer] = new King(squarePointer, true);
                        containor.add(pieces[squarePointer], 0);
                        localWKSquare = squarePointer;
                    }
                    if (splitFEN[part].charAt(i) == 'n') {
                        pieces[squarePointer] = new Knight(squarePointer, false);
                        containor.add(pieces[squarePointer], 0);
                    }
                    if (splitFEN[part].charAt(i) == 'N') {
                        pieces[squarePointer] = new Knight(squarePointer, true);
                        containor.add(pieces[squarePointer], 0);
                    }
                    if (splitFEN[part].charAt(i) == 'b') {
                        pieces[squarePointer] = new Bishop(squarePointer, false);
                        containor.add(pieces[squarePointer], 0);
                    }
                    if (splitFEN[part].charAt(i) == 'B') {
                        pieces[squarePointer] = new Bishop(squarePointer, true);
                        containor.add(pieces[squarePointer], 0);
                    }

                    if (splitFEN[part].charAt(i) == '/') {
                        line++;
                        squarePointer--;
                    }
                    squarePointer++;
                    i++;
                    if (squarePointer > 63)
                        break;
                }
            }
            if (part == 1) {
                isWhiteTurn = Objects.equals(splitFEN[part], "w");
            }
            if (part == 2) {
                while (i < splitFEN[part].length()) {
                    switch (splitFEN[part].charAt(i)) {
                        case 'K':
                            wKingSideCastle = true;
                            break;
                        case 'k':
                            bKingSideCastle = true;
                            break;
                        case 'Q':
                            wQueenSideCastle = true;
                            break;
                        case 'q':
                            bQueenSideCastle = true;
                            break;
                    }
                    i++;
                }
            }
            if (part == 3) {
            }
            if (part == 4) {
            }
            if (part == 5) {
            }
        }
        setPiecesMouseListener();
        King.bKSquare = localBKSquare;
        King.wKSquare = localWKSquare;
        containor.repaint();
    }

    public void resetGame() {
        square[lastMove[0]].setBackground(lastMove[2] == 0 ? firstColor : secondColor);
        square[lastMove[1]].setBackground(lastMove[3] == 0 ? firstColor : secondColor);
        isCheckmate = false;
        isDraw = false;
        isGameFrozen = false;
        hideHints();
        containor.removeAll();
        for (int i = 0; i < 64; i++) {
            pieces[i] = null;
        }
        moves.clear();
        engine.moves = "";
        containor.add(this);
        revalidate();
        repaint();
    }

    private void hideHints() {
        for (int i = 0; i < 64; i++) {
            square[i].setText("");
        }
        containor.repaint();
    }

    public void showHints(int sqr) {
        //neeeeeeeed some optimization // not anymore :D at least for now :D
        hideHints();
        pieces[sqr].isValidMove(sqr, sqr, pieces);
        for (int i = 0; i < pieces[sqr].validMoves.size(); i++) {
            if (!isWhiteTurn ^ pieces[sqr].isWhite) {
                square[pieces[sqr].validMoves.get(i)].setText("•");
            }
        }
        containor.repaint();
    }

    public boolean isValidMove(int sqr1, int sqr2) {
        //System.out.println("BK: "+King.bKSquare);
        //System.out.println("WH: "+King.wKSquare);
        if (isCheckmate(isWhiteTurn ? King.wKSquare : King.bKSquare, pieces))
            System.out.println("Checkmate!!");
        if (pieces[sqr1] == null)
            return false;
        if (isWhiteTurn ^ pieces[sqr1].isWhite)
            return false;
        return pieces[sqr1].isValidMove(sqr1, sqr2, pieces);
    }

    public static boolean isSquareInCheck(int square, Piece[] board) {
        //System.out.println("Checking square: " + square);
        for (Piece piece : board) {
            if (piece != null && piece.isWhite != isWhiteTurn) {
                piece.calculateValidMoves(piece.square, board);
                for (int move : piece.validMoves)
                    if (move == square) {
                        //System.out.println("Check!!");
                        return true;
                    }
            }
        }
        //System.out.println("no check!!");
        return false;
    }

    public boolean isCheckmate(int kingSquare, Piece[] position) {
        boolean checkmate = ((King) position[kingSquare]).inCheck;
        if (((King) position[kingSquare]).inCheck) {
            position[kingSquare].isValidMove(kingSquare, kingSquare, position);
            if (!position[kingSquare].validMoves.isEmpty()) {
                checkmate = false;
            } else {
                for (Piece piece : position) {
                    if (piece != null && piece.isWhite == isWhiteTurn) {
                        piece.isValidMove(piece.square, piece.square, position);
                        if (!piece.validMoves.isEmpty()) {
                            checkmate = false;
                            break;
                        }
                    }
                }
            }
        }
        //System.out.println("Checkmate: " + checkmate);
        if (checkmate) {
            isCheckmate = true;
            freezeGame();
        }
        return checkmate;
    }

    public void isDraw(int kSquare, Piece[] position) {
        //TODO: implement draw BY repetition
        //TODO: implement draw BY 50 moves
        //TODO: implement draw BY insufficient material

        boolean draw = true;
        if (((King) position[kSquare]).inCheck) {
            draw = false;
        } else {
            for (Piece piece : position) {
                if (piece != null && piece.isWhite == isWhiteTurn) {
                    piece.isValidMove(piece.square, piece.square, position);
                    if (!piece.validMoves.isEmpty()) {
                        draw = false;
                        break;
                    }
                }
            }
        }
        System.out.println("Draw: " + draw);
        if (draw) {
            isDraw = true;
            freezeGame();
        }
    }

    private String squareToString(int square) {
        return "" + (char) (square % 8 + 97) + (char) (8 - square / 8 + 48);
    }

    public int stringToSquare(String square) {
        // System.out.println("Square: " + square);
        return (8 - Character.getNumericValue(square.charAt(1))) * 8 + (int) square.charAt(0) - 97;
    }

    private String moveToString(Piece selectedPiece, int sqr1, int square) {
        String move = "";
        if (selectedPiece instanceof King && Math.abs(sqr1 - square) == 2) {
            if (square == sqr1 + 2) {
                move = "O-O";
            }
            if (square == sqr1 - 2) {
                move = "O-O-O";
            }
        } else {
            move = selectedPiece.toString();
            move += isMoreThanOnePiece(selectedPiece, sqr1, square);
            if (selectedPiece instanceof Pawn && sqr1 % 8 != square % 8) {
                move += (char) (sqr1 % 8 + 97);
            }
            if (pieces[square] != null) {
                move += "x";
            }

            move += squareToString(square);
        }
        return move;
    }

    private String isMoreThanOnePiece(Piece movingPiece, int sqr1, int square2) {
        String move = "";
        ArrayList<Integer> squares = new ArrayList<>();
        for (Piece piece : pieces) {
            if (piece != null && piece.isWhite == movingPiece.isWhite &&
                    piece.toString() == movingPiece.toString() && piece != movingPiece) {

                piece.isValidMove(piece.square, piece.square, pieces);
                for (int move2 : piece.validMoves) {
                    if (move2 == square2) {
                        squares.add(piece.square);
                    }
                }
            }
        }
        if (!squares.isEmpty()) {
            for (int sqr : squares) {
                if (sqr % 8 != sqr1 % 8) {
                    move += (char) (sqr1 % 8 + 97);
                    break;
                } else {
                    move += (char) (8 - sqr1 / 8 + 48);
                    break;
                }
            }
        }
        return move;
    }

    private void promotePawn(Pawn pawn, int sqr1, int square) {
        containor.remove(pawn);
        freezeGame();
        newPiece(sqr1, square, pawn.isWhite);
    }

    private void freezeGame() {
        for (int i = 0; i < 64; i++) {
            if (pieces[i] != null) {
                for (MouseListener listener : pieces[i].getMouseListeners()) {
                    pieces[i].removeMouseListener(listener);
                }
                for (MouseMotionListener listener : pieces[i].getMouseMotionListeners()) {
                    pieces[i].removeMouseMotionListener(listener);
                }
            }
        }
    }

    private void unFreezeGame() {
        for (int i = 0; i < 64; i++) {
            if (pieces[i] != null) {

                pieces[i].addMouseListener(pieces[i].pieceListener);
                pieces[i].addMouseMotionListener(pieces[i].pieceMotionListener);
                pieces[i].addMouseListener(Board.this);
            }
        }
    }

    private void newPiece(int square1, int square2, boolean isWhite) {
        Piece[] promotionPieces = new Piece[4];
        JPanel promotionPanel = new JPanel();
        promotionPanel.setBounds(square2 % 8 * 75, isWhite ? 0 : 300, 75, 300);
        promotionPanel.setLayout(new GridLayout(4, 1));
        promotionPanel.setBackground(new Color(255, 255, 255));
        promotionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        promotionPanel.setOpaque(true);
        containor.validate();
        containor.add(promotionPanel);

        for (int i = isWhite ? 0 : 3; isWhite ? i < 4 : i >= 0; ) {
            if (i == 0)
                promotionPieces[i] = new Knight(square2, isWhite);
            if (i == 1)
                promotionPieces[i] = new Bishop(square2, isWhite);
            if (i == 2)
                promotionPieces[i] = new Rook(square2, isWhite);
            if (i == 3)
                promotionPieces[i] = new Queen(square2, isWhite);
            promotionPanel.add(promotionPieces[i]);
            promotionPanel.setComponentZOrder(promotionPieces[i], 0);
            promotionPieces[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    unFreezeGame();
                    if ((e.getSource()) instanceof Rook) {
                        pieces[square1] = new Rook(square1, isWhite);
                    } else if ((e.getSource()) instanceof Queen) {
                        pieces[square1] = new Queen(square1, isWhite);
                    } else if ((e.getSource()) instanceof Bishop) {
                        pieces[square1] = new Bishop(square1, isWhite);
                    } else if ((e.getSource()) instanceof Knight) {
                        pieces[square1] = new Knight(square1, isWhite);
                    }
                    containor.remove(promotionPanel);
                    pieces[square1].setSquare(square1);
                    containor.add(pieces[square1]);
                    containor.setComponentZOrder(pieces[square1], 0);
                    pieces[square1].addMouseListener(Board.this);
                    moves.set(moves.size() - 1, moves.get(moves.size() - 1) + "=" + pieces[square1].toString());
                    movePiece(pieces[square1], square1, square2);
                    moves.remove(moves.size() - 1);
                    controlPanel.updateBoardState();
                    containor.validate();
                    containor.repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }

            });
            if (isWhite) {
                i++;
            } else {
                i--;
            }
        }
        containor.setComponentZOrder(promotionPanel, 0);
        containor.validate();
        containor.repaint();
    }

    public void movePiece(Piece selectedPiece, int sqr1, int square) {
        highLightLastMove(sqr1, square);
        moves.add(moveToString(selectedPiece, sqr1, square));
        if (selectedPiece instanceof Pawn) {
            if (((sqr1 > 7 && sqr1 < 16) && (((Pawn) selectedPiece).isWhite)) || ((sqr1 > 47 && sqr1 < 56) && !(((Pawn) selectedPiece).isWhite))) {
                promotePawn((Pawn) selectedPiece, sqr1, square);
                return;
            } else if (((Pawn) selectedPiece).firstMove) {
                ((Pawn) selectedPiece).firstMove = false;
                if (square == sqr1 - 16 || square == sqr1 + 16) {
                    ((Pawn) selectedPiece).isEnPassan = true;
                    if (pieces[Pawn.enPassant] instanceof Pawn)
                        ((Pawn) pieces[Pawn.enPassant]).isEnPassan = false;
                    Pawn.enPassant = square;
                }
                moves.remove(moves.size() - 1);
                movePiece(selectedPiece, sqr1, square);
                return;
            } else if ((square - sqr1) % 8 != 0 && pieces[square] == null) {
                if (selectedPiece.isWhite) {
                    containor.remove(pieces[square + 8]);
                    pieces[square + 8] = null;
                } else {
                    containor.remove(pieces[square - 8]);
                    pieces[square - 8] = null;
                }
            }
        }

        if (pieces[Pawn.enPassant] instanceof Pawn) {
            ((Pawn) pieces[Pawn.enPassant]).isEnPassan = false;
            Pawn.enPassant = 0;
        }

        if (selectedPiece instanceof King) {
            if (square == sqr1 + 2) {
                pieces[square - 1] = pieces[sqr1 + 3];
                pieces[sqr1 + 3] = null;
                pieces[square - 1].setSquare(square - 1);
            }
            if (square == sqr1 - 2) {
                pieces[square + 1] = pieces[sqr1 - 4];
                pieces[sqr1 - 4] = null;
                pieces[square + 1].setSquare(square + 1);
            }
        }


        if (pieces[square] != null) {
            containor.remove(pieces[square]);
            pieces[square] = selectedPiece;
            containor.repaint();
        } else {
            pieces[square] = selectedPiece;
        }

        pieces[sqr1] = null;
        pieces[square].setSquare(square);
        if (isWhiteTurn) {
            isWhiteTurn = false;

            if (isSquareInCheck(King.bKSquare, pieces)) {
                ((King) pieces[King.bKSquare]).inCheck = true;
                isCheckmate(King.bKSquare, pieces);
                if (isCheckmate(King.bKSquare, pieces))
                    moves.set(moves.size() - 1, moves.get(moves.size() - 1) + "#");
                else
                    moves.set(moves.size() - 1, moves.get(moves.size() - 1) + "+");
            } else if (((King) pieces[King.bKSquare]).inCheck && !isSquareInCheck(King.bKSquare, pieces))
                ((King) pieces[King.bKSquare]).inCheck = false;
            isDraw(King.bKSquare, pieces);

        } else {
            isWhiteTurn = true;

            if (isSquareInCheck(King.wKSquare, pieces)) {
                ((King) pieces[King.wKSquare]).inCheck = true;
                isCheckmate(King.wKSquare, pieces);
                if (isCheckmate(King.wKSquare, pieces))
                    moves.set(moves.size() - 1, moves.get(moves.size() - 1) + "#");
                else
                    moves.set(moves.size() - 1, moves.get(moves.size() - 1) + "+");
            } else if (((King) pieces[King.wKSquare]).inCheck && !isSquareInCheck(King.wKSquare, pieces))
                ((King) pieces[King.wKSquare]).inCheck = false;
            isDraw(King.wKSquare, pieces);
        }

        controlPanel.updateBoardState();
        hideHints();
        containor.validate();
        containor.repaint();
        engine.updateMoves(squareToString(sqr1) + squareToString(square));
        if (!isWhiteTurn)
            engine.makeMove();
    }

    private void highLightLastMove(int sqr1, int sqr2) {
        square[lastMove[0]].setBackground(lastMove[2] == 0 ? firstColor : secondColor);
        square[lastMove[1]].setBackground(lastMove[3] == 0 ? firstColor : secondColor);
        lastMove[0] = sqr1;
        lastMove[2] = square[sqr1].getBackground() == firstColor ? 0 : 1;
        lastMove[1] = sqr2;
        lastMove[3] = square[sqr2].getBackground() == firstColor ? 0 : 1;

        square[sqr1].setBackground(new Color(
                (firstColor.getRed() + secondColor.getRed()) / 2,
                (firstColor.getGreen() + secondColor.getGreen()) / 2 + ((255 - (firstColor.getBlue() + secondColor.getBlue()) / 2) / 5),
                ((firstColor.getBlue() + secondColor.getBlue()) / 2) + ((255 - (firstColor.getBlue() + secondColor.getBlue()) / 2) / 5)));
        square[sqr2].setBackground(new Color(
                (firstColor.getRed() + secondColor.getRed()) / 2,
                (firstColor.getGreen() + secondColor.getGreen()) / 2,
                (firstColor.getBlue() + secondColor.getBlue()) / 2));
    }

    public String getPGN() {
        String pgn = "";
        for (int i = 0; i < moves.size(); i++)
            pgn += ((i % 2 == 0 ? i / 2 + 1 + "." : "") + moves.get(i) + " ");
        return pgn;
    }

    @Override
    public void mousePressed(MouseEvent e) {

        for (int i = 0; i < 64; i++) {
            if (e.getSource() == pieces[i]) {
                containor.setComponentZOrder(pieces[i], 0);
                if (selectedPiece != null && selectedPiece != pieces[i]) {
                    if (isValidMove(selectedPiece.square, i)) {
                        movePiece(selectedPiece, selectedPiece.square, i);
                        selectedPiece = null;
                    } else {
                        if (pieces[i] != null) {
                            selectedPiece = pieces[i];

                            firstTouch = true;
                            showHints(i);
                        }
                    }
                } else if (selectedPiece != null && selectedPiece == pieces[i]) {
                    firstTouch = false;
                } else {
                    selectedPiece = pieces[i];

                    firstTouch = true;
                    showHints(i);
                }

            } else if (e.getSource() == square[i]) {
                if (selectedPiece != null && pieces[i] == null) {
                    if (isValidMove(selectedPiece.square, i)) {
                        movePiece(selectedPiece, selectedPiece.square, i);
                        selectedPiece = null;
                    } else {
                        hideHints();
                    }
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (int i = 0; i < 64; i++) {
            if (e.getSource() == pieces[i]) {
                if (selectedPiece != null) {

                    if (isValidMove(i, selectedPiece.square)) {
                        movePiece(selectedPiece, i, selectedPiece.square);
                        selectedPiece = null;
                    } else {
                        // System.out.println("not valid");
                        if (selectedPiece != null && selectedPiece.square == i && !firstTouch) {
                            selectedPiece = null;
                            hideHints();
                        } else if (selectedPiece != null && selectedPiece.square == i && firstTouch) {
                            firstTouch = false;
                        }
                        pieces[i].setSquare(i);
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
