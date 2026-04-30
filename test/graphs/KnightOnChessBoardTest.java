package graphs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KnightOnChessBoardTest {

    KnightOnChessBoard sol = new KnightOnChessBoard();

    // ─── Already at destination ──────────────────────────────────────────────

    @Test
    void startEqualsEnd() {
        // No moves needed when start == end
        assertEquals(0, sol.knight(8, 8, 1, 1, 1, 1));
    }

    // ─── Standard 8×8 board ──────────────────────────────────────────────────

    @Test
    void standardOneMove() {
        // From (1,1) a knight can reach (2,3) in exactly 1 move
        assertEquals(1, sol.knight(8, 8, 1, 1, 2, 3));
    }

    @Test
    void standardTwoMoves() {
        // From (1,1) to (1,2) requires 4 moves on a standard board
        assertEquals(4, sol.knight(8, 8, 1, 1, 1, 2));
    }

    @Test
    void standardCornerToCorner() {
        // (1,1) → (8,8) on an 8×8 board: 6 moves
        assertEquals(6, sol.knight(8, 8, 1, 1, 8, 8));
    }

    @Test
    void standardMidBoard() {
        // (4,4) → (4,5) on 8×8
        assertEquals(3, sol.knight(8, 8, 4, 4, 4, 5));
    }

    // ─── Small / constrained boards ──────────────────────────────────────────

    @Test
    void singleCell() {
        // 1×1 board: start == end, 0 moves
        assertEquals(0, sol.knight(1, 1, 1, 1, 1, 1));
    }

    @Test
    void twoByThreeBoard() {
        // On a 2×3 board a knight at (1,1) can reach (2,3) in 3 moves
        assertEquals(3, sol.knight(2, 3, 1, 1, 2, 3));
    }

    @Test
    void unreachableOnNarrowBoard() {
        // On a 2×2 board a knight can never move — any destination other than
        // start is unreachable
        assertEquals(-1, sol.knight(2, 2, 1, 1, 2, 2));
    }

    @Test
    void threeByThreeCornerToOppositeCorner() {
        // (1,1) → (3,3) on a 3×3 board
        assertEquals(4, sol.knight(3, 3, 1, 1, 3, 3));
    }

    // ─── Non-square boards ───────────────────────────────────────────────────

    @Test
    void tallBoard() {
        // 10×3 board: (1,1) → (10,3)
        assertEquals(5, sol.knight(10, 3, 1, 1, 10, 3));
    }

    @Test
    void wideBoard() {
        // 3×10 board: (1,1) → (3,10)
        assertEquals(5, sol.knight(3, 10, 1, 1, 3, 10));
    }
}
