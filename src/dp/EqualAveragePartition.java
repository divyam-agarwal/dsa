package dp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class EqualAveragePartition {
    // Given a sorted array A of non-negative integers, partition it into two
    // non-empty subsets S1 and S2 such that avg(S1) == avg(S2).
    // Return [S1, S2] where S1 is the lexicographically smallest valid subset.
    // Return [[], []] if no such partition exists.
    /*
    * Steps
    * 1. sort A
    * 2. dp[i][j][count] = true/false for elements considered till i, sum is j and # of elements in subset is count
    * 3. dp[i][j][count] = dp[i-1][j-A[i]][count-1]; dp[i][j][count] = dp[i-1][j][count]; base - dp[0][0][0] = 1
    * 4. check for every true if j/count ==N && j%(count*N)==0, if found retrace till count!=0 and reverse the array
    *
    * */
    public ArrayList<ArrayList<Integer>> solve(ArrayList<Integer> A) {
        A.sort(Integer::compareTo);

        int n = A.size();
        int totalSumA = A.stream().reduce(0, (a,b)-> a+b);
        int averageA = totalSumA/n;

        boolean[][][] dp = new boolean[n][totalSumA+1][n+1];
        for(int i = 0;i<n;i++){
            dp[i][0][0] = true;
        }
        dp[0][A.get(0)][1] = true;

        ArrayList<Integer> retracePath = new ArrayList<>();
        ArrayList<Integer> secondPath = new ArrayList<>();
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        boolean found = false;

        for(int i = 1;i<n && !found;i++){
            for(int sum = 0;sum<=totalSumA/2 && !found;sum++){
                for(int count = 1;count<=n && !found;count++){
                    if(sum==0 && count==0){
                        // do nothing
                    }
                    else{
                        if(i-1>=0){
                            dp[i][sum][count] |= dp[i-1][sum][count];
                            if(sum-A.get(i)>=0 && count-1>=0){
                                dp[i][sum][count] |= dp[i-1][sum-A.get(i)][count-1];
                            }
                        }
                        //check for happy path and retrace if yes
                        if(dp[i][sum][count]){
                            if(sum== (totalSumA*count)/A.size() && (totalSumA*count)%A.size()==0){
                                //match found, now retrace
                                retrace(dp, A, retracePath, i, sum, count);
                                getSecondSubset(secondPath,retracePath, A);
                                found = true;
                            }
                        }

                    }
                }
            }
        }
        if(retracePath.size()!=0 && secondPath.size()!=0){
            ans.add(retracePath);
            ans.add(secondPath);
        }
        return ans;


    }

    private void getSecondSubset(ArrayList<Integer> secondPath, ArrayList<Integer> retracePath, ArrayList<Integer> A) {
        int k = 0;
        for (Integer integer : A) {
            if (k < retracePath.size() && integer.equals(retracePath.get(k))) {
                k++;
            } else {
                secondPath.add(integer);
            }
        }
    }

    private void retrace(boolean[][][]dp, ArrayList<Integer> A, ArrayList<Integer> retracePath, int i, int sum, int count) {

        while(count>=0 && i>=0){
            if(i-1>=0 && (sum-A.get(i))>=0 && count-1>=0 && dp[i-1][sum-A.get(i)][count-1]){
                retracePath.add(A.get(i));
                sum-=A.get(i);
                count-=1;
            }
            i-=1;
        }
        Collections.reverse(retracePath);
    }
}
