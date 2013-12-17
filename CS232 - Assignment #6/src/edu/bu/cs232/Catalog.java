package edu.bu.cs232;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Collections;

public class Catalog {

	private LList<CatalogItem> catalogItems;
	
	
	public Catalog() {
		this.catalogItems = new LList<CatalogItem>();
	}
	
	public void addItem(CatalogItem item) {
		if (this.catalogItems.contains(item)) {
			throw new IllegalArgumentException(String.format("Duplicate Item:  %s", item.getName()));
		}
		this.catalogItems.add(item);
		Collections.sort(this.catalogItems);
	}
	public void addItem(String name, double price) {
		this.addItem(new CatalogItem(name, price));
	}
	
	public CatalogItem getItem(int index) {
		try {
			return this.catalogItems.get(index);
		} catch (IndexOutOfBoundsException ex) {
			throw new ItemNotFoundException(String.format("Item #%d not found.", index + 1));
		}
	}
	
	public static Catalog fromFile(String path) throws IOException, FileNotFoundException {
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(path);
			return Catalog.fromStream(fileInput);
		} finally {
			fileInput.close();
		}
	}
	public static Catalog fromStream(InputStream fileInput) throws IOException, FileNotFoundException {
		Catalog result = new Catalog();
		ObjectInputStream objectInput = null;
		try {
			objectInput = new ObjectInputStream(fileInput);
			int totalItemCount = 0, checkItemCount = 0;
			try {
				totalItemCount = objectInput.readInt();
				for (int i = 0; i < totalItemCount; ++i) {
					result.addItem((CatalogItem) objectInput.readObject());
				}
				checkItemCount = objectInput.readInt();
			} catch (IOException ex) {
				throw(ex);
			} catch(Exception ex) {
				;
			}
			if (totalItemCount != checkItemCount) {
				throw new IllegalStateException("Corrupt save file.");
			}
		} finally {
			try {
				objectInput.close();
			} catch (IOException e) {
				;
			}
		}
		
		return result;
	}
	
	public int saveToFile(String path) throws FileNotFoundException, IOException {
		FileOutputStream fileOutput = null;
		try {
			fileOutput = new FileOutputStream(path, false);
			return this.saveToStream(fileOutput);
		} finally {
			fileOutput.close();
		}
	}
	
	public int saveToStream(OutputStream fileOutput) throws IOException {
		ObjectOutputStream oOutput = null;
		int totalItemCount = this.catalogItems.size(), writtenItemCount = 0;
		try {
			oOutput = new ObjectOutputStream(fileOutput);
			oOutput.writeInt(this.catalogItems.size());
			for (CatalogItem ci : this.catalogItems) {
				oOutput.writeObject(ci);
				++writtenItemCount;
			}
			oOutput.writeInt(writtenItemCount);
		} finally {
			try {
				oOutput.close();
			} catch (IOException e) {
				;
			}
		}
		if (totalItemCount == writtenItemCount) {
			return writtenItemCount;
		} else {
			throw new IllegalStateException("Mismatch in write counts.");
		}
	}

	public int size() {
		return this.catalogItems.size();
	}
	
	public boolean contains(CatalogItem ci) {
		return this.catalogItems.contains(ci);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		int rowNum = 0;
		String baseFormat = "%% %dd %%s%%n";
		String format = String.format(baseFormat, Integer.toString(this.catalogItems.size()).length());
		for (CatalogItem ci : this.catalogItems) {
			result.append(String.format(format, ++rowNum, ci));
		}
		return result.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (null == o) {
			return false;
		}
		if (this == o) {
			return false;
		}
		try {
			Catalog co = (Catalog) o;
			if (this.size() == co.size()) {
				for (CatalogItem ci : this.catalogItems) {
					if (!co.contains(ci)) {
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		} catch (ClassCastException ex) {
			return false;
		}
	}
	
	
}
