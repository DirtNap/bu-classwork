package edu.bu.cs342.l01;

/**
 * Simulates an airplane waiting at an airport.
 */
public class Airplane {

    public final int runwayClaimTime;  // For how long this plane needs to claim a runway
    public final int enqueueTime;  // The time at which this plane was put into a queue

    public Airplane(int claimTime, int enqueueTime) {
        this.runwayClaimTime = claimTime;
        this.enqueueTime = enqueueTime;
    }
}
