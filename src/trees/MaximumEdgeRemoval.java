package trees;

import java.util.ArrayList;
import java.util.List;

public class MaximumEdgeRemoval {
    int cuts;
    public ArrayList<ArrayList<Integer>> getGraph(int A, ArrayList<ArrayList<Integer>> B){
        //assuming node 0 has no edges, just for easy code
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= A; i++) {
            graph.add(new ArrayList<>());
        }
        for(ArrayList<Integer> edge : B){
            graph.get(edge.get(0)).add(edge.get(1));
        }

        return graph;

    }

    public int dfs(ArrayList<ArrayList<Integer>> graph, int node, boolean[] visited){
        visited[node] = true;
        int sizeAtNode = 1;

        for(int i = 0;i<graph.get(node).size();i++){
            int v = graph.get(node).get(i);
            if(!visited[v]){
                int subTreeNodes = dfs(graph, v, visited);

                if(subTreeNodes%2==0){
                    cuts++;
                }
                else{
                    sizeAtNode+= subTreeNodes;
                }
            }
        }
        return sizeAtNode;
    }

    public int solve(int A, ArrayList<ArrayList<Integer>> B) {
        cuts = 0;
        ArrayList<ArrayList<Integer>> graph = getGraph(A,B);
        boolean[] visited = new boolean[A+1];
        int nodes = dfs(graph, 1, visited);

        return cuts;
    }
}
