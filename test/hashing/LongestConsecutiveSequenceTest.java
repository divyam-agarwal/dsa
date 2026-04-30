package hashing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LongestConsecutiveSequenceTest {

    LongestConsecutiveSequence sol = new LongestConsecutiveSequence();

    @Test
    void example1() {
        // [100,4,200,1,3,2] -> 1,2,3,4 = length 4
        assertEquals(4, sol.longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));
    }

    @Test
    void example2() {
        // [0,3,7,2,5,8,4,6,0,1] -> 0..8 = length 9
        assertEquals(9, sol.longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));
    }

    @Test
    void singleElement() {
        assertEquals(1, sol.longestConsecutive(new int[]{42}));
    }

    @Test
    void emptyArray() {
        assertEquals(0, sol.longestConsecutive(new int[]{}));
    }

    @Test
    void allDuplicates() {
        // all same value -> sequence length 1
        assertEquals(1, sol.longestConsecutive(new int[]{5, 5, 5}));
    }

    @Test
    void negativeNumbers() {
        // -3,-2,-1,0,1 = length 5
        assertEquals(5, sol.longestConsecutive(new int[]{-1, -3, 0, -2, 1}));
    }

    @Test
    void twoDisjointSequences() {
        // 1,2,3 and 10,11,12,13 -> longer is 4
        assertEquals(4, sol.longestConsecutive(new int[]{1, 2, 3, 10, 11, 12, 13}));
    }

    @Test
    void alreadySorted() {
        assertEquals(5, sol.longestConsecutive(new int[]{1, 2, 3, 4, 5}));
    }
}
