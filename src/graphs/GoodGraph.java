package graphs;

import java.util.ArrayList;

// InterviewBit: Good Graph
// https://www.interviewbit.com/problems/good-graph/
//
// Given a directed graph of N nodes where each node points to exactly one node.
// A node is 'good' if:
//   1. It is node 1 (the special node), OR
//   2. It points to node 1, OR
//   3. It points to a good node.
//
// Return the minimum number of pointer changes to make all nodes good.
//
// Key insight: in a functional graph every connected component has exactly one
// cycle. A node is good iff its path eventually reaches node 1. Any cycle that
// does not contain node 1 must have exactly one of its pointers redirected to
// reach node 1. So the answer = number of cycles that do NOT contain node 1.
//
// Input: 1-indexed array A where A[i] is the node that node (i+1) points to.
// Constraints: 1 <= N <= 10^6, 1 <= A[i] <= N

public class GoodGraph {

    public int findParent(int node, int[] parent){
        if(parent[node]==node)
            return node;
        return parent[node] = findParent(parent[node] , parent);
    }

    public boolean union(int root, int node, int[] parent){
        int p1 = findParent(root, parent);
        int p2 = findParent(node, parent);

        if(p1==p2)
            return false;//cycle detected
        else{
            parent[p2] = p1;
            return true;
        }
    }

    public int solve(ArrayList<Integer> A) {
        int n = A.size();
        int[] parent = new int[n+1];
        for(int i = 0;i<n+1;i++){
            parent[i]= i;
        }
        int ans = 0;
        for(int i = 1;i<n;i++){
            if(!union(A.get(i),i+1, parent)){
                //this edge is forming a cycle. so we remove this and make i point to 1
                ans++;
                union(1,i+1, parent);
            }
            else{
                //join normally no issue
            }
        }

        if(union(1,A.get(0), parent)){
           ans++;
        }
        return ans;
    }
}
