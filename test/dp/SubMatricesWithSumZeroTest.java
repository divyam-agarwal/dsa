package dp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubMatricesWithSumZeroTest {
    SubMatricesWithSumZero sol = new SubMatricesWithSumZero();

    private ArrayList<ArrayList<Integer>> matrix(Integer[]... rows) {
        ArrayList<ArrayList<Integer>> A = new ArrayList<>();
        for (Integer[] row : rows) {
            A.add(new ArrayList<>(Arrays.asList(row)));
        }
        return A;
    }

    @Test
    void interviewBitExample() {
        // [[1, -1], [-1, 1]] → 5 submatrices sum to 0:
        //   row 0 cols 0-1, row 1 cols 0-1, col 0 rows 0-1, col 1 rows 0-1, full matrix
        ArrayList<ArrayList<Integer>> A = matrix(
            new Integer[]{1, -1},
            new Integer[]{-1, 1}
        );
        assertEquals(5, sol.solve(A));
    }

    @Test
    void singleZero() {
        assertEquals(1, sol.solve(matrix(new Integer[]{0})));
    }

    @Test
    void singleNonZero() {
        assertEquals(0, sol.solve(matrix(new Integer[]{5})));
    }

    @Test
    void allZeros() {
        // 2x2 all-zero: 4 single cells + 2 full rows + 2 full cols + 1 full matrix = 9
        ArrayList<ArrayList<Integer>> A = matrix(
            new Integer[]{0, 0},
            new Integer[]{0, 0}
        );
        assertEquals(9, sol.solve(A));
    }

    @Test
    void noZeroSubmatrix() {
        ArrayList<ArrayList<Integer>> A = matrix(
            new Integer[]{1, 2},
            new Integer[]{3, 4}
        );
        assertEquals(0, sol.solve(A));
    }

    @Test
    void rowCanceelledOut() {
        // Rows 0 and 1 are negatives of each other; submatrix spanning both cols i..j
        // always sums to 0. That's C(3,2)=3 such col ranges × 1 row-pair = 3.
        // Plus any single-cell or single-row subarray within row 0 or row 1 that is 0 — none here.
        ArrayList<ArrayList<Integer>> A = matrix(
            new Integer[]{1, 2, 3},
            new Integer[]{-1, -2, -3}
        );
        assertEquals(6, sol.solve(A));
    }
}
