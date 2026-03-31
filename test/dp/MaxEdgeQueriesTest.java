package dp;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MaxEdgeQueriesTest {

    MaxEdgeQueries sol = new MaxEdgeQueries();

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private ArrayList<ArrayList<Integer>> edges(int[]... rows) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        for (int[] row : rows) {
            ArrayList<Integer> e = new ArrayList<>();
            for (int v : row) e.add(v);
            list.add(e);
        }
        return list;
    }

    private ArrayList<ArrayList<Integer>> queries(int[]... rows) {
        return edges(rows);  // same shape
    }

    // ─── Single edge ──────────────────────────────────────────────────────────

    @Test
    void singleEdge() {
        // Tree: 1-2 (w=5).  Query (1,2) → 5
        var A = edges(new int[]{1, 2, 5});
        var B = queries(new int[]{1, 2});
        assertEquals(List.of(5), sol.solve(A, B));
    }

    @Test
    void singleEdgeReversed() {
        // Query direction should not matter: (2,1) → 5
        var A = edges(new int[]{1, 2, 5});
        var B = queries(new int[]{2, 1});
        assertEquals(List.of(5), sol.solve(A, B));
    }

    // ─── Same-node query ──────────────────────────────────────────────────────

    @Test
    void sameNode() {
        // No edges traversed → 0
        var A = edges(new int[]{1, 2, 5});
        var B = queries(new int[]{1, 1});
        assertEquals(List.of(0), sol.solve(A, B));
    }

    // ─── Linear chain ─────────────────────────────────────────────────────────

    @Test
    void linearChain() {
        // 1-2(w=3), 2-3(w=1), 3-4(w=7)
        // (1,4) → max(3,1,7)=7   (1,3) → max(3,1)=3   (2,4) → max(1,7)=7
        var A = edges(new int[]{1, 2, 3}, new int[]{2, 3, 1}, new int[]{3, 4, 7});
        var B = queries(new int[]{1, 4}, new int[]{1, 3}, new int[]{2, 4});
        assertEquals(List.of(7, 3, 7), sol.solve(A, B));
    }

    // ─── Star graph ───────────────────────────────────────────────────────────

    @Test
    void starGraph() {
        // Center=1: 1-2(w=4), 1-3(w=2), 1-4(w=6)
        // (2,3) → max(4,2)=4   (2,4) → max(4,6)=6   (3,4) → max(2,6)=6
        var A = edges(new int[]{1, 2, 4}, new int[]{1, 3, 2}, new int[]{1, 4, 6});
        var B = queries(new int[]{2, 3}, new int[]{2, 4}, new int[]{3, 4});
        assertEquals(List.of(4, 6, 6), sol.solve(A, B));
    }

    // ─── Branching tree ───────────────────────────────────────────────────────

    @Test
    void branchingTree() {
        // 1-2(w=3), 1-3(w=5), 2-4(w=2), 2-5(w=7)
        //     1
        //    / \
        //   2   3
        //  / \
        // 4   5
        // (3,4) → max(5,3,2)=5   (4,5) → max(2,7)=7
        // (1,5) → max(3,7)=7     (3,5) → max(5,3,7)=7
        var A = edges(
            new int[]{1, 2, 3},
            new int[]{1, 3, 5},
            new int[]{2, 4, 2},
            new int[]{2, 5, 7}
        );
        var B = queries(
            new int[]{3, 4},
            new int[]{4, 5},
            new int[]{1, 5},
            new int[]{3, 5}
        );
        assertEquals(List.of(5, 7, 7, 7), sol.solve(A, B));
    }

    // ─── Path through root with equal-weight edges ────────────────────────────

    @Test
    void equalWeights() {
        // 1-2(w=4), 2-3(w=4), 3-4(w=4) — all edges same weight
        // Any query should return 4
        var A = edges(new int[]{1, 2, 4}, new int[]{2, 3, 4}, new int[]{3, 4, 4});
        var B = queries(new int[]{1, 4}, new int[]{1, 3}, new int[]{2, 4});
        assertEquals(List.of(4, 4, 4), sol.solve(A, B));
    }

    @Test
    void interviewBitFailedCase() {
        // Path 13→11(28146)→4(24465)→2(29359)→3(18468)→5(5706)
        // max = 29359
        var A = edges(
            new int[]{10, 6,  42},
            new int[]{3,  2,  18468},
            new int[]{12, 7,  6335},
            new int[]{9,  5,  26501},
            new int[]{2,  1,  19170},
            new int[]{8,  3,  15725},
            new int[]{7,  1,  11479},
            new int[]{4,  2,  29359},
            new int[]{6,  3,  26963},
            new int[]{11, 4,  24465},
            new int[]{5,  3,  5706},
            new int[]{13, 11, 28146}
        );
        var B = queries(new int[]{13, 5});
        assertEquals(List.of(29359), sol.solve(A, B));
    }
}
