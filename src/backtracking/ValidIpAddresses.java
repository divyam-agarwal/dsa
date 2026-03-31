package backtracking;

import java.util.*;

public class ValidIpAddresses {

    // ─── Solution ────────────────────────────────────────────────────────────

    public boolean ensureLower(String s1, String s2){
        int a = Integer.parseInt(s1);
        int b = Integer.parseInt(s2);

        return a<=b;
    }

    public boolean ensureHigher(String s1, String s2){
        int a = Integer.parseInt(s1);
        int b = Integer.parseInt(s2);

        return a>=b;
    }

    public String makeIPAddress(ArrayList<Integer> dotPositions, String A){
        StringBuilder sb = new StringBuilder(A);
        for(int i = 0;i<dotPositions.size();i++){
            sb.insert(dotPositions.get(i)+i, ".");
        }
        return sb.toString();
    }

    // index = start of the current segment being placed
    public void backtrack(ArrayList<String> ans, ArrayList<Integer> dotPositions, String A, int index){
        if(dotPositions.size() == 3){
            String toCheck = A.substring(index);
            if(toCheck.length() >= 1 && toCheck.length() <= 3 &&
               !(toCheck.length() > 1 && toCheck.charAt(0) == '0') &&
               ensureLower(toCheck, "255")){
                ans.add(makeIPAddress(dotPositions, A));
            }
            return;
        }

        // try segment lengths 1, 2, 3
        for(int len = 1; len <= 3; len++){
            if(index + len >= A.length()) break; // need at least 1 char remaining for next segments
            String segment = A.substring(index, index + len);
            if(segment.length() > 1 && segment.charAt(0) == '0') break; // leading zero
            if(!ensureLower(segment, "255")) break; // > 255
            dotPositions.add(index + len);
            backtrack(ans, dotPositions, A, index + len);
            dotPositions.remove(dotPositions.size() - 1);
        }
    }

    public ArrayList<String> restoreIpAddresses(String A) {
        ArrayList<String> ans = new ArrayList<String>();
        ArrayList<Integer> dotPositions = new ArrayList<>();
        backtrack(ans, dotPositions, A, 0);
        return ans;
    }


    // ─── Main (paste into Main.java to test) ─────────────────────────────────
    /*
    public static void main(String[] args) {
        ValidIpAddresses sol = new ValidIpAddresses();

        System.out.println(sol.restoreIpAddresses("25525511135"));
        // Expected: [255.255.11.135, 255.255.111.35]

        System.out.println(sol.restoreIpAddresses("0000"));
        // Expected: [0.0.0.0]

        System.out.println(sol.restoreIpAddresses("101023"));
        // Expected: [1.0.10.23, 1.0.102.3, 10.1.0.23, 10.10.2.3, 101.0.2.3]
    }
    */
}
