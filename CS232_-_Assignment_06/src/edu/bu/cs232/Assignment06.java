package edu.bu.cs232;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.NumberFormat;

public class Assignment06 extends ShoppingRunner {

	protected InputStream input;
	protected PrintStream output;
	protected InputReader reader;
	
	protected final static String CATALOG_FILE_NAME = "catalog.dat";

	public Assignment06(InputStream in, PrintStream out) {
		this.input = in;
		this.output = out;
		this.reader = new InputReader(this.input, this.output);
	}
	public int Main() {
		int result = 0;
		String name = this.getName();
		double budget = this.getBudget();
		Catalog itemCatalog = this.loadCatalog(Assignment06.CATALOG_FILE_NAME);
		QCShopper shopper = new QCShopper(itemCatalog, budget);
		this.doShopping(name, shopper, this.reader);
		return result;
	}
	
	public void doShopping(String name, QCShopper shopper, InputReader reader) {
		NumberFormat money = NumberFormat.getCurrencyInstance();
		String greeting = "Hello ";
		while (true) {
			reader.outputSource.printf("%s%s,  you currently have %s in your bank account.%n", greeting, name, money.format(shopper.getBudget()));
			greeting = "";
			if (!reader.readBoolean("Would you like to shop in the store?")) {
				break;
			}
			this.buildShoppingList(shopper, this.reader);
			QLLShoppingList purchasedItems = shopper.shop();
			if (purchasedItems.length() > 0) {
				reader.outputSource.println("You purchased the following items:");
				reader.outputSource.println(purchasedItems);
				reader.outputSource.printf("for a total of %s.", money.format(purchasedItems.getTotal()));
			} else {
				reader.outputSource.println("No items purchased.");
			}
			if (shopper.getShoppingList().length() > 0) {
				reader.outputSource.println("You still have the following items:");
				reader.outputSource.println(shopper.getShoppingList());
				reader.outputSource.printf("for a total of %s.", money.format(shopper.getShoppingList().getTotal()));
			} else {
				reader.outputSource.println("No items remain in your shopping cart.");
			}
		}
		reader.outputSource.println("Thank you for shopping.");
	}
	private Catalog loadCatalog(String catalogFileName) {
		try {
			return Catalog.fromFile(catalogFileName);
		} catch (Exception ex) {
			return this.createDefaultCatalog(catalogFileName);
		}
	}
	private Catalog createDefaultCatalog(String catalogFileName) {
		Catalog result = new Catalog();
		result.addItem("Milk", 3.99d);
		result.addItem("Bread", 2.99d);
		result.addItem("Eggs", 2.89d);
		result.addItem("Butter", 1.99d);
		result.addItem("Water", 0.99d);
		result.addItem("Batteries", 5.99d);
		result.addItem("Cereal", 3.79d);
		result.addItem("Coffee", 8.99d);
		result.addItem("Tea", 2.99d);
		result.addItem("Pasta", 0.89d);
		result.addItem("Fish", 12.99d);
		result.addItem("Letuce", 2.49d);
		result.addItem("Toothpaste", 3.29d);
		result.addItem("Soap", 2.79d);
		result.addItem("Ice Cream", 2.59d);
		result.addItem("Beef", 11.89d);
		result.addItem("Chicken", 6.49d);
		result.addItem("Broccoli", 1.99d);
		result.addItem("Wheat Germ", 2.69d);
		result.addItem("Java Textbook", 113.99d);
		result.addItem("Chips", 1.99d);
		result.addItem("Pudding", 1.29d);
		result.addItem("Soup", 0.79d);
		result.addItem("Tacos", 5.99d);
		result.addItem("Aspirin", 9.99d);
		result.addItem("Flour", 1.89d);
		try {
			result.saveToFile(catalogFileName);
		} catch (IOException e) {
			;
		}
		return result;
	}
	private double getBudget() {
		return this.reader.readDouble("How much is in your bank account?");
	}
	private String getName() {
		return this.reader.readAlphaWord("What is your name?");
	}
	public static void main(String[] args) {
		Assignment06 self = new Assignment06(System.in, System.out);
		System.exit(self.Main());
	}
}
