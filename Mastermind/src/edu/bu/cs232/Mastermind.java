package edu.bu.cs232;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public final class Mastermind {

	private static final String[] RULES = new String[] {
		"The computer selects a sequence of four colors, hidden from the player.",
		"The player has a number of turns to guess the pattern.",
		"The player is given more turns to guess on easier difficulty settings.",
		"On each turn, the player enters a sequence of four characters representing the pattern of colors.",
		"Players enter 'R' for 'Red', 'P' for 'Pink', 'G' for 'Green', 'Y' for 'Yellow', 'W' for 'White' and 'B' for 'Black'.",
		"Not all colors will be used in the pattern, and colors may be repeated.",
		"When the player enters a valid guess, the computer returns a code indicating how right or wrong the guess is.",
		"'X' represents the correct color in the correct spot, 'O' in the wrong spot, and '-' an incorrect color.",
		"The object of the game is to guess the pattern before the turns run out."
	};
	
	
	private InputStream input;
	private PrintStream output;
	private InputReader reader;
	private ScoreBoard scoreBoard;
	private String playerName;
	private boolean playEnabled;
	public Mastermind(InputStream input, PrintStream output) {
		this.input = input;
		this.output = output;
		this.playEnabled = true;
		this.reader = new InputReader(this.input, this.output);
		this.output.println("Welcome to Mastermind.");
		this.playerName = this.reader.readLine("What is your name?");
		Menu mainMenu = new Menu("Main Menu", this.output, this.reader,
				                 MenuItems.Play, MenuItems.Scores, MenuItems.Rules, MenuItems.Exit);
		while(this.playEnabled) {
			switch (mainMenu.getMenuOption()) {
			case Play:
				this.playGame();
				break;
			case Rules:
				this.displayRules();
				break;
			case Exit:
				this.playEnabled = false;
			case Scores:
				this.displayScores();
				break;
			default:
				this.output.println("The option was valid, but was not understood.  Exiting.");
				this.playEnabled = false;
				break;
			}
		}
	}
	private ScoreBoard getScoreBoard() {
		if (this.scoreBoard == null) {
			try {
				this.scoreBoard = ScoreBoard.fromFile();
			} catch (Exception read) {
				this.scoreBoard = new ScoreBoard();
				try {
					this.scoreBoard.saveToFile();
				} catch (Exception write) {
					;
				}
			}
		}
		return this.scoreBoard;
	}
	private void displayScores() {
		this.output.printf("High Scores%n%n");
		this.output.println(this.getScoreBoard());
		this.output.println();
	}
	private void displayRules() {
		this.output.printf("Instructions%n%n");
		for (int i = 0; i < Mastermind.RULES.length; ++i) {
			this.output.printf("%d\t%s%n", i + 1, Mastermind.RULES[i]);
		}
		this.output.println();
	}
	private void playGame() {
		Game game = new Game(this.playerName, this.getScoreBoard(), this.output, this.reader);
		game.setDifficulty();
		try {
			game.playGame();
			try {
				this.getScoreBoard().saveToFile();
			} catch (IOException e) {
				;
			}
		} catch (GameExitException ex) {
			this.playEnabled = false;
		}
	}
	public static void main(String[] args) {
		Mastermind self = new Mastermind(System.in, System.out);
		System.exit(self.run());
	}
	private int run() {
		// TODO Auto-generated method stub
		return 0;
	}

}
