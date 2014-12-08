package edu.bu.cs575.processscheduler;

public class ProcessResult {

  public final int processId;
  public final int priority;
  public final int totalBursts;
  ProcessBurstResult[] burstResults;
  private int totalCPUTime = 0;
  private double avgResponseTime = 0.0d;
  private double avgTurnaroundTime = 0.0d;
  private double avgWaitTime = 0.0d;

  public ProcessResult(Process p) {
    this.processId = p.processId;
    this.priority = p.priority;
    this.totalBursts = p.burstRequests.size();
    this.burstResults = new ProcessBurstResult[this.totalBursts];
    int idx = 0;
    for (ProcessBurstRequest request : p.burstRequests) {
      ProcessBurstResult result = new ProcessBurstResult(request);
      this.totalCPUTime  += result.getBurstSize();
      this.avgWaitTime += result.getAvgWaitTime();
      this.avgTurnaroundTime += result.getAvgTurnaroundTime();
      this.avgResponseTime += result.getAvgResponseTime();
      this.burstResults[idx++] = result;
    }
  }

  public int getTotalCPUTime() {
    return totalCPUTime;
  }

  public double getAvgResponseTime() {
    return avgResponseTime;
  }

  public double getAvgTurnaroundTime() {
    return avgTurnaroundTime;
  }

  public double getAvgWaitTime() {
    return avgWaitTime;
  }
  
  public double getAverageBurstSize() {
    return (double) this.getTotalCPUTime() / (double) this.totalBursts;
  }
  
  @Override
  public String toString() {
    return String.format("%d, %d, %d, %d, %.2f, %.2f, %.2f, %.2f", this.processId, this.priority,
        this.getTotalCPUTime(), this.totalBursts, this.getAverageBurstSize(), 
        this.getAvgResponseTime(), this.getAvgWaitTime(), this.getAvgTurnaroundTime());
  }
}
