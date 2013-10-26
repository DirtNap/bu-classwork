package edu.bu.cs232;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ShopperTest {
	
	public static final double ALLOWABLE_FLOAT_VARIANCE = 1.0e-10d;
	public static final double DEFAULT_BUDGET = 59.0d;
	private Shopper shopper;
	@Before
	public void setUp() throws Exception {
		this.shopper = new Shopper(ShoppingList.DEFAULT_ARRAY_SIZE, ShopperTest.DEFAULT_BUDGET);
	}

	@Test
	public void testGetRandomPrice() {
		double targetPriceTotal = ShopperTest.DEFAULT_BUDGET / 0.59d;  
		double targetItemPrice = targetPriceTotal / ShoppingList.DEFAULT_ARRAY_SIZE;
		double targetPriceFloor = targetItemPrice * 0.79d;
		double targetPriceCeiling = targetItemPrice * 1.31d;
		double sum = 0.0d, avg = 0.0d;
		for (int i = 0; i < 10; ++i) {
			double total = 0;
			for (int j = 0; j < ShoppingList.DEFAULT_ARRAY_SIZE; ++j) {
				double price = this.shopper.getRandomPrice();
				assertEquals("Price should be at least 80% of target", -1, 
						Double.compare(targetPriceFloor, price));
				assertEquals("Price should be at most 130% of target", 1, 
						Double.compare(targetPriceCeiling, price));
				total += price;
			}
			sum += total;
		}
		avg = sum / 10d;
		assertEquals("On average, price should exceed target total", -1,
					Double.compare(100.0d, avg));
	}

	@Test
	public void testLength() {
		assertEquals(ShoppingList.DEFAULT_ARRAY_SIZE, this.shopper.length());
	}

	@Test
	public void testAddItem() {
		this.shopper.addItem("Item 1", 2);
		this.shopper.addItem("Item 2", 1, 7.5d);
		ShoppingList sl = this.shopper.getShoppingList();
		assertEquals("Test that random prices are being set to reasonable values", -1,
				Double.compare(sl.get(0).getPrice(), sl.get(1).getPrice()));
	}

	@Test
	public void testSetPriority() {
		this.shopper.addItem("Item 1", 2);
		this.shopper.addItem("Item 2", 1);
		ShoppingList sl = this.shopper.getShoppingList();
		assertEquals("Check that Item 2 is first", "Item 2", sl.get(0).getName());
		sl.get(0).setPriority(3);
		sl.sortItems();
		assertEquals("Check that Item 1 is first", "Item 1", sl.get(0).getName());
		sl = this.shopper.getShoppingList();
		assertEquals("Check that ShoppingList is mutable.", "Item 1", sl.get(0).getName());
	}
	@Test
	public void testShopping() {
		double budget = this.shopper.getBudget();
		for (int i = 0; i < this.shopper.length(); ++i) {
			this.shopper.addItem(String.format("Item %d", i), i);
		}
		ShoppingList sl = this.shopper.shop();
		double price = 0.0d;
		for (ShoppingListItem sli : sl) {
			price += sli.getPrice();
		}
		assertTrue("Purchased Items less than budget.", Double.compare(budget, price) >= 0);
		ShoppingListItem sli = this.shopper.getShoppingList().get(0);
		if (sli != null) {
			price += sli.getPrice();
			assertTrue("Un-purchased Items outside of budget", Double.compare(budget, price) < 0);
		}
	}
}
