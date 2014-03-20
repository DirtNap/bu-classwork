package edu.bu.cs342.utilities;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListStack<E> implements Iterable<E> {

    class LinkedListStackNode<T> {
        public LinkedListStackNode<T> getPrevious() {
            return this.previous;
        }

        public void setPrevious(LinkedListStackNode<T> previous) {
            this.previous = previous;
        }

        public T getPayload() {
            return this.payload;
        }

        private final T payload;
        LinkedListStackNode<T> previous;

        public LinkedListStackNode(T payload) {
            this.payload = payload;
        }
    }

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

    public LinkedListStack() {
        this.size = 0;
    }

    public void push(E payload) {
        LinkedListStackNode<E> newNode = new LinkedListStackNode<>(payload);
        newNode.setPrevious(this.top);
        this.top = newNode;
        ++this.size;
    }

    public E pop() {
        if (null == this.top) {
            return null;
        }
        E result = this.top.getPayload();
        this.top = this.top.getPrevious();
        --this.size;
        return result;
    }

    public int length() {
        return this.size;
    }

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

}
