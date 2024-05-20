package TicTacToe;

import TicTacToe.Marker.Marker;

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
