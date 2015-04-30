package edu.bu.cs575.processscheduler;

public class ProcessRunQueueResult {
    public final int burstSize;
    public final int enqueueTime;
    public final int firstServiceTime;
    public final int lastServiceTime;
    public final int responseTime;
    public final int turnaroundTime;
    public final int waitTime;

    public ProcessRunQueueResult(int burstSize, int enqueueTime, ProcessRunQueueEntry entry) {
        this.burstSize = burstSize;
        this.enqueueTime = enqueueTime;
        this.firstServiceTime = entry.getFirstServiceTime();
        this.responseTime = this.firstServiceTime - this.enqueueTime;
        this.lastServiceTime = entry.getLastServiceTime();
        this.turnaroundTime = this.lastServiceTime + 1 - this.enqueueTime;
        this.waitTime = this.turnaroundTime - this.burstSize;

    }
}
