package edu.bu.cs232;

public class ItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8353181383798064576L;

	public ItemNotFoundException() {
		super();
	}
	public ItemNotFoundException(String message) {
		super(message);
	}

}
