package arraylist;

import arraylist.sort.QuickSort;

import java.util.*;

/**
 * Resizable-array implementation of the CustomList interface
 *
 * @param <E>
 */
public class CustomArrayList<E> implements CustomList<E> {

    private final static int DEFAULT_CAPACITY = 10;

    private final static Object[] EMPTY_OBJ_ARRAY = {};

    private Object[] obj_array;

    private int size = 0;

    public CustomArrayList() {
        obj_array = EMPTY_OBJ_ARRAY;
    }

    public CustomArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            obj_array = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            obj_array = EMPTY_OBJ_ARRAY;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    public CustomArrayList(Collection<E> collection) {
        obj_array = collection.toArray();
    }

    @Override
    public boolean add(Object element) {
        if (obj_array.length >= size) {
            obj_array = extendArraySize(obj_array);
        }
        obj_array[size] = element;

        size++;
        return true;
    }

    @Override
    public boolean add(int index, Object element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
        }

        if (obj_array.length >= size) {
            obj_array = extendArraySize(obj_array);
        }

        System.arraycopy(obj_array, index, obj_array, index + 1, size - index);

        obj_array[index] = element;
        size++;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {

        int sizeToAdd = c.size();

        if (obj_array.length - size < sizeToAdd) {
            obj_array = extendArraySize(obj_array, sizeToAdd);
        }

        System.arraycopy(c.toArray(), 0, obj_array, size, sizeToAdd);
        size += sizeToAdd;
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            obj_array[i] = null;
        }
        size = 0;
    }

    @Override
    public E get(int index) {
        @SuppressWarnings("unchecked") final E element = (E) obj_array[index];
        return element;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size);
        }
        if (size - (index + 1) >= 0)
            System.arraycopy(obj_array, index + 1, obj_array, index + 1 - 1, size - (index + 1));
        obj_array[size - 1] = null;
        size--;
    }

    @Override
    public boolean remove(Object o) {
        boolean found = false;
        int indexToDelete = 0;

        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (obj_array[i] == null) {
                    indexToDelete = i;
                    found = true;
                    break;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(obj_array[i])) {
                    indexToDelete = i;
                    found = true;
                    break;
                }
            }
        }

        if (found) {
            remove(indexToDelete);
        }

        return found;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> c) {
        QuickSort.sort((E[]) obj_array, c);
    }

    @Override
    public int size() {
        return size;
    }

    private Object[] extendArraySize(Object[] arr) {
        return extendArraySize(arr, size + 1);
    }

    private Object[] extendArraySize(Object[] arr, int sizeToAdd) {
        int prevSize = arr.length;
        int addSize = prevSize += prevSize >> 1;
        if ((prevSize + sizeToAdd) > addSize) {
            addSize = prevSize + sizeToAdd;
        }
        return Arrays.copyOf(arr, addSize);
    }

}
