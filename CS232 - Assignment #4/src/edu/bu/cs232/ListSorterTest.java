package edu.bu.cs232;

import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class ListSorterTest extends ListSorter {
	ShoppingListItem[] baseArray;
	private ShoppingListItem[] testArray;
	private ShoppingListItem[] emptyArray;
	private ShoppingListItem[] sparseArray;
	
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

	@Test
	public void testBubbleSort() {
		Arrays.sort(this.baseArray);
		this.bubbleSort(testArray);
		assertArrayEquals(this.baseArray, this.testArray);
	}
	@Test
	public void testBubbleEmpty() {
		this.bubbleSort(this.emptyArray);
	}
	@Test
	public void testBubbleSparse() {
		this.bubbleSort(this.sparseArray);
		assertNull(this.sparseArray[this.sparseArray.length - 1]);
	}

	@Test
	public void testQuickSort() {
		Arrays.sort(this.baseArray);
		this.quickSort(this.testArray);
		assertArrayEquals(this.baseArray, this.testArray);
	}
	@Test
	public void testQuickEmpty() {
		this.quickSort(this.emptyArray);
	}
	@Test
	public void testQuickSparse() {
		this.quickSort(this.sparseArray);
		assertNull(this.sparseArray[this.sparseArray.length - 1]);
	}

	@Test
	public void testSelectionSort() {
		Arrays.sort(this.baseArray);
		this.selectionSort(this.testArray);
		assertArrayEquals(this.baseArray, this.testArray);
	}
	@Test
	public void testSelectionEmpty() {
		this.selectionSort(this.emptyArray);
	}
	@Test
	public void testSelectionSparse() {
		this.selectionSort(this.sparseArray);
		assertNull(this.sparseArray[this.sparseArray.length - 1]);
	}


	@Test
	public void testDefaultSort() {
		Arrays.sort(this.baseArray);
		this.defaultSort(this.testArray);
		assertArrayEquals(this.baseArray, this.testArray);
	}
	@Test(expected=NullPointerException.class)
	public void testDefaultEmpty() {
		this.defaultSort(this.emptyArray);
	}
	@Test(expected=NullPointerException.class)
	public void testDefaultSparse() {
		this.defaultSort(this.sparseArray);
	}

	@Override
	public void doSorting(ShoppingListItem[] theList) {
		this.defaultSort(theList);
	}
}
