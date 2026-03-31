package trees;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class ValidBSTFromPreorderTest {

    ValidBSTFromPreorder sol = new ValidBSTFromPreorder();

    private ArrayList<Integer> list(Integer... vals) {
        return new ArrayList<>(Arrays.asList(vals));
    }

    // ─── InterviewBit examples ───────────────────────────────────────────────

    @Test
    void example_validPreorder() {
        // BST:    5
        //        / \
        //       2   7
        //      / \
        //     1   3
        // Preorder: [5, 2, 1, 3, 7] → valid
        assertEquals(1, sol.solve(list(5, 2, 1, 3, 7)));
    }

    @Test
    void example_invalidPreorder() {
        // [5, 2, 6, 1, 3] → invalid: 1 appears after 6 has pushed lowerBound to 5
        assertEquals(0, sol.solve(list(5, 2, 6, 1, 3)));
    }

    // ─── Valid cases ─────────────────────────────────────────────────────────

    @Test
    void singleElement() {
        assertEquals(1, sol.solve(list(1)));
    }

    @Test
    void strictlyIncreasing_rightSkewedBST() {
        // 1 → 2 → 3 → 4 (each node is right child)
        assertEquals(1, sol.solve(list(1, 2, 3, 4)));
    }

    @Test
    void strictlyDecreasing_leftSkewedBST() {
        // 4 → 3 → 2 → 1 (each node is left child)
        assertEquals(1, sol.solve(list(4, 3, 2, 1)));
    }

    @Test
    void balancedBST() {
        //       4
        //      / \
        //     2   6
        //    / \ / \
        //   1  3 5  7
        assertEquals(1, sol.solve(list(4, 2, 1, 3, 6, 5, 7)));
    }

    @Test
    void rootOnlyWithRightChild() {
        assertEquals(1, sol.solve(list(5, 10)));
    }

    @Test
    void rootOnlyWithLeftChild() {
        assertEquals(1, sol.solve(list(5, 3)));
    }

    // ─── Invalid cases ───────────────────────────────────────────────────────

    @Test
    void rightValueAppearsInLeftSubtree() {
        // Root 5, then 7 (right child), then 6 — 6 is a left child of 7
        // but 7 is in root's right subtree, so 6 > 5: still valid BST
        // Distinguish: [5, 7, 2] → 2 < lowerBound(5), invalid
        assertEquals(0, sol.solve(list(5, 7, 2)));
    }

    @Test
    void smallInvalidThreeNodes() {
        // [3, 5, 1] → after seeing 5 (right subtree), lowerBound = 3; 1 < 3, invalid
        assertEquals(0, sol.solve(list(3, 5, 1)));
    }

    @Test
    void leftValueAfterMovingToRightSubtree() {
        // [10, 5, 1, 7, 40, 50, 8] → 8 < lowerBound(10), invalid
        assertEquals(0, sol.solve(list(10, 5, 1, 7, 40, 50, 8)));
    }

    @Test
    void invalidAtStart() {
        // [3, 1, 4, 2] → after seeing 4 (right subtree), lowerBound = 3; 2 < 3
        assertEquals(0, sol.solve(list(3, 1, 4, 2)));
    }

    @Test
    void duplicateValueInLargeSequence() {
        // 763 appears at indices 41 and 42 — duplicate key violates strict BST
        assertEquals(0, sol.solve(list(315,279,263,205,187,184,70,68,141,100,176,185,193,309,839,749,491,384,364,416,392,386,418,417,457,433,655,645,596,584,524,630,748,667,658,692,735,732,805,787,773,763,763,796,897,896,859,863,886,960,948,943,969,996,989)));
    }
}
