### Design TicTacToe  

---
### Phase-1: First, think about the associated objects and its role.

Player -> Who plays the game. 
Board -> One board on which players play the game.  
Marker -> Each player has one marker during a game.
Game -> Consists of everything, and the players play the game using the marker. 

---
### Phase-2: Think about the property and association of objects, design pattern you can think of. 

Marker:

> Marker can be 0, X, $, #... anything. Players can choose their marker. So we need one Enum which stores MarkerType. I guess
we need one Marker class as well, which contains this MarkerType. After that, we can have two markers as of now for Two players, which 
we again need to create. 

    public enum MarkerEnum {
        X,
        O,
        $
    }

    public class Marker {
    
        MarkerEnum sign;
    
        public Marker(MarkerEnum sign) {
            this.sign = sign;
        }
    
        public void setSign(MarkerEnum sign) {
            this.sign = sign;
        }
    
        public MarkerEnum getSign() {
            return this.sign;
        }
    }

    public class MarkerO extends Marker{
    
        public MarkerO() {
            super(MarkerEnum.O);
        }
    }

    public class MarkerX extends Marker{
    
        public MarkerX() {
            super(MarkerEnum.X);
        }
    }

Player:
    
> Player is a person who can have one unique id(I choose the email), and player can have its marker, so he can play the game. 

    public class Player {
    
        String email;
        Marker marker;
    
        public Player(Marker marker, String email) {
            this.marker = marker;
            this.email = email;
        }
    
        public String getEmail() {
            return email;
        }
    
        public void setEmail(String email) {
            this.email = email;
        }
    
        public Marker getMarker() {
            return marker;
        }
    
        public void setMarker(Marker marker) {
            this.marker = marker;
        }
    }

Board:
    
> We ask user the size of board. 
> Another thing is that the board consists of multiple markers of both players. We need some methods that are used during Game markPosition(), isBoardFull(), printBoard().
> Even I figured out methods during the optimization phase. 

>> Determine the responsibilities of objects carefully and ensure that each object is responsible for a specific aspect of the functionality. Based on these responsibilities, decide where to implement each method to maintain clarity, maintainability, and modularity in your code. Which is Single Responsibility Principle.  

    public class Board {
    
        int size;
        Marker[][] board;
        int totalMarkedPosition;
    
        public Board(int size) {
            this.size = size;
            board = new Marker[this.size][this.size];
            totalMarkedPosition = 0;
        }
    
        public boolean markPosition(int x, int y, Marker marker) {
            
            // if the position is already marked, we can't mark it again. 
            if(board[x][y] != null) {
                return false;
            }
            
            // mark position. 
            board[x][y] = marker;
            totalMarkedPosition++;
            return true;
        }
    
        public boolean isBoardFull() {
            // check, all positions are marked. 
            return totalMarkedPosition == (size * size);
        }
    
        public void printBoard() {
    
            for(int i = 0; i < board.length; i++) {
    
                for(int j = 0; j < board.length; j++) {
    
                    if(j == 0) {
                        if(board[i][j] != null) {
                            System.out.print(board[i][j].getSign());
                        }
                        else {
                            System.out.print(" ");
                        }
    
                    }
                    else {
                        if(board[i][j] != null) {
                            System.out.print(" | " + board[i][j].getSign());
                        }
                        else {
                            System.out.print(" | " + " ");
                        }
    
                    }
                }
                System.out.println();
            }
        }
    }

---

### Phase-3: Integrate each piece together.

Game:

> We first need to initialize the game with board and players. Ask user about size of board and create a board.
> Add two players into deque. Why deque? Easy to add at front and remove from front. 
> Apply Game logic. 

Game Logic:
- Check board is full or not, if yes, Declare the game as a tie. 
- Otherwise, start with front player by pulling. Next, player makes move.
- We validate that moves and based on that decides retry is required or okay with that move. 
- If move is valid, we check with that move player win the game or not. If yes we return, else we give the next player a chance by(adding the player at the end in queue)
- If not, then we again give a try to the same person.


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
            Player p1 = new Player(new MarkerO(), "aatman@gmail.com");
            Player p2 = new Player(new MarkerX(), "mitul@gmail.com");
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






