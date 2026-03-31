package trees;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;

public class InorderTraversal {

    public static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) {
            this.val = val;
        }
    }

    // ─── Solution ────────────────────────────────────────────────────────────
    // Inorder: left → root → right
    // Iterative with an explicit stack:
    //   - Push nodes going left until null
    //   - Pop, record value, then move to right child
    //
    // Time:  O(n) — every node pushed and popped exactly once
    // Space: O(h) — stack depth equals tree height

    public int treeSize(TreeNode A){
        if(A==null)
            return 0;

        return 1 + treeSize(A.left) + treeSize(A.right);
    }
    public int[] inorderTraversal(TreeNode A) {
        int n = treeSize(A);
        int[] ans = new int[n];

        Deque<TreeNode> dq = new ArrayDeque<>();
        //dq.offerFirst(A);
        TreeNode curr = A;
        //curr = curr.left;
        int idx = 0;

        while(!dq.isEmpty() || curr!=null){
            while(curr!=null){
                dq.offerFirst(curr);
                curr = curr.left;
            }

            curr = dq.pollFirst();
            if(idx<n){
                ans[idx] = curr.val;
                idx++;
            }


            if(curr.right!=null){
                //dq.offerFirst(curr.right);
                curr = curr.right;
            }
            else{
                curr = null;
            }
        }
        return ans;
    }
}
