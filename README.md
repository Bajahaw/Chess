SIMPLE CHESS GUI 
# Chess

This is a Java-based chess game implemented using Swing for the graphical user interface. The game includes all the standard rules of chess, including castling and pawn promotion. The `Board` class in [src/chess/Board.java](src/chess/Board.java) represents the chessboard and handles user interactions via mouse events. Each piece on the board is an instance of a class that extends the `Piece` class in [src/chess/Piece.java](src/chess/Piece.java). The game alternates between white and black turns, which is managed by the `isWhiteTurn` variable in the `Board` class.

The project is built using Ant, with the build configuration specified in the [build.xml](build.xml) file. Unit tests are run using JUnit, with the JUnit library located in the `lib/junit_5` directory.

This dicription is written by chatgpt and does not represent the author's idea for the project, It is just for the beauty of the page. If you have a problem or suggestion feel free to ask or use the issues section.