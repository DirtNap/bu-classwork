package edu.bu.cs232;

public class ALShopper extends SortingShopper {

	public ALShopper(int itemCount, double budget) {
		super(itemCount, budget);
	}

	@Override
	protected ShoppingList createShoppingList(int itemCount) {
		return new ALShoppingList(itemCount);
	}
}
