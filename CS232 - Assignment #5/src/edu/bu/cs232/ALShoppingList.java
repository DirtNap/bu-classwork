package edu.bu.cs232;

import java.util.ArrayList;
import java.util.Collections;

public class ALShoppingList extends ShoppingList {
	private ArrayList<ShoppingListItem> listItemsArrayList;
	
	protected ALShoppingList(ShoppingListItem [] listItems, boolean existing) {
		super(listItems, existing);
		this.listItemsArrayList = new ArrayList<ShoppingListItem>(listItems.length);
		this.setSortProvider(this);
		if (existing) {
			this.currentIndex = 0;
			for (ShoppingListItem sli : listItems) {
				this.listItemsArrayList.add(sli);
			}
			this.buildIndex(this.listItemsArrayList.toArray(listItems));
		}
	}
	public ALShoppingList(ShoppingListItem [] listItems) {
		this(listItems, true);
	}
	public ALShoppingList(int itemCount) {
		this(new ShoppingListItem[itemCount], false);
	}
	public ALShoppingList() {
		this(ShoppingList.DEFAULT_ARRAY_SIZE);
	}
	
	@Override
	public ShoppingListItem get(int index) {
		try {
			return this.listItemsArrayList.get(index);
		} catch (IndexOutOfBoundsException ex) {
			return super.get(index);
		}
	}
	@Override
	public void put(ShoppingListItem item) {
		super.put(item);
		this.listItemsArrayList.add(this.getIndexByName(item.getName()), item);
	}
	@Override
	protected void sortItems() {
		if (this.listItemsArrayList != null) {
			this.getSortProvider().doSorting(this.listItemsArrayList.toArray(new ShoppingListItem[0]));
			this.validateItemMap();
		}
	}
	@Override
	public void doSorting(ShoppingListItem[] theList) {
		for (ShoppingListItem sli : theList) {
			if (! this.listItemsArrayList.contains(sli)) {
				throw new ArrayListItemMismatchException(String.format("Item %s not found in local collection.", sli));
			}
		}
		Collections.sort(this.listItemsArrayList);
	}
}
