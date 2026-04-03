package dp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxRectangleInBinaryMatrixTest {
    MaxRectangleInBinaryMatrix sol = new MaxRectangleInBinaryMatrix();

    private ArrayList<ArrayList<Integer>> matrix(Integer[]... rows) {
        ArrayList<ArrayList<Integer>> A = new ArrayList<>();
        for (Integer[] row : rows) {
            A.add(new ArrayList<>(Arrays.asList(row)));
        }
        return A;
    }

    @Test
    void interviewBitExample() {
        // 4x5 matrix — largest rectangle of 1s has area 6
        ArrayList<ArrayList<Integer>> A = matrix(
            new Integer[]{1, 0, 1, 0, 0},
            new Integer[]{1, 0, 1, 1, 1},
            new Integer[]{1, 1, 1, 1, 1},
            new Integer[]{1, 0, 0, 1, 0}
        );
        assertEquals(6, sol.solve(A));
    }

    @Test
    void allOnes() {
        ArrayList<ArrayList<Integer>> A = matrix(
            new Integer[]{1, 1},
            new Integer[]{1, 1}
        );
        assertEquals(4, sol.solve(A));
    }

    @Test
    void allZeros() {
        ArrayList<ArrayList<Integer>> A = matrix(
            new Integer[]{0, 0},
            new Integer[]{0, 0}
        );
        assertEquals(0, sol.solve(A));
    }

    @Test
    void singleOne() {
        ArrayList<ArrayList<Integer>> A = matrix(new Integer[]{1});
        assertEquals(1, sol.solve(A));
    }

    @Test
    void singleZero() {
        ArrayList<ArrayList<Integer>> A = matrix(new Integer[]{0});
        assertEquals(0, sol.solve(A));
    }

    @Test
    void singleRow() {
        // [1, 1, 0, 1, 1, 1] — longest run of 1s is 3
        ArrayList<ArrayList<Integer>> A = matrix(
            new Integer[]{1, 1, 0, 1, 1, 1}
        );
        assertEquals(3, sol.solve(A));
    }

    @Test
    void singleColumn() {
        ArrayList<ArrayList<Integer>> A = matrix(
            new Integer[]{1},
            new Integer[]{1},
            new Integer[]{0},
            new Integer[]{1},
            new Integer[]{1},
            new Integer[]{1}
        );
        assertEquals(3, sol.solve(A));
    }

    @Test
    void rectangleSpanningMultipleRows() {
        // Heights per column after row 1: [1,2,2] → max rectangle = 4
        ArrayList<ArrayList<Integer>> A = matrix(
            new Integer[]{0, 1, 1},
            new Integer[]{1, 1, 1}
        );
        assertEquals(4, sol.solve(A));
    }
}
