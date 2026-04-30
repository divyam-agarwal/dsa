package hashing;

import java.util.*;

public class LongestConsecutiveSequence {

    // LC 128 — Longest Consecutive Sequence
    // Given an unsorted array of integers nums, return the length of the longest
    // consecutive elements sequence. Must run in O(n) time.

    // add all nums in a hashset
    // for each in num
        //check if num-1 is present in set
            //if yes, get the length, contniue
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;

        HashSet<Integer> hs = new HashSet<>();
        for(Integer num : nums){
            hs.add(num);
        }
        int maxLen = 1;
        for(int num: nums){
            if(!hs.contains(num-1)){
                int currLen = 1;
                int currNum = num;
                while(hs.contains(currNum+1)){
                    currLen++;
                    currNum++;
                }
                maxLen = Math.max(maxLen, currLen);
            }
        }
        return maxLen;
    }
}
