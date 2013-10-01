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
		assertEquals("Testing default size in constructor",ShoppingList.DEFAULT_ARRAY_SIZE, sl.length());
	}

	@Test
	public void testLength() {
		ShoppingList sl;
		sl = new ShoppingList(10);
		assertEquals(10, sl.length());
		ShoppingListItem [] sli = {new ShoppingListItem("name", 1, 2.20d)};
		sl = new ShoppingList(sli);
		assertEquals(1, sl.length());
	}

}
