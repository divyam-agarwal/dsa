package dp;

public class LongestCommonSubsequence {

    // ─── Solution ────────────────────────────────────────────────────────────
    // dp[i][j] = LCS length of A[0..i-1] and B[0..j-1].
    // If A[i-1] == B[j-1]: dp[i][j] = dp[i-1][j-1] + 1
    // Else:                 dp[i][j] = max(dp[i-1][j], dp[i][j-1])
    //
    // Time:  O(m * n)
    // Space: O(m * n)

    public int solve(String A, String B) {
        /*
            if A[i]==B[j]
            dp[i][j] = dp[i-1][j-1] + 1;

            else
            dp[i][j] = max(dp[i-1][j], dp[i][j-1]);

         */

        int n = A.length();
        int m = A.length();

        int[][] dp = new int[n+1][m+1];//init with all values = 0
        for(int i = 1; i<n+1;i++){
            for(int j = 1;j<m+1;j++){
                if(A.charAt(i-1)==B.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }
                else{
                    dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }

        return dp[n][m];


    }
}
