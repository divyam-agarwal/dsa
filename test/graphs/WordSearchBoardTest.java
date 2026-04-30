package graphs;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class WordSearchBoardTest {

    WordSearchBoard sol = new WordSearchBoard();

    private ArrayList<String> board(String... rows) {
        ArrayList<String> A = new ArrayList<>();
        for (String row : rows) A.add(row);
        return A;
    }

    // ─── InterviewBit examples ───────────────────────────────────────────────

    @Test
    void interviewBitExampleFound() {
        // Board:
        // A B C E
        // S F C S
        // A D E E
        // Word: "ABCCED" → 1
        assertEquals(1, sol.exist(board("ABCE", "SFCS", "ADEE"), "ABCCED"));
    }

    @Test
    void interviewBitExampleNotFound() {
        // Same board, Word: "ABCB" → 0 (can't reuse B at (0,1))
        assertEquals(0, sol.exist(board("ABCE", "SFCS", "ADEE"), "ABCB"));
    }

    @Test
    void interviewBitExampleSEE() {
        // Same board, Word: "SEE" → 1
        // S(1,3) → E(2,3) → E(2,2)
        assertEquals(1, sol.exist(board("ABCE", "SFCS", "ADEE"), "SEE"));
    }

    // ─── Single cell ─────────────────────────────────────────────────────────

    @Test
    void singleCellMatch() {
        assertEquals(1, sol.exist(board("A"), "A"));
    }

    @Test
    void singleCellNoMatch() {
        assertEquals(0, sol.exist(board("A"), "B"));
    }

    // ─── Word longer than board ───────────────────────────────────────────────

    @Test
    void wordLongerThanBoard() {
        assertEquals(0, sol.exist(board("AB", "CD"), "ABCDE"));
    }

    // ─── Word is entire board ─────────────────────────────────────────────────

    @Test
    void wordIsEntireBoard() {
        // A B
        // D C
        // "ABCD" → A(0,0)→B(0,1)→C(1,1)→D(1,0) → 1
        assertEquals(1, sol.exist(board("AB", "DC"), "ABCD"));
    }

    // ─── Duplicate letters on board ──────────────────────────────────────────

    @Test
    void duplicateLettersWordFound() {
        // A A
        // A A
        // "AAA" → 1
        assertEquals(1, sol.exist(board("AA", "AA"), "AAA"));
    }

    @Test
    void duplicateLettersCantReuseCell() {
        // Only one 'B': can't spell "BB"
        assertEquals(0, sol.exist(board("AB", "CD"), "BB"));
    }

    // ─── Path must be adjacent (no diagonals) ────────────────────────────────

    @Test
    void diagonalNotAllowed() {
        // A B
        // C D
        // "AD" requires diagonal → 0
        assertEquals(0, sol.exist(board("AB", "CD"), "AD"));
    }

    // ─── Vertical path ───────────────────────────────────────────────────────

    @Test
    void verticalPath() {
        assertEquals(1, sol.exist(board("A", "B", "C"), "ABC"));
    }

    @Test
    void verticalPathReversed() {
        assertEquals(1, sol.exist(board("A", "B", "C"), "CBA"));
    }

    // ─── Winding path ────────────────────────────────────────────────────────

    @Test
    void windingPath() {
        // C A T
        // B A G
        // T A G
        // "BACAT" → B(1,0)→A(1,1)→C(0,0)→A(0,1)→T(0,2) → 1
        assertEquals(1, sol.exist(board("CAT", "BAG", "TAG"), "BACAT"));
    }

    // ─── Word not present at all ─────────────────────────────────────────────

    @Test
    void letterMissingFromBoard() {
        assertEquals(0, sol.exist(board("ABC", "DEF", "GHI"), "XYZ"));
    }

    // ─── Single row / single column ──────────────────────────────────────────

    @Test
    void singleRowFound() {
        assertEquals(1, sol.exist(board("HELLO"), "ELLO"));
    }

    @Test
    void singleRowNotFound() {
        // "OLLE" needs two L's but there's only one
        assertEquals(0, sol.exist(board("HELLO"), "OLLE"));
    }

    // ─── Backtracking required ───────────────────────────────────────────────

    @Test
    void backtrackingRequired() {
        // A A B
        // A A A
        // "AAAB": wrong greedy path explored first, must backtrack
        assertEquals(1, sol.exist(board("AAB", "AAA"), "AAAB"));
    }

    @Test
    void fiveAsThenB() {
        // A A A
        // A A B
        // "AAAAAB" → 1
        assertEquals(1, sol.exist(board("AAA", "AAB"), "AAAAAB"));
    }
}
