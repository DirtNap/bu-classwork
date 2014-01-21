package edu.bu.cs232;

public class ShoppingListItem implements Comparable<ShoppingListItem>, Cloneable {
	
	private int itemPriceCents;
	private final String itemName;
	private int itemPriority;
	private boolean hasPrice = false;
	private boolean hasPriority = false;
	

	public ShoppingListItem(String itemName) {
		this.itemName = itemName;
	}
	public ShoppingListItem(String itemName, int itemPriority) {
		this(itemName);
		this.setPriority(itemPriority);
	}
	public ShoppingListItem(String itemName, int itemPriority, double itemPrice) {
		this(itemName,itemPriority);
		this.setPrice(itemPrice);
	}
	
	protected boolean isInitialized() {
		return hasPriority && hasPrice;
	}
	protected void checkState() {
		if (!this.isInitialized()) {
			throw new IllegalStateException("ShoppingListItem requires both priority and price");
		}
	}
	
	public double getPrice() {
		this.checkState();
		return itemPriceCents / 100.0d;
	}
	public void setPrice(double itemPrice) {
		this.itemPriceCents = (int)(itemPrice * 100);
		this.hasPrice = true;
	}
	public int getPriority() {
		this.checkState();
		return itemPriority;
	}
	public void setPriority(int itemPriority) {
		this.itemPriority = itemPriority;
		this.hasPriority = true;
	}
	public String getName() {
		this.checkState();
		return itemName;
	}
	@Override
	public int compareTo(ShoppingListItem o) {
		// Sort by PRIORITY ASC, PRICE ASC, NAME ASC
		if (o == null) {
			throw new NullPointerException();
		}
		int result = Integer.compare(this.getPriority(), o.getPriority());
		if (result == 0) {
			result = Double.compare(this.getPrice(), o.getPrice());
		}
		if (result == 0) {
			result = this.getName().compareToIgnoreCase(o.getName());
		}
		return result;
	}
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			// Never equals null
			return false;
		}
		if (this == o) {
			// Always equals itself
			return true;
		}
		try {
			// See if it represents the same item.
			return this.getName().equalsIgnoreCase(((ShoppingListItem)(o)).getName());	
		} catch (ClassCastException ex) {
			// Wasn't a ShoppingListItem
			return false;
		}
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	@Override
	public int hashCode() {
		return this.getName().toLowerCase().hashCode();
	}
	public String toString() {
		return this.getName();
	}
}
