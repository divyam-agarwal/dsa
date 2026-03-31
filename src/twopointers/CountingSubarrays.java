package twopointers;

import java.util.ArrayList;

public class CountingSubarrays {
    public int solve(ArrayList<Integer> A, int B) {
        int l = 0;
        int count = 0;
        int currSum = 0;
        for(int r = 0; r < A.size(); r++){
            currSum += A.get(r);
            while(currSum >= B){
                currSum -= A.get(l);
                l++;
            }
            // subarrays [l..r], [l+1..r], ..., [r..r] all have sum < B
            count += (r - l + 1);
        }
        return count;
    }
}
