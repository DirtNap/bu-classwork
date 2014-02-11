package edu.bu.cs342.hw02;

/**
 * 
 * @author Michael Donnelly
 * 
 *         A forward-only linked list node for Integers.
 * 
 */
class IntegerLinkedListNode {
    private final Integer payload; // The immutable value encapsulated in this
                                   // node.
    private IntegerLinkedListNode next; // A reference to the next element in
                                        // the linked list.

    /**
     * 
     * @param payload
     *            Integer Value of the list element.
     */
    IntegerLinkedListNode(Integer payload) {
        this.payload = payload;
        this.next = null;
    }

    /**
     * 
     * @return Integer the value of the list element;
     */
    Integer getValue() {
        return this.payload;
    }

    /**
     * 
     * @return IntegerLinkedListNode the next node in the linked list
     */
    IntegerLinkedListNode getNext() {
        return this.next;
    }

    /**
     * 
     * @param next
     *            IntegerLinkedListNode the next node in the linked list
     * @return IntegerLinkedListNode the previous value for the next node
     */
    IntegerLinkedListNode setNext(IntegerLinkedListNode next) {
        IntegerLinkedListNode result = this.next;
        this.next = next;
        return result;
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }
}
