package edu.bu.cs579.etch.exceptions;

public class VariableNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 945718955540955463L;

	public VariableNotFoundException(String msg) {
		super(msg);
	}
}
