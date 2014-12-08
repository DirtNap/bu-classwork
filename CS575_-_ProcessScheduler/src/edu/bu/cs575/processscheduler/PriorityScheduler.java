package edu.bu.cs575.processscheduler;

public class PriorityScheduler extends Scheduler {

    public PriorityScheduler() {
        super();
    }

    @Override
    public int compare(ProcessRunQueueEntry o1, ProcessRunQueueEntry o2) {
        return Integer.compare(o1.request.process.priority, o2.request.process.priority);
    }

    @Override
    public SchedulingAlgorithm getQueueType() {
        return SchedulingAlgorithm.PRIORITY;
    }

    @Override
    public void processTick() {
        this.current.registerTick();
    }
}
