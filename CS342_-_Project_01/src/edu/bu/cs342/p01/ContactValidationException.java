package edu.bu.cs342.p01;

/**
 * Thrown when an element of a contact can't be parsed.
 * 
 * @author Michael Donnelly
 * 
 */
public class ContactValidationException extends Exception {
    public static final String VALIDATION_EXCEPTION_FORMAT = "Could not validate '%s':  %s";
    private static final long serialVersionUID = 1L;

    public ContactValidationException() {
        super();
    }

    public ContactValidationException(String message) {
        super(message);
    }

    public ContactValidationException(String input, String error) {
        this(String.format(ContactValidationException.VALIDATION_EXCEPTION_FORMAT, input, error));
    }
}
