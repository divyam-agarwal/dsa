package dp;

import java.util.ArrayList;

public class RegularExpressionII {

    // ─── Solution ────────────────────────────────────────────────────────────
    // '.' matches any single character.
    // '*' matches zero or more of the preceding element.
    //
    // dp[i][j] = true if A[0..i-1] matches pattern B[0..j-1].
    //
    // Transitions:
    //   B[j-1] == A[i-1] or B[j-1] == '.'  →  dp[i][j] = dp[i-1][j-1]
    //   B[j-1] == '*':
    //     zero occurrences of preceding:    dp[i][j] |= dp[i][j-2]
    //     one+ occurrences (if B[j-2] matches A[i-1]):
    //                                       dp[i][j] |= dp[i-1][j]
    //
    // Base cases:
    //   dp[0][0] = true
    //   dp[0][j] = true only if pattern B[0..j-1] can match empty string
    //              (e.g. "a*", "a*b*", ".*")
    //
    // Time:  O(m * n)
    // Space: O(m * n)

    public ArrayList<String> combineString(String s){
        ArrayList<String> list = new ArrayList<>();
        int n = s.length();
        int i = 0;
        while(i<n){
            if(i+1<n && s.charAt(i+1)=='*'){
                list.add(s.charAt(i) + "*");
                i+=2;
            }
            else{
                list.add(String.valueOf(s.charAt(i)));
                i++;
            }
        }
        return list;
    }
    // DO NOT MODIFY THE LIST. IT IS READ ONLY
    public int isMatch(final String A, final String B) {
        ArrayList<String> b = combineString(B);
        //ArrayList<String> a = combineString(A);

        int n = A.length();
        int m = b.size();

        int[][] dp = new int[n+1][m+1];

        dp[0][0] =1;
        for(int j = 1; j<=m; j++){
            if(b.get(j-1).length()==2){
                dp[0][j] = 1;
            }
            else{
                break;
            }
        }

        for(int i = 1;i<=n;i++){
            for(int j =1;j<=m;j++){
                if(b.get(j-1).length()==1){
                    if(b.get(j-1).equals(".")){
                        dp[i][j] = dp[i-1][j-1];
                    }
                    else{
                        if(A.charAt(i-1)==b.get(j-1).charAt(0)){
                            dp[i][j] = dp[i-1][j-1];
                        }
                    }
                }
                else{
                    //length = 2
                    char charToCompare = b.get(j-1).charAt(0);
                    if(charToCompare=='.'){
                        dp[i][j] = dp[i-1][j-1] | dp[i-1][j] | dp[i][j-1];
                    }
                    else{
                        dp[i][j] = dp[i][j-1];  // zero occurrences — always valid
                        if(charToCompare==A.charAt(i-1)){
                            dp[i][j] |= dp[i-1][j];  // one or more occurrences
                        }
                    }
                }
            }
        }
        return dp[n][m];

    }
}
