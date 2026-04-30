package graphs;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class ValidPathTest {

    ValidPath sol = new ValidPath();

    private ArrayList<Integer> list(Integer... vals) {
        return new ArrayList<>(Arrays.asList(vals));
    }

    // ─── InterviewBit example ────────────────────────────────────────────────

    @Test
    void interviewBitExample() {
        // 3×4 grid (cells 0..2 × 0..3), one circle of radius 1 at the destination (2,3)
        assertEquals("NO", sol.solve(2, 3, 1, 1, list(2), list(3)));
    }

    // ─── No circles ──────────────────────────────────────────────────────────

    @Test
    void noCirclesSmallGrid() {
        assertEquals("YES", sol.solve(5, 5, 0, 0, list(), list()));
    }

    @Test
    void noCirclesOriginEqualsDestination() {
        // Start == end == (0,0), nothing blocking
        assertEquals("YES", sol.solve(0, 0, 0, 0, list(), list()));
    }

    @Test
    void noCirclesSingleRow() {
        // Flat 5×0 strip — straight line to destination
        assertEquals("YES", sol.solve(5, 0, 0, 0, list(), list()));
    }

    // ─── Start or destination blocked ────────────────────────────────────────

    @Test
    void startBlocked() {
        // Circle of radius 1 centred at origin — (0,0) itself has dist 0 ≤ 1
        assertEquals("NO", sol.solve(3, 3, 1, 1, list(0), list(0)));
    }

    @Test
    void destinationBlocked() {
        // Circle of radius 1 centred at (3,3) — destination dist 0 ≤ 1
        assertEquals("NO", sol.solve(3, 3, 1, 1, list(3), list(3)));
    }

    // ─── Single circle, path still exists ────────────────────────────────────

    @Test
    void singleCircleInCentrePathAlongEdges() {
        // 7×7 grid (cells 0..6 × 0..6), circle R=2 at (3,3); bottom+right edge is fully clear
        // e.g. (0,0)→(6,0)→(6,6) stays at distance ≥ 3 from (3,3)
        assertEquals("YES", sol.solve(6, 6, 1, 2, list(3), list(3)));
    }

    @Test
    void singleCircleInCentreOfSmallGrid() {
        // 5×5 grid (cells 0..4 × 0..4), circle R=1 at (2,2) — bottom-right perimeter is clear
        // path (0,0)→…→(4,0)→(4,1)→…→(4,4): all edge cells are > 1 from (2,2)
        assertEquals("YES", sol.solve(4, 4, 1, 1, list(2), list(2)));
    }

    // ─── Single circle blocks path (no way around) ───────────────────────────

    @Test
    void singleCircleBlocksAll3NeighboursOfOrigin() {
        // 3×3 grid (cells 0..2 × 0..2), circle R=1 at (1,1)
        // (1,0), (0,1), (1,1) are all at distance ≤ 1 from centre → (0,0) is isolated
        assertEquals("NO", sol.solve(2, 2, 1, 1, list(1), list(1)));
    }

    @Test
    void singleCircleBlocksSingleRowMidway() {
        // Flat 5×0 strip, circle R=1 at (2,0) → (1,0),(2,0),(3,0) all blocked
        assertEquals("NO", sol.solve(5, 0, 1, 1, list(2), list(0)));
    }

    // ─── Two circles ─────────────────────────────────────────────────────────

    @Test
    void twoDiagonalCirclesPathBetweenThem() {
        // 5×5 grid (cells 0..4 × 0..4), circles R=1 at (1,3) and (3,1)
        // Diagonal path (0,0)→(1,1)→(2,2)→(3,3)→(4,4) clears both circles
        // each step is distance √2 ≈ 1.41 from the nearer centre
        assertEquals("YES", sol.solve(4, 4, 2, 1, list(1, 3), list(3, 1)));
    }

    // ─── Wall of circles ─────────────────────────────────────────────────────

    @Test
    void horizontalWallOfThreeCircles() {
        // 5×5 grid (cells 0..4 × 0..4), circles R=1 at (0,2),(2,2),(4,2)
        // Together they block every cell in rows y=1..3 across the full width,
        // cutting the rectangle in two halves — no path from (0,0) to (4,4)
        assertEquals("NO", sol.solve(4, 4, 3, 1, list(0, 2, 4), list(2, 2, 2)));
    }

    @Test
    void verticalWallBlocksNarrowRectangle() {
        // 3×5 grid (cells 0..2 × 0..4), circles R=1 at (1,1) and (1,3) — vertical wall at x=1
        // (0,0) and (2,4) are clear (dist √2 > 1 from nearest centre)
        // but every crossing cell at x=1 is blocked, and diagonals from x=0
        // to x=2 all pass within distance 1 of a centre → "NO"
        assertEquals("NO", sol.solve(2, 4, 2, 1, list(1, 1), list(1, 3)));
    }
}
