package arraylist.sort;

import java.util.Comparator;

public class QuickSort {

    public static void sort(Object[] arr, Comparator c) {
        quickSort(arr, c, 0, arr.length - 1);
    }

    private static void quickSort(Object[] arr, Comparator c, int startIndex, int endIndex) {

        if (startIndex >= endIndex) {
            return;
        }

        Object pivot = arr[endIndex];
        int lp = startIndex;
        int rp = endIndex;

        while (lp < rp) {
            while (c.compare(arr[lp], pivot) <= 0 && lp < rp) {
                lp++;
            }
            while (c.compare(arr[rp], pivot) >= 0 && lp < rp) {
                rp--;
            }
            swap(arr, lp, rp);
        }

        swap(arr, lp, endIndex);

        quickSort(arr, c, startIndex, lp - 1);
        quickSort(arr, c, lp + 1, endIndex);
    }


    private static void swap(Object[] arr, int x, int y) {
        Object o = arr[x];
        arr[x] = arr[y];
        arr[y] = o;
    }
}
