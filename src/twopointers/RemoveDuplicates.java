package twopointers;

import java.util.*;

public class RemoveDuplicates {

    // ─── Solution ────────────────────────────────────────────────────────────
    // Two pointers: l tracks the position of the last unique element written,
    // r scans forward. When A[r] differs from A[l], write it at l+1 and advance l.

    public int removeDuplicates(ArrayList<Integer> a) {
        int r = 0;
        for(int i = 0;i<a.size();i++){
            if(i+1<a.size()){
                if(a.get(i)==a.get(i+1)){
                    r = i+1;
                    while(r<a.size() && a.get(r)==a.get(i)){
                        r++;
                    }
                    a.subList(i+1, r).clear();
                }
            }
        }
        System.out.println(a.size());
        return a.size();
    }

    // ─── Main (paste into Main.java to test) ─────────────────────────────────
    /*
    public static void main(String[] args) {
        RemoveDuplicates sol = new RemoveDuplicates();

        ArrayList<Integer> a1 = new ArrayList<>(Arrays.asList(1, 1, 2));
        System.out.println(sol.removeDuplicates(a1)); // Expected: 2

        ArrayList<Integer> a2 = new ArrayList<>(Arrays.asList(1, 2, 2, 3, 3));
        System.out.println(sol.removeDuplicates(a2)); // Expected: 3

        ArrayList<Integer> a3 = new ArrayList<>(Arrays.asList(1, 1, 1, 1));
        System.out.println(sol.removeDuplicates(a3)); // Expected: 1
    }
    */
}
