package edu.bu.cs232;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShoppingListItemTest {

	public static final double ALLOWABLE_FLOAT_VARIANCE = 1.0e-10d;

	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testShoppingListItem() {
		String testName = "Test Name";
		ShoppingListItem sli = new ShoppingListItem(testName);
		sli = new ShoppingListItem(testName, 1);
		sli = new ShoppingListItem(testName, 1, 2.20d);
		String sliName = sli.getName();
		assertEquals(testName, sliName);
	}
	@Test(expected=IllegalStateException.class)
	public void testInitialization() {
		ShoppingListItem sli = new ShoppingListItem("Name");
		sli.setPriority(10);
		sli.getName();
	}
	@Test
	public void testPriority() {
		ShoppingListItem sli = new ShoppingListItem("Name", 1, 2.20d);
		assertEquals(1, sli.getPriority());
		sli.setPriority(12);
		assertEquals(12, sli.getPriority());
	}
	@Test
	public void testEquality() {
		ShoppingListItem sli = new ShoppingListItem("Name", 1, 2.20d);
		assertFalse("Testing inequality of null", sli.equals(null));
		assertTrue("Testing equality of self", sli.equals(sli));
		assertFalse("Testing inequality of equivalent string", sli.equals(new String("Name")));
		assertTrue("Testing equality of ShoppingListItem with same data",
				sli.equals(new ShoppingListItem("Name", 1, 2.20d)));
		assertTrue("Testing equality of ShoppingListItem with different data",
				sli.equals(new ShoppingListItem("Name", 2, 4.20d)));
		assertFalse("Testing inequality of ShoppingListItem with different data and name",
				sli.equals(new ShoppingListItem("Name 1", 2, 4.20d)));
		Object o = sli;
		assertTrue("Testing object equivalence (forward)", sli.equals(o));
		assertTrue("Testing object equivalence (reverse)", o.equals(sli));
	}
	@Test
	public void testCompareTo() {
		ShoppingListItem sli = new ShoppingListItem("b", 3, 2.0d);
		assertEquals("Testing equality of self", 0, sli.compareTo(sli));
		assertTrue("Sorting by priority (greater)", (sli.compareTo(new ShoppingListItem("b", 2, 2.0d)) > 0));
		assertTrue("Sorting by priority (lesser)", (sli.compareTo(new ShoppingListItem("b", 4, 2.0d)) < 0));
		assertTrue("Sorting by price (greater)", (sli.compareTo(new ShoppingListItem("b", 3, 1.0d)) > 0));
		assertTrue("Sorting by price (lesser)", (sli.compareTo(new ShoppingListItem("b", 3, 3.0d)) < 0));
		assertTrue("Sorting by name (greater)", (sli.compareTo(new ShoppingListItem("a", 3, 2.0d)) > 0));
		assertTrue("Sorting by name (lesser)", (sli.compareTo(new ShoppingListItem("c", 3, 2.0d)) < 0));
	}
	@Test
	public void testSorting() {
		ShoppingListItem [] aSLI = new ShoppingListItem[4];
		aSLI[0] = new ShoppingListItem("Lowest Priority, Higher Price, but longer name.", 1, 2.0d);
		aSLI[1] = new ShoppingListItem("Lowest Priority, Lower Price", 1, 1.5d);
		aSLI[2] = new ShoppingListItem("Lowest Priority, Higher Price", 1, 2.0d);
		aSLI[3] = new ShoppingListItem("Higher Priority, Lowest Price", 2, 1.0d);
		ShoppingListItem [] aCorrect = {aSLI[1], aSLI[2], aSLI[0], aSLI[3]};
		Arrays.sort(aSLI);
		Assert.assertArrayEquals("Array sorted by priority, then price, then name", aCorrect, aSLI);
	}
	@Test
	public void testHashCode() {
		String name = new String("Test Name");
		ShoppingListItem sli = new ShoppingListItem(name, 1, 2.20d);
		assertEquals("", sli.hashCode(), sli.hashCode());
		assertEquals("", name.hashCode(), sli.hashCode());
		sli = new ShoppingListItem(name, 2, 2.20d);
		assertEquals("", name.hashCode(), sli.hashCode());
		sli = new ShoppingListItem("New Name", 2, 2.20d);
		assertEquals("", sli.hashCode(), sli.hashCode());
		assertFalse("",(name.hashCode() == sli.hashCode()));
	}
	@Test
	public void testClone() throws CloneNotSupportedException {
		ShoppingListItem sli1, sli2;
		sli1 = new ShoppingListItem("name", 1, 1.0d);
		sli2 = (ShoppingListItem) sli1.clone();
		assertFalse("Objects are not the same", (sli1 == sli2));
		assertEquals("Objects are equal", sli1, sli2);
		assertEquals("Objects compare equally", 0, sli1.compareTo(sli2));
	}
}
