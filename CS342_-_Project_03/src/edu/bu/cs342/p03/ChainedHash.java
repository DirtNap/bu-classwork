package edu.bu.cs342.p03;

import java.io.PrintStream;

public class ChainedHash<E> extends CollectionHash<E> {

    class ChainedHashListNode<T> {
        private T payload;
        private ChainedHashListNode<T> next;

        public ChainedHashListNode<T> getNext() {
            return this.next;
        }

        public void setNext(ChainedHashListNode<T> next) {
            this.next = next;
        }

        public ChainedHashListNode() {
            this(null);
        }

        public ChainedHashListNode(T item) {
            this.payload = item;
        }

        public boolean add(T item) {
            if (null == this.payload) {
                this.payload = item;
                return true;
            } else {
                if (null == this.getNext()) {
                    this.setNext(new ChainedHashListNode<T>(item));
                    return true;
                } else {
                    return this.getNext().add(item);
                }
            }
        }

        public T remove(T item) {
            T result = null;
            if (null != this.payload) {
                if (this.payload.equals(item)) {
                    result = this.payload;
                    if (null == this.getNext()) {
                        this.payload = null;
                    } else {
                        this.payload = this.getNext().payload;
                        this.setNext(this.getNext());
                    }
                } else {
                    if (null != this.getNext()) {
                        return this.getNext().remove(item);
                    }
                }
            }
            return result;
        }

    }

    private Object[] buckets;

    @SuppressWarnings("unchecked")
    private ChainedHashListNode<E> getBucket(int index) {
        return (ChainedHashListNode<E>) this.buckets[index];
    }

    private int getBucketIndex(Object item) {
        return item.hashCode() % this.buckets.length;
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
            ChainedHash test = (ChainedHash) o;
            // TODO Perform ChainedHash specific comparison
        } catch (ClassCastException ex) {
            return false;
        }
        return false;
    }

    public ChainedHash(int bucketCount) {
        this.buckets = new Object[bucketCount];
        for (int i = 0; i < this.buckets.length; ++i) {
            this.buckets[i] = new ChainedHashListNode<E>();
        }
    }

    @Override
    public boolean add(E item) {
        return this.getBucket(this.getBucketIndex(item)).add(item);
    }

    @Override
    public E delete(E item) {
        return this.getBucket(this.getBucketIndex(item)).remove(item);
    }

    @Override
    public void showHash(PrintStream output) {
        // TODO Auto-generated method stub

    }

    @Override
    public int traceSearch(E item, PrintStream output) {
        // TODO Auto-generated method stub
        return 0;
    }
}
