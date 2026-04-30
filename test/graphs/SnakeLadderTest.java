package graphs;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class SnakeLadderTest {

    SnakeLadder sol = new SnakeLadder();

    private ArrayList<ArrayList<Integer>> pairs(int[]... rows) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        for (int[] row : rows)
            result.add(new ArrayList<>(Arrays.asList(row[0], row[1])));
        return result;
    }

    // ─── No snakes, no ladders ───────────────────────────────────────────────

    @Test
    void noSnakesNoLadders() {
        // Best path: 1 +6*16→ 97 +3→ 100  =  17 rolls
        assertEquals(17, sol.snakeLadder(pairs(), pairs()));
    }

    // ─── Ladders only ────────────────────────────────────────────────────────

    @Test
    void oneLadderWinsInOneRoll() {
        // Ladder 2→100: roll 1 from cell 1, land on 2, take ladder to 100
        assertEquals(1, sol.snakeLadder(pairs(), pairs(new int[]{2, 100})));
    }

    @Test
    void ladderNearEnd() {
        // Ladder 3→98: 1 +2→ 3 -ladder→ 98 +2→ 100  =  2 rolls
        assertEquals(2, sol.snakeLadder(pairs(), pairs(new int[]{3, 98})));
    }

    @Test
    void chainedLadders() {
        // Ladder 2→10, ladder 11→99: 1 +1→ 2 -ladder→ 10 +1→ 11 -ladder→ 99 +1→ 100  =  3 rolls
        assertEquals(3, sol.snakeLadder(pairs(), pairs(new int[]{2, 10}, new int[]{11, 99})));
    }

    // ─── Snakes only ─────────────────────────────────────────────────────────

    @Test
    void snakeOnWinCellIgnored() {
        // Standard rule: landing on cell 100 = win; a snake at 100 is not applied
        assertEquals(17, sol.snakeLadder(pairs(new int[]{100, 2}), pairs()));
    }

    @Test
    void snakeForcesDetour() {
        // Snake 99→1: must reach 100 without landing on 99
        // 1 +6*16→ 97 +3→ 100 (avoids 99)  =  17 rolls
        assertEquals(17, sol.snakeLadder(pairs(new int[]{99, 1}), pairs()));
    }

    // ─── Impossible cases ────────────────────────────────────────────────────

    @Test
    void impossibleWhenAllFirstCellsBlocked() {
        // From cell 1 the only reachable cells in one roll are 2..7.
        // All have snakes back to 1 → stuck in a loop → -1
        ArrayList<ArrayList<Integer>> snakes = pairs(
            new int[]{2, 1}, new int[]{3, 1}, new int[]{4, 1},
            new int[]{5, 1}, new int[]{6, 1}, new int[]{7, 1}
        );
        assertEquals(-1, sol.snakeLadder(snakes, pairs()));
    }

    // ─── Snakes and ladders together ─────────────────────────────────────────

    @Test
    void ladderBypassesSnake() {
        // Snake 10→2; ladder 3→90 lets you skip over the snake
        // 1 +2→ 3 -ladder→ 90 +6→ 96 +4→ 100  =  3 rolls
        assertEquals(3, sol.snakeLadder(pairs(new int[]{10, 2}), pairs(new int[]{3, 90})));
    }

    @Test
    void snakeDoesNotBlockLadderShortcut() {
        // Ladder 2→98, snake 99→1: snake is irrelevant since we jump over it
        // 1 +1→ 2 -ladder→ 98 +2→ 100  =  2 rolls
        assertEquals(2, sol.snakeLadder(pairs(new int[]{99, 1}), pairs(new int[]{2, 98})));
    }

    @Test
    void multipleLaddersAndSnakes() {
        // Ladders: 4→50, 53→74  |  Snakes: 51→3
        // 1 +3→ 4 -ladder→ 50 +3→ 53 -ladder→ 74 +6→ 80 +6→ 86 +6→ 92 +6→ 98 +2→ 100  =  7 rolls
        assertEquals(7, sol.snakeLadder(
            pairs(new int[]{51, 3}),
            pairs(new int[]{4, 50}, new int[]{53, 74})
        ));
    }

    // ─── Edge cases ──────────────────────────────────────────────────────────

    @Test
    void directRollToHundred() {
        // Ladder 2→94: 1 +1→ 2 -ladder→ 94 +6→ 100  =  2 rolls
        assertEquals(2, sol.snakeLadder(pairs(), pairs(new int[]{2, 94})));
    }
}
