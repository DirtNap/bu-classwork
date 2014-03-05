package edu.bu.cs342.l01;

import java.nio.BufferOverflowException;

public class AirplaneQueue {

    int nextWrite; // next available write location
    int nextRead; // next available read location
    int initialSize; // Size of the internal array
    int currentSize; // number of elements currently enqueued

    private Airplane[] queue; // Contains the queue data

    /**
     * A queue of Airplane objects
     * @param size the internal maximum size of the queue
     */
    public AirplaneQueue(int size) {
        this.initialSize = size;
        this.nextWrite = 0;
        this.nextRead = 0;
        this.currentSize = 0;
        this.queue = new Airplane[this.initialSize];
    }

    /**
     * The number of items currently in the queue
     * @return int the number of items currently in the queue
     */
    public int length() {
        return this.currentSize;
    }

    /**
     * Add a plain to the queue
     * @param plane the plane to enqueue
     * @throws BufferOverflowException when no room is left in the queue
     */
    public void enqueue(Airplane plane) {
        if (this.queue.length == this.length()) {
            throw new BufferOverflowException();
        }
        this.queue[this.nextWrite] = plane;
        // Rotate the queue for re-use
        this.nextWrite = (this.nextWrite + 1) % this.initialSize;
        ++this.currentSize;
    }

    /**
     * Return the Airplane at the head of the queue, without dequeuing it
     * @return Airplane the Airplane at the head of the queue
     */
    public Airplane peek() {
        Airplane result = null;
        if (this.length() > 0) {
            result = this.queue[this.nextRead];
        }
        return result;
    }

    /**
     * Remove the first Airplane from the queue, and return it
     * @return Airplane the Airplane at the head of the queue
     */
    public Airplane dequeue() {
        Airplane result = this.peek();
        if (null != result) {
            // Rotate the queue for re-use
            this.nextRead = (this.nextRead + 1) % this.initialSize;
            --this.currentSize;
        }
        return result;
    }
}
