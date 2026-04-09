package graphs;

// InterviewBit: Smallest Multiple With 0 and 1
// https://www.interviewbit.com/problems/smallest-multiple-with-0-and-1/
//
// Given an integer A, find the smallest positive multiple of A whose decimal
// representation contains only the digits 0 and 1. Return it as a string
// (no leading zeros).
//
// Approach: BFS over remainders mod A.
// States are remainders 0..A-1; start from remainder 1 (the digit "1").
// At each state r, two edges:
//   append '0' → new remainder = (r * 10)     % A
//   append '1' → new remainder = (r * 10 + 1) % A
// BFS guarantees the shortest (lexicographically smallest for equal length)
// number is found first. Reconstruct path via parent pointers.
//
// Input:  A — the divisor (1 ≤ A ≤ 10^5)
// Output: smallest multiple of A using only digits 0 and 1, as a string

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class SmallestMultiple {

    public static class RemainderDigitPair{
        int remainder;
        int digit;

        public RemainderDigitPair(int remainder, int digit) {
            this.remainder = remainder;
            this.digit = digit;
        }

        public RemainderDigitPair(RemainderDigitPair curr) {
            this.remainder = curr.remainder;
            this.digit = curr.digit;
        }
    }

    public String multiple(int A) {
        printBFSGraph(A);
        if(A==0 || A==1)
            return Integer.toString(A);
        RemainderDigitPair[][] parent = new RemainderDigitPair[A][2];
        Deque<RemainderDigitPair> dq = new ArrayDeque<>();
        boolean[] visited = new boolean[A];

        //start with 1,1 pair
        RemainderDigitPair start = new RemainderDigitPair(1,1);
        dq.addFirst(start);

        while(!dq.isEmpty()){
            RemainderDigitPair curr = dq.pollLast();
            visited[curr.remainder] = true;
            if(curr.remainder==0){
                return tracePath(curr, parent);
            }

            int nextZeroBranchRemainder = (curr.remainder*10)%A;
            if(!visited[nextZeroBranchRemainder]){
                RemainderDigitPair currZeroBranch = new RemainderDigitPair(nextZeroBranchRemainder, 0);
                parent[nextZeroBranchRemainder][0] = new RemainderDigitPair(curr);
                dq.addFirst(currZeroBranch);
            }

            int nextOneBranchRemainder = (curr.remainder*10 + 1)%A;
            if(!visited[nextOneBranchRemainder]){
                RemainderDigitPair currOneBranch = new RemainderDigitPair(nextOneBranchRemainder, 1);
                parent[nextOneBranchRemainder][1] = new RemainderDigitPair(curr);
                dq.addFirst(currOneBranch);
            }

        }
        return "";
    }

    // Prints the BFS spanning tree as a 2D ASCII tree.
    //   - Left branch  = append '0'  (0-branch)
    //   - Right branch = append '1'  (1-branch)
    //   - Node label   = (remainder, lastDigit)
    //   - Goal node    = marked with *
    // x-positions are assigned via in-order traversal so left subtrees stay
    // left and right subtrees stay right; edges drawn with '/' and '\'.
    public void printBFSGraph(int A) {
        if (A == 1) {
            System.out.println("(0,1)*");
            return;
        }
        if (A > 100) {
            System.out.printf("[Tree too large to render (A=%d). Use A ≤ 100.]%n", A);
            return;
        }

        // ── Phase 1: BFS to build spanning tree ──────────────────────────────
        boolean[] visited  = new boolean[A];
        int[]     digitTo  = new int[A];      // digit used to reach remainder r
        int[]     depth    = new int[A];
        int[]     leftCh   = new int[A];      // 0-branch child remainder, -1 = none
        int[]     rightCh  = new int[A];      // 1-branch child remainder, -1 = none
        Arrays.fill(leftCh,  -1);
        Arrays.fill(rightCh, -1);

        Deque<Integer>      bfsQ   = new ArrayDeque<>();
        List<List<Integer>> levels = new ArrayList<>();

        visited[1]  = true;
        digitTo[1]  = 1;
        bfsQ.add(1);

        int maxDepth = 0;
        while (!bfsQ.isEmpty()) {
            int rem = bfsQ.poll();
            while (levels.size() <= depth[rem]) levels.add(new ArrayList<>());
            levels.get(depth[rem]).add(rem);
            maxDepth = Math.max(maxDepth, depth[rem]);

            if (rem == 0) continue; // goal is a leaf

            int nz = (rem * 10) % A;
            if (!visited[nz]) {
                visited[nz] = true; digitTo[nz] = 0;
                depth[nz] = depth[rem] + 1; leftCh[rem] = nz;
                bfsQ.add(nz);
            }
            int no = (rem * 10 + 1) % A;
            if (!visited[no]) {
                visited[no] = true; digitTo[no] = 1;
                depth[no] = depth[rem] + 1; rightCh[rem] = no;
                bfsQ.add(no);
            }
        }

        // ── Phase 2: assign x-positions via in-order traversal ───────────────
        // Each leaf gets a sequential slot; internal nodes sit at the midpoint
        // of their children. This keeps left subtrees left, right subtrees right.
        int labelW  = String.valueOf(A - 1).length() + 5; // "(rem,d)*" max width
        int nodeSlot = labelW + 2;                         // gap between siblings
        int[] xPos  = new int[A];
        int[] leaf  = {0};
        assignXPos(1, leftCh, rightCh, xPos, leaf, nodeSlot);

        // ── Phase 3: print node rows with '/' '\' edge rows between them ─────
        int bufSize = leaf[0] * nodeSlot + nodeSlot;
        for (int d = 0; d <= maxDepth; d++) {

            // Node row
            char[] row = new char[bufSize];
            Arrays.fill(row, ' ');
            for (int rem : levels.get(d)) {
                String lbl = "(" + rem + "," + digitTo[rem] + ")" + (rem == 0 ? "*" : "");
                int cx = xPos[rem] + nodeSlot / 2;
                int sx = cx - lbl.length() / 2;
                for (int i = 0; i < lbl.length(); i++) row[sx + i] = lbl.charAt(i);
            }
            System.out.println(rTrim(row));

            if (d == maxDepth) break;

            // Edge row: '/' from leftChild-center to parent-center,
            //           '\' from parent-center to rightChild-center
            char[] erow = new char[bufSize];
            Arrays.fill(erow, ' ');
            for (int rem : levels.get(d)) {
                int pc = xPos[rem] + nodeSlot / 2;
                if (leftCh[rem] != -1) {
                    int cc = xPos[leftCh[rem]] + nodeSlot / 2;
                    for (int x = cc + 1; x < pc; x++) erow[x] = '/';
                }
                if (rightCh[rem] != -1) {
                    int cc = xPos[rightCh[rem]] + nodeSlot / 2;
                    for (int x = pc + 1; x < cc; x++) erow[x] = '\\';
                }
            }
            System.out.println(rTrim(erow));
        }
    }

    // In-order traversal: visit left subtree, then right subtree, assign
    // parent x = midpoint of children (or child's x when only one exists).
    private void assignXPos(int rem, int[] lc, int[] rc, int[] xPos, int[] leaf, int slot) {
        if (lc[rem] == -1 && rc[rem] == -1) { xPos[rem] = leaf[0]++ * slot; return; }
        if (lc[rem] != -1) assignXPos(lc[rem], lc, rc, xPos, leaf, slot);
        if (rc[rem] != -1) assignXPos(rc[rem], lc, rc, xPos, leaf, slot);
        int lx = lc[rem] != -1 ? xPos[lc[rem]] : xPos[rc[rem]];
        int rx = rc[rem] != -1 ? xPos[rc[rem]] : xPos[lc[rem]];
        xPos[rem] = (lx + rx) / 2;
    }

    private String rTrim(char[] row) {
        int len = row.length;
        while (len > 0 && row[len - 1] == ' ') len--;
        return new String(row, 0, len);
    }

    private String tracePath(RemainderDigitPair curr, RemainderDigitPair[][] parent) {
        StringBuilder sb = new StringBuilder();
        while(curr!=null){
            sb.append(Integer.toString(curr.digit));
            curr = parent[curr.remainder][curr.digit];
        }
        return sb.reverse().toString();
    }
}
