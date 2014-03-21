package edu.bu.cs342.p02;

public class Position {

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
            return (this.file == test.file && this.rank == test.rank);
        } catch (ClassCastException ex) {
            return false;
        }
    }

    public final int file;
    public final int rank;

    public Position(int rank, int file) {
        if (1 > rank || 1 > file || 8 < rank || 8 < file) {
            throw new IllegalArgumentException("Chessboard is 8x8");
        }
        this.file = file;
        this.rank = rank;
    }

    public Position absoluteDistanceFrom(Position test) {
        return new Position(Math.abs(this.rank - test.rank), Math.abs(this.file - test.file));
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this.rank, this.file);
    }
}
