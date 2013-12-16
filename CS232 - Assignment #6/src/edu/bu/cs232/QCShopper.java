package edu.bu.cs232;

public class QCShopper {

	private Catalog catalog;
	private QLLShoppingList shoppingList;
	private double budget;

	public QCShopper(Catalog catalog, double budget) {
		this.catalog = catalog;
		this.shoppingList = new QLLShoppingList();
		this.setBudget(budget);
	}

	public String getCatalogItemList() {
		return this.catalog.toString();
	}
	
	public double getBudget() {
		return this.budget;
	}
	public void setBudget(double budget) {
		if (budget <= 0) {
			throw new IllegalArgumentException("Invalid budget.");
		}
		this.budget = budget;
	}
	public void setPriority(String itemName, int priority) {
		QShoppingListItem result = null;
		try {
			result = this.shoppingList.get(itemName);
		} catch (IndexOutOfBoundsException ex) {
			throw new IllegalArgumentException(String.format("Item %s not found.", itemName));
		}
		result.setPriority(priority);
	}
	public void setQuantity(String itemName, int quantity) {
		QShoppingListItem result = null;
		try {
			result = this.shoppingList.get(itemName);
		} catch (IndexOutOfBoundsException ex) {
			throw new IllegalArgumentException(String.format("Item %s not found.", itemName));
		}
		if (quantity < 1) {
			this.shoppingList.remove(itemName);
		} else {
			result.setQuantity(quantity);
		}
	}
	
	public QLLShoppingList getShoppingList() {
		return this.shoppingList;
	}
	
	public QLLShoppingList shop() {
		QLLShoppingList result = new QLLShoppingList();
		QShoppingListItem purchasedItem;
		double currentBudget = this.getBudget();
		for (QShoppingListItem sli : this.shoppingList) {
			if (currentBudget < sli.getPrice()) {
				break;
			}
			int targetQty = (int)(currentBudget / sli.getPrice());
			boolean continueShopping = true;
			if (targetQty >= sli.getQuantity()) {
				targetQty = sli.getQuantity();
			} else {
				continueShopping = false;
			}
			purchasedItem = new QShoppingListItem(sli.getName(), sli.getPriority(), sli.getPrice(), targetQty);
			currentBudget -= purchasedItem.getQuantity() * purchasedItem.getPrice();
			result.put(purchasedItem);
			if (!continueShopping) {
				break;
			}
		}
		this.setBudget(currentBudget);
		for (QShoppingListItem sli : result) {
			purchasedItem = this.shoppingList.get(sli.getName());
			purchasedItem.setQuantity(purchasedItem.getQuantity() - sli.getQuantity());
		}
		return result;
	}
	
	public boolean addItemToList(int catalogNum, int priority) {
		return this.addItemToList(catalogNum, priority, 1);
	}
	public boolean addItemToList(int catalogNum, int priority, int quantity) {
		CatalogItem ci = this.catalog.getItem(catalogNum);
		if (ci.isAvailable()) {
			ci.setAvailable(false);
		} else {
			throw new IllegalArgumentException(String.format("Item %s is not available" , ci.getName()));
		}
		try {
			this.shoppingList.put(new QShoppingListItem(ci.getName(), priority, ci.getPrice(), quantity));
		} catch (IllegalArgumentException ex) {
			return false;
		}
		return true;
	}

	
}
