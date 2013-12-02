package edu.bu.cs232;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ListSorterTestBase extends ListSorter {
	protected ShoppingListItem[] baseArray;
	protected ShoppingListItem[] testArray;
	protected ShoppingListItem[] emptyArray;
	protected ShoppingListItem[] sparseArray;
	
	@Before
	public void setUp() throws Exception {
		this.emptyArray = new ShoppingListItem[3];
		this.baseArray = new ShoppingListItem[] { new ShoppingListItem("Priority 5 Price 1", 5, 1.0d),
					       						  new ShoppingListItem("Priority 1 Price 2", 1, 2.0d),
					       						  new ShoppingListItem("Priority 1 Price 2 #2", 1, 2.0d),
					       						  new ShoppingListItem("Priority 4 Price 4", 4, 4.0d),
					       						  new ShoppingListItem("Priority 1 Price 5", 1, 5.0d),
					       						  new ShoppingListItem("Priority 1 Price 1", 1, 1.0d),
			};
		this.testArray = this.baseArray.clone();
		this.sparseArray = this.testArray.clone();
		this.sparseArray[3] = null;
	}

	@Test
	public void testTest() {
		assertArrayEquals(this.baseArray, this.testArray);
	}

	@Override
	public void doSorting(ShoppingListItem[] theList) {
		this.defaultSort(theList);
	}
}
