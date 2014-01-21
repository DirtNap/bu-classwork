package edu.bu.cs232;

public class SortingShopper extends Shopper {
	public SortingShopper(int itemCount, double budget) {
		super(itemCount, budget);
		this.getShoppingList().setSortProvider(new ListSorterSortProvider());
	}

}
