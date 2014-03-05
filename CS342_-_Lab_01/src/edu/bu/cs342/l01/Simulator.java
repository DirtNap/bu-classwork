package edu.bu.cs342.l01;

import java.util.Random;

/**
 * 
 * Simulates running an airport.
 */
public class Simulator {

    private int simulationTime;  // Total time to run
    private int currentTime;  // Time run so far
    private Airport airport;  // The airport on which to run the simulation
    private Runway runway;  // The runway to use for the simulation
    private AirplaneQueue landingQueue;  // Planes waiting to land
    private AirplaneQueue takeOffQueue;  // Planes waiting to take off
    private int landedCount;  // Number of planes landed successfully
    private int takenOffCount;  // Number of planes launched successfully
    private int crashedCount;  // Number of crashed planes
    private double averageTakeOffWait;  // average time waiting for launch
    private double averageLandingWait;  // average time waiting for landing.

    private static Random prng;  // Singleton pseudo-random number generator
    /**
     * Static singleton accessor for a random number generator.
     * @return Random a singleton instance of a random number generator.
     */
    private static Random getRandom() {
        if (null == Simulator.prng) {
            Simulator.prng = new Random();
        }
        return Simulator.prng;
    }

    /**
     * Update a running average.
     * 
     * This method will lose precision
     * @param currentAverage double the average before the new element
     * @param currentCount int the number of elements before the new element
     * @param newValue int new value to add to the average
     * @return double the updated average
     */
    private static double calculateAverage(double currentAverage, int currentCount, int newValue) {
        return ((currentAverage * currentCount) + newValue) / (currentCount + 1);
    }
    /**
     * Simulates running an airport
     * @param simulationTime int Number of minutes to simulate
     * @param airport Airport a configured airport on which to run the simulation
     */
    public Simulator(int simulationTime, Airport airport) {
        this.simulationTime = simulationTime;
        this.currentTime = 0;
        this.airport = airport;
        this.runway = new Runway();
        this.takeOffQueue = new AirplaneQueue(this.simulationTime);
        this.landingQueue = new AirplaneQueue(this.simulationTime);
        this.landedCount = 0;
        this.takenOffCount = 0;
        this.crashedCount = 0;
        this.averageTakeOffWait = 0.0d;
        this.averageLandingWait = 0.0d;
    }
    /**
     * Evaluates a "one-in-'max'" chance
     * 
     * @param max int number of possible values
     * @return boolean true if max was selected, otherwise false
     */
    public static boolean checkProbabilityEvent(int max) {
        int result = Simulator.getRandom().nextInt(max) + 1;
        return (result == max);
    }

    /**
     * Simulates running an airport.
     * 
     * Running an airport follows the following algorithm:
     * 
     * Each minute:
     * * See if time has run out on the simulation
     * * * if not:
     * * * * Check to see if a plane wants to land
     * * * * * If so, add it to the landing queue
     * * * * Check to see if a plane wants to take-off
     * * * * * If so, add it to the take-off queue
     * * * * While there are planes waiting to land:
     * * * * * Check to see if the first plane has crashed
     * * * * * * If so, discard it and get the new plane
     * * * * * Check to see if the first plane can claim the runway
     * * * * * * If so, discard it and end the landing loop
     * * * * If there are planes waiting to take off:
     * * * * * Check to see if the first plane can claim the runway
     * * * * * * If so, discard it and end the take-off loop
     * See if any of the planes in the landing queue had crashed by the simulation time.
     */
    public void run() {
        while (++this.currentTime <= this.simulationTime) {
            if (Simulator.checkProbabilityEvent(this.airport.getAverageLandingTime())) {
                this.landingQueue.enqueue(new Airplane(this.airport.getRequiredLandingTime(),
                        this.currentTime));
            }
            if (Simulator.checkProbabilityEvent(this.airport.getAverageTakeOffTime())) {
                this.takeOffQueue.enqueue(new Airplane(this.airport.getRequiredTakeOffTime(),
                        this.currentTime));
            }
            while (0 < this.landingQueue.length()) {
                Airplane lander = this.landingQueue.peek();
                if (lander.enqueueTime + this.airport.getLandingCrashTime() < this.currentTime) {
                    ++this.crashedCount;
                    this.landingQueue.dequeue();
                } else {
                    if (this.runway.claim(this.currentTime, lander.runwayClaimTime)) {
                        this.landingQueue.dequeue();
                        this.averageLandingWait = Simulator.calculateAverage(
                                this.averageLandingWait, this.landedCount, this.currentTime
                                        - lander.enqueueTime);
                        ++this.landedCount;
                    }
                    break;
                }
            }
            if (0 < this.takeOffQueue.length()) { 
                Airplane takeOff = this.takeOffQueue.peek();
                if (this.runway.claim(this.currentTime, takeOff.runwayClaimTime)) {
                    this.takeOffQueue.dequeue();
                    this.averageTakeOffWait = Simulator.calculateAverage(this.averageTakeOffWait,
                            this.takenOffCount, this.currentTime - takeOff.enqueueTime);
                    ++this.takenOffCount;
                }
            }
        }
        while (0 < this.landingQueue.length()) {
            Airplane nonLander = this.landingQueue.dequeue();
            if (nonLander.enqueueTime + this.airport.getLandingCrashTime() < this.currentTime) {
                ++this.crashedCount;
            } else {
                break;
            }
        }
        System.out.printf("Number of planes that took off:\t%d%n", this.takenOffCount);
        System.out.printf("Number of planes that landed:\t%d%n", this.landedCount);
        System.out.printf("Number of planes that crashed:\t%d%n", this.crashedCount);
        System.out.printf("Average time waiting for take-off:\t%.02f%n", this.averageTakeOffWait);
        System.out.printf("Average time waiting for landing:\t%.02f%n", this.averageLandingWait);
    }
}
