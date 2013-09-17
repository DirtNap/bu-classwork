package edu.bu.cs232;
import java.io.PrintStream;
import java.util.Scanner;
public class InputReader {
	public static final String INTEGER_ERROR = "Please enter a valid integer";
	public static final String MIN_VALUE_ERROR = "Enter an integer greater than or equal to %d";
	public static final String NUMBER_ERROR = "Please enter a valid number";
	public static final String DECIMAL_ERROR = "Please enter a number with a mantissa";
	public static final String STRING_LENGTH_ERROR = "Enter a string with non-zero length (illegal characters may be ignored)";
	public static final String CHAR_LENGTH_ERROR = "Enter only one character per line (last input ignored)";
	protected Scanner inputSource;
	protected PrintStream outputSource;
	protected Readable readableInput;
	protected java.io.InputStream streamInput;
	protected InputReader.InputType inputType;

	public InputReader(Readable input) {
		this(input, System.out);
	}
	
	public InputReader(Readable input, PrintStream output) {
		this.readableInput = input;
		this.inputType = InputReader.InputType.READABLE;
		this.setOutput(output);
		this.refreshInput();
	}
	public InputReader(java.io.InputStream input) {
		this(input, System.out);
	}
	
	public InputReader(java.io.InputStream input, PrintStream output) {
		this.streamInput = input;
		this.inputType = InputReader.InputType.INPUT_STREAM;
		this.setOutput(output);
		this.refreshInput();
	}
	
	private void setOutput(PrintStream output) {
		this.outputSource = output;
	}

	protected enum InputType {
		INPUT_STREAM, READABLE
	}
	
	protected void refreshInput() {
		switch(this.inputType) {
		case READABLE:
			this.inputSource = new Scanner(this.readableInput);
			break;
		case INPUT_STREAM:
			this.inputSource = new Scanner(this.streamInput);
			break;
		}
	}
	
	protected void prompt(String prompt) {
		this.prompt(prompt, ':');
	}
	protected void prompt(String prompt, char separator) {
		if (prompt.length() > 0) {
			this.outputSource.printf("%s%c\t", prompt, separator);
		}
	}

	public boolean readBoolean(String prompt) {
		return this.readBoolean(prompt, "Y", "N");
	}
	public boolean readBoolean(String prompt, String truePrompt, String falsePrompt) {
		return this.readBoolean(prompt, truePrompt, falsePrompt, null);
	}

	public boolean readBoolean(String prompt, String truePrompt, String falsePrompt, Boolean defaultResult) {
		this.prompt(String.format("%s (%s/%s)", prompt, truePrompt,  falsePrompt), '?');
		String input = "";
		this.refreshInput();
		while (input.equals("")) {
			input = this.inputSource.next();
			if (input.toLowerCase().equals(truePrompt.toLowerCase())) { 
				return true;
			} else if (input.toLowerCase().equals(falsePrompt.toLowerCase())) {
				return false;
			} else {
				if (defaultResult == null) {
					input = "";
				} else {
					return defaultResult.booleanValue();
				}
			}
		}
		return false; // The compiler doesn't believe the above while loop returns in every case, but it does.
	}
	public String readByCharacter(String prompt) {
		return this.readByCharacter(prompt, false);
	}
	public String readByCharacter(String prompt, boolean force) {
		StringBuilder ret = new StringBuilder();
		this.prompt(prompt);
		String chars = "";
		this.refreshInput();
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
	public double readDouble(String prompt) {
		return this.readDouble(prompt, false);
	}
	public double readDouble(String prompt, boolean force) {
		double value = 0.0d;
		String input = "";
		this.prompt(prompt);
		this.refreshInput();
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


	public int readInteger(String prompt) {
		return this.readInteger(prompt, Integer.MAX_VALUE);
	}
	public int readInteger(String prompt, int minValue) {
		int value = 0;
		String input = "";
		this.prompt(prompt);
		this.refreshInput();
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
	public String readWord(String prompt) {
		return this.readWord(prompt, "", "");
	}
	public String readWord(String prompt, String pattern, String replacement) {
		return this.readWord(prompt, pattern, replacement, false, false);
	}
	public String readWord(String prompt, String pattern, String replacement, boolean byCharacter, boolean forceCharacter) {
		String value = "";
		this.prompt(prompt);
		this.refreshInput();
		while (value.equals("")) {
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
	public String readLine(String prompt) {
		String value = "";
		this.prompt(prompt);
		this.refreshInput();
		while (value.equals("")) {
			value = this.inputSource.nextLine();
			if (value.length() == 0) {
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
}
