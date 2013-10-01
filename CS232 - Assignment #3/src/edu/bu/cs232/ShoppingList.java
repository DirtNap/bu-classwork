package edu.bu.cs232;

public class ShoppingList {
	public static final int DEFAULT_ARRAY_SIZE = 7;
	private ShoppingListItem[] listItems;

	public ShoppingList(ShoppingListItem [] listItems) {
		this.listItems = listItems;
	}
	public ShoppingList(int itemCount) {
		this(new ShoppingListItem[itemCount]);
	}
	public ShoppingList() {
		this(ShoppingList.DEFAULT_ARRAY_SIZE);
	}
	public int length() {
		return this.listItems.length;
	}
}
