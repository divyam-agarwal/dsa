package graphs;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class GoodGraphTest {

    GoodGraph sol = new GoodGraph();

    private ArrayList<Integer> list(Integer... vals) {
        return new ArrayList<>(Arrays.asList(vals));
    }

    // ─── InterviewBit examples ───────────────────────────────────────────────

    @Test
    void interviewBitExample1() {
        // A = [1, 2, 1, 2]
        // Node 1→1 (good, is node 1)
        // Node 2→2 (cycle {2}, not containing node 1 → 1 change)
        // Node 3→1 (good)
        // Node 4→2 (points into cycle {2}, also bad)
        assertEquals(1, sol.solve(list(1, 2, 1, 2)));
    }

    @Test
    void interviewBitExample2() {
        // A = [3, 1, 3, 1]
        // Node 1→3, Node 3→3 (cycle {3}, not containing node 1 → 1 change)
        // Node 2→1 (good), Node 4→1 (good)
        assertEquals(1, sol.solve(list(3, 1, 3, 1)));
    }

    // ─── Already all good ────────────────────────────────────────────────────

    @Test
    void singleNodePointsToItself() {
        // Node 1→1: node 1 is always good, no changes needed
        assertEquals(0, sol.solve(list(1)));
    }

    @Test
    void allPointToNode1() {
        // Every node points directly to node 1 → already good
        assertEquals(0, sol.solve(list(1, 1, 1, 1)));
    }

    @Test
    void cycleContainsNode1() {
        // 1→2→3→1: cycle {1,2,3} contains node 1 → all good, 0 changes
        assertEquals(0, sol.solve(list(2, 3, 1)));
    }

    @Test
    void longChainLeadingToNode1() {
        // 4→3→2→1→1: every node's chain reaches node 1
        assertEquals(0, sol.solve(list(1, 1, 2, 3)));
    }

    // ─── One cycle not containing node 1 ─────────────────────────────────────

    @Test
    void selfLoopNotNode1() {
        // Node 1→1 (good), Node 2→2 (cycle {2}, 1 change)
        assertEquals(1, sol.solve(list(1, 2)));
    }

    @Test
    void twoNodeCycleNotContainingNode1() {
        // Node 1→1, Node 2→3, Node 3→2 → cycle {2,3}, 1 change
        assertEquals(1, sol.solve(list(1, 3, 2)));
    }

    @Test
    void node1InTailLeadingIntoCycle() {
        // Node 1→2, Node 2→3, Node 3→2 → cycle {2,3} (node 1 is not in the cycle)
        // Node 1 is good by definition; cycle {2,3} doesn't contain node 1 → 1 change
        assertEquals(1, sol.solve(list(2, 3, 2)));
    }

    @Test
    void largerCycleNotContainingNode1() {
        // Node 1→1, cycle {2,3,4,5}
        // 2→3→4→5→2
        assertEquals(1, sol.solve(list(1, 3, 4, 5, 2)));
    }

    // ─── Two cycles not containing node 1 ────────────────────────────────────

    @Test
    void twoCyclesNotContainingNode1() {
        // Node 1→1 (good)
        // Cycle {2,3}: 2→3→2
        // Cycle {4,5}: 4→5→4
        assertEquals(2, sol.solve(list(1, 3, 2, 5, 4)));
    }

    @Test
    void twoCyclesWithTailsAttached() {
        // Node 1→1 (good)
        // Node 6→2 (tail into cycle {2,3})
        // Node 7→4 (tail into cycle {4,5})
        // Still only 2 cycles to fix
        assertEquals(2, sol.solve(list(1, 3, 2, 5, 4, 2, 4)));
    }

    // ─── Three cycles ────────────────────────────────────────────────────────

    @Test
    void threeCyclesNoneContainingNode1() {
        // Node 1→1, cycles {2}, {3}, {4}
        assertEquals(3, sol.solve(list(1, 2, 3, 4)));
    }

    // ─── Node 1 inside a larger cycle ────────────────────────────────────────

    @Test
    void node1InLargerCycleOtherCycleExists() {
        // 1→2→3→1 (cycle contains node 1, 0 cost)
        // 4→5→4 (cycle {4,5}, 1 change)
        assertEquals(1, sol.solve(list(2, 3, 1, 5, 4)));
    }

    @Test
    void allNodesInCycleWithNode1() {
        // 1→2→3→4→5→1: single cycle containing node 1
        assertEquals(0, sol.solve(list(2, 3, 4, 5, 1)));
    }
}
