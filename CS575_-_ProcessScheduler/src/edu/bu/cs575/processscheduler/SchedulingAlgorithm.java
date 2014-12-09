package edu.bu.cs575.processscheduler;

public enum SchedulingAlgorithm {
    FCFS("First Come, First Served Scheduler"), SJF("Shortest Job First Scheduler"), PRIORITY(
            "Priority Scheduler"), RR("Round-Robin Scheduler");

    private final String description;

    SchedulingAlgorithm(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return this.description;
    }
}
