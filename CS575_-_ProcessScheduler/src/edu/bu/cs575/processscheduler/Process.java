package edu.bu.cs575.processscheduler;

public class Process implements Cloneable {

    public final long created;
    public final int processId;
    public final int priority;
    private int enqueueTime;
    private int burstTime;
    private int ticksInBurst;

    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (this == o) {
            return true;
        }
        try {
            Process test = (Process) o;
            return this.created == test.created && this.processId == test.processId;
        } catch (ClassCastException ex) {
            return false;
        }
    }

    public Process(int processId, int priority) {
        this.processId = processId;
        this.created = System.currentTimeMillis();
        this.priority = priority;
    }

    private Process(Process from) {
        this.processId = from.processId;
        this.priority = from.priority;
        this.created = from.created;
        this.setBurstTime(from.getBurstTime());
    }

    public int getBurstTime() {
        return this.burstTime;
    }

    public int getEnqueueTime() {
        return this.enqueueTime;
    }

    public void setEnqueueTime(int ticks) {
        this.enqueueTime = ticks;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public boolean applyBurstTick() {
        this.ticksInBurst++;
        return (this.ticksInBurst == this.burstTime);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Process(this);
    }
}
