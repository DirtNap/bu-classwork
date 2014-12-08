package edu.bu.cs575.processscheduler;

import java.util.Random;

public class Simulator {

    private static Random randomNumberGenerator;

    static Random getRandom() {
        if (null == Simulator.randomNumberGenerator) {
            Simulator.randomNumberGenerator = new Random();
        }
        return Simulator.randomNumberGenerator;
    }

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

    private int getVariableNumber(int base) {
        return this.getVariableNumber(base, this.simulationOptions.varianceDegree);
    }

    private int getVariableNumber(int base, int varianceDegree) {
        return this.getVariableNumber(base, varianceDegree, 1);
    }

    private int getVariableNumber(int base, int varianceDegree, int minimum) {
        double factor = Simulator.getRandom().nextGaussian();
        factor *= varianceDegree;
        double intermediate = (double) base;
        intermediate += factor;
        int result = 0;
        switch (Simulator.getRandom().nextInt(1)) {
        case 0:
            result = (int) Math.floor(intermediate);
        case 1:
            result = (int) Math.ceil(intermediate);
        }
        if (minimum > result) {
            result = minimum;
        }
        return result;
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
            this.processes[i] = new Process(1 + i,
                    this.getVariableNumber(this.simulationOptions.avgPriority));
        }
        int[] ps = new int[this.processes.length];
        for (int i = 0; i < this.simulationOptions.burstCount; ++i) {
            int processIndex = Simulator.getRandom().nextInt(this.processes.length);
            this.processes[processIndex].setBurstTime(this
                    .getVariableNumber(this.simulationOptions.avgBurstTime));
            ps[processIndex]++;
        }
        for (int i = 0; i < ps.length; ++i) {
            System.out.println(ps[i]);
        }
    }
}
