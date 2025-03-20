package chess;

import javax.swing.plaf.IconUIResource;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Engine {
    ChessBot bot;
    String moves = "";
    Board board;

    public Engine(Board board) {
        bot = new ChessBot();
        if (bot.startEngine()) {
            sendCommand("uci");
            // System.out.println(getOutput());
            // bot.stopEngine();
        }
        this.board = board;
    }

    public void makeMove() {
        String move;
        sendCommand("position startpos moves" + moves);
        sendCommand("go movetime 100");
        move = getOutput().split("bestmove ")[1].split(" ")[0];
        // System.out.println(move);
        int from = board.stringToSquare(move.substring(0, 2));
        int to = board.stringToSquare(move.substring(2, 4));
        board.movePiece(board.pieces[from], from, to);
    }

    private String getOutput() {
        try {
            return bot.getOutput();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendCommand(String s) {
        // System.out.println(s);
        try {
            bot.sendCommand(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateMoves(String s) {
        moves += " " + s;
    }

    public static class ChessBot {
        private Process stockfish;
        private BufferedReader reader;
        private OutputStreamWriter writer;

        public boolean startEngine() {
            try {
                stockfish = new ProcessBuilder("engines\\stockfish\\stockfish-windows-x86-64-avx2.exe").start();
                reader = new BufferedReader(new InputStreamReader(stockfish.getInputStream()));
                writer = new OutputStreamWriter(stockfish.getOutputStream());
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        public void sendCommand(String command) throws IOException {
            writer.write(command + "\n");
            writer.flush();
        }

        public String getOutput() throws IOException {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                if (line.equals("uciok") || line.equals("readyok") || line.contains("bestmove")) {
                    break;
                }
            }
            // System.out.println(output.toString());
            return output.toString();
        }

        public void stopEngine() {
            try {
                sendCommand("quit");
                stockfish.destroy();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


