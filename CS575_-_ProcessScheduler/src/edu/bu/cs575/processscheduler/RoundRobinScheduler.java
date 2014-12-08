package edu.bu.cs575.processscheduler;

public class RoundRobinScheduler extends Scheduler {

  private int quantum;
  private int currentQuantum;

  public RoundRobinScheduler(int quantum) {
    super();
    this.quantum = quantum;
    this.currentQuantum = 0;
  }
  
  public RoundRobinScheduler() {
    this(4);
  }

  @Override
  public int compare(ProcessRunQueueEntry o1, ProcessRunQueueEntry o2) {
    return 0;
  }
  
  @Override
  protected void removeRunQueueEntry(ProcessRunQueueEntry entry) {
    super.removeRunQueueEntry(entry);
    this.currentQuantum = 0;
  }


  @Override
  public SchedulingAlgorithm getQueueType() {
    return SchedulingAlgorithm.RR;
  }

  @Override
  public void processTick() {
    if (this.currentQuantum == this.quantum) {
      this.removeRunQueueEntry();
      this.current.registerInterrupt();
      this.runQueue.add(this.current);
      this.current = this.runQueue.get(0);
    }
    this.current.registerTick(this.tickCount);
    this.currentQuantum++;
  }

}
