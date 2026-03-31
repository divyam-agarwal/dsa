package dp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LongestCommonSubsequenceTest {

    LongestCommonSubsequence sol = new LongestCommonSubsequence();

    // ─── InterviewBit examples ───────────────────────────────────────────────

    @Test
    void example1() {
        // "acd" is the LCS of "abbcdgf" and "bbadcgf" ... actually let's verify:
        // InterviewBit: A="acd", B="ace" → LCS="ac" (length 2)? No:
        // A="acd", B="ace": a-c in both → "ac" len 2, or a-c-? no d≠e → 2
        // InterviewBit example: A="abbcdgf", B="bbadcgf" → LCS length 5 ("bbcgf" or "badgf")
        assertEquals(5, sol.solve("abbcdgf", "bbadcgf"));
    }

    @Test
    void example2() {
        // A="acd", B="ace" → LCS="ac" length 2
        assertEquals(2, sol.solve("acd", "ace"));
    }

    // ─── Identical strings ───────────────────────────────────────────────────

    @Test
    void identicalStrings() {
        assertEquals(6, sol.solve("abcdef", "abcdef"));
    }

    // ─── No common characters ────────────────────────────────────────────────

    @Test
    void noCommonChars() {
        assertEquals(0, sol.solve("abc", "xyz"));
    }

    // ─── One empty string ────────────────────────────────────────────────────

    @Test
    void firstEmpty() {
        assertEquals(0, sol.solve("", "abc"));
    }

    @Test
    void secondEmpty() {
        assertEquals(0, sol.solve("abc", ""));
    }

    @Test
    void bothEmpty() {
        assertEquals(0, sol.solve("", ""));
    }

    // ─── Single character ────────────────────────────────────────────────────

    @Test
    void singleCharMatch() {
        assertEquals(1, sol.solve("a", "a"));
    }

    @Test
    void singleCharNoMatch() {
        assertEquals(0, sol.solve("a", "b"));
    }

    // ─── One string is a subsequence of the other ────────────────────────────

    @Test
    void subsequenceOfOther() {
        // "ace" is a subsequence of "abcde"
        assertEquals(3, sol.solve("abcde", "ace"));
    }

    // ─── Repeated characters ─────────────────────────────────────────────────

    @Test
    void repeatedChars() {
        // A="aaa", B="aa" → LCS="aa" length 2
        assertEquals(2, sol.solve("aaa", "aa"));
    }

    // ─── Longer strings ──────────────────────────────────────────────────────

    @Test
    void longerStrings() {
        // A="AGGTAB", B="GXTXAYB" → LCS="GTAB" length 4
        assertEquals(4, sol.solve("AGGTAB", "GXTXAYB"));
    }
}
