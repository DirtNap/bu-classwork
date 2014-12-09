package edu.bu.cs575.processscheduler;

import java.util.HashMap;
import java.util.Map;

public class SJFScheduler extends Scheduler {

    private Map<Integer, PredictiveResult> burstMap;
    private double recentPredictionWeight;
    private int defaultBurstPrediction;

    private class PredictiveResult {
        public int lastBurst;
        public int lastPrediction;
    }

    public SJFScheduler(int recentPredictionWeightPercent, int defaultBurstPrediction) {
        super();
        this.burstMap = new HashMap<Integer, PredictiveResult>();
        this.recentPredictionWeight = recentPredictionWeightPercent / 100.0d;
        this.defaultBurstPrediction = defaultBurstPrediction;
    }

    @Override
    protected void addRunQueueEntry(ProcessRunQueueEntry entry) {
        super.addRunQueueEntry(entry);
        int prediction = this.defaultBurstPrediction;
        PredictiveResult pr;
        if (this.burstMap.containsKey(entry.request.process.processId)) {
            pr = this.burstMap.get(entry.request.process.processId);
            prediction = (int) Math.round((this.recentPredictionWeight * pr.lastBurst)
                    + ((1.0d - this.recentPredictionWeight) * pr.lastPrediction));
            pr.lastPrediction = prediction;
        } else {
            pr = new PredictiveResult();
            this.burstMap.put(entry.request.process.processId, pr);
            pr.lastBurst = entry.request.burstSize;
            pr.lastPrediction = prediction;
        }
    }

    protected int getBurstPrediction(ProcessRunQueueEntry entry) {
        return this.burstMap.get(entry.request.process.processId).lastPrediction;
    }

    @Override
    public int compare(ProcessRunQueueEntry o1, ProcessRunQueueEntry o2) {
        return Integer.compare(this.getBurstPrediction(o1), this.getBurstPrediction(o2));
    }

    @Override
    public SchedulingAlgorithm getQueueType() {
        return SchedulingAlgorithm.SJF;
    }

    @Override
    public void processTick() {
        this.current.registerTick(this.tickCount);
    }

}
