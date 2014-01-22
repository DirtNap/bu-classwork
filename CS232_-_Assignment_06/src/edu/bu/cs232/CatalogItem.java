package edu.bu.cs232;

import java.io.Serializable;
import java.text.NumberFormat;

public class CatalogItem implements Serializable, Comparable<CatalogItem> {

    private static final long serialVersionUID = -3695407328615414842L;
    private double price;
    private String name;
    private boolean available;

    public CatalogItem(String name, double price, boolean available) {
        this.setName(name);
        this.setPrice(price);
        this.setAvailable(available);
    }

    public CatalogItem(String name, double price) {
        this(name, price, true);
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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
            return this.getName().equalsIgnoreCase(((CatalogItem) o).getName());
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

    @Override
    public String toString() {
        NumberFormat money = NumberFormat.getCurrencyInstance();
        String availability = this.isAvailable() ? "Available" : "Unavailable";
        return String.format("%s (%s) [%s]", this.getName(), money.format(this.getPrice()),
                availability);
    }
}
