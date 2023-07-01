package arraylist;

import java.util.Comparator;

public interface Sort {
    void sort(Integer[] arr);
    void sort(Object[] arr, Comparator c);
}
