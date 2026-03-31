package dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShortestCommonSuperstring {

    // ─── Solution ─────────────────────────────────────────────────────────────
    // Find the length of the shortest string that contains every string in A as
    // a substring.
    //
    // Steps:
    //   1. Remove strings that are already substrings of another string in A.
    //   2. Precompute overlap[i][j] = length of the longest suffix of A[i] that
    //      is also a prefix of A[j].
    //   3. Bitmask DP:
    //        dp[mask][i] = min length of superstring that covers exactly the
    //                      strings indicated by mask, ending with string i.
    //      Transition:
    //        dp[mask | (1<<j)][j] = min(dp[mask][i] + len(A[j]) - overlap[i][j])
    //      Base: dp[1<<i][i] = len(A[i])
    //      Answer: min over i of dp[(1<<n)-1][i]
    //
    // Time:  O(2^n * n^2)
    // Space: O(2^n * n)
    int overlap2Strings(String s1, String s2){
        int n1 = s1.length();
        int n2 = s2.length();
        for(int len = Math.min(n1,n2);len>=1;len--){
            if(s1.substring(n1-len).equals(s2.substring(0,len))){
                return len;
            }
        }
        return 0;
    }

    public int[][] getOverlap(String[] A){
        int n = A.length;
        int[][] overlap = new int[n][n];
        for(int i = 0;i<n;i++){
            for(int j = 0;j<n;j++){
                if(i==j){
                    overlap[i][j] = A[i].length();
                }
                else{
                    overlap[i][j] = overlap2Strings(A[i], A[j]);
                }

            }
        }
        return overlap;
    }

    public int solve(String[] A) {
        int n = A.length;
        int[][] dp = new int[1<<n][n];
        int[][] parent = new int[1<<n][n];

        int[][] overlap = getOverlap(A);

        for(int mask = 1;mask< (1<<n);mask++){
            Arrays.fill(dp[mask], -1);
            Arrays.fill(parent[mask], -1);
        }

        for(int i = 0;i<n;i++){
            int mask = 1<<i;
            dp[mask][i] = 0;
            parent[mask][i] = -1;
        }

        for(int mask = 1; mask < 1<<n;mask++){
            for(int i = 0;i<n;i++){
                if((mask & (1<<i))!=0){
                    int prevMask = mask ^ 1<<i;
                    // assuming ith is the last string in the current mask and
                    //trying to find max overlap here
                    for(int j = 0;j<n;j++){
                        if((prevMask & 1<<j) !=0){
                            //try overlap A[j], A[i]
                            if(dp[prevMask][j]+ overlap[j][i] > dp[mask][i]){
                                dp[mask][i] = (dp[prevMask][j]+ overlap[j][i]);
                                parent[mask][i] = j;
                            }

                        }

                    }
                }
            }
        }

        //get max from last row of dp
        int maxOverlap = 0;
        for(int i = 0;i<n;i++){
            int lastMask = (1<<n) -1;
            maxOverlap = Math.max(maxOverlap, dp[lastMask][i]);
        }

        int allStringslength = 0;
        for(String s : A){
            allStringslength+= s.length();
        }

        return allStringslength - maxOverlap;

     }
}
