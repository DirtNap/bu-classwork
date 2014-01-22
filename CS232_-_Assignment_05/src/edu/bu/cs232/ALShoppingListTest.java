package edu.bu.cs232;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class ALShoppingListTest {

    private ShoppingListItem[] testSLIArray;
    private ShoppingListItem[] sortedSLIArray;
    private ALShoppingList testALShoppingList;
    private ShoppingListItem testItem;

    @Before
    public void setUp() throws Exception {
        this.testSLIArray = new ShoppingListItem[4];
        this.sortedSLIArray = new ShoppingListItem[4];
        for (int i = 0; i < this.testSLIArray.length; ++i) {
            int priority = i;
            if (priority > 3) {
                priority = priority % 3;
            }
            ShoppingListItem testItem = new ShoppingListItem(String.format("Test Item %d", i),
                    priority, i);
            this.testSLIArray[i] = testItem;
            this.sortedSLIArray[i] = testItem;
        }
        Arrays.sort(this.sortedSLIArray);
        this.testALShoppingList = new ALShoppingList(this.testSLIArray);
        this.testItem = new ShoppingListItem("Test Name", 7, 8.20d);
    }

    @Test
    public final void testGet() {
        for (int i = 0; i < this.testSLIArray.length; ++i) {
            assertEquals(this.testSLIArray[i], this.testALShoppingList.get(i));
            assertEquals(this.testSLIArray[i],
                    this.testALShoppingList.get(this.testSLIArray[i].getName()));
        }
    }

    @Test
    public final void testSortItems() {
        this.testALShoppingList.sortItems();
        for (int i = 0; i < this.testSLIArray.length; ++i) {
            assertEquals(this.sortedSLIArray[i], this.testALShoppingList.get(i));
        }
    }

    @Test
    public final void testPut() {
        ALShoppingList localALShoppingList = new ALShoppingList(1);
        localALShoppingList.put(this.testItem);
        assertEquals(this.testItem, localALShoppingList.get(this.testItem.getName()));
    }

    @Test
    public final void testDoSorting() {
        this.testALShoppingList.doSorting(this.sortedSLIArray);
    }

    @Test(expected = ArrayListItemMismatchException.class)
    public final void testDoSortingException() {
        this.sortedSLIArray[0] = this.testItem;
        this.testALShoppingList.doSorting(this.sortedSLIArray);
    }

    @Test
    public final void testLength() {
        assertEquals(this.testSLIArray.length, this.testALShoppingList.length());
    }
}
