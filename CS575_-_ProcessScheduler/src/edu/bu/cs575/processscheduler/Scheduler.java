package edu.bu.cs575.processscheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Scheduler implements Comparator<ProcessRunQueueEntry> {
  
    protected List<ProcessRunQueueEntry> runQueue;
    protected int tickCount;
    protected ProcessRunQueueEntry current;
    
    protected void sortRunQueue() {
      Collections.sort(this.runQueue, this);
    }
  
    public abstract SchedulingAlgorithm getQueueType();
    public abstract void processTick();
    public void ExecuteTick(ProcessRunQueueEntry newProcess) {
      if (null != newProcess) {
        this.runQueue.add(newProcess);
        this.sortRunQueue();
      }
      ++this.tickCount;
      this.current = this.runQueue.get(0);
      this.processTick();
      if (this.current.isBurstComplete()) {
        this.runQueue.remove(this.current);
      }
    }
    
    public Scheduler() {
      this.runQueue = new ArrayList<ProcessRunQueueEntry>();
      this.tickCount = 0;
    }
    
    
    
}
