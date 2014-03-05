package edu.bu.cs342.l01;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * @author dirtnap@bu.edu
 * @author jbracken@bu.edu
 * @author dthapa@bu.edu
 * 
 */
public class Airport {

    public int getRequiredTakeOffTime() {
        return this.requiredTakeOffTime;
    }

    public int getRequiredLandingTime() {
        return this.requiredLandingTime;
    }

    public int getAverageTakeOffTime() {
        return this.averageTakeOffTime;
    }

    public int getAverageLandingTime() {
        return this.averageLandingTime;
    }

    public int getLandingCrashTime() {
        return this.landingCrashTime;
    }

    public int getSimulationTime() {
        return this.simulationTime;
    }

    private int requiredTakeOffTime;
    private int requiredLandingTime;
    private int averageTakeOffTime;
    private int averageLandingTime;
    private int landingCrashTime;
    private int simulationTime;

    public Airport() {
    }

    public static void main(String[] args) {
        Airport self = new Airport();
        int simulationTime = self.getConfiguration(System.in, System.out, System.err);
        Simulator simulator = new Simulator(simulationTime, self);
        simulator.run();
    }

    private int getInteger(String prompt, InputStream in, PrintStream out, PrintStream err) {
        int result = 0;
        while (0 >= result) {
            @SuppressWarnings("resource")
            Scanner scanner = new Scanner(in);
            out.printf("%s:\t", prompt);
            int input = 0;
            try {
                input = scanner.nextInt();
                if (input <= 0) {
                    err.println("Please enter a positive integer for number of minutes.");
                }
            } catch (InputMismatchException ex) {
                err.println("Invalid Integer");
            }
            result = input;
        }
        return result;
    }

    private int getConfiguration(InputStream in, PrintStream out, PrintStream err) {
        this.requiredTakeOffTime = this.getInteger(
                "Enter the number of minutes needed for one plane to land", in, out, err);
        this.requiredLandingTime = this.getInteger(
                "Enter the number of minutes needed for one plane to take off", in, out, err);
        this.averageTakeOffTime = this.getInteger(
                "Enter the average time between take-off attempts", in, out, err);
        this.averageLandingTime = this.getInteger(
                "Enter the average time between landing attempts", in, out, err);
        this.landingCrashTime = this.getInteger(
                "Enter the time a plane can circle without crashing", in, out, err);
        return this.getInteger("Enter the number of minutes to simulate", in, out, err);

    }
}
