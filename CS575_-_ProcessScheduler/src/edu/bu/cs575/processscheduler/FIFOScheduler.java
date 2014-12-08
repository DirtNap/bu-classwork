package edu.bu.cs575.processscheduler;

import java.util.ArrayList;
import java.util.Comparator;

public class FIFOScheduler implements Scheduler {

    class FIFOComparator implements Comparator<Process> {

        @Override
        public int compare(Process o1, Process o2) {
            return Long.compare(o1.getEnqueueTime(), o2.getEnqueueTime());
        }

    }

    ArrayList<Process> runQueue;
    private Comparator<Process> comparator;

    private int ticks;

    public FIFOScheduler() {
        this.runQueue = new ArrayList<>();
        this.comparator = new FIFOComparator();
    }

    @Override
    public Process ExecuteTick(Process newProcess) {
        Process current;
        this.ticks++;
        if (null != newProcess) {
            newProcess.setEnqueueTime(this.ticks);
            this.runQueue.add(newProcess);
        }
        current = this.runQueue.get(0);
        if (current.applyBurstTick()) {
            this.runQueue.remove(0);
            return current;
        }
        return null;
    }

    @Override
    public Comparator<Process> getComparator() {
        return this.comparator;
    }
}
