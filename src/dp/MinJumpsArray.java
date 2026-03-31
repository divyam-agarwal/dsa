package dp;

import java.util.ArrayList;
import java.util.Arrays;

public class MinJumpsArray {

    // ─── Solution ────────────────────────────────────────────────────────────
    // A[i] = max jump length from index i.
    // Return the minimum number of jumps to reach the last index, or -1 if
    // impossible.
    //
    // Greedy:
    //   Track the farthest index reachable within the current jump window.
    //   When the scan pointer hits the end of the window, consume one jump
    //   and extend the window to farthest.  If farthest never advances past
    //   the current window, the end is unreachable.
    //
    // Time:  O(n)
    // Space: O(1)

    public int jump(ArrayList<Integer> A) {
        int n = A.size();
        if (n <= 1) return 0;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for(int i = 1;i<n;i++){
            for(int j = i-1;j>=0;j--){
                if(dp[j] != Integer.MAX_VALUE && A.get(j) >= i-j){
                    dp[i] = Math.min(dp[i], dp[j]+1);
                }
            }
        }

        return dp[n-1] == Integer.MAX_VALUE ? -1 : dp[n-1];
    }

    // ─── Alternate: Greedy (steps countdown) ─────────────────────────────────
    // maxReach: farthest index reachable so far.
    // steps:    steps remaining in the current jump's reach window.
    // Each index consumed decrements steps; when steps hits 0 we must jump and
    // reset steps to (maxReach - i), the reach gained by the best jump seen so
    // far.  If steps hits 0 and i >= maxReach we are stuck → -1.
    //
    // Time:  O(n)
    // Space: O(1)

    public int jumpGreedy(ArrayList<Integer> A) {
        int n = A.size();

        if (n <= 1) return 0;

        if (A.get(0) == 0) return -1;

        int maxReach = A.get(0);
        int steps = A.get(0);
        int jumps = 1;

        for (int i = 1; i < n; i++) {
            if (i == n - 1) return jumps;

            maxReach = Math.max(maxReach, i + A.get(i));

            steps--;

            if (steps == 0) {
                jumps++;

                if (i >= maxReach) return -1;

                steps = maxReach - i;
            }
        }

        return -1;
    }
}
