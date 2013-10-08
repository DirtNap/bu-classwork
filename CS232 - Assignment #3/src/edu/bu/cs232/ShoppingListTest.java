package edu.bu.cs232;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ShoppingListTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testShoppingList() {
		ShoppingList sl;
		sl = new ShoppingList(10);
		ShoppingListItem [] sli = {new ShoppingListItem("name", 1, 2.20d)};
		sl = new ShoppingList(sli);
		sl = new ShoppingList();
		assertEquals("Testing default size in constructor", 0, sl.length());
	}

	@Test
	public void testLength() {
		ShoppingList sl;
		sl = new ShoppingList(10);
		assertEquals(0, sl.length());
		ShoppingListItem [] sli = {new ShoppingListItem("name", 1, 2.20d)};
		sl = new ShoppingList(sli);
		assertEquals(1, sl.length());
	}
	@Test
	public void testEquality() {
		ShoppingList sl1 = new ShoppingList(3);
		ShoppingList sl2 = new ShoppingList(3);
		ShoppingListItem sli1 = new ShoppingListItem("1", 1, 1.0d);
		ShoppingListItem sli2 = new ShoppingListItem("2", 2, 2.0d);
		ShoppingListItem sli3 = new ShoppingListItem("3", 3, 3.0d);
		sl1.put(sli1);
		sl1.put(sli2);
		sl1.put(sli3);
		sl2.put(sli3);
		sl2.put(sli2);
		sl2.put(sli1);
		assertTrue("Testing equality of different shopping lists with the same items", sl1.equals(sl2));
	}
	@Test
	public void testIterator() {
		ShoppingListItem[] items = new ShoppingListItem[] {
				new ShoppingListItem("1", 1, 1.0d),
				new ShoppingListItem("2", 2, 2.0d),
				new ShoppingListItem("3", 3, 3.0d),
				};
		int ct = -1;
		for (ShoppingListItem i: new ShoppingList(items)) {
			assertTrue(items[++ct].equals(i)); 
		}
		assertEquals("Test that we made it through all items", 2, ct);
	}
}
