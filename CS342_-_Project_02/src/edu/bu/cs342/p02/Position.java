package edu.bu.cs342.p02;

public class Position {

    private static final byte ASCII_FILE_BASE = (byte)'`';

    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (this == o) {
            return true;
        }
        try {
            Position test = (Position) o;
            return (this.fileIndex == test.fileIndex && this.rankIndex == test.rankIndex);
        } catch (ClassCastException ex) {
            return false;
        }
    }

    public final char file;
    public final int rank;
    public final int fileIndex;
    public final int rankIndex;

    public Position(int rank, char file) {
        file = Position.indexToFile(Position.fileToIndex(file));
        rank = Position.rankToIndex(Position.rankToIndex(rank));
        this.file = file;
        this.rank = rank;
        this.fileIndex = Position.fileToIndex(file);
        this.rankIndex = Position.rankToIndex(rank);
    }

    public Position absoluteDistanceFrom(Position test) {
        return new Position(Math.abs(this.rankIndex - test.rankIndex),
              Position.indexToFile(Math.abs(this.fileIndex - test.fileIndex)));
    }

    /**
     * Convert a rank (anchored bottom-left on the chessboard) to an index
     * (anchored top-left).
     * 
     * @param rank int the rank of the position.
     * @return int the index of the position.
     */
    public static int rankToIndex(int rank) {
      if (rank < 1 || rank > 8) {
        throw new IllegalArgumentException("Rank must be between 1 and 8");
      }
      return 9 - rank;
    }

    /**
     * Convert a file (A-H) to an index (1-8)
     * @param file char the file of the position.
     * @return int the index of the position.
     */
    public static int fileToIndex(char file) {
      char input = Character.toLowerCase(file);
      int value = input - Position.ASCII_FILE_BASE;
      if (value < 1 || value > 8) {
        throw new IllegalArgumentException("File must be between A and H");
      }
      return value;
    }
    
    /**
     * Convert an index (1-8) to a file (A-H)
     * @param fileIndex in the index of the position.
     * @return char the file of the position.
     */
    public static char indexToFile(int fileIndex) {
      if (fileIndex < 1 || fileIndex > 8) {
        throw new IllegalArgumentException("File must be between a and h");
      }
      return (char)(fileIndex + Position.ASCII_FILE_BASE);
    }

    @Override
    public String toString() {
        return String.format("%c%d", this.file, this.rank);
    }
}
