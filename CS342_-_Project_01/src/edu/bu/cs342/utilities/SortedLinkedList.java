package edu.bu.cs342.utilities;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A double linked list which stores elements in ascending order.
 * 
 * @author Michael Donnelly
 * 
 * @param <E>
 *            Type Type of list elements, implementing {@codeComparable<E>}
 * @see java.lang.Comparable
 */
public class SortedLinkedList<E extends Comparable<E>> implements Iterable<E> {

    /**
     * A double linked list node.
     * 
     * The node is mutable, while the enclosed payload is not.
     * 
     * @author Michael Donnelly
     * 
     * @param <T>
     *            Type Type Node payload, implementing {@codeComparable<E>}
     */
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
            this.setNext(next);
            this.setPrevious(previous);
        }

        /**
         * Get the node following this node.
         * 
         * @return SortedLinkedListNode<T> The next node in the list, or null if
         *         this is the last node.
         */
        public SortedLinkedListNode<T> getNext() {
            return this.nextNode;
        }

        /**
         * Set the node following this node.
         * 
         * @param newNext
         *            SortedLinkedListNode<T> The node following this node.
         * @return SortedLinkedListNode<T> The previous value of
         *         {@code getNext()}
         */
        public SortedLinkedListNode<T> setNext(SortedLinkedListNode<T> newNext) {
            SortedLinkedListNode<T> result = this.getNext();
            this.nextNode = newNext;
            return result;
        }

        /**
         * Get the node previous to this node.
         * 
         * @return SortedLinkedListNode<T> The previous node in the list, or
         *         null if this is the first node.
         */
        public SortedLinkedListNode<T> getPrevious() {
            return this.previousNode;
        }

        /**
         * Set the node previous to this node.
         * 
         * @param newPrevious
         *            SortedLinkedListNode<T> The node previous to this node.
         * @return SortedLinkedListNode<T> The previous value of
         *         {@code getPrevious()}
         */
        public SortedLinkedListNode<T> setPrevious(SortedLinkedListNode<T> newPrevious) {
            SortedLinkedListNode<T> result = this.getPrevious();
            this.previousNode = newPrevious;
            return result;
        }

        /**
         * Get the payload enclosed by this node.
         * 
         * @return T The payload the node was created with.
         */
        public T getValue() {
            return this.payload;
        }

        /**
         * Exposes {@code T.toString()}
         * 
         * @see Object#toString()
         */
        @Override
        public String toString() {
            return this.getValue().toString();
        }

        /**
         * Exposes {@code T.compareTo(T)}
         * 
         * @see java.lang.Comparable#compareTo(Object)
         */
        @Override
        public int compareTo(T o) {
            return this.payload.compareTo(o);
        }

        /**
         * Based upon {@code T.equals(Object)}
         * 
         * @see Object#equals(Object)
         */
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

        /**
         * Exposes {@code T.hashCode}
         * 
         * @see Object#hashCode()
         */
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

    private SortedLinkedListNode<E> first; // head of the list
    private SortedLinkedListNode<E> last; // tail of the list
    private int itemCount;
    private boolean allowDuplicates;

    public SortedLinkedList() {
        this(true);
    }

    /**
     * A linked list stored in ascending order.
     * 
     * @param allowDuplicates
     *            boolean Whether the list stores multiple copies of equivalent
     *            elements. (default: true)
     */
    public SortedLinkedList(boolean allowDuplicates) {
        this.first = this.last = null;
        this.itemCount = 0;
        this.allowDuplicates = allowDuplicates;
    }

    /**
     * Finds the index of the element in the list.
     * 
     * @param element
     *            E the payload to search the list for.
     * @return int The 0-indexed count of the item, or -1 if the item is not
     *         found.
     */
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

    /**
     * Indicates whether the element is a member of the list.
     * 
     * @param element
     *            E the payload to search the list for.
     * @return boolean true if the element appears at least once in the list,
     *         otherwise false.
     */
    public boolean contains(E element) {
        return (this.indexOf(element) != -1);
    }

    /**
     * Add an item to the list, in ascending order.
     * 
     * @param payload
     *            E The item to be added.
     * @return boolean whether the item was added. May return false if the
     *         payload is null, or if the payload is a duplicate and duplicates
     *         are not permitted.
     */
    public boolean addItem(E payload) {
        SortedLinkedListNode<E> node = null, currentNode = null, previousNode = null;
        if (null == payload) {
            return false;
        }
        currentNode = this.first;
        while (null != currentNode && currentNode.compareTo(payload) < 0) {
            currentNode = currentNode.getNext();
        }
        if (null == currentNode) {
            previousNode = this.last;
        } else {
            if (!this.allowDuplicates && currentNode.getValue().equals(payload)) {
                return false;
            }
            previousNode = currentNode.getPrevious();
        }
        node = new SortedLinkedListNode<>(payload, currentNode, previousNode);
        if (null == node.getNext()) {
            this.last = node;
        } else {
            node.getNext().setPrevious(node);
        }
        if (null == node.getPrevious()) {
            this.first = node;
        } else {
            node.getPrevious().setNext(node);
        }
        ++this.itemCount;
        return true;
    }

    /**
     * The length of the linked list
     * 
     * @return int the length of the linked list.
     */
    public int length() {
        return this.itemCount;
    }

    // Remove the node, and splice the list back together.
    private void remove(SortedLinkedListNode<E> node) {
        SortedLinkedListNode<E> prev = node.getPrevious(), next = node.getNext();
        if (null == next) {
            this.last = prev;
        } else {
            next.setPrevious(prev);
        }
        if (null == prev) {
            this.first = next;
        } else {
            prev.setNext(next);
        }
    }

    /**
     * Remove all instances of payload from the linked list.
     * 
     * @param payload
     *            E the element to search for.
     * @return true if elements were removed.
     */
    public boolean removeItem(E payload) {
        SortedLinkedListNode<E> currentNode = this.first;
        boolean found = false;
        while (null != currentNode) {
            if (currentNode.getValue().equals(payload)) {
                found = true;
                this.remove(currentNode);
            } else {
                if (found) {
                    break;
                }
            }
            currentNode = currentNode.getNext();
        }
        return found;
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
