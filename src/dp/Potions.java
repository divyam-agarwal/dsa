package dp;

public class Potions {
    // Interval DP: mix N adjacent potions to minimize total smoke.
    // Mixing colors X and Y: smoke = X*Y, result color = (X+Y) % 100.
    public static class ColorSmokePair{
        int color;//0-99
        int minSmoke;

        public ColorSmokePair(int color, int minSmoke) {
            this.color = color;
            this.minSmoke = minSmoke;
        }
    }
    public long solve(int[] A) {
        int n = A.length;
        ColorSmokePair[][] dp = new ColorSmokePair[n][n];

        //base case
        for(int i = 0;i<n;i++){
            dp[i][i] = new ColorSmokePair(A[i],0);
        }

        for(int k = 1;k<n;k++){
            int i = 0, j = k;
            while(i<n && j<n){
                dp[i][j] = new ColorSmokePair(-1,Integer.MAX_VALUE);
                for(int m = i;m<j;m++){
                    //i->m, m+1->j
                    ColorSmokePair temp = combine(dp[i][m], dp[m+1][j]);
                    dp[i][j] = (dp[i][j].minSmoke > temp.minSmoke ? temp:dp[i][j]);
                }
                i++;
                j++;
            }
        }
        return dp[0][n-1].minSmoke;
    }

    private ColorSmokePair combine(ColorSmokePair p1, ColorSmokePair p2) {
        int newColor = (p1.color + p2.color)%100;
        int newSmoke = p1.minSmoke + p2.minSmoke + (p1.color*p2.color);
        return new ColorSmokePair(newColor, newSmoke);
    }
}
