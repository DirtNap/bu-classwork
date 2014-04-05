package edu.bu.cs342.utilities;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A linked list backed stack.
 * 
 * @author Michael Donnelly
 * 
 * @param <E>
 *            Type type of elements in the stack.
 */
public class LinkedListStack<E> implements Iterable<E> {

    /**
     * A reverse-only linked list node.
     * 
     * @author Michael Donnelly
     * 
     * @param <T>
     *            Type type of the node payload.
     */
    class LinkedListStackNode<T> {
        /**
         * Get the previous item in the stack.
         * 
         * @return LinkedListStackNode<T> the previous node.
         */
        public LinkedListStackNode<T> getPrevious() {
            return this.previous;
        }

        /**
         * Set the previous item in the stack.
         * 
         * @param previous
         *            LinkedListStackNode<T> the previous node.
         */
        public void setPrevious(LinkedListStackNode<T> previous) {
            this.previous = previous;
        }

        /**
         * Get the payload for the node.
         * 
         * @return T the payload of the node.
         */
        public T getPayload() {
            return this.payload;
        }

        private final T payload;
        LinkedListStackNode<T> previous;

        public LinkedListStackNode(T payload) {
            this.payload = payload;
        }
    }

    /**
     * An iterator for a stack.
     * 
     * @author Michael Donnelly
     * 
     * @param <T>
     *            Type the type of elements on the stack.
     */
    class LinkedListStackIterator<T> implements Iterator<T> {

        private LinkedListStackNode<T> next;

        public LinkedListStackIterator(LinkedListStackNode<T> start) {
            this.next = start;
        }

        @Override
        public boolean hasNext() {
            return (null != this.next);
        }

        @Override
        public T next() {
            T result;
            try {
                result = this.next.getPayload();
                this.next = this.next.getPrevious();
            } catch (NullPointerException ex) {
                throw new NoSuchElementException();
            }
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    LinkedListStackNode<E> top;
    int size;

    /**
     * Create a stack for storing ordered items of type E.
     */
    public LinkedListStack() {
        this.size = 0;
    }

    /**
     * Push an item on to the stack.
     * 
     * @param payload
     *            E the value to be placed on the stack.
     */
    public void push(E payload) {
        LinkedListStackNode<E> newNode = new LinkedListStackNode<>(payload);
        newNode.setPrevious(this.top);
        this.top = newNode;
        ++this.size;
    }

    /**
     * Remove the top item from the stack.
     * 
     * @return E the top item on the stack.
     */
    public E pop() {
        if (null == this.top) {
            return null;
        }
        E result = this.top.getPayload();
        this.top = this.top.getPrevious();
        --this.size;
        return result;
    }

    /**
     * The number of items on the stack.
     * 
     * @return int the number of items on the stack.
     */
    public int length() {
        return this.size;
    }

    /**
     * Checks for the presence of an item on the stack.
     * 
     * @param item
     *            E the item to check for.
     * @return boolean whether or not the item was found on the stack.
     */
    public boolean contains(E item) {
        for (E element : this) {
            if (element.equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String delimiter = "";
        StringBuilder sb = new StringBuilder();
        for (E element : this) {
            sb.append(delimiter);
            sb.append(element);
            delimiter = " -> ";
        }
        return sb.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListStackIterator<E>(this.top);
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
            LinkedListStack<String> test = (LinkedListStack<String>) o;
            if (this.length() != test.length()) {
                return false;
            }
            Iterator<E> testIterator = (Iterator<E>) test.iterator();
            for (E element : this) {
                if (!element.equals(testIterator.next())) {
                    return false;
                }
            }
            return true;
        } catch (ClassCastException ex) {
            return false;
        }
    }

}
