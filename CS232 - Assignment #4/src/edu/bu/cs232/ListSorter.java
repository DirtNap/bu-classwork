package edu.bu.cs232;

import java.util.Arrays;

public abstract class ListSorter {
	public void selectionSort(ShoppingListItem[] theList) {
		for (int i = 0; i < theList.length; ++i) {
			//Move the smallest to the beginning, and leave it there.
			int lowest = i;
			for (int j = i; j < theList.length; ++j) {
				if (this.compareToWithNullsLast(theList[lowest], theList[j]) > 0) {
					lowest = j;
				}
				if (theList[lowest] == null) {
					this.exchangeItems(theList, lowest, j);
				}
			}
			if (lowest != i) {
				this.exchangeItems(theList, i, lowest);
			}
		}
	}
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
				if (result > 0) {
					repeat = true;
					this.exchangeItems(theList, i, next);
				}
			}
		}
	}
	public void quickSort(ShoppingListItem[] theList) {
		// If the length is less than 2, we don't need to sort
		if (theList.length > 2) {
			int pivot = this.getPivot(theList);
			// If we didn't find any non-null pivot, move on.
			if (pivot < theList.length) {
				ShoppingListItem[] less = new ShoppingListItem[theList.length - 1];
				ShoppingListItem[] more = new ShoppingListItem[theList.length - 1];
				ShoppingListItem[] equal = new ShoppingListItem[theList.length];
				int ctLess = 0, ctMore = 0, ctEqual = 0;
				for (int i = 0; i < theList.length; ++i) {
					if (this.compareToWithNullsLast(theList[pivot], theList[i]) > 0) {
						less[ctLess++] = theList[i];
					} else if (this.compareToWithNullsLast(theList[pivot], theList[i]) < 0) {
						more[ctMore++] = theList[i];
					} else {
						equal[ctEqual++] = theList[i];
					}
				}
				equal = Arrays.copyOfRange(equal, 0, ctEqual);
				if (ctLess >= 0) {
					less = Arrays.copyOfRange(less, 0, ctLess);
					this.quickSort(less);
				}
				if (ctMore >= 0) {
					more = Arrays.copyOfRange(more, 0, ctMore);
					this.quickSort(more);
				}
				for (int i = 0; i < less.length; ++i) {
					theList[i] = less[i];
				}
				for (int i = 0; i < equal.length; ++i) {
					theList[less.length + i] = equal[i];
				}
				for (int i = 0; i< more.length; ++i) {
					theList[less.length + equal.length + i] = more[i];
				}
			}
		} else if (theList.length == 2) {
			// This is a special case, because there is no pivot (no element is "between" 2 elements.)
			if (theList[0] == null && theList[1] != null) {
				this.exchangeItems(theList, 0, 1);
			} else if (theList[0] != null) {
				if (this.compareToWithNullsLast(theList[0], theList[1]) > 0) {
					this.exchangeItems(theList, 0, 1);
				}
			}
		}
	}
	public void defaultSort(ShoppingListItem[]  theList) {
		Arrays.sort(theList);
	}
	public void mergeSort(ShoppingListItem[] theList) {
		class MergeSort {
			  private ShoppingListItem[] inputList;
			  private ShoppingListItem[] swap;

			  private int number;

			  public void sort(ShoppingListItem [] theList) {
			    this.inputList = theList;
			    number = this.inputList.length;
			    this.swap = new ShoppingListItem[number];
			    mergesort(0, number - 1);
			  }

			  private void mergesort(int low, int high) {
			    // check if low is smaller then high, if not then the array is sorted
			    if (low < high) {
			      // Get the index of the element which is in the middle
			      int middle = low + (high - low) / 2;
			      // Sort the left side of the array
			      mergesort(low, middle);
			      // Sort the right side of the array
			      mergesort(middle + 1, high);
			      // Combine them both
			      merge(low, middle, high);
			    }
			  }

			  private void merge(int low, int middle, int high) {

			    // Copy both parts into the helper array
			    for (int i = low; i <= high; i++) {
			      this.swap[i] = this.inputList[i];
			    }

			    int i = low;
			    int j = middle + 1;
			    int k = low;
			    // Copy the smallest values from either the left or the right side back
			    // to the original array
			    while (i <= middle && j <= high) {
			      if (ListSorter.this.compareToWithNullsLast(this.swap[i], this.swap[j]) <= 0) {
			        this.inputList[k] = this.swap[i];
			        i++;
			      } else {
			        this.inputList[k] = this.swap[j];
			        j++;
			      }
			      k++;
			    }
			    // Copy the rest of the left side of the array into the target array
			    while (i <= middle) {
			      this.inputList[k] = this.swap[i];
			      k++;
			      i++;
			    }

			  }
			} 
		MergeSort ms = new MergeSort();
		ms.sort(theList);
	}
	
	
	// Utility functions
	private int getPivot(ShoppingListItem[] theList) {
		int result = 0;
		if (theList.length > 2) {
			// Find the first target pivot
			result = theList.length / 2;
			// If it's null, we won't be able to compare, so first try to find
			//  a non-null pivot to the left
			while (result >= 0 && theList[result] == null) {
				--result;
			}
			// If we didn't find a non-null result on the left, try the right
			if (result < 0) {
				result = ((theList.length / 2) + 1);
				while (result < theList.length && theList[result] == null) {
					++result;
				}
			}
		}
		return result;
	}
	protected int compareToWithNullsLast(ShoppingListItem item1, ShoppingListItem item2) {
		try {
			return item1.compareTo(item2);
		} catch (NullPointerException ex) {
			if (item1 == null) {
				if (item2 == null) {
					return 0;
				} else {
					return 1;
				}
			}
			return -1;
		}
	}
	protected void exchangeItems(ShoppingListItem[] items, int index1, int index2) {
		ShoppingListItem sli = items[index2];
		items[index2] = items[index1];
		items[index1] = sli;
	}

	// Abstract Methods
	public abstract void doSorting(ShoppingListItem[] theList);
}
