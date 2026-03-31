package twopointers;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class CountingSubarraysTest {

    CountingSubarrays sol = new CountingSubarrays();

    @Test
    void basicCase() {
        assertEquals(4, sol.solve(new ArrayList<>(Arrays.asList(2, 5, 6)), 10)); // [2],[5],[6],[2,5]
    }

    @Test
    void allElementsBelowB() {
        assertEquals(6, sol.solve(new ArrayList<>(Arrays.asList(1, 2, 3)), 10));
    }

    @Test
    void singleElementBelowB() {
        assertEquals(1, sol.solve(new ArrayList<>(Arrays.asList(3)), 5));
    }

    @Test
    void singleElementEqualToB() {
        assertEquals(0, sol.solve(new ArrayList<>(Arrays.asList(5)), 5));
    }

    @Test
    void allElementsAboveOrEqualB() {
        assertEquals(0, sol.solve(new ArrayList<>(Arrays.asList(10, 20, 30)), 5));
    }
}
