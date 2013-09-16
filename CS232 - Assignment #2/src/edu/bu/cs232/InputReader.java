package edu.bu.cs232;
import java.util.Scanner;
import java.io.PrintStream;
public class InputReader {
	public static final String INTEGER_ERROR = "Please enter a valid integer";
	public static final String MIN_VALUE_ERROR = "Enter an integer greater than or equal to %d";
	public static final String NUMBER_ERROR = "Please enter a valid number";
	public static final String DECIMAL_ERROR = "Please enter a number with a mantissa";
	public static final String STRING_LENGTH_ERROR = "Enter a string with non-zero length (illegal characters may be ignored)";
	public static final String CHAR_LENGTH_ERROR = "Enter only one character per line (last input ignored)";
	protected Scanner inputSource;
	protected PrintStream outputSource;

	public InputReader(Scanner input) {
		this(input, System.out);
	}
	
	public InputReader(Scanner input, PrintStream output) {
		this.inputSource = input;
		this.outputSource = output;
	}
	private void prompt(String prompt) {
		this.prompt(prompt, ':');
	}
	private void prompt(String prompt, char separator) {
		switch (prompt) {
		case "":
			break;
		default:
			this.outputSource.printf("%s%c\t", prompt, separator);
		}
	}
	
	public int readInteger(String prompt) {
		return this.readInteger(prompt, Integer.MAX_VALUE);
	}
	
	public int readInteger(String prompt, int minValue) {
		int value = 0;
		String input = "";
		this.prompt(prompt);
		while (input == "") {
			String error = InputReader.INTEGER_ERROR;
			input = this.inputSource.next();
			try {
				value = Integer.parseInt(input);
				if (minValue < Integer.MAX_VALUE) {
					if (value < minValue) {
						error = String.format(InputReader.MIN_VALUE_ERROR, minValue);
						throw new NumberFormatException();
					}
				}
			} catch (NumberFormatException ex) {
				input = "";
				this.prompt(error);
			}
		}
		return value;
	}

	public double readDouble(String prompt) {
		return this.readDouble(prompt, false);
	}
	public double readDouble(String prompt, boolean force) {
		double value = 0.0d;
		String input = "";
		this.prompt(prompt);
		while (input == "") {
			String error = InputReader.NUMBER_ERROR;
			input = this.inputSource.next();
			try {
				value = Double.parseDouble(input);
				if (force) {
					if (value % 1.0d == 0.0d) {
						error = InputReader.DECIMAL_ERROR;
						throw new NumberFormatException();
					}
				}
			} catch (NumberFormatException ex) {
				input = "";
				this.prompt(error);
			}
		}
		return value;
	}

	public String readWord(String prompt) {
		return this.readWord(prompt, "", "");
	}
	public String readWord(String prompt, String pattern, String replacement) {
		return this.readWord(prompt, pattern, replacement, false, false);
	}
	public String readWord(String prompt, String pattern, String replacement, boolean byCharacter, boolean forceCharacter) {
		String value = "";
		this.prompt(prompt);
		while (value == "") {
			if (byCharacter) {
				value = this.readByCharacter("", forceCharacter);
			}else {
				value = this.inputSource.next();
			}
			if (pattern != "") {
				value = value.replaceAll(pattern, replacement);
			}
			if (value.length() == 0) {
				value = "";
				this.prompt(InputReader.STRING_LENGTH_ERROR);
			}
		}
		return value; 
	}
	public String readAlphaWord(String prompt) {
		return this.readWord(prompt, "[^a-zA-Z]", "");
	}
	public String readAlphaWord(String prompt, boolean byCharacter, boolean forceCharacter) {
		return this.readWord(prompt, "[^a-zA-Z]", "", byCharacter, forceCharacter);
	}


	public String readByCharacter(String prompt) {
		return this.readByCharacter(prompt, false);
	}
	public String readByCharacter(String prompt, boolean force) {
		StringBuilder ret = new StringBuilder();
		this.prompt(prompt);
		String chars = "";
		while (! chars.equals("") || ret.length() == 0) {
			chars = this.inputSource.nextLine();
			if ((force && chars.length() > 1) || (ret.length() == 0 && chars == "")) {
				this.prompt(InputReader.CHAR_LENGTH_ERROR);
			} else {
				ret.append(chars);
			}
		}
		return ret.toString();
	}
	public boolean readBoolean(String prompt) {
		return this.readBoolean(prompt, "Y", "N");
	}
	public boolean readBoolean(String prompt, String truePrompt, String falsePrompt) {
		return this.readBoolean(prompt, truePrompt, falsePrompt, null);
	}
	public boolean readBoolean(String prompt, String truePrompt, String falsePrompt, Boolean defaultResult) {
		this.prompt(String.format("%s (%s/%s)", prompt, truePrompt,  falsePrompt), '?');
		String input = this.inputSource.next();
		if (input.toLowerCase().equals(truePrompt.toLowerCase())) { 
		// switch (input.toLowerCase()) {
			return true;
		} else if (input.toLowerCase().equals(falsePrompt.toLowerCase())) {
			return false;
		} else {
			if (defaultResult == null) {
				return this.readBoolean(prompt, truePrompt, falsePrompt, defaultResult);
			} else {
				return defaultResult.booleanValue();
			}
		}
	}
}
