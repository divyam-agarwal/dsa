package dp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShortestCommonSuperstringTest {

    ShortestCommonSuperstring sol = new ShortestCommonSuperstring();

    // ─── InterviewBit example ─────────────────────────────────────────────────

    @Test
    void example1() {
        // "geek" is a substring of "geeks" → removed.
        // No overlaps among ["geeks","quiz","for"] → 5+4+3 = 12
        assertEquals(12, sol.solve(new String[]{"geeks", "quiz", "for", "geek"}));
    }

    // ─── Trivial cases ────────────────────────────────────────────────────────

    @Test
    void singleString() {
        assertEquals(5, sol.solve(new String[]{"hello"}));
    }

    @Test
    void identicalStrings() {
        // duplicates: one removed → answer = len
        assertEquals(3, sol.solve(new String[]{"abc", "abc"}));
    }

    // ─── Substring elimination ────────────────────────────────────────────────

    @Test
    void oneIsSubstringOfAnother() {
        // "cde" is a substring of "abcde" → removed; answer = 5
        assertEquals(5, sol.solve(new String[]{"abcde", "cde"}));
    }

    @Test
    void middleIsSubstring() {
        // "bc" is a substring of "abcd" → removed; answer = len("abcd") = 4
        assertEquals(4, sol.solve(new String[]{"abcd", "bc"}));
    }

    // ─── Overlap cases ────────────────────────────────────────────────────────

    @Test
    void simpleOverlap() {
        // "ab" + "bc": suffix "b" of "ab" = prefix "b" of "bc" → overlap 1 → "abc" = 3
        assertEquals(3, sol.solve(new String[]{"ab", "bc"}));
    }

    @Test
    void largerOverlap() {
        // "abcde" + "cdefg": overlap "cde" (3) → "abcdefg" = 7
        assertEquals(7, sol.solve(new String[]{"abcde", "cdefg"}));
    }

    @Test
    void chainOverlap() {
        // best order: "abc"→"bcd"→"cde", overlaps 2+2 → 3+1+1 = 5 ("abcde")
        assertEquals(5, sol.solve(new String[]{"abc", "bcd", "cde"}));
    }

    @Test
    void reverseOverlap() {
        // "ab"→"ba": suffix "b" of "ab" = prefix "b" of "ba" → overlap 1 → "aba" = 3
        assertEquals(3, sol.solve(new String[]{"ab", "ba"}));
    }

    // ─── No overlap ───────────────────────────────────────────────────────────

    @Test
    void noOverlap() {
        // no shared suffix/prefix → just concatenate → 3+3 = 6
        assertEquals(6, sol.solve(new String[]{"abc", "xyz"}));
    }

    // ─── Multiple strings ─────────────────────────────────────────────────────

    @Test
    void threeStringsWithOverlaps() {
        // "abcd"→"cdef"→"efgh": overlaps 2+2 → 4+2+2 = 8 ("abcdefgh")
        assertEquals(8, sol.solve(new String[]{"abcd", "cdef", "efgh"}));
    }
}
