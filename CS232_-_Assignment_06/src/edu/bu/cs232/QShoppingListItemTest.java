package edu.bu.cs232;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class QShoppingListItemTest {

	@Test
	public final void testQuantity() {
		QShoppingListItem sli;
		sli = new QShoppingListItem("Lowest Priority, Higher Price", 1, 2.0d);
		assertEquals(1, sli.getQuantity());
		sli = new QShoppingListItem("Lowest Priority, Higher Price", 1, 2.0d, 4);
		assertEquals(4, sli.getQuantity());
		sli.setQuantity(2);
		assertEquals(2, sli.getQuantity());
	}

	@Test
	public void testSorting() {
		QShoppingListItem [] aSLI = new QShoppingListItem[5];
		aSLI[0] = new QShoppingListItem("Lowest Priority, Higher Price, but longer name.", 1, 2.0d);
		aSLI[1] = new QShoppingListItem("Lowest Priority, Lower Price", 1, 1.5d);
		aSLI[2] = new QShoppingListItem("Lowest Priority, Higher Price", 1, 2.0d);
		aSLI[3] = new QShoppingListItem("Higher Priority, Lowest Price", 2, 1.0d, 2);
		aSLI[4] = new QShoppingListItem("Higher Priority, Lowest Price", 2, 1.0d, 1);
		QShoppingListItem [] aCorrect = {aSLI[1], aSLI[2], aSLI[0], aSLI[4], aSLI[3]};
		Arrays.sort(aSLI);
		Assert.assertArrayEquals("Array sorted by priority, then price, then name", aCorrect, aSLI);
		ShoppingListItem sli = new ShoppingListItem(aSLI[4].getName(), aSLI[4].getPriority(), aSLI[4].getPrice());
		assertTrue("Test implied quantity comparison of parent class", aSLI[4].compareTo(sli) > 0);
	}

	
}
