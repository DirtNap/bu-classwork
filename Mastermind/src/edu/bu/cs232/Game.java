package edu.bu.cs232;

import java.io.PrintStream;

public class Game {

	private String playerName;
	private ScoreBoard scoreBoard;
	private PrintStream output;
	private InputReader reader;
	private Colors[] solution;
	private int turns;
	private Menu difficultyMenu;
	private int currentTurn;

	public Game(String playerName, ScoreBoard scoreBoard, PrintStream output, InputReader reader) {
		this.playerName = playerName;
		this.scoreBoard = scoreBoard;
		this.output = output;
		this.reader = reader;
		this.solution = Colors.randomPattern(4, Colors.WHITE, Colors.BLACK, Colors.RED,
				                                Colors.PINK, Colors.GREEN, Colors.YELLOW);
		this.difficultyMenu = new Menu("Difficulty", this.output, this.reader,
                                       MenuItems.Easy, MenuItems.Medium, MenuItems.Hard);
		this.turns = 12;
		this.currentTurn = 0;
	}
	public void setDifficulty() {
		switch (this.difficultyMenu.getMenuOption()) {
		case Easy:
			this.turns = 12;
			break;
		case Medium:
			this.turns = 10;
			break;
		case Hard:
			this.turns = 8;
			break;
		default:
			this.turns = 12;
		}
	}
	public void playGame() throws GameExitException {
		this.output.println("Guess my pattern!");
		String guess = "";
		while (++this.currentTurn <= this.turns) {
			guess = this.getGuess();
			if (guess.equalsIgnoreCase("XXXX")) {
				break;
			} else {
				this.output.printf("Result:\t%s%n", guess);
			}
				
		}
		if (guess.equalsIgnoreCase("XXXX")) {
			System.out.println("You Win!");
			this.scoreBoard.addEntry(new ScoreEntry(this.playerName, Game.calculateScore(this.currentTurn, this.turns)));
		} else {
			System.out.println("Sorry, you lose!");
		}
	}
	public static int calculateScore(int turnsTaken, int totalTurns) {
		int modifier = 20 - totalTurns;
		int pointsPerTurn = modifier * 25;
		return ((modifier - turnsTaken) * pointsPerTurn);
	}
	private String getGuess() throws GameExitException {
		while (true) {
			String guess = this.reader.readLine(String.format("Enter guess #%d (4 characters), or X to exit",
                                                               this.currentTurn));
			if (guess.equalsIgnoreCase("x")) {
				throw new GameExitException();
			}
			try {
				String result = this.validateGuess(guess);
				return result;
			} catch (IllegalArgumentException ex) {
				System.out.println("Invalid guess format.");
			}
		}
	}
	private String validateGuess(String guess) {
		String[] result = new String[this.solution.length];
		String[] tempSol = new String[this.solution.length];
		guess = guess.replaceAll("[ 	]", "");
		if (guess.length() != 4) {
			throw new IllegalArgumentException("Guess must be four characters long");
		}
		for (int i = 0; i < result.length; ++i) {
			result[i] = "-";
			tempSol[i] = this.solution[i].getSelector(); 
			if (tempSol[i].equalsIgnoreCase(String.valueOf(guess.charAt(i)))) {
				result[i] = "X";
				tempSol[i] = "-";
			}
		}
		for (int i = 0; i < result.length; ++i) {
			if (result[i].equals("-")) {
				for (int j = 0; j < tempSol.length; ++j) {
					if (!tempSol[j].equals("-")) {
						if (tempSol[j].equalsIgnoreCase(String.valueOf(guess.charAt(i)))) {
							tempSol[j] = "-";
							result[i] = "O";
						}
					}
				}
			}
		}
		StringBuilder sb = new StringBuilder(4);
		for (int i = 0; i < result.length; ++i) {
			sb.append(result[i]);
		}
		return sb.toString();
	}
}
