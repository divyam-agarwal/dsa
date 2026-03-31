package trees;

import java.util.ArrayList;

public class SumRootToLeaf {

    public static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) {
            this.val = val;
        }
    }

    // ─── Solution ────────────────────────────────────────────────────────────
    // Each root-to-leaf path spells out a number digit by digit.
    // DFS carrying the number built so far: at each node, num = num * 10 + val.
    // When a leaf is reached, add num to the running total.
    //
    // Time:  O(n) — every node visited once
    // Space: O(h) — recursion stack depth equals tree height

    public int REMAINDER = 1003;
    long ans = 0;

    public long getleafSum(ArrayList<Integer> temp){
        long a = 0;
        for(int i = 0; i < temp.size(); i++){
            a = a * 10 + temp.get(i);
        }
        return a;
    }

    public void leafSum(TreeNode root, ArrayList<Integer> temp){
        if(root==null)
            return;

        temp.add(root.val);
        if(root.left==null && root.right == null){
            ans += getleafSum(temp);
        }

        if(root.left!=null){
            leafSum(root.left, temp);
        }
        if(root.right!=null){
            leafSum(root.right, temp);
        }

        temp.remove(temp.size()-1);
    }

    public int sumNumbers(TreeNode A) {
        ans = 0;
        ArrayList<Integer> temp = new ArrayList<>();

        leafSum(A, temp);
        return (int) ans;
    }
}
