package edu.bu.cs342.utilities;

/**
 * Thrown when classes implementing {@link Searchable} encounter runtime errors
 * in interface methods.
 * 
 * @author Michael Donnelly
 * 
 */
public class SearchOptionException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public SearchOptionException() {
    }

    public SearchOptionException(String message) {
        super(message);
    }

    public SearchOptionException(Throwable cause) {
        super(cause);
    }

    public SearchOptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchOptionException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
