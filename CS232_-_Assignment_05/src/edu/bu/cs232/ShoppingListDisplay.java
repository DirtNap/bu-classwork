package edu.bu.cs232;

public interface ShoppingListDisplay {
	public void setOutputStream(java.io.PrintStream output);
	
	public void DisplayList(Shopper theShopper);
	
	public void DisplayList(ShoppingList theList);
	
	public void DisplayShoppingResults(ShoppingList purchasedItems, Shopper theShopper);
	
}
