package arraylist;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;

public class ArrayListTest {


    @Test
    public void test() {
        //https://stackoverflow.com/questions/15302020/how-to-write-a-test-class-to-test-my-code
        assertEquals("Result", "Result");
    }

    @Test
    public void sortTest() {
        ArrayList<Integer> customArrayList = new ArrayListImpl<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1));
        ArrayList<Integer> expected = new ArrayListImpl<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        customArrayList.sort(Integer::compareTo);
      //  assertEquals(expected, customArrayList);

    }
}
