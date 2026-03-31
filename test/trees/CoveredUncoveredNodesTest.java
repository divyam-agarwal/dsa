package trees;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoveredUncoveredNodesTest {

    CoveredUncoveredNodes sol = new CoveredUncoveredNodes();

    private CoveredUncoveredNodes.TreeNode n(int val) {
        return new CoveredUncoveredNodes.TreeNode(val);
    }

    // ─── Provided examples ───────────────────────────────────────────────────

    @Test
    void example1() {
        //     2
        //    / \
        //   1   4
        //  /   / \
        // 6  10   5
        // leftSpine:  2→1→6       = 9
        // rightSpine: 2(skip)→4→5 = 9
        // boundary=18, total=28, non-boundary=10 → |18-10| = 8
        CoveredUncoveredNodes.TreeNode root = n(2);
        root.left             = n(1);
        root.right            = n(4);
        root.left.left        = n(6);
        root.right.left       = n(10);
        root.right.right      = n(5);
        assertEquals(8, sol.coveredNodes(root));
    }

    @Test
    void example2() {
        //   1
        //  /
        // 2
        // /
        // 3
        // leftSpine: 1→2→3 = 6
        // rightSpine: all in hs → 0
        // boundary=6, total=6, non-boundary=0 → |6-0| = 6
        CoveredUncoveredNodes.TreeNode root = n(1);
        root.left      = n(2);
        root.left.left = n(3);
        assertEquals(6, sol.coveredNodes(root));
    }

    // ─── Single node ─────────────────────────────────────────────────────────

    @Test
    void singleNode() {
        // leftSpine: 5; rightSpine: 5 already in hs → 0
        // boundary=5, total=5, non-boundary=0 → |5-0| = 5
        assertEquals(5, sol.coveredNodes(n(5)));
    }

    // ─── Root with two leaf children ─────────────────────────────────────────

    @Test
    void rootWithTwoLeaves() {
        //  1
        // / \
        // 2  3
        // leftSpine: 1→2 = 3; rightSpine: 1(skip)→3 = 3
        // boundary=6, total=6, non-boundary=0 → |6-0| = 6
        CoveredUncoveredNodes.TreeNode root = n(1);
        root.left  = n(2);
        root.right = n(3);
        assertEquals(6, sol.coveredNodes(root));
    }

    // ─── Right-skewed ────────────────────────────────────────────────────────

    @Test
    void rightSkewed() {
        // 1→2→3 (each is right child)
        // leftSpine: 1 (no left) → falls to right: 1→2→3 = 6
        // rightSpine: all in hs → 0
        // boundary=6, total=6, non-boundary=0 → |6-0| = 6
        CoveredUncoveredNodes.TreeNode root = n(1);
        root.right       = n(2);
        root.right.right = n(3);
        assertEquals(6, sol.coveredNodes(root));
    }

    // ─── Non-boundary (uncovered by code's definition) nodes exist ───────────

    @Test
    void internalNodeOffBoundary() {
        //     5
        //    / \
        //   3   8
        //  / \
        // 1   4
        // leftSpine: 5→3→1 = 9; rightSpine: 5(skip)→8 = 8
        // boundary=17, total=21, non-boundary=4 → |17-4| = 13
        CoveredUncoveredNodes.TreeNode root = n(5);
        root.left       = n(3);
        root.right      = n(8);
        root.left.left  = n(1);
        root.left.right = n(4);
        assertEquals(13, sol.coveredNodes(root));
    }

    @Test
    void balancedTreeTwoNonBoundaryLeaves() {
        //       1
        //      / \
        //     2   3
        //    / \ / \
        //   4  5 6  7
        // leftSpine: 1→2→4 = 7; rightSpine: 1(skip)→3→7 = 10
        // boundary=17, total=28, non-boundary=11 → |17-11| = 6
        CoveredUncoveredNodes.TreeNode root = n(1);
        root.left        = n(2);
        root.right       = n(3);
        root.left.left   = n(4);
        root.left.right  = n(5);
        root.right.left  = n(6);
        root.right.right = n(7);
        assertEquals(6, sol.coveredNodes(root));
    }

    // ─── Null root ───────────────────────────────────────────────────────────

    @Test
    void nullRoot() {
        assertEquals(0, sol.coveredNodes(null));
    }

    // ─── Known failing case (InterviewBit submission failure) ────────────────

    @Test
    void submissionFailureCase() {
        // Serialized: 17  1 3 8 -1 -1 6 7 2 4 5 -1 -1 -1 -1 -1 -1 -1
        //         1
        //        / \
        //       3   8
        //          / \
        //         6   7
        //        / \ /
        //       2  4 5
        // rightSpine falls back from 7 (right=null) to 5 via left child,
        // incorrectly pulling 5 into the boundary → code returns 12, expected 28
        CoveredUncoveredNodes.TreeNode root = n(1);
        root.left              = n(3);
        root.right             = n(8);
        root.right.left        = n(6);
        root.right.right       = n(7);
        root.right.left.left   = n(2);
        root.right.left.right  = n(4);
        root.right.right.left  = n(5);
        assertEquals(28, sol.coveredNodes(root));
    }
}
