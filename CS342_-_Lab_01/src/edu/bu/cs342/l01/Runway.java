package edu.bu.cs342.l01;

/**
 * Simulates an airport runway.
 * Only one plane may use the runway at a time
 */
public class Runway {

    private int claimEnd;  // When the current claim on the runway ends

    public Runway() {
      this.claimEnd = 0;
    }

    /**
     * Try to claim the runway for exclusive use
     * @param timeAt int the time at which the claim is being processed
     * @param timeFor int the time for which the runway must be reserved.
     * @return boolean true if the runway is available at timeAt, otherwise false.
     */
    public boolean claim(int timeAt, int timeFor) {
        if (timeAt > this.claimEnd) {
            this.claimEnd = timeAt + timeFor;
            return true;
        } else {
            return false;
        }
    }
}
