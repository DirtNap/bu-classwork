package edu.bu.cs342.utilities;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class SortedLinkedListTest {

    private String[] testArrayUnSorted;
    private String[] testArraySorted;
    private SortedLinkedList<String> testSortedLinkedList;
    private String unIncludedValue;

    @Before
    public void setUp() throws Exception {
        this.testArraySorted = this.testArrayUnSorted = new String[] { "B", "A", "E", "D", "F", "C" };
        this.unIncludedValue = "G";
        Arrays.sort(this.testArraySorted);
        this.testSortedLinkedList = new SortedLinkedList<String>();
        for (int i = 0; i < this.testArrayUnSorted.length; ++i) {
            this.testSortedLinkedList.addItem(this.testArrayUnSorted[i]);
        }
    }

    @Test
    public void testIndexOf() {
        for (int i = 0; i < this.testArraySorted.length; ++i) {
            assertEquals(i, this.testSortedLinkedList.indexOf(this.testArraySorted[i]));
        }
        assertEquals(-1, this.testSortedLinkedList.indexOf(this.unIncludedValue));
    }

    @Test
    public void testContains() {
        for (int i = 0; i < this.testArrayUnSorted.length; ++i) {
            assertTrue(this.testSortedLinkedList.contains(this.testArrayUnSorted[i]));
        }
        assertFalse(this.testSortedLinkedList.contains(this.unIncludedValue));
    }

    @Test
    public void testAddItem() {
        int i = 0;
        for (String s : this.testSortedLinkedList) {
            assertEquals(this.testArraySorted[i++], s);
        }
    }

    @Test
    public void testEqualsObject() {
        assertFalse(this.testSortedLinkedList.equals(new Object()));
        assertTrue(this.testSortedLinkedList.equals(this.testSortedLinkedList));
        SortedLinkedList<String> localTestSortedLinkedList = new SortedLinkedList<>();
        assertFalse(this.testSortedLinkedList.equals(localTestSortedLinkedList));
        for (int i = 0; i < this.testArrayUnSorted.length; ++i) {
            localTestSortedLinkedList.addItem(this.testArrayUnSorted[i]);
        }
        assertTrue(this.testSortedLinkedList.equals(localTestSortedLinkedList));
        localTestSortedLinkedList.addItem(this.unIncludedValue);
        assertFalse(this.testSortedLinkedList.equals(localTestSortedLinkedList));
    }

    @Test
    public void testIterator() {
        int ct = 0;
        for (@SuppressWarnings("unused")
        String s : this.testSortedLinkedList) {
            ++ct;
        }
        assertEquals(this.testArrayUnSorted.length, ct);
    }

    @Test
    public void testToString() {
        SortedLinkedList<String> localTestSortedLinkedList = new SortedLinkedList<>();
        assertEquals("[]", localTestSortedLinkedList.toString());
        assertEquals("[A <-> B <-> C <-> D <-> E <-> F]", this.testSortedLinkedList.toString());
    }
}
