package edu.bu.cs232;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SortingShoppingListTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testShoppingList() {
		SortingShoppingList sl;
		sl = new SortingShoppingList(10);
		ShoppingListItem [] sli = {new ShoppingListItem("name", 1, 2.20d)};
		sl = new SortingShoppingList(sli);
		sl = new SortingShoppingList();
		assertEquals("Testing default size in constructor", 0, sl.length());
	}

	@Test
	public void testLength() {
		SortingShoppingList sl;
		sl = new SortingShoppingList(10);
		assertEquals(0, sl.length());
		ShoppingListItem [] sli = {new ShoppingListItem("name", 1, 2.20d)};
		sl = new SortingShoppingList(sli);
		assertEquals(1, sl.length());
	}
	@Test
	public void testPut() {
		SortingShoppingList sl = new SortingShoppingList(3);
		ShoppingListItem sli1 = new ShoppingListItem("1", 1, 1.0d);
		ShoppingListItem sli2 = new ShoppingListItem("2", 2, 2.0d);
		ShoppingListItem sli3 = new ShoppingListItem("3", 3, 3.0d);
		sl.put(sli3);
		assertEquals("First item is entered at index 0", sli3, sl.get(0));
		sl.put(sli2);
		sl.put(sli1);
		assertEquals("Sorted item is entered at index 0", sli1, sl.get(0));
		assertEquals("Checking length", 3, sl.length());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testPutDuplicate() {
		SortingShoppingList sl = new SortingShoppingList(3);
		ShoppingListItem sli1 = new ShoppingListItem("1", 1, 1.0d);
		sl.put(sli1);
		sl.put(sli1);
	}
	@Test
	public void testGet() {
		SortingShoppingList sl = new SortingShoppingList(3);
		ShoppingListItem sli1 = new ShoppingListItem("1", 1, 1.0d);
		ShoppingListItem sli2 = new ShoppingListItem("2", 2, 2.0d);
		ShoppingListItem sli3 = new ShoppingListItem("3", 3, 3.0d);
		sl.put(sli3);
		sl.put(sli2);
		sl.put(sli1);
		assertEquals("Sorted item is entered at index 0", sli1, sl.get(0));
		assertEquals("Get object by name", sli2, sl.get("2"));
		assertEquals("Get index by name", 0, sl.getIndexByName("1"));
		assertEquals("Checking length", 3, sl.length());
	}
	@Test
	public void testEquality() {
		SortingShoppingList sl1 = new SortingShoppingList(3);
		SortingShoppingList sl2 = new SortingShoppingList(3);
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
		for (ShoppingListItem i: new SortingShoppingList(items)) {
			assertTrue(items[++ct].equals(i)); 
		}
		assertEquals("Test that we made it through all items", 2, ct);
	}
}
