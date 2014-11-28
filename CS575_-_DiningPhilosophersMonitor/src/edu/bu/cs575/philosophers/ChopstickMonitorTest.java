package edu.bu.cs575.philosophers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ChopstickMonitorTest {
    private static class ChopstickMonitorTestThread extends Thread {

        private final ChopstickMonitor monitor;
        private final int left;
        private final int right;
        private boolean shouldRun;
        public boolean hasLock;

        public ChopstickMonitorTestThread(ChopstickMonitor monitor, int left, int right) {
            super("Test Monitor Thread");
            this.monitor = monitor;
            this.left = left;
            this.right = right;
            this.shouldRun = true;
            this.hasLock = false;
        }

        @Override
        public void run() {
            if (!this.monitor.claimChopsticks("Monitor Test", this.left, this.right, 0)) {
                fail("Didn't receive chopsticks.");
            }
            this.hasLock = true;
            while (this.shouldRun) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    fail("Thread interrupted unexpectedly.");
                }
            }
            this.monitor.releaseChopsticks("Monitor Test", this.left, this.right);
            this.hasLock = false;
        }

        public void StopRunning() {
            this.shouldRun = false;
        }
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public final void testLength() {
        for (int i = 2; i <= 10; ++i) {
            assertEquals("Created with corect length.", i, new ChopstickMonitor(i).length());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testBadLength() {
        new ChopstickMonitor(1);
    }

    @Test
    public final void testGetChopsticks() throws InterruptedException {
        ChopstickMonitor monitor = new ChopstickMonitor(4);
        ChopstickMonitorTestThread cmtt = new ChopstickMonitorTestThread(monitor, 0, 1);
        cmtt.start();
        assertTrue("Can acquire open chopsticks.",
                monitor.claimChopsticks("Test Assertion", 2, 3, 10));
        while (!cmtt.hasLock) {
            Thread.sleep(10);
        }
        assertFalse("Can not acquire used chopsticks.",
                monitor.claimChopsticks("Test Assertion", 0, 1, 10));
        assertFalse("Can not acquire used chopsticks.",
                monitor.claimChopsticks("Test Assertion", 2, 1, 10));
        assertFalse("Can not acquire used chopsticks.",
                monitor.claimChopsticks("Test Assertion", 2, 0, 10));
        cmtt.StopRunning();
        while (cmtt.hasLock) {
            Thread.sleep(10);
        }
        assertTrue("Can acquire released chopsticks.",
                monitor.claimChopsticks("Test Assertion", 0, 1, 10));
    }
}
