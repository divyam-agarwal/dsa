package dp;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class MinJumpsArrayTest {

    MinJumpsArray sol = new MinJumpsArray();

    private ArrayList<Integer> list(int... vals) {
        ArrayList<Integer> a = new ArrayList<>();
        for (int v : vals) a.add(v);
        return a;
    }

    // ─── InterviewBit examples ───────────────────────────────────────────────

    @Test
    void example1() {
        // [2,3,1,1,4] → jump 1 to index 1 (val 3), then jump 3 to end → 2 jumps
        assertEquals(2, sol.jump(list(2, 3, 1, 1, 4)));
    }

    @Test
    void example2() {
        // [2,3,0,1,4] → same two-jump path via index 1
        assertEquals(2, sol.jump(list(2, 3, 0, 1, 4)));
    }

    // ─── Trivial cases ───────────────────────────────────────────────────────

    @Test
    void singleElement() {
        // already at last index
        assertEquals(0, sol.jump(list(0)));
    }

    @Test
    void twoElementsReachable() {
        assertEquals(1, sol.jump(list(1, 0)));
    }

    // ─── Impossible cases ────────────────────────────────────────────────────

    @Test
    void firstElementZero() {
        // can't move at all
        assertEquals(-1, sol.jump(list(0, 1, 2)));
    }

    @Test
    void blockedInMiddle() {
        // [3,2,1,0,4] → every path leads to index 3 (val 0), stuck
        assertEquals(-1, sol.jump(list(3, 2, 1, 0, 4)));
    }

    // ─── Minimum jumps needed ────────────────────────────────────────────────

    @Test
    void allOnes() {
        // [1,1,1,1] → must take every single step → 3 jumps
        assertEquals(3, sol.jump(list(1, 1, 1, 1)));
    }

    @Test
    void bigFirstJump() {
        // first element covers the whole array
        assertEquals(1, sol.jump(list(5, 0, 0, 0, 0)));
    }

    @Test
    void greedyChoiceMatters() {
        // [1,2,3,0,0] → index0→index1→index2→index4 → 3 jumps
        assertEquals(3, sol.jump(list(1, 2, 3, 0, 0)));
    }

    @Test
    void longerPath() {
        // [5,4,3,2,1,0,0] → all paths from 0 reach only up to index5 (val0), can't reach index6
        assertEquals(-1, sol.jump(list(5, 4, 3, 2, 1, 0, 0)));
    }

    @Test
    void exactlyFourJumps() {
        // [1,1,1,1,1] → step by step → 4 jumps
        assertEquals(4, sol.jump(list(1, 1, 1, 1, 1)));
    }

    // ─── Greedy (steps countdown) — same cases ────────────────────────────────

    @Test void g_example1()          { assertEquals(2,  sol.jumpGreedy(list(2, 3, 1, 1, 4))); }
    @Test void g_example2()          { assertEquals(2,  sol.jumpGreedy(list(2, 3, 0, 1, 4))); }
    @Test void g_singleElement()     { assertEquals(0,  sol.jumpGreedy(list(0))); }
    @Test void g_twoElementsReachable() { assertEquals(1, sol.jumpGreedy(list(1, 0))); }
    @Test void g_firstElementZero()  { assertEquals(-1, sol.jumpGreedy(list(0, 1, 2))); }
    @Test void g_blockedInMiddle()   { assertEquals(-1, sol.jumpGreedy(list(3, 2, 1, 0, 4))); }
    @Test void g_allOnes()           { assertEquals(3,  sol.jumpGreedy(list(1, 1, 1, 1))); }
    @Test void g_bigFirstJump()      { assertEquals(1,  sol.jumpGreedy(list(5, 0, 0, 0, 0))); }
    @Test void g_greedyChoiceMatters() { assertEquals(3, sol.jumpGreedy(list(1, 2, 3, 0, 0))); }
    @Test void g_longerPath()        { assertEquals(-1, sol.jumpGreedy(list(5, 4, 3, 2, 1, 0, 0))); }
    @Test void g_exactlyFourJumps()  { assertEquals(4,  sol.jumpGreedy(list(1, 1, 1, 1, 1))); }
}
