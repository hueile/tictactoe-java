import java.util.ArrayList;
import java.util.List;

public class Node {
    private final Board board;
    private final Node parent;
    private final List<Node> children;
    private int visits;
    private double score;
    private final int moveRow;
    private final int moveCol;
    private final char playerSymbol;

    public Node(Board board, Node parent, int moveRow, int moveCol, char playerSymbol) {
        this.board = new Board(board);
        this.parent = parent;
        this.children = new ArrayList<>();
        this.visits = 0;
        this.score = 0.0;
        this.moveRow = moveRow;
        this.moveCol = moveCol;
        this.playerSymbol = playerSymbol;

        // If moveRow and moveCol are valid, apply the move to this node's board
        if (moveRow >= 0 && moveCol >= 0) {
            this.board.makeMove(moveRow, moveCol, playerSymbol);
        }
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public void update(double result) {
        visits++;
        score += result;
    }

    public int getVisits() {
        return visits;
    }

    public double getScore() {
        return score;
    }

    public Board getBoard() {
        return board;
    }

    public Node getParent() {
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public int getMoveRow() {
        return moveRow;
    }

    public int getMoveCol() {
        return moveCol;
    }

    public char getPlayerSymbol() {
        return playerSymbol;
    }
}
