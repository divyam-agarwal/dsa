package dp;

import java.util.ArrayList;
import java.util.List;

public class MaxPathInTriangle {
    public int solve(List<List<Integer>> A) {
        int n = A.size();
        int[][] dp = new int[n+1][n+1];

        dp[1][0] = A.get(0).get(0);
        int ans = dp[1][0];
        for(int i = 2;i<=n;i++){
            for(int j= 0;j<i;j++){
                dp[i][j] = Math.max(dp[i][j], dp[i-1][j]+ A.get(i-1).get(j));
                if(j>0){
                    dp[i][j] = Math.max(dp[i][j], dp[i-1][j-1]+ A.get(i-1).get(j));
                }
                if(i==n){
                    ans = Math.max(ans, dp[i][j]);
                }
            }
        }
        return ans;
    }
}
