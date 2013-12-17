package edu.bu.cs232;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Iterator;

public class QLLShoppingList implements Iterable<QShoppingListItem> {
	
	private LList<QShoppingListItem> itemList;


	public QLLShoppingList() {
		this.itemList = new LList<>();
	}

	public QLLShoppingList(QShoppingListItem[] sli) {
		this();
		for (QShoppingListItem qi : sli) {
			if (null != qi) {
				this.put(qi);
			}
		}
	}

	public QLLShoppingList(int i) {
		this();
	}

	public int length() {
		return this.itemList.size();
	}

	public void put(QShoppingListItem theItem) {
		if (this.containsItem(theItem)) {
			throw new IllegalArgumentException(String.format("Item %s is already in ShoppingList", theItem.getName()));
		}
		this.itemList.add(theItem);
		Collections.sort(this.itemList);
	}

	public QShoppingListItem get(String name) {
		return this.get(this.getIndexByName(name));
	}

	public int getIndexByName(String name) {
		int result = 0;
		for (QShoppingListItem qi : this.itemList) {
			if (qi.getName().equalsIgnoreCase(name)) {
				return result;
			} else {
				++result;
			}
		}
		return -1;
	}

	public QShoppingListItem get(int i) {
		return this.itemList.get(i);
	}

	public double getTotal() {
		double result = 0.0d;
		for (QShoppingListItem qi : this.itemList) {
			result += qi.getPrice() * qi.getQuantity();
		}
		return result;
	}

	@Override
	public Iterator<QShoppingListItem> iterator() {
		return this.itemList.iterator();
	}

	public boolean containsItem(QShoppingListItem theItem) {
		return this.itemList.contains(theItem);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		if (this.length() > 0) {
			NumberFormat money = NumberFormat.getCurrencyInstance();
			Collections.sort(this.itemList);
			for (QShoppingListItem sli : this) {
				if (sli != null) {
					result.append(String.format("%d\t%s (%d@%s)%n", sli.getPriority(),
							sli.getName(), sli.getQuantity(), money.format(sli.getPrice())));
				}
			}
		}
		return result.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			// Never equals null
			return false;
		} 
		if (this == o) {
			// Always equals itself
			return true;
		}
		try {
			QLLShoppingList obj = (QLLShoppingList)o;
			if (this.length() == obj.length()) {
				for (QShoppingListItem sli : this) {
					if (!obj.containsItem(sli)) {
						// ShoppingLists contain different items
						return false;
					}
				}
				// Success!
				return true;
			} else {
				// ShoppingList has different number of items
				return false;
			}
		} catch (ClassCastException ex) {
			// Wasn't a ShoppingList
			return false;
		}
	}

	public boolean remove(String name) {
		return this.itemList.remove(this.get(name));
	}

	
}
