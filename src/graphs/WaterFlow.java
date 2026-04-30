package graphs;

import java.util.ArrayList;
import java.util.List;

// InterviewBit: Water Flow
// https://www.interviewbit.com/problems/water-flow/
//
// Given an N×M matrix where each cell represents terrain height, water can flow
// from a cell to an adjacent cell (up/down/left/right) only if the neighbour's
// height is <= the current cell's height.
//
// Pacific Ocean touches the top and left borders.
// Atlantic Ocean touches the bottom and right borders.
//
// Return all [row, col] cells from which water can reach BOTH oceans.
// Return the list sorted by row, then by column.
//
// Constraints:
//   1 <= N, M <= 150
//   0 <= A[i][j] <= 10^5

public class WaterFlow {

    public int solve(ArrayList<ArrayList<Integer>> A) {
        int n = A.size();
        int m = A.get(0).size();
        boolean[][] canReachBlue = new boolean[n][m];
        boolean[][] canReachRed = new boolean[n][m];

        for(int i = 0;i<n;i++){
            canReachBlue[i][0] = true;
            canReachRed[i][m-1] = true;
        }

        for(int i = 0;i<m;i++){
            canReachBlue[0][i] = true;
            canReachRed[n-1][i] = true;
        }


        for(int i = 0;i<n;i++){
            boolean[][] visitedBlue = new boolean[n][m];
            dfs(i,0,visitedBlue, canReachBlue, A);

            boolean[][] visitedRed = new boolean[n][m];
            dfs(i,m-1, visitedRed, canReachRed, A);
        }
        for(int i = 0;i<m;i++){
            boolean[][] visitedBlue = new boolean[n][m];
            dfs(0,i,visitedBlue, canReachBlue, A);

            boolean[][] visitedRed = new boolean[n][m];
            dfs(n-1,i,visitedRed, canReachRed, A);
        }

        int count = 0;
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                if(canReachRed[i][j] && canReachBlue[i][j])
                    count++;
            }
        }

        return count;
    }

    private void dfs(int i, int j, boolean[][] visited, boolean[][] canReach, ArrayList<ArrayList<Integer>> A) {
        int n = canReach.length;
        int m = canReach[0].length;
        visited[i][j] = true;
        canReach[i][j] = true;

        int[] X = {0,0,1,-1};
        int[] Y = {1,-1,0,0};

        for(int k = 0;k<4;k++){
            int x1 = i+X[k];
            int y1 = j+Y[k];
            if(isValidSquare(x1,y1,n,m) && !visited[x1][y1] && !canReach[x1][y1]){
                if(A.get(i).get(j) <= A.get(x1).get(y1)){
                    dfs(x1,y1, visited, canReach, A);
                }
            }
        }
    }

    private boolean isValidSquare(int x1, int y1, int n, int m) {
        return x1>=0 && x1<n && y1>=0 && y1<m;
    }

}
