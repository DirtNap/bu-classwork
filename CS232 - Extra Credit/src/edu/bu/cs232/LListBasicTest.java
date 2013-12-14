package edu.bu.cs232;

import static org.junit.Assert.*;

import org.junit.Test;

public class LListBasicTest extends LListTestBase {

	@Test
	public final void testSize() {
		assertEquals(0, this.localTestList.size());
		assertEquals(5, this.testLList.size());
	}

	@Test
	public final void testIsEmpty() {
		assertTrue(this.localTestList.isEmpty());
		assertFalse(this.testLList.isEmpty());
	}

	@Test
	public final void testContains() {
		assertTrue(this.testLList.contains("second"));
		assertFalse(this.testLList.contains("sixth"));
	}

	@Test
	public final void testAdd() {
		assertTrue(this.localTestList.add("test"));
		assertEquals("test", this.localTestList.get(0));
		assertTrue(this.localTestList.add("test 2"));
		assertEquals("test 2", this.localTestList.get(1));
	}

	@Test
	public final void testAddByIndex() {
		int testElement = 3;
		for (int i = 0; i < this.testArray.length; ++i) {
			if (i != testElement) {
				this.localTestList.add(this.testArray[i]);
			}
		}
		this.localTestList.add(testElement, this.testArray[testElement]);
		for (int i = 0; i < this.testArray.length; ++i) {
			assertEquals(this.testArray[i], this.localTestList.get(i));
		}
	}
	
	@Test
	public final void testPush() {
		for (int i = 0; i < this.testArray.length; ++i) {
			this.localTestList.push(this.testArray[i]);
			assertEquals(this.testArray[i], this.localTestList.get(i));
		}
	}

	@Test
	public final void testClear() {
		this.testLList.clear();
		assertEquals(this.testLList.size(), 0);
	}

	@Test
	public final void testGet() {
		for (int i = 0; i < this.testArray.length; ++i) {
			assertEquals(this.testArray[i], this.testLList.get(i));
		}
	}

	@Test
	public final void testIndexOf() {
		for (int i = 0; i < this.testArray.length; ++i) {
			assertEquals(i, this.testLList.indexOf(this.testArray[i]));
		}
	}

	@Test
	public final void testLastIndexOf() {
		String unique = "Unique";
		String repeat1 = "Repeated 1";
		String repeat2 = "Repeated 2";
		this.localTestList.add(unique);
		this.localTestList.add(repeat1);
		this.localTestList.add(repeat2);
		this.localTestList.add(repeat2);
		this.localTestList.add(repeat1);
		assertEquals(0, this.localTestList.lastIndexOf(unique));
		assertEquals(this.localTestList.indexOf(unique), this.localTestList.lastIndexOf(unique));
		assertEquals(1, this.localTestList.indexOf(repeat1));
		assertEquals(2, this.localTestList.indexOf(repeat2));
		assertEquals(3, this.localTestList.lastIndexOf(repeat2));
		assertEquals(4, this.localTestList.lastIndexOf(repeat1));
	}

}
