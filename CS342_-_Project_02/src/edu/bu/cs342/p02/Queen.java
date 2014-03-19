package edu.bu.cs342.p02;

public class Queen {

    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (this == o) {
            return true;
        }
        try {
            Queen test = (Queen) o;
            return this.position.equals(test.position);
        } catch (ClassCastException ex) {
            return false;
        }
    }

    public final Position position;

    public Queen(Position position) {
        this.position = position;
    }

    public boolean blocks(Position position) {
        if (this.position.rank == position.rank || this.position.file == position.file) {
            return true;
        }
        Position distance = this.position.absoluteDistanceFrom(position);
        return (distance.rank == distance.file);
    }

    @Override
    public String toString() {
        return this.position.toString();
    }
}
