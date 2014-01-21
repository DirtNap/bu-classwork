package edu.bu.cs232;

/**
 * @author dirtnap
 *
 * Exception is raised when an array based superclass and list based subclass
 * should contain the same items, but do not.
 */
public class ArrayListItemMismatchException extends RuntimeException {

	private static final long serialVersionUID = 2665707740436393382L;
	public static final String DEFAULT_MESSAGE = "Array and List items don't match.";

	/**
	 * 
	 */
	public ArrayListItemMismatchException() {
		this(ArrayListItemMismatchException.DEFAULT_MESSAGE);
	}

	/**
	 * @param message
	 */
	public ArrayListItemMismatchException(String message) {
		super(message);
	}

	/**
	 * This private constructor prevents use of the parent class constructor.
	 * 
	 * @param cause
	 */
	@SuppressWarnings("unused")
	private ArrayListItemMismatchException(Throwable cause) {
		throw new AssertionError("Superclass constructor not valid for subclass.");
	}

	/**
	 * This private constructor prevents use of the parent class constructor.
	 * 
	 * @param message
	 * @param cause
	 */
	@SuppressWarnings("unused")
	private ArrayListItemMismatchException(String message, Throwable cause) {
		throw new AssertionError("Superclass constructor not valid for subclass.");
	}

	/**
	 * This private constructor prevents use of the parent class constructor.
	 * 
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	@SuppressWarnings("unused")
	private ArrayListItemMismatchException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		throw new AssertionError("Superclass constructor not valid for subclass.");
	}

}
