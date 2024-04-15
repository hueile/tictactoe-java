import java.util.ArrayList;
import java.util.List;

public class Board {
    private final char[][] board;

    public Board() {
        board = new char[3][3];
        initializeBoard();
    }

    public Board(Board other) {
        this.board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(other.board[i], 0, this.board[i], 0, other.board[i].length);
        }
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            System.out.println();
            for (int j = 0; j < 3; j++) {
                if (j == 0) {
                    System.out.print("| ");
                }
                System.out.print(board[i][j] + " | ");
            }
        }
        System.out.println();
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean makeMove(int row, int col, char player) {
        if (board[row][col] == '-') {
            board[row][col] = player;
            return true;
        } else {
            return false;
        }
    }

    public List<int[]> getAvailableMoves() {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    moves.add(new int[]{i, j});
                }
            }
        }
        return moves;
    }

    public char[][] getBoard() {
        return board;
    }

}
