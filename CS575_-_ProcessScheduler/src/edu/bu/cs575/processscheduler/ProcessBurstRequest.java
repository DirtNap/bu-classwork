package edu.bu.cs575.processscheduler;

import java.util.HashMap;
import java.util.Map;

public class ProcessBurstRequest {
    public final Process process;
    Map<SchedulingAlgorithm, ProcessRunQueueEntry> requests;
    public final int enqueueTime;
    public final int burstSize;

    public ProcessBurstRequest(Process process, int ticks, int burstSize) {
        this.process = process;
        this.enqueueTime = ticks;
        this.burstSize = burstSize;
        this.requests = new HashMap<SchedulingAlgorithm, ProcessRunQueueEntry>();
    }

    public ProcessRunQueueEntry getRunQueueEntry(SchedulingAlgorithm queueType) {
        if (this.requests.containsKey(queueType)) {
            return this.requests.get(queueType);
        }
        ProcessRunQueueEntry result = new ProcessRunQueueEntry(this, queueType);
        this.requests.put(queueType, result);
        return result;
    }

    public boolean isBurstComplete() {
        for (SchedulingAlgorithm queueType : this.requests.keySet()) {
            if (!this.requests.get(queueType).isBurstComplete()) {
                return false;
            }
        }
        return true;
    }
}
