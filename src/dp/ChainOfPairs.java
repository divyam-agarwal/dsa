package dp;

import java.util.ArrayList;

public class ChainOfPairs {
    // Given N pairs of numbers where first < second in each pair,
    // find the length of the longest chain where pair (c,d) can follow
    // (a,b) only if b < c. Pairs can be reordered.
    public static class Pair{
        int start;
        int end;
        public Pair(int start, int end){
            this.start = start;
            this.end = end;
        }
    }
    public int findIdxInTemp(ArrayList<Pair> temp, Pair p){
        if(temp.isEmpty()){
            temp.add(p);
            return 0;
        }

        // Find rightmost j where temp[j] can precede p (temp[j].end < p.start)
        int l = 0, r = temp.size()-1, pos = -1;
        while(l<=r){
            int m = l + (r-l)/2;
            if(isValidChain(temp.get(m), p)){
                pos = m;
                l = m+1;
            } else{
                r = m-1;
            }
        }

        int insertIdx = pos+1;
        if(insertIdx < temp.size()){
            if(p.end < temp.get(insertIdx).end){
                temp.set(insertIdx, p);
            }
        } else{
            temp.add(p);
        }
        return insertIdx;
    }

    public boolean isValidChain(Pair p1, Pair p2){
        if(p1.end < p2.start)
            return true;
        return false;
    }
    public int solve(ArrayList<ArrayList<Integer>> A) {
        ArrayList<Pair> temp = new ArrayList<>();
        for(ArrayList<Integer> pair : A){
            findIdxInTemp(temp, new Pair(pair.get(0), pair.get(1)));
        }
        return temp.size();
    }
}
