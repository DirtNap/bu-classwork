package edu.bu.cs342.utilities;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LinkedListStackTest {

    private String[] testData;
    private LinkedListStack<String> testLinkedListStack;

    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (this == o) {
            return true;
        }
        try {
            LinkedListStackTest test = (LinkedListStackTest) o;
            // TODO Perform LinkedListStackTest specific comparison
        } catch (ClassCastException ex) {
            return false;
        }
        return false;
    }

    @Before
    public void setUp() throws Exception {
        this.testData = new String[] { "A", "B", "C", "D", "E", "F" };
        this.testLinkedListStack = new LinkedListStack<String>();
    }

    @Test
    public final void testPushAndPop() {
        for (int i = 0; i < this.testData.length; ++i) {
            this.testLinkedListStack.push(this.testData[i]);
        }
        assertEquals(this.testData.length, this.testLinkedListStack.length());
        for (int i = this.testData.length - 1; i >= 0; --i) {
            assertEquals(this.testData[i], this.testLinkedListStack.pop());
        }
        assertEquals(0, this.testLinkedListStack.length());
    }

    @Test
    public final void testLength() {
        assertEquals(0, this.testLinkedListStack.length());
        this.testLinkedListStack.push("test");
        assertEquals(1, this.testLinkedListStack.length());
        this.testLinkedListStack.pop();
        assertEquals(0, this.testLinkedListStack.length());
    }

    @Test
    public final void testContains() {
        for (int i = 0; i < this.testData.length; ++i) {
            this.testLinkedListStack.push(this.testData[i]);
        }
        for (int i = 0; i < this.testData.length; ++i) {
            assertTrue(this.testLinkedListStack.contains(this.testData[i]));
        }
        String removed = this.testLinkedListStack.pop();
        assertFalse(this.testLinkedListStack.contains(removed));
    }

    @Test
    public final void testIterator() {
        int ct = 0;
        for (; ct < this.testData.length; ++ct) {
            this.testLinkedListStack.push(this.testData[ct]);
        }
        for (String e : this.testLinkedListStack) {
            assertEquals(this.testData[--ct], e);
        }

    }
}
