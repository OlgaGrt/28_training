package arraylist;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class ArrayListImpl<E> implements ArrayList<E> {

    private int defaultCapacity = 10;
    private final Object[] obj_array;
    public int size;

    public ArrayListImpl(int defaultCapacity) {
        this.defaultCapacity = defaultCapacity;
        obj_array = new Object[defaultCapacity];
    }

    public ArrayListImpl(Collection<E> collection) {
        obj_array = collection.toArray();
    }

    @Override
    public void add(int index, Object element) {

    }

    @Override
    public void addAll(Collection c) {

    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        @SuppressWarnings("unchecked")
        final E element = (E) obj_array[index];
        return element;
    }

    @Override
    public boolean isEmpty() {
        return obj_array.length == 0;
    }

    @Override
    public void remove(int index) {

    }

    @Override
    public void remove(Object o) {

    }

    @Override
    public void sort(Comparator<? super E> c) {

    }




}
