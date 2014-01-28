package edu.bu.cs342.hw01;

import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    private int maxValue;
    private int minValue;
    private final Scanner scanner;
    private final PrintStream out;
    private final PrintStream err;
    private int solution;
    private static Random prng;

    /**
     * A number guessing game. The user selects a number between {@code 1} and
     * some upper boundary. The class provides feedback on the guess, indicating
     * if it was too high, too low, or correct. The number of moves required to
     * reach the correct answer is provided to the user at the end of the game.
     */
    public NumberGuessingGame() {
        this(1000);
    }

    /**
     * Allows the upper bound to be selected.
     * 
     * @param maxValue
     *            the upper bound with which the game should be played.
     */
    public NumberGuessingGame(int maxValue) {
        this(maxValue, new Scanner(System.in), System.out, System.err);
    }

    /**
     * Allows the input/output options to be configured.
     * 
     * @param scanner
     *            a configured java.util.Scanner from which to read input
     * @param out
     *            an open java.io.PrintStream to provide feedback
     * @param err
     *            an open java.io.PrintStream to display errors
     */
    public NumberGuessingGame(int maxValue, Scanner scanner, PrintStream out, PrintStream err) {
        this.maxValue = maxValue;
        this.minValue = 1;
        this.scanner = scanner;
        this.out = out;
        this.err = err;
        this.solution = NumberGuessingGame.generateRandomNumber(this.minValue, this.maxValue);
    }

    /**
     * Generates a pseudo-random number between the two provided values.
     * 
     * @param min
     *            the lowest allowable number
     * @param max
     *            the highest allowable number
     * @return a pseudo-random number between the two provided values
     * 
     * @exception IllegalArgumentException
     *                when {@code min} is less than {@code max}
     * @see java.util.Random#nextInt
     */
    public static int generateRandomNumber(int min, int max) {
        if (max == min) {
            return max;
        } else if (max < min) {
            throw new IllegalArgumentException("Max must not be smaller than min.");
        }
        if (NumberGuessingGame.prng == null) {
            NumberGuessingGame.prng = new Random();
        }
        // Random.nextInt generates a number between 0 and n, where n is the
        // provided upper bound, inclusive of 0 but exclusive of n. 1 must be
        // added to max to allow the upper bound to be included. Next, min is
        // subtracted to make an equivalent range beginning at 0. Once a
        // number is selected from that range, it is added back to min before
        // being returned.
        int upperBound = (max + 1) - min;
        int result = NumberGuessingGame.prng.nextInt(upperBound);
        return min + result;
    }

    /**
     * Gets an integer guess from the user using the provided Scanner and
     * PrintStreams.
     * 
     * @return a valid integer between the minimum and maximum values, as
     *         supplied by the user.
     */
    protected int getGuess() {
        // In this case there is only one possible guess.
        if (this.minValue == this.maxValue) {
            this.out.printf("The only option left is %d!%n", this.minValue);
            return this.minValue;
        }
        int result = 0;
        // Loop until the user provides an integer in the appropriate range
        do {
            this.out.printf("Enter a number between %d and %d:  ", this.minValue, this.maxValue);
            // Get a string from the user input to simplify integer validation
            String scanned = this.scanner.next();
            try {
                result = Integer.parseInt(scanned, this.scanner.radix());
            } catch (NumberFormatException ex) {
                this.err.println("Please enter a valid integer.");
            }
        } while (this.minValue > result || this.maxValue < result);
        return result;
    }

    /**
     * Runs the number guessing game.
     */
    public void run() {
        int guess;
        int moves = 0;
        do {
            ++moves;
            guess = this.getGuess();
            if (guess > this.solution) {
                this.out.println("Sorry, your guess was too high.");
                this.maxValue = guess - 1;
            } else if (guess < this.solution) {
                this.out.println("Sorry, your guess was too low.");
                this.minValue = guess + 1;
            }

        } while (guess != this.solution);
        this.out.printf("Congratulations, you guessed the correct answer in only %d moves.%n",
                moves);
    }

    public static void main(String[] args) {
        // Create an instance of the class and run the game.
        NumberGuessingGame self = new NumberGuessingGame();
        self.run();
    }
}
