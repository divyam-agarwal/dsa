package trees;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class NodesAtDistanceKTest {

    NodesAtDistanceK sol = new NodesAtDistanceK();

    // Convenience builder
    private NodesAtDistanceK.TreeNode n(int val) {
        return new NodesAtDistanceK.TreeNode(val);
    }

    // ─── InterviewBit example ────────────────────────────────────────────────

    @Test
    void interviewBitExample() {
        //          20
        //         /  \
        //        8    22
        //       / \
        //      4   12
        //         /  \
        //        10   14
        // target=8, K=2 → [10, 14, 22]
        NodesAtDistanceK.TreeNode root = n(20);
        root.left        = n(8);
        root.right       = n(22);
        root.left.left   = n(4);
        root.left.right  = n(12);
        root.left.right.left  = n(10);
        root.left.right.right = n(14);

        assertEquals(Arrays.asList(10, 14, 22), sol.solve(root, 8, 2));
    }

    // ─── K = 0: target itself ────────────────────────────────────────────────

    @Test
    void kZeroReturnsTargetItself() {
        NodesAtDistanceK.TreeNode root = n(1);
        root.left  = n(2);
        root.right = n(3);
        assertEquals(Arrays.asList(1), sol.solve(root, 1, 0));
    }

    @Test
    void kZeroOnLeaf() {
        NodesAtDistanceK.TreeNode root = n(1);
        root.left = n(2);
        assertEquals(Arrays.asList(2), sol.solve(root, 2, 0));
    }

    // ─── Target is root ──────────────────────────────────────────────────────

    @Test
    void targetIsRootKOne() {
        //   1
        //  / \
        // 2   3
        // target=1, K=1 → [2, 3]
        NodesAtDistanceK.TreeNode root = n(1);
        root.left  = n(2);
        root.right = n(3);
        assertEquals(Arrays.asList(2, 3), sol.solve(root, 1, 1));
    }

    @Test
    void targetIsRootKTwo() {
        //       1
        //      / \
        //     2   3
        //    / \
        //   4   5
        // target=1, K=2 → [4, 5]
        NodesAtDistanceK.TreeNode root = n(1);
        root.left        = n(2);
        root.right       = n(3);
        root.left.left   = n(4);
        root.left.right  = n(5);
        assertEquals(Arrays.asList(4, 5), sol.solve(root, 1, 2));
    }

    // ─── Upward traversal through parent ────────────────────────────────────

    @Test
    void traversesUpThroughParent() {
        //   1
        //  / \
        // 2   3
        // target=2, K=1 → [1, (no right sibling) ] wait: 1 is parent, 3 is uncle
        // K=1 from 2: parent(1) only direct neighbor upward → [1]
        // but also right sibling via parent? No — from 2, distance 1 neighbours are: parent(1) and left/right children of 2 (none here)
        NodesAtDistanceK.TreeNode root = n(1);
        root.left  = n(2);
        root.right = n(3);
        assertEquals(Arrays.asList(1), sol.solve(root, 2, 1));
    }

    @Test
    void reachesCousinThroughAncestor() {
        //       1
        //      / \
        //     2   3
        //    /     \
        //   4       5
        // target=4, K=3: 4→2→1→3 → [3]
        // target=4, K=4: 4→2→1→3→5 → [5]
        NodesAtDistanceK.TreeNode root = n(1);
        root.left        = n(2);
        root.right       = n(3);
        root.left.left   = n(4);
        root.right.right = n(5);
        assertEquals(Arrays.asList(3), sol.solve(root, 4, 3));
        assertEquals(Arrays.asList(5), sol.solve(root, 4, 4));
    }

    // ─── K larger than any reachable distance ────────────────────────────────

    @Test
    void kExceedsTreeDepthReturnsEmpty() {
        NodesAtDistanceK.TreeNode root = n(1);
        root.left  = n(2);
        root.right = n(3);
        assertEquals(new ArrayList<>(), sol.solve(root, 1, 5));
    }

    // ─── Single node tree ────────────────────────────────────────────────────

    @Test
    void singleNodeKZero() {
        assertEquals(Arrays.asList(1), sol.solve(n(1), 1, 0));
    }

    @Test
    void singleNodeKOne() {
        assertEquals(new ArrayList<>(), sol.solve(n(1), 1, 1));
    }

    // ─── Target is a leaf, K reaches root and beyond ────────────────────────

    @Test
    void targetLeafKReachesRoot() {
        //     5
        //    /
        //   3
        //  /
        // 1
        // target=1, K=2 → [5]
        NodesAtDistanceK.TreeNode root = n(5);
        root.left      = n(3);
        root.left.left = n(1);
        assertEquals(Arrays.asList(5), sol.solve(root, 1, 2));
    }
}
