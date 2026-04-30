package dp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TusharsBirthdayPartyTest {
    TusharsBirthdayParty sol = new TusharsBirthdayParty();

    // A  = friend capacities
    // B  = capacity (portions) of each dish   ← B[j] is portions
    // C  = cost of each dish                  ← C[j] is cost
    // Always include a dish with B[j]=1 so every capacity is reachable.

    private ArrayList<Integer> list(Integer... vals) {
        return new ArrayList<>(Arrays.asList(vals));
    }

    @Test
    void singleFriendBulkDishCheaper() {
        // Dishes: cap=1 cost=10, cap=4 cost=3
        // Friend cap 4: 4 × cap-1 = 40  vs  1 × cap-4 = 3  → 3
        assertEquals(3, sol.solve(list(4), list(1, 4), list(10, 3)));
    }

    @Test
    void singleFriendRepeatCheaper() {
        // Dishes: cap=1 cost=7, cap=3 cost=4, cap=6 cost=9
        // Friend cap 6: 6×1=42, 2×cap3=8, 1×cap6=9  → 8
        assertEquals(8, sol.solve(list(6), list(1, 3, 6), list(7, 4, 9)));
    }

    @Test
    void twoFriendsDifferentCapacities() {
        // Dishes: cap=1 cost=5, cap=3 cost=8
        // Friend cap 3:  3×1=15 vs 1×cap3=8         → 8
        // Friend cap 6:  6×1=30 vs 2×cap3=16        → 16
        // Total = 24
        assertEquals(24, sol.solve(list(3, 6), list(1, 3), list(5, 8)));
    }

    @Test
    void twoFriendsSameCapacity() {
        // Dishes: cap=1 cost=3, cap=2 cost=4
        // Friend cap 4: 4×1=12, 2×cap2=8, cap2+2×cap1=10  → 8
        // Total = 16
        assertEquals(16, sol.solve(list(4, 4), list(1, 2), list(3, 4)));
    }

    @Test
    void singleCapacityOneDishOnly() {
        // Only cap-1 dish available
        // Friend cap 1: 1×cap1=5
        assertEquals(5, sol.solve(list(1), list(1), list(5)));
    }

    @Test
    void capOneDishInMiddle() {
        // Dishes: cap=3 cost=4, cap=1 cost=7, cap=6 cost=9  (cap-1 is index 1)
        // Friend cap 6: 6×cap1=42, 2×cap3=8, 1×cap6=9  → 8
        assertEquals(8, sol.solve(list(6), list(3, 1, 6), list(4, 7, 9)));
    }

    @Test
    void capOneDishAtEnd() {
        // Dishes: cap=3 cost=8, cap=4 cost=10, cap=1 cost=5  (cap-1 is last)
        // Friend cap 3: 3×cap1=15, 1×cap3=8  → 8
        // Friend cap 6: 2×cap3=16, 1×cap3+3×cap1=8+15=23  → 16
        // Total = 24
        assertEquals(24, sol.solve(list(3, 6), list(3, 4, 1), list(8, 10, 5)));
    }

    @Test
    void capOneDishAtEndSingleFriend() {
        // Dishes: cap=4 cost=3, cap=1 cost=10  (cap-1 is last)
        // Friend cap 4: 1×cap4=3, 4×cap1=40  → 3
        assertEquals(3, sol.solve(list(4), list(4, 1), list(3, 10)));
    }

    @Test
    void greedyWouldFail() {
        // Dishes: cap=1 cost=5, cap=3 cost=4, cap=4 cost=3
        // Greedy by ratio picks cap-4 (0.75/portion) first:
        //   cap-4 + 2×cap-1 = 3+10 = 13
        // DP finds 2×cap-3 = 8  → optimal
        assertEquals(8, sol.solve(list(6), list(1, 3, 4), list(5, 4, 3)));
    }
}
