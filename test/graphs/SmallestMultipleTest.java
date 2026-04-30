package graphs;

import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import static org.junit.jupiter.api.Assertions.*;

class SmallestMultipleTest {

    SmallestMultiple sol = new SmallestMultiple();

    // ─── InterviewBit examples ───────────────────────────────────────────────

    @Test
    void interviewBitExample_2() {
        // 10 = 2 × 5
        assertEquals("10", sol.multiple(2));
    }

    @Test
    void interviewBitExample_55() {
        // 110 = 55 × 2
        assertEquals("110", sol.multiple(55));
    }

    @Test
    void interviewBitExample_17() {
        // 11101 = 17 × 653
        assertEquals("11101", sol.multiple(17));
    }

    @Test
    void interviewBitExample_12() {
        // 11100 = 12 × 925
        assertEquals("11100", sol.multiple(12));
    }

    // ─── Trivial cases ───────────────────────────────────────────────────────

    @Test
    void a_equals_1() {
        // Every positive integer is a multiple of 1; smallest with 0/1 digits is "1"
        assertEquals("1", sol.multiple(1));
    }

    @Test
    void a_equals_10() {
        // 10 = 10 × 1
        assertEquals("10", sol.multiple(10));
    }

    @Test
    void a_equals_11() {
        // 11 = 11 × 1  (the number itself uses only digit 1)
        assertEquals("11", sol.multiple(11));
    }

    // ─── Powers of 2 (answer is a power of 10) ───────────────────────────────

    @Test
    void a_equals_4() {
        // 100 = 4 × 25
        assertEquals("100", sol.multiple(4));
    }

    @Test
    void a_equals_8() {
        // 1000 = 8 × 125
        assertEquals("1000", sol.multiple(8));
    }

    // ─── Multiples of 5 (must end in 0) ─────────────────────────────────────

    @Test
    void a_equals_5() {
        // 10 = 5 × 2
        assertEquals("10", sol.multiple(5));
    }

    @Test
    void a_equals_100() {
        // 100 = 100 × 1
        assertEquals("100", sol.multiple(100));
    }

    @Test
    void a_equals_1000() {
        // 1000 = 1000 × 1
        assertEquals("1000", sol.multiple(1000));
    }

    // ─── Other cases ─────────────────────────────────────────────────────────

    @Test
    void a_equals_3() {
        // 111 = 3 × 37  (digit sum 3, divisible by 3)
        assertEquals("111", sol.multiple(3));
    }

    @Test
    void a_equals_6() {
        // 1110 = 6 × 185
        assertEquals("1110", sol.multiple(6));
    }

    @Test
    void a_equals_7() {
        // 1001 = 7 × 143
        assertEquals("1001", sol.multiple(7));
    }

    @Test
    void a_equals_13() {
        // 1001 = 13 × 77  (same value as A=7, different factorisation)
        assertEquals("1001", sol.multiple(13));
    }

    // ─── No leading zeros ────────────────────────────────────────────────────

    @Test
    void resultHasNoLeadingZeros() {
        // All answers must start with '1'
        for (int a : new int[]{2, 3, 4, 5, 6, 7, 8, 10, 11, 12, 13, 17, 55}) {
            String result = sol.multiple(a);
            assertEquals('1', result.charAt(0),
                    "Leading zero found for A=" + a + ": " + result);
        }
    }

    // ─── Result is actually a multiple ───────────────────────────────────────

    @Test
    void resultIsDivisibleByA() {
        // Spot-check divisibility for a range of inputs
        for (int a = 1; a <= 50; a++) {
            String result = sol.multiple(a);
            BigInteger val = new BigInteger(result);
            assertEquals(BigInteger.ZERO, val.mod(BigInteger.valueOf(a)),
                    "Result \"" + result + "\" is not divisible by A=" + a);
        }
    }

    // ─── Result uses only digits 0 and 1 ─────────────────────────────────────

    @Test
    void resultContainsOnlyZeroAndOne() {
        for (int a = 1; a <= 50; a++) {
            String result = sol.multiple(a);
            assertTrue(result.matches("[01]+"),
                    "Result \"" + result + "\" contains invalid digits for A=" + a);
        }
    }
}
