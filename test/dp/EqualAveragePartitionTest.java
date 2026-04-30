package dp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EqualAveragePartitionTest {
    EqualAveragePartition sol = new EqualAveragePartition();

    private ArrayList<Integer> list(Integer... vals) {
        return new ArrayList<>(Arrays.asList(vals));
    }

    private ArrayList<ArrayList<Integer>> partition(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        result.add(s1);
        result.add(s2);
        return result;
    }

    @Test
    void simplePartition() {
        // A=[1,2,3,4], sum=10, avg=2.5
        // k=2 target=5: valid pairs [1,4],[2,3]; lex smallest S1=[1,4]
        assertEquals(
            partition(list(1, 4), list(2, 3)),
            sol.solve(list(1, 2, 3, 4))
        );
    }

    @Test
    void lexSmallerS1HasMoreElements() {
        // A=[1,2,3,4,5], sum=15, avg=3
        // Candidates: [3],[1,5],[1,3,5],[1,2,4,5] — lex smallest is [1,2,4,5]
        assertEquals(
            partition(list(1, 2, 4, 5), list(3)),
            sol.solve(list(1, 2, 3, 4, 5))
        );
    }

    @Test
    void singleElementS1IsNotLexSmallest() {
        // A=[1,2,3], sum=6, avg=2
        // k=1 → S1=[2]; k=2 → S1=[1,3]; [1,3]<[2] lex → S1=[1,3]
        assertEquals(
            partition(list(1, 3), list(2)),
            sol.solve(list(1, 2, 3))
        );
    }

    @Test
    void duplicateElements() {
        // A=[3,3], sum=6, avg=3 — only valid partition is [3],[3]
        assertEquals(
            partition(list(3), list(3)),
            sol.solve(list(3, 3))
        );
    }

    @Test
    void largerExample() {
        // A=[1,7,9,11,15,29], sum=72, avg=12
        // k=2: {9,15}; k=4: {1,7,11,29}; lex: [1,7,11,29] < [9,15]
        // avg([1,7,11,29])=48/4=12, avg([9,15])=24/2=12
        assertEquals(
            partition(list(1, 7, 11, 29), list(9, 15)),
            sol.solve(list(1, 7, 9, 11, 15, 29))
        );
    }

    @Test
    void noPartitionTwoElements() {
        // A=[1,2], avg=1.5 — no integer target for any k
        assertEquals(
            partition(list(), list()),
            sol.solve(list(1, 2))
        );
    }

    @Test
    void noPartitionNonDivisibleSum() {
        // A=[1,2,4], sum=7 — k*7/3 is never an integer for k=1,2
        assertEquals(
            partition(list(), list()),
            sol.solve(list(1, 2, 4))
        );
    }

    @Test
    void nonIntegerAverage() {
        // A=[47,14,30,19,30,4,32,32,15,2,6,24], sum=255, n=12, avg=21.25 — not an integer, no valid partition
        assertEquals(
            partition(list(), list()),
            sol.solve(list(47, 14, 30, 19, 30, 4, 32, 32, 15, 2, 6, 24))
        );
    }
}
