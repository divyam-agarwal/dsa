package slidingwindow;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MinimumWindowSubstringTest {

    MinimumWindowSubstring sol = new MinimumWindowSubstring();

    @Test
    void example1() {
        // classic LC example
        assertEquals("BANC", sol.minWindow("ADOBECODEBANC", "ABC"));
    }

    @Test
    void example2() {
        // s == t -> answer is s itself
        assertEquals("a", sol.minWindow("a", "a"));
    }

    @Test
    void noValidWindow() {
        // t has a char not in s
        assertEquals("", sol.minWindow("a", "aa"));
    }

    @Test
    void duplicatesInT() {
        // t = "aaab", need at least 3 a's and 1 b
        assertEquals("aaab", sol.minWindow("aaab", "aaab"));
    }

    @Test
    void windowNotAtStart() {
        assertEquals("ba", sol.minWindow("xyzba", "ab"));
    }

    @Test
    void windowNotAtEnd() {
        assertEquals("ab", sol.minWindow("abxyz", "ab"));
    }

    @Test
    void multipleValidWindows() {
        // "BANC" and "BECODEBA" both contain ABC, but "BANC" is shorter
        assertEquals("BANC", sol.minWindow("ADOBECODEBANC", "ABC"));
    }

    @Test
    void tLongerThanS() {
        assertEquals("", sol.minWindow("ab", "abc"));
    }

    @Test
    void singleCharMatch() {
        assertEquals("b", sol.minWindow("xbyz", "b"));
    }
}
