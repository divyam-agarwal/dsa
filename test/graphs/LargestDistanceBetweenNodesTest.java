package graphs;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class LargestDistanceBetweenNodesTest {

    LargestDistanceBetweenNodes sol = new LargestDistanceBetweenNodes();

    private ArrayList<Integer> list(Integer... vals) {
        return new ArrayList<>(Arrays.asList(vals));
    }

    // ─── InterviewBit example ────────────────────────────────────────────────

    @Test
    void interviewBitExample() {
        // A = [-1, 0, 0, 0, 3]
        //       0
        //     / | \
        //    1  2  3
        //          \
        //           4
        // Longest path: 1→0→3→4 or 2→0→3→4 = 3 edges
        assertEquals(3, sol.solve(list(-1, 0, 0, 0, 3)));
    }

    // ─── Single node ─────────────────────────────────────────────────────────

    @Test
    void singleNode() {
        // Only the root exists, no edges
        assertEquals(0, sol.solve(list(-1)));
    }

    // ─── Two nodes ───────────────────────────────────────────────────────────

    @Test
    void twoNodes() {
        // 0 → 1 (root is 0, node 1's parent is 0)
        assertEquals(1, sol.solve(list(-1, 0)));
    }

    // ─── Linear chain ────────────────────────────────────────────────────────

    @Test
    void linearChain() {
        // 0 → 1 → 2 → 3 → 4  (each node's parent is the previous one)
        // Diameter = 4 edges (0 to 4)
        assertEquals(4, sol.solve(list(-1, 0, 1, 2, 3)));
    }

    @Test
    void linearChainRootInMiddle() {
        // A = [1, -1, 1, 2, 3]
        //   root = 1
        //   1's children: 0, 2
        //   2's child: 3
        //   3's child: 4
        // Paths: 0→1→2→3→4 = 4 edges
        assertEquals(4, sol.solve(list(1, -1, 1, 2, 3)));
    }

    // ─── Balanced binary tree ────────────────────────────────────────────────

    @Test
    void perfectBinaryTreeDepth2() {
        // A = [-1, 0, 0, 1, 1, 2, 2]
        //         0
        //        / \
        //       1   2
        //      / \ / \
        //     3  4 5  6
        // Diameter: 3→1→0→2→6 = 4 edges
        assertEquals(4, sol.solve(list(-1, 0, 0, 1, 1, 2, 2)));
    }

    @Test
    void perfectBinaryTreeDepth1() {
        // A = [-1, 0, 0]
        //     0
        //    / \
        //   1   2
        // Diameter: 1→0→2 = 2 edges
        assertEquals(2, sol.solve(list(-1, 0, 0)));
    }

    // ─── Skewed trees ────────────────────────────────────────────────────────

    @Test
    void leftSkewed() {
        // 0←1←2←3←4←5  (all left children)
        // A = [-1, 0, 1, 2, 3, 4]
        // Diameter = 5
        assertEquals(5, sol.solve(list(-1, 0, 1, 2, 3, 4)));
    }

    @Test
    void rightSkewed() {
        // Same as linear chain just verifying direction doesn't matter
        // A = [-1, 0, 1, 2, 3]
        assertEquals(4, sol.solve(list(-1, 0, 1, 2, 3)));
    }

    // ─── Star graph (root with many leaves) ──────────────────────────────────

    @Test
    void starGraph() {
        // Root 0 with 5 leaves: 1, 2, 3, 4, 5
        // Diameter: any leaf → root → any other leaf = 2 edges
        assertEquals(2, sol.solve(list(-1, 0, 0, 0, 0, 0)));
    }

    // ─── Diameter goes through a non-root node ───────────────────────────────

    @Test
    void diameterThroughInternalNode() {
        // A = [-1, 0, 1, 1, 2, 2]
        //     0
        //     |
        //     1
        //    / \
        //   2   3
        //  / \
        // 4   5
        // Diameter: 3→1→2→4 or 3→1→2→5 = 3 edges
        assertEquals(3, sol.solve(list(-1, 0, 1, 1, 2, 2)));
    }

    @Test
    void diameterDoesNotPassThroughRoot() {
        // A = [-1, 0, 1, 2, 2, 1, 5, 5]
        //        0
        //        |
        //        1
        //       / \
        //      2   5
        //     / \ / \
        //    3  4 6  7
        // Longest: 3→2→1→5→6 or 3→2→1→5→7 or 4→2→1→5→6 etc = 4 edges
        assertEquals(4, sol.solve(list(-1, 0, 1, 2, 2, 1, 5, 5)));
    }

    // ─── Two long branches from root ─────────────────────────────────────────

    @Test
    void twoLongBranchesFromRoot() {
        // Root 0 with two chains of length 3 each
        // A = [-1, 0, 1, 2, 0, 4, 5]
        //   0
        //  / \
        // 1   4
        // |   |
        // 2   5
        // |   |
        // 3   6
        // Diameter: 3→2→1→0→4→5→6 = 6 edges
        assertEquals(6, sol.solve(list(-1, 0, 1, 2, 0, 4, 5)));
    }
}
