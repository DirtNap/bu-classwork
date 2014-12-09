package edu.bu.cs575.processscheduler;

import java.util.HashMap;
import java.util.Map;

public class ProcessBurstResult {
    private int burstSize;
    private int enqueueTime;
    private Map<SchedulingAlgorithm, ProcessRunQueueResult> runResults;
    private SchedulingAlgorithm lowestTurnaroundTimeType;
    private SchedulingAlgorithm highestTurnaroundTimeType;
    private int lowestTurnaroundTimeValue = -1;
    private int highestTurnaroundTimeValue = -1;
    private int lowestResponseTimeValue = -1;
    private int highestResponseTimeValue = -1;
    private SchedulingAlgorithm lowestResponseTimeType;
    private SchedulingAlgorithm highestResponseTimeType;
    private int lowestWaitTimeValue = -1;
    private SchedulingAlgorithm lowestWaitTimeType;
    private SchedulingAlgorithm highestWaitTimeType;
    private int highestWaitTimeValue = -1;
    private double avgWaitTime;
    private double avgResponseTime;
    private double avgTurnaroundTime;
    private int processId;
    private int priority;

    @Override
    public String toString() {
        String format = "%d, %d, %d, %.2f, %d, %s, %d, %s, %.2f, %d, %s, %d, %s, %.2f, %d, %s, %d, %s";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(format, this.processId, this.priority, this.burstSize, this
                .getAvgResponseTime(), this.getLowestResponseTimeValue(), this
                .getLowestResponseTimeType().name(), this.getHighestResponseTimeValue(), this
                .getHighestResponseTimeType().name(), this.getAvgTurnaroundTime(), this
                .getLowestTurnaroundTimeValue(), this.getLowestTurnaroundTimeType().name(), this
                .getHighestTurnaroundTimeValue(), this.getHighestTurnaroundTimeType().name(), this
                .getAvgWaitTime(), this.getLowestWaitTimeValue(), this.getLowestWaitTimeType()
                .name(), this.getHighestWaitTimeValue(), this.getHighestWaitTimeType().name()));
        return sb.toString();
    }

    public ProcessBurstResult(ProcessBurstRequest request) {
        this.processId = request.process.processId;
        this.priority = request.process.priority;
        this.burstSize = request.burstSize;
        this.enqueueTime = request.enqueueTime;
        this.runResults = new HashMap<SchedulingAlgorithm, ProcessRunQueueResult>();
        int totalWaitTime = 0;
        int totalTurnaroundTime = 0;
        int totalResponseTime = 0;
        for (SchedulingAlgorithm qType : request.requests.keySet()) {
            ProcessRunQueueResult result = new ProcessRunQueueResult(this.burstSize,
                    this.enqueueTime, request.requests.get(qType));
            this.runResults.put(qType, result);
            if (result.turnaroundTime >= this.highestTurnaroundTimeValue) {
                this.highestTurnaroundTimeType = qType;
                this.highestTurnaroundTimeValue = result.turnaroundTime;
            }
            if (this.lowestTurnaroundTimeValue < 0
                    || result.turnaroundTime <= this.lowestTurnaroundTimeValue) {
                this.lowestTurnaroundTimeType = qType;
                this.lowestTurnaroundTimeValue = result.turnaroundTime;
            }
            if (result.responseTime >= this.highestResponseTimeValue) {
                this.highestResponseTimeType = qType;
                this.highestResponseTimeValue = result.responseTime;
            }
            if (this.lowestResponseTimeValue < 0
                    || result.responseTime <= this.lowestResponseTimeValue) {
                this.lowestResponseTimeType = qType;
                this.lowestResponseTimeValue = result.responseTime;
            }
            if (result.waitTime >= this.highestWaitTimeValue) {
                this.highestWaitTimeType = qType;
                this.highestWaitTimeValue = result.waitTime;
            }
            if (this.lowestWaitTimeValue < 0 || result.waitTime <= this.lowestWaitTimeValue) {
                this.lowestWaitTimeType = qType;
                this.lowestWaitTimeValue = result.waitTime;
            }
            totalWaitTime += result.waitTime;
            totalTurnaroundTime += result.turnaroundTime;
            totalResponseTime += result.responseTime;
        }
        this.avgWaitTime = (double) totalWaitTime / (double) this.runResults.size();
        this.avgTurnaroundTime = (double) totalTurnaroundTime / (double) this.runResults.size();
        this.avgResponseTime = (double) totalResponseTime / (double) this.runResults.size();
    }

    public int getBurstSize() {
        return this.burstSize;
    }

    public int getEnqueueTime() {
        return this.enqueueTime;
    }

    public SchedulingAlgorithm getLowestTurnaroundTimeType() {
        return this.lowestTurnaroundTimeType;
    }

    public SchedulingAlgorithm getHighestTurnaroundTimeType() {
        return this.highestTurnaroundTimeType;
    }

    public int getLowestTurnaroundTimeValue() {
        return this.lowestTurnaroundTimeValue;
    }

    public int getHighestTurnaroundTimeValue() {
        return this.highestTurnaroundTimeValue;
    }

    public int getLowestResponseTimeValue() {
        return this.lowestResponseTimeValue;
    }

    public int getHighestResponseTimeValue() {
        return this.highestResponseTimeValue;
    }

    public SchedulingAlgorithm getLowestResponseTimeType() {
        return this.lowestResponseTimeType;
    }

    public SchedulingAlgorithm getHighestResponseTimeType() {
        return this.highestResponseTimeType;
    }

    public int getLowestWaitTimeValue() {
        return this.lowestWaitTimeValue;
    }

    public SchedulingAlgorithm getLowestWaitTimeType() {
        return this.lowestWaitTimeType;
    }

    public SchedulingAlgorithm getHighestWaitTimeType() {
        return this.highestWaitTimeType;
    }

    public int getHighestWaitTimeValue() {
        return this.highestWaitTimeValue;
    }

    public double getAvgWaitTime() {
        return this.avgWaitTime;
    }

    public double getAvgResponseTime() {
        return this.avgResponseTime;
    }

    public double getAvgTurnaroundTime() {
        return this.avgTurnaroundTime;
    }
}
