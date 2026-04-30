package graphs;

// InterviewBit: Snake Ladder Problem
// https://www.interviewbit.com/problems/snake-ladder-problem/
//
// Given a 30-cell linear board (cells 1..30), find the minimum number of
// dice rolls to travel from cell 1 to cell 30.
//
// Rules:
//   - Each roll moves the player forward by 1–6 cells.
//   - Landing on a ladder's bottom teleports you to its top (higher cell).
//   - Landing on a snake's head teleports you to its tail (lower cell).
//   - Overshooting cell 30 is not allowed (rolls that would exceed 30 are skipped).
//   - Return -1 if cell 30 cannot be reached.
//
// Input:
//   snakes  — int[][] where snakes[i] = {head, tail}, head > tail
//   ladders — int[][] where ladders[i] = {bottom, top}, bottom < top
// Output: minimum number of dice rolls to reach cell 30, or -1

import java.util.*;

public class SnakeLadder {

    public int snakeLadder(ArrayList<ArrayList<Integer>> snakes, ArrayList<ArrayList<Integer>> ladders) {
        int[] minRolls = new int[101];
        Arrays.fill(minRolls, Integer.MAX_VALUE/2);

        int level = 0;
        HashMap<Integer, Integer> ladderMap = getHashMap(ladders);
        HashMap<Integer, Integer> snakesMap = getHashMap(snakes);

        Deque<Integer> dq = new ArrayDeque<>();
        dq.addFirst(1);


        while(!dq.isEmpty() && level<100){
            int levelSize = dq.size();
            for(int j = 0;j<levelSize;j++){
                int curr = dq.pollLast();
                minRolls[curr] = Math.min(minRolls[curr], level);
                for(int i = 1;i<=6;i++){
                    int toAdd = curr+i;
                    if(toAdd<=100){
                        toAdd = ladderMap.containsKey(toAdd)? ladderMap.get(toAdd) : toAdd;
                        toAdd = snakesMap.containsKey(toAdd)? snakesMap.get(toAdd) : toAdd;
                        if(level+1< minRolls[toAdd]){
                            if(toAdd==100)
                                return level+1;
                            dq.addFirst(toAdd);
                        }
                    }
                }
            }
            level++;

        }
        return -1;

    }

    private HashMap<Integer, Integer> getHashMap(ArrayList<ArrayList<Integer>> pairs) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for(ArrayList<Integer> pair : pairs){
            map.putIfAbsent(pair.get(0), pair.get(1));
        }
        return map;
    }
}
