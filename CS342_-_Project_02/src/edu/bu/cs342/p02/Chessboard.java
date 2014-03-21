package edu.bu.cs342.p02;

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.bu.cs342.utilities.LinkedListStack;

/**
 * TODO: Insert description here. (generated by dirtnap)
 */
public class Chessboard {

    private LinkedListStack<Queen> queenStack;
    private int startingFile;
    private int[] positionMap;

    class PositionGenerator implements Iterator<Position>, Iterable<Position> {
        private LinkedListStack<Queen> currentPlacement;
        private int file;
        private Position[] available;
        private int count;
        private int next;

        public PositionGenerator(int file, LinkedListStack<Queen> currentPlacement) {
            this.file = file;
            this.currentPlacement = currentPlacement;
            this.available = new Position[8];
            this.count = 0;
            this.next = 0;
            for (int i = 0; i < 8; ++i) {
                Position testPosition = new Position(i + 1, this.file);
                boolean unblocked = true;
                for (Queen q : this.currentPlacement) {
                    if (q.blocks(testPosition)) {
                        unblocked = false;
                        break;
                    }
                }
                if (unblocked) {
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
     * 
     */
    public Chessboard(Queen initial) {
        this.positionMap = new int[8];
        this.queenStack = new LinkedListStack<Queen>();
        this.addQueen(initial);
        this.startingFile = initial.position.file;
        this.solve(this.getNextFile(this.startingFile));
    }

    private int getNextFile(int currentFile) {
        if (currentFile > 8 || currentFile < 1) {
            throw new IllegalPlacementException();
        }
        int result = currentFile + 1;
        if (result == 9) {
            result = 1;
        }
        return result;
    }

    private boolean solve(int file) {
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

    public void addQueen(Queen q) {
        if (this.positionMap[q.position.file - 1] == 0) {
            this.positionMap[q.position.file - 1] = q.position.rank;
            this.queenStack.push(q);
        } else {
            throw new IllegalPlacementException();
        }
    }

    public Queen removeQueen() {
        Queen result = this.queenStack.pop();
        if (null != result) {
            this.positionMap[result.position.file - 1] = 0;
        }
        return result;
    }

    public boolean isSolved() {
        for (int i = 0; i < this.positionMap.length; ++i) {
            if (0 == this.positionMap[i]) {
                return false;
            }
        }
        return true;
    }

    public PositionGenerator getAvailablePositions(int file) {
        return new PositionGenerator(file, this.queenStack);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.positionMap.length; ++i) {
            sb.append("|");
            for (int j = 1; j <= 8; ++j) {
                if (this.positionMap[i] == j) {
                    sb.append("Q");
                } else {
                    sb.append(" ");
                }
                sb.append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    public static void main(String[] args) {
      int count = 0;
      for (int i = 1; i < 9; ++i) {
        for (int j = 1; j < 9; ++j) {
          Chessboard board = new Chessboard(new Queen(new Position(i, j)));
          if (board.isSolved()) {
            ++count;
          }
        }
      }
      System.out.println(count);
    }
    
}
