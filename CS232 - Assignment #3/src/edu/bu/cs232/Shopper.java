package edu.bu.cs232;

import java.text.NumberFormat;
import java.util.Random;

public class Shopper {
	private int itemCount;
	// Floating point prices are OK here. ShoppingListItem will constrain the behavior.
	private double budget;
	private double targetTotal;
	private double priceTarget;
	private ShoppingList shoppingList;
	private Random randomGenerator;

	public Shopper(int itemCount, double budget) {
		this.itemCount = itemCount;
		this.setBudget(budget);
		this.shoppingList = new ShoppingList(this.itemCount);
		this.randomGenerator = new Random();
	}
	protected double getRandomPrice() {
		// Get a price which is between 80% and 130% of the target price.
		return this.priceTarget * (0.8f + (this.randomGenerator.nextFloat() * 0.5f)) ;
	}
	public ShoppingList getShoppingList() {
		return this.shoppingList;
	}
	public int length() {
		return this.itemCount;
	}
	public void addItem(String itemName, int priority) {
		this.addItem(itemName, priority, this.getRandomPrice());
	}
	public void addItem(String itemName, int priority, double price) {
		this.shoppingList.put(new ShoppingListItem(itemName, priority, price));
	}
	public double getBudget() {
		return this.budget;
	}
	private void setBudget(double budget) {
		this.setBudget(budget, true);
	}
	private void setBudget(double budget, boolean reset) {
		this.budget = budget;
		if (reset) {
			this.targetTotal = this.budget/0.59f;
			this.priceTarget = this.targetTotal / this.itemCount;
		}
	}
	public ShoppingList shop() {
		int purchaseCount = 0;
		double amountSpent = 0.0d;
		ShoppingList returnValue, resetValue;
		for (ShoppingListItem sli : this.getShoppingList()) {
			if (this.budget >= amountSpent + sli.getPrice()) {
				purchaseCount++;
				amountSpent += sli.getPrice();
			} else {
				break;
			}
		}
		this.setBudget(this.getBudget() - amountSpent, false);
		returnValue = new ShoppingList(purchaseCount);
		resetValue = this.getShoppingList();
		this.shoppingList = new ShoppingList(this.length());
		int resetPriority = 0;
		for (int i = 0; i < this.length(); ++i) {
			ShoppingListItem sli = resetValue.get(i);
			if (i < purchaseCount) {
				sli.setPriority(i + 1);
				returnValue.put(sli);
			} else {
				this.addItem( sli.getName(), ++resetPriority, sli.getPrice());
			}
		}
		return returnValue;
	}
	@Override
	public String toString() {
		StringBuilder retVal = new StringBuilder();
		NumberFormat money = NumberFormat.getCurrencyInstance();
		retVal.append(String.format("Shopping List%nBudget:\t%s%n", money.format(this.getBudget())));
		retVal.append(this.getShoppingList().toString());
		retVal.append(String.format("------------------------------------------%nTotal:\t%s%n",
				money.format(this.getShoppingList().getTotal())));
		return retVal.toString();
	}
	@Override
	public boolean equals(Object o) {
		if (o != null) {
			if (o == this) {
				return true;
			}
			if (o instanceof Shopper) {
				Shopper s = (Shopper) o;
				if (this.getBudget() == s.getBudget()) {
					if (this.getShoppingList().equals(s.getShoppingList())) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
