package graphs;

import java.util.ArrayList;

// InterviewBit: Cycle in Directed Graph
// https://www.interviewbit.com/problems/cycle-in-directed-graph/
//
// Given a directed graph with A nodes (1-indexed) and edges listed in B,
// return 1 if the graph contains a cycle, else return 0.
//
// Input:
//   A - number of nodes
//   B - list of directed edges, each edge is [u, v] (u → v)
// Output: 1 if cycle exists, 0 otherwise

public class CycleInDirectedGraph {

    public int findParent(int node, int[] parent){
        if(parent[node]==node)
            return node;
        return parent[node] = findParent(parent[node], parent);
    }

    public boolean union(int node, int root, int[] parent){
        int p1 = findParent(node, parent);
        int p2 = findParent(root, parent);

        if(p1==p2)
            return false;
        else{
            parent[p1] = p2;
            return true;
        }
    }
    public int solve(int n, ArrayList<ArrayList<Integer>> B) {
        int[] parent = new int[n+1];

        for(int i = 0;i<n+1;i++){
            parent[i] =i;
        }

        for(ArrayList<Integer> edge: B){
            if(!union(edge.get(0), edge.get(1), parent)){
                return 1;
            }
        }

        return 0;
    }
}
