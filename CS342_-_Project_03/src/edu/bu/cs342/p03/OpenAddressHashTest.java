package edu.bu.cs342.p03;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class OpenAddressHashTest {

    class HashEqual {
        private final int hash;
        public final String data;

        public HashEqual(String data, int hash) {
            this.data = data;
            this.hash = hash;
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
                HashEqual test = (HashEqual) o;
                return this.data.equals(test.data);
            } catch (ClassCastException ex) {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return this.hash;
        }
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public final void testGetProbe() {
        for (int testLength = 1; testLength < 32; ++testLength) {
            boolean[] testArray = new boolean[31];
            OpenAddressHash<String> testOAHash = new OpenAddressHash<>(testArray.length);
            for (int i : testOAHash.getProbe("a")) {
                testArray[i] = true;
            }
            for (int i = 0; i < testArray.length; ++i) {
                assertTrue(testArray[i]);
            }
        }
    }

    @Test
    public final void testAdd() {
        int testSize = 31;
        OpenAddressHash<String> testOAHash = new OpenAddressHash<>(testSize);
        String testString = "test";
        testOAHash.add(testString);
        int testPosition = testOAHash.search(testString);
        assertNotEquals(-1, testPosition);
        assertEquals(testString.hashCode() % testSize, testPosition);
        assertEquals(1, testOAHash.size());
        testOAHash.add(testString);
        assertEquals(2, testOAHash.size());
    }

    @Test
    public final void testDelete() {
        int testSize = 31;
        OpenAddressHash<String> testOAHash = new OpenAddressHash<>(testSize);
        String[] testStrings = new String[] { "test1", "test2", "test3", "test4" };
        for (int i = 0; i < 5; ++i) {
            for (String testString : testStrings) {
                testOAHash.add(testString);
            }
        }
        assertEquals(testStrings.length * 5, testOAHash.size());
        for (int i = 0; i < testStrings.length; ++i) {
            assertEquals((testStrings.length - i) * 5, testOAHash.size());
            assertNotEquals(-1, testOAHash.search(testStrings[i]));
            assertEquals(testStrings[i], testOAHash.delete(testStrings[i]));
            assertEquals(-1, testOAHash.search(testStrings[i]));
        }
        assertEquals(0, testOAHash.size());
    }

    @Test
    public final void testSearch() {
        int testSize = 31;
        OpenAddressHash<HashEqual> testOAHash = new OpenAddressHash<>(testSize);
        int testHash = "test".hashCode();
        HashEqual test1 = new HashEqual("test", testHash);
        HashEqual test2 = new HashEqual("another test", testHash);
        assertEquals(-1, testOAHash.search(test1));
        testOAHash.add(test1);
        int testPosition = testOAHash.search(test1);
        assertNotEquals(-1, testPosition);
        assertEquals(test1.hashCode() % testSize, testPosition);
        testOAHash.add(test2);
        testPosition = testOAHash.search(test2);
        assertNotEquals(-1, testPosition);
        assertNotEquals(test1.hashCode() % testSize, testPosition);
        testOAHash.delete(test1);
        testOAHash.add(test2);
        testPosition = testOAHash.search(test2);
        assertNotEquals(-1, testPosition);
        assertEquals(test1.hashCode() % testSize, testPosition);
    }
}
