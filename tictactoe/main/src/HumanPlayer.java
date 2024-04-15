import java.util.Scanner;

public class HumanPlayer implements Player {
    private final char symbol;
    private final Scanner scanner;

    public HumanPlayer(char symbol) {
        this.symbol = symbol;
        scanner = new Scanner(System.in);
    }

    @Override
    public int[] getMove(Board board) {
        System.out.print("Enter row and column: ");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        return new int[] {row, col};
    }

    @Override
    public char getSymbol() {
        return symbol;
    }
}
