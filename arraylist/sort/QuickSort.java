package arraylist.sort;

import java.util.Comparator;
import java.util.Random;

public class QuickSort {

    public static <T> void sort(T[] arr, Comparator<? super T> c) {
        quickSort(arr, 0, arr.length - 1, c);
    }

    private static <T> void quickSort(T[] arr, int startIndex, int endIndex, Comparator<? super T> c) {

        if (startIndex >= endIndex) {
            return;
        }

        int pivotIndex = new Random().nextInt(endIndex - startIndex) + startIndex;
        T pivot = arr[pivotIndex];
        swap(arr, pivotIndex, endIndex);

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

        quickSort(arr, startIndex, lp - 1, c);
        quickSort(arr, lp + 1, endIndex, c);
    }


    private static void swap(Object[] arr, int x, int y) {
        Object o = arr[x];
        arr[x] = arr[y];
        arr[y] = o;
    }
}
