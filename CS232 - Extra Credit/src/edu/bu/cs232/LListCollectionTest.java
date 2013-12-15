package edu.bu.cs232;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

public class LListCollectionTest extends LListTestBase {

	private ArrayList<String> testArrayList;
	private ArrayList<String> testPartialArrayList;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.testArrayList = new ArrayList<String>();
		for (String s : this.testArray) {
			this.testArrayList.add(s);
		}
		this.testPartialArrayList = new ArrayList<String>(this.testArrayList);
		boolean remove = false;
		for (String s : this.testArrayList) {
			if (remove) {
				this.testPartialArrayList.remove(s);
				remove = false;
			} else {
				remove = true;
			}
		}
	}

	@Test
	public final void testAddAll() {
		this.localTestList.addAll(this.testArrayList);
		assertEquals(this.testLList, this.localTestList);
		this.localTestList.addAll(this.testArrayList);
		for (int i = 0; i < this.testArray.length; ++i) {
			assertEquals(this.testArray[i], this.localTestList.get(this.testArray.length + i));
		}
		Collections.reverse(this.testArrayList);
		this.localTestList.addAll(this.testArray.length, this.testArrayList);
		assertEquals(this.testArrayList.get(0), this.localTestList.get(this.testArray.length));
	}

	@Test
	public final void testRemoveAll() {
		this.testArrayList.removeAll(this.testPartialArrayList);
		this.testLList.removeAll(testPartialArrayList);
		assertTrue(this.testLList.containsAll(this.testArrayList));
		assertTrue(this.testArrayList.containsAll(this.testLList));
	}
	
	public final void testRetainAll() {
		this.testArrayList.retainAll(this.testPartialArrayList);
		this.testLList.retainAll(testPartialArrayList);
		assertTrue(this.testLList.containsAll(this.testPartialArrayList));
		assertTrue(this.testPartialArrayList.containsAll(this.testLList));
	}

	@Test
	public final void testToArray() {
		String[] sArray = new String[this.testArray.length];
		sArray = this.testLList.toArray(sArray);
		assertArrayEquals(this.testArray, sArray);
	}
	
	@Test
	public final void testToArrayEmpty() {
		String[] sArray = new String[0];
		assertEquals(0, sArray.length);
		sArray = this.testLList.toArray(sArray);
		assertEquals(this.testArray.length, sArray.length);
		assertArrayEquals(this.testArray, sArray);
	}
	
	@Test
	public final void testToArrayOversize() {
		int testSize = this.testArray.length * 2;
		String[] sArray = new String[testSize];
		for (int i = 0; i < sArray.length; ++i) {
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
	public final void testToObjectArray() {
		Object[] oArray = this.testLList.toArray();
		for (int i = 0; i < oArray.length; ++i) {
			assertEquals(this.testArray[i], oArray[i]);
		}
	}

	@Test
	public final void testSorting() {
		Arrays.sort(this.testArray);
		Collections.sort(this.testLList);
		for (int i = 0; i < this.testArray.length; ++i) {
			assertEquals(this.testArray[i], this.testLList.get(i));
		}
	}
}
