package edu.bu.cs342.p03;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * A hash which is suitable for storing a collection of items.
 * 
 * @author Michael Donnelly
 * 
 * @param <E>
 */
public abstract class CollectionHash<E> {
    protected static class DummyOutputStream extends OutputStream {
        @Override
        public void write(int b) throws IOException {
            return;
        }
    }

    /**
     * Attempt to add an item to the hash.
     * 
     * @return boolean whether there was space for the item in the hash table.
     */
    public abstract boolean add(E item);

    /**
     * Delete all equivalent items from the hash.
     * 
     * @param item
     *            E The item to delete.
     * @return E The deleted item.
     */
    public abstract E delete(E item);

    /**
     * Display the buckets of the hash, and their contents.
     * 
     * @param output
     */
    public abstract void showHash(PrintStream output);

    /**
     * Search for an item, displaying the steps required to locate that item.
     * 
     * @param item
     *            E The item to find.
     * @param output
     *            PrintStream The stream to write the trace to.
     * @return int The index of the bucket containing the item, or -1 if it was
     *         not found.
     */
    public abstract int traceSearch(E item, PrintStream output);

    /**
     * The number of elements in the array.
     */
    public abstract int size();

    /**
     * Returns the result of {@code CollectionHash#search(Object)} on item > -1
     */
    public boolean contains(E item) {
        return (-1 != this.search(item));
    }

    /**
     * Identical to {@link #traceSearch(Object, PrintStream)} with a dummy
     * PrintStream
     */
    public int search(E item) {
        return this.traceSearch(item, new PrintStream(new DummyOutputStream()));
    }

}
