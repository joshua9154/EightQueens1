package com.company;


public class Queen {
    public int boardDiameter = 8;
    public int[][] queens = new int[boardDiameter][2];
    public int[][] board = new int[boardDiameter][boardDiameter];


    public Queen() {
        resetBoard();
        int queen = 0;
        int horizontal = 0;
        int vertical = 0;
        while (queen < boardDiameter) {
               /*
               If the spot is open the queen will be placed
               and the next queen will be made and set to 0,0
               the preliminaryCheck is for optimization.
                */
            if (preliminaryCheck(vertical, horizontal, queen)) {

                boolean placed = placeQueen(vertical, horizontal, queen + 1);
                if (placed == true) {
                    queens[queen][0] = vertical;
                    queens[queen][1] = horizontal;
                    queen++;
                    horizontal = 0;
                    vertical = 0;
                }
            }
               /*
               If the queen has not been placed, then it is checked if that queen
               has made it to the end of th board. If so then the previous queen
               is removed using the timeMachine method and is placed at the next available slot.
                */
            if (vertical >= boardDiameter - 1 && horizontal >= boardDiameter - 1) {
                timeMachine(queen);
                vertical = queens[queen - 1][0];
                horizontal = queens[queen - 1][1];
                queen--;
            }
                  /*
                  The queen is incremented to move along the board.
                   */
            if (vertical < boardDiameter - 1 && horizontal <= boardDiameter - 1) {
                vertical++;
            } else if (vertical >= boardDiameter - 1 && horizontal <= boardDiameter - 1) {
                vertical = 0;
                horizontal++;
            }

        }
    }


    public boolean placeQueen(int vertical, int horizontal, int numQueen) {
        /*
        When a Queen is placed all the spots she can move in
        one turn are marked with 1's. When a new Queen wants to be placed
        then she needs to find a place that still has a 0 in it, or she needs
        to come back with a new designation.
         */
        if (board[vertical][horizontal] != 0)
            return false;
        else {
            board[vertical][horizontal] = numQueen + 1;
            //mark row
            for (int column = 0; column < boardDiameter; column++) {
                if (board[column][horizontal] == 0)
                    board[column][horizontal] = 1;
            }
            //mark columns
            for (int row = 0; row < boardDiameter; row++) {
                if (board[vertical][row] == 0)
                    board[vertical][row] = 1;
            }
            //mark diagonals
            markDiagonals(horizontal, vertical, 1, 1);
            markDiagonals(horizontal, vertical, -1, 1);
            markDiagonals(horizontal, vertical, 1, -1);
            markDiagonals(horizontal, vertical, -1, -1);
        }
        return true;
    }

    public boolean markDiagonals(int horizontal, int vertical, int one, int two) {
        int currentVertical = vertical;
        int currentHorizontal = horizontal;
        /*
        Checks if the queen is on a edge and only has two diagonals instead of 4
         */
        if (horizontal == boardDiameter - 1 && two > 0)
            return false;
        if (horizontal == 0 && two < 0)
            return false;
        if (vertical == boardDiameter - 1 && one > 0)
            return false;
        if (vertical == 0 && one < 0)
            return false;
        /*
        Marks all the diagonal paths of the queen with 1's while
        still keeping on the board.
         */
        do {
            currentVertical = currentVertical + one;
            currentHorizontal = currentHorizontal + two;
            if (board[currentVertical][currentHorizontal] == 0)
                board[currentVertical][currentHorizontal] = 1;
        }
        while (currentVertical > 0 && currentVertical < boardDiameter - 1 && currentHorizontal > 0 && currentHorizontal < boardDiameter - 1);
        return true;
    }

    public void resetBoard() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = 0;
            }
        }
    }

    public boolean preliminaryCheck(int vertical, int horizontal, int numQueen) {
        /*
        Checks to see if another Queen shares a row or a column before the
        placeQueen does further checking.
         */
        for (int i = 0; i < numQueen; i++) {
            if (queens[numQueen][0] == vertical || queens[numQueen][1] == horizontal)
                return false;
        }
        return true;
    }

    public void timeMachine(int numQueen) {
        /*
        Resets the board and places the all the previous queens
        back besides the last one.
         */
        resetBoard();
        for (int i = 0; i < numQueen - 1; i++) {
            placeQueen(queens[i][0], queens[i][1], i + 1);
        }

    }

    public void printBoard() {

        /*
        Anything that isn't a 0 or 1 is a queen.
         */
        for (int row = 0; row < board.length; row++) {
            System.out.println();
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] >= 2) System.out.print("|x|");
                else
                    System.out.print("|0|");
            }
        }


    }
}

