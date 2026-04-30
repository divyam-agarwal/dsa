package graphs;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

// InterviewBit: Largest Distance Between Nodes of a Tree
// https://www.interviewbit.com/problems/largest-distance-between-nodes-of-a-tree/
//
// Given an array A of size N where A[i] is the parent of node i.
// The root node has A[root] = -1.
// Return the largest distance between any two nodes in the tree.
// Distance is measured in number of edges on the path between the two nodes.
//
// Constraints:
//   1 <= N <= 10^4

public class LargestDistanceBetweenNodes {

    public static class DistanceNodePair{
        int node;
        int distance;

        public DistanceNodePair(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public int solve(ArrayList<Integer> A) {
        int root = 0;
        List<List<Integer>> graph = new ArrayList<>();

        for(int i = 0;i<A.size();i++){
            graph.add(new ArrayList<>());
        }

        for(int i =0;i<A.size();i++){
            int u = i;
            int v = A.get(i);
            if(A.get(i)!=-1){
                graph.get(u).add(v);
                graph.get(v).add(u);
            }
            else{
                root = u;
            }
        }

        boolean[] visited = new boolean[A.size()];
        DistanceNodePair p = bfs(root, graph, visited);

        visited = new boolean[A.size()];
        DistanceNodePair ans = bfs(p.node, graph, visited);

        return ans.distance;
    }

    private DistanceNodePair bfs(int root, List<List<Integer>> graph, boolean[] visited) {
        Deque<Integer> dq = new ArrayDeque<>();
        int level = 0;
        dq.addFirst(root);
        int lastNode = root;

        while(!dq.isEmpty()){
            //process all same level nodes
            int size = dq.size();
            for(int i = 0;i<size && !dq.isEmpty();i++){
                int u = dq.pollLast();
                visited[u] = true;
                lastNode = u;
                for(int neighbor: graph.get(u)){
                    if(!visited[neighbor]){
                        dq.addFirst(neighbor);
                    }
                }
            }
            level++;
        }

        return new DistanceNodePair(lastNode, level-1);
    }
}
