// This AI player uses the minimax algorithm with alpha-beta pruning.

public class AIPlayer implements Player {
    private final char symbol;
    private final char opponentSymbol;

    public AIPlayer(char symbol) {
        this.symbol = symbol;
        this.opponentSymbol = (symbol == 'X') ? 'O' : 'X';
    }

    @Override
    public int[] getMove(Board board) {
        int[] bestMove = new int[] {-1, -1};
        int bestValue = Integer.MIN_VALUE;

        char[][] b = board.getBoard();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (b[row][col] == '-') {
                    b[row][col] = symbol;
                    int moveValue = minimax(b, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    b[row][col] = '-';

                    if (moveValue > bestValue) {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestValue = moveValue;
                    }
                }
            }
        }
        return bestMove;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    private int minimax(char[][] board, int depth, boolean isMaximizing, int alpha, int beta) {
        int score = evaluate(board);

        // Base case: return the score if the game is over
        if (score == 1 || score == -1 || isBoardFull(board))
            return score;

        int best;
        if (isMaximizing) {
            best = Integer.MIN_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = symbol;
                        best = Math.max(best, minimax(board, depth + 1, false, alpha, beta));
                        board[i][j] = '-';
                        alpha = Math.max(alpha, best); // Update alpha

                        if (beta <= alpha) {
                            break; // Alpha-beta pruning
                        }
                    }
                }
            }
        }

        else {
            best = Integer.MAX_VALUE;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = opponentSymbol;
                        best = Math.min(best, minimax(board, depth + 1, true, alpha, beta));
                        board[i][j] = '-';
                        beta = Math.min(beta, best); // Update beta

                        if (beta <= alpha) {
                            break; // Alpha-beta pruning
                        }
                    }
                }
            }

        }
        return best;
    }

    private int evaluate(char[][] board) {
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
                if (board[row][0] == symbol) {
                    return 1;
                }
                else if (board[row][0] == opponentSymbol) {
                    return -1;
                }
            }
        }

        for (int col = 0; col < 3; col++) {
            if (board[0][col] == board[1][col] && board[1][col] == board[2][col]) {
                if (board[0][col] == symbol) {
                    return 1;
                }
                else if (board[0][col] == opponentSymbol) {
                    return -1;
                }
            }
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == symbol) {
                return 1;
            } else if (board[0][0] == opponentSymbol) {
                return -1;
            }
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == symbol) {
                return 1;
            } else if (board[0][2] == opponentSymbol) {
                return -1;
            }
        }

        return 0;
    }

    private boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }
}
