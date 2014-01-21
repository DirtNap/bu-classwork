package edu.bu.cs232;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;

public class CatalogItemTest {

	public static final double ALLOWABLE_FLOAT_VARIANCE = 1.0e-10d;
	
	private CatalogItem testItem;
	private String testName;
	private double testPrice;

	@Before
	public void setUp() {
		this.testName = "Test Item";
		this.testPrice = 5.50d;
		this.testItem = new CatalogItem(this.testName, this.testPrice);
	}
	
	@Test
	public final void testHashCode() {
		assertEquals(this.testName.toLowerCase().hashCode(), this.testItem.hashCode());
	}

	@Test
	public final void testPrice() {
		double testPrice = 1.0d;
		assertEquals(this.testPrice, this.testItem.getPrice(), CatalogItemTest.ALLOWABLE_FLOAT_VARIANCE);
		this.testItem.setPrice(testPrice);
		assertEquals(testPrice, this.testItem.getPrice(), CatalogItemTest.ALLOWABLE_FLOAT_VARIANCE);
	}

	@Test
	public final void testName() {
		String testString = "Other Item";
		assertEquals(this.testName, this.testItem.getName());
		this.testItem.setName(testString);
		assertEquals(testString, this.testItem.getName());
	}

	@Test
	public final void testAvailable() {
		assertTrue(this.testItem.isAvailable());
		CatalogItem localTestItem = new CatalogItem(this.testName, this.testPrice, false);
		assertFalse(localTestItem.isAvailable());
	}
	
	@Test
	public final void testEquals() {
		CatalogItem localTestItem = new CatalogItem(this.testName, this.testPrice);
		assertFalse(this.testItem.equals(null));
		assertEquals(this.testItem, this.testItem);
		assertEquals(this.testItem, localTestItem);
		localTestItem.setPrice(1.0d);
		assertEquals(this.testItem, localTestItem);
		localTestItem.setName("Other Item");
		assertFalse(this.testItem.equals(localTestItem));
	}

	@Test
	public final void testComparable() {
		CatalogItem localTestItem = new CatalogItem("Other Name", this.testPrice);
		assertEquals(0, this.testItem.compareTo(this.testItem));
		assertTrue(0 > localTestItem.compareTo(this.testItem));
		assertTrue(0 < this.testItem.compareTo(localTestItem));
	}

	@Test
	public final void testSerializable() {
		ByteArrayOutputStream baOutStream = null;
		ByteArrayInputStream baInStream = null;
		ObjectOutputStream oOutStream = null;
		ObjectInputStream oInStream = null;
		CatalogItem localTestItem = null;
		try {
			baOutStream = new ByteArrayOutputStream();
			oOutStream = new ObjectOutputStream(baOutStream);
			oOutStream.writeObject(this.testItem);
			baInStream = new ByteArrayInputStream(baOutStream.toByteArray());
			oInStream = new ObjectInputStream(baInStream);
			localTestItem = (CatalogItem) oInStream.readObject();
		} catch (IOException e) {
			fail("Unable to write object");
		} catch (ClassNotFoundException ex) {
			fail("Serialization error");
		} finally {
			try {
				oOutStream.close();
				oInStream.close();
				baInStream.close();
				baOutStream.close();
			} catch (IOException ex) {
				;
			}
		}
		assertEquals(this.testItem, localTestItem);
		assertEquals(this.testItem.getName(), localTestItem.getName());
		assertEquals(this.testItem.getPrice(), localTestItem.getPrice(), CatalogItemTest.ALLOWABLE_FLOAT_VARIANCE);
		assertFalse(this.testItem == localTestItem);
	}
	
}
