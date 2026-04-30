package dp;

public class CountPermutationsOfBST {
    // Count permutations of [1..A] that produce a BST of height exactly B.
    // Height = max edges from root to any leaf. Return answer mod 10^9+7.
    //
    // Approach: interval DP
    //   atMost(n, h) = # permutations of n elements giving BST height <= h
    //   atMost(n, h) = sum_{r=1}^{n} C(n-1, r-1) * atMost(r-1, h-1) * atMost(n-r, h-1)
    //   answer = atMost(A, B) - atMost(A, B-1)
    public int cntPermBST(int N, int H) {
        //nodes - 1, 2, 3, ....N
        // height - -1,0,1, ....H-1
        if(H>=N){
            return 0;
        }

        long[][] dp = new long[N+1][H+2];

        dp[1][1] = 1; // #nodes -1, height - 0
        dp[0][0] = 1; // #nodes - 0, height - -1
        long MODULO = 1_000_000_007;

        for(int nodes = 2;nodes<=N;nodes++){

            for(int height = 1;height<=H;height++){
                for(int i = 1;i<=nodes;i++){
                    //root = i
                    int leftNodes = i-1;
                    int rightNodes = nodes-i;
                    int heightIndex = height+1;
                    long localAns = 0;

                    localAns += dp[leftNodes][heightIndex-1] * dp[rightNodes][heightIndex-1];
                    localAns %= MODULO;

                    for(int lh = 0;lh<heightIndex-1;lh++){
                        localAns += dp[leftNodes][lh] * dp[rightNodes][heightIndex-1];
                    }
                    localAns %= MODULO;

                    for(int rh = 0;rh<heightIndex-1;rh++){
                        localAns += dp[rightNodes][rh] * dp[leftNodes][heightIndex-1];
                    }
                    localAns %= MODULO;

                    localAns = localAns * (choose(leftNodes+rightNodes, leftNodes) % MODULO) % MODULO;

                    dp[nodes][heightIndex] += localAns;
                    dp[nodes][heightIndex] %= MODULO;
                }
            }
        }

        return (int) dp[N][H+1];
    }

    private long choose(int up, int down) {
        down = Math.min(down, up - down);
        long ans = 1;
        for(int i = 0; i < down; i++){
            ans = ans * (up - i) / (i + 1);
        }
        return ans;
    }
}
