package edu.bu.cs232;

import java.util.Arrays;

public abstract class ListSorter {
	public void bubbleSort(ShoppingListItem[] theList) {
		boolean repeat = true;
		// If we had to change anything, sort again.
		while (repeat) {
			// We haven't changed anything yet.
			repeat = false;
			for (int i = 0; i < theList.length - 1; ++i) {
				int result = 0;
				int next = i + 1;
				if (theList[i] == null && theList[next] != null) {
					result = 1;
				} else {
					result = this.compareToWithNullsLast(theList[i], theList[next]);
				}
				if (result == 1) {
					repeat = true;
					this.exchangeItems(theList, i, next);
				}
			}
		}
	}

	public void quickSort(ShoppingListItem[] theList) {
		// If the length is less than 2, we don't need to sort
		if (theList.length > 2) {
			// Find the first target pivot
			int pivot = theList.length / 2;
			// If it's null, we won't be able to compare, so first try to find
			//  a non-null pivot to the left
			while (pivot >= 0 && theList[pivot] == null) {
				--pivot;
			}
			// If we didn't find a non-null pivot on the left, try the right
			if (pivot < 0) {
				pivot = ((theList.length / 2) + 1);
				while (pivot < theList.length && theList[pivot] == null) {
					++pivot;
				}
			}
			// If we didn't find any non-null pivot, move on.
			if (pivot < theList.length) {
				ShoppingListItem[] less = new ShoppingListItem[theList.length - 1];
				ShoppingListItem[] more = new ShoppingListItem[theList.length];
				int ctLess = 0, ctMore = 0;
				for (int i = 0; i < theList.length; ++i) {
					if (this.compareToWithNullsLast(theList[pivot], theList[i]) == 1) {
						less[ctLess++] = theList[i];
					} else {
						more[ctMore++] = theList[i];
					}
				}
				if (ctLess >= 0) {
					less = Arrays.copyOfRange(less, 0, ctLess);
					this.quickSort(less);
				}
				if (ctMore >= 0) {
					more = Arrays.copyOfRange(more, 0, ctMore);
					this.quickSort(more);
				}
				for (int i = 0; i < theList.length; ++i) {
					if (i < less.length) {
						theList[i] = less[i];
					} else {
						theList[i] = more[i - less.length];
					}
				}
			}
		} else if (theList.length == 2) {
			// This is a special case, because there is no pivot (no element is "between" 2 elements.)
			if (theList[0] == null && theList[1] != null) {
				this.exchangeItems(theList, 0, 1);
			} else if (theList[0] != null) {
				if (this.compareToWithNullsLast(theList[0], theList[1]) == 1) {
					this.exchangeItems(theList, 0, 1);
				}
			}
		}
	}

	
	
	public void defaultSort(ShoppingListItem[]  theList) {
		Arrays.sort(theList);
	}
	
	protected int compareToWithNullsLast(ShoppingListItem item1, ShoppingListItem item2) {
		try {
			return item1.compareTo(item2);
		} catch (NullPointerException ex) {
			return -1;
		}
	}
	protected void exchangeItems(ShoppingListItem[] items, int index1, int index2) {
		ShoppingListItem sli = items[index2];
		items[index2] = items[index1];
		items[index1] = sli;
	}
}
