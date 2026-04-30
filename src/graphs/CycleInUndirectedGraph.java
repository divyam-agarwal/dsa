package graphs;

// InterviewBit: Cycle in Undirected Graph
// https://www.interviewbit.com/problems/cycle-in-undirected-graph/
//
// Given an undirected graph with A nodes (1-indexed) and edges listed in B,
// return 1 if the graph contains a cycle, else return 0.
//
// A cycle in an undirected graph requires a back edge to a non-parent node.
// A single edge u-v is NOT a cycle; the smallest possible cycle uses 3 nodes.
// Self-loops (u-u) are considered a cycle.
//
// Approach: DFS — track parent to avoid treating the tree edge as a back edge.
// Alternatively, Union-Find: if both endpoints of an edge share the same root,
// adding that edge would create a cycle.
//
// Input:
//   A — number of nodes
//   B — list of undirected edges, each edge is [u, v]
// Output: 1 if cycle exists, 0 otherwise

import java.util.ArrayList;
import java.util.List;

public class CycleInUndirectedGraph {

    public int solve(int A, ArrayList<ArrayList<Integer>> B) {
        //check cycle using back edge
        List<List<Integer>> graph = new ArrayList<>();
        for(int i = 0;i<=A;i++){
            graph.add(new ArrayList<>());
        }

        for(int i = 0;i<B.size();i++){
            int u = B.get(i).get(0);
            int v = B.get(i).get(1);
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        boolean[] visited = new boolean[A+1];
        int[] parent = new int[A+1];
        boolean[] iscycle = new boolean[1];


        for(int i = 1;i<=A;i++){
            if(!visited[i]){
                parent[i] = -1;
                dfs(i, visited, graph, iscycle, parent);
            }
        }

        return iscycle[0] ? 1:0;

    }

    public void dfs(int u, boolean[] visited, List<List<Integer>> graph, boolean[] iscycle, int[] parent){
        if(iscycle[0])
            return;
        visited[u] = true;
        for(int v: graph.get(u)){
            if(!visited[v]){
                parent[v] = u;
                dfs(v, visited, graph, iscycle, parent);
            }
            else{
                if(parent[u]!=v){
                    //cycle detected
                    iscycle[0] = true;
                }
            }
        }
    }
}
