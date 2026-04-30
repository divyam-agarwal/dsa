package graphs;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class DeleteEdgeTest {

    DeleteEdge sol = new DeleteEdge();

    private ArrayList<Integer> weights(Integer... vals) {
        return new ArrayList<>(Arrays.asList(vals));
    }

    private ArrayList<ArrayList<Integer>> edges(int[]... pairs) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (int[] pair : pairs) {
            result.add(new ArrayList<>(Arrays.asList(pair[0], pair[1])));
        }
        return result;
    }

    // ─── Minimal trees ───────────────────────────────────────────────────────

    @Test
    void twoNodes() {
        // Weights: [3, 4], edge: 1-2
        // Only cut: sums 3 and 4 → product 12
        assertEquals(12, sol.deleteEdge(weights(3, 4), edges(new int[]{1, 2})));
    }

    @Test
    void twoNodesEqualWeights() {
        // Weights: [5, 5], edge: 1-2
        // product = 25
        assertEquals(25, sol.deleteEdge(weights(5, 5), edges(new int[]{1, 2})));
    }

    // ─── Path graphs ─────────────────────────────────────────────────────────

    @Test
    void pathThreeNodes() {
        // Weights: [1, 2, 3], path: 1-2-3
        // Cut 1-2: sums 1 and 5 → 5
        // Cut 2-3: sums 3 and 3 → 9  ← best
        assertEquals(9, sol.deleteEdge(weights(1, 2, 3), edges(new int[]{1, 2}, new int[]{2, 3})));
    }

    @Test
    void pathFourNodesSymmetric() {
        // Weights: [1, 1, 1, 1], path: 1-2-3-4
        // Cut 1-2: 1 and 3 → 3
        // Cut 2-3: 2 and 2 → 4  ← best
        // Cut 3-4: 3 and 1 → 3
        assertEquals(4, sol.deleteEdge(weights(1, 1, 1, 1), edges(
                new int[]{1, 2}, new int[]{2, 3}, new int[]{3, 4})));
    }

    @Test
    void pathFourNodesAsymmetric() {
        // Weights: [2, 3, 4, 1], path: 1-2-3-4
        // Total = 10
        // Cut 1-2: 2 × 8 = 16
        // Cut 2-3: 5 × 5 = 25  ← best
        // Cut 3-4: 9 × 1 = 9
        assertEquals(25, sol.deleteEdge(weights(2, 3, 4, 1), edges(
                new int[]{1, 2}, new int[]{2, 3}, new int[]{3, 4})));
    }

    // ─── Star graphs ─────────────────────────────────────────────────────────

    @Test
    void starFourNodesEqualWeights() {
        // Weights: [1, 1, 1, 1], center = 1, leaves = 2,3,4
        // Total = 4; any cut: 3 × 1 = 3  (all cuts identical)
        assertEquals(3, sol.deleteEdge(weights(1, 1, 1, 1), edges(
                new int[]{1, 2}, new int[]{1, 3}, new int[]{1, 4})));
    }

    @Test
    void starFourNodesBestCutIsHeaviestLeaf() {
        // Weights: [1, 2, 3, 4], center = 1, leaves = 2,3,4
        // Total = 10
        // Cut 1-2: (1+3+4) × 2 = 8 × 2 = 16
        // Cut 1-3: (1+2+4) × 3 = 7 × 3 = 21
        // Cut 1-4: (1+2+3) × 4 = 6 × 4 = 24  ← best
        assertEquals(24, sol.deleteEdge(weights(1, 2, 3, 4), edges(
                new int[]{1, 2}, new int[]{1, 3}, new int[]{1, 4})));
    }

    // ─── Balanced binary tree ────────────────────────────────────────────────

    @Test
    void balancedBinaryTreeAllWeightOne() {
        // 7 nodes, weight 1 each
        //       1
        //      / \
        //     2   3
        //    / \ / \
        //   4  5 6  7
        // Total = 7
        // Best cut: 1-2 or 1-3 → 4 × 3 = 12
        assertEquals(12, sol.deleteEdge(weights(1, 1, 1, 1, 1, 1, 1), edges(
                new int[]{1, 2}, new int[]{1, 3},
                new int[]{2, 4}, new int[]{2, 5},
                new int[]{3, 6}, new int[]{3, 7})));
    }

    // ─── General trees ───────────────────────────────────────────────────────

    @Test
    void caterpillarTree() {
        // Weights: [1, 2, 1, 3, 1], spine: 1-2-3, leaves: 4 on 2, 5 on 3
        //   1 - 2 - 3
        //       |   |
        //       4   5
        // Total = 8
        // Cut 1-2: 1 × 7 = 7
        // Cut 2-3: (1+2+4=7) ... wait node 4 is leaf of node 2
        //   subtree(2) = {2,4} = 2+3=5, subtree rest = {1,3,5} = 1+1+1=3 → 15
        //   Cut 2-3: subtree(3) = {3,5} = 1+1=2, rest = {1,2,4} = 1+2+3=6 → 12
        //   Cut 1-2: subtree(1) = {1} = 1, rest = {2,3,4,5} = 2+1+3+1=7 → 7
        //   Cut 2-4: subtree(4) = {4} = 3, rest = {1,2,3,5} = 1+2+1+1=5 → 15
        //   Cut 3-5: subtree(5) = {5} = 1, rest = {1,2,3,4} = 1+2+1+3=7 → 7
        // Best = 15
        assertEquals(15, sol.deleteEdge(weights(1, 2, 1, 3, 1), edges(
                new int[]{1, 2}, new int[]{2, 3}, new int[]{2, 4}, new int[]{3, 5})));
    }

    @Test
    void skewedTree() {
        // Weights: [1, 2, 3, 4, 5], chain: 1-2-3-4-5
        // Total = 15
        // Cut 3-4: sums 6 and 9 → 54  ← best
        // Cut 2-3: sums 3 and 12 → 36
        // Cut 4-5: sums 10 and 5 → 50
        assertEquals(54, sol.deleteEdge(weights(1, 2, 3, 4, 5), edges(
                new int[]{1, 2}, new int[]{2, 3}, new int[]{3, 4}, new int[]{4, 5})));
    }

    // ─── Modular arithmetic ──────────────────────────────────────────────────

    @Test
    void largeWeightsRequireModulo() {
        // Two nodes each with weight 10^9
        // product = 10^18 which overflows int; answer = (10^9 * 10^9) % (10^9+7)
        int w = 1_000_000_000;
        long product = ((long) w * w) % 1_000_000_007;
        assertEquals((int) product, sol.deleteEdge(weights(w, w), edges(new int[]{1, 2})));
    }

    // ─── Large random tree (223 nodes) ───────────────────────────────────────

    @Test
    void largeRandomTree223Nodes() {
        // Total weight sum = 116802
        // Best cut: edge (7→17), subtrees sum to 8773 and 108029
        // product = 8773 * 108029 = 947,738,417 (no modulo needed)
        ArrayList<Integer> A = weights(
            42, 468, 335, 501, 170, 725, 479, 359, 963, 465, 706, 146, 282, 828, 962, 492, 996,
            943, 828, 437, 392, 605, 903, 154, 293, 383, 422, 717, 719, 896, 448, 727, 772, 539,
            870, 913, 668, 300, 36, 895, 704, 812, 323, 334, 674, 665, 142, 712, 254, 869, 548,
            645, 663, 758, 38, 860, 724, 742, 530, 779, 317, 36, 191, 843, 289, 107, 41, 943, 265,
            649, 447, 806, 891, 730, 371, 351, 7, 102, 394, 549, 630, 624, 85, 955, 757, 841, 967,
            377, 932, 309, 945, 440, 627, 324, 538, 539, 119, 83, 930, 542, 834, 116, 640, 659,
            705, 931, 978, 307, 674, 387, 22, 746, 925, 73, 271, 830, 778, 574, 98, 513, 987, 291,
            162, 637, 356, 768, 656, 575, 32, 53, 351, 151, 942, 725, 967, 431, 108, 192, 8, 338,
            458, 288, 754, 384, 946, 910, 210, 759, 222, 589, 423, 947, 507, 31, 414, 169, 901,
            592, 763, 656, 411, 360, 625, 538, 549, 484, 596, 42, 603, 351, 292, 837, 375, 21, 597,
            22, 349, 200, 669, 485, 282, 735, 54, 1000, 419, 939, 901, 789, 128, 468, 729, 894,
            649, 484, 808, 422, 311, 618, 814, 515, 310, 617, 936, 452, 601, 250, 520, 557, 799,
            304, 225, 9, 845, 610, 990, 703, 196, 486, 94, 344, 524, 588, 315
        );
        ArrayList<ArrayList<Integer>> B = edges(
            new int[]{76,15}, new int[]{207,87}, new int[]{204,143}, new int[]{102,67},
            new int[]{203,9}, new int[]{77,65}, new int[]{173,92}, new int[]{199,82},
            new int[]{95,50}, new int[]{132,84}, new int[]{213,67}, new int[]{65,55},
            new int[]{116,74}, new int[]{195,64}, new int[]{52,3}, new int[]{194,58},
            new int[]{78,68}, new int[]{190,169}, new int[]{153,67}, new int[]{115,87},
            new int[]{96,85}, new int[]{215,22}, new int[]{138,38}, new int[]{19,11},
            new int[]{24,4}, new int[]{217,56}, new int[]{14,2}, new int[]{206,127},
            new int[]{211,154}, new int[]{148,111}, new int[]{29,25}, new int[]{30,17},
            new int[]{141,5}, new int[]{2,1}, new int[]{49,5}, new int[]{70,22},
            new int[]{220,69}, new int[]{9,6}, new int[]{193,80}, new int[]{156,69},
            new int[]{218,155}, new int[]{72,48}, new int[]{103,34}, new int[]{88,52},
            new int[]{120,62}, new int[]{5,1}, new int[]{61,16}, new int[]{81,66},
            new int[]{151,137}, new int[]{16,8}, new int[]{163,72}, new int[]{114,106},
            new int[]{188,173}, new int[]{31,19}, new int[]{59,4}, new int[]{85,50},
            new int[]{82,26}, new int[]{177,174}, new int[]{40,13}, new int[]{58,40},
            new int[]{186,43}, new int[]{113,69}, new int[]{121,73}, new int[]{104,23},
            new int[]{139,134}, new int[]{161,123}, new int[]{22,18}, new int[]{23,16},
            new int[]{91,30}, new int[]{90,16}, new int[]{80,16}, new int[]{117,22},
            new int[]{201,126}, new int[]{179,162}, new int[]{47,16}, new int[]{93,88},
            new int[]{12,10}, new int[]{167,119}, new int[]{36,14}, new int[]{200,132},
            new int[]{174,99}, new int[]{185,142}, new int[]{74,38}, new int[]{145,118},
            new int[]{150,88}, new int[]{134,7}, new int[]{175,37}, new int[]{178,57},
            new int[]{133,97}, new int[]{20,18}, new int[]{53,1}, new int[]{171,104},
            new int[]{98,75}, new int[]{122,40}, new int[]{169,13}, new int[]{13,1},
            new int[]{125,98}, new int[]{191,24}, new int[]{137,96}, new int[]{48,42},
            new int[]{34,3}, new int[]{127,98}, new int[]{71,33}, new int[]{21,19},
            new int[]{62,15}, new int[]{6,2}, new int[]{130,119}, new int[]{135,28},
            new int[]{181,93}, new int[]{159,105}, new int[]{41,2}, new int[]{54,20},
            new int[]{42,19}, new int[]{63,43}, new int[]{214,203}, new int[]{157,68},
            new int[]{100,80}, new int[]{187,153}, new int[]{183,9}, new int[]{209,52},
            new int[]{221,26}, new int[]{140,42}, new int[]{64,2}, new int[]{69,58},
            new int[]{94,50}, new int[]{99,71}, new int[]{44,33}, new int[]{75,44},
            new int[]{208,35}, new int[]{197,36}, new int[]{219,149}, new int[]{176,128},
            new int[]{126,7}, new int[]{158,15}, new int[]{182,49}, new int[]{108,19},
            new int[]{168,157}, new int[]{110,11}, new int[]{57,54}, new int[]{184,63},
            new int[]{170,49}, new int[]{18,6}, new int[]{146,15}, new int[]{45,34},
            new int[]{17,7}, new int[]{152,9}, new int[]{39,19}, new int[]{10,9},
            new int[]{4,1}, new int[]{124,36}, new int[]{118,20}, new int[]{11,10},
            new int[]{144,128}, new int[]{160,78}, new int[]{128,1}, new int[]{202,168},
            new int[]{28,16}, new int[]{33,27}, new int[]{223,141}, new int[]{164,14},
            new int[]{180,163}, new int[]{7,3}, new int[]{129,95}, new int[]{25,5},
            new int[]{142,87}, new int[]{222,175}, new int[]{37,26}, new int[]{107,3},
            new int[]{50,37}, new int[]{149,10}, new int[]{83,73}, new int[]{111,75},
            new int[]{51,26}, new int[]{73,66}, new int[]{15,3}, new int[]{56,30},
            new int[]{106,41}, new int[]{8,1}, new int[]{192,15}, new int[]{123,3},
            new int[]{32,17}, new int[]{165,131}, new int[]{189,72}, new int[]{154,24},
            new int[]{97,86}, new int[]{210,1}, new int[]{172,27}, new int[]{87,46},
            new int[]{55,52}, new int[]{119,34}, new int[]{216,89}, new int[]{136,2},
            new int[]{131,92}, new int[]{109,36}, new int[]{3,1}, new int[]{60,55},
            new int[]{147,21}, new int[]{43,33}, new int[]{84,33}, new int[]{112,47},
            new int[]{212,169}, new int[]{67,29}, new int[]{38,15}, new int[]{101,94},
            new int[]{143,23}, new int[]{162,84}, new int[]{92,48}, new int[]{27,5},
            new int[]{68,4}, new int[]{79,29}, new int[]{46,30}, new int[]{26,12},
            new int[]{105,103}, new int[]{196,142}, new int[]{198,50}, new int[]{166,85},
            new int[]{205,87}, new int[]{35,6}, new int[]{89,71}, new int[]{155,13},
            new int[]{66,16}, new int[]{86,78}
        );
        assertEquals(947_738_417, sol.deleteEdge(A, B));
    }
}
