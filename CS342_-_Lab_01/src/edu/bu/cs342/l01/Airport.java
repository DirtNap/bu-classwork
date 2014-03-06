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
 * An airport, suitable for running simulations upon.
 */
public class Airport {

    /**
     * The time a plane needs a runway for in order to take off
     * 
     * @return int the time required to take off
     */
    public int getRequiredTakeOffTime() {
        return this.requiredTakeOffTime;
    }

    /**
     * The time a plane needs a runway for in order to land
     * 
     * @return int the time required to land
     */
    public int getRequiredLandingTime() {
        return this.requiredLandingTime;
    }

    /**
     * The average number of minutes between planes wishing to depart
     * 
     * @return int average time between departures
     */
    public int getAverageTakeOffTime() {
        return this.averageTakeOffTime;
    }

    /**
     * The average number of minutes between planes wishing to arrive
     * 
     * @return int average time between arrivals
     */
    public int getAverageLandingTime() {
        return this.averageLandingTime;
    }

    /**
     * The maiximum number of minutes a plane can wait at the airport before landing
     * 
     * @return int the time the plane can wait before crashing
     */
    public int getLandingCrashTime() {
        return this.landingCrashTime;
    }

    private int requiredTakeOffTime;
    private int requiredLandingTime;
    private int averageTakeOffTime;
    private int averageLandingTime;
    private int landingCrashTime;

    public Airport() {
    }

    public static void main(String[] args) {
        Airport self = new Airport();
        int simulationTime = self.getConfiguration(System.in, System.out, System.err);
        Simulator simulator = new Simulator(simulationTime, self);
        simulator.run(System.out);
    }

    /**
     * Get a positive integer from an input source
     * @param prompt String the prompt to issue for the integer
     * @param in InputStream the source of input
     * @param out PrintStream the source to communicate with the user
     * @param err PrintStream the source to report errors
     * @return int the requested integer
     */
    private int getInteger(String prompt, InputStream in, PrintStream out, PrintStream err) {
        int result = 0;
        while (0 >= result) {
            @SuppressWarnings("resource") // Closing the scanner would close the InputStream
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

    /**
     * Configure the airport, and determine the simulation time.
     * @param in InputStream the source of input
     * @param out PrintStream the source to communicate with the user
     * @param err PrintStream the source to report errors
     * @return int the number of minutes of simulation to perform
     */
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
