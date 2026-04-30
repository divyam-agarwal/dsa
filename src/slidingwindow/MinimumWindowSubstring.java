package slidingwindow;

import java.util.*;

public class MinimumWindowSubstring {

    // LC 76 — Minimum Window Substring
    // Given strings s and t, return the minimum window substring of s such that
    // every character in t (including duplicates) is included. Return "" if no
    // such window exists. Must run in O(m + n) time.

    public int[] getFreqArray(String s){
        int[] freq = new int[58];
        for(char c: s.toCharArray()){
            freq[c-'A']++;
        }
        return freq;
    }

    public boolean isValidWindow(int[] freqS, int[] freqT){
        for(int i = 0;i< freqS.length;i++){
            if(freqS[i]< freqT[i])
                return false;
        }
        return true;
    }

    public String minWindow(String s, String t) {
        if(s.length()< t.length()) return "";

        int[] freqT = getFreqArray(t);
        int[] freqS = getFreqArray(s.substring(0, t.length()));
        int ansL = 0, ansR = 0;
        int l = 0;
        int minWindowSize = Integer.MAX_VALUE;

        if(isValidWindow(freqT, freqS)){
            ansR = t.length();
            minWindowSize = ansR-ansL;
        }

        for(int i = t.length();i<s.length();i++){
            freqS[s.charAt(i)-'A']++;
            if(i-l>= t.length() && isValidWindow(freqS, freqT)){
                if(i-l< minWindowSize){
                    ansL= l;
                    ansR = i+1;
                    minWindowSize = i-l;
                }
                while(isValidWindow(freqS, freqT)){
                    if(i-l< minWindowSize){
                        ansL= l;
                        ansR = i+1;
                        minWindowSize = i-l;
                    }

                    freqS[s.charAt(l)-'A']--;
                    l++;
                }
            }
        }

        return s.substring(ansL, ansR);

    }
}
