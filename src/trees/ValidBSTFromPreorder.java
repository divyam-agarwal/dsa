package trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class ValidBSTFromPreorder {

    // ─── Solution ────────────────────────────────────────────────────────────
    // Preorder visits: root → left subtree → right subtree.
    // When we first see a value larger than the stack top, we are crossing into
    // a right subtree. Everything popped off the stack while doing so becomes
    // the new lower bound — no future value may be ≤ that bound.
    //
    // Algorithm (O(n) time, O(n) space):
    //   lowerBound = Integer.MIN_VALUE
    //   for each value in preorder:
    //       if value < lowerBound  →  invalid (violates ancestor constraint)
    //       while stack not empty && stack.top < value  →  pop, update lowerBound
    //       push value
    //   return true

    public int solve(ArrayList<Integer> A) {
        int lowerBound = Integer.MIN_VALUE;;
        Deque<Integer> dq = new ArrayDeque<>();

        dq.addFirst(A.get(0));
        for(int i = 1;i<A.size();i++){
            if(A.get(i)<lowerBound){
                return 0;
            }

            if(A.get(i)< dq.peek()){
                dq.addFirst(A.get(i));
            }
            else if(A.get(i).equals(dq.peek())){
                return 0;
            }
            else{
                while(!dq.isEmpty() && A.get(i)>dq.peek()){
                    int newBound = dq.pollFirst();
                    lowerBound = Math.max(lowerBound, newBound);
                }
                dq.addFirst(A.get(i));
            }
        }
        return 1;
    }
}
