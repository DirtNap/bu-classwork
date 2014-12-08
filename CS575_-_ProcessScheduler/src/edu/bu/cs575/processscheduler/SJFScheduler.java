package edu.bu.cs575.processscheduler;

public class SJFScheduler extends Scheduler {

  public SJFScheduler() {
    // TODO(dirtnap): Auto-generated constructor stub
  }

  @Override
  public int compare(ProcessRunQueueEntry o1, ProcessRunQueueEntry o2) {
    return Integer.compare(o1.request.burstSize, o2.request.burstSize);
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
