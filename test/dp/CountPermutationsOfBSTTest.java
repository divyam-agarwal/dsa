package dp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountPermutationsOfBSTTest {
    CountPermutationsOfBST sol = new CountPermutationsOfBST();

    // ─── InterviewBit examples ────────────────────────────────────────────────

    @Test
    void example1_threeNodes_height1() {
        // [2,1,3] and [2,3,1] both give a balanced BST of height 1
        assertEquals(2, sol.cntPermBST(3, 1));
    }

    @Test
    void example2_threeNodes_height2() {
        // Remaining 4 of 3!=6 permutations give height 2
        assertEquals(4, sol.cntPermBST(3, 2));
    }

    // ─── Single node ─────────────────────────────────────────────────────────

    @Test
    void singleNode_height0() {
        // [1] → BST with one node, height = 0
        assertEquals(1, sol.cntPermBST(1, 0));
    }

    @Test
    void singleNode_heightTooTall() {
        // Can't have height 1 with only one node
        assertEquals(0, sol.cntPermBST(1, 1));
    }

    // ─── Two nodes ───────────────────────────────────────────────────────────

    @Test
    void twoNodes_height0_impossible() {
        // Two nodes always give height 1
        assertEquals(0, sol.cntPermBST(2, 0));
    }

    @Test
    void twoNodes_height1() {
        // [1,2] → right-skewed (h=1); [2,1] → left-skewed (h=1)
        assertEquals(2, sol.cntPermBST(2, 1));
    }

    // ─── Height impossible given node count ──────────────────────────────────

    @Test
    void heightZero_moreThanOneNode() {
        assertEquals(0, sol.cntPermBST(4, 0));
    }

    @Test
    void heightExceedsMaxPossible() {
        // Max height for 3 nodes is 2; height 3 is impossible
        assertEquals(0, sol.cntPermBST(3, 3));
    }

    // ─── Four nodes ──────────────────────────────────────────────────────────

    @Test
    void fourNodes_height1_impossible() {
        // Need root r where both subtrees have <=1 node: r-1<=1 and 4-r<=1 → impossible
        assertEquals(0, sol.cntPermBST(4, 1));
    }

    @Test
    void fourNodes_height2() {
        // atMost(4,2) - atMost(4,1) = 16 - 0 = 16
        assertEquals(16, sol.cntPermBST(4, 2));
    }

    @Test
    void fourNodes_height3() {
        // atMost(4,3) - atMost(4,2) = 24 - 16 = 8
        assertEquals(8, sol.cntPermBST(4, 3));
    }

    // ─── Five nodes ──────────────────────────────────────────────────────────

    @Test
    void fiveNodes_height2() {
        assertEquals(40, sol.cntPermBST(5, 2));
    }

    // ─── Total permutations sanity check ─────────────────────────────────────

    @Test
    void allPermutations_threeNodes_sumToFactorial() {
        // ways(3,1) + ways(3,2) = 2 + 4 = 6 = 3!
        int sum = sol.cntPermBST(3, 1) + sol.cntPermBST(3, 2);
        assertEquals(6, sum);
    }

    @Test
    void allPermutations_fourNodes_sumToFactorial() {
        // ways(4,2) + ways(4,3) = 16 + 8 = 24 = 4!
        int sum = sol.cntPermBST(4, 2) + sol.cntPermBST(4, 3);
        assertEquals(24, sum);
    }
}
