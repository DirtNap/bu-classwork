package edu.bu.cs232;

public class QShoppingListItem extends ShoppingListItem implements Comparable<ShoppingListItem> {
	
	private int itemQuantity;
	public QShoppingListItem(String itemName) {
		super(itemName);
		this.setQuantity(1);
	}
	public QShoppingListItem(String itemName, int itemPriority) {
		super(itemName, itemPriority);
		this.setQuantity(1);
	}
	public QShoppingListItem(String itemName, int itemPriority, double itemPrice) {
		super(itemName, itemPriority, itemPrice);
		this.setQuantity(1);
	}
	public QShoppingListItem(String itemName, int itemPriority, double itemPrice, int qty) {
		super(itemName, itemPriority, itemPrice);
		this.setQuantity(qty);
	}
	public int getQuantity() {
		return itemQuantity;
	}
	public void setQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public int compareTo(ShoppingListItem o) {
		// Sort by PRIORITY ASC, PRICE ASC, QTY ASC, NAME ASC
		if (o == null) {
			throw new NullPointerException();
		}
		int result = Integer.compare(this.getPriority(), o.getPriority());
		if (result == 0) {
			result = Double.compare(this.getPrice(), o.getPrice());
		}
		if (result == 0) {
			try {
				result = Integer.compare(this.getQuantity(), ((QShoppingListItem)o).getQuantity());
			} catch (ClassCastException ex) {
				result = Integer.compare(this.getQuantity(), 1);
			}
			
		}
		if (result == 0) {
			result = this.getName().compareToIgnoreCase(o.getName());
		}
		return result;
	}
	
}
