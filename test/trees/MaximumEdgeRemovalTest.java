package trees;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class MaximumEdgeRemovalTest {

    MaximumEdgeRemoval sol = new MaximumEdgeRemoval();

    private ArrayList<ArrayList<Integer>> edges(int[]... pairs) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        for (int[] p : pairs) {
            ArrayList<Integer> edge = new ArrayList<>();
            edge.add(p[0]);
            edge.add(p[1]);
            list.add(edge);
        }
        return list;
    }

    // ─── InterviewBit example ────────────────────────────────────────────────

    @Test
    void interviewBitExample() {
        // 6 nodes, edges: 1-2, 1-3, 3-4, 3-5, 3-6
        //      1
        //     / \
        //    2   3
        //       /|\
        //      4 5 6
        // Subtree {3,4,5,6} has size 4 (even) → cut edge 1-3
        // Remaining: {1,2} and {3,4,5,6} — both even. Answer = 1.
        assertEquals(1, sol.solve(6, edges(new int[]{1,2}, new int[]{1,3}, new int[]{3,4}, new int[]{3,5}, new int[]{3,6})));
    }

    // ─── Two nodes ───────────────────────────────────────────────────────────

    @Test
    void twoNodes() {
        // 1-2: cutting the only edge gives {1} and {2}, both size 1 (odd) — not allowed
        // But wait: that edge IS even-sized subtree of size 1? No, subtree of node 2 = size 1 (odd)
        // The whole tree is size 2 (even), but root doesn't count. Answer = 0.
        assertEquals(0, sol.solve(2, edges(new int[]{1, 2})));
    }

    // ─── Linear path (chain) ─────────────────────────────────────────────────

    @Test
    void chainSixNodes() {
        // 1-2-3-4-5-6
        // Subtrees from leaves: {6}=1, {5,6}=2→cut, {4,5,6}→0 after cut,
        // continuing: {3,0}=1, {2,3,0}=2→cut, root size=0. Answer=2.
        // Components: {5,6}, {3,4}, {1,2}
        assertEquals(2, sol.solve(6, edges(new int[]{1,2}, new int[]{2,3}, new int[]{3,4}, new int[]{4,5}, new int[]{5,6})));
    }

    @Test
    void chainFourNodes() {
        // 1-2-3-4
        // Subtree {3,4}=2→cut. Answer=1. Components: {1,2}, {3,4}
        assertEquals(1, sol.solve(4, edges(new int[]{1,2}, new int[]{2,3}, new int[]{3,4})));
    }

    // ─── Star graph ──────────────────────────────────────────────────────────

    @Test
    void starFourNodes() {
        // Center=1, leaves=2,3,4 — but 4 nodes total
        // Each leaf subtree has size 1 (odd), no cuts possible. Answer=0.
        assertEquals(0, sol.solve(4, edges(new int[]{1,2}, new int[]{1,3}, new int[]{1,4})));
    }

    @Test
    void starEightNodes() {
        // Center=1, leaves=2..8 — 8 nodes total
        // Pairing leaves: subtrees of size 1 (odd), can't cut any individual leaf.
        // No even-sized proper subtrees. Answer=0.
        assertEquals(0, sol.solve(8, edges(new int[]{1,2}, new int[]{1,3}, new int[]{1,4}, new int[]{1,5}, new int[]{1,6}, new int[]{1,7}, new int[]{1,8})));
    }

    // ─── Multiple cuts ────────────────────────────────────────────────────────

    @Test
    void multipleCuts() {
        // 8 nodes:
        //        1
        //       / \
        //      2   3
        //     / \   \
        //    4   5   6
        //   / \
        //  7   8
        // Subtree {4,7,8}=3 odd, {7}=1, {8}=1, {5}=1, {2,4,5,7,8}=5 odd
        // Subtree {6}=1, {3,6}=2→cut. Remaining root subtree={1,2,4,5,7,8}=6, odd
        // Hmm let me recount: 1+5=6? No: {1,2,4,7,8,5}=6 (even)→but it's root, no cut.
        // Answer=1.
        assertEquals(1, sol.solve(8,
            edges(new int[]{1,2}, new int[]{1,3}, new int[]{2,4}, new int[]{2,5},
                  new int[]{3,6}, new int[]{4,7}, new int[]{4,8})));
    }

    @Test
    void twoSymmetricSubtrees() {
        // 8 nodes:
        //       1
        //      / \
        //     2   3
        //    / \ / \
        //   4  5 6  7
        //  /
        // 8
        // Subtree {4,8}=2→cut, {5}=1, {2,5}+0=2→cut (after cutting 4's subtree)
        // wait: after cutting {4,8}, node 2's subtree = {2,5,0} = 2 → cut
        // Subtree {6}=1, {7}=1, {3,6,7}=3 odd
        // Root = 1+0+3 = 4 (even, but root — no cut)
        // Answer=2.
        assertEquals(2, sol.solve(8,
            edges(new int[]{1,2}, new int[]{1,3}, new int[]{2,4}, new int[]{2,5},
                  new int[]{3,6}, new int[]{3,7}, new int[]{4,8})));
    }
}
