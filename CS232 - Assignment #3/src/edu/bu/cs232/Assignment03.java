package edu.bu.cs232;

public class Assignment03 {
	
	private InputReader inputReader;
	private Shopper shopper;
	
	private void addItems() {
		for (int i = 0; i < this.shopper.length(); ) {
			try {
				this.addItem(++i);
			} catch (IllegalArgumentException ex) {
				System.err.printf("Error:  %s\n", ex.getMessage());
				--i;
			}
		}
	}
	private void addItem(int priority) {
		String itemName = this.inputReader.readLine("What would you like to buy?");
		this.shopper.addItem(itemName, priority);
	}
	
	public Assignment03(InputReader reader) {
		this.inputReader = reader;
	}

	protected Shopper createShopper(int itemCount, double budget) {
		return new Shopper(itemCount, budget);
	}
	
	public int Main() {
		int itemCount = this.inputReader.readInteger("How many items are on your list?", 7);
		double budget = this.inputReader.readDouble("What is your budget?");
		this.shopper = this.createShopper(itemCount, budget); 
		this.addItems();
		System.out.println(this.shopper);
		while (this.inputReader.readBoolean("Would you like to change priorities?")) {
			String itemName = this.inputReader.readWord("Which item would you like to change priority for?");
			try {
				ShoppingListItem sli = this.shopper.getShoppingList().get(itemName);
				int priority = this.inputReader.readInteger(String.format("New priority for %s", itemName), 1);
				sli.setPriority(priority);
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.err.printf("Error:  %s\n", ex.getMessage());
			}
			System.out.println(this.shopper);
		}
		ShoppingList purchasedItems = this.shopper.shop();
		System.out.printf("The following items were purchased:%n%s%nHere's what remains:%n", purchasedItems);
		System.out.println(this.shopper);
		return 0;
	}
	public static void main(String[] args) {
		Assignment03 self = new Assignment03(new InputReader(System.in, System.out));
		System.exit(self.Main());
	}

}
