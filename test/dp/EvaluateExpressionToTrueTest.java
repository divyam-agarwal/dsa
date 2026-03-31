package dp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EvaluateExpressionToTrueTest {

    EvaluateExpressionToTrue sol = new EvaluateExpressionToTrue();

    // ─── InterviewBit example ─────────────────────────────────────────────────

    @Test
    void example() {
        // T|F&T^F → 5 ways (see breakdown below)
        // T|(F&T^F): (F&T)^F=F and F&(T^F)=F → T|F=T ×2
        // (T|F)&(T^F):  T&T=T ×1
        // (T|F&T)^F: (T|F)&T=T and T|(F&T)=T → T^F=T ×2
        assertEquals(5, sol.cnttrue("T|F&T^F"));
    }

    // ─── Single symbol ────────────────────────────────────────────────────────

    @Test
    void singleTrue() {
        assertEquals(1, sol.cnttrue("T"));
    }

    @Test
    void singleFalse() {
        assertEquals(0, sol.cnttrue("F"));
    }

    // ─── Two operands ─────────────────────────────────────────────────────────

    @Test
    void orTrueTrue() {
        assertEquals(1, sol.cnttrue("T|T"));
    }

    @Test
    void andTrueFalse() {
        assertEquals(0, sol.cnttrue("T&F"));
    }

    @Test
    void xorTrueTrue() {
        // T^T = F → 0 ways
        assertEquals(0, sol.cnttrue("T^T"));
    }

    @Test
    void xorTrueFalse() {
        // T^F = T → 1 way
        assertEquals(1, sol.cnttrue("T^F"));
    }

    // ─── Three operands ───────────────────────────────────────────────────────

    @Test
    void andAndTrue() {
        // T&T&T: both parens give T → 2 ways
        assertEquals(2, sol.cnttrue("T&T&T"));
    }

    @Test
    void orOrTrue() {
        // T|T|T: both parens give T → 2 ways
        assertEquals(2, sol.cnttrue("T|T|T"));
    }

    @Test
    void xorXorTrue() {
        // T^T^T: T^(T^T)=T^F=T, (T^T)^T=F^T=T → 2 ways
        assertEquals(2, sol.cnttrue("T^T^T"));
    }

    @Test
    void allFalseOrOr() {
        // F&F|F: (F&F)|F=F, F&(F|F)=F → 0 ways
        assertEquals(0, sol.cnttrue("F&F|F"));
    }

    @Test
    void mixedThree() {
        // T&T|F: (T&T)|F=T, T&(T|F)=T → 2 ways
        assertEquals(2, sol.cnttrue("T&T|F"));
    }

    // ─── Modular arithmetic ───────────────────────────────────────────────────

    @Test
    void resultIsModded() {
        // result must be in [0, 1002]
        int result = sol.cnttrue("T|T&T^T|T&T^T|T&T^T|T&T^T|T&T^T");
        assertTrue(result >= 0 && result < 1003);
    }
}
