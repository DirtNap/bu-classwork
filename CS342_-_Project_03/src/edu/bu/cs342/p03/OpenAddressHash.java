package edu.bu.cs342.p03;

import java.io.PrintStream;
import java.util.Iterator;

public class OpenAddressHash<E> extends CollectionHash<E> {

    class probe implements Iterator<Integer>, Iterable<Integer> {

        private int startValue;
        private int currentBase;
        private int bucketCount;

        public probe(Object item, int buckets) {
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
            return (this.currentBase <= this.bucketCount);
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

    private class OpenAddressContainer<T> {
        private T payload;
        private boolean available;
        private boolean used;

        public OpenAddressContainer() {
            this(null);
        }

        public OpenAddressContainer(T item) {
            this.setPayload(item);
            this.setAvailable((null == this.getPayload()));
            this.setUsed(!this.isAvailable());
        }

        public T getPayload() {
            return this.payload;
        }

        public void setPayload(T payload) {
            this.payload = payload;

        }

        public boolean isAvailable() {
            return this.available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public boolean isUsed() {
            return this.used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (this == o) {
            return true;
        }
        try {
            OpenAddressHash test = (OpenAddressHash) o;
            // TODO Perform OpenAddressHash specific comparison
        } catch (ClassCastException ex) {
            return false;
        }
        return false;
    }

    public int initialSize;

    public OpenAddressHash() {
        this(31);
    }

    public OpenAddressHash(int size) {
        this.initialSize = size;
    }

    @Override
    public boolean add(E item) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public E delete(E item) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void showHash(PrintStream output) {
        // TODO Auto-generated method stub

    }

    @Override
    public int traceSearch(E item, PrintStream output) {
        // TODO Auto-generated method stub
        return -1;
    }
}
