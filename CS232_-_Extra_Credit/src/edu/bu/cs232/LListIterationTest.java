package edu.bu.cs232;

import static org.junit.Assert.*;

import java.util.ListIterator;
import org.junit.Test;

public class LListIterationTest extends LListTestBase {

	@Test
	public final void testIterator() {
		int i = 0;
		for (String s : this.testLList) {
			assertEquals(this.testArray[i++], s);
		}
	}

	@Test
	public final void testListIterator() {
		ListIterator<String> li = this.testLList.listIterator();
		assertTrue(li.hasNext());
		assertFalse(li.hasPrevious());
		for (String s : this.testLList) {
			assertEquals(s, li.next());
		}
		assertFalse(li.hasNext());
		assertTrue(li.hasPrevious());
		int i = this.testArray.length - 1;
		while (li.hasPrevious()) {
			--i;
			assertEquals(this.testArray[i], li.previous());
		}
		assertTrue(li.hasNext());
		assertFalse(li.hasPrevious());
		assertEquals(0, i);
	}

	@Test
	public final void testListIteratorIdentity() {
		ListIterator<String> li = this.testLList.listIterator(2);
		String t1, t2;
		t1 = li.next();
		t2 = li.previous();
		assertEquals(t1, t2);
	}
	
	@Test
	public final void testListIteratorIndex() {
		ListIterator<String> li;
		for (int i = this.testArray.length - 1; i > 0; --i) {
			li = this.testLList.listIterator(i);
			assertTrue(li.hasPrevious());
			for (int j = i - 1; j >= 0; --j) {
				assertTrue(li.hasPrevious());
				assertEquals(this.testArray[j], li.previous());
			}
			assertFalse(li.hasPrevious());
		}
		li = this.testLList.listIterator(0);
		assertFalse(li.hasPrevious());
	}

	@Test
	public final void testListIteratorIndexIteratorEquality() {
		ListIterator<String> li = this.testLList.listIterator();
		while (li.hasNext()) {
			assertEquals(this.testLList.get(li.nextIndex()), li.next());
		}
		while (li.hasPrevious()) {
			assertEquals(this.testLList.get(li.previousIndex()), li.previous());
		}
	}
}
