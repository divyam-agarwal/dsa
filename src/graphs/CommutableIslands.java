package graphs;

// InterviewBit: Commutable Islands
// https://www.interviewbit.com/problems/commutable-islands/
//
// Given A islands and a list of bidirectional bridges B, find the minimum
// total cost to make all islands reachable from each other (Minimum Spanning Tree).
// Return -1 if it is impossible to connect all islands.
//
// Approach: Kruskal's (sort edges by weight + Union-Find) or Prim's algorithm.
//
// Input:
//   A — number of islands (nodes), labelled 1..A
//   B — ArrayList of edges, each edge is [u, v, w]:
//         u, v  — islands the bridge connects (1-indexed)
//         w     — cost of the bridge
// Output: minimum cost to connect all islands, or -1 if impossible

import java.util.ArrayList;
import java.util.Comparator;

public class CommutableIslands {

    public int findParent(int node, int[] parent){
        if(parent[node]==node)
            return node;
        return parent[node] = findParent(parent[node], parent);
    }

    public boolean union(int node, int root, int[] parent, int[] rank){
        if(rank[node]>rank[root]){
            //swap root and node
            int temp = root;
            root = node;
            node = temp;
        }

        int p1 = findParent(node, parent);
        int p2 = findParent(root, parent);

        if(p1==p2)
            return false;
        else{
            parent[p1] = p2;
            rank[p2]++;
            return true;
        }
    }

    public int solve(int A, ArrayList<ArrayList<Integer>> edges) {
        edges.sort(Comparator.comparingInt(e -> e.get(2)));
        int[] parent = new int[A+1];
        int[] rank = new int[A+1];
        //init parent array for DSU
        for(int i = 1;i<=A;i++){
            parent[i] =i;
        }
        int cost = 0;
        for(ArrayList<Integer> edge: edges){
            int u = edge.get(0);
            int v = edge.get(1);
            int wt = edge.get(2);

            if(union(u,v, parent, rank)){
                cost += wt;
            }
        }
        return cost;
    }
}
