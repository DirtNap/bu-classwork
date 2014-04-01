package edu.bu.cs342.p02;

import edu.bu.cs342.utilities.UserInput;

/**
 * Solve the 8 Queens problem.
 */
public class EightQueens {

    private UserInput userInput;

    public EightQueens() {
        this.userInput = new UserInput(System.in, System.out);
    }

    public static void main(String[] args) {
        EightQueens self = new EightQueens();
        while (self.userInput.readBoolean("Solve an 8 Queens board", "Y", "n", true)) {
            Queen q = new Queen(self.getPosition());
            Chessboard board = new Chessboard(q);
            board.solve();
            if (board.isSolved()) {
                System.out.printf("Solution for the board with queen at:  %s%n%n%s%n%n",
                        q.position, board);
            } else {
                System.out.printf("No Solution for the board with queen at:  %s%n", q.position);
            }
        }
    }

    /**
     * Get the rank and file from the user, and return the position.
     * 
     * @return Position the position provided by the user.
     */
    private Position getPosition() {
        int rank = 0;
        char file = 'A';
        do {
            rank = this.userInput.readInteger(
                    "Enter starting rank (row) as an integer between 1 and 8", 1);
        } while (rank > 8);
        file = this.userInput.readWord(
                "Enter starting file (column) as a character between a and h", "[^A-Ha-h]+", "")
                .charAt(0);
        return new Position(rank, file);
    }

}
