package edu.bu.cs342.p02;

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.bu.cs342.utilities.LinkedListStack;

/**
 * @author: Michael Donnelly
 */
public class Chessboard {

    private LinkedListStack<Queen> queenStack;
    private char startingFile;
    private int[] positionMap;
    

    /**
     * Generates the list of acceptable moves for a chessboard.
     */
    class PositionGenerator implements Iterator<Position>, Iterable<Position> {
        private LinkedListStack<Queen> currentPlacement;
        private char file;
        private Position[] available;
        private int count;
        private int next;

        /**
         * Determines the available placements in file, based on currentPlacement
         * @param file char the file in which to place the queen.
         * @param currentPlacement LinkedListStack<Queen> the currently placed queens.
         */
        public PositionGenerator(char file, LinkedListStack<Queen> currentPlacement) {
            this.file = file;
            this.currentPlacement = currentPlacement;
            this.available = new Position[8]; // cache the available positions for the iterator.
            this.count = 0;
            this.next = 0;
            for (int i = 0; i < 8; ++i) { // Check each position in the file to see if any queen blocks it.
                Position testPosition = new Position(i + 1, this.file);
                boolean unblocked = true;
                for (Queen q : this.currentPlacement) {
                    if (q.blocks(testPosition)) {
                        unblocked = false;
                        break;
                    }
                }
                if (unblocked) { // No queen is blocking this position, so add it to the cache.
                    this.available[this.count++] = testPosition;
                }
            }
        }
        
        @Override
        public Iterator<Position> iterator() {
            return this;
        }

        @Override
        public boolean hasNext() {
            return (this.count > this.next);
        }

        @Override
        public Position next() {
            if (this.hasNext()) {
                return this.available[this.next++];
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * Create a chessboard with a queen at the initial position.
     * 
     * @param initial Queen the initial Queen around which to place the remaining queens.
     */
    public Chessboard(Queen initial) {
        this.positionMap = new int[8];
        this.queenStack = new LinkedListStack<Queen>();
        this.addQueen(initial);
        this.startingFile = initial.position.file;
    }

    /**
     * Find the next file, rotating around the files on a chessboard.
     * @param currentFile int the index of the current file.
     * @return char the next file.
     */
    private char getNextFile(char currentFile) {
        int checkFile = Position.fileToIndex(currentFile);
        if (checkFile > 8 || checkFile < 1) {
            throw new IllegalPlacementException();
        }
        int result = checkFile + 1;
        if (result == 9) {
            result = 1;
        }
        return Position.indexToFile(result);
    }

    public boolean solve() {
      return this.solve(this.getNextFile(this.startingFile));
    }
    
    /**
     * Solve the board beginning at the provided file.
     * @param file int the index of the current file
     * @return boolean whether or not the board was solved.
     */
    private boolean solve(char file) {
        if (file != this.startingFile) {
            for (Position p : this.getAvailablePositions(file)) {
                this.addQueen(new Queen(p));
                if (this.solve(this.getNextFile(file))) {
                    break;
                } else {
                    this.removeQueen();
                }
            }
        }
        return this.isSolved();
    }

    /**
     * Place a queen on the board.
     * @param q Queen the queen to place.
     */
    public void addQueen(Queen q) {
        if (this.positionMap[q.position.fileIndex - 1] == 0) {
            this.positionMap[q.position.fileIndex - 1] = q.position.rank;
            this.queenStack.push(q);
        } else {
            throw new IllegalPlacementException();
        }
    }

    /**
     * Remove a queen from the board.
     * @return Queen the removed queen.
     */
    public Queen removeQueen() {
        Queen result = this.queenStack.pop();
        if (null != result) {
            this.positionMap[result.position.fileIndex - 1] = 0;
        }
        return result;
    }

    /**
     * Indicates whether or not the board is in a solved state.
     * @return boolean true if the board is in a solved state.
     */
    public boolean isSolved() {
        for (int i = 0; i < this.positionMap.length; ++i) {
            if (0 == this.positionMap[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns an Iterable Iterator of Position representing all valid
     * placements in {@code file} at call time.
     * 
     * @param file int the file in which to calculate postitions.
     * @return PositionGenerator a pre-calculated iterable iterator of valid positions.
     */
    public PositionGenerator getAvailablePositions(char file) {
        return new PositionGenerator(file, this.queenStack);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        final String header = "  a b c d e f g h\n"; 
        sb.append(header);
        for (int i = this.positionMap.length; 0 < i; --i) {
            sb.append(i);
            sb.append("|");
            for (int j = 1; j <= 8; ++j) {
                if (this.positionMap[i - 1] == j) {
                    sb.append("Q");
                } else {
                    sb.append(" ");
                }
                sb.append("|");
            }
            sb.append(i);
            sb.append("\n");
        }
        sb.append(header);
        return sb.toString();
    }
}
