package twopointers;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class RemoveDuplicatesTest {

    RemoveDuplicates sol = new RemoveDuplicates();

    @Test
    void example1() {
        assertEquals(2, sol.removeDuplicates(new ArrayList<>(Arrays.asList(1, 1, 2))));
    }

    @Test
    void example2() {
        assertEquals(3, sol.removeDuplicates(new ArrayList<>(Arrays.asList(1, 2, 2, 3, 3))));
    }

    @Test
    void allDuplicates() {
        assertEquals(1, sol.removeDuplicates(new ArrayList<>(Arrays.asList(5, 5, 5, 5))));
    }

    @Test
    void noDuplicates() {
        assertEquals(4, sol.removeDuplicates(new ArrayList<>(Arrays.asList(1, 2, 3, 4))));
    }

    @Test
    void singleElement() {
        assertEquals(1, sol.removeDuplicates(new ArrayList<>(Arrays.asList(7))));
    }

    @Test
    void arrayIsModifiedInPlace() {
        ArrayList<Integer> a = new ArrayList<>(Arrays.asList(1, 1, 2, 3, 3));
        int len = sol.removeDuplicates(a);
        assertEquals(3, len);
        assertEquals(Arrays.asList(1, 2, 3), a.subList(0, len));
    }
}
