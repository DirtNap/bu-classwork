package edu.bu.cs232;

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
		this.budget = budget;
		this.targetTotal = (int)(this.budget/0.59f);
		this.priceTarget = this.targetTotal / this.itemCount;
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
	
}
