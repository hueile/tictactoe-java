// This AI player uses the Monte Carlo Tree Search (MCTS).

public class AIPlayer2 implements Player {
    private final char symbol;

    public AIPlayer2(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public int[] getMove(Board board) {
        MCTS mcts = new MCTS(new Board(board), symbol);
        return mcts.getMove();
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
