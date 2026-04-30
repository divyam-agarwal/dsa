package graphs;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class CourseScheduleTest {

    CourseSchedule sol = new CourseSchedule();

    private ArrayList<Integer> from(int[][] pairs) {
        ArrayList<Integer> b = new ArrayList<>();
        for (int[] p : pairs) b.add(p[0]);
        return b;
    }

    private ArrayList<Integer> to(int[][] pairs) {
        ArrayList<Integer> c = new ArrayList<>();
        for (int[] p : pairs) c.add(p[1]);
        return c;
    }

    // ─── InterviewBit examples ───────────────────────────────────────────────

    @Test
    void interviewBitExample_noCycle() {
        // [1,2] means course 1 requires course 2 → take 2 then 1 → possible
        int[][] pairs = {{1, 2}};
        assertEquals(1, sol.solve(2, from(pairs), to(pairs)));
    }

    @Test
    void interviewBitExample_cycle() {
        // [1,2] and [2,1] → circular dependency → impossible
        int[][] pairs = {{1, 2}, {2, 1}};
        assertEquals(0, sol.solve(2, from(pairs), to(pairs)));
    }

    // ─── No prerequisites ────────────────────────────────────────────────────

    @Test
    void singleCourseNoPrerequisites() {
        assertEquals(1, sol.solve(1, new ArrayList<>(), new ArrayList<>()));
    }

    @Test
    void manyCoursesNoPrerequisites() {
        assertEquals(1, sol.solve(5, new ArrayList<>(), new ArrayList<>()));
    }

    // ─── Acyclic graphs (should return 1) ────────────────────────────────────

    @Test
    void linearChainNoCycle() {
        // 1←2←3←4
        int[][] pairs = {{2, 1}, {3, 2}, {4, 3}};
        assertEquals(1, sol.solve(4, from(pairs), to(pairs)));
    }

    @Test
    void diamondDagNoCycle() {
        //     1
        //    / \
        //   2   3
        //    \ /
        //     4
        int[][] pairs = {{2, 1}, {3, 1}, {4, 2}, {4, 3}};
        assertEquals(1, sol.solve(4, from(pairs), to(pairs)));
    }

    @Test
    void twoIndependentChains() {
        // Chain A: 1←2   Chain B: 3←4  — disconnected, both acyclic
        int[][] pairs = {{2, 1}, {4, 3}};
        assertEquals(1, sol.solve(4, from(pairs), to(pairs)));
    }

    // ─── Cyclic graphs (should return 0) ─────────────────────────────────────

    @Test
    void selfLoop() {
        int[][] pairs = {{1, 1}};
        assertEquals(0, sol.solve(2, from(pairs), to(pairs)));
    }

    @Test
    void simpleCycleThreeNodes() {
        // 1→2→3→1
        int[][] pairs = {{1, 2}, {2, 3}, {3, 1}};
        assertEquals(0, sol.solve(3, from(pairs), to(pairs)));
    }

    @Test
    void cycleInOneDisconnectedComponent() {
        // {1,2} acyclic; {3,4} form a cycle
        int[][] pairs = {{2, 1}, {4, 3}, {3, 4}};
        assertEquals(0, sol.solve(4, from(pairs), to(pairs)));
    }

    @Test
    void longCycle() {
        // 1→2→3→4→5→1
        int[][] pairs = {{2, 1}, {3, 2}, {4, 3}, {5, 4}, {1, 5}};
        assertEquals(0, sol.solve(5, from(pairs), to(pairs)));
    }
}
