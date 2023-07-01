package arraylist;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

public class CustomArrayListTest {


    @Test
    public void test() {
        //https://stackoverflow.com/questions/15302020/how-to-write-a-test-class-to-test-my-code
        assertEquals("Result", "Result");
    }

    @Test
    public void sortTest() {
        CustomArrayList<Integer> customArrayList = new CustomArrayListImpl<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1));
        CustomArrayList<Integer> expected = new CustomArrayListImpl<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        customArrayList.sort(Integer::compareTo);
      //  assertEquals(expected, customArrayList);

    }
}
