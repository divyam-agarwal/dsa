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
    void noValidChainInGivenOrder() {
        // Pairs in descending order — no pair can follow the previous one
        // in the given order, so longest chain is 1
        ArrayList<ArrayList<Integer>> A = pairs(
            new Integer[]{5, 6},
            new Integer[]{3, 4},
            new Integer[]{1, 2}
        );
        assertEquals(1, sol.solve(A));
    }

    @Test
    void twoDisjointChains() {
        // Chain A: (1,5) → (6,10) → (11,15), length 3
        // Chain B: (3,7) → (8,12),            length 2
        // (1,5) and (3,7) overlap so the chains cannot be merged; answer is 3
        ArrayList<ArrayList<Integer>> A = pairs(
            new Integer[]{1, 5},
            new Integer[]{3, 7},
            new Integer[]{6, 10},
            new Integer[]{8, 12},
            new Integer[]{11, 15}
        );
        assertEquals(3, sol.solve(A));
    }
}
