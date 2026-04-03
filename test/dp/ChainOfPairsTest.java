package dp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChainOfPairsTest {
    ChainOfPairs sol = new ChainOfPairs();

    private ArrayList<ArrayList<Integer>> pairs(Integer[]... pairs) {
        ArrayList<ArrayList<Integer>> A = new ArrayList<>();
        for (Integer[] p : pairs) {
            A.add(new ArrayList<>(Arrays.asList(p)));
        }
        return A;
    }

    @Test
    void interviewBitExample() {
        // Chain: (5,24) → (27,40) → (50,90)
        ArrayList<ArrayList<Integer>> A = pairs(
            new Integer[]{5, 24},
            new Integer[]{39, 60},
            new Integer[]{15, 28},
            new Integer[]{27, 40},
            new Integer[]{50, 90}
        );
        assertEquals(3, sol.solve(A));
    }

    @Test
    void singlePair() {
        assertEquals(1, sol.solve(pairs(new Integer[]{1, 5})));
    }

    @Test
    void allChainable() {
        // [1,2] → [3,4] → [5,6] → [7,8]
        ArrayList<ArrayList<Integer>> A = pairs(
            new Integer[]{1, 2},
            new Integer[]{3, 4},
            new Integer[]{5, 6},
            new Integer[]{7, 8}
        );
        assertEquals(4, sol.solve(A));
    }

    @Test
    void allOverlapping() {
        // No pair can follow any other — best chain length is 1
        ArrayList<ArrayList<Integer>> A = pairs(
            new Integer[]{1, 10},
            new Integer[]{2, 9},
            new Integer[]{3, 8}
        );
        assertEquals(1, sol.solve(A));
    }

    @Test
    void unsortedInput() {
        // Input given in reverse order; solution must reorder
        // Chain: (1,2) → (3,4) → (5,6)
        ArrayList<ArrayList<Integer>> A = pairs(
            new Integer[]{5, 6},
            new Integer[]{3, 4},
            new Integer[]{1, 2}
        );
        assertEquals(3, sol.solve(A));
    }

    @Test
    void twoDisjointChains() {
        // Longer chain: (1,2) → (3,4) → (5,6), length 3
        // Shorter chain: (10,20) → (21,30), length 2
        ArrayList<ArrayList<Integer>> A = pairs(
            new Integer[]{10, 20},
            new Integer[]{1, 2},
            new Integer[]{21, 30},
            new Integer[]{3, 4},
            new Integer[]{5, 6}
        );
        assertEquals(3, sol.solve(A));
    }
}
