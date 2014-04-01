package edu.bu.cs342.p02;

/**
 * Indicates an illegal move on a chess board.
 * 
 * @author Michael Donnelly
 */
public class IllegalPlacementException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public IllegalPlacementException() {
        super();
    }
}
