package trees;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InorderTraversalTest {

    InorderTraversal sol = new InorderTraversal();

    private InorderTraversal.TreeNode n(int val) {
        return new InorderTraversal.TreeNode(val);
    }

    // ─── InterviewBit example ────────────────────────────────────────────────

    @Test
    void interviewBitExample() {
        //    1
        //     \
        //      2
        //     /
        //    3
        // Inorder: [1, 3, 2]
        InorderTraversal.TreeNode root = n(1);
        root.right      = n(2);
        root.right.left = n(3);
        assertArrayEquals(new int[]{1, 3, 2}, sol.inorderTraversal(root));
    }

    // ─── Null / single node ──────────────────────────────────────────────────

    @Test
    void nullRoot() {
        assertArrayEquals(new int[]{}, sol.inorderTraversal(null));
    }

    @Test
    void singleNode() {
        assertArrayEquals(new int[]{7}, sol.inorderTraversal(n(7)));
    }

    // ─── Skewed trees ────────────────────────────────────────────────────────

    @Test
    void leftSkewed() {
        //     3
        //    /
        //   2
        //  /
        // 1
        // Inorder: [1, 2, 3]
        InorderTraversal.TreeNode root = n(3);
        root.left      = n(2);
        root.left.left = n(1);
        assertArrayEquals(new int[]{1, 2, 3}, sol.inorderTraversal(root));
    }

    @Test
    void rightSkewed() {
        // 1
        //  \
        //   2
        //    \
        //     3
        // Inorder: [1, 2, 3]
        InorderTraversal.TreeNode root = n(1);
        root.right       = n(2);
        root.right.right = n(3);
        assertArrayEquals(new int[]{1, 2, 3}, sol.inorderTraversal(root));
    }

    // ─── BST: inorder gives sorted output ────────────────────────────────────

    @Test
    void balancedBST() {
        //       4
        //      / \
        //     2   6
        //    / \ / \
        //   1  3 5  7
        // Inorder: [1, 2, 3, 4, 5, 6, 7]
        InorderTraversal.TreeNode root = n(4);
        root.left        = n(2);
        root.right       = n(6);
        root.left.left   = n(1);
        root.left.right  = n(3);
        root.right.left  = n(5);
        root.right.right = n(7);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7}, sol.inorderTraversal(root));
    }

    // ─── Asymmetric trees ────────────────────────────────────────────────────

    @Test
    void rootWithLeftChildOnly() {
        //   5
        //  /
        // 3
        // Inorder: [3, 5]
        InorderTraversal.TreeNode root = n(5);
        root.left = n(3);
        assertArrayEquals(new int[]{3, 5}, sol.inorderTraversal(root));
    }

    @Test
    void rootWithRightChildOnly() {
        // 5
        //  \
        //   8
        // Inorder: [5, 8]
        InorderTraversal.TreeNode root = n(5);
        root.right = n(8);
        assertArrayEquals(new int[]{5, 8}, sol.inorderTraversal(root));
    }

    @Test
    void deeperAsymmetricTree() {
        //        5
        //       / \
        //      3   8
        //         / \
        //        6   9
        //         \
        //          7
        // Inorder: [3, 5, 6, 7, 8, 9]
        InorderTraversal.TreeNode root = n(5);
        root.left              = n(3);
        root.right             = n(8);
        root.right.left        = n(6);
        root.right.right       = n(9);
        root.right.left.right  = n(7);
        assertArrayEquals(new int[]{3, 5, 6, 7, 8, 9}, sol.inorderTraversal(root));
    }
}
