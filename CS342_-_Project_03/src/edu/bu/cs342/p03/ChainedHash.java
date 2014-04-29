package edu.bu.cs342.p03;

import java.io.PrintStream;

/**
 * Chained hash implementation of CollectionHash.
 * 
 * @author Michael Donnelly
 * 
 * @param <E>
 *            Type Type of the elements in the collection.
 */
public class ChainedHash<E> extends CollectionHash<E> {

    /**
     * Node to represent a chain of hash entries.
     * 
     * @param <T>
     *            Type Type of the payload
     */
    class ChainedHashListNode<T> {
        private T payload;
        private ChainedHashListNode<T> next;

        public ChainedHashListNode<T> getNext() {
            return this.next;
        }

        public void setNext(ChainedHashListNode<T> next) {
            this.next = next;
        }

        public T getPayload() {
            return this.payload;
        }

        public void setPayload(T payload) {
            this.payload = payload;
        }

        public ChainedHashListNode() {
            this(null);
        }

        public ChainedHashListNode(T item) {
            this.payload = item;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.getPayload());
            if (null != this.getNext()) {
                sb.append(String.format(" -> %s", this.getNext()));
            }
            return sb.toString();
        }

    }

    private Object[] buckets;
    private int count;

    /**
     * Get the initial node at the designated index.
     * 
     * @param index
     *            int the index of the bucket.
     * @return ChainedHashListNode<E> The first node in the bucket.
     */
    @SuppressWarnings("unchecked")
    private ChainedHashListNode<E> getBucket(int index) {
        return (ChainedHashListNode<E>) this.buckets[index];
    }

    /**
     * Get the appropriate bucket for an item.
     * 
     * @param item
     *            Object the item to store.
     * @return int the index of the bucket.
     */
    private int getBucketIndex(Object item) {
        return Math.abs(item.hashCode()) % this.buckets.length;
    }

    public ChainedHash() {
        this(31);
    }

    public ChainedHash(int bucketCount) {
        this.buckets = new Object[bucketCount];
        for (int i = 0; i < this.buckets.length; ++i) {
            this.buckets[i] = new ChainedHashListNode<E>();
        }
        this.count = 0;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public boolean add(E item) {
        ChainedHashListNode<E> node = this.getBucket(this.getBucketIndex(item));
        while (null != node.getPayload()) {
            System.err.println("Slot full, moving to next slot.");
            if (null == node.getNext()) {
                node.setNext(new ChainedHashListNode<E>());
            }
            node = node.getNext();
        }
        node.setPayload(item);
        return (this.search(item) != -1);
    }

    @Override
    public E delete(E item) {
        int index;
        E result = null;
        if ((index = this.search(item)) != -1) {
            ChainedHashListNode<E> node = this.getBucket(index);
            while (null != node) {
                if (item.equals(node.getPayload())) {
                    result = item;
                    if (null == node.getNext()) {
                        node.setPayload(null);
                    } else {
                        node.setPayload(node.getNext().getPayload());
                        node.setNext(node.getNext());
                    }
                }
                node = node.getNext();
            }
        }
        return result;
    }

    @Override
    public void showHash(PrintStream output) {
        for (int i = 0; i < this.buckets.length; ++i) {
            System.out.printf("%d\t%s%n", i + 1, this.buckets[i]);
        }
    }

    @Override
    public int traceSearch(E item, PrintStream output) {
        int index = this.getBucketIndex(item);
        output.printf("Searching bucket %d for %s%n", index, item);
        ChainedHashListNode<E> node = this.getBucket(index);
        int count = 1;
        while (null != node) {
            if (item.equals(node.getPayload())) {
                output.printf("Item found in slot %d%n", count);
                return index;
            } else {
                output.printf("Item not found in slot %d%n", count++);
                node = node.getNext();
            }
        }
        return -1;
    }
}
