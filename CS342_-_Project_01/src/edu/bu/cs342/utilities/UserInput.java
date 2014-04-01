package edu.bu.cs342.utilities;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * 
 * @author Michael Donnelly
 * 
 */

public class UserInput {
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
    protected UserInput.InputType inputType;

    public UserInput(Readable input) {
        this(input, System.out);
    }

    /**
     * Create an instance of UserInput based on an instance of {@link Readable}
     * 
     * @param input
     *            Readable A Readable to scan for input
     * @param output
     *            PrintStream a PrintStream for output
     */
    public UserInput(Readable input, PrintStream output) {
        this.readableInput = input;
        this.inputType = UserInput.InputType.READABLE;
        this.setOutput(output);
        this.refreshInput();
    }

    public UserInput(InputStream input) {
        this(input, System.out);
    }

    /**
     * Create an instance of UserInput based on an instance of {@link Readable}
     * 
     * @param input
     *            InputStream An InputStream to scan for input
     * @param output
     *            PrintStream a PrintStream for output
     */
    public UserInput(InputStream input, PrintStream output) {
        this.streamInput = input;
        this.inputType = UserInput.InputType.INPUT_STREAM;
        this.setOutput(output);
        this.refreshInput();
    }

    /**
     * Set the output source
     * 
     * @param output
     *            PrintStream the output source.
     */
    private void setOutput(PrintStream output) {
        this.outputSource = output;
    }

    /**
     * Defines the available input types.
     * 
     * @author Michael Donnelly
     * 
     */
    protected enum InputType {
        INPUT_STREAM, READABLE
    }

    /**
     * Create a new scanner based on the available input.
     */
    protected void refreshInput() {
        switch (this.inputType) {
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

    /**
     * Prompt the user for input.
     * 
     * @param prompt
     *            String the text of the prompt.
     * @param separator
     *            char the character separating the text from the user input.
     */
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

    /**
     * Read a boolean value from the user.
     * 
     * @param prompt
     *            String the prompt to display.
     * @param truePrompt
     *            String the value to treat as true (default "Y")
     * @param falsePrompt
     *            String the value to treat as false (default "N")
     * @param defaultResult
     *            Boolean if non-null, the default result when input is not
     *            provided.
     * @return boolean the user input.
     */
    public boolean readBoolean(String prompt, String truePrompt, String falsePrompt,
            Boolean defaultResult) {
        truePrompt = truePrompt.toLowerCase();
        falsePrompt = falsePrompt.toLowerCase();
        if (null != defaultResult) {
            if (defaultResult.booleanValue()) {
                truePrompt = truePrompt.toUpperCase();
            } else {
                falsePrompt = falsePrompt.toUpperCase();
            }
        }
        String promptOutput = String.format("%s (%s/%s)", prompt, truePrompt, falsePrompt);
        this.prompt(promptOutput, '?');
        String input = "";
        this.refreshInput();
        while (input.equals("")) {
            input = this.inputSource.nextLine();
            if (input.toLowerCase().equals(truePrompt.toLowerCase())) {
                return true;
            } else if (input.toLowerCase().equals(falsePrompt.toLowerCase())) {
                return false;
            } else {
                if (defaultResult == null) {
                    input = "";
                    this.prompt(promptOutput, '?');
                } else {
                    return defaultResult.booleanValue();
                }
            }
        }
        return false; // The compiler doesn't believe the above while loop
                      // returns in every case, but it does.
    }

    public double readDouble(String prompt) {
        return this.readDouble(prompt, false);
    }

    /**
     * Read a double value from the user.
     * 
     * @param prompt
     *            String the prompt to display.
     * @param force
     *            boolean when true, user input must include a mantissa.
     * @return double the user input.
     */
    public double readDouble(String prompt, boolean force) {
        double value = 0.0d;
        String input = "";
        this.prompt(prompt);
        this.refreshInput();
        while (input == "") {
            String error = UserInput.NUMBER_ERROR;
            input = this.inputSource.next();
            try {
                value = Double.parseDouble(input);
                if (force) {
                    if (value % 1.0d == 0.0d) {
                        error = UserInput.DECIMAL_ERROR;
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

    /**
     * Read an integer from the user.
     * 
     * @param prompt
     *            String the prompt to display.
     * @param minValue
     *            if provided, the minimum value to accept.
     * @return int the user input.
     */
    public int readInteger(String prompt, int minValue) {
        int value = 0;
        String input = "";
        this.prompt(prompt);
        this.refreshInput();
        while (input == "") {
            String error = UserInput.INTEGER_ERROR;
            input = this.inputSource.next();
            try {
                value = Integer.parseInt(input);
                if (minValue < Integer.MAX_VALUE) {
                    if (value < minValue) {
                        error = String.format(UserInput.MIN_VALUE_ERROR, minValue);
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

    /**
     * Read a word from the user.
     * 
     * @param prompt
     *            String the prompt to display.
     * @param pattern
     *            String if provided, this pattern will be replaced before the
     *            value is returned.
     * @param replacement
     *            String replacement text for {@code pattern}
     * @return String the user input.
     */
    public String readWord(String prompt, String pattern, String replacement) {
        String value = "";
        this.prompt(prompt);
        this.refreshInput();
        while (value.equals("")) {
            value = this.inputSource.next();
            if (pattern != "") {
                value = value.replaceAll(pattern, replacement);
            }
            if (value.length() == 0) {
                value = "";
                this.prompt(UserInput.STRING_LENGTH_ERROR);
            }
        }
        return value;
    }

    /**
     * Read a line in from the user.
     * 
     * @param prompt
     *            String the prompt to display.
     * @return String the user input.
     */
    public String readLine(String prompt) {
        String value = "";
        this.prompt(prompt);
        this.refreshInput();
        while (value.equals("")) {
            value = this.inputSource.nextLine();
            if (value.length() == 0) {
                this.prompt(UserInput.STRING_LENGTH_ERROR);
            }
        }
        return value;
    }

    /**
     * Read an alpha word from the user.
     * 
     * @param prompt
     *            String the prompt to display.
     * @return String the user input.
     */
    public String readAlphaWord(String prompt) {
        return this.readWord(prompt, "[^a-zA-Z]", "");
    }
}
