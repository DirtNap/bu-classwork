package edu.bu.cs232;

public class SortingShoppingList extends ShoppingList {
	public SortingShoppingList(ShoppingListItem [] listItems) {
		super(listItems);
		this.setSortProvider(new ListSorterSortProvider());
	}
	public SortingShoppingList(int itemCount) {
		super(itemCount);
		this.setSortProvider(new ListSorterSortProvider());
	}
	public SortingShoppingList() {
		super();
		this.setSortProvider(new ListSorterSortProvider());
	}

}
