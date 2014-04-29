package edu.bu.cs342.p03;

import java.io.PrintStream;
import java.util.Iterator;

/**
 * An Open-Address implementation of CollectionHash.
 * 
 * @author Michael Donnelly
 * 
 * @param <E>
 *            Type Type for elements in the collection
 */
public class OpenAddressHash<E> extends CollectionHash<E> {

    /**
     * Distinguishes probing strategies
     * 
     */
    static enum ProbeType {
        QUADRATIC, LINEAR
    }

    /**
     * An iterator which provides quadratic probing for a set of buckets, then
     * degrades to linear probing when quadratic probing is no longer effective.
     * 
     */
    class Probe implements Iterator<Integer>, Iterable<Integer> {

        private int startValue;
        private int currentBase;
        private int bucketCount;
        private int lastIndex;
        private boolean[] visited;
        private ProbeType probeType;

        /**
         * Creates a probe of possible bucket indices for storing item.
         * 
         * @param item
         *            Object The item to be stored.
         * @param buckets
         *            int The number of buckets in the hash.
         */
        public Probe(Object item, int buckets) {
            this.bucketCount = buckets;
            this.startValue = Math.abs(item.hashCode()) % buckets;
            this.currentBase = 0;
            this.lastIndex = -1;
            this.visited = new boolean[this.bucketCount];
            this.probeType = ProbeType.QUADRATIC;
        }

        @Override
        public Iterator<Integer> iterator() {
            return this;
        }

        /**
         * Updates the index via Quadratic Probing
         */
        private void quadraticProbe() {
            this.lastIndex = ((this.startValue + (this.currentBase * this.currentBase++)) % this.bucketCount);
        }

        /**
         * Updates the index via Linear Probing
         * 
         * This probe skips indices already suggested by the Quadratic Probe.
         */
        private void linearProbe() {
            for (; this.visited[this.lastIndex % this.bucketCount]; this.lastIndex = ++this.lastIndex
                    % this.bucketCount)
                ;
        }

        /**
         * Returns true as long as all buckets have not been visited.
         */
        @Override
        public boolean hasNext() {
            for (int i = 0; i < this.visited.length; ++i) {
                if (!this.visited[i]) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Returns the next index.
         * 
         * At first, Quadratic Probing is used. Once the Quadratic algorithm
         * becomes ineffective, the iterator degrades to linear probing.
         */
        @Override
        public Integer next() {
            switch (this.probeType) {
            case QUADRATIC:
                this.quadraticProbe();
                if (this.currentBase > (this.bucketCount / 2)) {
                    this.probeType = ProbeType.LINEAR;
                }
                break;
            case LINEAR:
                this.linearProbe();
                break;
            default:
                throw new UnsupportedOperationException("Unknown Probe Type");
            }
            this.visited[this.lastIndex] = true;
            return this.lastIndex;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * This CollectionHash is equal to any CollectionHash which has the same
     * size and contains all of the same elements.
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (this == o) {
            return true;
        }
        try {
            CollectionHash<E> test = (CollectionHash<E>) o;
            if (this.size() != test.size()) {
                return false;
            }
            for (int i = 0; i < this.buckets.length; ++i) {
                if (null != this.buckets[i]) {
                    if (test.search((E) this.buckets[i]) == -1) {
                        return false;
                    }
                }
            }
        } catch (ClassCastException ex) {
            return false;
        }
        return true;
    }

    public int initialSize;
    private Object[] buckets;
    private boolean[] usedBuckets;
    private int count;

    public OpenAddressHash() {
        this(31);
    }

    /**
     * An Open-Address implementation of CollectionHash.
     * 
     * @param size
     *            int The number of buckets to allocate.
     */
    public OpenAddressHash(int size) {
        this.initialSize = size;
        this.buckets = new Object[this.initialSize];
        this.usedBuckets = new boolean[this.initialSize];
        this.count = 0;
    }

    /**
     * Get a #Probe for the specified item.
     * 
     * @param item
     *            E the item which will be added to the hash
     * @return Probe An Iterable Iterator for Integer which lists potentially
     *         available buckets.
     */
    protected Probe getProbe(E item) {
        return new Probe(item, this.initialSize);
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public boolean add(E item) {
        for (int i : this.getProbe(item)) {
            if (null == this.buckets[i]) {
                this.buckets[i] = item;
                this.usedBuckets[i] = true;
                ++this.count;
                return true;
            } else {
                System.err.printf("Bucket %d in use%n", i); // Print a warning
                                                            // as per the
                                                            // instructions
            }
        }
        return (this.search(item) != -1);
    }

    /**
     * 
     */
    @Override
    public E delete(E item) {
        E result = null;
        int i;
        while ((i = this.search(item)) != -1) {
            result = item;
            this.buckets[i] = null;
            --this.count;
        }
        return result;
    }

    @Override
    public void showHash(PrintStream output) {
        for (int i = 0; i < this.buckets.length; ++i) {
            output.printf("% 3d\t%s%n", i + 1, this.buckets[i]);
        }
    }

    @Override
    public int traceSearch(E item, PrintStream output) {
        for (int i : this.getProbe(item)) {
            if (item.equals(this.buckets[i])) {
                output.printf("Found %s at bucket %d%n", item, i);
                return i;
            } else {
                if (this.usedBuckets[i]) {
                    output.printf("Bucket %d does not contain %s%n", i, item);
                } else {
                    output.printf("Chain exhausted at bucket %d%n", i);
                    break;
                }
            }
        }
        output.println("Item not found.");
        return -1;
    }
}
