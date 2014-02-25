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
            this.setNext(next);
            this.setPrevious(previous);
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

    public int length() {
        return this.itemCount;
    }

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
