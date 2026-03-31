package dp;

import java.util.ArrayList;
import java.util.Arrays;

public class MaxEdgeQueries {

    // ─── Solution ─────────────────────────────────────────────────────────────
    // Given a weighted tree of N nodes and Q queries, for each query (u, v)
    // return the maximum edge weight on the path from u to v.
    //
    // A: list of edges, each [u, v, w]
    // B: list of queries, each [u, v]
    //
    // TODO

//    First argument is a 2-D array A of size (N-1) x 3  where  (A[i][0], A[i][1]) denotes an edge of the tree from node A[i][0] to node A[i][1] with weight A[i][2].
//
//    Second argument is a 2-D array B of size Q x 2 denoting the queries, B[i][0] denotes u and B[i][1] denotes v.
    int n;
    int LOG = 17;
    int[][] up;
    int[] depth;
    int[][] maxEdge;
    ArrayList<ArrayList<Pair>> adj = new ArrayList<>();

    public static class Pair{
        int wt;
        int v;

        public Pair(int v, int wt) {
            this.wt = wt;
            this.v = v;
        }

        public int getWt() {
            return wt;
        }

        public void setWt(int wt) {
            this.wt = wt;
        }

        public int getV() {
            return v;
        }

        public void setV(int v) {
            this.v = v;
        }
    }

    public void dfs(int u, int parent, int parentEdgeWt){
        up[u][0] = parent;
        maxEdge[u][0] = parentEdgeWt;
        for(int k = 1;k<LOG;k++){
            up[u][k] = up[up[u][k-1]][k-1];
            maxEdge[u][k] = Math.max(maxEdge[u][k-1], maxEdge[up[u][k-1]][k-1]);

        }
        ArrayList<Pair> neighbours = adj.get(u);
        for(Pair p: neighbours){
            int v = p.getV();
            int wt = p.getWt();
            depth[v] = depth[u]+1;
            dfs(v, u, wt);
        }
    }

    public int solveQuery(int u, int v){
        int ans = 0;
        if(depth[u]< depth[v]){
            //swap u,v. will lift u
            int t = v;
            v = u;
            u = t;
        }
        int diff = depth[u] - depth[v];

        int newU = u;
        int liftCount = 0;
        while(diff!=0){

            if((diff & 1) !=0){
                ans = Math.max(ans, maxEdge[newU][liftCount]);
                newU = up[newU][liftCount];
                liftCount++;
            }
            diff = diff>>1;
        }

        //now both are at same depth
        if(newU==v)
            return ans;

        for(int k = LOG-1;k>=0;k--){
            if(up[newU][k]!=up[v][k]){
                ans = Math.max(ans, Math.max(maxEdge[newU][k], maxEdge[v][k]));
                newU = up[newU][k];
                v = up[v][k];
            }
        }

        ans = Math.max(ans, Math.max(maxEdge[newU][0], maxEdge[v][0]));
        return ans;
    }



    public ArrayList<Integer> solve(ArrayList<ArrayList<Integer>> A, ArrayList<ArrayList<Integer>> B) {
        //build adj list
        n = A.size()+1;
        up = new int[n+1][LOG];
        depth = new int[n+1];
        maxEdge = new int[n+1][LOG];
        adj = new ArrayList<>();              // reset for each call
        for(int i = 0;i<=n;i++){             // 0..n so adj.get(u) is valid for 1-indexed nodes
            adj.add(new ArrayList<>());
        }
        int[] parent = new int[n+1];
        Arrays.fill(parent, -1);

        for(ArrayList<Integer> edge: A){
            int u = edge.get(0);
            int v = edge.get(1);
            int wt = edge.get(2);
            Pair p1 = new Pair(v, wt);
            adj.get(u).add(p1);
            parent[v] = u;

        }
        //to get ancestors

        //root - 1
        dfs(1, 1, 0);

        ArrayList<Integer> ans = new ArrayList<>();
        for(ArrayList<Integer> query: B){
            ans.add(solveQuery(query.get(0), query.get(1)));
        }

        return ans;
    }
}
