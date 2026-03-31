package trees;

import org.junit.jupiter.api.Test;
import java.util.ArrayDeque;
import java.util.Deque;
import static org.junit.jupiter.api.Assertions.*;

class SumRootToLeafTest {

    SumRootToLeaf sol = new SumRootToLeaf();

    private SumRootToLeaf.TreeNode n(int val) {
        return new SumRootToLeaf.TreeNode(val);
    }

    // Parses a level-order serialized tree (no-expand-null, -1 = null).
    // The leading count integer must be omitted — pass only the node values.
    private SumRootToLeaf.TreeNode buildTree(int[] vals) {
        if (vals.length == 0 || vals[0] == -1) return null;
        SumRootToLeaf.TreeNode root = n(vals[0]);
        Deque<SumRootToLeaf.TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int i = 1;
        while (!queue.isEmpty() && i < vals.length) {
            SumRootToLeaf.TreeNode node = queue.poll();
            if (i < vals.length && vals[i] != -1) { node.left  = n(vals[i]); queue.offer(node.left);  }
            i++;
            if (i < vals.length && vals[i] != -1) { node.right = n(vals[i]); queue.offer(node.right); }
            i++;
        }
        return root;
    }

    // ─── InterviewBit examples ───────────────────────────────────────────────

    @Test
    void example1() {
        //   1
        //  / \
        // 2   3
        // Paths: 12 + 13 = 25
        SumRootToLeaf.TreeNode root = n(1);
        root.left  = n(2);
        root.right = n(3);
        assertEquals(25, sol.sumNumbers(root));
    }

    @Test
    void example2() {
        //     4
        //    / \
        //   9   0
        //  / \
        // 5   1
        // Paths: 495 + 491 + 40 = 1026
        SumRootToLeaf.TreeNode root = n(4);
        root.left       = n(9);
        root.right      = n(0);
        root.left.left  = n(5);
        root.left.right = n(1);
        assertEquals(1026, sol.sumNumbers(root));
    }

    // ─── Single node ─────────────────────────────────────────────────────────

    @Test
    void singleNode() {
        // Only path: 7 → sum = 7
        assertEquals(7, sol.sumNumbers(n(7)));
    }

    @Test
    void singleNodeZero() {
        assertEquals(0, sol.sumNumbers(n(0)));
    }

    // ─── Null root ───────────────────────────────────────────────────────────

    @Test
    void nullRoot() {
        assertEquals(0, sol.sumNumbers(null));
    }

    // ─── Skewed trees ────────────────────────────────────────────────────────

    @Test
    void leftSkewed() {
        //   1
        //  /
        // 2
        //  /
        // 3
        // Only path: 123
        SumRootToLeaf.TreeNode root = n(1);
        root.left      = n(2);
        root.left.left = n(3);
        assertEquals(123, sol.sumNumbers(root));
    }

    @Test
    void rightSkewed() {
        // 1
        //  \
        //   2
        //    \
        //     3
        // Only path: 123
        SumRootToLeaf.TreeNode root = n(1);
        root.right       = n(2);
        root.right.right = n(3);
        assertEquals(123, sol.sumNumbers(root));
    }

    // ─── Root with one child ─────────────────────────────────────────────────

    @Test
    void rootWithLeftChildOnly() {
        //   5
        //  /
        // 6
        // Path: 56
        SumRootToLeaf.TreeNode root = n(5);
        root.left = n(6);
        assertEquals(56, sol.sumNumbers(root));
    }

    // ─── Path contains zero digit ────────────────────────────────────────────

    @Test
    void zeroDigitInPath() {
        //   1
        //  / \
        // 0   5
        // Paths: 10 + 15 = 25
        SumRootToLeaf.TreeNode root = n(1);
        root.left  = n(0);
        root.right = n(5);
        assertEquals(25, sol.sumNumbers(root));
    }

    // ─── Deeper tree ─────────────────────────────────────────────────────────

    @Test
    void threeLevelBalanced() {
        //        1
        //       / \
        //      2   3
        //     / \ / \
        //    4  5 6  7
        // Paths: 124 + 125 + 136 + 137 = 522
        SumRootToLeaf.TreeNode root = n(1);
        root.left        = n(2);
        root.right       = n(3);
        root.left.left   = n(4);
        root.left.right  = n(5);
        root.right.left  = n(6);
        root.right.right = n(7);
        assertEquals(522, sol.sumNumbers(root));
    }

    // ─── Known failing case (InterviewBit submission failure) ────────────────

    @Test
    void largeTreeMod1003() {
        // 763-node tree from InterviewBit submission failure.
        // Expected: 790  (sum of all root-to-leaf numbers mod 1003)
        // The current code always returns 0 because `ans` is passed by value
        // (Long is immutable in Java) — `ans += ...` inside leafSum() only
        // updates the local copy, never the caller's variable.
        int[] vals = {
            3,7,9,9,0,-1,-1,2,1,4,3,2,5,2,2,4,8,1,1,4,9,0,-1,8,3,5,2,5,-1,1,
            6,2,8,1,0,7,3,-1,7,-1,6,6,1,7,1,5,9,4,7,-1,7,-1,-1,-1,6,2,8,7,8,1,
            5,9,0,4,6,-1,-1,5,6,-1,2,1,8,2,5,5,-1,4,-1,1,9,1,4,3,5,7,4,-1,-1,0,
            6,7,5,-1,2,1,7,1,9,0,2,5,4,-1,-1,-1,-1,-1,8,2,2,-1,-1,-1,-1,-1,2,-1,
            3,9,4,8,8,6,4,7,2,5,7,1,-1,9,5,3,8,0,4,-1,-1,5,5,7,2,-1,-1,-1,8,0,4,
            4,5,5,7,-1,-1,5,6,3,-1,9,1,9,-1,8,-1,-1,9,-1,-1,8,-1,-1,-1,-1,-1,-1,
            -1,-1,-1,6,7,3,-1,1,8,-1,-1,1,8,-1,-1,-1,8,0,0,5,6,-1,-1,0,-1,9,-1,5,
            -1,6,6,-1,6,2,6,5,-1,-1,7,3,1,6,-1,7,6,-1,-1,6,-1,3,9,-1,-1,-1,0,-1,
            2,-1,0,-1,7,3,5,-1,8,2,0,6,8,7,3,9,0,1,0,-1,-1,-1,0,8,7,2,9,-1,6,6,
            6,-1,2,3,2,-1,-1,1,1,4,8,-1,2,0,-1,-1,-1,-1,-1,-1,1,3,6,-1,-1,-1,-1,
            5,4,1,7,7,-1,-1,-1,-1,-1,-1,0,8,0,-1,5,5,-1,7,3,-1,-1,1,-1,-1,-1,7,9,
            4,-1,4,-1,-1,-1,-1,-1,-1,-1,-1,-1,0,0,5,5,-1,-1,-1,2,6,8,1,-1,0,-1,6,
            -1,0,-1,-1,-1,-1,-1,-1,6,8,2,-1,4,2,-1,1,-1,-1,-1,2,1,0,2,7,8,-1,1,
            -1,-1,3,4,-1,-1,-1,-1,-1,5,-1,-1,8,2,-1,-1,-1,-1,2,8,-1,3,-1,8,6,3,
            -1,-1,-1,8,6,4,-1,-1,-1,-1,5,-1,-1,-1,-1,-1,-1,9,4,-1,-1,-1,-1,-1,-1,
            -1,2,2,7,3,9,-1,-1,9,-1,-1,-1,-1,6,-1,3,8,-1,-1,-1,-1,-1,-1,-1,3,-1,
            -1,-1,-1,-1,-1,-1,-1,4,-1,2,-1,-1,-1,-1,2,-1,-1,1,9,1,-1,-1,2,5,1,-1,
            -1,4,2,-1,-1,-1,7,6,3,8,2,8,-1,-1,0,-1,-1,3,1,-1,-1,5,-1,-1,9,-1,2,
            -1,0,-1,-1,-1,8,-1,-1,8,-1,-1,0,-1,0,-1,7,-1,-1,1,4,-1,9,5,3,-1,-1,
            -1,2,3,-1,-1,-1,6,7,-1,0,6,-1,-1,-1,-1,-1,5,-1,-1,-1,5,-1,-1,-1,-1,
            -1,4,8,3,-1,-1,9,5,-1,-1,-1,9,0,-1,-1,-1,-1,-1,-1,-1,-1,4,-1,7,-1,-1,
            -1,-1,-1,-1,-1,4,0,-1,8,1,-1,5,-1,0,-1,-1,-1,-1,-1,1,1,0,-1,8,6,-1,
            -1,-1,-1,-1,-1,3,5,9,4,1,9,-1,-1,-1,-1,-1,6,-1,-1,-1,-1,-1,-1,5,-1,
            -1,9,-1,-1,0,-1,-1,-1,-1,0,1,-1,3,-1,8,-1,1,-1,-1,-1,-1,2,5,6,2,6,-1,
            6,6,4,-1,9,-1,-1,-1,-1,5,8,-1,-1,-1,-1,-1,1,-1,-1,-1,-1,5,-1,7,-1,-1,
            -1,-1,9,4,2,1,8,-1,-1,-1,3,4,-1,-1,-1,-1,-1,-1,-1,5,-1,-1,-1,-1,-1,4,
            -1,9,-1,-1,-1,3,-1,-1,3,-1,-1,7,4,1,-1,-1,-1,-1,-1,-1,-1,-1,7,8,-1,9,
            0,-1,-1,-1,2,6,-1,8,-1,-1,-1,-1,-1,2,-1,4,2,-1,-1,6,8,-1,-1,-1,-1,-1,
            4,-1,-1
        };
        assertEquals(790, sol.sumNumbers(buildTree(vals)));
    }
}
