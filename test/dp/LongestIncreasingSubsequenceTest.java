package dp;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LongestIncreasingSubsequenceTest {

    LongestIncreasingSubsequence sol = new LongestIncreasingSubsequence();

    private List<Integer> list(int... vals) {
        List<Integer> a = new ArrayList<>();
        for (int v : vals) a.add(v);
        return a;
    }

    // ─── InterviewBit examples ────────────────────────────────────────────────

    @Test
    void example1() {
        // [0,8,4,12,2,10,6,14,1,9,5,13,3,11,7,15] → LIS length 6 e.g. 0,2,6,9,11,15
        assertEquals(6, sol.lis(list(0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15)));
    }

    // ─── Trivial cases ────────────────────────────────────────────────────────

    @Test
    void singleElement() {
        assertEquals(1, sol.lis(list(5)));
    }

    @Test
    void emptyArray() {
        assertEquals(0, sol.lis(list()));
    }

    // ─── Already sorted ───────────────────────────────────────────────────────

    @Test
    void strictlyIncreasing() {
        // entire array is the LIS
        assertEquals(5, sol.lis(list(1, 2, 3, 4, 5)));
    }

    @Test
    void strictlyDecreasing() {
        // every element is its own LIS
        assertEquals(1, sol.lis(list(5, 4, 3, 2, 1)));
    }

    // ─── Duplicates ───────────────────────────────────────────────────────────

    @Test
    void allSame() {
        // strictly increasing — equal elements don't extend LIS
        assertEquals(1, sol.lis(list(3, 3, 3, 3)));
    }

    @Test
    void duplicatesInMix() {
        // [1,3,2,3,4] → 1,2,3,4 → length 4
        assertEquals(4, sol.lis(list(1, 3, 2, 3, 4)));
    }

    // ─── General cases ────────────────────────────────────────────────────────

    @Test
    void lisInMiddle() {
        // [3,10,2,1,20] → 3,10,20 → length 3
        assertEquals(3, sol.lis(list(3, 10, 2, 1, 20)));
    }

    @Test
    void multipleChoices() {
        // [10,9,2,5,3,7,101,18] → 2,3,7,18 or 2,5,7,101 → length 4
        assertEquals(4, sol.lis(list(10, 9, 2, 5, 3, 7, 101, 18)));
    }

    @Test
    void twoElements_increasing() {
        assertEquals(2, sol.lis(list(1, 2)));
    }

    @Test
    void twoElements_decreasing() {
        assertEquals(1, sol.lis(list(2, 1)));
    }
}
