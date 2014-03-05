package edu.bu.cs342.l01;

public class Airplane {

    public final int runwayClaimTime;
    public final int enqueueTime;

    public Airplane(int claimTime, int enqueueTime) {
        this.runwayClaimTime = claimTime;
        this.enqueueTime = enqueueTime;
    }
}
