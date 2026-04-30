package trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class SearchAutoComplete {
    public static class TrieNode{
        char c;
        TrieNode[] children;
        boolean isTerminal;
        int frequency;

        public TrieNode(char c) {
            this.c = c;
            children = new TrieNode[27];
            isTerminal = false;
            frequency = 0;
        }
    }

    public StringBuilder currentInput;
    public TrieNode rootNode;

    public void insertWordInTrie(String word,int freq, boolean init){
        word = word.toLowerCase();
        TrieNode start = rootNode;
        for(char ch: word.toCharArray()){
            int index = ch == ' ' ? 26 : ch - 'a';
            if(start.children[index]==null){
                TrieNode newNode = new TrieNode(ch);
                start.children[index] = newNode;
            }
            start = start.children[index];
        }
        start.isTerminal = true;

        if(init)
            start.frequency += freq;
        else
            start.frequency++;
    }

    public List<String> searchPrefixTop3(String prefix){
        prefix = prefix.toLowerCase();
        TrieNode start = rootNode;
        List<Pair<String, Integer>> matches = new ArrayList<>();
        for(char ch: prefix.toCharArray()){
            int index = ch == ' ' ? 26 : ch - 'a';
            if(start.children[index]==null){
                return new ArrayList<>();
            }
            start = start.children[index];
        }
        //now we need to check all neighors downstream for matches
        PriorityQueue<Pair<String, Integer>> pq = new PriorityQueue<>((p,q)->{
            if(Integer.compare(p.frequency, q.frequency)!=0){
                return Integer.compare(q.frequency, p.frequency);
            }
            return p.term.compareTo(q.term);
        });
        List<Pair<String, Integer>> validPairsForPQ = new ArrayList<>();
        getPairs(start, prefix, validPairsForPQ);
        for(Pair<String, Integer> p: validPairsForPQ){
            pq.offer(p);
        }

        List<String> ans = new ArrayList<>();
        for(int i = 0;i<3;i++){
            ans.add(Objects.requireNonNull(pq.poll()).term);
        }
        return ans;
    }

    private void getPairs(TrieNode start, String prefix, List<Pair<String, Integer>> result) {
        if(start.isTerminal)
            result.add(new Pair<>(prefix, start.frequency));

        for(TrieNode child: start.children){
            if(child!=null){
                getPairs(child, prefix + child.c, result);
            }
        }
    }

    public SearchAutoComplete() {
        this.currentInput = new StringBuilder();
        this.rootNode = new TrieNode('$');
    }

    public List<String> input(char c){
        if(c=='#'){
            insertWordInTrie(currentInput.toString(), 1, false);
            currentInput.delete(0, currentInput.length());
            return new ArrayList<>();
        }
        else{
            currentInput.append(c);
            return searchPrefixTop3(currentInput.toString());
        }
    }


}
