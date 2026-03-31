package dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WaysToColor3xNBoard {

    // ─── Solution ─────────────────────────────────────────────────────────────
    // Count ways to color a 3×N board with 4 colors such that no two adjacent
    // cells share a color.  Return the answer mod 10^9+7.
    //
    // TODO

    //get valid bitmasks by checking middle 2 bits ad checking if not equal to either left 2 or right 2 bits
    public ArrayList<Integer> getValidBitmasks(){
        ArrayList<Integer> ans = new ArrayList<>();
        for(int i = 0;i<64;i++){
            int first2bits = 3 & i;
            int second2bits = ((3<<2) & i) >> 2;
            int third2bits = ((3<<4) & i) >> 4;

            if(second2bits!=first2bits && second2bits!=third2bits)
                ans.add(i);

        }
        return ans;
    }

    boolean isValidMerge(int mask1, int mask2){
        //assuming each is indivisually valid via getValidBitmasks
        int first2bits = 3;
        int second2bits = 3<<2;
        int third2bits = 3<<4;

        if((mask1 & first2bits)==(mask2 & first2bits)){
            return false;
        }
        if((mask1 & second2bits)==(mask2 & second2bits)){
            return false;
        }
        if((mask1 & third2bits)==(mask2 & third2bits)){
            return false;
        }
        return true;
    }

    public int solve(int A) {
        ArrayList<Integer> validMasks = getValidBitmasks();

        HashMap<Integer, Integer> prev = new HashMap<>();
        HashMap<Integer, Integer> curr = new HashMap<>();

        //base case
        for (Integer validMask : validMasks) {
            prev.put(validMask, 1);
        }

        for(int i = 1;i<A;i++){
            curr.clear();
            for(Integer validMask: validMasks){
                for(Integer validPrevMask: validMasks){
                    if(isValidMerge(validMask, validPrevMask)){
                        int toAdd = prev.get(validPrevMask);
                        curr.merge(validMask, toAdd, (oldValue, newValue)-> (oldValue+toAdd) % 1_000_000_007);
                    }
                }
            }
            // swap: curr becomes prev for the next iteration
            HashMap<Integer, Integer> temp = prev;
            prev = curr;
            curr = temp;
        }

        int sum = 0;
        for (int val : prev.values()) {
            sum = (sum + val) % 1_000_000_007;
        }
        return sum;
    }
}
