package edu.bu.cs232;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class QCShopperTest {

	
	public static final double ALLOWABLE_FLOAT_VARIANCE = 1.0e-10d;
	public static final double DEFAULT_BUDGET = 59.0d;
	private QCShopper shopper;
	private Catalog catalog;
	private CatalogItem[] testCatalogItems;
	private QShoppingListItem[] testSLItems;
	@Before
	public void setUp() throws Exception {
		this.catalog = new Catalog();
		this.shopper = new QCShopper(this.catalog, ShopperTest.DEFAULT_BUDGET);
		this.testCatalogItems = new CatalogItem[] { new CatalogItem("Item 1", 12.0d),
												    new CatalogItem("Item 2", 10.0d),
												    new CatalogItem("Item 3", 13.0d) };
		this.testSLItems = new QShoppingListItem[this.testCatalogItems.length];
		for (int i = 0; i < this.testCatalogItems.length; ++i) {
			this.catalog.addItem(this.testCatalogItems[i]);
			this.testSLItems[i] = new QShoppingListItem(this.testCatalogItems[i].getName(), i + 1, this.testCatalogItems[i].getPrice(), i + 1);
		}
		for (int i = 0; i < this.testCatalogItems.length; ++i) {
			this.shopper.addItemToList(i, this.testSLItems[i].getPriority(), this.testSLItems[i].getQuantity());
		}
	}

	@Test
	public void testShopping() {
		QLLShoppingList purchased = this.shopper.shop();
		QLLShoppingList remains = this.shopper.getShoppingList();
		if (remains.length() > 0) {
			assertTrue(Double.compare(remains.get(0).getPrice(), this.shopper.getBudget()) > 0);
		}
	}
}
