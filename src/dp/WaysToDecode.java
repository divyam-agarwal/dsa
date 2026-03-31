package dp;

public class WaysToDecode {

    // ─── Solution ────────────────────────────────────────────────────────────
    // 'A'→1, 'B'→2, …, 'Z'→26.  Count distinct decodings of digit string A.
    //
    // dp[i] = number of ways to decode A[0..i-1].
    //
    // Transitions (mod 10^9+7):
    //   Single digit  A[i-1] != '0'          → dp[i] += dp[i-1]
    //   Two digits    10 <= A[i-2..i-1] <= 26 → dp[i] += dp[i-2]
    //
    // Base cases:
    //   dp[0] = 1  (empty prefix — one way to decode nothing)
    //   dp[1] = 1 if A[0] != '0', else 0
    //
    // Time:  O(n)
    // Space: O(n)

    public int numDecodings(String A) {
        long MODULO = (long)(Math.pow(10,9)) + 7;
        int n = A.length();
        long[] dp = new long[n+1];
        dp[0] = 1;
        dp[1] = A.charAt(0) == '0' ? 0 : 1;

        for(int i = 2;i<=n;i++){
            String s = "" + A.charAt(i-2) + A.charAt(i-1);
            int a = Integer.parseInt(s);
            if(A.charAt(i-2)!='0' && a>9 && a<=26){
                dp[i] += dp[i-2];
            }

            if(A.charAt(i-1)!='0')
                dp[i] += dp[i-1];
            else{
                if(A.charAt(i-2)!='1' && A.charAt(i-2)!='2')
                    return 0;
            }

            dp[i] %= MODULO;
        }

        return (int)dp[n];
    }
}
