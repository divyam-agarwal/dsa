package trees;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NodesAtDistanceK {

    public static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) {
            this.val = val;
        }
    }

    // ─── Solution ────────────────────────────────────────────────────────────
    // A node at distance K can be reached by going DOWN (through children) or
    // UP (through the parent) from the target. To allow upward traversal we
    // first record every node's parent with a DFS, then treat the tree as an
    // undirected graph and run a BFS from the target for exactly K steps.
    //
    // Time:  O(n)  — one DFS pass + one BFS pass, each visiting every node once
    // Space: O(n)  — parent map + visited set + BFS queue

    // A: root, B: target node value, C: distance K
    HashMap<TreeNode,TreeNode> parent = new HashMap<>();
    TreeNode targetNode;
    boolean found = false;

    public void updateParent(TreeNode root){
        if(root==null)
            return;

        if(root.left!=null){
            parent.put(root.left,root);
        }

        if(root.right!=null){
            parent.put(root.right,root);
        }

        updateParent(root.left);
        updateParent(root.right);
    }

    public void findTreeNode(TreeNode root, int B){
        if(root==null || found)
            return;

        if(root.val==B){
            found = true;
            targetNode = root;
            return;
        }

        findTreeNode(root.left,B);
        findTreeNode(root.right,B);
    }

    public ArrayList<Integer> solve(TreeNode A, int B, int C) {
        updateParent(A);
        findTreeNode(A,B);
        TreeNode Bth = targetNode;
        HashMap<TreeNode, Boolean> visited = new HashMap<>();
        ArrayList<Integer> ans = new ArrayList<>();

        Deque<TreeNode> dq = new ArrayDeque<>();
        int distance = 0;
        dq.offerFirst(Bth);
        visited.put(Bth, true);

        while(!dq.isEmpty()){
            int size = dq.size();

            if(distance==C){
                for(int i = 0;i<size;i++){
                    TreeNode temp = dq.pollLast();
                    ans.add(temp.val);
                }
                return ans;
            }

            for(int i = 0;i<size;i++){
                TreeNode temp = dq.pollLast();
                if(temp.left!=null && !visited.containsKey(temp.left)){
                    dq.offerFirst(temp.left);
                    visited.put(temp.left, true);
                }
                if(temp.right!=null && !visited.containsKey(temp.right)){
                    dq.offerFirst(temp.right);
                    visited.put(temp.right, true);
                }
                if(parent.containsKey(temp) && !visited.containsKey(parent.get(temp))){
                    dq.offerFirst(parent.get(temp));
                    visited.put(parent.get(temp), true);
                }
            }
            distance++;
        }
        return ans;
    }
}
