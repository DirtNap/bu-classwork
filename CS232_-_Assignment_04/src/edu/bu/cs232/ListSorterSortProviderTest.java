package edu.bu.cs232;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import edu.bu.cs232.ListSorterSortProvider.SortTypes;

public class ListSorterSortProviderTest extends ListSorterTestBase {

	public void testSorting(SortProvider<ShoppingListItem> provider) {
		Arrays.sort(this.baseArray);
		provider.doSorting(this.testArray);
		assertArrayEquals(this.baseArray, this.testArray);
		provider.doSorting(this.emptyArray);
		provider.doSorting(this.sparseArray);
		assertNull(this.sparseArray[this.sparseArray.length - 1]);
	}
	@Test
	public void testDefaultSort() {
		this.testSorting(new ListSorterSortProvider());
	}
	@Test
	public void testJavaSort() {
		this.testSorting(new ListSorterSortProvider(SortTypes.JAVA));
	}
	@Test
	public void testBubbleSort() {
		this.testSorting(new ListSorterSortProvider(SortTypes.BUBBLE));
	}
	@Test
	public void testMergeSort() {
		this.testSorting(new ListSorterSortProvider(SortTypes.MERGE));
	}
	@Test
	public void testQuickSort() {
		this.testSorting(new ListSorterSortProvider(SortTypes.QUICK));
	}
	@Test
	public void testSelecctionSort() {
		this.testSorting(new ListSorterSortProvider(SortTypes.SELECTION));
	}
}
