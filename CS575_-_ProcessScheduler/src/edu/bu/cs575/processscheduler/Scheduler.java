package edu.bu.cs575.processscheduler;

import java.util.Comparator;

public interface Scheduler {
    public Comparator<Process> getComparator();

    public Process ExecuteTick(Process newProcess);
}
