package twopointers;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class SubarraysWithDistinctIntegersTest {

    SubarraysWithDistinctIntegers sol = new SubarraysWithDistinctIntegers();

    @Test
    void example1() {
        assertEquals(7, sol.solve(new ArrayList<>(Arrays.asList(1, 2, 1, 2, 3)), 2));
    }

    @Test
    void example2() {
        assertEquals(3, sol.solve(new ArrayList<>(Arrays.asList(1, 2, 1, 3, 4)), 3));
    }

    @Test
    void singleElement() {
        assertEquals(1, sol.solve(new ArrayList<>(Arrays.asList(1)), 1));
    }

    @Test
    void allSame() {
        // [1],[1],[1] — only subarrays with exactly 1 distinct
        assertEquals(6, sol.solve(new ArrayList<>(Arrays.asList(1, 1, 1)), 1));
    }
}
