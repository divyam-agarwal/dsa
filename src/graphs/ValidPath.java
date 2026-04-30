package graphs;

import java.util.ArrayList;

// InterviewBit: Valid Path
// https://www.interviewbit.com/problems/valid-path/
//
// Given a rectangle with bottom-left corner at (0,0) and top-right corner at (A,B),
// and N circles each with radius D centred at (E[i], F[i]),
// return "YES" if there exists a path from (0,0) to (A,B) without touching any circle,
// or "NO" otherwise.
//
// Movement is 8-directional across integer grid cells.
// A cell is blocked if its Euclidean distance to any circle centre is <= D.
//
// Input:
//   A - x-coordinate of destination
//   B - y-coordinate of destination
//   C - number of circles
//   D - radius of every circle
//   E - x-coordinates of circle centres
//   F - y-coordinates of circle centres
// Output: "YES" or "NO"

public class ValidPath {

    public String solve(int X, int Y, int numberOfCircles, int radius, ArrayList<Integer> XCircle, ArrayList<Integer> YCircle) {
        int[][] grid = new int[X+1][Y+1];//0 is fine, 1 is obstacle
        
        for(int i = 0;i<numberOfCircles;i++){
            updateObstacles(grid, XCircle.get(i),YCircle.get(i), radius, XCircle.get(i), YCircle.get(i));
        }
        boolean[][] visited = new boolean[X+1][Y+1];
        boolean b = dfs(0,0,grid, visited);

        if(b)
            return "YES";
        else
            return "NO";
    }

    private boolean dfs(int x, int y, int[][] grid, boolean[][] visited) {
        if(x== grid.length-1 && y==grid[0].length-1)
            return true;
        visited[x][y] = true;
        int[] X = {1, 1, 1, 0, 0, -1, -1, -1};
        int[] Y = {0, 1,-1, 1,-1,  1,  0, -1};

        for(int k = 0;k<8;k++){
            int x1=x+X[k];
            int y1=y+Y[k];

            if(isValidSquare(x1,y1,grid) && !visited[x1][y1] && grid[x1][y1]!=1){
                if(dfs(x1,y1,grid,visited)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidSquare(int x1, int y1, int[][] grid) {
        return (x1>=0 && x1<grid.length && y1>=0 && y1<grid[0].length);
    }

    private void updateObstacles(int[][] grid, int x, int y, int radius, Integer cx, Integer cy) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        boolean b = isNotInsideCircle(0,0,radius, cx, cy, grid);
        dfsUpdateOnes(grid, x, y, radius, cx, cy, visited, b);
    }

    private boolean isNotInsideCircle(int x, int y, int radius, Integer cx, Integer cy, int[][] grid) {
        return ((cx-x)*(cx-x) + (cy-y)*(cy-y)) >= radius*radius;
    }

    private void dfsUpdateOnes(int[][] grid,int x, int y, int radius, Integer cx, Integer cy, boolean[][] visited, boolean notInside) {
        int n = grid.length;
        int m = grid[0].length;

        visited[x][y] = true;
        boolean b = isNotInsideCircle(x,y, radius, cx,cy,grid);
        if(b^notInside){
            grid[x][y] = 1;
        }
        int[] X = {1, 1, 1, 0, 0, -1, -1, -1};
        int[] Y = {0, 1,-1, 1,-1,  1,  0, -1};
        for(int i = 0;i<8;i++){
            int x1=x+X[i];
            int y1=y+Y[i];
            if(isValidSquare(x1,y1,grid) && !visited[x1][y1]){
                dfsUpdateOnes(grid, x1,y1, radius, cx, cy, visited, notInside);
            }
        }

    }
}
