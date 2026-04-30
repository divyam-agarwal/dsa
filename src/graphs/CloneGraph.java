package graphs;

// InterviewBit: Clone Graph
// https://www.interviewbit.com/problems/clone-graph/
//
// Given a reference to a node in a connected undirected graph, return a
// deep copy (clone) of the entire graph.
//
// Each node has an integer label and a list of neighbors.
// The cloned graph must be structurally identical but use entirely new node
// objects — no original node should appear in the clone.
//
// Input:  reference to any node in the graph
// Output: reference to the corresponding node in the cloned graph

import java.util.*;

public class CloneGraph {


     public static class UndirectedGraphNode {
         int label;
         List<UndirectedGraphNode> neighbors;
         UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
     };

     //populate this while creating adj list
     public HashMap<Integer, Integer> nodeLabelMapping;
     //populate while getAdjList
     int totalNodes;


    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) return null;
        nodeLabelMapping = new HashMap<>();
        totalNodes = 0;
        List<List<Integer>> graph = getAdjList(node);

        UndirectedGraphNode[] cloned = new UndirectedGraphNode[totalNodes];
        for (int i = 0; i < totalNodes; i++)
            cloned[i] = new UndirectedGraphNode(nodeLabelMapping.get(i));

        boolean[] visited = new boolean[totalNodes];
        dfs(0, graph, visited, cloned);
        return cloned[0];
    }

    private List<List<Integer>> getAdjList(UndirectedGraphNode node) {
        HashMap<UndirectedGraphNode, Integer> nodeToNumber = new HashMap<>();
        List<List<Integer>> graph = new ArrayList<>();
        dfsinit(node, graph, nodeToNumber);
        return graph;
    }

    private int dfsinit(UndirectedGraphNode node, List<List<Integer>> graph, HashMap<UndirectedGraphNode, Integer> nodeToNumber) {
        graph.add(new ArrayList<>());
        int nodeNumber = graph.size()-1;
        totalNodes++;
        nodeLabelMapping.put(nodeNumber, node.label);
        nodeToNumber.put(node, nodeNumber);

        for(UndirectedGraphNode neighbor: node.neighbors){
            if(!nodeToNumber.containsKey(neighbor)){
                int newNodeNumber = dfsinit(neighbor, graph, nodeToNumber);
                graph.get(nodeNumber).add(newNodeNumber);
            } else {
                graph.get(nodeNumber).add(nodeToNumber.get(neighbor));
            }
        }
        return nodeNumber;
    }

    private void dfs(int i, List<List<Integer>> graph, boolean[] visited, UndirectedGraphNode[] cloned) {
        visited[i] = true;
        for (int v : graph.get(i)) {
            if (!visited[v]) dfs(v, graph, visited, cloned);
            cloned[i].neighbors.add(cloned[v]);
        }
    }
}
