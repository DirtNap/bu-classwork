package edu.bu.cs342.hw02;

import java.util.Random;

/**
 * 
 * @author Michael Donnelly Represents a forward-only linked list of integers.
 */
public class IntegerLinkedList {
    private IntegerLinkedListNode first;
    private int count;

    public IntegerLinkedList() {
        this.first = null;
        this.count = 0;
    }

    /**
     * 
     * @param index
     *            int the position of the node to retrieve
     * @return IntegerLinkedListNode the node at the specified position.
     * @exception IllegalArgumentException
     *                when index is not a valid list index.
     * @exception IndexOutOfBoundsException
     *                when index is beyond the last element of the list.
     */
    private IntegerLinkedListNode getNode(int index) {
        if (index < 0) {
            throw new IllegalArgumentException();
        }
        if (index >= this.length()) {
            throw new IndexOutOfBoundsException(String.format(
                    "Index %d outside of list with %d items", index, this.length()));
        }
        IntegerLinkedListNode result = this.first;
        for (int i = 0; i < index; ++i) {
            result = result.getNext();
        }
        return result;
    }

    public int length() {
        return this.count;
    }

    /**
     * Add a new integer to the list
     * 
     * @param payload
     *            Integer the integer to be added.
     */
    public void addItem(Integer payload) {
        IntegerLinkedListNode incoming = new IntegerLinkedListNode(payload);
        if (null == this.first) {
            this.first = incoming;
        } else {
            this.getNode(this.length() - 1).setNext(incoming);
        }
        ++this.count;
    }

    /**
     * Remove the final element of the array.
     */
    public void deleteLast() {
        switch (this.length()) {
        case 0: // The array is empty, nothing to remove.
            break;
        case 1: // First and last elements are the same.
            this.first = null;
            this.count = 0;
            break;
        default: // Remove the next link from the penultimate element.
            this.getNode(this.length() - 2).setNext(null);
            --this.count;
        }
    }

    @Override
    public String toString() {
        if (0 == this.length()) {
            return "<>";
        }
        StringBuilder result = new StringBuilder("<");
        String delimiter = "";
        IntegerLinkedListNode current = this.first;
        do {
            result.append(String.format("%s%s", delimiter, current));
            delimiter = ", ";
            current = current.getNext();
        } while (current != null);
        result.append(">");
        return result.toString();
    }

    public static void main(String[] args) {
        Random prng = new Random();
        int newValue;
        IntegerLinkedList self = new IntegerLinkedList();
        for (int i = 0; i < 10; ++i) {
            newValue = prng.nextInt(100);
            System.out.printf("Current list value is %s%n", self);
            System.out.printf("Adding %d to list%n", newValue);
            self.addItem(newValue);
        }
        System.out.printf("Full list value: %s%n", self);
        System.out.println("Deleting the last item from the list.");
        self.deleteLast();
        System.out.printf("Full list value: %s%n", self);
    }
}
