package dp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PotionsTest {
    Potions sol = new Potions();

    @Test
    void example1_twoPotions() {
        // Only one way to mix: 2 * 3 = 6
        assertEquals(6, sol.solve(new int[]{2, 3}));
    }

    @Test
    void example2_fourPotions() {
        // Minimum smoke over all mixing orders is 71
        assertEquals(71, sol.solve(new int[]{2, 3, 4, 5}));
    }

    @Test
    void singlePotion_noMixingNeeded() {
        assertEquals(0, sol.solve(new int[]{42}));
    }

    @Test
    void zeroColorPotion_noSmoke() {
        // Mixing anything with 0 produces 0 smoke at that step
        assertEquals(0, sol.solve(new int[]{0, 5}));
    }

    @Test
    void zeroColorInMiddle_reducesSmoke() {
        // Optimal: mix (5,0) first → 0 smoke, result color 5; then mix 5*7 = 35
        // vs mix (0,7) first → 0 smoke, result color 7; then mix 5*7 = 35
        // vs mix (5,0,7) via split at index 1 first full: same result
        assertEquals(35, sol.solve(new int[]{5, 0, 7}));
    }

    @Test
    void colorsWrapAroundMod100() {
        // sum(50,60) % 100 = 10; smoke for single mix = 50*60 = 3000
        assertEquals(3000, sol.solve(new int[]{50, 60}));
    }

    @Test
    void modAffectsIntermediateSmoke() {
        // [1, 99, 1]: mixing 1+99=100 ≡ 0 mod 100 first costs 1*99=99 smoke,
        // then mixing 0 with 1 costs 0 — total 99.
        // Other split: 99+1=0, costs 99*1=99, then 1*0=0 — also 99.
        assertEquals(99, sol.solve(new int[]{1, 99, 1}));
    }

    @Test
    void allSameColor() {
        // [3, 3, 3]:
        // split at 0: dp[0][0]+dp[1][2] + 3*6 = 0+9+18 = 27
        // split at 1: dp[0][1]+dp[2][2] + 6*3 = 9+0+18 = 27
        assertEquals(27, sol.solve(new int[]{3, 3, 3}));
    }

    @Test
    void largeValues_maxColors() {
        // [99, 99]: smoke = 99*99 = 9801
        assertEquals(9801, sol.solve(new int[]{99, 99}));
    }

    @Test
    void orderingMatters() {
        // [1, 2, 10]:
        // split at 0: 0 + (2*10) + 1*12 = 20+12 = 32
        // split at 1: (1*2) + 0 + 3*10 = 2+30 = 32
        // Both yield 32 here.
        assertEquals(32, sol.solve(new int[]{1, 2, 10}));
    }
}
