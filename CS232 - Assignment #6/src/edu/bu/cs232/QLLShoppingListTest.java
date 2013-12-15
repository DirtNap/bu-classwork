package edu.bu.cs232;

import static org.junit.Assert.*;

import org.junit.Test;

import java.text.NumberFormat;

import org.junit.Before;

public class QLLShoppingListTest {
	public static final double ALLOWABLE_FLOAT_VARIANCE = 1.0e-10d;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testShoppingList() {
		QLLShoppingList sl;
		sl = new QLLShoppingList(10);
		QShoppingListItem [] sli = {new QShoppingListItem("name", 1, 2.20d)};
		sl = new QLLShoppingList(sli);
		sl = new QLLShoppingList();
		assertEquals("Testing default size in constructor", 0, sl.length());
	}

	@Test
	public void testLength() {
		QLLShoppingList sl;
		sl = new QLLShoppingList(10);
		assertEquals(0, sl.length());
		QShoppingListItem [] sli = {new QShoppingListItem("name", 1, 2.20d, 1)};
		sl = new QLLShoppingList(sli);
		assertEquals(1, sl.length());
	}
	@Test
	public void testPut() {
		QLLShoppingList sl = new QLLShoppingList(3);
		QShoppingListItem sli1 = new QShoppingListItem("1", 1, 1.0d);
		QShoppingListItem sli2 = new QShoppingListItem("2", 2, 2.0d);
		QShoppingListItem sli3 = new QShoppingListItem("3", 3, 3.0d);
		sl.put(sli3);
		assertEquals("First item is entered at index 0", sli3, sl.get(0));
		sl.put(sli2);
		sl.put(sli1);
		assertEquals("Sorted item is entered at index 0", sli1, sl.get(0));
		assertEquals("Checking length", 3, sl.length());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testPutDuplicate() {
		QLLShoppingList sl = new QLLShoppingList(3);
		QShoppingListItem sli1 = new QShoppingListItem("1", 1, 1.0d);
		sl.put(sli1);
		sl.put(sli1);
	}
	@Test
	public void testGet() {
		QLLShoppingList sl = new QLLShoppingList(3);
		QShoppingListItem sli1 = new QShoppingListItem("1", 1, 1.0d);
		QShoppingListItem sli2 = new QShoppingListItem("2", 2, 2.0d);
		QShoppingListItem sli3 = new QShoppingListItem("3", 3, 3.0d);
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
		QLLShoppingList sl1 = new QLLShoppingList(3);
		QLLShoppingList sl2 = new QLLShoppingList(3);
		QShoppingListItem sli1 = new QShoppingListItem("1", 1, 1.0d);
		QShoppingListItem sli2 = new QShoppingListItem("2", 2, 2.0d);
		QShoppingListItem sli3 = new QShoppingListItem("3", 3, 3.0d);
		sl1.put(sli1);
		sl1.put(sli2);
		sl1.put(sli3);
		sl2.put(sli3);
		sl2.put(sli2);
		sl2.put(sli1);
		assertTrue("Testing equality of different shopping lists with the same items", sl1.equals(sl2));
	}
	@Test
	public void testTotal() {
		int testCount = 10;
		QLLShoppingList sl = new QLLShoppingList(testCount);
		double total =  0.0d;
		for (int i = 0; i < testCount; ++i) {
			sl.put(new QShoppingListItem(Integer.toString(i), i, i));
			total += i;
		}
		assertEquals(total, sl.getTotal(), ShoppingListTest.ALLOWABLE_FLOAT_VARIANCE);
	}
	@Test
	public void testIterator() {
		QShoppingListItem[] items = new QShoppingListItem[] {
				new QShoppingListItem("1", 1, 1.0d),
				new QShoppingListItem("2", 2, 2.0d),
				new QShoppingListItem("3", 3, 3.0d),
				};
		int ct = -1;
		for (QShoppingListItem i: new QLLShoppingList(items)) {
			assertTrue(items[++ct].equals(i)); 
		}
		assertEquals("Test that we made it through all items", 2, ct);
	}
	@Test
	public void testToString() {
		int testCount = 10;
		StringBuilder testString = new StringBuilder();
		NumberFormat money = NumberFormat.getCurrencyInstance();
		QLLShoppingList sl = new QLLShoppingList(testCount);
		for (int i = 0; i < testCount; ++i) {
			QShoppingListItem sli = new QShoppingListItem(Integer.toString(i), i, i); 
			sl.put(sli);
			testString.append(String.format("%d\t%s (%d@%s)%n", sli.getPriority(),
					sli.getName(), sli.getQuantity(), money.format(sli.getPrice())));

		}
		assertEquals(testString.toString(), sl.toString());
	}

}
