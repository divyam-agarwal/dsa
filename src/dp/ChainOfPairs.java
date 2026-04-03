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

        int l = 0, r= temp.size()-1;
        int m = l, ans = r+1;
        while(l<=r){
            m = l+ (r-l)/2;
            if(isValidChain(p, temp.get(m))){
                if(isValidChain(temp.get(m-1), p)){
                    ans = m;
                    temp.set(ans, p);
                    return ans;
                }
                else{
                    ans = m-1;
                }
                r = m-1;
            }
            else{
                l = m+1;
            }
        }

        if(ans<temp.size()){
            //check all edge cases
            Pair p1 = temp.get(ans);
            Pair p2 = null;
            if(ans-1>=0){
                p2 = temp.get(ans-1);
            }

            if(p1.end > p.end && (p2==null || p2.end < p.start)){
                temp.set(ans, p);
            }
        }
        else{
            temp.add(p);
        }

        return ans;


    }

    public boolean isValidChain(Pair p1, Pair p2){
        if(p1.end < p2.start)
            return true;
        return false;
    }
    public int solve(ArrayList<ArrayList<Integer>> A) {
        int n = A.size();

        ArrayList<Pair> temp = new ArrayList<>();

        for(int i = 0;i<n;i++){
            int idx = findIdxInTemp(temp, new Pair(A.get(i).get(0), A.get(i).get(1)));
        }
        return temp.size();
    }
}
