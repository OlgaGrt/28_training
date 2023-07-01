package arraylist;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

public interface CustomArrayList<E> {
    void add(int index, E element);
    void  addAll(Collection<? extends E> c);
    void  clear();
    E get(int index);
    boolean isEmpty();
    void  remove(int index);
    void  remove(Object o);
    void  sort(Comparator<? super E> c) ;//(quicksort, merge sort)


    public static void main(String[] args) {
        Collection<String> a = new LinkedList<>();
    }
}

