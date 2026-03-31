package dp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WaysToColor3xNBoardTest {

    WaysToColor3xNBoard sol = new WaysToColor3xNBoard();

    // ─── Small N (hand-verified via state transfer) ───────────────────────────
    // Valid column colorings split into two types (4 colors, top≠mid, mid≠bot):
    //   Type A (top=bot): 12 states   Type B (top≠bot): 24 states
    // Transition counts per state:  A→A=7, A→B=10, B→A=5, B→B=11
    //
    // f(n,A) = 7·f(n-1,A) + 5·f(n-1,B)
    // f(n,B) = 10·f(n-1,A) + 11·f(n-1,B)

    @Test
    void n1() {
        // 4×3×3 = 36  (top: 4, mid: 3, bot: 3 — top and bot are not adjacent)
        assertEquals(36, sol.solve(1));
    }

    @Test
    void n2() {
        // f(2,A)=204, f(2,B)=384 → 588
        assertEquals(588, sol.solve(2));
    }

    @Test
    void n3() {
        // f(3,A)=3348, f(3,B)=6264 → 9612
        assertEquals(9612, sol.solve(3));
    }

    @Test
    void n4() {
        // f(4,A)=54756, f(4,B)=102384 → 157140
        assertEquals(157140, sol.solve(4));
    }

    // ─── Modular arithmetic ───────────────────────────────────────────────────

    @Test
    void modDoesNotOverflow() {
        // result must be in [0, 10^9+6]
        int result = sol.solve(100000);
        assertTrue(result >= 0 && result < 1_000_000_007);
    }

    @Test
    void largeNIsConsistent() {
        // solve(n) and solve(n+1) must both be valid mod values
        int r1 = sol.solve(10000);
        int r2 = sol.solve(10001);
        assertTrue(r1 >= 0 && r1 < 1_000_000_007);
        assertTrue(r2 >= 0 && r2 < 1_000_000_007);
    }
}
