package edu.bu.cs575.processscheduler;

import edu.bu.cs575.processscheduler.Simulator.Options;

public class SimulationResult {

    private int totalRunTicks;
    private Options options;
    private Process[] processes;
    private ProcessResult[] results;

    public SimulationResult(Simulator.Options options, int tickCount, Process[] processes) {
        this.totalRunTicks = tickCount;
        this.options = options;
        this.processes = processes;
        this.results = new ProcessResult[processes.length];
        this.generateAggregates();
    }

    private void generateAggregates() {
        for (int i = 0; i < this.processes.length; ++i) {
            this.results[i] = new ProcessResult(this.processes[i]);
        }
    }

    public void printReportHeader() {
        System.out.println("Simulation Report");
        System.out.printf("Simulated %d processes for %d clock cycles (%d cycles elapsed)%n%n",
                this.processes.length, this.options.burstCount, this.totalRunTicks);
    }

    public void printProcessReport() {
        System.out.println("Detailed Process Report");
        System.out
                .println("PID, Priority, Total CPU Time, Total Bursts, Average Burst Size, Average Response Time, Average Wait Time, Average Turnaround Time");
        for (int i = 0; i < this.results.length; ++i) {
            System.out.println(this.results[i]);
        }
    }

    public void printBurstReport() {
        System.out.println("Detailed Burst Report");
        System.out
                .println("PID, Priority, Burst Size, "
                        + "Average Response Time, Lowest Response Time, Lowest Response Time (Type), Highest Response Time, Highest Response Time (Type), "
                        + "Average Turnaround Time, Lowest Turnaround Time, Lowest Turnaround Time (Type), Highest Turnaround Time, Highest Turnaround Time (Type), "
                        + "Average Wait Time, Lowest Wait Time, Lowest Wait Time (Type), Highest Wait Time, Highest Wait Time (Type)");
        for (int i = 0; i < this.results.length; ++i) {
            for (int j = 0; j < this.results[i].burstResults.length; ++j) {
                System.out.println(this.results[i].burstResults[j]);
            }
        }
    }
}
