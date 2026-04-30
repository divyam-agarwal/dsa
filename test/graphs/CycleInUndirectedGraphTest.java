package graphs;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class CycleInUndirectedGraphTest {

    CycleInUndirectedGraph sol = new CycleInUndirectedGraph();

    private ArrayList<ArrayList<Integer>> edges(int[]... pairs) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (int[] pair : pairs)
            result.add(new ArrayList<>(Arrays.asList(pair[0], pair[1])));
        return result;
    }

    // ─── InterviewBit examples ───────────────────────────────────────────────

    @Test
    void interviewBitExample_noCycle() {
        // A=3, edges 1-2, 1-3 → star/tree → no cycle
        assertEquals(0, sol.solve(3, edges(new int[]{1, 2}, new int[]{1, 3})));
    }

    @Test
    void interviewBitExample_withCycle() {
        // A=3, triangle 1-2-3-1 → cycle
        assertEquals(1, sol.solve(3, edges(
                new int[]{1, 2}, new int[]{2, 3}, new int[]{1, 3}
        )));
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
        // Node 1 connected to itself
        assertEquals(1, sol.solve(1, edges(new int[]{1, 1})));
    }

    // ─── Two-node cases ──────────────────────────────────────────────────────

    @Test
    void twoNodesOneEdge() {
        // Single undirected edge 1-2 is NOT a cycle (needs ≥3 nodes)
        assertEquals(0, sol.solve(2, edges(new int[]{1, 2})));
    }

    // ─── Trees (no cycle) ────────────────────────────────────────────────────

    @Test
    void linearChain() {
        // 1-2-3-4-5 (a path/tree — no cycle)
        assertEquals(0, sol.solve(5, edges(
                new int[]{1, 2}, new int[]{2, 3}, new int[]{3, 4}, new int[]{4, 5}
        )));
    }

    @Test
    void starNoCycle() {
        // Node 1 connected to nodes 2, 3, 4, 5 (star tree — no cycle)
        assertEquals(0, sol.solve(5, edges(
                new int[]{1, 2}, new int[]{1, 3}, new int[]{1, 4}, new int[]{1, 5}
        )));
    }

    // ─── Cycle cases ─────────────────────────────────────────────────────────

    @Test
    void triangle() {
        // 1-2-3-1 (smallest non-trivial cycle)
        assertEquals(1, sol.solve(3, edges(
                new int[]{1, 2}, new int[]{2, 3}, new int[]{3, 1}
        )));
    }

    @Test
    void extraEdgeInChainCreatesCycle() {
        // Chain 1-2-3-4 plus back edge 4-2 → cycle {2,3,4}
        assertEquals(1, sol.solve(4, edges(
                new int[]{1, 2}, new int[]{2, 3}, new int[]{3, 4}, new int[]{4, 2}
        )));
    }

    @Test
    void largerCycle() {
        // Ring: 1-2-3-4-5-1
        assertEquals(1, sol.solve(5, edges(
                new int[]{1, 2}, new int[]{2, 3}, new int[]{3, 4},
                new int[]{4, 5}, new int[]{5, 1}
        )));
    }

    // ─── Disconnected graph ──────────────────────────────────────────────────

    @Test
    void disconnectedNoCycle() {
        // Component 1: 1-2  Component 2: 3-4  (both trees)
        assertEquals(0, sol.solve(4, edges(new int[]{1, 2}, new int[]{3, 4})));
    }

    @Test
    void disconnectedOneCycleOneTree() {
        // Component 1: 1-2-3 (tree), Component 2: 4-5-6-4 (triangle cycle)
        assertEquals(1, sol.solve(6, edges(
                new int[]{1, 2}, new int[]{2, 3},
                new int[]{4, 5}, new int[]{5, 6}, new int[]{6, 4}
        )));
    }

    @Test
    void disconnectedBothCycles() {
        // Component 1: triangle {1,2,3}, Component 2: triangle {4,5,6}
        assertEquals(1, sol.solve(6, edges(
                new int[]{1, 2}, new int[]{2, 3}, new int[]{3, 1},
                new int[]{4, 5}, new int[]{5, 6}, new int[]{6, 4}
        )));
    }
}
