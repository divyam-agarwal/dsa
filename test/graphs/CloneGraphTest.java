package graphs;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class CloneGraphTest {

    CloneGraph sol = new CloneGraph();

    // ─── Helpers ─────────────────────────────────────────────────────────────

    /** Collect all nodes reachable from root via BFS. */
    private Set<CloneGraph.UndirectedGraphNode> allNodes(CloneGraph.UndirectedGraphNode root) {
        Set<CloneGraph.UndirectedGraphNode> visited = new HashSet<>();
        Deque<CloneGraph.UndirectedGraphNode> queue = new ArrayDeque<>();
        queue.add(root);
        visited.add(root);
        while (!queue.isEmpty()) {
            CloneGraph.UndirectedGraphNode n = queue.poll();
            for (CloneGraph.UndirectedGraphNode nb : n.neighbors) {
                if (visited.add(nb)) queue.add(nb);
            }
        }
        return visited;
    }

    /**
     * Assert that:
     *  1. Every node in the clone has the same label as the original.
     *  2. Every node object in the clone is different from every original node object.
     *  3. Neighbor labels match between original and clone.
     */
    private void assertDeepClone(CloneGraph.UndirectedGraphNode original, CloneGraph.UndirectedGraphNode clone) {
        Set<CloneGraph.UndirectedGraphNode> origNodes = allNodes(original);
        Set<CloneGraph.UndirectedGraphNode> cloneNodes = allNodes(clone);

        // Same number of nodes
        assertEquals(origNodes.size(), cloneNodes.size());

        // No shared object references
        for (CloneGraph.UndirectedGraphNode c : cloneNodes) {
            assertFalse(origNodes.contains(c), "Clone shares a node object with the original");
        }

        // Build label → node maps
        Map<Integer, CloneGraph.UndirectedGraphNode> origByLabel = new HashMap<>();
        for (CloneGraph.UndirectedGraphNode n : origNodes) origByLabel.put(n.label, n);
        Map<Integer, CloneGraph.UndirectedGraphNode> cloneByLabel = new HashMap<>();
        for (CloneGraph.UndirectedGraphNode n : cloneNodes) cloneByLabel.put(n.label, n);

        // Same labels exist in both
        assertEquals(origByLabel.keySet(), cloneByLabel.keySet());

        // Neighbor structure matches
        for (int label : origByLabel.keySet()) {
            CloneGraph.UndirectedGraphNode o = origByLabel.get(label);
            CloneGraph.UndirectedGraphNode c = cloneByLabel.get(label);
            List<Integer> oLabels = new ArrayList<>();
            for (CloneGraph.UndirectedGraphNode nb : o.neighbors) oLabels.add(nb.label);
            List<Integer> cLabels = new ArrayList<>();
            for (CloneGraph.UndirectedGraphNode nb : c.neighbors) cLabels.add(nb.label);
            Collections.sort(oLabels);
            Collections.sort(cLabels);
            assertEquals(oLabels, cLabels, "Neighbor mismatch at node " + label);
        }
    }

    // ─── Null input ───────────────────────────────────────────────────────────

    @Test
    void nullNodeReturnsNull() {
        assertNull(sol.cloneGraph(null));
    }

    // ─── Single node ──────────────────────────────────────────────────────────

    @Test
    void singleNodeNoNeighbors() {
        CloneGraph.UndirectedGraphNode n = new CloneGraph.UndirectedGraphNode(1);
        CloneGraph.UndirectedGraphNode clone = sol.cloneGraph(n);

        assertNotNull(clone);
        assertNotSame(n, clone);
        assertEquals(1, clone.label);
        assertTrue(clone.neighbors.isEmpty());
    }

    // ─── Two nodes ────────────────────────────────────────────────────────────

    @Test
    void twoNodesConnected() {
        CloneGraph.UndirectedGraphNode a = new CloneGraph.UndirectedGraphNode(1);
        CloneGraph.UndirectedGraphNode b = new CloneGraph.UndirectedGraphNode(2);
        a.neighbors.add(b);
        b.neighbors.add(a);

        CloneGraph.UndirectedGraphNode clone = sol.cloneGraph(a);
        assertDeepClone(a, clone);
    }

    // ─── Linear chain ─────────────────────────────────────────────────────────

    @Test
    void linearChainFourNodes() {
        // 1 — 2 — 3 — 4
        CloneGraph.UndirectedGraphNode n1 = new CloneGraph.UndirectedGraphNode(1);
        CloneGraph.UndirectedGraphNode n2 = new CloneGraph.UndirectedGraphNode(2);
        CloneGraph.UndirectedGraphNode n3 = new CloneGraph.UndirectedGraphNode(3);
        CloneGraph.UndirectedGraphNode n4 = new CloneGraph.UndirectedGraphNode(4);
        n1.neighbors.add(n2); n2.neighbors.add(n1);
        n2.neighbors.add(n3); n3.neighbors.add(n2);
        n3.neighbors.add(n4); n4.neighbors.add(n3);

        assertDeepClone(n1, sol.cloneGraph(n1));
    }

    // ─── Cycle ────────────────────────────────────────────────────────────────

    @Test
    void triangleCycle() {
        // 1 — 2 — 3 — 1
        CloneGraph.UndirectedGraphNode n1 = new CloneGraph.UndirectedGraphNode(1);
        CloneGraph.UndirectedGraphNode n2 = new CloneGraph.UndirectedGraphNode(2);
        CloneGraph.UndirectedGraphNode n3 = new CloneGraph.UndirectedGraphNode(3);
        n1.neighbors.add(n2); n1.neighbors.add(n3);
        n2.neighbors.add(n1); n2.neighbors.add(n3);
        n3.neighbors.add(n1); n3.neighbors.add(n2);

        assertDeepClone(n1, sol.cloneGraph(n1));
    }

    // ─── Complete graph ───────────────────────────────────────────────────────

    @Test
    void completeGraphFourNodes() {
        // Every pair of the 4 nodes is connected
        CloneGraph.UndirectedGraphNode[] nodes = new CloneGraph.UndirectedGraphNode[4];
        for (int i = 0; i < 4; i++) nodes[i] = new CloneGraph.UndirectedGraphNode(i + 1);
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (i != j) nodes[i].neighbors.add(nodes[j]);

        assertDeepClone(nodes[0], sol.cloneGraph(nodes[0]));
    }

    // ─── Star topology ────────────────────────────────────────────────────────

    @Test
    void starGraphCentreNode() {
        // Centre node 1 connected to leaves 2, 3, 4, 5
        CloneGraph.UndirectedGraphNode centre = new CloneGraph.UndirectedGraphNode(1);
        for (int i = 2; i <= 5; i++) {
            CloneGraph.UndirectedGraphNode leaf = new CloneGraph.UndirectedGraphNode(i);
            centre.neighbors.add(leaf);
            leaf.neighbors.add(centre);
        }

        assertDeepClone(centre, sol.cloneGraph(centre));
    }

    @Test
    void starGraphLeafNode() {
        // Clone starting from a leaf rather than the centre
        CloneGraph.UndirectedGraphNode centre = new CloneGraph.UndirectedGraphNode(1);
        CloneGraph.UndirectedGraphNode leaf = new CloneGraph.UndirectedGraphNode(2);
        centre.neighbors.add(leaf);
        leaf.neighbors.add(centre);
        for (int i = 3; i <= 5; i++) {
            CloneGraph.UndirectedGraphNode other = new CloneGraph.UndirectedGraphNode(i);
            centre.neighbors.add(other);
            other.neighbors.add(centre);
        }

        assertDeepClone(leaf, sol.cloneGraph(leaf));
    }

    // ─── Mutation independence ────────────────────────────────────────────────

    @Test
    void mutatingCloneDoesNotAffectOriginal() {
        CloneGraph.UndirectedGraphNode a = new CloneGraph.UndirectedGraphNode(1);
        CloneGraph.UndirectedGraphNode b = new CloneGraph.UndirectedGraphNode(2);
        a.neighbors.add(b); b.neighbors.add(a);

        CloneGraph.UndirectedGraphNode cloneA = sol.cloneGraph(a);
        // Add a new neighbor to the clone's node — original must be unaffected
        cloneA.neighbors.add(new CloneGraph.UndirectedGraphNode(99));
        assertEquals(1, a.neighbors.size(), "Mutating clone should not affect original");
    }
}
