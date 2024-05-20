import TicTacToe.Game;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(3);
        String winnerEmail = game.startGame();
        System.out.println("Winner: " + winnerEmail);
    }
}