package graphs;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class CycleInDirectedGraphTest {

    CycleInDirectedGraph sol = new CycleInDirectedGraph();

    private ArrayList<ArrayList<Integer>> edges(int[]... pairs) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (int[] pair : pairs) {
            result.add(new ArrayList<>(Arrays.asList(pair[0], pair[1])));
        }
        return result;
    }

    // ─── InterviewBit examples ───────────────────────────────────────────────

    @Test
    void interviewBitExample1() {
        // 3 nodes: 1→2, 2→3, 3→1 → cycle exists
        assertEquals(1, sol.solve(3, edges(new int[]{1, 2}, new int[]{2, 3}, new int[]{3, 1})));
    }

    @Test
    void interviewBitExample2() {
        // 3 nodes: 1→2, 2→3 → no cycle
        assertEquals(0, sol.solve(3, edges(new int[]{1, 2}, new int[]{2, 3})));
    }

    // ─── No edges ────────────────────────────────────────────────────────────

    @Test
    void singleNodeNoEdges() {
        assertEquals(0, sol.solve(1, edges()));
    }

    @Test
    void multipleNodesNoEdges() {
        assertEquals(0, sol.solve(5, edges()));
    }

    // ─── Self-loop ───────────────────────────────────────────────────────────

    @Test
    void selfLoop() {
        // Node 1 → 1
        assertEquals(1, sol.solve(1, edges(new int[]{1, 1})));
    }

    @Test
    void selfLoopOnNonFirstNode() {
        // 3 nodes: 1→2, 3→3
        assertEquals(1, sol.solve(3, edges(new int[]{1, 2}, new int[]{3, 3})));
    }

    // ─── Two-node cases ──────────────────────────────────────────────────────

    @Test
    void twoNodesOneEdgeNoCycle() {
        // 1→2
        assertEquals(0, sol.solve(2, edges(new int[]{1, 2})));
    }

    @Test
    void twoNodesMutualEdge() {
        // 1→2, 2→1
        assertEquals(1, sol.solve(2, edges(new int[]{1, 2}, new int[]{2, 1})));
    }

    // ─── Linear chain (no cycle) ─────────────────────────────────────────────

    @Test
    void linearChain() {
        // 1→2→3→4→5
        assertEquals(0, sol.solve(5, edges(
                new int[]{1, 2}, new int[]{2, 3}, new int[]{3, 4}, new int[]{4, 5}
        )));
    }

    // ─── DAG (directed acyclic graph) ────────────────────────────────────────

    @Test
    void dagNoCycle() {
        // Diamond: 1→2, 1→3, 2→4, 3→4
        assertEquals(0, sol.solve(4, edges(
                new int[]{1, 2}, new int[]{1, 3}, new int[]{2, 4}, new int[]{3, 4}
        )));
    }

    @Test
    void dagWithMultipleSources() {
        // 1→3, 2→3, 3→4
        assertEquals(0, sol.solve(4, edges(
                new int[]{1, 3}, new int[]{2, 3}, new int[]{3, 4}
        )));
    }

    // ─── Back edge (cycle) ───────────────────────────────────────────────────

    @Test
    void backEdgeCreatingCycle() {
        // 1→2→3→4, 4→2 → cycle {2,3,4}
        assertEquals(1, sol.solve(4, edges(
                new int[]{1, 2}, new int[]{2, 3}, new int[]{3, 4}, new int[]{4, 2}
        )));
    }

    @Test
    void backEdgeToRoot() {
        // 1→2→3→4→1 → cycle containing all nodes
        assertEquals(1, sol.solve(4, edges(
                new int[]{1, 2}, new int[]{2, 3}, new int[]{3, 4}, new int[]{4, 1}
        )));
    }

    // ─── Disconnected graph ──────────────────────────────────────────────────

    @Test
    void disconnectedNoCycle() {
        // Component 1: 1→2, Component 2: 3→4
        assertEquals(0, sol.solve(4, edges(new int[]{1, 2}, new int[]{3, 4})));
    }

    @Test
    void disconnectedOneCycleOneTree() {
        // Component 1: 1→2→3 (no cycle), Component 2: 4→5→4 (cycle)
        assertEquals(1, sol.solve(5, edges(
                new int[]{1, 2}, new int[]{2, 3}, new int[]{4, 5}, new int[]{5, 4}
        )));
    }

    @Test
    void disconnectedBothCycles() {
        // Cycle {1,2} and Cycle {3,4}
        assertEquals(1, sol.solve(4, edges(
                new int[]{1, 2}, new int[]{2, 1}, new int[]{3, 4}, new int[]{4, 3}
        )));
    }

    // ─── Cross / forward edges don't form cycles ─────────────────────────────

    @Test
    void crossEdgeNoCycle() {
        // 1→2, 1→3, 2→4, 3→4, 2→3 (cross edge — no back edge)
        assertEquals(0, sol.solve(4, edges(
                new int[]{1, 2}, new int[]{1, 3}, new int[]{2, 4}, new int[]{3, 4}, new int[]{2, 3}
        )));
    }
}
