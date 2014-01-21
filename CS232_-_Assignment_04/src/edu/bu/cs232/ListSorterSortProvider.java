package edu.bu.cs232;

/**
 * 
 * @author dirtnap
 *
 * Concrete implementation of {@link ListSorter} which selects sort type
 * based on a constructor parameter.
 */
public class ListSorterSortProvider extends ListSorter {
	public enum SortTypes {
		SELECTION,
		BUBBLE,
		QUICK,
		MERGE,
		JAVA
	}
	protected SortTypes sortType;
	public ListSorterSortProvider(SortTypes sortType) {
		this.sortType = sortType;
	}
	public ListSorterSortProvider() {
		this(SortTypes.JAVA);
	}
	
	
	@Override
	public void doSorting(ShoppingListItem[] theList) {
		switch(this.sortType) {
		case SELECTION:
			this.selectionSort(theList);
			break;
		case BUBBLE:
			this.bubbleSort(theList);
			break;
		case QUICK:
			this.quickSort(theList);
			break;
		case MERGE:
			this.mergeSort(theList);
			break;
		default: // Same as JAVA
			this.defaultSort(theList);
		}
	}

}
