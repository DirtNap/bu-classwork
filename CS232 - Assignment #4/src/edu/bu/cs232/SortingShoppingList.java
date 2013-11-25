package edu.bu.cs232;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;

public class SortingShoppingList extends ListSorter implements Iterator<ShoppingListItem>, Iterable<ShoppingListItem> {
	private ShoppingListItem[] listItems;
	private HashMap<String, Integer> itemIndex;
	private int currentIndex;
	private int currentIterIndex;

	private SortingShoppingList(ShoppingListItem [] listItems, boolean existing) {
		this.listItems = listItems;
		this.itemIndex = new HashMap<String, Integer>(this.listItems.length);
		this.currentIndex = 0;
		if (existing) {
			for (ShoppingListItem sli : listItems) {
				this.addToItemIndex(sli.getName(), this.currentIndex++);
			}
			this.sortItems();
		}
	}
	public SortingShoppingList(ShoppingListItem [] listItems) {
		this(listItems, true);
	}
	public SortingShoppingList(int itemCount) {
		this(new ShoppingListItem[itemCount], false);
	}
	public SortingShoppingList() {
		this(ShoppingList.DEFAULT_ARRAY_SIZE);
	}
	public int length() {
		return this.currentIndex;
	}
	public ShoppingListItem get(int index) {
		return this.listItems[index];
	}
	public ShoppingListItem get(String itemName) {
		return this.listItems[this.getIndexByName(itemName)];
	}
	public int getIndexByName(String itemName) {
		Integer index = this.itemIndex.get(itemName.toLowerCase());
		if (index == null) {
			throw new ArrayIndexOutOfBoundsException(String.format("Item %s not in ShoppingList", itemName));
		}
		return index;
	}
	protected void sortItems() {
		this.doSorting(this.listItems);
		for (int i = 0; i < this.currentIndex; ++i) {
			if (this.addToItemIndex(this.listItems[i].getName(), i)) {
				throw new RuntimeException("Corrupt map.  Unable to continue");
			}
		}
	}
	protected boolean addToItemIndex(String itemName, Integer index) {
		Integer previous = this.itemIndex.put(itemName.toLowerCase(), index);
		return (previous == null);
	}
	public boolean containsItem(ShoppingListItem item) {
		return this.itemIndex.containsKey(item.getName().toLowerCase());
	}
	public void put(ShoppingListItem item) {
		if (this.containsItem(item)) {
			throw new IllegalArgumentException(String.format("Item %s is already in ShoppingList", item.getName()));
		}
		int putIndex = this.currentIndex++;
		this.listItems[putIndex] = item;
		this.addToItemIndex(item.getName(), putIndex);
		this.sortItems();
	}
	@Override
	public Iterator<ShoppingListItem> iterator() {
		this.currentIterIndex = 0;
		return this;
	}
	@Override
	public boolean hasNext() {
		return (this.currentIterIndex < this.currentIndex);
	}
	@Override
	public ShoppingListItem next() {
		return this.get(this.currentIterIndex++);
	}
	@Override
	public void remove() {
		throw new UnsupportedOperationException("remove method not implemented");
	}
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
			SortingShoppingList obj = (SortingShoppingList)o;
			if (this.length() == obj.length()) {
				for (ShoppingListItem sli : this) {
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
	@Override
	public String toString() {
		StringBuilder retVal = new StringBuilder();
		NumberFormat money = NumberFormat.getCurrencyInstance();
		this.sortItems();
		for (ShoppingListItem sli : this.listItems) {
			if (sli != null) {
				retVal.append(String.format("%d\t%s (%s)%n", sli.getPriority(),
						sli.getName(), money.format(sli.getPrice())));
			}
		}
		return retVal.toString();
	}
	public double getTotal() {
		double total = 0;
		for (ShoppingListItem sli : this.listItems) {
			if (sli != null) {
				total += sli.getPrice();
			}
		}
		return total;
	}
	@Override
	public void doSorting(ShoppingListItem[] theList) {
		this.quickSort(theList);
	}
}
