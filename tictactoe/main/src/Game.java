/*
Name: Gia Hieu Le
Student ID: 1162560

This is a simple Tic-Tac-Toe game implemented in Java. The game can be played by two human players or by a human player
against an AI player that uses the minimax algorithm with alpha-beta pruning or MCTS to determine its moves.
 */

public class Game {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;

    public Game(Player p1, Player p2) {
        board = new Board();
        this.player1 = p1;
        this.player2 = p2;
        this.currentPlayer = player1;
    }

    public void startGame() {
        boolean gameEnded = false;
        while(!gameEnded) {
            board.printBoard();
            int[] move = currentPlayer.getMove(board);
            if (board.makeMove(move[0], move[1], currentPlayer.getSymbol())) {
                if (checkForWin(board)) {
                    board.printBoard();
                    System.out.println("Player " + currentPlayer.getSymbol() + " wins!");
                    gameEnded = true;
                } else if (board.isFull()) {
                    board.printBoard();
                    System.out.println("Draw!");
                    gameEnded = true;
                } else {
                    switchPlayer();
                }
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    public static boolean checkForWin(Board board) {
        char[][] b = board.getBoard();
        for (int i = 0; i < 3; i++) {
            if (b[i][0] == b[i][1] && b[i][1] == b[i][2] && b[i][0] != '-') {
                return true;
            }
            if (b[0][i] == b[1][i] && b[1][i] == b[2][i] && b[0][i] != '-') {
                return true;
            }
        }
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2] && b[0][0] != '-') {
            return true;
        }
        return b[0][2] == b[1][1] && b[1][1] == b[2][0] && b[0][2] != '-';
    }
}
