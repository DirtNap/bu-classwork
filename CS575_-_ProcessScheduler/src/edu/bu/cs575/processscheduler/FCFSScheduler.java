package edu.bu.cs575.processscheduler;

/**
 * Implementation of a First Come First Serve Scheduler
 */
public class FCFSScheduler extends Scheduler {
    public FCFSScheduler() {
        super();
    }

    @Override
    public SchedulingAlgorithm getQueueType() {
        return SchedulingAlgorithm.FCFS;
    }

    @Override
    public int compare(ProcessRunQueueEntry prqe1, ProcessRunQueueEntry prqe2) {
        return Integer.compare(prqe1.request.enqueueTime, prqe2.request.enqueueTime);
    }

    @Override
    public void processTick() {
        this.current.registerTick(this.tickCount);
    }
}
