package TicTacToe;

import TicTacToe.Marker.Marker;
import TicTacToe.Marker.MarkerEnum;
import TicTacToe.Marker.MarkerFactory;
import TicTacToe.Player.Player;

import java.util.*;

public class Game {

    int size;
    Board board;
    Deque<Player> players;
    Scanner scanner;

    public Game(int size) {
        this.size = size;
        this.board = new Board(size);
        scanner = new Scanner(System.in);
        initializeGame();
    }

    public void initializeGame() {
        players = new LinkedList<>();
        Player p1 = new Player(MarkerFactory.getMarker(MarkerEnum.O), "aatman@gmail.com");
        Player p2 = new Player(MarkerFactory.getMarker(MarkerEnum.X), "mitul@gmail.com");
        players.add(p1);
        players.add(p2);
    }

    public String startGame() {

        boolean noWinner = true;

        while(noWinner) {

            if(board.isBoardFull()) {
                noWinner = false;
            }

            board.printBoard();

            Player curPlayer = players.removeFirst();
            System.out.println("Your turn: " + curPlayer.getEmail());
            System.out.println("Please Enter your position(x, y): ");
            String inputLine = scanner.nextLine();

            // Split the input line into parts
            String[] parts = inputLine.split(" ");

            // Parse the parts as integers
            int xPos = Integer.parseInt(parts[0]);
            int yPos = Integer.parseInt(parts[1]);

            if(!board.markPosition(xPos, yPos, curPlayer.getMarker())) {
                // If move is not valid, again we give a chance to curPlayer
                System.out.println("Invalid move");
                players.addFirst(curPlayer);
                continue;
            }

            if(isCurrenPlayerWinner(xPos, yPos, curPlayer, board)) {
                board.printBoard();
                return curPlayer.getEmail();
            }

            // Add curPlayer at last.
            players.addLast(curPlayer);
        }

        return "tie";
    }

    private boolean isCurrenPlayerWinner(int curX, int curY, Player p, Board board) {

        Marker[][] boards = board.board;

        boolean rowMatch = true;
        boolean colMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;

        // check for row (We need to check curX row only)
        for(int col = 0; col < boards.length; col++) {
            if (boards[curX][col] != p.getMarker()) {
                rowMatch = false;
            }
        }

        // check for column ((We need to check curY col only)
        for(int row = 0; row < boards.length; row++) {
            if (boards[row][curY] != p.getMarker()) {
                colMatch = false;
            }
        }

        // check diagonal
        for(int i = 0; i < boards.length; i++) {
            if(boards[i][i] != p.getMarker()) {
                diagonalMatch = false;
            }
        }

        // check anti diagonal
        int N = boards.length - 1;
        for(int i = 0; i < boards.length; i++) {
            if(boards[N-i][i] != p.getMarker()) {
                antiDiagonalMatch = false;
            }
        }

        return (rowMatch || colMatch || antiDiagonalMatch || diagonalMatch);
    }

}
