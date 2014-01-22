package edu.bu.cs232;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import java.util.Arrays;
import org.junit.Test;

public class ListSorterTest extends ListSorterTestBase {
    @Test
    public void testBubbleSort() {
        Arrays.sort(this.baseArray);
        this.bubbleSort(this.testArray);
        assertArrayEquals(this.baseArray, this.testArray);
    }

    @Test
    public void testBubbleEmpty() {
        this.bubbleSort(this.emptyArray);
    }

    @Test
    public void testBubbleSparse() {
        this.bubbleSort(this.sparseArray);
        assertNull(this.sparseArray[this.sparseArray.length - 1]);
    }

    @Test
    public void testQuickSort() {
        Arrays.sort(this.baseArray);
        this.quickSort(this.testArray);
        assertArrayEquals(this.baseArray, this.testArray);
    }

    @Test
    public void testQuickEmpty() {
        this.quickSort(this.emptyArray);
    }

    @Test
    public void testQuickSparse() {
        this.quickSort(this.sparseArray);
        assertNull(this.sparseArray[this.sparseArray.length - 1]);
    }

    @Test
    public void testSelectionSort() {
        Arrays.sort(this.baseArray);
        this.selectionSort(this.testArray);
        assertArrayEquals(this.baseArray, this.testArray);
    }

    @Test
    public void testSelectionEmpty() {
        this.selectionSort(this.emptyArray);
    }

    @Test
    public void testSelectionSparse() {
        this.selectionSort(this.sparseArray);
        assertNull(this.sparseArray[this.sparseArray.length - 1]);
    }

    @Test
    public void testMergeSparse() {
        this.mergeSort(this.sparseArray);
        assertNull(this.sparseArray[this.sparseArray.length - 1]);
    }

    @Test
    public void testMergeSort() {
        Arrays.sort(this.baseArray);
        this.mergeSort(this.testArray);
        assertArrayEquals(this.baseArray, this.testArray);
    }

    @Test
    public void testMergeEmpty() {
        this.mergeSort(this.emptyArray);
    }

    @Test
    public void testDefaultSort() {
        Arrays.sort(this.baseArray);
        this.defaultSort(this.testArray);
        assertArrayEquals(this.baseArray, this.testArray);
    }

    public void testDefaultEmpty() {
        this.defaultSort(this.emptyArray);
    }

    public void testDefaultSparse() {
        this.defaultSort(this.sparseArray);
        assertNull(this.sparseArray[this.sparseArray.length - 1]);
    }

}
