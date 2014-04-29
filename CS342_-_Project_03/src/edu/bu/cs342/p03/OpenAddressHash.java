package edu.bu.cs342.p03;

import java.io.PrintStream;
import java.util.Iterator;

public class OpenAddressHash<E> extends CollectionHash<E> {

    static enum ProbeType {
        QUADRATIC, LINEAR
    }

    class Probe implements Iterator<Integer>, Iterable<Integer> {

        private int startValue;
        private int currentBase;
        private int bucketCount;
        private int lastIndex;
        private boolean[] visited;
        private ProbeType probeType;

        public Probe(Object item, int buckets) {
            this.bucketCount = buckets;
            this.startValue = item.hashCode() % buckets;
            this.currentBase = 0;
            this.lastIndex = -1;
            this.visited = new boolean[this.bucketCount];
            this.probeType = ProbeType.QUADRATIC;
        }

        @Override
        public Iterator<Integer> iterator() {
            return this;
        }

        private void quadraticProbe() {
            this.lastIndex = ((this.startValue + (this.currentBase * this.currentBase++)) % this.bucketCount);
        }

        private void linearProbe() {
            for (; this.visited[this.lastIndex % this.bucketCount]; this.lastIndex = ++this.lastIndex
                    % this.bucketCount)
                ;
        }

        @Override
        public boolean hasNext() {
            for (int i = 0; i < this.visited.length; ++i) {
                if (!this.visited[i]) {
                    return true;
                }
            }
            return false;
        }

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
        for (int i : this.getProbe(item)) {
            if (item.equals(this.buckets[i])) {
                result = item;
                this.buckets[i] = null;
                --this.count;
                // If we are the end of the chain, we can free this element.
                Probe cleanUpProbe = this.getProbe(item);
                int next = -1;
                while (next != i && cleanUpProbe.hasNext()) {
                    next = cleanUpProbe.next();
                }
                if (cleanUpProbe.hasNext()) {
                    next = cleanUpProbe.next();
                    if (!this.usedBuckets[next]) {
                        this.usedBuckets[i] = false;
                        break; // End of chain
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
