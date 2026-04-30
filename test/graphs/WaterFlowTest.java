package graphs;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class WaterFlowTest {

    WaterFlow sol = new WaterFlow();

    private ArrayList<ArrayList<Integer>> grid(int[][] raw) {
        ArrayList<ArrayList<Integer>> A = new ArrayList<>();
        for (int[] row : raw) {
            ArrayList<Integer> r = new ArrayList<>();
            for (int v : row) r.add(v);
            A.add(r);
        }
        return A;
    }

    // ─── InterviewBit example ────────────────────────────────────────────────

    @Test
    void interviewBitExample() {
        // [0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0] → 7 cells
        int[][] raw = {
            {1, 2, 2, 3, 5},
            {3, 2, 3, 4, 4},
            {2, 4, 5, 3, 1},
            {6, 7, 1, 4, 5},
            {5, 1, 1, 2, 4}
        };
        assertEquals(7, sol.solve(grid(raw)));
    }

    // ─── Single cell ─────────────────────────────────────────────────────────

    @Test
    void singleCell() {
        // 1×1 touches both Pacific (top+left) and Atlantic (bottom+right)
        assertEquals(1, sol.solve(grid(new int[][]{{42}})));
    }

    // ─── Flat matrix (all same height) ───────────────────────────────────────

    @Test
    void allSameHeight3x3() {
        // Equal-height neighbours are reachable, so every cell reaches both oceans
        int[][] raw = {
            {3, 3, 3},
            {3, 3, 3},
            {3, 3, 3}
        };
        assertEquals(9, sol.solve(grid(raw)));
    }

    // ─── Mountain peak in centre ─────────────────────────────────────────────

    @Test
    void mountainPeakCentre() {
        // {1,2,1},{2,9,2},{1,2,1}
        // (0,0) can only reach Pacific; (2,2) can only reach Atlantic.
        // All other 7 cells reach both → count = 7
        int[][] raw = {
            {1, 2, 1},
            {2, 9, 2},
            {1, 2, 1}
        };
        assertEquals(7, sol.solve(grid(raw)));
    }

    // ─── Single row ──────────────────────────────────────────────────────────

    @Test
    void singleRow() {
        // n=1: row 0 is both the top (Pacific) and bottom (Atlantic) border,
        // so every cell trivially reaches both oceans.
        int[][] raw = {{5, 4, 3, 2, 1}};
        assertEquals(5, sol.solve(grid(raw)));
    }

    @Test
    void singleRowIncreasing() {
        int[][] raw = {{1, 2, 3, 4, 5}};
        assertEquals(5, sol.solve(grid(raw)));
    }

    // ─── Single column ───────────────────────────────────────────────────────

    @Test
    void singleColumn() {
        // m=1: col 0 is both the left (Pacific) and right (Atlantic) border,
        // so every cell trivially reaches both oceans.
        int[][] raw = {{5}, {4}, {3}, {2}, {1}};
        assertEquals(5, sol.solve(grid(raw)));
    }

    // ─── 2×2 matrix ──────────────────────────────────────────────────────────

    @Test
    void twoByTwo() {
        // {1,2},{4,3}
        // (0,0)=1: Pacific border; all neighbours higher → cannot reach Atlantic. Only Pacific.
        // (0,1)=2: top+right border → both.
        // (1,0)=4: left+bottom border → both.
        // (1,1)=3: Atlantic border; flows to (0,1)=2 → Pacific border. Both.
        // count = 3
        int[][] raw = {
            {1, 2},
            {4, 3}
        };
        assertEquals(3, sol.solve(grid(raw)));
    }

    // ─── Interior pit surrounded by high walls ───────────────────────────────

    @Test
    void interiorPitUnreachable() {
        // All border cells are 9 and reach both oceans.
        // Interior (1,1)=1 cannot flow out (all neighbours are higher) → 0 oceans.
        // count = 8 (all border cells, not the interior pit)
        int[][] raw = {
            {9, 9, 9},
            {9, 1, 9},
            {9, 9, 9}
        };
        assertEquals(8, sol.solve(grid(raw)));
    }

    // ─── All border cells always reach both ──────────────────────────────────

    @Test
    void allBorderCellsCountForLargerMatrix() {
        // A matrix where only border cells can reach both oceans
        // Interior cells are low → cannot flow outward
        int[][] raw = {
            {5, 5, 5, 5},
            {5, 1, 1, 5},
            {5, 1, 1, 5},
            {5, 5, 5, 5}
        };
        // All 12 border cells reach both oceans; 4 interior cells cannot.
        assertEquals(12, sol.solve(grid(raw)));
    }
}
