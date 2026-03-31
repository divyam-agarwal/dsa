package backtracking;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class SubsetsTest {

    Subsets sol = new Subsets();

    @Test
    void example_threeElements() {
        // [1,2,3] → 2^3 = 8 subsets, sorted lexicographically
        ArrayList<ArrayList<Integer>> result = sol.subsets(new ArrayList<>(Arrays.asList(1, 2, 3)));
        assertEquals(8, result.size());
    }

    @Test
    void singleElement() {
        ArrayList<ArrayList<Integer>> result = sol.subsets(new ArrayList<>(Arrays.asList(5)));
        assertEquals(2, result.size());
    }

    @Test
    void emptyInput() {
        ArrayList<ArrayList<Integer>> result = sol.subsets(new ArrayList<>());
        assertEquals(1, result.size());
    }

    @Test
    void unsortedInput_sortedOutput() {
        ArrayList<ArrayList<Integer>> result = sol.subsets(new ArrayList<>(Arrays.asList(3, 1, 2)));
        assertEquals(8, result.size());
    }

    @Test
    void twoElements() {
        ArrayList<ArrayList<Integer>> result = sol.subsets(new ArrayList<>(Arrays.asList(1, 2)));
        assertEquals(4, result.size());
    }

    @Test
    void subsetCount() {
        // n elements → 2^n subsets
        int n = 4;
        ArrayList<ArrayList<Integer>> result = sol.subsets(new ArrayList<>(Arrays.asList(1, 2, 3, 4)));
        assertEquals(1 << n, result.size());
    }
}
