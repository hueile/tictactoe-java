import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MCTS {
    private Node root;
    private static final int SIMULATIONS = 1000;
    private final char playerSymbol;
    private final char opponentSymbol;
    private final Random random = new Random();

    public MCTS(Board initialState, char playerSymbol) {
        this.playerSymbol = playerSymbol;
        this.opponentSymbol = (playerSymbol == 'X') ? 'O' : 'X';
        this.root = new Node(initialState, null, -1, -1, this.playerSymbol);
        System.out.println("MCTS initialized with playerSymbol: " + playerSymbol);
    }

    private Node selectPromisingNode(Node rootNode) {
        Node node = rootNode;
        while (!node.getChildren().isEmpty()) {
            node = findBestNodeWithUCB1(node);
        }
        return node;
    }

    private void expandNode(Node node) {
        List<int[]> possibleMoves = node.getBoard().getAvailableMoves();
        for (int[] move : possibleMoves) {
            Board newBoard = new Board(node.getBoard());
            char currentPlayerSymbol = node.getPlayerSymbol() == playerSymbol ? opponentSymbol : playerSymbol;
            newBoard.makeMove(move[0], move[1], currentPlayerSymbol);
            Node childNode = new Node(newBoard, node, move[0], move[1], currentPlayerSymbol);
            node.addChild(childNode);
        }
    }

    private int simulateRandomPlayout(Node node) {
        Node tempNode = new Node(node.getBoard(), null, -1, -1, node.getPlayerSymbol());
        Board tempBoard = tempNode.getBoard();
        char currentPlayerSymbol = tempNode.getPlayerSymbol();

        while (!tempBoard.isFull() && !Game.checkForWin(tempBoard)) {
            List<int[]> availableMoves = tempBoard.getAvailableMoves();
            if (availableMoves.isEmpty()) return 0; // Game draw
            int[] move = availableMoves.get(random.nextInt(availableMoves.size()));
            tempBoard.makeMove(move[0], move[1], currentPlayerSymbol);
            currentPlayerSymbol = (currentPlayerSymbol == this.playerSymbol) ? this.opponentSymbol : this.playerSymbol;
        }
        return Game.checkForWin(tempBoard) ? (currentPlayerSymbol == this.opponentSymbol ? 1 : -1) : 0;
    }

    private void backPropagate(Node node, int playerScore) {
        Node tempNode = node;
        while (tempNode != null) {
            tempNode.update(playerScore);
            playerScore = -playerScore;
            tempNode = tempNode.getParent();
        }
    }

    private Node findBestNodeWithUCB1(Node node) {
        int parentVisit = node.getVisits();
        return node.getChildren().stream()
                .max(Comparator.comparingDouble(c -> ucb1Value(parentVisit, c.getVisits(), c.getScore())))
                .orElseThrow(IllegalStateException::new);
    }

    private double ucb1Value(int totalVisits, int nodeVisits, double nodeScore) {
        if (nodeVisits == 0) return Double.MAX_VALUE;
        return (nodeScore / nodeVisits) + Math.sqrt(2) * Math.sqrt(Math.log(totalVisits) / nodeVisits);
    }

    public int[] getMove() {
        for (int i = 0; i < SIMULATIONS; i++) {
            Node node = selectPromisingNode(root);
            if (!node.getBoard().isFull() && !Game.checkForWin(node.getBoard())) {
                expandNode(node);
            }
            int result = simulateRandomPlayout(node);
            backPropagate(node, result);
        }

        System.out.println("Move scores:");
        printMoveScores();

        Node bestMove = root.getChildren().stream()
                .max(Comparator.comparingDouble(n -> n.getScore() / (double) n.getVisits()))
                .orElseThrow(IllegalStateException::new);

        root = bestMove; // Update root to the best move
        return new int[]{bestMove.getMoveRow(), bestMove.getMoveCol()};
    }

    public void printMoveScores() {
        if (root == null || root.getChildren().isEmpty()) {
            System.out.println("No possible moves.");
            return;
        }
        for (Node move : root.getChildren()) {
            System.out.printf("Move [%d, %d]: Score = %.2f, Visits = %d%n",
                    move.getMoveRow(), move.getMoveCol(), move.getScore(), move.getVisits());
        }
    }
}
