package edu.bu.cs342.p02;

/**
 * Representation of a Queen on a chess board.
 * 
 * @author Michael Donnelly
 * 
 */
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

    /**
     * Create a queen at the given position.
     * 
     * @param position
     *            Position a valid board placement.
     */
    public Queen(Position position) {
        this.position = position;
    }

    /**
     * Checks whether this queen blocks (has an attack vector to) a piece at
     * another position.
     * 
     * @param position
     *            Position the position to test.
     * @return boolean whether or not {@code position} is vulnerable to this
     *         Queen.
     */
    public boolean blocks(Position position) {
        if (this.position.rankIndex == position.rankIndex
                || this.position.fileIndex == position.fileIndex) {
            return true;
        }
        Position distance = this.position.absoluteDistanceFrom(position);
        return (distance.rank == distance.fileIndex);
    }

    @Override
    public String toString() {
        return String.format("Q%s", this.position.toString());
    }
}
