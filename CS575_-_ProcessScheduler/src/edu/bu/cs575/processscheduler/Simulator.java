package edu.bu.cs575.processscheduler;

public class Simulator {
    private class Options {
        public final int avgBurstTime;
        public final int varianceDegree;
        public final int avgPriority;
        public final int processCount;
        public final int burstCount;

        public Options(int processCount, int burstCount, int varianceDegree, int avgBurstTime,
                int avgPriority) {
            this.avgBurstTime = avgBurstTime;
            this.avgPriority = avgPriority;
            this.varianceDegree = varianceDegree;
            this.burstCount = burstCount;
            this.processCount = processCount;
        }
    }

    private Options simulationOptions;
    private Process[] processes;

    private Options getOptions(String arguments[]) {
        int abt = 5;
        int vd = 2;
        int pri = 3;
        int pc = 100;
        int bc = 10000;
        for (int i = 0; i < arguments.length; ++i) {
            switch (arguments[i].toLowerCase()) {
            case "--average-burst-time":
                abt = Integer.parseInt(arguments[++i]);
                break;
            case "--average-priority":
                pri = Integer.parseInt(arguments[++i]);
                break;
            case "--variance-degree":
                vd = Integer.parseInt(arguments[++i]);
                break;
            case "--process-count":
                pc = Integer.parseInt(arguments[++i]);
                break;
            case "--burst-count":
                bc = Integer.parseInt(arguments[++i]);
            default:
                throw new IllegalArgumentException(String.format("Unknown argument:  %s",
                        arguments[i]));
            }
        }
        return new Options(pc, bc, vd, abt, pri);
    }

    public Simulator(String arguments[]) {
        this.simulationOptions = this.getOptions(arguments);
    }

    public static void main(String args[]) {
        Simulator self = new Simulator(args);
        self.runSimulation();
    }

    public void runSimulation() {
        this.processes = new Process[this.simulationOptions.processCount];
        for (int i = 0; i < this.processes.length; ++i) {
            this.processes[i] = new Process(1000 + 1, this.simulationOptions.varianceDegree, this.simulationOptions.avgPriority, this.simulationOptions.avgBurstTime);
        }
    }
}
