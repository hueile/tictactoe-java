public class TicTacToe {
    public static void main(String[] args) {
        Player player1 = new AIPlayer2('X');
        Player player2 = new HumanPlayer('O');
        Game game = new Game(player1, player2);
        game.startGame();
    }
}