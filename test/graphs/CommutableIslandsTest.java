package graphs;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class CommutableIslandsTest {

    CommutableIslands sol = new CommutableIslands();

    private ArrayList<ArrayList<Integer>> edges(int[]... rows) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (int[] row : rows)
            result.add(new ArrayList<>(Arrays.asList(row[0], row[1], row[2])));
        return result;
    }

    // ─── InterviewBit example ────────────────────────────────────────────────

    @Test
    void interviewBitExample() {
        // A=4, bridges: (1-2,w=1),(2-3,w=4),(1-4,w=3),(4-3,w=2),(1-3,w=2)
        // MST: (1-2,1) + (4-3,2) + (1-3,2) = 5
        assertEquals(5, sol.solve(4, edges(
            new int[]{1,2,1}, new int[]{2,3,4},
            new int[]{1,4,3}, new int[]{4,3,2},
            new int[]{1,3,2}
        )));
    }

    // ─── Trivial cases ───────────────────────────────────────────────────────

    @Test
    void singleIsland() {
        // One island, no bridges needed → cost 0
        assertEquals(0, sol.solve(1, edges()));
    }

    @Test
    void twoIslandsOneBridge() {
        // Only one possible bridge → must use it
        assertEquals(5, sol.solve(2, edges(new int[]{1, 2, 5})));
    }

    // ─── Minimum spanning tree selection ─────────────────────────────────────

    @Test
    void trianglePicksTwoCheapestEdges() {
        // A=3: edges (1-2,1),(2-3,2),(1-3,10)
        // MST: (1-2,1) + (2-3,2) = 3  (skip the expensive 1-3 edge)
        assertEquals(3, sol.solve(3, edges(
            new int[]{1,2,1}, new int[]{2,3,2}, new int[]{1,3,10}
        )));
    }

    @Test
    void fourIslandsKruskal() {
        // A=4: edges (1-2,2),(2-3,3),(3-4,4),(2-4,1),(1-4,10)
        // Sorted: (2-4,1),(1-2,2),(2-3,3),(3-4,4),(1-4,10)
        // MST: (2-4,1) + (1-2,2) + (2-3,3) = 6
        assertEquals(6, sol.solve(4, edges(
            new int[]{1,2,2}, new int[]{2,3,3},
            new int[]{3,4,4}, new int[]{2,4,1}, new int[]{1,4,10}
        )));
    }

    @Test
    void parallelEdgesPicksCheaper() {
        // Two bridges between islands 1 and 2 with costs 5 and 3 → pick 3
        assertEquals(3, sol.solve(2, edges(new int[]{1,2,5}, new int[]{1,2,3})));
    }

    @Test
    void allSameWeight() {
        // A=3, all bridges cost 4 → any valid MST costs 4+4 = 8
        assertEquals(8, sol.solve(3, edges(
            new int[]{1,2,4}, new int[]{2,3,4}, new int[]{1,3,4}
        )));
    }

    @Test
    void linearChain() {
        // A=5: only bridges form a chain 1-2-3-4-5 with weights 1,2,3,4
        // MST is the chain itself → cost 1+2+3+4 = 10
        assertEquals(10, sol.solve(5, edges(
            new int[]{1,2,1}, new int[]{2,3,2},
            new int[]{3,4,3}, new int[]{4,5,4}
        )));
    }

    @Test
    void skipExpensiveBridgeWithCheaperAlternative() {
        // A=4: star from node 1 with cheap edges, plus an expensive cross-bridge
        // Bridges: (1-2,1),(1-3,2),(1-4,3),(2-3,100)
        // MST: star edges (1-2,1)+(1-3,2)+(1-4,3) = 6  (skip 2-3,100)
        assertEquals(6, sol.solve(4, edges(
            new int[]{1,2,1}, new int[]{1,3,2},
            new int[]{1,4,3}, new int[]{2,3,100}
        )));
    }

    // ─── Impossible case ─────────────────────────────────────────────────────

    @Test
    void disconnectedIslands() {
        // A=4 but only bridges within {1,2} and {3,4} — no way to connect both groups
        assertEquals(-1, sol.solve(4, edges(new int[]{1,2,1}, new int[]{3,4,2})));
    }
}
