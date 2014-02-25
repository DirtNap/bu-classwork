package edu.bu.cs342.utilities;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedLinkedList<E extends Comparable<E>> implements Iterable<E> {

    public class SortedLinkedListNode<T extends Comparable<T>> implements Comparable<T> {
        private final T payload;
        private SortedLinkedListNode<T> nextNode;
        private SortedLinkedListNode<T> previousNode;

        SortedLinkedListNode(T payload) {
            this(payload, null, null);
        }

        SortedLinkedListNode(T payload, SortedLinkedListNode<T> next,
                SortedLinkedListNode<T> previous) {
            this.payload = payload;
        }

        public SortedLinkedListNode<T> getNext() {
            return this.nextNode;
        }

        public SortedLinkedListNode<T> setNext(SortedLinkedListNode<T> newNext) {
            SortedLinkedListNode<T> result = this.getNext();
            this.nextNode = newNext;
            return result;
        }

        public SortedLinkedListNode<T> getPrevious() {
            return this.previousNode;
        }

        public SortedLinkedListNode<T> setPrevious(SortedLinkedListNode<T> newPrevious) {
            SortedLinkedListNode<T> result = this.getPrevious();
            this.previousNode = newPrevious;
            return result;
        }

        public T getValue() {
            return this.payload;
        }

        @Override
        public String toString() {
            return this.getValue().toString();
        }

        @Override
        public int compareTo(T o) {
            return this.payload.compareTo(o);
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
                @SuppressWarnings("unchecked")
                SortedLinkedListNode<T> test = (SortedLinkedListNode<T>) o;
                return this.getValue().equals(test.getValue());
            } catch (ClassCastException ex) {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return this.getValue().hashCode();
        }
    }

    private class SortedListIterator<T extends Comparable<T>> implements Iterator<T> {

        private SortedLinkedListNode<T> nextNode;

        public SortedListIterator(SortedLinkedListNode<T> first) {
            this.nextNode = first;
        }

        @Override
        public boolean hasNext() {
            return (null != this.nextNode);
        }

        @Override
        public T next() {
            T result = null;
            try {
                result = this.nextNode.getValue();
            } catch (NullPointerException ex) {
                throw new NoSuchElementException();
            }
            this.nextNode = this.nextNode.getNext();
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private SortedLinkedListNode<E> first;
    private SortedLinkedListNode<E> last;
    private int itemCount;
    private boolean allowDuplicates;

    public SortedLinkedList() {
        this(true);
    }

    public SortedLinkedList(boolean allowDuplicates) {
        this.first = this.last = null;
        this.itemCount = 0;
        this.allowDuplicates = allowDuplicates;
    }

    public int indexOf(E element) {
        int result = -1;
        int ct = 0;
        for (E item : this) {
            if (item.equals(element)) {
                result = ct;
                break;
            }
            ++ct;
        }
        return result;
    }

    public boolean contains(E element) {
        return (this.indexOf(element) != -1);
    }

    public boolean addItem(E payload) {
        SortedLinkedListNode<E> node, currentNode, previousNode;
        node = new SortedLinkedListNode<>(payload);
        if (null == this.last) {
            this.first = this.last = node;
        } else {
            currentNode = this.first;
            while (null != currentNode && currentNode.compareTo(node.getValue()) < 0) {
                currentNode = currentNode.getNext();
            }
            if (null == currentNode) {
                node.setPrevious(this.last);
                this.last.setNext(node);
                this.last = node;
            } else {
                if (!this.allowDuplicates && currentNode.getValue().equals(node.getValue())) {
                    return false;
                }
                previousNode = currentNode.getPrevious();
                if (null == previousNode) {
                    this.first = node;
                } else {
                    previousNode.setNext(node);
                }
                node.setPrevious(previousNode);
                node.setNext(currentNode);
                currentNode.setPrevious(node);
            }
        }
        ++this.itemCount;
        return true;
    }

    public int length() {
        return this.itemCount;
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
            @SuppressWarnings("unchecked")
            SortedLinkedList<E> test = (SortedLinkedList<E>) o;
            if (this.length() != test.length()) {
                return false;
            }
            Iterator<E> testIterator = test.iterator();
            for (E item : this) {
                if (!item.equals(testIterator.next())) {
                    return false;
                }
            }
        } catch (ClassCastException ex) {
            return false;
        }
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return new SortedListIterator<E>(this.first);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        String delimiter = "";
        for (E element : this) {
            sb.append(delimiter);
            delimiter = " <-> ";
            sb.append(element.toString());
        }
        sb.append("]");
        return sb.toString();
    }
}
