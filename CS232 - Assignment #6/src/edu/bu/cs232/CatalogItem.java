package edu.bu.cs232;

import java.io.Serializable;

public class CatalogItem implements Serializable, Comparable<CatalogItem> {

	private static final long serialVersionUID = -3695407328615414842L;
	private double price;
	private String name;

	public CatalogItem(String name, double price) {
		this.setName(name);
		this.setPrice(price);
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (null == o) {
			return false;
		}
		if (o == this) {
			return true;
		}
		try {
			return this.getName().equalsIgnoreCase(((CatalogItem)o).getName());
		} catch (ClassCastException ex) {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.getName().toLowerCase().hashCode();
	}

	@Override
	public int compareTo(CatalogItem o) {
		return this.getName().compareToIgnoreCase(o.getName());
	}
	
}
