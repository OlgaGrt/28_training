package arraylist;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CustomListTest {

    @Test
    public void sortTest() {
        CustomList<Integer> customArrayList = new CustomArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1));
        CustomList<Integer> expected = new CustomArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        customArrayList.sort(Integer::compareTo);

        assertEquals(expected.get(0), customArrayList.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getTest() {
        CustomArrayList<Object> list= new CustomArrayList<>();
        list.get(0);
    }

    @Test
    public void addTest() {
        CustomList<Object> testArrayList = new CustomArrayList<>();
        assertTrue(testArrayList.add(1));
    }

    @Test
    public void addIndexTest() {
        CustomList<Object> testArrayList = new CustomArrayList<>();
        testArrayList.add(1);
        testArrayList.add(3);

        assertTrue(testArrayList.add(0, 10));
        assertEquals(10, testArrayList.get(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void addOutOfBoundIndexTest() {
        CustomList<Object> testArrayList = new CustomArrayList<>();
        testArrayList.add(10, 1);
    }

    @Test
    public void isEmptyTest() {
        CustomList<Object> testArrayList = new CustomArrayList<>();
        assertTrue(testArrayList.isEmpty());
        testArrayList.add(0);
        assertFalse(testArrayList.isEmpty());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void removeByWrongIndexTest() {
        CustomList<Object> testArrayList = new CustomArrayList<>();
        testArrayList.remove(0);
    }

    @Test
    public void removeByIndexTest() {
        CustomList<Object> testArrayList = new CustomArrayList<>();
        testArrayList.add(1);
        testArrayList.add(2);
        testArrayList.remove(0);
        CustomList<Integer> expected = new CustomArrayList<>(List.of(2));
        assertEquals(expected.get(0), testArrayList.get(0));
    }

    @Test
    public void removeNullObjectTest() {

        Object nullObj = null;

        CustomList<Object> testArrayList = new CustomArrayList<>();
        testArrayList.add(1);
        testArrayList.add(nullObj);

        assertTrue(testArrayList.remove(nullObj));
    }

    @Test
    public void removeObjectTest() {
        Integer toRemove = 3;

        CustomList<Integer> testArrayList = new CustomArrayList<>();
        testArrayList.add(2);
        testArrayList.add(1);
        testArrayList.add(toRemove);

        assertTrue(testArrayList.remove(new Integer(3)));
        assertFalse(testArrayList.remove(new Integer(99)));
        assertFalse(testArrayList.remove(null));
    }


    @Test
    public void addAllTest() {
        CustomList<Integer> testArrayList = new CustomArrayList<>();
        testArrayList.add(1);
        testArrayList.add(1);

        assertTrue(testArrayList.addAll(Arrays.asList(2, 2, 2)));
        assertEquals(testArrayList.size(), 5);
    }

    @Test
    public void addAllToEmptyListTest() {
        CustomList<Integer> testArrayList = new CustomArrayList<>();
        assertTrue(testArrayList.addAll(Arrays.asList(2, 2, 2)));
        assertEquals(testArrayList.size(), 3);
    }
}
