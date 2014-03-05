package edu.bu.cs342.l01;

import java.util.Random;

public class Simulator {

    private int simulationTime;
    private int currentTime;
    private Airport airport;
    private Runway runway;
    private AirplaneQueue landingQueue;
    private AirplaneQueue takeOffQueue;
    private int landedCount;
    private int takenOffCount;
    private int crashedCount;
    private double averageTakeOffWait;
    private double averageLandingWait;

    private static Random prng;

    private static Random getRandom() {
        if (null == Simulator.prng) {
            Simulator.prng = new Random();
        }
        return Simulator.prng;
    }

    private static double calculateAverage(double currentAverage, int currentCount, int newValue) {
        return ((currentAverage * currentCount) + newValue) / (currentCount + 1);
    }

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

    public static boolean checkProbabilityEvent(int max) {
        int result = Simulator.getRandom().nextInt(max) + 1;
        return (result == max);
    }

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
            if (0 < this.takeOffQueue.length()) { // check the length of the
                                                  // take-off queue
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
