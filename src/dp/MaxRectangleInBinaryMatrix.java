package dp;

import java.util.ArrayList;

public class MaxRectangleInBinaryMatrix {
    // Given a 2D binary matrix A of size N x M, find the largest rectangle
    // containing only 1s and return its area.
    public int solve(ArrayList<ArrayList<Integer>> A) {
        int n = A.size();
        int m = A.getFirst().size();

        int[][] dp = new int[n][m];
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(j==0)
                    dp[i][j] = A.get(i).get(j);
                else{
                    if(A.get(i).get(j)!=0)
                        dp[i][j] = dp[i][j-1] + A.get(i).get(j);
                }
            }
        }
        int ans = 0;
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(dp[i][j]!=0){
                    int k = i;
                    int minValue = dp[i][j];
                    while(k>=0 && dp[k][j]>0){
                        minValue = Math.min(minValue, dp[k][j]);
                        ans = Math.max(ans, minValue*(i-k+1));
                        k--;
                    }
                }
            }
        }
        return ans;
    }
}
