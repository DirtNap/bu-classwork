package edu.bu.cs232;


public class GameExitException extends Exception {

	private static final long serialVersionUID = -6816422808844012892L;

	public GameExitException() {
		super("Player requested exit from the current game.");
	}

}
