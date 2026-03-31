package dp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegularExpressionIITest {

    RegularExpressionII sol = new RegularExpressionII();

    // ─── InterviewBit examples ───────────────────────────────────────────────

    @Test
    void example1() {
        // "aab" matches "c*a*b": c* = zero c's, a* = two a's, b = b
        assertEquals(1, sol.isMatch("aab", "c*a*b"));
    }

    @Test
    void example2() {
        // "aa" does not match "a": pattern too short
        assertEquals(0, sol.isMatch("aa", "a"));
    }

    // ─── Exact match ─────────────────────────────────────────────────────────

    @Test
    void exactMatch() {
        assertEquals(1, sol.isMatch("aa", "aa"));
    }

    @Test
    void exactMatchSingle() {
        assertEquals(1, sol.isMatch("a", "a"));
    }

    // ─── Dot wildcard ────────────────────────────────────────────────────────

    @Test
    void dotMatchesSingle() {
        // "." matches any one character
        assertEquals(1, sol.isMatch("a", "."));
    }

    @Test
    void dotDoesNotMatchMultiple() {
        assertEquals(0, sol.isMatch("ab", "."));
    }

    @Test
    void dotStar() {
        // ".*" matches any string
        assertEquals(1, sol.isMatch("ab", ".*"));
    }

    @Test
    void dotStarMatchesEmpty() {
        assertEquals(1, sol.isMatch("", ".*"));
    }

    // ─── Star: zero or more ──────────────────────────────────────────────────

    @Test
    void starZeroOccurrences() {
        // "b*" matches "" (zero b's), but string is "a" → no match
        assertEquals(0, sol.isMatch("a", "b*"));
    }

    @Test
    void starZeroOccurrencesMatchesEmpty() {
        assertEquals(1, sol.isMatch("", "a*"));
    }

    @Test
    void starMultipleOccurrences() {
        // "a*" matches "aaa"
        assertEquals(1, sol.isMatch("aaa", "a*"));
    }

    @Test
    void starOneOccurrence() {
        assertEquals(1, sol.isMatch("a", "a*"));
    }

    // ─── Empty string / pattern ───────────────────────────────────────────────

    @Test
    void emptyBoth() {
        assertEquals(1, sol.isMatch("", ""));
    }

    @Test
    void emptyStringNonEmptyPattern() {
        assertEquals(0, sol.isMatch("", "a"));
    }

    @Test
    void emptyStringCollapsiblePattern() {
        // "a*b*c*" can collapse to empty
        assertEquals(1, sol.isMatch("", "a*b*c*"));
    }

    @Test
    void nonEmptyStringEmptyPattern() {
        assertEquals(0, sol.isMatch("a", ""));
    }

    // ─── Combined patterns ────────────────────────────────────────────────────

    @Test
    void dotStarWithLiteral() {
        // ".*c" matches any string ending in 'c'
        assertEquals(1, sol.isMatch("abc", ".*c"));
        assertEquals(0, sol.isMatch("abc", ".*d"));
    }

    @Test
    void complexPattern() {
        // "a*b.c*" → zero+ a's, one b, any char, zero+ c's
        assertEquals(1, sol.isMatch("bx", "a*b.c*"));
        assertEquals(0, sol.isMatch("b", "a*b.c*"));  // needs one char after b
    }

    @Test
    void noMatchLongerString() {
        assertEquals(0, sol.isMatch("aaa", "aa"));
    }
}
