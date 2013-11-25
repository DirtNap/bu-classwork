package edu.bu.cs232;

import static org.junit.Assert.*;

import org.junit.Test;

public class LListCollectionTest extends LListTestBase {

	@Test
	public final void testToObjectArray() {
		Object[] oArray = this.testLList.toArray();
		for (int i = 0; i < oArray.length; ++i) {
			assertEquals(this.testArray[i], oArray[i]);
		}
	}

	@Test
	public final void testToArray() {
		String[] sArray = new String[this.testArray.length];
		sArray = this.testLList.toArray(sArray);
		assertArrayEquals(this.testArray, sArray);
	}
	
	
	@Test
	public final void testToArrayOversize() {
		int testSize = this.testArray.length * 2;
		String[] sArray = new String[testSize];
		for (int i = 0; i <= sArray.length; ++i) {
			sArray[i] = Integer.toString(i);
		}
		sArray = this.testLList.toArray(sArray);
		for (int i = 0; i < this.testArray.length; ++i) {
			assertEquals(this.testArray[i], sArray[i]);
		}
		assertEquals(null, sArray[this.testArray.length]);
		for (int i = this.testArray.length + 1; i < sArray.length; ++i) {
			assertEquals(sArray[i], Integer.toString(i));
		}
	}

	@Test
	public final void testToArrayEmpty() {
		String[] sArray = new String[0];
		assertEquals(0, sArray.length);
		sArray = this.testLList.toArray(sArray);
		assertEquals(this.testArray.length, sArray);
		assertArrayEquals(this.testArray, sArray);
	}
	
}
