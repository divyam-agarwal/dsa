package dp;

import java.util.ArrayList;
import java.util.List;

public class WordBreakII {
    // Given string A and dictionary B, return all ways to segment A into
    // valid dictionary words, as space-separated sentences sorted lexicographically.
//    Input 1:
//    A = "catsanddog",
//    B = ["cat", "cats", "and", "sand", "dog"]
//
//    Output 1:
//            ["cat sand dog", "cats and dog"]
    /*
    * dp[i][j] = String[] which contains all valid possibilities from i to j
    * dp[i][j] = for all m combine(dp[i][m], dp[m+1][j]) (n*m Strings as result)
    * base case -> for each i, populate using isSubstring(A, B[i])
    *
    * */
    public List<List<Integer>> getSubstringIndexes(String A, String pattern){
        //return -1,-1 if not found
        List<List<Integer>> indices = new ArrayList<>();
        int[] lsp = getLSP(pattern);
        int m = pattern.length();
        int n = A.length();
        int j = 0;
        int i = 0;
        while(i<n){
            if(A.charAt(i)==pattern.charAt(j)){
                j++;
                if(j==m){
                    indices.add(List.of(i-m+1,i));
                    j= lsp[m-1];
                }
                i++;
            }
            else{
                //no match
                if(j!=0)
                j = lsp[j-1];
                else{
                    i++;
                }
            }
        }
        return indices;
    }

    private int[] getLSP(String pattern) {
        int n = pattern.length();
        int[] lsp = new int[n];
        for(int i = 0;i<n;i++){
            //fill lsp[i]
            if(i!=0){
                for(int len = i;len>0;len--){
                    if((pattern.substring(0, len)).equals(pattern.substring(i+1 -(len),i+1))){
                        lsp[i] = len;
                        break;
                    }
                }
            }
        }
        return lsp;
    }


    public List<String> wordBreak(String A, ArrayList<String> B) {
        int n = A.length();
        List<String>[][] dp = new ArrayList[n][n];

        for(int i = 0;i<n;i++){
            for(int j = 0;j<n;j++){
                dp[i][j] = new ArrayList<>();
            }
        }

        //base case
        for (String pattern : B) {
            List<List<Integer>> indices = getSubstringIndexes(A, pattern);
            for (List<Integer> index : indices) {
                dp[index.get(0)][index.get(1)].add(pattern);
            }
        }

        for(int k = 1;k<n;k++){
            int i = 0, j = k;
            while(i<n && j<n){
                for(int m = i;m<j;m++){
                    addStrings(dp[i][j], combineIntermediates(dp[i][m], dp[m+1][j]));
                }
                i++;j++;
            }
        }

        return dp[0][n-1];
    }

    private void addStrings(List<String> dpIJ, List<String> strings1) {
        dpIJ.addAll(strings1);
    }

    private List<String> combineIntermediates(List<String> strings1, List<String> strings2) {
        if(strings1.isEmpty() || strings2.isEmpty())
            return new ArrayList<>();
        List<String> ans = new ArrayList<>();
        for (String s : strings1) {
            for (String string : strings2) {
                ans.add(s + " " + string);
            }
        }
        return ans;
    }
}
