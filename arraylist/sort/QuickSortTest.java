package arraylist.sort;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertArrayEquals;

public class QuickSortTest {

    @Test
    public void sortTest() {
        Object[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        Object[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        QuickSort quickSort = new QuickSort();
        quickSort.sort(arr, Comparator.comparingInt(x->(Integer) x));
        assertArrayEquals(expected, arr);
    }

    @Test
    public void sortReverseOrderTest() {
        Object[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Object[] expected =  {9, 8, 7, 6, 5, 4, 3, 2, 1};

        QuickSort quickSort = new QuickSort();
        quickSort.sort(arr, Comparator.comparingInt(x->(Integer) x).reversed());
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testTwoElementsInArray() {
        Integer[] arr = {2, 1};
        Integer[] expected = {1, 2};

        QuickSort quickSort = new QuickSort();
        quickSort.sort(arr, Comparator.comparingInt(x->(Integer) x));
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testOneElementInArray() {
        Integer[] arr = {0};
        Integer[] expected = {0};

        QuickSort quickSort = new QuickSort();
        quickSort.sort(arr, Comparator.comparingInt(x->(Integer) x));
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testEmptyArr() {
        Integer[] arr = {};
        Integer[] expected = {};

        QuickSort quickSort = new QuickSort();
        quickSort.sort(arr, Comparator.comparingInt(x->(Integer) x));
        assertArrayEquals(expected, arr);
    }
}
