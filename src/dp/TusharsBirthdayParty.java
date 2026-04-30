package dp;

import java.util.ArrayList;
import java.util.HashMap;

public class TusharsBirthdayParty {
    // N friends each need to eat exactly A[i] units.
    // M dishes are available: dish j capacity B[j] and costs C[j] portions (unbounded).
    // For each friend, find the minimum cost to eat exactly A[i] units.
    // Return the total minimum cost across all friends.
    public int solve(ArrayList<Integer> A, ArrayList<Integer> B, ArrayList<Integer> C) {
        int maxFriendCapacity = A.stream().max(Integer::compareTo).get();
        int[][] dp = new int[B.size()][maxFriendCapacity+1];
        HashMap<Integer, Integer> mp = new HashMap<>();
        for(Integer a : A){
            mp.put(a,Integer.MAX_VALUE);
        }

        for(int i = 0;i<dp.length;i++){
            for(int j = 0;j<dp[0].length;j++){
                if(j==0)
                    dp[i][j] = 0;
                else
                    dp[i][j] = Integer.MAX_VALUE;
            }
        }


        for(int j = 1;j<dp[0].length;j++){
            if(j-B.get(0)>=0 && dp[0][j-B.get(0)]!=Integer.MAX_VALUE){
                dp[0][j] = dp[0][j-B.get(0)] + C.get(0);
            }
            if(mp.containsKey(j) && dp[0][j]< mp.get(j)){
                mp.put(j, dp[0][j]);
            }
        }

        for(int i = 1;i<dp.length;i++){
            for(int j = 1;j<dp[0].length;j++){
                dp[i][j] = dp[i-1][j];
                if(j-B.get(i)>=0 && dp[i][j-B.get(i)]!=Integer.MAX_VALUE){
                    dp[i][j] = Math.min(dp[i-1][j] , dp[i][j-B.get(i)]+ C.get(i));
                }


                if(mp.containsKey(j) && dp[i][j]< mp.get(j)){
                    mp.put(j, dp[i][j]);
                }
            }
        }

        int ans = 0;
        for(Integer a: A){
            if(mp.containsKey(a))
                ans+= mp.get(a);
        }

        return ans;
    }
}
