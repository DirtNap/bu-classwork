package edu.bu.cs575.processscheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Process {

    public final long created;
    public final int processId;
    public final int priority;
    private int burstBase;
    private int varianceDegree;
    List<ProcessBurstRequest> burstRequests;

    private static Random randomNumberGenerator;

    static Random getRandom() {
        if (null == Process.randomNumberGenerator) {
            Process.randomNumberGenerator = new Random();
        }
        return Process.randomNumberGenerator;
    }

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

    public Process(int processId, int varianceDegree, int targetPriority, int targetBurstBase) {
        this.processId = processId;
        this.created = System.currentTimeMillis();
        this.varianceDegree = this.getVariableNumber(varianceDegree, 1, varianceDegree);
        this.priority = this.getVariableNumber(targetPriority);
        this.burstBase = this.getVariableNumber(targetBurstBase);
        this.burstRequests = new ArrayList<ProcessBurstRequest>();
    }

    private int getVariableNumber(int base) {
        return this.getVariableNumber(base, this.varianceDegree);
    }

    private int getVariableNumber(int base, int varianceDegree) {
        return this.getVariableNumber(base, varianceDegree, 1);
    }

    private int getVariableNumber(int base, int varianceDegree, int minimum) {
        double factor = Process.getRandom().nextGaussian();
        factor *= varianceDegree;
        double intermediate = base;
        intermediate += factor;
        int result = 0;
        switch (Process.getRandom().nextInt(1)) {
        case 0:
            result = (int) Math.floor(intermediate);
        case 1:
            result = (int) Math.ceil(intermediate);
        }
        if (minimum > result) {
            result = minimum;
        }
        return result;
    }

    public ProcessBurstRequest getBurstRequest(int ticks) {
        ProcessBurstRequest result = new ProcessBurstRequest(this, ticks,
                this.getVariableNumber(this.burstBase));
        this.burstRequests.add(result);
        return result;
    }

}
