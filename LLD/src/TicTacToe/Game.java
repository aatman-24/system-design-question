package TicTacToe;

import TicTacToe.Marker.Marker;
import TicTacToe.Marker.MarkerO;
import TicTacToe.Marker.MarkerX;
import TicTacToe.Player.Player;

import java.util.*;

public class Game {

    int size;
    Marker[][] boards;
    Deque<Player> players;
    Scanner scanner;

    public Game(int size) {
        this.size = size;
        scanner = new Scanner(System.in);
        initializeGame();
    }

    public void initializeGame() {
        boards = new Marker[this.size][this.size];
        players = new LinkedList<>();

        Player p1 = new Player(new MarkerO(), "aatman@gmail.com");
        Player p2 = new Player(new MarkerX(), "mitul@gmail.com");

        players.add(p1);
        players.add(p2);
    }

    public String startGame() {

//        each time this set of actions happens:
//        - one of the player put their marker on board.
//        - we need to check whether that person won from that move, move is valid(no, again we give chance to that person)
//        - put the player after the next one so again it can make entry...
//

        boolean isTie = false;

        while(!isTie) {

            if(isGameTie(boards)) {
                isTie = true;
            }

            printBoard(boards);

            Player p = players.removeFirst();
            System.out.println("Your turn: " + p.getEmail());
            System.out.println("Please Enter your position(x, y): ");
            String inputLine = scanner.nextLine();

            // Split the input line into parts
            String[] parts = inputLine.split(" ");

            // Parse the parts as integers
            int xPos = Integer.parseInt(parts[0]);
            int yPos = Integer.parseInt(parts[1]);

            if(!isValidMoveAndMarkPosition(xPos, yPos, p, boards)) {
                System.out.println("Invalid move");
                players.addFirst(p);
                continue;
            }

            if(isWinner(xPos, yPos, p, boards)) {
                printBoard(boards);
                return p.getEmail();
            }

            // otherwise add this player at last.
            players.addLast(p);
        }

        return "tie";
    }

    private void printBoard(Marker[][] boards) {

        for(int i = 0; i < boards.length; i++) {

            for(int j = 0; j < boards.length; j++) {

                    if(j == 0) {
                        if(boards[i][j] != null) {
                            System.out.print(boards[i][j].getSign());
                        }
                        else {
                            System.out.print(" ");
                        }

                    }
                    else {
                        if(boards[i][j] != null) {
                            System.out.print(" | " + boards[i][j].getSign());
                        }
                        else {
                            System.out.print(" | " + " ");
                        }

                    }
            }

            System.out.println();

        }

    }

    private boolean isWinner(int curX, int curY, Player p, Marker[][] boards) {

        boolean rowMatch = true;
        boolean colMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;

        // check for row
        for(int col = 0; col < boards.length; col++) {
            if (boards[curX][col] != p.getMarker()) {
                rowMatch = false;
            }
        }

        // check for column
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

    private boolean isValidMoveAndMarkPosition(int xPos, int yPos, Player p, Marker[][] boards) {

        if(boards[xPos][yPos] != null) {
            return false;
        }

        // otherwise set the position
        boards[xPos][yPos] = p.getMarker();

        return true;
    }

    // If any position is not set, it means game is not tied
    private boolean isGameTie(Marker[][] boards) {
        for(int i = 0; i < boards.length; i++) {
            for(int j = 0; j < boards.length; j++) {
                if(boards[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

}
