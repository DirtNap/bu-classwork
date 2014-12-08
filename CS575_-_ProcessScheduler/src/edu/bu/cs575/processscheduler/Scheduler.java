package edu.bu.cs575.processscheduler;

import java.util.Comparator;

public abstract class Scheduler {
    public abstract Comparator<Process> getComparator();
    public abstract void ExecuteTick(ProcessRunQueueEntry newProcess);
}
