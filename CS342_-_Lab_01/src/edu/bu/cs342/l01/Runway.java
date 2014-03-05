package edu.bu.cs342.l01;

public class Runway {

    private int claimEnd = 0;

    public Runway() {
        // TODO Auto-generated constructor stub
    }

    public boolean claim(int timeAt, int timeFor) {
        if (timeAt > this.claimEnd) {
            this.claimEnd = timeAt + timeFor;
            return true;
        } else {
            return false;
        }
    }
}
