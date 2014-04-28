package edu.bu.cs342.p03;

import java.io.PrintStream;
import java.util.Iterator;

public class OpenAddressHash<E> extends CollectionHash<E> {

    class Probe implements Iterator<Integer>, Iterable<Integer> {

        private int startValue;
        private int currentBase;
        private int bucketCount;

        public Probe(Object item, int buckets) {
            this.bucketCount = buckets;
            this.startValue = item.hashCode() % buckets;
            this.currentBase = 0;
        }

        @Override
        public Iterator<Integer> iterator() {
            return this;
        }

        @Override
        public boolean hasNext() {
            return (this.currentBase <= Math.ceil(this.bucketCount/2.0d));
        }

        @Override
        public Integer next() {
            return (this.startValue + (this.currentBase * this.currentBase++)) % this.bucketCount;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    
    
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
                    if (test.search((E)this.buckets[i]) == -1) {
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

    public OpenAddressHash(int size) {
        this.initialSize = size;
        this.buckets = new Object[this.initialSize];
        this.usedBuckets = new boolean[this.initialSize];
        this.count = 0;
    }

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
            }
        }
        return false;
    }

    @Override
    public E delete(E item) {
        E result = null;
        Probe cleanUpProbe = this.getProbe(item);
        for (int i : this.getProbe(item)) {
            cleanUpProbe.next();
            if (item.equals(this.buckets[i])) {
                result = item;
                this.buckets[i] = null;
                --this.count;
                // If we are the end of the chain, we can free this element.
                if (cleanUpProbe.hasNext()) {
                    int next = cleanUpProbe.next();
                    if (!this.usedBuckets[next]) {
                        this.usedBuckets[i] = false;
                    }
                }
            }
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
