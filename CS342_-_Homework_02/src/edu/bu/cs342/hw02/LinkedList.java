package edu.bu.cs342.hw02;

/**
 * 
 * @author Michael Donnelly
 * 
 *         Represents a generic forward-only linked list.
 * @param <E>
 *            Type type of elements to be contained in the list.
 */
public class LinkedList<E> {
    private LinkedListNode<E> first;
    private int count;

    public LinkedList() {
        this.first = null;
        this.count = 0;
    }

    /**
     * 
     * @param index
     *            int the position of the node to retrieve
     * @return LinkedListNode the node at the specified position.
     * @exception IllegalArgumentException
     *                when index is not a valid list index.
     * @exception IndexOutOfBoundsException
     *                when index is beyond the last element of the list.
     */
    private LinkedListNode<E> getNode(int index) {
        if (index < 0) {
            throw new IllegalArgumentException();
        }
        if (index >= this.length()) {
            throw new IndexOutOfBoundsException(String.format(
                    "Index %d outside of list with %d items", index, this.length()));
        }
        LinkedListNode<E> result = this.first;
        for (int i = 0; i < index; ++i) {
            result = result.getNext();
        }
        return result;
    }

    public int length() {
        return this.count;
    }

    /**
     * Add a new item to the list
     * 
     * @param payload
     *            E the element to be added.
     */
    public void addItem(E payload) {
        LinkedListNode<E> incoming = new LinkedListNode<>(payload);
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
        case 0: // Empty list
            break;
        case 1: // First item is the same as the last
            this.first = null;
            this.count = 0;
            break;
        default: // Remove the reference to the last item from the penultimate
                 // item.
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
        LinkedListNode<E> current = this.first;
        do {
            result.append(String.format("%s%s", delimiter, current));
            delimiter = ", ";
            current = current.getNext();
        } while (current != null);
        result.append(">");
        return result.toString();
    }

    public static void main(String[] args) {
        LinkedList<String> self = new LinkedList<>();
        for (int i = 0; i < 10; ++i) {
            System.out.printf("Current list value is %s%n", self);
            System.out.printf("Adding Item %d to list%n", i);
            self.addItem(String.format("Item %d", i));
        }
        System.out.printf("Full list value: %s%n", self);
        System.out.println("Deleting the last item from the list.");
        self.deleteLast();
        System.out.printf("Full list value: %s%n", self);
    }
}
