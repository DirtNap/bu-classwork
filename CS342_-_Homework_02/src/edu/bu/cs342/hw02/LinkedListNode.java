package edu.bu.cs342.hw02;

/**
 * 
 * @author Michael Donnelly
 * 
 *         A generic forward-only linked list node.
 * @param <T>
 *            Type type of the payload contained by this element.
 * 
 */
class LinkedListNode<T> {
    private final T payload;
    private LinkedListNode<T> next;

    /**
     * 
     * @param payload
     *            T Value of the list element.
     */
    LinkedListNode(T payload) {
        this.payload = payload;
        this.next = null;
    }

    /**
     * 
     * @return T the value of the list element;
     */
    T getValue() {
        return this.payload;
    }

    /**
     * 
     * @return LinkedListNode the next node in the linked list
     */
    LinkedListNode<T> getNext() {
        return this.next;
    }

    /**
     * 
     * @param next
     *            LinkedListNode the next node in the linked list
     * @return LinkedListNode the previous value for the next node
     */
    LinkedListNode<T> setNext(LinkedListNode<T> next) {
        LinkedListNode<T> result = this.next;
        this.next = next;
        return result;
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }
}
