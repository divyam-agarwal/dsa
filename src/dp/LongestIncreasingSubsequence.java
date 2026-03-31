package dp;

import java.util.ArrayList;
import java.util.List;

public class LongestIncreasingSubsequence {

    // ─── Solution ─────────────────────────────────────────────────────────────
    // dp[i] = length of the longest strictly increasing subsequence ending at i.
    //
    // Transition:
    //   for each j < i where A[j] < A[i]:
    //     dp[i] = max(dp[i], dp[j] + 1)
    //
    // Answer: max(dp[i]) over all i.
    //
    // Time:  O(n²)
    // Space: O(n)

    public int getJustSmallerIndexBinarySearch(ArrayList<Integer> optimalLIS, int elem){
        int l = 0, r = optimalLIS.size() - 1;
        int res = 0; // sentinel at index 0 is always < any valid element
        while(l <= r){
            int m = l + (r-l)/2;
            if(optimalLIS.get(m) < elem){
                res = m;
                l = m + 1;
            }
            else{
                r = m - 1;
            }
        }
        return res;
    }
    // DO NOT MODIFY THE LIST. IT IS READ ONLY
    public int lis(final List<Integer> A) {
        int n = A.size();
        if (n == 0) return 0;

        ArrayList<Integer> optimalLIS = new ArrayList<>(); // index 1 represents min elem of LIS of length 1
        optimalLIS.add(-1);
        optimalLIS.add(A.get(0));

        for(int i = 1;i<n;i++){
            int lengthOfLisAtI = getJustSmallerIndexBinarySearch(optimalLIS, A.get(i));
            //maxLIS = Math.max(maxLIS, lengthOfLisAtI+1);

            if(lengthOfLisAtI+1 < optimalLIS.size()){
                if(optimalLIS.get(lengthOfLisAtI+1)> A.get(i))
                    optimalLIS.set(lengthOfLisAtI+1 , A.get(i));
            }
            else{
                optimalLIS.add(A.get(i));
            }


        }

        return optimalLIS.size()-1;



    }
}
