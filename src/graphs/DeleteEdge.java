package graphs;

import java.util.ArrayList;
import java.util.List;

// InterviewBit: Delete Edge!
// https://www.interviewbit.com/problems/delete-edge/
//
// Given an undirected tree with N nodes (1-indexed) and node weights,
// delete exactly one edge so that the product of the sums of node weights
// in the two resulting subtrees is maximised.
//
// Return the maximum product modulo 1,000,000,007.
//
// Input:
//   A - node weights (A.get(i) is the weight of node i+1, 0-indexed)
//   B - list of undirected edges, each edge is [u, v]
// Output: maximum product of the two subtree weight sums, mod 1_000_000_007

public class DeleteEdge {
    long MODULO = 1_000_000_007L;

    public long populateSubtreeSum(int u, long[] subtree, boolean[] visited, List<List<Integer>> graph, ArrayList<Integer> A){
        visited[u]=true;
        long localSum = A.get(u-1);
        for(int v:graph.get(u)){
            if(!visited[v]){
                if(subtree[v]==0)
                    localSum += populateSubtreeSum(v, subtree, visited, graph, A);
                else
                    localSum+= subtree[v];
            }
            localSum%=MODULO;
        }
        subtree[u] = localSum;
        return localSum;
    }

    public void dfs(int u, long[] subtree, List<List<Integer>> graph, ArrayList<Integer> A, boolean[] visited, long[] result, long totalSum){
        visited[u] = true;
        for(int v: graph.get(u)){
            if(!visited[v]){
                long l = subtree[v];
                long r = totalSum - l;
                result[0] = Math.max(result[0], l*r);
                result[0]%=MODULO;
                dfs(v,subtree, graph, A, visited, result, totalSum);
            }
        }
    }
    public int deleteEdge(ArrayList<Integer> A, ArrayList<ArrayList<Integer>> B) {
        List<List<Integer>> graph = new ArrayList<>();
        int n = A.size();

        for(int i =0;i<=n;i++){
            graph.add(new ArrayList<>());
        }

        for(ArrayList<Integer> edge: B){
            int u = edge.get(0);
            int v = edge.get(1);
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        //get subtree sum rooted at 1
        long[] subtree = new long[n+1];
        boolean[] visited = new boolean[n+1];

        long totalSum = populateSubtreeSum(1, subtree, visited, graph, A);

        // now do dfs again assuming edge gone for each subtree
        long[] result = new long[1];

        visited = new boolean[n+1];
        dfs(1, subtree, graph, A, visited, result, totalSum);
        return (int)result[0];

    }
}
