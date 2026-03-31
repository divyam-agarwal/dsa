package backtracking;

import java.util.*;
import java.util.stream.Collectors;

public class Subsets {

    // ─── Solution ────────────────────────────────────────────────────────────

    public void backtrack(ArrayList<ArrayList<Integer>> ans, ArrayList<Integer> currentSubset, ArrayList<Integer> A, int start){
        if(start>=A.size()){
            ans.add(currentSubset);
            return;
        }

        backtrack(ans, currentSubset, A, start+1);
        currentSubset.add(A.get(start));
        backtrack(ans, currentSubset, A, start+1);
        currentSubset.remove(currentSubset.size()-1);

    }
    public ArrayList<ArrayList<Integer>> subsets(ArrayList<Integer> A) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> currentSubset = new ArrayList<>();

        backtrack(ans, currentSubset, A, 0);

        for(ArrayList<Integer> a: ans){
            System.out.println(a);
        }

        return ans;
    }


    // ─── Main (paste into Main.java to test) ─────────────────────────────────
    /*
    public static void main(String[] args) {
        Subsets sol = new Subsets();

        System.out.println(sol.subsets(new int[]{1, 2, 3}));
        // Expected: [[], [1], [1,2], [1,2,3], [1,3], [2], [2,3], [3]]

        System.out.println(sol.subsets(new int[]{}));
        // Expected: [[]]
    }
    */
}
