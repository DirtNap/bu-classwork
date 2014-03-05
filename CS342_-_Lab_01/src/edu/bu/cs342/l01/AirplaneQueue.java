package edu.bu.cs342.l01;

public class AirplaneQueue {

    int nextWrite;
    int nextRead;
    int initialSize;
    int currentSize;

    private Airplane[] queue;

    public AirplaneQueue(int size) {
        this.initialSize = size;
        this.nextWrite = 0;
        this.nextRead = 0;
        this.currentSize = 0;
        this.queue = new Airplane[this.initialSize];
    }

    public int length() {
        return this.currentSize;
    }

    public void enqueue(Airplane plane) {
        if (this.queue.length == this.length()) {
            throw new RuntimeException("Queue Overflow");
        }
        this.queue[this.nextWrite] = plane;
        this.nextWrite = (this.nextWrite + 1) % this.initialSize;
        ++this.currentSize;
    }

    public Airplane peek() {
        if (this.length() > 0) {
            return this.queue[this.nextRead];
        } else {
            return null;
        }
    }

    public Airplane dequeue() {
        Airplane result = null;
        if (this.length() > 0) {
            result = this.queue[this.nextRead];
            this.nextRead = (this.nextRead + 1) % this.initialSize;
            --this.currentSize;
        }
        return result;
    }
}
