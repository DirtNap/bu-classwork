package edu.bu.cs232;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;

public class CatalogTest {

	private Catalog testCatalog;
	private CatalogItem[] testItems;

	@Before
	public void setUp() {
		this.testCatalog = new Catalog();
		this.testItems = new CatalogItem[] { new CatalogItem("Item 1", 1.0d),
										     new CatalogItem("Item 2", 2.0d),
										     new CatalogItem("Item 3", 3.0d, false) };
		for (CatalogItem ci : this.testItems) {
			this.testCatalog.addItem(ci);
		}
	}
	
	
	@Test
	public final void testAddItem() {
		CatalogItem localTestItem = new CatalogItem("Item 0", 0.99d);
		assertEquals(3, this.testCatalog.size());
		assertEquals(this.testItems[0], this.testCatalog.getItem(0));
		this.testCatalog.addItem("Item 4", 4.0d);
		assertEquals(4, this.testCatalog.size());
		this.testCatalog.addItem(localTestItem);
		assertEquals(5, this.testCatalog.size());
		assertEquals(this.testCatalog.getItem(0), localTestItem);
	}

	@Test
	public final void testGetItem() {
		for (int i = 0; i < this.testItems.length; ++i) {
			assertEquals(this.testItems[i], this.testCatalog.getItem(i));
		}
	}

	@Test
	public final void testFromStream() {
		ByteArrayOutputStream baOutStream = null;
		ByteArrayInputStream baInStream = null;
		ObjectOutputStream oOutStream = null;
		Catalog localTestCatalog = null;
		try {
			baOutStream = new ByteArrayOutputStream();
			oOutStream = new ObjectOutputStream(baOutStream);
			oOutStream.writeInt(this.testItems.length);
			for (int i = 0; i < this.testItems.length; ++i) {
				oOutStream.writeObject(this.testItems[i]);
			}
			oOutStream.writeInt(this.testItems.length);
			oOutStream.flush();
			baOutStream.flush();
			baInStream = new ByteArrayInputStream(baOutStream.toByteArray());
			localTestCatalog = Catalog.fromStream(baInStream);
		} catch (IOException ex) {
			fail("IO Error");
		} finally {
			try {
				baOutStream.close();
				oOutStream.close();
			} catch (IOException e) {
				;
			}
		}
		assertEquals(this.testCatalog, localTestCatalog);
	}

	@Test
	public final void testSaveToStream() {
		ByteArrayOutputStream baOutStream = null;
		ByteArrayInputStream baInStream = null;
		ObjectInputStream oInStream = null;
		try {
			baOutStream = new ByteArrayOutputStream();
			this.testCatalog.saveToStream(baOutStream);
			baInStream = new ByteArrayInputStream(baOutStream.toByteArray());
			oInStream = new ObjectInputStream(baInStream);
			assertEquals(this.testItems.length, oInStream.readInt());
			for (int i = 0; i < this.testItems.length; ++i) {
				assertEquals(this.testItems[i], oInStream.readObject());
			}
			assertEquals(this.testCatalog.size(), oInStream.readInt());
		} catch (IOException ex) {
			fail("IO Error");
		} catch (ClassNotFoundException e) {
			fail("Serialization Error");
		} finally {
			try {
				baOutStream.close();
				oInStream.close();
			} catch (IOException e) {
				;
			}
		}
	}

	@Test
	public final void testToString() {
		String output = this.testCatalog.toString();
		int current = -1;
		int previous = -1;
		for (int i = 0; i < this.testItems.length; ++i) {
			current = output.indexOf(this.testItems[i].toString());
			assertFalse(-1 == current);
			assertTrue(current > previous);
			previous = current;
		}
	}

}
