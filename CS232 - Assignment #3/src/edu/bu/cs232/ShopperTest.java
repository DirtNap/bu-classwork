package edu.bu.cs232;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ShopperTest {
	
	public static final double ALLOWABLE_FLOAT_VARIANCE = 1.0e-10d;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetRandomPrice() {
		double budget = 59.0d;
		Shopper sObj = new Shopper(ShoppingList.DEFAULT_ARRAY_SIZE, budget);
		double targetPriceTotal = budget / 0.59d;  
		double targetItemPrice = targetPriceTotal / ShoppingList.DEFAULT_ARRAY_SIZE;
		double targetPriceFloor = targetItemPrice * 0.79d;
		double targetPriceCeiling = targetItemPrice * 1.31d;
		double sum = 0.0d, avg = 0.0d;
		for (int i = 0; i < 10; ++i) {
			double total = 0;
			for (int j = 0; j < ShoppingList.DEFAULT_ARRAY_SIZE; ++j) {
				double price = sObj.getRandomPrice();
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
		Shopper sObj = new Shopper(ShoppingList.DEFAULT_ARRAY_SIZE, 59.0d);
		assertEquals(ShoppingList.DEFAULT_ARRAY_SIZE, sObj.length());
	}

}
