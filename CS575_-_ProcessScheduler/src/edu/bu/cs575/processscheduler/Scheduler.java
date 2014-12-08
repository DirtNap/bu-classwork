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

    protected void removeRunQueueEntry(ProcessRunQueueEntry entry) {
      this.runQueue.remove(entry);
    }
    
    protected void removeRunQueueEntry() {
      this.removeRunQueueEntry(this.current);
    }
    
    public abstract SchedulingAlgorithm getQueueType();

    public abstract void processTick();

    public ProcessRunQueueEntry ExecuteTick(ProcessRunQueueEntry newProcess) {
        if (null != newProcess) {
            this.runQueue.add(newProcess);
            this.sortRunQueue();
            if (null == this.current) {
              this.current = newProcess;
            }
        }
        ++this.tickCount;
        if (!this.current.equals(this.runQueue.get(0))) {
          this.current.registerInterrupt();
          this.current = this.runQueue.get(0);
        }
        this.processTick();
        if (this.current.isBurstComplete()) {
            this.removeRunQueueEntry();
            return this.current;
        }
        return null;
    }

    public Scheduler() {
        this.runQueue = new ArrayList<ProcessRunQueueEntry>();
        this.tickCount = 0;
    }

}
