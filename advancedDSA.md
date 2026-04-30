# Advanced DSA — Company-Wise Curated Problems

> Problems organized by company. Each entry includes problem statement, approach, and Java solution skeleton.

---

## Table of Contents

### Google
1. [Design Search Autocomplete System](#1-design-search-autocomplete-system-lc-642)

---

## Google

### 1. Design Search Autocomplete System (LC 642)
> Read: 1 | Coded: 1

**Difficulty**: Hard
**Source**: [HelloInterview](https://www.hellointerview.com/community/questions/design-search-autocomplete-system/67257ab0-683b-481d-bfd2-f7e63258056e)

**Problem**: Build an autocomplete system that processes character-by-character input and returns the top 3 most frequent sentences matching the current prefix. Rankings are determined by frequency (descending), with lexicographical order as a tiebreaker. When the user types `#`, the current sentence is saved and its frequency incremented.

**Constraints**:
- Return at most 3 results per query
- Sentences contain lowercase letters and spaces, up to 100 characters
- Initial frequencies are positive integers

**Example**:
```
sentences = ["hello world", "hello interview", "hello"]
frequencies = [5, 3, 2]

Input: 'h' → returns ["hello world", "hello interview", "hello"]
Input: 'e' → returns ["hello world", "hello interview", "hello"]
Input: '#' → saves "he", increments its frequency
```

**Approach**: Trie where each node stores all sentences passing through that prefix. On each character, traverse the trie and sort candidates by `(-frequency, sentence)`, returning top 3. On `#`, increment frequency in the map and update trie.

```java
class AutocompleteSystem {
    private Map<String, Integer> freq = new HashMap<>();
    private TrieNode root = new TrieNode();
    private StringBuilder curr = new StringBuilder();

    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        Set<String> sentences = new HashSet<>();
    }

    public AutocompleteSystem(String[] sentences, int[] times) {
        for (int i = 0; i < sentences.length; i++) {
            freq.put(sentences[i], times[i]);
            insert(sentences[i]);
        }
    }

    private void insert(String s) {
        TrieNode node = root;
        for (char c : s.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
            node.sentences.add(s);
        }
    }

    public List<String> input(char c) {
        if (c == '#') {
            String s = curr.toString();
            freq.merge(s, 1, Integer::sum);
            insert(s);
            curr.setLength(0);
            return new ArrayList<>();
        }
        curr.append(c);
        TrieNode node = root;
        for (char ch : curr.toString().toCharArray()) {
            if (!node.children.containsKey(ch)) return new ArrayList<>();
            node = node.children.get(ch);
        }
        return node.sentences.stream()
            .sorted((a, b) -> freq.get(b) != freq.get(a)
                ? freq.get(b) - freq.get(a)
                : a.compareTo(b))
            .limit(3)
            .collect(Collectors.toList());
    }
}
```

**Complexity**: O(N log N) per query for sorting; O(M × L) space where M = number of sentences, L = average sentence length.

---
