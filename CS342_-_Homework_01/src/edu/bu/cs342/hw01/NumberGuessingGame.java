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

    public NumberGuessingGame() {
        this(1000);
    }

    public NumberGuessingGame(int maxValue) {
        this(maxValue, new Scanner(System.in), System.out, System.err);
    }

    public NumberGuessingGame(int maxValue, Scanner scanner, PrintStream out, PrintStream err) {
        this.maxValue = maxValue;
        this.minValue = 1;
        this.scanner = scanner;
        this.out = out;
        this.err = err;
        this.solution = this.generateRandomNumber(this.minValue, this.maxValue);
    }

    protected int generateRandomNumber(int min, int max) {
        Random prng = new Random();
        int upperBound = (max + 1) - min;
        int result = prng.nextInt(upperBound);
        return min + result;
    }

    protected int getGuess() {
        if (this.minValue == this.maxValue) {
            this.out.printf("The only option left is %d!%n", this.minValue);
            return this.minValue;
        }
        int result = 0;
        do {
            this.out.printf("Enter a number between %d and %d:  ", this.minValue, this.maxValue);
            String scanned = this.scanner.next();
            try {
                result = Integer.parseInt(scanned, this.scanner.radix());
            } catch (NumberFormatException ex) {
                this.err.println("Please enter a valid integer.");
            }
        } while (this.minValue > result || this.maxValue < result);
        return result;
    }

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
        NumberGuessingGame self = new NumberGuessingGame();
        self.run();
    }
}
