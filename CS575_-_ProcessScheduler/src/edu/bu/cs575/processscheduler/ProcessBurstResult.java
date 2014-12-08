package edu.bu.cs575.processscheduler;

import java.util.HashMap;
import java.util.Map;

public class ProcessBurstResult {
    private int burstSize;
    private int enqueueTime;
    private Map<SchedulingAlgorithm, ProcessRunQueueResult> runResults;
    private SchedulingAlgorithm lowestTurnaroundTimeType;
    private SchedulingAlgorithm highestTurnaroundTimeType;
    private int lowestTurnaroundTimeValue;
    private int highestTurnaroundTimeValue;
    private int lowestResponseTimeValue;
    private int highestResponseTimeValue;
    private SchedulingAlgorithm lowestResponseTimeType;
    private SchedulingAlgorithm highestResponseTimeType;
    private int lowestWaitTimeValue;
    private SchedulingAlgorithm lowestWaitTimeType;
    private SchedulingAlgorithm highestWaitTimeType;
    private int highestWaitTimeValue;
    private double avgWaitTime;
    private double avgResponseTime;
    private double avgTurnaroundTime;

    public ProcessBurstResult(ProcessBurstRequest request) {
      this.burstSize = request.burstSize;
      this.enqueueTime = request.enqueueTime;
      this.runResults = new HashMap<SchedulingAlgorithm, ProcessRunQueueResult>();
      int totalWaitTime = 0;
      int totalTurnaroundTime = 0;
      int totalResponseTime = 0;
      for (SchedulingAlgorithm qType : request.requests.keySet()) {
        ProcessRunQueueResult result = new ProcessRunQueueResult(this.burstSize, this.enqueueTime, request.requests.get(qType));
        this.runResults.put(qType, result);
        if (result.turnaroundTime >= this.highestTurnaroundTimeValue) {
          this.highestResponseTimeType = qType;
          this.highestResponseTimeValue = result.turnaroundTime;
        } else if (result.turnaroundTime <= this.lowestTurnaroundTimeValue) {
          this.lowestTurnaroundTimeType = qType;
          this.lowestTurnaroundTimeValue = result.turnaroundTime;
        }
        if (result.responseTime >= this.highestResponseTimeValue) {
          this.highestResponseTimeType = qType;
          this.highestResponseTimeValue = result.responseTime;
        } else if (result.responseTime <= this.lowestResponseTimeValue) {
          this.lowestResponseTimeType = qType;
          this.lowestResponseTimeValue = result.responseTime;
        }
        if (result.waitTime >= this.highestWaitTimeValue) {
          this.highestResponseTimeType = qType;
          this.highestResponseTimeValue = result.waitTime;
        } else if (result.waitTime <= this.lowestWaitTimeValue) {
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
      return burstSize;
    }

    public int getEnqueueTime() {
      return enqueueTime;
    }

    public SchedulingAlgorithm getLowestTurnaroundTimeType() {
      return lowestTurnaroundTimeType;
    }

    public SchedulingAlgorithm getHighestTurnaroundTimeType() {
      return highestTurnaroundTimeType;
    }

    public int getLowestTurnaroundTimeValue() {
      return lowestTurnaroundTimeValue;
    }

    public int getHighestTurnaroundTimeValue() {
      return highestTurnaroundTimeValue;
    }

    public int getLowestResponseTimeValue() {
      return lowestResponseTimeValue;
    }

    public int getHighestResponseTimeValue() {
      return highestResponseTimeValue;
    }

    public SchedulingAlgorithm getLowestResponseTimeType() {
      return lowestResponseTimeType;
    }

    public SchedulingAlgorithm getHighestResponseTimeType() {
      return highestResponseTimeType;
    }

    public int getLowestWaitTimeValue() {
      return lowestWaitTimeValue;
    }

    public SchedulingAlgorithm getLowestWaitTimeType() {
      return lowestWaitTimeType;
    }

    public SchedulingAlgorithm getHighestWaitTimeType() {
      return highestWaitTimeType;
    }

    public int getHighestWaitTimeValue() {
      return highestWaitTimeValue;
    }

    public double getAvgWaitTime() {
      return avgWaitTime;
    }

    public double getAvgResponseTime() {
      return avgResponseTime;
    }

    public double getAvgTurnaroundTime() {
      return avgTurnaroundTime;
    }
}
