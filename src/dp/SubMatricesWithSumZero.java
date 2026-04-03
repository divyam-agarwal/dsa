package dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SubMatricesWithSumZero {
    // Given a 2D matrix A of size N x M, count the number of submatrices
    // whose elements sum to zero.
    /*
    * Steps
    * 1. create suffix[i][j] for storing row sum of row i from 0-jth column
    * 2. fix c1,c2 for each row and get rowSum(i, c1,c2) = suffix[i][c2]- suffix[i][c1-1]
    * 3. store each rowSum(i,c1,c2) for each i in 1d array.
    *   3.1 now find sum 0 of subarray in O(n)- (make prefix array, and check if prefix present in hashmap while iteration)
    *
    * */
    public int solve(ArrayList<ArrayList<Integer>> A) {
        int n = A.size();
        int m = A.get(0).size();

        int[][] suffix = new int[n][m];

        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(j==0)
                    suffix[i][j] = A.get(i).get(j);
                else{
                    suffix[i][j] = suffix[i][j-1] + A.get(i).get(j);
                }
            }
        }
        int ans = 0;
        for(int c1 = 0;c1<m;c1++){
            for(int c2 = c1;c2<m;c2++){
                int[] temp = new int[n];
                for(int k = 0;k<n;k++){
                    if(c1-1>=0)
                        temp[k] = suffix[k][c2]- suffix[k][c1-1];
                    else
                        temp[k] = suffix[k][c2];
                }
                ans += findZeroSum(temp);
            }
        }
        return ans;
    }

    private int findZeroSum(int[] temp) {
        HashMap<Integer, Integer> mp = new HashMap<>();
        int n = temp.length;
        mp.put(0,1);
        int currSum = 0, zeroSum = 0;
        for (int j : temp) {
            currSum += j;
            if (mp.containsKey(currSum)) {
                zeroSum += mp.get(currSum);
                mp.computeIfPresent(currSum, (k,v)-> v+1);
            } else {
                mp.putIfAbsent(currSum, 1);
            }
        }
        return zeroSum;
    }
}
