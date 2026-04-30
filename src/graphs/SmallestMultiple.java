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
        if(A==0 || A==1)
            return Integer.toString(A);
        RemainderDigitPair[][] parent = new RemainderDigitPair[A][2];
        Deque<RemainderDigitPair> dq = new ArrayDeque<>();
        boolean[] visited = new boolean[A];

        //start with 1,1 pair
        RemainderDigitPair start = new RemainderDigitPair(1,1);
        visited[1] = true;
        dq.addFirst(start);

        while(!dq.isEmpty()){
            RemainderDigitPair curr = dq.pollLast();
            if(curr.remainder==0){
                return tracePath(curr, parent);
            }

            int nextZeroBranchRemainder = (curr.remainder*10)%A;
            if(!visited[nextZeroBranchRemainder]){
                visited[nextZeroBranchRemainder] = true;
                RemainderDigitPair currZeroBranch = new RemainderDigitPair(nextZeroBranchRemainder, 0);
                parent[nextZeroBranchRemainder][0] = new RemainderDigitPair(curr);
                dq.addFirst(currZeroBranch);
            }

            int nextOneBranchRemainder = (curr.remainder*10 + 1)%A;
            if(!visited[nextOneBranchRemainder]){
                visited[nextOneBranchRemainder] = true;
                RemainderDigitPair currOneBranch = new RemainderDigitPair(nextOneBranchRemainder, 1);
                parent[nextOneBranchRemainder][1] = new RemainderDigitPair(curr);
                dq.addFirst(currOneBranch);
            }

        }
        return "";
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
