package edu.bu.cs232;

import java.io.PrintStream;

public class ConsoleShoppingListDisplay implements ShoppingListDisplay {

	private PrintStream outputStream;

	public ConsoleShoppingListDisplay() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setOutputStream(PrintStream output) {
		this.outputStream = output;
	}

	@Override
	public void DisplayList(Shopper theShopper) {
		this.outputStream.println(theShopper);
	}

	@Override
	public void DisplayList(ShoppingList theList) {
		this.outputStream.println(theList);
	}

	@Override
	public void DisplayShoppingResults(ShoppingList purchasedItems,
			Shopper theShopper) {
		this.outputStream.println("The following items were purchased");
		this.outputStream.println(purchasedItems);
		this.outputStream.println("Here's what remains:");
		this.outputStream.println(theShopper);
		
	}

	
}
