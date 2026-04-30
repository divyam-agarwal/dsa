package graphs;

import java.util.ArrayList;

// InterviewBit: Word Search Board
// https://www.interviewbit.com/problems/word-search-board/
//
// Given a 2D board of characters A and a string B, return 1 if B exists in the
// board, 0 otherwise. The word must be constructed from letters of sequentially
// adjacent cells (horizontally or vertically neighboring). The same cell may
// not be used more than once.
//
// Constraints:
//   1 <= rows, cols <= 500
//   1 <= |B| <= rows * cols

public class WordSearchBoard {

    public int exist(ArrayList<String> A, String B) {
        int n = A.size();
        int m = A.get(0).length();
        boolean found = false;
        for(int i = 0;i<n;i++){
            for(int j = 0;j<m;j++){
                ArrayList<Character> currentString = new ArrayList<>();
                found |= dfs(i,j,currentString, A, B);
                if(found)
                    return 1;
            }
        }
        return 0;
    }

    private boolean dfs(int i, int j, ArrayList<Character> currentString, ArrayList<String> A, String B) {
        if(A.get(i).charAt(j) ==B.charAt(currentString.size())){
            currentString.add(A.get(i).charAt(j));
            if(currentString.size()== B.length())
                return true;

            int[] X = {0, 1, 0, -1};
            int[] Y = {1, 0, -1, 0};

            boolean isFound = false;
            for(int neighbour = 0;neighbour<4;neighbour++){
                int x1 = i+X[neighbour];
                int y1 = j+Y[neighbour];
                if(isValidSquare(x1, y1, A)){
                    isFound |= dfs(x1, y1, currentString, A, B);
                    if(isFound)
                        return true;
                }
            }
            currentString.removeLast();
        }
        return false;
    }

    private boolean isValidSquare(int x1, int y1, ArrayList<String> a) {
        int n = a.size();
        int m = a.get(0).length();

        return x1>=0 && x1<n && y1>=0 && y1<m;
    }
}
