package twopointers;

import java.util.*;

public class SubarraysWithDistinctIntegers {

    // ─── Solution ────────────────────────────────────────────────────────────
    // Exactly K distinct = atMost(K) - atMost(K-1)

    public int atMost(ArrayList<Integer> A, int B){
        int l = 0, count = 0;
        HashMap<Integer, Integer> mp = new HashMap();
        for(int r = 0;r<A.size();r++){
            //get all good subarrays ending at r
            if(!mp.containsKey(A.get(r))){
                mp.put(A.get(r),1);
            }
            else{
                mp.put(A.get(r), mp.get(A.get(r))+1);
            }

            while(mp.size()>B){
                mp.put(A.get(l), mp.get(A.get(l)) - 1);
                if(mp.get(A.get(l))==0){
                    mp.remove(A.get(l));
                }
                l++;
            }

            if(mp.size()==B){
                count+= (r-l+1);
            }
        }
        return count;
    }
    public int solve(ArrayList<Integer> A, int B) {
        return atMost(A,B)- atMost(A,B-1);
    }
}
