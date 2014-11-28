package edu.bu.cs575.philosophers;

import java.util.Random;

/**
 * 
 * @author Michael Donnelly
 * 
 */
public class Philosopher extends Thread {

    private static final int PHILOSOPHER_COUNT = 5;
    private static final int PHILOSOPHER_ACTION_COUNT = 50;

    private static enum PhilospherActions {
        THINK("Thinking"), EAT("Eating"), RESTING("Resting");

        private final String description;
        private static final PhilospherActions[] actionList = PhilospherActions.values();

        private static Random randomNumberGenerator;

        PhilospherActions(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return this.description;
        }

        public static PhilospherActions getRandomAction() {
            if (null == PhilospherActions.randomNumberGenerator) {
                PhilospherActions.randomNumberGenerator = new Random();
            }
            return PhilospherActions.actionList[PhilospherActions.randomNumberGenerator
                    .nextInt(PhilospherActions.actionList.length)];
        }
    }

    private final int position;
    private final ChopstickMonitor monitor;
    private final int targetRunCount;
    private int currentRun;
    private int eatAttempted;
    private int eatSuceeded;

    public int getEatAttempted() {
        return this.eatAttempted;
    }

    public int getEatSuceeded() {
        return this.eatSuceeded;
    }

    public Philosopher(int position, ChopstickMonitor monitor, int runCount) {
        this.position = position;
        this.monitor = monitor;
        this.targetRunCount = runCount;
        this.currentRun = 0;
        this.eatAttempted = 0;
        this.eatSuceeded = 0;
    }

    @Override
    public void run() {
        while (this.currentRun < this.targetRunCount) {
            switch (PhilospherActions.getRandomAction()) {
            case EAT:
                ++this.eatAttempted;
                System.out.printf("%s attempting to eat... ", this);
                if (this.monitor.claimChopsticks(this.toString(), this.position, this.position + 1,
                        1000)) {
                    ++this.eatSuceeded;
                    System.out.println("succeeded.");
                } else {
                    System.out.println("failed.");
                }
                break;
            case RESTING:
                break;
            case THINK:
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ;
            } finally {
                ++this.currentRun;
            }
            this.monitor.releaseChopsticks(this.toString(), this.position, this.position + 1);
        }
    }

    @Override
    public String toString() {
        return String.format("Philosopher %d", this.position);
    }

    public static void main(String[] args) {
        ChopstickMonitor monitor = new ChopstickMonitor(Philosopher.PHILOSOPHER_COUNT);
        Philosopher[] philosophers = new Philosopher[Philosopher.PHILOSOPHER_COUNT];
        for (int i = 0; i < philosophers.length; ++i) {
            philosophers[i] = new Philosopher(i, monitor, Philosopher.PHILOSOPHER_ACTION_COUNT);
            philosophers[i].start();
        }
        for (int i = 0; i < philosophers.length; ++i) {
            System.out.printf("%s:\t", philosophers[i]);
            try {
                philosophers[i].join();
                System.out.printf("Attempted to eat %d times, succeeded %d times.%n",
                        philosophers[i].getEatAttempted(), philosophers[i].getEatSuceeded());
            } catch (InterruptedException ex) {
                System.out.println("ERROR");
            }
        }
    }
}
