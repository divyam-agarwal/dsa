package trees;

import java.util.HashSet;

public class CoveredUncoveredNodes {

    public static class TreeNode {
        int val;
        CoveredUncoveredNodes.TreeNode left, right;
        TreeNode(int val) {
            this.val = val;
        }
    }

    public int leftCover(TreeNode A, HashSet<TreeNode> hs){
        if(A==null){
            return 0;
        }
        int sum = 0;

        if(!hs.contains(A)){
            hs.add(A);
            sum+=A.val;
        }

        if(A.left!=null){
            return sum + leftCover(A.left, hs);
        }
        else{
            return sum + leftCover(A.right, hs);
        }

    }

    public int rightCover(TreeNode A, HashSet<TreeNode> hs){
        if(A==null){
            return 0;
        }
        int sum = 0;

        if(!hs.contains(A)){
            hs.add(A);
            sum+=A.val;
        }

        if(A.right!=null){
            return sum + rightCover(A.right, hs);
        }
        else{
            return sum + rightCover(A.left, hs);
        }

    }

    public int totalSum(TreeNode A){
        if(A==null){
            return 0;
        }
        int sum = A.val;



        if(A.right!=null){
            sum += totalSum(A.right);
        }
        if(A.left!=null){
            sum += totalSum(A.left);
        }

        return sum;



    }


    public long coveredNodes(TreeNode A) {
        HashSet<TreeNode> hs = new HashSet<>();
        int left = leftCover(A,hs);
        int right = rightCover(A,hs);

        int total = totalSum(A);
        int uncovered = total - left-right;
        int covered = left+right;

        return Math.abs(covered-uncovered);

    }
}