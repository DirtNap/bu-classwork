package edu.bu.cs232;

public abstract class ShoppingRunner {

		protected void buildShoppingList(QCShopper shopper, InputReader reader) {
			String an = "an";
			while (true) {
				if (reader.readBoolean(String.format("Add %s item to your shopping list?", an))) {
					if (reader.readBoolean("Would you like to see the product catalog?")) {
						reader.outputSource.println(shopper.getCatalogItemList());
					}
					this.addShoppingListItem(shopper, reader);
					an = "another";
				} else {
					break;
				}
			}
			while (shopper.getShoppingList().length() > 0) {
				if (reader.readBoolean("Would you like to see your shopping list?")) {
					reader.outputSource.println(shopper.getShoppingList());
				}
				if (reader.readBoolean("Would you like to edit the priority of any items?")) {
					this.editShoppingListPriority(shopper, reader);
				} else {
					break;
				}
			}
		}
		protected void editShoppingListPriority(QCShopper shopper, InputReader reader) {
			String itemName = "";
			while (itemName == "") {
				itemName = reader.readWord("Enter the name of the item to modify?");
				try {
					shopper.setPriority(itemName, 1);
					int priority = reader.readInteger("Enter the new priority", 0);
					shopper.setPriority(itemName, priority);
				} catch (IllegalArgumentException ex) {
					reader.outputSource.printf("Item %s not found%n", itemName);
					itemName = "";
				}
			}
		}
		protected void addShoppingListItem(QCShopper shopper, InputReader reader) {
			int catalogIndex = -1;
			while (0 > catalogIndex) {
				catalogIndex = reader.readInteger("Enter the number of the catalog item you would like to add", 1);
				int itemQuantity = reader.readInteger("How many would you like to purchase?", 1);
				int itemPriority = reader.readInteger("What priority would you like to assign?  (lower numbers are given precedence when shopping)",
						                                   0);
				try {
					shopper.addItemToList(catalogIndex, itemPriority, itemQuantity);
				} catch (Exception ex) {
					catalogIndex = -1;
				}
			}
		}

		public abstract void doShopping(String name, QCShopper shopper, InputReader reader);
}
