package edu.bu.cs575.philosophers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ChopstickMonitor {

    private static class Chopstick {
        private int id;
        private ReentrantLock lock;

        public Chopstick(int id) {
            this.id = id;
            this.lock = new ReentrantLock();
        }

        public boolean claim(long timeOutMillis) throws InterruptedException {
            if (!this.lock.isHeldByCurrentThread()) {
                return this.lock.tryLock(timeOutMillis, TimeUnit.MILLISECONDS);
            } else {
                return true;
            }
        }

        public void release() {
            if (this.lock.isHeldByCurrentThread()) {
                this.lock.unlock();
            }
        }

        @Override
        public String toString() {
            return String.format("Chopstick %d", this.id);
        }
    }

    private static class CumulativeWaitTimer {
        private final long endTime;

        public CumulativeWaitTimer(long timeOutInMilliseconds) {
            this.endTime = System.currentTimeMillis() + timeOutInMilliseconds;
        }

        public long getRemainingTime() {
            return Math.max(0, this.endTime - System.currentTimeMillis());
        }
    }

    private Chopstick[] chopsticks;
    private ReentrantLock monitorLock;

    public ChopstickMonitor(int positions) {
        this.chopsticks = new Chopstick[positions];
        this.monitorLock = new ReentrantLock();
        this.monitorLock.lock();
        for (int i = 0; i < positions; i++) {
            this.chopsticks[i] = new Chopstick(i);
        }
        if (1 >= this.length()) {
            throw new IllegalArgumentException("Must provide at least two chopsticks.");
        }
        this.monitorLock.unlock();
    }

    public int length() {
        return this.chopsticks.length;
    }

    public boolean claimChopsticks(String forWhom, int leftStick, int rightStick,
            long timeOutInMilliseconds) {
        Chopstick left = this.chopsticks[leftStick % this.length()];
        Chopstick right = this.chopsticks[rightStick % this.length()];
        CumulativeWaitTimer timer = new CumulativeWaitTimer(timeOutInMilliseconds);
        try {
            this.monitorLock.tryLock(timer.getRemainingTime(), TimeUnit.MILLISECONDS);
            try {
                boolean leftResult = left.claim(timer.getRemainingTime());
                if (leftResult) {
                    try {
                        boolean rightResult = right.claim(timer.getRemainingTime());
                        if (rightResult) {
                            return true;
                        }
                    } catch (InterruptedException ex) {
                        ; // Didn't get the right chopstick
                    }
                    left.release();
                }
            } catch (InterruptedException ex) {
                ; // Didn't get the left chopstick
            } finally {
                this.monitorLock.unlock();
            }
        } catch (InterruptedException ex) {
            ; // Didn't get the monitor lock
        }
        return false;
    }

    public void releaseChopsticks(String forWhom, int... sticks) {
        for (int stickId : sticks) {
            this.chopsticks[stickId % this.length()].release();
        }
    }
}
