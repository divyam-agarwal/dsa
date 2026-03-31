package dp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WaysToDecodeTest {

    WaysToDecode sol = new WaysToDecode();

    // ─── InterviewBit examples ───────────────────────────────────────────────

    @Test
    void example1() {
        // "12" → "AB" or "L" → 2 ways
        assertEquals(2, sol.numDecodings("12"));
    }

    @Test
    void example2() {
        // "226" → "BBF", "BZ", "VF" → 3 ways
        assertEquals(3, sol.numDecodings("226"));
    }

    // ─── Zero handling ───────────────────────────────────────────────────────

    @Test
    void leadingZero() {
        // "0" cannot be decoded
        assertEquals(0, sol.numDecodings("0"));
    }

    @Test
    void leadingZeroInString() {
        // "01" is invalid — leading zero
        assertEquals(0, sol.numDecodings("01"));
    }

    @Test
    void zeroAfterValid() {
        // "10" → "J" → 1 way
        assertEquals(1, sol.numDecodings("10"));
    }

    @Test
    void zeroAfterInvalid() {
        // "30" → no valid two-digit (30 > 26), and '0' alone is invalid → 0 ways
        assertEquals(0, sol.numDecodings("30"));
    }

    @Test
    void consecutiveZeros() {
        // "100" → "10" then "0" — '0' alone is invalid → 0 ways
        assertEquals(0, sol.numDecodings("100"));
    }

    // ─── Single digit ────────────────────────────────────────────────────────

    @Test
    void singleNonZero() {
        assertEquals(1, sol.numDecodings("5"));
    }

    // ─── Boundary two-digit values ───────────────────────────────────────────

    @Test
    void exactly26() {
        // "26" → "BF" or "Z" → 2 ways
        assertEquals(2, sol.numDecodings("26"));
    }

    @Test
    void beyond26() {
        // "27" → only "BG" → 1 way (27 > 26, no two-digit decode)
        assertEquals(1, sol.numDecodings("27"));
    }

    @Test
    void exactly10() {
        // "10" → "J" → 1 way
        assertEquals(1, sol.numDecodings("10"));
    }

    // ─── All ones ────────────────────────────────────────────────────────────

    @Test
    void allOnes() {
        // "111" → "AAA", "AK", "KA" → 3 ways
        assertEquals(3, sol.numDecodings("111"));
    }

    // ─── Longer string ───────────────────────────────────────────────────────

    @Test
    void longer() {
        // "1234" → "ABCD","LCD","AWD","ABM" — 3 ways
        // 1|2|3|4, 12|3|4, 1|23|4, 12|34, 1|2|34 → checking: 34>26 so no; 12|34 invalid
        // valid: 1|2|3|4, 12|3|4, 1|23|4 → 3 ways
        assertEquals(3, sol.numDecodings("1234"));
    }
}
