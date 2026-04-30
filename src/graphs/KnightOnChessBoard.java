package graphs;

// InterviewBit: Knight On Chess Board
// https://www.interviewbit.com/problems/knight-on-chess-board/
//
// Given an N×M chessboard with a knight at position (x, y) (1-indexed),
// find the minimum number of moves for the knight to reach position (p, q).
//
// A knight moves in an "L" shape: 2 squares in one direction, 1 square
// perpendicular (or vice versa) — up to 8 possible moves per position.
//
// Input:  N (rows), M (cols), x (start row), y (start col), p (end row), q (end col)
// Output: minimum number of moves, or -1 if unreachable

import java.util.*;

public class KnightOnChessBoard {

    int MAX_MOVES = 1_000_000_000;


    private static class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public int knight(int N, int M, int sx, int sy, int dx, int dy) {
        int[][] dist = new int[N+1][M+1];
        for(int i = 1;i<=N;i++){
            for(int j = 1;j<=M;j++){
                dist[i][j] = Integer.MAX_VALUE;
            }
        }
        boolean[][] visited = new boolean[N+1][M+1];
        Deque<Coordinate> dq = new ArrayDeque<>();
        int moves = 0;
        dq.add(new Coordinate(sx,sy));
        visited[sx][sy] = true;

        while(!dq.isEmpty() && moves < MAX_MOVES){
            int levelSize = dq.size();
            for(int i =0;i<levelSize;i++){
                Coordinate poppedCoordinate = dq.pollLast();
                dist[poppedCoordinate.x][poppedCoordinate.y] = Math.min(dist[poppedCoordinate.x][poppedCoordinate.y], moves);
                if(dist[dx][dy]< Integer.MAX_VALUE){
                    return dist[dx][dy];
                }
                int[] X = {2, 2, -2, -2, 1, -1, 1, -1};
                int[] Y = {1, -1, 1, -1, 2, 2, -2, -2};

                for(int j = 0;j<8;j++){
                    int nextX = poppedCoordinate.x + X[j];
                    int nextY = poppedCoordinate.y + Y[j];

                    if(isValidSquare(nextX, nextY, N, M) && !visited[nextX][nextY]){
                        if(nextX==dx && nextY==dy){
                            return moves+1;
                        }
                        visited[nextX][nextY] = true;
                        dq.addFirst(new Coordinate(nextX, nextY));
                    }
                }

            }
            moves++;
        }
        return -1;

    }

    private boolean isValidSquare(int nextX, int nextY, int n, int m) {
        return nextX>=1 && nextX<=n && nextY>=1 && nextY<=m;
    }
}
