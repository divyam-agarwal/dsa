# DSA Revision — Topics Ranked by Interview Importance

> Java skeletons only. Problems are unique across all topics.

---

## Table of Contents

### Tier 1 — Must Know
1. [Two Pointers](#1-two-pointers)
2. [Hash Tables](#2-hash-tables)
3. [Sliding Window — Fixed Size](#3-sliding-window--fixed-size)
4. [Sliding Window — Dynamic Size](#4-sliding-window--dynamic-size)
5. [Binary Search](#5-binary-search)
6. [Strings](#6-strings)
7. [Tree DFS (Pre/In/Post Order)](#7-tree-dfs-preinpost-order)
8. [Tree BFS (Level Order)](#8-tree-bfs-level-order)
9. [1-D DP](#9-1-d-dp)
10. [Graph DFS](#10-graph-dfs)
11. [Graph BFS](#11-graph-bfs)
12. [Prefix Sum](#12-prefix-sum)

### Tier 2 — Very Important
13. [Linked List + In-place Reversal](#13-linked-list--in-place-reversal)
14. [Fast & Slow Pointers](#14-fast--slow-pointers)
15. [Stacks](#15-stacks)
16. [Monotonic Stack](#16-monotonic-stack)
17. [Heaps / Top K Elements](#17-heaps--top-k-elements)
18. [Backtracking](#18-backtracking)
19. [0/1 Knapsack](#19-01-knapsack)
20. [Topological Sort](#20-topological-sort)
21. [Union Find](#21-union-find)
22. [Kadane's Algorithm](#22-kadanes-algorithm)
23. [Recursion](#23-recursion)
24. [Divide & Conquer](#24-divide--conquer)

### Tier 3 — Important
25. [Two Heaps](#25-two-heaps)
26. [Intervals](#26-intervals)
27. [QuickSort / QuickSelect](#27-quicksort--quickselect)
28. [Tries](#28-tries)
29. [2D Grid DP](#29-2d-grid-dp)
30. [String DP](#30-string-dp)
31. [Queues / Monotonic Queue](#31-queues--monotonic-queue)
32. [BST / Ordered Set](#32-bst--ordered-set)
33. [Greedy](#33-greedy)
34. [Merge Sort](#34-merge-sort)

### Tier 4 — Good to Know
35. [Bit Manipulation](#35-bit-manipulation)
36. [Matrix](#36-matrix)
37. [K-Way Merge](#37-k-way-merge)
38. [Shortest Path](#38-shortest-path)
39. [Minimum Spanning Tree](#39-minimum-spanning-tree)
40. [LIS (Longest Increasing Subsequence)](#40-lis-longest-increasing-subsequence)
41. [Tree / Graph DP](#41-tree--graph-dp)
42. [Data Structure Design](#42-data-structure-design)
43. [Bitmask DP](#43-bitmask-dp)
44. [Bucket Sort](#44-bucket-sort)

### Tier 5 — Advanced / Rare
45. [Unbounded Knapsack](#45-unbounded-knapsack)
46. [State Machine DP](#46-state-machine-dp)
47. [Digit DP](#47-digit-dp)
48. [Probability DP](#48-probability-dp)
49. [BIT / Segment Tree](#49-bit--segment-tree)
50. [String Matching (KMP)](#50-string-matching-kmp)
51. [Line Sweep](#51-line-sweep)
52. [Maths / Geometry](#52-maths--geometry)
53. [Eulerian Circuit](#53-eulerian-circuit)

---

## 1. Two Pointers
**Tier 1**

### Pattern
Use two indices moving toward each other (or in the same direction) to reduce O(n²) brute force to O(n) on sorted arrays or strings.

### Java Skeleton
```java
int left = 0, right = n - 1;
while (left < right) {
    if (/* condition met */) {
        // record answer
        left++; right--;
    } else if (/* sum/value too small */) {
        left++;
    } else {
        right--;
    }
}
```

### Famous Problems

#### 1. Two Sum II — Input Array Is Sorted (LC 167)
> Read: 1 | Coded: 0

**Problem**: Given a 1-indexed array of integers numbers that is already sorted in non-decreasing order , find two numbers such that they add up to a specific target number. Let these two numbers be numbers[index 1 ] and numbers[index 2 ] where 1 <= index 1 < index 2 <= numbers.length . Return the indices of the two numbers index 1 and index 2 , each incremented by one, as an integer array [index 1 , index 2 ] of length 2. The tests are generated such that there is exactly one solution . You may not use the same element twice. Your solution must use only constant extra space.

**Approach**: Left pointer at start, right at end. If sum < target move left up, if sum > target move right down.
```java
public int[] twoSum(int[] numbers, int target) {
    int left = 0, right = numbers.length - 1;
    while (left < right) {
        int sum = numbers[left] + numbers[right];
        if (sum == target) return new int[]{left + 1, right + 1};
        else if (sum < target) left++;
        else right--;
    }
    return new int[]{-1, -1};
}
```

#### 2. Container With Most Water (LC 11)
> Read: 1 | Coded: 0

**Problem**: You are given an integer array height of length n . There are n vertical lines drawn such that the two endpoints of the i th line are (i, 0) and (i, height[i]) . Find two lines that together with the x-axis form a container, such that the container contains the most water. Return the maximum amount of water a container can store . Notice that you may not slant the container.

**Approach**: Move the pointer with the smaller height — it can only get worse by keeping it.
```java
public int maxArea(int[] height) {
    int left = 0, right = height.length - 1, max = 0;
    while (left < right) {
        max = Math.max(max, Math.min(height[left], height[right]) * (right - left));
        if (height[left] < height[right]) left++;
        else right--;
    }
    return max;
}
```

#### 3. 3Sum (LC 15)
> Read: 1 | Coded: 0

**Problem**: Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j , i != k , and j != k , and nums[i] + nums[j] + nums[k] == 0 . Notice that the solution set must not contain duplicate triplets.

**Approach**: Sort, fix one element, run two-pointer on the rest. Skip duplicates.
```java
public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> res = new ArrayList<>();
    for (int i = 0; i < nums.length - 2; i++) {
        if (i > 0 && nums[i] == nums[i - 1]) continue; // skip dup
        int left = i + 1, right = nums.length - 1;
        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];
            if (sum == 0) {
                res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                while (left < right && nums[left] == nums[left + 1]) left++;
                while (left < right && nums[right] == nums[right - 1]) right--;
                left++; right--;
            } else if (sum < 0) left++;
            else right--;
        }
    }
    return res;
}
```

---

## 2. Hash Tables
**Tier 1**

### Pattern
Use `HashMap`/`HashSet` for O(1) average lookup of complements, frequencies, or groupings.

### Java Skeleton
```java
Map<Key, Value> map = new HashMap<>();
for (int x : arr) {
    if (map.containsKey(complement(x))) {
        // found pair
    }
    map.put(x, map.getOrDefault(x, 0) + 1);
}
```

### Famous Problems

#### 1. Two Sum (LC 1)
> Read: 1 | Coded: 0

**Problem**: Given an array of integers nums and an integer target , return indices of the two numbers such that they add up to target . You may assume that each input would have exactly one solution , and you may not use the same element twice. You can return the answer in any order.

**Approach**: Store each number's index. For each number check if `target - num` exists.
```java
public int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) return new int[]{map.get(complement), i};
        map.put(nums[i], i);
    }
    return new int[]{};
}
```

#### 2. Group Anagrams (LC 49)
> Read: 1 | Coded: 0

**Problem**: Given an array of strings strs , group the anagrams together. You can return the answer in any order .

**Approach**: Use sorted string as key; group all anagrams together.
```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> map = new HashMap<>();
    for (String s : strs) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        String key = new String(chars);
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
    }
    return new ArrayList<>(map.values());
}
```

#### 3. Longest Consecutive Sequence (LC 128)
> Read: 1 | Coded: 0

**Problem**: Given an unsorted array of integers nums , return the length of the longest consecutive elements sequence. You must write an algorithm that runs in O(n) time.
##### Input: nums = [100,4,200,1,3,2]
##### Output : 4

**Approach**: Put all numbers in a HashSet. For each number that is a sequence start (num-1 not in set), count streak.
```java
public int longestConsecutive(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int n : nums) set.add(n);
    int longest = 0;
    for (int n : set) {
        if (!set.contains(n - 1)) { // start of sequence
            int length = 1;
            while (set.contains(n + length)) length++;
            longest = Math.max(longest, length);
        }
    }
    return longest;
}
```

---

## 3. Sliding Window — Fixed Size(using frequency array is good as adding and subtracting from it is O(1))
**Tier 1**

### Pattern
Window of exactly size `k`. Add the new right element, remove the element that fell out of the window.

### Java Skeleton
```java
// Initialize window of size k
for (int i = 0; i < k; i++) { /* add nums[i] to window */ }
// Slide
for (int i = k; i < n; i++) {
    /* add nums[i] to window */
    /* remove nums[i - k] from window */
    /* update answer */
}
```

### Famous Problems

#### 1. Find All Anagrams in a String (LC 438)
> Read: 1 | Coded: 0

**Problem**: Given two strings s and p , return an array of all the start indices of p 's anagrams in s . You may return the answer in any order .

**Approach**: Fixed window of size `p.length()`. Compare frequency arrays each step.
```java
public List<Integer> findAnagrams(String s, String p) {
    List<Integer> res = new ArrayList<>();
    if (s.length() < p.length()) return res;
    int[] pCount = new int[26], sCount = new int[26];
    for (char c : p.toCharArray()) pCount[c - 'a']++;
    for (int i = 0; i < p.length(); i++) sCount[s.charAt(i) - 'a']++;
    if (Arrays.equals(pCount, sCount)) res.add(0);
    for (int i = p.length(); i < s.length(); i++) {
        sCount[s.charAt(i) - 'a']++;
        sCount[s.charAt(i - p.length()) - 'a']--;
        if (Arrays.equals(pCount, sCount)) res.add(i - p.length() + 1);
    }
    return res;
}
```

#### 2. Maximum Average Subarray I (LC 643)
> Read: 1 | Coded: 0

**Problem**: You are given an integer array nums consisting of n elements, and an integer k . Find a contiguous subarray whose length is equal to k that has the maximum average value and return this value . Any answer with a calculation error less than 10 -5 will be accepted.

**Approach**: Track running sum, subtract element leaving window.
```java
public double findMaxAverage(int[] nums, int k) {
    double sum = 0;
    for (int i = 0; i < k; i++) sum += nums[i];
    double max = sum;
    for (int i = k; i < nums.length; i++) {
        sum += nums[i] - nums[i - k];
        max = Math.max(max, sum);
    }
    return max / k;
}
```

#### 3. Permutation in String (LC 567)
> Read: 1 | Coded: 0

**Problem**: Given two strings s1 and s2 , return true if s2 contains a permutation of s1 , or false otherwise. In other words, return true if one of s1 's permutations is the substring of s2 .

**Approach**: Fixed window of `s1.length()`. Track how many distinct characters have matching counts (`matches` counter).
```java
public boolean checkInclusion(String s1, String s2) {
    if (s1.length() > s2.length()) return false;
    int[] count = new int[26];
    for (char c : s1.toCharArray()) count[c - 'a']++;
    int need = (int) Arrays.stream(count).filter(x -> x > 0).count();
    int have = 0;
    int[] window = new int[26];
    for (int i = 0; i < s2.length(); i++) {
        int c = s2.charAt(i) - 'a';
        window[c]++;
        if (window[c] == count[c]) have++;
        if (i >= s1.length()) {
            int out = s2.charAt(i - s1.length()) - 'a';
            if (window[out] == count[out]) have--;
            window[out]--;
        }
        if (have == need) return true;
    }
    return false;
}
```

---

## 4. Sliding Window — Dynamic Size
**Tier 1**

### Pattern
Expand right pointer, shrink left when the window violates the constraint. Window size is variable.

### Java Skeleton
```java
int left = 0;
// window state (map, set, counts)
for (int right = 0; right < n; right++) {
    // add nums[right] to window state
    while (/* window invalid */) {
        // remove nums[left] from window state
        left++;
    }
    // update answer with window [left, right]
}
```

### Famous Problems

#### 1. Longest Substring Without Repeating Characters (LC 3)
> Read: 1 | Coded: 0

**Problem**: Given a string s , find the length of the longest substring without duplicate characters.

**Approach**: Shrink window whenever a duplicate character is added.
```java
public int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> map = new HashMap<>();
    int max = 0, left = 0;
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        if (map.containsKey(c) && map.get(c) >= left) {
            left = map.get(c) + 1;
        }
        map.put(c, right);
        max = Math.max(max, right - left + 1);
    }
    return max;
}
```

#### 2. Minimum Window Substring (LC 76)
> Read: 1 | Coded: 1

**Problem**: Given two strings s and t of lengths m and n respectively, return the minimum window substring of s such that every character in t ( including duplicates ) is included in the window . If there is no such substring, return the empty string "" . The testcases will be generated such that the answer is unique .

**Approach**: Expand until all chars covered, then shrink. Track `have` vs `need`. check my solution using sliding window and freq array
```java
public String minWindow(String s, String t) {
    Map<Character, Integer> need = new HashMap<>(), window = new HashMap<>();
    for (char c : t.toCharArray()) need.merge(c, 1, Integer::sum);
    int have = 0, total = need.size(), left = 0, minLen = Integer.MAX_VALUE, start = 0;
    for (int right = 0; right < s.length(); right++) {
        char c = s.charAt(right);
        window.merge(c, 1, Integer::sum);
        if (need.containsKey(c) && window.get(c).equals(need.get(c))) have++;
        while (have == total) {
            if (right - left + 1 < minLen) { minLen = right - left + 1; start = left; }
            char out = s.charAt(left++);
            window.merge(out, -1, Integer::sum);
            if (need.containsKey(out) && window.get(out) < need.get(out)) have--;
        }
    }
    return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);
}
```

#### 3. Longest Repeating Character Replacement (LC 424)
> Read: 1 | Coded: 0

**Problem**: You are given a string s and an integer k . You can choose any character of the string and change it to any other uppercase English character. You can perform this operation at most k times. Return the length of the longest substring containing the same letter you can get after performing the above operations .

**Approach**: Window is valid if `(windowSize - maxFreq) <= k`. Expand right; if invalid, slide left.
```java
public int characterReplacement(String s, int k) {
    int[] count = new int[26];
    int left = 0, maxFreq = 0, result = 0;
    for (int right = 0; right < s.length(); right++) {
        maxFreq = Math.max(maxFreq, ++count[s.charAt(right) - 'A']);
        while ((right - left + 1) - maxFreq > k) {
            count[s.charAt(left++) - 'A']--;
        }
        result = Math.max(result, right - left + 1);
    }
    return result;
}
```

---

## 5. Binary Search
**Tier 1**

### Pattern
Eliminate half the search space at each step. Works on any monotonic/sorted space. Key: define `lo`, `hi`, invariant, and what `mid` represents.

### Java Skeleton
```java
int lo = 0, hi = n - 1;
while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;
    if (feasible(mid)) {
        hi = mid - 1; // or answer = mid; hi = mid - 1
    } else {
        lo = mid + 1;
    }
}
return lo; // or answer
```

### Famous Problems

#### 1. Search in Rotated Sorted Array (LC 33)
> Read: 0 | Coded: 0

**Problem**: There is an integer array nums sorted in ascending order (with distinct values). Prior to being passed to your function, nums is possibly left rotated at an unknown index k ( 1 <= k < nums.length ) such that the resulting array is [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] ( 0-indexed ). For example, [0,1,2,4,5,6,7] might be left rotated by 3 indices and become [4,5,6,7,0,1,2] . Given the array nums after the possible rotation and an integer target , return the index of target if it is in nums , or -1 if it is not in nums . You must write an algorithm with O(log n) runtime complexity.

**Approach**: Determine which half is sorted; check if target lies in that half.
```java
public int search(int[] nums, int target) {
    int lo = 0, hi = nums.length - 1;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (nums[mid] == target) return mid;
        if (nums[lo] <= nums[mid]) { // left half sorted
            if (nums[lo] <= target && target < nums[mid]) hi = mid - 1;
            else lo = mid + 1;
        } else { // right half sorted
            if (nums[mid] < target && target <= nums[hi]) lo = mid + 1;
            else hi = mid - 1;
        }
    }
    return -1;
}
```

#### 2. Koko Eating Bananas (LC 875)
> Read: 0 | Coded: 0

**Problem**: Koko loves to eat bananas. There are n piles of bananas, the i th pile has piles[i] bananas. The guards have gone and will come back in h hours. Koko can decide her bananas-per-hour eating speed of k . Each hour, she chooses some pile of bananas and eats k bananas from that pile. If the pile has less than k bananas, she eats all of them instead and will not eat any more bananas during this hour. Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return. Return the minimum integer k such that she can eat all the bananas within h hours .

**Approach**: Binary search on answer (speed). Check if a given speed finishes all piles in `h` hours.
```java
public int minEatingSpeed(int[] piles, int h) {
    int lo = 1, hi = Arrays.stream(piles).max().getAsInt();
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        long hours = 0;
        for (int p : piles) hours += (p + mid - 1) / mid;
        if (hours <= h) hi = mid;
        else lo = mid + 1;
    }
    return lo;
}
```

#### 3. Find Minimum in Rotated Sorted Array (LC 153)
> Read: 0 | Coded: 0

**Problem**: Suppose an array of length n sorted in ascending order is rotated between 1 and n times. For example, the array nums = [0,1,2,4,5,6,7] might become: [4,5,6,7,0,1,2] if it was rotated 4 times. [0,1,2,4,5,6,7] if it was rotated 7 times. Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the array [a[n-1], a[0], a[1], a[2], ..., a[n-2]] . Given the sorted rotated array nums of unique elements, return the minimum element of this array . You must write an algorithm that runs in O(log n) time .

**Approach**: Minimum is in the unsorted half. If `nums[mid] > nums[hi]`, min is to the right.
```java
public int findMin(int[] nums) {
    int lo = 0, hi = nums.length - 1;
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (nums[mid] > nums[hi]) lo = mid + 1;
        else hi = mid;
    }
    return nums[lo];
}
```

---

## 6. Strings
**Tier 1**

### Pattern
Frequency maps for anagram/permutation checks; expand-around-center for palindromes; encoding with length prefixes.

### Java Skeleton
```java
// Frequency map
int[] freq = new int[26];
for (char c : s.toCharArray()) freq[c - 'a']++;

// Expand around center for palindrome
for (int i = 0; i < s.length(); i++) {
    expandAroundCenter(s, i, i);     // odd length
    expandAroundCenter(s, i, i + 1); // even length
}
```

### Famous Problems

#### 1. Valid Anagram (LC 242)
> Read: 0 | Coded: 0

**Problem**: Given two strings s and t , return true if t is an anagram of s , and false otherwise.

**Approach**: Count character frequencies; both strings must have identical counts.
```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;
    int[] count = new int[26];
    for (char c : s.toCharArray()) count[c - 'a']++;
    for (char c : t.toCharArray()) {
        if (--count[c - 'a'] < 0) return false;
    }
    return true;
}
```

#### 2. Longest Palindromic Substring (LC 5)
> Read: 0 | Coded: 0

**Problem**: Given a string s , return the longest palindromic substring in s .

**Approach**: Expand around every center (n odd + n-1 even centers).
```java
public String longestPalindrome(String s) {
    int start = 0, maxLen = 1;
    for (int i = 0; i < s.length(); i++) {
        for (int[] c : new int[][]{{i, i}, {i, i + 1}}) {
            int lo = c[0], hi = c[1];
            while (lo >= 0 && hi < s.length() && s.charAt(lo) == s.charAt(hi)) {
                if (hi - lo + 1 > maxLen) { maxLen = hi - lo + 1; start = lo; }
                lo--; hi++;
            }
        }
    }
    return s.substring(start, start + maxLen);
}
```

#### 3. Encode and Decode Strings (LC 271)
> Read: 0 | Coded: 0

**Problem**: Design an algorithm to encode a list of strings to a single string. The encoded string is then sent over the network and is decoded back to the original list of strings. Implement encode and decode methods.

**Approach**: Prefix each string with its length and a delimiter: `"4#word"`.
```java
public String encode(List<String> strs) {
    StringBuilder sb = new StringBuilder();
    for (String s : strs) sb.append(s.length()).append('#').append(s);
    return sb.toString();
}
public List<String> decode(String s) {
    List<String> res = new ArrayList<>();
    int i = 0;
    while (i < s.length()) {
        int j = s.indexOf('#', i);
        int len = Integer.parseInt(s.substring(i, j));
        res.add(s.substring(j + 1, j + 1 + len));
        i = j + 1 + len;
    }
    return res;
}
```

---

## 7. Tree DFS (Pre/In/Post Order)
**Tier 1**

### Pattern
Recursive traversal. Pre-order: process before children. In-order: process between children (sorted for BST). Post-order: process after children (needed when result depends on subtree).

### Java Skeleton
```java
ReturnType dfs(TreeNode node) {
    if (node == null) return baseValue;
    ReturnType left = dfs(node.left);   // post: compute children first
    ReturnType right = dfs(node.right);
    // combine left, right, node.val
    return result;
}
```

### Famous Problems

#### 1. Invert Binary Tree (LC 226)
> Read: 0 | Coded: 0

**Problem**: Given the root of a binary tree, invert the tree, and return its root .

**Approach**: Post-order. Recursively invert subtrees, then swap children.
```java
public TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    TreeNode left = invertTree(root.left);
    TreeNode right = invertTree(root.right);
    root.left = right;
    root.right = left;
    return root;
}
```

#### 2. Diameter of Binary Tree (LC 543)
> Read: 0 | Coded: 0

**Problem**: Given the root of a binary tree, return the length of the diameter of the tree . The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root . The length of a path between two nodes is represented by the number of edges between them.

**Approach**: At each node, diameter through it = leftHeight + rightHeight. Track global max.
```java
int maxDiameter = 0;
public int diameterOfBinaryTree(TreeNode root) {
    height(root);
    return maxDiameter;
}
private int height(TreeNode node) {
    if (node == null) return 0;
    int left = height(node.left), right = height(node.right);
    maxDiameter = Math.max(maxDiameter, left + right);
    return 1 + Math.max(left, right);
}
```

#### 3. Path Sum II (LC 113)
> Read: 0 | Coded: 0

**Problem**: Given the root of a binary tree and an integer targetSum , return all root-to-leaf paths where the sum of the node values in the path equals targetSum . Each path should be returned as a list of the node values , not node references . A root-to-leaf path is a path starting from the root and ending at any leaf node. A leaf is a node with no children.

**Approach**: Pre-order DFS with backtracking. Add node to path, recurse, remove on return.
```java
public List<List<Integer>> pathSum(TreeNode root, int target) {
    List<List<Integer>> res = new ArrayList<>();
    dfs(root, target, new ArrayList<>(), res);
    return res;
}
private void dfs(TreeNode node, int remaining, List<Integer> path, List<List<Integer>> res) {
    if (node == null) return;
    path.add(node.val);
    if (node.left == null && node.right == null && remaining == node.val)
        res.add(new ArrayList<>(path));
    dfs(node.left, remaining - node.val, path, res);
    dfs(node.right, remaining - node.val, path, res);
    path.remove(path.size() - 1); // backtrack
}
```

---

## 8. Tree BFS (Level Order)
**Tier 1**

### Pattern
Queue-based level-by-level traversal. Capture `queue.size()` at the start of each level to process exactly that many nodes.

### Java Skeleton
```java
Queue<TreeNode> q = new LinkedList<>();
if (root != null) q.offer(root);
while (!q.isEmpty()) {
    int size = q.size(); // nodes in this level
    for (int i = 0; i < size; i++) {
        TreeNode node = q.poll();
        // process node
        if (node.left != null) q.offer(node.left);
        if (node.right != null) q.offer(node.right);
    }
    // level complete
}
```

### Famous Problems

#### 1. Binary Tree Level Order Traversal (LC 102)
> Read: 0 | Coded: 0

**Problem**: Given the root of a binary tree, return the level order traversal of its nodes' values . (i.e., from left to right, level by level).

**Approach**: Collect node values per level into a list.
```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);
    while (!q.isEmpty()) {
        List<Integer> level = new ArrayList<>();
        for (int i = q.size(); i > 0; i--) {
            TreeNode node = q.poll();
            level.add(node.val);
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
        res.add(level);
    }
    return res;
}
```

#### 2. Binary Tree Right Side View (LC 199)
> Read: 0 | Coded: 0

**Problem**: Given the root of a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom .

**Approach**: Last node processed in each level is the rightmost visible node.
```java
public List<Integer> rightSideView(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) return res;
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);
    while (!q.isEmpty()) {
        TreeNode last = null;
        for (int i = q.size(); i > 0; i--) {
            last = q.poll();
            if (last.left != null) q.offer(last.left);
            if (last.right != null) q.offer(last.right);
        }
        res.add(last.val);
    }
    return res;
}
```

#### 3. Binary Tree Zigzag Level Order Traversal (LC 103)
> Read: 0 | Coded: 0

**Problem**: Given the root of a binary tree, return the zigzag level order traversal of its nodes' values . (i.e., from left to right, then right to left for the next level and alternate between).

**Approach**: Alternate direction each level using a `leftToRight` flag.
```java
public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);
    boolean leftToRight = true;
    while (!q.isEmpty()) {
        LinkedList<Integer> level = new LinkedList<>();
        for (int i = q.size(); i > 0; i--) {
            TreeNode node = q.poll();
            if (leftToRight) level.addLast(node.val);
            else level.addFirst(node.val);
            if (node.left != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
        res.add(level);
        leftToRight = !leftToRight;
    }
    return res;
}
```

---

## 9. 1-D DP
**Tier 1**

### Pattern
`dp[i]` = answer for subproblem of size `i`. Recurrence derived from smaller subproblems. Often space-optimizable to O(1) by keeping only last 1-2 values.

### Java Skeleton
```java
int[] dp = new int[n + 1];
dp[0] = /* base case */;
for (int i = 1; i <= n; i++) {
    dp[i] = /* recurrence using dp[i-1], dp[i-2], ... */;
}
return dp[n];
```

### Famous Problems

#### 1. Climbing Stairs (LC 70)
> Read: 0 | Coded: 0

**Problem**: You are climbing a staircase. It takes n steps to reach the top. Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

**Approach**: `dp[i] = dp[i-1] + dp[i-2]` (can reach i from i-1 or i-2 steps).
```java
public int climbStairs(int n) {
    if (n <= 2) return n;
    int prev2 = 1, prev1 = 2;
    for (int i = 3; i <= n; i++) {
        int curr = prev1 + prev2;
        prev2 = prev1;
        prev1 = curr;
    }
    return prev1;
}
```

#### 2. House Robber (LC 198)
> Read: 0 | Coded: 0

**Problem**: You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected and it will automatically contact the police if two adjacent houses were broken into on the same night . Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police .

**Approach**: `dp[i] = max(dp[i-1], dp[i-2] + nums[i])` — rob this house or skip it.
```java
public int rob(int[] nums) {
    int prev2 = 0, prev1 = 0;
    for (int num : nums) {
        int curr = Math.max(prev1, prev2 + num);
        prev2 = prev1;
        prev1 = curr;
    }
    return prev1;
}
```

#### 3. Decode Ways (LC 91)
> Read: 0 | Coded: 0

**Problem**: You have intercepted a secret message encoded as a string of numbers. The message is decoded via the following mapping: "1" -> 'A' "2" -> 'B' ... "25" -> 'Y' "26" -> 'Z' However, while decoding the message, you realize that there are many different ways you can decode the message because some codes are contained in other codes ( "2" and "5" vs "25" ). For example, "11106" can be decoded into: "AAJF" with the grouping (1, 1, 10, 6) "KJF" with the grouping (11, 10, 6) The grouping (1, 11, 06) is invalid because "06" is not a valid code (only "6" is valid).

**Approach**: `dp[i]` = ways to decode `s[0..i-1]`. Single digit valid if != '0'; double digit valid if 10-26.
```java
public int numDecodings(String s) {
    int n = s.length();
    int[] dp = new int[n + 1];
    dp[0] = 1;
    dp[1] = s.charAt(0) == '0' ? 0 : 1;
    for (int i = 2; i <= n; i++) {
        int ones = s.charAt(i - 1) - '0';
        int tens = Integer.parseInt(s.substring(i - 2, i));
        if (ones >= 1) dp[i] += dp[i - 1];
        if (tens >= 10 && tens <= 26) dp[i] += dp[i - 2];
    }
    return dp[n];
}
```

---

## 10. Graph DFS
**Tier 1**

### Pattern
Mark node visited, recurse on unvisited neighbors. Use recursion stack or explicit stack. Good for: connected components, path existence, flood fill.

### Java Skeleton
```java
boolean[] visited = new boolean[n];
void dfs(int node, List<List<Integer>> graph) {
    visited[node] = true;
    for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) dfs(neighbor, graph);
    }
}
// For grid:
void dfs(int[][] grid, int r, int c) {
    if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] != 1) return;
    grid[r][c] = 0; // mark visited by sinking
    dfs(grid, r+1, c); dfs(grid, r-1, c);
    dfs(grid, r, c+1); dfs(grid, r, c-1);
}
```

### Famous Problems

#### 1. Number of Islands (LC 200)
> Read: 0 | Coded: 0

**Problem**: Given an m x n 2D binary grid grid which represents a map of '1' s (land) and '0' s (water), return the number of islands . An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

**Approach**: For each unvisited '1', increment count and DFS to sink the entire island.
```java
public int numIslands(char[][] grid) {
    int count = 0;
    for (int r = 0; r < grid.length; r++)
        for (int c = 0; c < grid[0].length; c++)
            if (grid[r][c] == '1') { dfs(grid, r, c); count++; }
    return count;
}
private void dfs(char[][] grid, int r, int c) {
    if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] != '1') return;
    grid[r][c] = '0';
    dfs(grid, r+1, c); dfs(grid, r-1, c); dfs(grid, r, c+1); dfs(grid, r, c-1);
}
```

#### 2. Pacific Atlantic Water Flow (LC 417)
> Read: 0 | Coded: 0

**Problem**: There is an m x n rectangular island that borders both the Pacific Ocean and Atlantic Ocean . The Pacific Ocean touches the island's left and top edges, and the Atlantic Ocean touches the island's right and bottom edges. The island is partitioned into a grid of square cells. You are given an m x n integer matrix heights where heights[r][c] represents the height above sea level of the cell at coordinate (r, c) . The island receives a lot of rain, and the rain water can flow to neighboring cells directly north, south, east, and west if the neighboring cell's height is less than or equal to the current cell's height. Water can flow from any cell adjacent to an ocean into the ocean. Return a 2D list of grid coordinates result where result[i] = [r i , c i ] denotes that rain water can flow from cell (r i , c i ) to both the Pacific and Atlantic oceans .

**Approach**: Reverse flow — DFS inward from both coastlines. Cell in both sets can flow to both.
```java
public List<List<Integer>> pacificAtlantic(int[][] heights) {
    int m = heights.length, n = heights[0].length;
    boolean[][] pac = new boolean[m][n], atl = new boolean[m][n];
    for (int r = 0; r < m; r++) { dfs(heights, pac, r, 0); dfs(heights, atl, r, n-1); }
    for (int c = 0; c < n; c++) { dfs(heights, pac, 0, c); dfs(heights, atl, m-1, c); }
    List<List<Integer>> res = new ArrayList<>();
    for (int r = 0; r < m; r++)
        for (int c = 0; c < n; c++)
            if (pac[r][c] && atl[r][c]) res.add(Arrays.asList(r, c));
    return res;
}
private void dfs(int[][] h, boolean[][] visited, int r, int c) {
    visited[r][c] = true;
    int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
    for (int[] d : dirs) {
        int nr = r+d[0], nc = c+d[1];
        if (nr>=0&&nr<h.length&&nc>=0&&nc<h[0].length&&!visited[nr][nc]&&h[nr][nc]>=h[r][c])
            dfs(h, visited, nr, nc);
    }
}
```

#### 3. Clone Graph (LC 133)
> Read: 0 | Coded: 0

**Problem**: Given a reference of a node in a connected undirected graph. Return a deep copy (clone) of the graph. Each node in the graph contains a value ( int ) and a list ( List[Node] ) of its neighbors. class Node { public int val; public List<Node> neighbors; } Test case format: For simplicity, each node's value is the same as the node's index (1-indexed). For example, the first node with val == 1 , the second node with val == 2 , and so on. The graph is represented in the test case using an adjacency list. An adjacency list is a collection of unordered lists used to represent a finite graph. Each list describes the set of neighbors of a node in the graph. The given node will always be the first node with val = 1 . You must return the copy of the given node as a reference to the cloned graph.

**Approach**: DFS with a HashMap from original node to its clone to handle cycles.
```java
Map<Node, Node> map = new HashMap<>();
public Node cloneGraph(Node node) {
    if (node == null) return null;
    if (map.containsKey(node)) return map.get(node);
    Node clone = new Node(node.val);
    map.put(node, clone);
    for (Node neighbor : node.neighbors)
        clone.neighbors.add(cloneGraph(neighbor));
    return clone;
}
```

---

## 11. Graph BFS
**Tier 1**

### Pattern
Queue-based shortest path in unweighted graphs. Guaranteed to reach nodes in order of increasing distance. Multi-source BFS: seed queue with all sources simultaneously.

### Java Skeleton
```java
Queue<int[]> q = new LinkedList<>();
boolean[][] visited = new boolean[m][n];
q.offer(new int[]{startR, startC});
visited[startR][startC] = true;
int dist = 0;
int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
while (!q.isEmpty()) {
    for (int size = q.size(); size > 0; size--) {
        int[] curr = q.poll();
        for (int[] d : dirs) {
            int nr = curr[0]+d[0], nc = curr[1]+d[1];
            if (nr>=0&&nr<m&&nc>=0&&nc<n&&!visited[nr][nc]) {
                visited[nr][nc] = true;
                q.offer(new int[]{nr, nc});
            }
        }
    }
    dist++;
}
```

### Famous Problems

#### 1. Rotting Oranges (LC 994)
> Read: 0 | Coded: 0

**Problem**: You are given an m x n grid where each cell can have one of three values: 0 representing an empty cell, 1 representing a fresh orange, or 2 representing a rotten orange. Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten. Return the minimum number of minutes that must elapse until no cell has a fresh orange . If this is impossible, return -1 .

**Approach**: Multi-source BFS from all initially rotten oranges simultaneously.
```java
public int orangesRotting(int[][] grid) {
    Queue<int[]> q = new LinkedList<>();
    int fresh = 0;
    for (int r = 0; r < grid.length; r++)
        for (int c = 0; c < grid[0].length; c++) {
            if (grid[r][c] == 2) q.offer(new int[]{r, c});
            else if (grid[r][c] == 1) fresh++;
        }
    if (fresh == 0) return 0;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    int minutes = 0;
    while (!q.isEmpty() && fresh > 0) {
        minutes++;
        for (int size = q.size(); size > 0; size--) {
            int[] curr = q.poll();
            for (int[] d : dirs) {
                int nr = curr[0]+d[0], nc = curr[1]+d[1];
                if (nr>=0&&nr<grid.length&&nc>=0&&nc<grid[0].length&&grid[nr][nc]==1) {
                    grid[nr][nc] = 2; fresh--; q.offer(new int[]{nr, nc});
                }
            }
        }
    }
    return fresh == 0 ? minutes : -1;
}
```

#### 2. Word Ladder (LC 127)
> Read: 0 | Coded: 0

**Problem**: A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s 1 -> s 2 -> ... -> s k such that: Every adjacent pair of words differs by a single letter. Every s i for 1 <= i <= k is in wordList . Note that beginWord does not need to be in wordList . s k == endWord Given two words, beginWord and endWord , and a dictionary wordList , return the number of words in the shortest transformation sequence from beginWord to endWord , or 0 if no such sequence exists.

**Approach**: BFS on word transformations; each step changes one letter. Use set for O(1) lookup.
```java
public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord)) return 0;
    Queue<String> q = new LinkedList<>();
    q.offer(beginWord);
    int steps = 1;
    while (!q.isEmpty()) {
        for (int size = q.size(); size > 0; size--) {
            char[] word = q.poll().toCharArray();
            for (int i = 0; i < word.length; i++) {
                char orig = word[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    word[i] = c;
                    String next = new String(word);
                    if (next.equals(endWord)) return steps + 1;
                    if (wordSet.remove(next)) q.offer(next);
                }
                word[i] = orig;
            }
        }
        steps++;
    }
    return 0;
}
```

#### 3. Shortest Path in Binary Matrix (LC 1091)
> Read: 0 | Coded: 0

**Problem**: Given an n x n binary matrix grid , return the length of the shortest clear path in the matrix . If there is no clear path, return -1 . A clear path in a binary matrix is a path from the top-left cell (i.e., (0, 0) ) to the bottom-right cell (i.e., (n - 1, n - 1) ) such that: All the visited cells of the path are 0 . All the adjacent cells of the path are 8-directionally connected (i.e., they are different and they share an edge or a corner). The length of a clear path is the number of visited cells of this path.

**Approach**: BFS with 8 directions. First time reaching bottom-right is shortest path.
```java
public int shortestPathBinaryMatrix(int[][] grid) {
    int n = grid.length;
    if (grid[0][0] == 1 || grid[n-1][n-1] == 1) return -1;
    Queue<int[]> q = new LinkedList<>();
    q.offer(new int[]{0, 0, 1});
    grid[0][0] = 1;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{1,-1},{-1,1},{-1,-1}};
    while (!q.isEmpty()) {
        int[] curr = q.poll();
        if (curr[0] == n-1 && curr[1] == n-1) return curr[2];
        for (int[] d : dirs) {
            int nr = curr[0]+d[0], nc = curr[1]+d[1];
            if (nr>=0&&nr<n&&nc>=0&&nc<n&&grid[nr][nc]==0) {
                grid[nr][nc] = 1;
                q.offer(new int[]{nr, nc, curr[2]+1});
            }
        }
    }
    return -1;
}
```

---

## 12. Prefix Sum
**Tier 1**

### Pattern
`prefix[i] = prefix[i-1] + nums[i-1]`. Range sum `[l, r]` = `prefix[r+1] - prefix[l]` in O(1).

### Java Skeleton
```java
int[] prefix = new int[n + 1];
for (int i = 0; i < n; i++) prefix[i + 1] = prefix[i] + nums[i];
// query [l, r] (0-indexed):
int rangeSum = prefix[r + 1] - prefix[l];
```

### Famous Problems

#### 1. Range Sum Query — Immutable (LC 303)
> Read: 0 | Coded: 0

**Problem**: Given an integer array nums , handle multiple queries of the following type: Calculate the sum of the elements of nums between indices left and right inclusive where left <= right . Implement the NumArray class: NumArray(int[] nums) Initializes the object with the integer array nums . int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right] ).

**Approach**: Build prefix array once. Each query is O(1).
```java
class NumArray {
    int[] prefix;
    public NumArray(int[] nums) {
        prefix = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) prefix[i+1] = prefix[i] + nums[i];
    }
    public int sumRange(int left, int right) {
        return prefix[right + 1] - prefix[left];
    }
}
```

#### 2. Subarray Sum Equals K (LC 560)
> Read: 0 | Coded: 0

**Problem**: Given an array of integers nums and an integer k , return the total number of subarrays whose sum equals to k . A subarray is a contiguous non-empty sequence of elements within an array.

**Approach**: For each index, check if `prefix[i] - k` was seen before. HashMap stores prefix sums → count.
```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);
    int sum = 0, count = 0;
    for (int num : nums) {
        sum += num;
        count += map.getOrDefault(sum - k, 0);
        map.merge(sum, 1, Integer::sum);
    }
    return count;
}
```

#### 3. Product of Array Except Self (LC 238)
> Read: 0 | Coded: 0

**Problem**: Given an integer array nums , return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i] . The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer. You must write an algorithm that runs in O(n) time and without using the division operation.

**Approach**: Left pass builds prefix products; right pass multiplies suffix products in-place.
```java
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    result[0] = 1;
    for (int i = 1; i < n; i++) result[i] = result[i-1] * nums[i-1];
    int right = 1;
    for (int i = n-1; i >= 0; i--) {
        result[i] *= right;
        right *= nums[i];
    }
    return result;
}
```

---

## 13. Linked List + In-place Reversal
**Tier 2**

### Pattern
Three pointers: `prev`, `curr`, `next`. Redirect `curr.next` to `prev`, advance both.

### Java Skeleton
```java
ListNode prev = null, curr = head;
while (curr != null) {
    ListNode next = curr.next;
    curr.next = prev;
    prev = curr;
    curr = next;
}
return prev; // new head
```

### Famous Problems

#### 1. Reverse Linked List (LC 206)
> Read: 0 | Coded: 0

**Problem**: Given the head of a singly linked list, reverse the list, and return the reversed list .

**Approach**: Iterative three-pointer reversal.
```java
public ListNode reverseList(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
```

#### 2. Reverse Nodes in k-Group (LC 25)
> Read: 0 | Coded: 0

**Problem**: Given the head of a linked list, reverse the nodes of the list k at a time, and return the modified list . k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is. You may not alter the values in the list's nodes, only nodes themselves may be changed.

**Approach**: Check if k nodes remain, reverse them, recurse on remainder.
```java
public ListNode reverseKGroup(ListNode head, int k) {
    ListNode check = head;
    for (int i = 0; i < k; i++) {
        if (check == null) return head; // fewer than k nodes
        check = check.next;
    }
    ListNode prev = null, curr = head;
    for (int i = 0; i < k; i++) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    head.next = reverseKGroup(curr, k); // head is now tail of reversed group
    return prev; // new head
}
```

#### 3. Palindrome Linked List (LC 234)
> Read: 0 | Coded: 0

**Problem**: Given the head of a singly linked list, return true if it is a palindrome or false otherwise .

**Approach**: Find middle (fast/slow), reverse second half, compare with first half.
```java
public boolean isPalindrome(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; }
    // reverse second half
    ListNode prev = null, curr = slow;
    while (curr != null) { ListNode next = curr.next; curr.next = prev; prev = curr; curr = next; }
    // compare
    ListNode left = head, right = prev;
    while (right != null) {
        if (left.val != right.val) return false;
        left = left.next; right = right.next;
    }
    return true;
}
```

---

## 14. Fast & Slow Pointers
**Tier 2**

### Pattern
`slow` moves 1 step, `fast` moves 2 steps. When they meet, a cycle exists. After meeting, reset one to head — they meet again at cycle entry.

### Java Skeleton
```java
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
    if (slow == fast) { /* cycle detected */ break; }
}
```

### Famous Problems

#### 1. Linked List Cycle II (LC 142)
> Read: 0 | Coded: 0

**Problem**: Given the head of a linked list, return the node where the cycle begins. If there is no cycle, return null . There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is connected to ( 0-indexed ). It is -1 if there is no cycle. Note that pos is not passed as a parameter . Do not modify the linked list.

**Approach**: Detect cycle with fast/slow. Reset slow to head; advance both one step — they meet at cycle entry.
```java
public ListNode detectCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next; fast = fast.next.next;
        if (slow == fast) {
            slow = head;
            while (slow != fast) { slow = slow.next; fast = fast.next; }
            return slow;
        }
    }
    return null;
}
```

#### 2. Middle of the Linked List (LC 876)
> Read: 0 | Coded: 0

**Problem**: Given the head of a singly linked list, return the middle node of the linked list . If there are two middle nodes, return the second middle node.

**Approach**: When fast reaches end, slow is at the middle.
```java
public ListNode middleNode(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    return slow;
}
```

#### 3. Happy Number (LC 202)
> Read: 0 | Coded: 0

**Problem**: Write an algorithm to determine if a number n is happy. A happy number is a number defined by the following process: Starting with any positive integer, replace the number by the sum of the squares of its digits. Repeat the process until the number equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1. Those numbers for which this process ends in 1 are happy. Return true if n is a happy number, and false if not .

**Approach**: Model digit-square-sum as a linked list; cycle detection → not happy. If slow == 1 → happy.
```java
public boolean isHappy(int n) {
    int slow = n, fast = squareSum(n);
    while (fast != 1 && slow != fast) {
        slow = squareSum(slow);
        fast = squareSum(squareSum(fast));
    }
    return fast == 1;
}
private int squareSum(int n) {
    int sum = 0;
    while (n > 0) { int d = n % 10; sum += d * d; n /= 10; }
    return sum;
}
```

---

## 15. Stacks
**Tier 2**

### Pattern
LIFO. Use for matching brackets, undo operations, history, or deferred processing.

### Java Skeleton
```java
Deque<Integer> stack = new ArrayDeque<>();
for (int x : arr) {
    if (!stack.isEmpty() && canProcess(stack.peek(), x)) {
        stack.pop(); // consume top
    } else {
        stack.push(x);
    }
}
```

### Famous Problems

#### 1. Valid Parentheses (LC 20)
> Read: 0 | Coded: 0

**Problem**: Given a string s containing just the characters '(' , ')' , '{' , '}' , '[' and ']' , determine if the input string is valid. An input string is valid if: Open brackets must be closed by the same type of brackets. Open brackets must be closed in the correct order. Every close bracket has a corresponding open bracket of the same type.

**Approach**: Push open brackets; on close bracket, pop and verify match.
```java
public boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    for (char c : s.toCharArray()) {
        if (c == '(' || c == '[' || c == '{') stack.push(c);
        else {
            if (stack.isEmpty()) return false;
            char top = stack.pop();
            if (c == ')' && top != '(') return false;
            if (c == ']' && top != '[') return false;
            if (c == '}' && top != '{') return false;
        }
    }
    return stack.isEmpty();
}
```

#### 2. Min Stack (LC 155)
> Read: 0 | Coded: 0

**Problem**: Design a stack that supports push, pop, top, and retrieving the minimum element in constant time. Implement the MinStack class: MinStack() initializes the stack object. void push(int val) pushes the element val onto the stack. void pop() removes the element on the top of the stack. int top() gets the top element of the stack. int getMin() retrieves the minimum element in the stack. You must implement a solution with O(1) time complexity for each function.

**Approach**: Auxiliary stack that tracks the minimum at each level.
```java
class MinStack {
    Deque<Integer> stack = new ArrayDeque<>(), minStack = new ArrayDeque<>();
    public void push(int val) {
        stack.push(val);
        minStack.push(minStack.isEmpty() ? val : Math.min(val, minStack.peek()));
    }
    public void pop() { stack.pop(); minStack.pop(); }
    public int top() { return stack.peek(); }
    public int getMin() { return minStack.peek(); }
}
```

#### 3. Evaluate Reverse Polish Notation (LC 150)
> Read: 0 | Coded: 0

**Problem**: You are given an array of strings tokens that represents an arithmetic expression in a Reverse Polish Notation . Evaluate the expression. Return an integer that represents the value of the expression . Note that: The valid operators are '+' , '-' , '*' , and '/' . Each operand may be an integer or another expression. The division between two integers always truncates toward zero . There will not be any division by zero. The input represents a valid arithmetic expression in a reverse polish notation. The answer and all the intermediate calculations can be represented in a 32-bit integer.

**Approach**: Push numbers; on operator, pop two operands and push result.
```java
public int evalRPN(String[] tokens) {
    Deque<Integer> stack = new ArrayDeque<>();
    for (String t : tokens) {
        if ("+-*/".contains(t)) {
            int b = stack.pop(), a = stack.pop();
            switch (t) {
                case "+": stack.push(a + b); break;
                case "-": stack.push(a - b); break;
                case "*": stack.push(a * b); break;
                case "/": stack.push(a / b); break;
            }
        } else stack.push(Integer.parseInt(t));
    }
    return stack.pop();
}
```

---

## 16. Monotonic Stack
**Tier 2**

### Pattern
Maintain a stack in strictly increasing or decreasing order. Pop when the invariant is violated — the popped element's "next greater/smaller" is the current element.

### Java Skeleton
```java
Deque<Integer> stack = new ArrayDeque<>(); // stores indices
for (int i = 0; i < n; i++) {
    // Decreasing stack (for next greater element):
    while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
        int idx = stack.pop();
        result[idx] = nums[i]; // nums[i] is the next greater for idx
    }
    stack.push(i);
}
```

### Famous Problems

#### 1. Daily Temperatures (LC 739)
> Read: 0 | Coded: 0

**Problem**: Given an array of integers temperatures represents the daily temperatures, return an array answer such that answer[i] is the number of days you have to wait after the i th day to get a warmer temperature . If there is no future day for which this is possible, keep answer[i] == 0 instead.

**Approach**: Decreasing monotonic stack of indices. When a warmer day is found, pop and record the gap.
```java
public int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] result = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
            int idx = stack.pop();
            result[idx] = i - idx;
        }
        stack.push(i);
    }
    return result;
}
```

#### 2. Largest Rectangle in Histogram (LC 84)
> Read: 0 | Coded: 0

**Problem**: Given an array of integers heights representing the histogram's bar height where the width of each bar is 1 , return the area of the largest rectangle in the histogram .

**Approach**: Increasing stack. Pop when shorter bar found; width = current index − stack top − 1.
```java
public int largestRectangleArea(int[] heights) {
    Deque<Integer> stack = new ArrayDeque<>();
    int max = 0;
    int[] h = Arrays.copyOf(heights, heights.length + 1); // sentinel 0
    for (int i = 0; i < h.length; i++) {
        while (!stack.isEmpty() && h[stack.peek()] > h[i]) {
            int height = h[stack.pop()];
            int width = stack.isEmpty() ? i : i - stack.peek() - 1;
            max = Math.max(max, height * width);
        }
        stack.push(i);
    }
    return max;
}
```

#### 3. Next Greater Element II (LC 503)
> Read: 0 | Coded: 0

**Problem**: Given a circular integer array nums (i.e., the next element of nums[nums.length - 1] is nums[0] ), return the next greater number for every element in nums . The next greater number of a number x is the first greater number to its traversing-order next in the array, which means you could search circularly to find its next greater number. If it doesn't exist, return -1 for this number.

**Approach**: Traverse array twice (circular). Decreasing stack; index `% n` for wraparound.
```java
public int[] nextGreaterElements(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < 2 * n; i++) {
        while (!stack.isEmpty() && nums[stack.peek()] < nums[i % n]) {
            result[stack.pop()] = nums[i % n];
        }
        if (i < n) stack.push(i);
    }
    return result;
}
```

---

## 17. Heaps / Top K Elements
**Tier 2**

### Pattern
Min-heap of size k keeps the k largest elements (heap top = kth largest). Max-heap keeps the k smallest.

### Java Skeleton
```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
for (int num : nums) {
    minHeap.offer(num);
    if (minHeap.size() > k) minHeap.poll(); // evict smallest
}
// minHeap contains k largest elements; peek() = kth largest
```

### Famous Problems

#### 1. Top K Frequent Elements (LC 347)
> Read: 0 | Coded: 0

**Problem**: Given an integer array nums and an integer k , return the k most frequent elements . You may return the answer in any order .

**Approach**: Count frequencies, then min-heap of size k keyed by frequency.
```java
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int n : nums) freq.merge(n, 1, Integer::sum);
    PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(freq::get));
    for (int n : freq.keySet()) {
        pq.offer(n);
        if (pq.size() > k) pq.poll();
    }
    int[] res = new int[k];
    for (int i = k - 1; i >= 0; i--) res[i] = pq.poll();
    return res;
}
```

#### 2. K Closest Points to Origin (LC 973)
> Read: 0 | Coded: 0

**Problem**: Given an array of points where points[i] = [x i , y i ] represents a point on the X-Y plane and an integer k , return the k closest points to the origin (0, 0) . The distance between two points on the X-Y plane is the Euclidean distance (i.e., √(x 1 - x 2 ) 2 + (y 1 - y 2 ) 2 ). You may return the answer in any order . The answer is guaranteed to be unique (except for the order that it is in).

**Approach**: Max-heap of size k by distance; evict farthest when over k.
```java
public int[][] kClosest(int[][] points, int k) {
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) ->
        (b[0]*b[0]+b[1]*b[1]) - (a[0]*a[0]+a[1]*a[1])); // max-heap by dist
    for (int[] p : points) {
        pq.offer(p);
        if (pq.size() > k) pq.poll();
    }
    return pq.toArray(new int[k][]);
}
```

#### 3. Reorganize String (LC 767)
> Read: 0 | Coded: 0

**Problem**: Given a string s , rearrange the characters of s so that any two adjacent characters are not the same. Return any possible rearrangement of s or return "" if not possible .

**Approach**: Max-heap by frequency. Greedily pick the most frequent char that differs from the previous.
```java
public String reorganizeString(String s) {
    int[] freq = new int[26];
    for (char c : s.toCharArray()) freq[c-'a']++;
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> b[1]-a[1]); // max-heap
    for (int i = 0; i < 26; i++) if (freq[i] > 0) pq.offer(new int[]{i, freq[i]});
    StringBuilder sb = new StringBuilder();
    while (pq.size() >= 2) {
        int[] first = pq.poll(), second = pq.poll();
        sb.append((char)('a'+first[0])).append((char)('a'+second[0]));
        if (--first[1] > 0) pq.offer(first);
        if (--second[1] > 0) pq.offer(second);
    }
    if (!pq.isEmpty()) {
        int[] last = pq.poll();
        if (last[1] > 1) return "";
        sb.append((char)('a'+last[0]));
    }
    return sb.toString();
}
```

---

## 18. Backtracking
**Tier 2**

### Pattern
Make a choice → recurse → undo the choice. Use a `start` index to avoid reuse/re-ordering. Sort input to enable duplicate skipping.

### Java Skeleton
```java
void backtrack(int start, List<Integer> current, List<List<Integer>> result) {
    result.add(new ArrayList<>(current)); // or: base case check
    for (int i = start; i < nums.length; i++) {
        if (i > start && nums[i] == nums[i-1]) continue; // skip duplicates
        current.add(nums[i]);
        backtrack(i + 1, current, result); // i+1 for no reuse; i for reuse
        current.remove(current.size() - 1); // undo
    }
}
```

### Subtopics & Problems

#### Subsets
> Read: 0 | Coded: 0


**1. Subsets (LC 78)**
**Problem**: Given an integer array nums of unique elements, return all possible subsets (the power set) . The solution set must not contain duplicate subsets. Return the solution in any order .

**Approach**: At each index, choose to include or not. Add snapshot at every node of recursion tree.
```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    backtrack(nums, 0, new ArrayList<>(), res);
    return res;
}
private void backtrack(int[] nums, int start, List<Integer> curr, List<List<Integer>> res) {
    res.add(new ArrayList<>(curr));
    for (int i = start; i < nums.length; i++) {
        curr.add(nums[i]);
        backtrack(nums, i + 1, curr, res);
        curr.remove(curr.size() - 1);
    }
}
```

**2. Subsets II (LC 90) — with duplicates**
**Problem**: Given an integer array nums that may contain duplicates, return all possible subsets (the power set) . The solution set must not contain duplicate subsets. Return the solution in any order .

**Approach**: Sort first. Skip `nums[i] == nums[i-1]` when `i > start` to avoid duplicate subsets.
```java
public List<List<Integer>> subsetsWithDup(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> res = new ArrayList<>();
    backtrack(nums, 0, new ArrayList<>(), res);
    return res;
}
private void backtrack(int[] nums, int start, List<Integer> curr, List<List<Integer>> res) {
    res.add(new ArrayList<>(curr));
    for (int i = start; i < nums.length; i++) {
        if (i > start && nums[i] == nums[i-1]) continue;
        curr.add(nums[i]); backtrack(nums, i+1, curr, res); curr.remove(curr.size()-1);
    }
}
```

#### Combinations
> Read: 0 | Coded: 0


**3. Combination Sum (LC 39) — reuse allowed**
**Problem**: Given an array of distinct integers candidates and a target integer target , return a list of all unique combinations of candidates where the chosen numbers sum to target . You may return the combinations in any order . The same number may be chosen from candidates an unlimited number of times . Two combinations are unique if the frequency of at least one of the chosen numbers is different. The test cases are generated such that the number of unique combinations that sum up to target is less than 150 combinations for the given input.

**Approach**: Same index allowed again (`backtrack(i, ...)`). Stop when sum ≥ target.
```java
public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> res = new ArrayList<>();
    Arrays.sort(candidates);
    backtrack(candidates, 0, target, new ArrayList<>(), res);
    return res;
}
private void backtrack(int[] cands, int start, int remain, List<Integer> curr, List<List<Integer>> res) {
    if (remain == 0) { res.add(new ArrayList<>(curr)); return; }
    for (int i = start; i < cands.length && cands[i] <= remain; i++) {
        curr.add(cands[i]);
        backtrack(cands, i, remain - cands[i], curr, res);
        curr.remove(curr.size() - 1);
    }
}
```

**4. Combination Sum II (LC 40) — no reuse, with duplicates**
**Problem**: Given a collection of candidate numbers ( candidates ) and a target number ( target ), find all unique combinations in candidates where the candidate numbers sum to target . Each number in candidates may only be used once in the combination.

**Approach**: `i+1` (no reuse) + skip `nums[i] == nums[i-1]` when `i > start`.
```java
public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    Arrays.sort(candidates);
    List<List<Integer>> res = new ArrayList<>();
    backtrack(candidates, 0, target, new ArrayList<>(), res);
    return res;
}
private void backtrack(int[] cands, int start, int remain, List<Integer> curr, List<List<Integer>> res) {
    if (remain == 0) { res.add(new ArrayList<>(curr)); return; }
    for (int i = start; i < cands.length && cands[i] <= remain; i++) {
        if (i > start && cands[i] == cands[i-1]) continue;
        curr.add(cands[i]);
        backtrack(cands, i+1, remain-cands[i], curr, res);
        curr.remove(curr.size()-1);
    }
}
```

#### Permutations
> Read: 0 | Coded: 0


**5. Permutations (LC 46)**
**Problem**: Given an array nums of distinct integers, return all the possible permutations . You can return the answer in any order .

**Approach**: `boolean[] used` array. Try each unused element at each position.
```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    backtrack(nums, new boolean[nums.length], new ArrayList<>(), res);
    return res;
}
private void backtrack(int[] nums, boolean[] used, List<Integer> curr, List<List<Integer>> res) {
    if (curr.size() == nums.length) { res.add(new ArrayList<>(curr)); return; }
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        used[i] = true; curr.add(nums[i]);
        backtrack(nums, used, curr, res);
        used[i] = false; curr.remove(curr.size()-1);
    }
}
```

**6. Permutations II (LC 47) — with duplicates**
**Problem**: Given a collection of numbers, nums , that might contain duplicates, return all possible unique permutations in any order .

**Approach**: Sort first. Skip if `nums[i] == nums[i-1]` and `!used[i-1]` (sibling already used same value).
```java
public List<List<Integer>> permuteUnique(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> res = new ArrayList<>();
    backtrack(nums, new boolean[nums.length], new ArrayList<>(), res);
    return res;
}
private void backtrack(int[] nums, boolean[] used, List<Integer> curr, List<List<Integer>> res) {
    if (curr.size() == nums.length) { res.add(new ArrayList<>(curr)); return; }
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        if (i > 0 && nums[i] == nums[i-1] && !used[i-1]) continue;
        used[i] = true; curr.add(nums[i]);
        backtrack(nums, used, curr, res);
        used[i] = false; curr.remove(curr.size()-1);
    }
}
```

#### Grid / Constraint Satisfaction
> Read: 0 | Coded: 0


**7. Word Search (LC 79)**
**Problem**: Given an m x n grid of characters board and a string word , return true if word exists in the grid . The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.

**Approach**: DFS on grid, mark cell visited temporarily, restore on backtrack.
```java
public boolean exist(char[][] board, String word) {
    for (int r = 0; r < board.length; r++)
        for (int c = 0; c < board[0].length; c++)
            if (dfs(board, word, r, c, 0)) return true;
    return false;
}
private boolean dfs(char[][] board, String word, int r, int c, int idx) {
    if (idx == word.length()) return true;
    if (r<0||r>=board.length||c<0||c>=board[0].length||board[r][c]!=word.charAt(idx)) return false;
    char tmp = board[r][c]; board[r][c] = '#';
    boolean found = dfs(board,word,r+1,c,idx+1)||dfs(board,word,r-1,c,idx+1)||
                    dfs(board,word,r,c+1,idx+1)||dfs(board,word,r,c-1,idx+1);
    board[r][c] = tmp;
    return found;
}
```

**8. N-Queens (LC 51)**
**Problem**: The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other. Given an integer n , return all distinct solutions to the n-queens puzzle . You may return the answer in any order . Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space, respectively.

**Approach**: Place queen row by row. Track columns, diagonals used.
```java
public List<List<String>> solveNQueens(int n) {
    List<List<String>> res = new ArrayList<>();
    char[][] board = new char[n][n];
    for (char[] row : board) Arrays.fill(row, '.');
    backtrack(board, 0, new HashSet<>(), new HashSet<>(), new HashSet<>(), res);
    return res;
}
private void backtrack(char[][] board, int row, Set<Integer> cols, Set<Integer> diag1, Set<Integer> diag2, List<List<String>> res) {
    if (row == board.length) {
        List<String> solution = new ArrayList<>();
        for (char[] r : board) solution.add(new String(r));
        res.add(solution); return;
    }
    for (int col = 0; col < board.length; col++) {
        if (cols.contains(col) || diag1.contains(row-col) || diag2.contains(row+col)) continue;
        board[row][col] = 'Q'; cols.add(col); diag1.add(row-col); diag2.add(row+col);
        backtrack(board, row+1, cols, diag1, diag2, res);
        board[row][col] = '.'; cols.remove(col); diag1.remove(row-col); diag2.remove(row+col);
    }
}
```

**9. Sudoku Solver (LC 37)**
**Problem**: Write a program to solve a Sudoku puzzle by filling the empty cells. A sudoku solution must satisfy all of the following rules : Each of the digits 1-9 must occur exactly once in each row. Each of the digits 1-9 must occur exactly once in each column. Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid. The '.' character indicates empty cells.

**Approach**: Try digits 1-9 in each empty cell. Validate with row/col/box sets.
```java
public void solveSudoku(char[][] board) { solve(board); }
private boolean solve(char[][] board) {
    for (int r = 0; r < 9; r++) for (int c = 0; c < 9; c++) {
        if (board[r][c] != '.') continue;
        for (char d = '1'; d <= '9'; d++) {
            if (isValid(board, r, c, d)) {
                board[r][c] = d;
                if (solve(board)) return true;
                board[r][c] = '.';
            }
        }
        return false; // no valid digit
    }
    return true;
}
private boolean isValid(char[][] board, int r, int c, char d) {
    for (int i = 0; i < 9; i++) {
        if (board[r][i] == d || board[i][c] == d) return false;
        if (board[3*(r/3)+i/3][3*(c/3)+i%3] == d) return false;
    }
    return true;
}
```

#### Partitioning / String Generation
> Read: 0 | Coded: 0


**10. Palindrome Partitioning (LC 131)**
**Problem**: Given a string s , partition s such that every substring of the partition is a palindrome . Return all possible palindrome partitioning of s .

**Approach**: At each index, try all prefixes; recurse if prefix is a palindrome.
```java
public List<List<String>> partition(String s) {
    List<List<String>> res = new ArrayList<>();
    backtrack(s, 0, new ArrayList<>(), res);
    return res;
}
private void backtrack(String s, int start, List<String> curr, List<List<String>> res) {
    if (start == s.length()) { res.add(new ArrayList<>(curr)); return; }
    for (int end = start + 1; end <= s.length(); end++) {
        String sub = s.substring(start, end);
        if (isPalindrome(sub)) {
            curr.add(sub);
            backtrack(s, end, curr, res);
            curr.remove(curr.size() - 1);
        }
    }
}
private boolean isPalindrome(String s) {
    int lo = 0, hi = s.length()-1;
    while (lo < hi) if (s.charAt(lo++) != s.charAt(hi--)) return false;
    return true;
}
```

**11. Generate Parentheses (LC 22)**
**Problem**: Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses .

**Approach**: Track open/close counts. Can add '(' if open < n; can add ')' if close < open.
```java
public List<String> generateParenthesis(int n) {
    List<String> res = new ArrayList<>();
    backtrack(n, 0, 0, new StringBuilder(), res);
    return res;
}
private void backtrack(int n, int open, int close, StringBuilder sb, List<String> res) {
    if (sb.length() == 2*n) { res.add(sb.toString()); return; }
    if (open < n)  { sb.append('('); backtrack(n, open+1, close, sb, res); sb.deleteCharAt(sb.length()-1); }
    if (close < open) { sb.append(')'); backtrack(n, open, close+1, sb, res); sb.deleteCharAt(sb.length()-1); }
}
```

**12. Restore IP Addresses (LC 93)**
**Problem**: A valid IP address consists of exactly four integers separated by single dots. Each integer is between 0 and 255 ( inclusive ) and cannot have leading zeros. For example, "0.1.2.201" and "192.168.1.1" are valid IP addresses, but "0.011.255.245" , "192.168.1.312" and "192.168@1.1" are invalid IP addresses. Given a string s containing only digits, return all possible valid IP addresses that can be formed by inserting dots into s . You are not allowed to reorder or remove any digits in s . You may return the valid IP addresses in any order.

**Approach**: Try segments of 1-3 digits; validate each segment (no leading zeros, value ≤ 255).
```java
public List<String> restoreIpAddresses(String s) {
    List<String> res = new ArrayList<>();
    backtrack(s, 0, new ArrayList<>(), res);
    return res;
}
private void backtrack(String s, int start, List<String> parts, List<String> res) {
    if (parts.size() == 4 && start == s.length()) { res.add(String.join(".", parts)); return; }
    if (parts.size() == 4 || start == s.length()) return;
    for (int len = 1; len <= 3 && start + len <= s.length(); len++) {
        String seg = s.substring(start, start + len);
        if (seg.length() > 1 && seg.charAt(0) == '0') break; // leading zero
        if (Integer.parseInt(seg) > 255) break;
        parts.add(seg);
        backtrack(s, start + len, parts, res);
        parts.remove(parts.size() - 1);
    }
}
```

---

## 19. 0/1 Knapsack
**Tier 2**

### Pattern
Each item used at most once. `dp[i][w]` = max value using items `0..i` with capacity `w`. Can be space-optimized to 1D by iterating `w` from high to low.

### Java Skeleton
```java
// 2D
int[][] dp = new int[n+1][W+1];
for (int i = 1; i <= n; i++)
    for (int w = 0; w <= W; w++) {
        dp[i][w] = dp[i-1][w]; // skip item i
        if (w >= weight[i-1]) dp[i][w] = Math.max(dp[i][w], dp[i-1][w-weight[i-1]] + value[i-1]);
    }
// 1D space optimization (iterate w backwards)
int[] dp = new int[W+1];
for (int i = 0; i < n; i++)
    for (int w = W; w >= weight[i]; w--)
        dp[w] = Math.max(dp[w], dp[w-weight[i]] + value[i]);
```

### Famous Problems

#### 1. 0/1 Knapsack (Classic)
> Read: 0 | Coded: 0

**Approach**: Standard 2D DP or space-optimized 1D with backward iteration.
```java
public int knapsack(int W, int[] weights, int[] values, int n) {
    int[] dp = new int[W + 1];
    for (int i = 0; i < n; i++)
        for (int w = W; w >= weights[i]; w--)
            dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
    return dp[W];
}
```

#### 2. Partition Equal Subset Sum (LC 416)
> Read: 0 | Coded: 0

**Problem**: Given an integer array nums , return true if you can partition the array into two subsets such that the sum of the elements in both subsets is equal or false otherwise .

**Approach**: Find subset summing to `total/2`. Boolean knapsack: `dp[w]` = can we achieve sum w.
```java
public boolean canPartition(int[] nums) {
    int total = Arrays.stream(nums).sum();
    if (total % 2 != 0) return false;
    int target = total / 2;
    boolean[] dp = new boolean[target + 1];
    dp[0] = true;
    for (int num : nums)
        for (int w = target; w >= num; w--)
            dp[w] = dp[w] || dp[w - num];
    return dp[target];
}
```

#### 3. Target Sum (LC 494)
> Read: 0 | Coded: 0

**Problem**: You are given an integer array nums and an integer target . You want to build an expression out of nums by adding one of the symbols '+' and '-' before each integer in nums and then concatenate all the integers. For example, if nums = [2, 1] , you can add a '+' before 2 and a '-' before 1 and concatenate them to build the expression "+2-1" . Return the number of different expressions that you can build, which evaluates to target .

**Approach**: Assign +/- to each number. Equivalent to: find subset with sum = `(total + target) / 2`.
```java
public int findTargetSumWays(int[] nums, int target) {
    int total = Arrays.stream(nums).sum();
    if ((total + target) % 2 != 0 || Math.abs(target) > total) return 0;
    int sum = (total + target) / 2;
    int[] dp = new int[sum + 1];
    dp[0] = 1;
    for (int num : nums)
        for (int w = sum; w >= num; w--)
            dp[w] += dp[w - num];
    return dp[sum];
}
```

---

## 20. Topological Sort
**Tier 2**

### Pattern
Linear ordering of a DAG. **Kahn's (BFS)**: track in-degrees, start from 0-in-degree nodes. **DFS-based**: post-order DFS, prepend to result.

### Java Skeleton
```java
// Kahn's BFS
int[] inDegree = new int[n];
for (int[] e : edges) inDegree[e[1]]++;
Queue<Integer> q = new LinkedList<>();
for (int i = 0; i < n; i++) if (inDegree[i] == 0) q.offer(i);
List<Integer> order = new ArrayList<>();
while (!q.isEmpty()) {
    int node = q.poll(); order.add(node);
    for (int nb : graph.get(node)) if (--inDegree[nb] == 0) q.offer(nb);
}
// If order.size() < n → cycle exists
```

### Famous Problems

#### 1. Course Schedule (LC 207)
> Read: 0 | Coded: 0

**Problem**: There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1 . You are given an array prerequisites where prerequisites[i] = [a i , b i ] indicates that you must take course b i first if you want to take course a i . For example, the pair [0, 1] , indicates that to take course 0 you have to first take course 1 . Return true if you can finish all courses. Otherwise, return false .

**Approach**: Kahn's. If topological order includes all nodes, no cycle exists.
```java
public boolean canFinish(int numCourses, int[][] prerequisites) {
    int[] inDegree = new int[numCourses];
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
    for (int[] p : prerequisites) { graph.get(p[1]).add(p[0]); inDegree[p[0]]++; }
    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) if (inDegree[i] == 0) q.offer(i);
    int processed = 0;
    while (!q.isEmpty()) {
        int node = q.poll(); processed++;
        for (int nb : graph.get(node)) if (--inDegree[nb] == 0) q.offer(nb);
    }
    return processed == numCourses;
}
```

#### 2. Course Schedule II (LC 210)
> Read: 0 | Coded: 0

**Problem**: There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1 . You are given an array prerequisites where prerequisites[i] = [a i , b i ] indicates that you must take course b i first if you want to take course a i . For example, the pair [0, 1] , indicates that to take course 0 you have to first take course 1 . Return the ordering of courses you should take to finish all courses . If there are many valid answers, return any of them. If it is impossible to finish all courses, return an empty array .

**Approach**: Return the topological order itself.
```java
public int[] findOrder(int numCourses, int[][] prerequisites) {
    int[] inDegree = new int[numCourses];
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
    for (int[] p : prerequisites) { graph.get(p[1]).add(p[0]); inDegree[p[0]]++; }
    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) if (inDegree[i] == 0) q.offer(i);
    int[] order = new int[numCourses]; int idx = 0;
    while (!q.isEmpty()) {
        int node = q.poll(); order[idx++] = node;
        for (int nb : graph.get(node)) if (--inDegree[nb] == 0) q.offer(nb);
    }
    return idx == numCourses ? order : new int[0];
}
```

#### 3. Alien Dictionary (LC 269)
> Read: 0 | Coded: 0

**Problem**: There is a new alien language that uses the English alphabet. However, the order of the letters is unknown to you. You are given a list of strings words from the alien language's dictionary, where the strings in words are sorted lexicographically by the rules of this new language. Return a string of the unique letters in the new alien language sorted in lexicographically increasing order by the new language's rules. If there is no solution, return "". If there are multiple solutions, return any of them.

**Approach**: Compare adjacent words to build char-order edges, then topological sort.
```java
public String alienOrder(String[] words) {
    Map<Character, Set<Character>> graph = new HashMap<>();
    Map<Character, Integer> inDegree = new HashMap<>();
    for (String w : words) for (char c : w.toCharArray()) { graph.putIfAbsent(c, new HashSet<>()); inDegree.putIfAbsent(c, 0); }
    for (int i = 0; i < words.length - 1; i++) {
        String a = words[i], b = words[i+1];
        if (a.length() > b.length() && a.startsWith(b)) return "";
        for (int j = 0; j < Math.min(a.length(), b.length()); j++) {
            if (a.charAt(j) != b.charAt(j)) {
                if (!graph.get(a.charAt(j)).contains(b.charAt(j))) {
                    graph.get(a.charAt(j)).add(b.charAt(j));
                    inDegree.merge(b.charAt(j), 1, Integer::sum);
                }
                break;
            }
        }
    }
    Queue<Character> q = new LinkedList<>();
    for (char c : inDegree.keySet()) if (inDegree.get(c) == 0) q.offer(c);
    StringBuilder sb = new StringBuilder();
    while (!q.isEmpty()) {
        char c = q.poll(); sb.append(c);
        for (char nb : graph.get(c)) if (inDegree.merge(nb, -1, Integer::sum) == 0) q.offer(nb);
    }
    return sb.length() == inDegree.size() ? sb.toString() : "";
}
```

---

## 21. Union Find (DSU)
**Tier 2**

### Pattern
Disjoint Set Union with path compression + union by rank. `find` returns root; `union` merges two components.

### Java Skeleton
```java
int[] parent, rank;
void init(int n) {
    parent = new int[n]; rank = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
}
int find(int x) {
    if (parent[x] != x) parent[x] = find(parent[x]); // path compression
    return parent[x];
}
boolean union(int x, int y) {
    int px = find(x), py = find(y);
    if (px == py) return false; // already connected
    if (rank[px] < rank[py]) { int t = px; px = py; py = t; }
    parent[py] = px;
    if (rank[px] == rank[py]) rank[px]++;
    return true;
}
```

### Famous Problems

#### 1. Number of Connected Components (LC 323)
> Read: 0 | Coded: 0

**Problem**: You have a graph of n nodes. You are given an integer n and an array edges where edges[i] = [ai, bi] indicates that there is an edge between ai and bi in the graph. Return the number of connected components in the graph.

**Approach**: Union all edges; count distinct roots.
```java
public int countComponents(int n, int[][] edges) {
    int[] parent = new int[n]; int[] rank = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
    int components = n;
    for (int[] e : edges) {
        int px = find(parent, e[0]), py = find(parent, e[1]);
        if (px != py) { parent[px] = py; components--; }
    }
    return components;
}
private int find(int[] parent, int x) {
    if (parent[x] != x) parent[x] = find(parent, parent[x]);
    return parent[x];
}
```

#### 2. Redundant Connection (LC 684)
> Read: 0 | Coded: 0

**Problem**: In this problem, a tree is an undirected graph that is connected and has no cycles. You are given a graph that started as a tree with n nodes labeled from 1 to n , with one additional edge added. The added edge has two different vertices chosen from 1 to n , and was not an edge that already existed. The graph is represented as an array edges of length n where edges[i] = [a i , b i ] indicates that there is an edge between nodes a i and b i in the graph. Return an edge that can be removed so that the resulting graph is a tree of n nodes . If there are multiple answers, return the answer that occurs last in the input.

**Approach**: Try to union each edge; the first edge that fails (both endpoints in same set) is redundant.
```java
public int[] findRedundantConnection(int[][] edges) {
    int n = edges.length;
    int[] parent = new int[n+1]; int[] rank = new int[n+1];
    for (int i = 0; i <= n; i++) parent[i] = i;
    for (int[] e : edges)
        if (!union(parent, rank, e[0], e[1])) return e;
    return new int[]{};
}
private boolean union(int[] p, int[] rank, int x, int y) {
    int px = find(p,x), py = find(p,y);
    if (px == py) return false;
    if (rank[px] < rank[py]) { int t=px; px=py; py=t; }
    p[py] = px; if (rank[px]==rank[py]) rank[px]++;
    return true;
}
private int find(int[] p, int x) { return p[x]==x ? x : (p[x]=find(p,p[x])); }
```

#### 3. Accounts Merge (LC 721)
> Read: 0 | Coded: 0

**Problem**: Given a list of accounts where each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account. Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some common email to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name. After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails in sorted order . The accounts themselves can be returned in any order .

**Approach**: Union emails by index. Group by root; reconstruct account names.
```java
public List<List<String>> accountsMerge(List<List<String>> accounts) {
    Map<String, Integer> emailToId = new HashMap<>();
    int[] parent; // initialize lazily per email id
    // ... (full implementation uses DSU on email IDs mapped to integers)
    // Key pattern:
    int id = 0;
    for (List<String> acc : accounts) {
        for (int i = 1; i < acc.size(); i++) {
            emailToId.putIfAbsent(acc.get(i), id++);
        }
    }
    parent = new int[id];
    for (int i = 0; i < id; i++) parent[i] = i;
    for (List<String> acc : accounts) {
        int first = emailToId.get(acc.get(1));
        for (int i = 2; i < acc.size(); i++) union(parent, first, emailToId.get(acc.get(i)));
    }
    Map<Integer, TreeSet<String>> groups = new HashMap<>();
    Map<Integer, String> rootToName = new HashMap<>();
    for (List<String> acc : accounts) {
        String name = acc.get(0);
        for (int i = 1; i < acc.size(); i++) {
            int root = find(parent, emailToId.get(acc.get(i)));
            groups.computeIfAbsent(root, k -> new TreeSet<>()).add(acc.get(i));
            rootToName.put(root, name);
        }
    }
    List<List<String>> res = new ArrayList<>();
    for (int root : groups.keySet()) {
        List<String> merged = new ArrayList<>();
        merged.add(rootToName.get(root));
        merged.addAll(groups.get(root));
        res.add(merged);
    }
    return res;
}
private void union(int[] p, int x, int y) { p[find(p,x)] = find(p,y); }
private int find(int[] p, int x) { return p[x]==x?x:(p[x]=find(p,p[x])); }
```

---

## 22. Kadane's Algorithm
**Tier 2**

### Pattern
Scan left to right, maintaining the best subarray ending at the current position. `currentSum = max(nums[i], currentSum + nums[i])`.

### Java Skeleton
```java
int maxSum = nums[0], currentSum = nums[0];
for (int i = 1; i < nums.length; i++) {
    currentSum = Math.max(nums[i], currentSum + nums[i]);
    maxSum = Math.max(maxSum, currentSum);
}
return maxSum;
```

### Famous Problems

#### 1. Maximum Subarray (LC 53)
> Read: 0 | Coded: 0

**Problem**: Given an integer array nums , find the subarray with the largest sum, and return its sum .

**Approach**: Classic Kadane's.
```java
public int maxSubArray(int[] nums) {
    int maxSum = nums[0], curr = nums[0];
    for (int i = 1; i < nums.length; i++) {
        curr = Math.max(nums[i], curr + nums[i]);
        maxSum = Math.max(maxSum, curr);
    }
    return maxSum;
}
```

#### 2. Maximum Product Subarray (LC 152)
> Read: 0 | Coded: 0

**Problem**: Given an integer array nums , find a subarray that has the largest product, and return the product . The test cases are generated so that the answer will fit in a 32-bit integer. Note that the product of an array with a single element is the value of that element.

**Approach**: Track both `maxProd` and `minProd` (negatives can become largest when multiplied by a negative).
```java
public int maxProduct(int[] nums) {
    int maxP = nums[0], minP = nums[0], result = nums[0];
    for (int i = 1; i < nums.length; i++) {
        int candidates[] = {nums[i], maxP*nums[i], minP*nums[i]};
        maxP = Arrays.stream(candidates).max().getAsInt();
        minP = Arrays.stream(candidates).min().getAsInt();
        result = Math.max(result, maxP);
    }
    return result;
}
```

#### 3. Best Time to Buy and Sell Stock I (LC 121)
> Read: 0 | Coded: 0

**Problem**: You are given an array prices where prices[i] is the price of a given stock on the i th day. You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock. Return the maximum profit you can achieve from this transaction . If you cannot achieve any profit, return 0 .

**Approach**: Track minimum price seen so far; profit at each step = price - minPrice.
```java
public int maxProfit(int[] prices) {
    int minPrice = Integer.MAX_VALUE, maxProfit = 0;
    for (int price : prices) {
        minPrice = Math.min(minPrice, price);
        maxProfit = Math.max(maxProfit, price - minPrice);
    }
    return maxProfit;
}
```

---

## 23. Recursion
**Tier 2**

### Pattern
Define base case, reduce problem to a smaller identical subproblem, combine results. No memoization (contrast with DP). Key: trust the recursive call returns the correct answer for smaller input.

### Java Skeleton
```java
ReturnType solve(InputType input) {
    if (isBaseCase(input)) return baseValue;
    // reduce input
    ReturnType sub = solve(smallerInput);
    return combine(sub, currentElement);
}
```

### Famous Problems

#### 1. Flatten Nested List Iterator (LC 341)
> Read: 0 | Coded: 0

**Problem**: You are given a nested list of integers nestedList . Each element is either an integer or a list whose elements may also be integers or other lists. Implement an iterator to flatten it. Implement the NestedIterator class: NestedIterator(List<NestedInteger> nestedList) Initializes the iterator with the nested list nestedList . int next() Returns the next integer in the nested list. boolean hasNext() Returns true if there are still some integers in the nested list and false otherwise. Your code will be tested with the following pseudocode: initialize iterator with nestedList res = [] while iterator.hasNext() append iterator.next() to the end of res return res If res matches the expected flattened list, then your code will be judged as correct.

**Approach**: Recursively flatten nested list into a flat list; iterator walks the flat list.
```java
public class NestedIterator implements Iterator<Integer> {
    private List<Integer> flat = new ArrayList<>();
    private int idx = 0;
    public NestedIterator(List<NestedInteger> nestedList) { flatten(nestedList); }
    private void flatten(List<NestedInteger> list) {
        for (NestedInteger ni : list) {
            if (ni.isInteger()) flat.add(ni.getInteger());
            else flatten(ni.getList());
        }
    }
    public Integer next() { return flat.get(idx++); }
    public boolean hasNext() { return idx < flat.size(); }
}
```

#### 2. Swap Nodes in Pairs (LC 24)
> Read: 0 | Coded: 0

**Problem**: Given a linked list, swap every two adjacent nodes and return its head. You must solve the problem without modifying the values in the list's nodes (i.e., only nodes themselves may be changed.)

**Approach**: Swap head and head.next, then recurse on head.next.next.
```java
public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode second = head.next;
    head.next = swapPairs(second.next); // recurse on rest
    second.next = head;                  // swap
    return second;                       // new head
}
```

#### 3. Decode String (LC 394)
> Read: 0 | Coded: 0

**Problem**: Given an encoded string, return its decoded string. The encoding rule is: k[encoded_string] , where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer. You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k . For example, there will not be input like 3a or 2[4] . The test cases are generated so that the length of the output will never exceed 10 5 .

**Approach**: Recursive parsing: handle `k[encoded_string]` by returning repeated inner string.
```java
private int i = 0; // global index
public String decodeString(String s) {
    StringBuilder sb = new StringBuilder();
    while (i < s.length() && s.charAt(i) != ']') {
        if (!Character.isDigit(s.charAt(i))) {
            sb.append(s.charAt(i++));
        } else {
            int k = 0;
            while (Character.isDigit(s.charAt(i))) k = k*10 + (s.charAt(i++)-'0');
            i++; // skip '['
            String inner = decodeString(s);
            i++; // skip ']'
            sb.append(inner.repeat(k));
        }
    }
    return sb.toString();
}
```

---

## 24. Divide & Conquer
**Tier 2**

### Pattern
Split problem into non-overlapping subproblems of the same type, solve each, merge results. Key difference from DP: subproblems don't overlap (no memoization needed).

### Java Skeleton
```java
ReturnType dc(int[] arr, int lo, int hi) {
    if (lo >= hi) return baseCase(arr[lo]);
    int mid = lo + (hi - lo) / 2;
    ReturnType left = dc(arr, lo, mid);
    ReturnType right = dc(arr, mid + 1, hi);
    return merge(left, right);
}
```

### Famous Problems

#### 1. Median of Two Sorted Arrays (LC 4)
> Read: 0 | Coded: 0

**Problem**: Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)) .

**Approach**: Binary search on partition position in smaller array to find balanced split.
```java
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);
    int m = nums1.length, n = nums2.length;
    int lo = 0, hi = m;
    while (lo <= hi) {
        int i = lo + (hi - lo) / 2, j = (m + n + 1) / 2 - i;
        int maxL1 = i==0 ? Integer.MIN_VALUE : nums1[i-1];
        int minR1 = i==m ? Integer.MAX_VALUE : nums1[i];
        int maxL2 = j==0 ? Integer.MIN_VALUE : nums2[j-1];
        int minR2 = j==n ? Integer.MAX_VALUE : nums2[j];
        if (maxL1 <= minR2 && maxL2 <= minR1) {
            int maxLeft = Math.max(maxL1, maxL2);
            if ((m+n)%2==1) return maxLeft;
            return (maxLeft + Math.min(minR1, minR2)) / 2.0;
        } else if (maxL1 > minR2) hi = i - 1;
        else lo = i + 1;
    }
    return 0;
}
```

#### 2. Different Ways to Add Parentheses (LC 241)
> Read: 0 | Coded: 0

**Problem**: Given a string expression of numbers and operators, return all possible results from computing all the different possible ways to group numbers and operators . You may return the answer in any order . The test cases are generated such that the output values fit in a 32-bit integer and the number of different results does not exceed 10 4 .

**Approach**: At each operator, divide into left and right sub-expressions; combine all pairs of results.
```java
public List<Integer> diffWaysToCompute(String expression) {
    List<Integer> res = new ArrayList<>();
    for (int i = 0; i < expression.length(); i++) {
        char c = expression.charAt(i);
        if (c == '+' || c == '-' || c == '*') {
            List<Integer> left = diffWaysToCompute(expression.substring(0, i));
            List<Integer> right = diffWaysToCompute(expression.substring(i + 1));
            for (int l : left) for (int r : right) {
                if (c == '+') res.add(l+r);
                else if (c == '-') res.add(l-r);
                else res.add(l*r);
            }
        }
    }
    if (res.isEmpty()) res.add(Integer.parseInt(expression)); // pure number
    return res;
}
```

#### 3. Construct Binary Tree from Preorder and Inorder Traversal (LC 105)
> Read: 0 | Coded: 0

**Problem**: Given two integer arrays preorder and inorder where preorder is the preorder traversal of a binary tree and inorder is the inorder traversal of the same tree, construct and return the binary tree .

**Approach**: Root = preorder[0]; find root in inorder; left subtree = everything left of root in inorder.
```java
private int preIdx = 0;
private Map<Integer, Integer> inMap = new HashMap<>();
public TreeNode buildTree(int[] preorder, int[] inorder) {
    for (int i = 0; i < inorder.length; i++) inMap.put(inorder[i], i);
    return build(preorder, 0, inorder.length - 1);
}
private TreeNode build(int[] preorder, int inLeft, int inRight) {
    if (inLeft > inRight) return null;
    int val = preorder[preIdx++];
    TreeNode root = new TreeNode(val);
    int mid = inMap.get(val);
    root.left = build(preorder, inLeft, mid - 1);
    root.right = build(preorder, mid + 1, inRight);
    return root;
}
```

---

## 25. Two Heaps
**Tier 3**

### Pattern
Max-heap for lower half, min-heap for upper half. Keep sizes balanced (differ by at most 1). Median = top of larger heap, or average of both tops.

### Java Skeleton
```java
PriorityQueue<Integer> lo = new PriorityQueue<>(Collections.reverseOrder()); // max-heap
PriorityQueue<Integer> hi = new PriorityQueue<>(); // min-heap

void add(int num) {
    lo.offer(num);
    hi.offer(lo.poll()); // ensure lo's max <= hi's min
    if (hi.size() > lo.size()) lo.offer(hi.poll()); // rebalance
}
double getMedian() {
    return lo.size() > hi.size() ? lo.peek() : (lo.peek() + hi.peek()) / 2.0;
}
```

### Famous Problems

#### 1. Find Median from Data Stream (LC 295)
> Read: 0 | Coded: 0

**Problem**: The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value, and the median is the mean of the two middle values. For example, for arr = [2,3,4] , the median is 3 . For example, for arr = [2,3] , the median is (2 + 3) / 2 = 2.5 . Implement the MedianFinder class: MedianFinder() initializes the MedianFinder object. void addNum(int num) adds the integer num from the data stream to the data structure. double findMedian() returns the median of all elements so far. Answers within 10 -5 of the actual answer will be accepted.

**Approach**: Maintain two heaps as above. Add each number and rebalance.
```java
class MedianFinder {
    PriorityQueue<Integer> lo = new PriorityQueue<>(Collections.reverseOrder());
    PriorityQueue<Integer> hi = new PriorityQueue<>();
    public void addNum(int num) {
        lo.offer(num);
        hi.offer(lo.poll());
        if (hi.size() > lo.size()) lo.offer(hi.poll());
    }
    public double findMedian() {
        return lo.size() > hi.size() ? lo.peek() : (lo.peek() + hi.peek()) / 2.0;
    }
}
```

#### 2. Sliding Window Median (LC 480)
> Read: 0 | Coded: 0

**Problem**: The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle values. For examples, if arr = [2, 3 ,4] , the median is 3 . For examples, if arr = [1, 2,3 ,4] , the median is (2 + 3) / 2 = 2.5 . You are given an integer array nums and an integer k . There is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Return the median array for each window in the original array . Answers within 10 -5 of the actual value will be accepted.

**Approach**: Same two-heap structure but also remove the outgoing element each step. Use a lazy-removal map.
```java
public double[] medianSlidingWindow(int[] nums, int k) {
    PriorityQueue<Integer> lo = new PriorityQueue<>(Collections.reverseOrder());
    PriorityQueue<Integer> hi = new PriorityQueue<>();
    Map<Integer, Integer> toRemove = new HashMap<>();
    double[] res = new double[nums.length - k + 1];
    // Add first k elements
    for (int i = 0; i < k; i++) { lo.offer(nums[i]); hi.offer(lo.poll()); if (hi.size() > lo.size()) lo.offer(hi.poll()); }
    res[0] = k % 2 == 0 ? ((double)lo.peek() + hi.peek()) / 2 : lo.peek();
    for (int i = k; i < nums.length; i++) {
        int out = nums[i - k];
        toRemove.merge(out, 1, Integer::sum);
        int balance = lo.contains(out) ? -1 : 1; // -1 means lo lost one
        lo.offer(nums[i]);
        hi.offer(lo.poll());
        if (hi.size() > lo.size()) lo.offer(hi.poll());
        if (balance < 0) { if (!hi.isEmpty() && !toRemove.isEmpty()) {} } // lazy prune
        // Prune tops
        while (!lo.isEmpty() && toRemove.getOrDefault(lo.peek(), 0) > 0) { toRemove.merge(lo.poll(), -1, Integer::sum); }
        while (!hi.isEmpty() && toRemove.getOrDefault(hi.peek(), 0) > 0) { toRemove.merge(hi.poll(), -1, Integer::sum); }
        res[i - k + 1] = k % 2 == 0 ? ((double)lo.peek() + hi.peek()) / 2 : lo.peek();
    }
    return res;
}
```

#### 3. IPO (LC 502)
> Read: 0 | Coded: 0

**Problem**: Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital, LeetCode would like to work on some projects to increase its capital before the IPO . Since it has limited resources, it can only finish at most k distinct projects before the IPO . Help LeetCode design the best way to maximize its total capital after finishing at most k distinct projects. You are given n projects where the i th project has a pure profit profits[i] and a minimum capital of capital[i] is needed to start it. Initially, you have w capital. When you finish a project, you will obtain its pure profit and the profit will be added to your total capital. Pick a list of at most k distinct projects from given projects to maximize your final capital , and return the final maximized capital . The answer is guaranteed to fit in a 32-bit signed integer.

**Approach**: Greedily pick highest-profit available project. Use min-heap by capital to unlock projects, max-heap by profit to pick best.
```java
public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
    int n = profits.length;
    int[][] projects = new int[n][2];
    for (int i = 0; i < n; i++) projects[i] = new int[]{capital[i], profits[i]};
    Arrays.sort(projects, (a, b) -> a[0] - b[0]); // sort by capital
    PriorityQueue<Integer> maxProfit = new PriorityQueue<>(Collections.reverseOrder());
    int idx = 0;
    for (int i = 0; i < k; i++) {
        while (idx < n && projects[idx][0] <= w) maxProfit.offer(projects[idx++][1]);
        if (maxProfit.isEmpty()) break;
        w += maxProfit.poll();
    }
    return w;
}
```

---

## 26. Intervals
**Tier 3**

### Pattern
Sort intervals by start time. Merge if `current.start <= last.end`. For scheduling, use a min-heap of end times.

### Java Skeleton
```java
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
List<int[]> merged = new ArrayList<>();
merged.add(intervals[0]);
for (int[] curr : intervals) {
    int[] last = merged.get(merged.size() - 1);
    if (curr[0] <= last[1]) last[1] = Math.max(last[1], curr[1]); // overlap: extend
    else merged.add(curr);                                           // no overlap: add new
}
```

### Famous Problems

#### 1. Merge Intervals (LC 56)
> Read: 0 | Coded: 0

**Problem**: Given an array of intervals where intervals[i] = [start i , end i ] , merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input .

**Approach**: Sort by start, merge overlapping intervals.
```java
public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    List<int[]> res = new ArrayList<>();
    res.add(intervals[0]);
    for (int[] curr : intervals) {
        int[] last = res.get(res.size() - 1);
        if (curr[0] <= last[1]) last[1] = Math.max(last[1], curr[1]);
        else res.add(curr);
    }
    return res.toArray(new int[0][]);
}
```

#### 2. Insert Interval (LC 57)
> Read: 0 | Coded: 0

**Problem**: You are given an array of non-overlapping intervals intervals where intervals[i] = [start i , end i ] represent the start and the end of the i th interval and intervals is sorted in ascending order by start i . You are also given an interval newInterval = [start, end] that represents the start and end of another interval. Insert newInterval into intervals such that intervals is still sorted in ascending order by start i and intervals still does not have any overlapping intervals (merge overlapping intervals if necessary). Return intervals after the insertion . Note that you don't need to modify intervals in-place. You can make a new array and return it.

**Approach**: Add all non-overlapping intervals before the new one, merge overlapping ones, add remainder.
```java
public int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> res = new ArrayList<>();
    int i = 0, n = intervals.length;
    while (i < n && intervals[i][1] < newInterval[0]) res.add(intervals[i++]);
    while (i < n && intervals[i][0] <= newInterval[1]) {
        newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
        newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
        i++;
    }
    res.add(newInterval);
    while (i < n) res.add(intervals[i++]);
    return res.toArray(new int[0][]);
}
```

#### 3. Meeting Rooms II (LC 253)
> Read: 0 | Coded: 0

**Problem**: Given an array of meeting time intervals intervals where intervals[i] = [starti, endi], return the minimum number of conference rooms required.

**Approach**: Min-heap of end times. Each meeting needs a new room if it starts before the earliest ending.
```java
public int minMeetingRooms(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    PriorityQueue<Integer> endTimes = new PriorityQueue<>();
    for (int[] meeting : intervals) {
        if (!endTimes.isEmpty() && endTimes.peek() <= meeting[0])
            endTimes.poll(); // reuse room
        endTimes.offer(meeting[1]);
    }
    return endTimes.size();
}
```

---

## 27. QuickSort / QuickSelect
**Tier 3**

### Pattern
**QuickSort**: partition around pivot, recurse on both halves. **QuickSelect**: partition, recurse only on the half containing the kth element — O(n) average.

### Java Skeleton
```java
// QuickSelect: find kth smallest (0-indexed)
int quickSelect(int[] nums, int lo, int hi, int k) {
    int pivot = partition(nums, lo, hi);
    if (pivot == k) return nums[pivot];
    return pivot < k ? quickSelect(nums, pivot+1, hi, k)
                     : quickSelect(nums, lo, pivot-1, k);
}
int partition(int[] nums, int lo, int hi) {
    int pivot = nums[hi], i = lo;
    for (int j = lo; j < hi; j++) if (nums[j] <= pivot) swap(nums, i++, j);
    swap(nums, i, hi);
    return i;
}
```

### Famous Problems

#### 1. Sort Colors (LC 75) — Dutch National Flag
> Read: 0 | Coded: 0

**Problem**: Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue. We will use the integers 0 , 1 , and 2 to represent the color red, white, and blue, respectively. You must solve this problem without using the library's sort function.

**Approach**: Three pointers: `lo` (boundary of 0s), `mid` (current), `hi` (boundary of 2s).
```java
public void sortColors(int[] nums) {
    int lo = 0, mid = 0, hi = nums.length - 1;
    while (mid <= hi) {
        if (nums[mid] == 0) swap(nums, lo++, mid++);
        else if (nums[mid] == 2) swap(nums, mid, hi--);
        else mid++;
    }
}
private void swap(int[] a, int i, int j) { int t=a[i]; a[i]=a[j]; a[j]=t; }
```

#### 2. Kth Largest Element in an Array (LC 215)
> Read: 0 | Coded: 0

**Problem**: Given an integer array nums and an integer k , return the k th largest element in the array . Note that it is the k th largest element in the sorted order, not the k th distinct element. Can you solve it without sorting?

**Approach**: QuickSelect. Kth largest = (n-k)th smallest.
```java
public int findKthLargest(int[] nums, int k) {
    return quickSelect(nums, 0, nums.length-1, nums.length-k);
}
private int quickSelect(int[] nums, int lo, int hi, int k) {
    int pivot = partition(nums, lo, hi);
    if (pivot == k) return nums[pivot];
    return pivot < k ? quickSelect(nums, pivot+1, hi, k) : quickSelect(nums, lo, pivot-1, k);
}
private int partition(int[] nums, int lo, int hi) {
    int pivot = nums[hi], i = lo;
    for (int j = lo; j < hi; j++) if (nums[j] <= pivot) { int t=nums[i]; nums[i++]=nums[j]; nums[j]=t; }
    int t=nums[i]; nums[i]=nums[hi]; nums[hi]=t;
    return i;
}
```

#### 3. Wiggle Sort II (LC 324)
> Read: 0 | Coded: 0

**Problem**: Given an integer array nums , reorder it such that nums[0] < nums[1] > nums[2] < nums[3]... . You may assume the input array always has a valid answer.

**Approach**: Find median with QuickSelect. Place larger-than-median at odd indices (descending), smaller-than-median at even indices (descending).
```java
public void wiggleSort(int[] nums) {
    int n = nums.length;
    int[] sorted = nums.clone();
    Arrays.sort(sorted);
    int hi = n - 1, lo = (n - 1) / 2;
    for (int i = 0; i < n; i++) {
        nums[i] = (i % 2 == 1) ? sorted[hi--] : sorted[lo--];
    }
}
```

---

## 28. Tries
**Tier 3**

### Pattern
Tree of characters. Each node holds children array and `isEnd` flag. Insert: walk/create nodes. Search: walk nodes, return false if any character missing.

### Java Skeleton
```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEnd;
}
class Trie {
    TrieNode root = new TrieNode();
    void insert(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (cur.children[c-'a'] == null) cur.children[c-'a'] = new TrieNode();
            cur = cur.children[c-'a'];
        }
        cur.isEnd = true;
    }
    boolean search(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (cur.children[c-'a'] == null) return false;
            cur = cur.children[c-'a'];
        }
        return cur.isEnd;
    }
}
```

### Famous Problems

#### 1. Implement Trie (LC 208)
> Read: 1 | Coded: 1

**Problem**: A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker. Implement the Trie class: Trie() Initializes the trie object. void insert(String word) Inserts the string word into the trie. boolean search(String word) Returns true if the string word is in the trie (i.e., was inserted before), and false otherwise. boolean startsWith(String prefix) Returns true if there is a previously inserted string word that has the prefix prefix , and false otherwise.

**Approach**: Standard insert/search/startsWith using TrieNode array children.
```java
class Trie {
    private TrieNode root = new TrieNode();
    public void insert(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (cur.children[c-'a'] == null) cur.children[c-'a'] = new TrieNode();
            cur = cur.children[c-'a'];
        }
        cur.isEnd = true;
    }
    public boolean search(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (cur.children[c-'a'] == null) return false;
            cur = cur.children[c-'a'];
        }
        return cur.isEnd;
    }
    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        for (char c : prefix.toCharArray()) {
            if (cur.children[c-'a'] == null) return false;
            cur = cur.children[c-'a'];
        }
        return true;
    }
    static class TrieNode { TrieNode[] children = new TrieNode[26]; boolean isEnd; }
}
```

#### 2. Word Search II (LC 212)
> Read: 0 | Coded: 0

**Problem**: Given an m x n board of characters and a list of strings words , return all words on the board . Each word must be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

**Approach**: Build Trie from word list. DFS on board, pruning branches not in Trie.
```java
public List<String> findWords(char[][] board, String[] words) {
    Trie trie = new Trie();
    for (String w : words) trie.insert(w);
    Set<String> res = new HashSet<>();
    for (int r = 0; r < board.length; r++)
        for (int c = 0; c < board[0].length; c++)
            dfs(board, r, c, trie.root, new StringBuilder(), res);
    return new ArrayList<>(res);
}
private void dfs(char[][] board, int r, int c, TrieNode node, StringBuilder path, Set<String> res) {
    if (r<0||r>=board.length||c<0||c>=board[0].length||board[r][c]=='#') return;
    char ch = board[r][c];
    TrieNode next = node.children[ch-'a'];
    if (next == null) return;
    path.append(ch); board[r][c] = '#';
    if (next.isEnd) res.add(path.toString());
    int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
    for (int[] d : dirs) dfs(board, r+d[0], c+d[1], next, path, res);
    path.deleteCharAt(path.length()-1); board[r][c] = ch;
}
// TrieNode same as above
```

#### 3. Design Add and Search Words Data Structure (LC 211)
> Read: 0 | Coded: 0

**Problem**: Design a data structure that supports adding new words and finding if a string matches any previously added string. Implement the WordDictionary class: WordDictionary() Initializes the object. void addWord(word) Adds word to the data structure, it can be matched later. bool search(word) Returns true if there is any string in the data structure that matches word or false otherwise. word may contain dots '.' where dots can be matched with any letter.

**Approach**: Insert as normal Trie. Search: '.' matches any child — recurse into all non-null children.
```java
class WordDictionary {
    TrieNode root = new TrieNode();
    public void addWord(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (cur.children[c-'a'] == null) cur.children[c-'a'] = new TrieNode();
            cur = cur.children[c-'a'];
        }
        cur.isEnd = true;
    }
    public boolean search(String word) { return dfs(word, 0, root); }
    private boolean dfs(String word, int i, TrieNode node) {
        if (i == word.length()) return node.isEnd;
        char c = word.charAt(i);
        if (c == '.') {
            for (TrieNode child : node.children) if (child != null && dfs(word, i+1, child)) return true;
            return false;
        }
        if (node.children[c-'a'] == null) return false;
        return dfs(word, i+1, node.children[c-'a']);
    }
    static class TrieNode { TrieNode[] children = new TrieNode[26]; boolean isEnd; }
}
```

---

## 29. 2D Grid DP
**Tier 3**

### Pattern
`dp[i][j]` depends on `dp[i-1][j]` (up) and/or `dp[i][j-1]` (left). Fill row by row, column by column.

### Java Skeleton
```java
int[][] dp = new int[m + 1][n + 1];
// initialize base cases (row 0, col 0)
for (int i = 1; i <= m; i++)
    for (int j = 1; j <= n; j++)
        dp[i][j] = /* recurrence using dp[i-1][j], dp[i][j-1], dp[i-1][j-1] */;
return dp[m][n];
```

### Famous Problems

#### 1. Unique Paths (LC 62)
> Read: 0 | Coded: 0

**Problem**: There is a robot on an m x n grid. The robot is initially located at the top-left corner (i.e., grid[0][0] ). The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1] ). The robot can only move either down or right at any point in time. Given the two integers m and n , return the number of possible unique paths that the robot can take to reach the bottom-right corner . The test cases are generated so that the answer will be less than or equal to 2 * 10 9 .

**Approach**: `dp[i][j] = dp[i-1][j] + dp[i][j-1]` — paths from top + paths from left.
```java
public int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n];
    Arrays.fill(dp[0], 1);
    for (int i = 0; i < m; i++) dp[i][0] = 1;
    for (int i = 1; i < m; i++)
        for (int j = 1; j < n; j++)
            dp[i][j] = dp[i-1][j] + dp[i][j-1];
    return dp[m-1][n-1];
}
```

#### 2. Minimum Path Sum (LC 64)
> Read: 0 | Coded: 0

**Problem**: Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right, which minimizes the sum of all numbers along its path.

**Approach**: `dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1])`.
```java
public int minPathSum(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    for (int i = 0; i < m; i++)
        for (int j = 0; j < n; j++) {
            if (i == 0 && j == 0) continue;
            int up   = i > 0 ? grid[i-1][j] : Integer.MAX_VALUE;
            int left = j > 0 ? grid[i][j-1] : Integer.MAX_VALUE;
            grid[i][j] += Math.min(up, left);
        }
    return grid[m-1][n-1];
}
```

#### 3. Maximal Square (LC 221)
> Read: 0 | Coded: 0

**Problem**: Given an m x n binary matrix filled with 0 's and 1 's, find the largest square containing only 1 's and return its area .

**Approach**: `dp[i][j]` = side of largest square with bottom-right at (i,j). = `min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1` if cell is '1'.
```java
public int maximalSquare(char[][] matrix) {
    int m = matrix.length, n = matrix[0].length, maxSide = 0;
    int[][] dp = new int[m+1][n+1];
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++)
            if (matrix[i-1][j-1] == '1') {
                dp[i][j] = Math.min(dp[i-1][j], Math.min(dp[i][j-1], dp[i-1][j-1])) + 1;
                maxSide = Math.max(maxSide, dp[i][j]);
            }
    return maxSide * maxSide;
}
```

---

## 30. String DP
**Tier 3**

### Pattern
`dp[i][j]` = some property of `s1[0..i-1]` and `s2[0..j-1]`. Base cases: empty prefix. Recurrence based on whether characters match.

### Java Skeleton
```java
int m = s1.length(), n = s2.length();
int[][] dp = new int[m+1][n+1];
// base cases
for (int i = 1; i <= m; i++)
    for (int j = 1; j <= n; j++)
        if (s1.charAt(i-1) == s2.charAt(j-1)) dp[i][j] = dp[i-1][j-1] + 1;
        else dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
```

### Famous Problems

#### 1. Longest Common Subsequence (LC 1143)
> Read: 0 | Coded: 0

**Problem**: Given two strings text1 and text2 , return the length of their longest common subsequence . If there is no common subsequence , return 0 . A subsequence of a string is a new string generated from the original string with some characters (can be none) deleted without changing the relative order of the remaining characters. For example, "ace" is a subsequence of "abcde" . A common subsequence of two strings is a subsequence that is common to both strings.

**Approach**: Match → `dp[i][j] = dp[i-1][j-1] + 1`. No match → `max(dp[i-1][j], dp[i][j-1])`.
```java
public int longestCommonSubsequence(String text1, String text2) {
    int m = text1.length(), n = text2.length();
    int[][] dp = new int[m+1][n+1];
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++)
            dp[i][j] = text1.charAt(i-1) == text2.charAt(j-1)
                ? dp[i-1][j-1] + 1
                : Math.max(dp[i-1][j], dp[i][j-1]);
    return dp[m][n];
}
```

#### 2. Edit Distance (LC 72)
> Read: 0 | Coded: 0

**Problem**: Given two strings word1 and word2 , return the minimum number of operations required to convert word1 to word2 . You have the following three operations permitted on a word: Insert a character Delete a character Replace a character

**Approach**: Match → carry forward. No match → `1 + min(insert, delete, replace)`.
```java
public int minDistance(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    int[][] dp = new int[m+1][n+1];
    for (int i = 0; i <= m; i++) dp[i][0] = i;
    for (int j = 0; j <= n; j++) dp[0][j] = j;
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++)
            if (word1.charAt(i-1) == word2.charAt(j-1)) dp[i][j] = dp[i-1][j-1];
            else dp[i][j] = 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
    return dp[m][n];
}
```

#### 3. Palindromic Substrings (LC 647)
> Read: 0 | Coded: 0

**Problem**: Given a string s , return the number of palindromic substrings in it . A string is a palindrome when it reads the same backward as forward. A substring is a contiguous sequence of characters within the string.

**Approach**: `dp[i][j]` = is `s[i..j]` a palindrome. Count all true entries.
```java
public int countSubstrings(String s) {
    int n = s.length(), count = 0;
    boolean[][] dp = new boolean[n][n];
    for (int len = 1; len <= n; len++) {
        for (int i = 0; i <= n - len; i++) {
            int j = i + len - 1;
            dp[i][j] = s.charAt(i) == s.charAt(j) && (len <= 2 || dp[i+1][j-1]);
            if (dp[i][j]) count++;
        }
    }
    return count;
}
```

---

## 31. Queues / Monotonic Queue
**Tier 3**

### Pattern
Deque (double-ended queue) where we remove elements from the back that can never be the answer, and remove from the front when they leave the window. The front always holds the optimal element.

### Java Skeleton
```java
Deque<Integer> dq = new ArrayDeque<>(); // stores indices
for (int i = 0; i < n; i++) {
    // Remove out-of-window elements from front
    while (!dq.isEmpty() && dq.peekFirst() < i - k + 1) dq.pollFirst();
    // Maintain decreasing order: remove smaller elements from back
    while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) dq.pollLast();
    dq.offerLast(i);
    if (i >= k - 1) result[i-k+1] = nums[dq.peekFirst()]; // front is window max
}
```

### Famous Problems

#### 1. Sliding Window Maximum (LC 239)
> Read: 0 | Coded: 0

**Problem**: You are given an array of integers nums , there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position. Return the max sliding window .

**Approach**: Monotonic decreasing deque of indices. Front = max of current window.
```java
public int[] maxSlidingWindow(int[] nums, int k) {
    int n = nums.length;
    int[] result = new int[n - k + 1];
    Deque<Integer> dq = new ArrayDeque<>();
    for (int i = 0; i < n; i++) {
        while (!dq.isEmpty() && dq.peekFirst() < i - k + 1) dq.pollFirst();
        while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) dq.pollLast();
        dq.offerLast(i);
        if (i >= k - 1) result[i - k + 1] = nums[dq.peekFirst()];
    }
    return result;
}
```

#### 2. Design Circular Queue (LC 622)
> Read: 0 | Coded: 0

**Problem**: Design your implementation of the circular queue. The circular queue is a linear data structure in which the operations are performed based on FIFO (First In First Out) principle, and the last position is connected back to the first position to make a circle. It is also called "Ring Buffer". One of the benefits of the circular queue is that we can make use of the spaces in front of the queue. In a normal queue, once the queue becomes full, we cannot insert the next element even if there is a space in front of the queue. But using the circular queue, we can use the space to store new values. Implement the MyCircularQueue class: MyCircularQueue(k) Initializes the object with the size of the queue to be k . int Front() Gets the front item from the queue. If the queue is empty, return -1 . int Rear() Gets the last item from the queue. If the queue is empty, return -1 . boolean enQueue(int value) Inserts an element into the circular queue. Return true if the operation is successful. boolean deQueue() Deletes an element from the circular queue. Return true if the operation is successful. boolean isEmpty() Checks whether the circular queue is empty or not. boolean isFull() Checks whether the circular queue is full or not. You must solve the problem without using the built-in queue data structure in your programming language.

**Approach**: Ring buffer with `head`, `tail` pointers and a `size` counter.
```java
class MyCircularQueue {
    int[] data; int head, tail, size, capacity;
    public MyCircularQueue(int k) { data = new int[k]; capacity = k; }
    public boolean enQueue(int val) {
        if (isFull()) return false;
        data[tail] = val; tail = (tail + 1) % capacity; size++; return true;
    }
    public boolean deQueue() {
        if (isEmpty()) return false; head = (head+1) % capacity; size--; return true;
    }
    public int Front() { return isEmpty() ? -1 : data[head]; }
    public int Rear()  { return isEmpty() ? -1 : data[(tail-1+capacity) % capacity]; }
    public boolean isEmpty() { return size == 0; }
    public boolean isFull()  { return size == capacity; }
}
```

#### 3. Jump Game VI (LC 1696)
> Read: 0 | Coded: 0

**Problem**: You are given a 0-indexed integer array nums and an integer k . You are initially standing at index 0 . In one move, you can jump at most k steps forward without going outside the boundaries of the array. That is, you can jump from index i to any index in the range [i + 1, min(n - 1, i + k)] inclusive . You want to reach the last index of the array (index n - 1 ). Your score is the sum of all nums[j] for each index j you visited in the array. Return the maximum score you can get .

**Approach**: DP where `dp[i] = nums[i] + max(dp[i-k..i-1])`. Use monotonic deque for range max.
```java
public int maxResult(int[] nums, int k) {
    int n = nums.length;
    int[] dp = new int[n];
    dp[0] = nums[0];
    Deque<Integer> dq = new ArrayDeque<>();
    dq.offerLast(0);
    for (int i = 1; i < n; i++) {
        while (!dq.isEmpty() && dq.peekFirst() < i - k) dq.pollFirst();
        dp[i] = nums[i] + dp[dq.peekFirst()];
        while (!dq.isEmpty() && dp[dq.peekLast()] <= dp[i]) dq.pollLast();
        dq.offerLast(i);
    }
    return dp[n-1];
}
```

---

## 32. BST / Ordered Set
**Tier 3**

### Pattern
BST property: `left < root < right`. Inorder traversal yields sorted order. Use `long` min/max bounds for validation.

### Java Skeleton
```java
// Validate BST
boolean isValid(TreeNode node, long min, long max) {
    if (node == null) return true;
    if (node.val <= min || node.val >= max) return false;
    return isValid(node.left, min, node.val) && isValid(node.right, node.val, max);
}
// Inorder traversal (iterative)
Deque<TreeNode> stack = new ArrayDeque<>();
TreeNode curr = root;
while (curr != null || !stack.isEmpty()) {
    while (curr != null) { stack.push(curr); curr = curr.left; }
    curr = stack.pop();
    // process curr
    curr = curr.right;
}
```

### Famous Problems

#### 1. Validate Binary Search Tree (LC 98)
> Read: 0 | Coded: 0

**Problem**: Given the root of a binary tree, determine if it is a valid binary search tree (BST) . A valid BST is defined as follows: The left subtree of a node contains only nodes with keys strictly less than the node's key. The right subtree of a node contains only nodes with keys strictly greater than the node's key. Both the left and right subtrees must also be binary search trees.

**Approach**: Pass min/max bounds down recursion; each node must lie strictly within bounds.
```java
public boolean isValidBST(TreeNode root) {
    return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
}
private boolean validate(TreeNode node, long min, long max) {
    if (node == null) return true;
    if (node.val <= min || node.val >= max) return false;
    return validate(node.left, min, node.val) && validate(node.right, node.val, max);
}
```

#### 2. Kth Smallest Element in a BST (LC 230)
> Read: 0 | Coded: 0

**Problem**: Given the root of a binary search tree, and an integer k , return the k th smallest value ( 1-indexed ) of all the values of the nodes in the tree .

**Approach**: Inorder traversal (sorted order); return kth element.
```java
public int kthSmallest(TreeNode root, int k) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) { stack.push(curr); curr = curr.left; }
        curr = stack.pop();
        if (--k == 0) return curr.val;
        curr = curr.right;
    }
    return -1;
}
```

#### 3. Binary Search Tree Iterator (LC 173)
> Read: 0 | Coded: 0

**Problem**: Implement the BSTIterator class that represents an iterator over the in-order traversal of a binary search tree (BST): BSTIterator(TreeNode root) Initializes an object of the BSTIterator class. The root of the BST is given as part of the constructor. The pointer should be initialized to a non-existent number smaller than any element in the BST. boolean hasNext() Returns true if there exists a number in the traversal to the right of the pointer, otherwise returns false . int next() Moves the pointer to the right, then returns the number at the pointer. Notice that by initializing the pointer to a non-existent smallest number, the first call to next() will return the smallest element in the BST. You may assume that next() calls will always be valid. That is, there will be at least a next number in the in-order traversal when next() is called.

**Approach**: Controlled inorder traversal with an explicit stack; `hasNext` is O(1) amortized.
```java
class BSTIterator {
    Deque<TreeNode> stack = new ArrayDeque<>();
    public BSTIterator(TreeNode root) { pushLeft(root); }
    private void pushLeft(TreeNode node) {
        while (node != null) { stack.push(node); node = node.left; }
    }
    public int next() {
        TreeNode node = stack.pop();
        pushLeft(node.right);
        return node.val;
    }
    public boolean hasNext() { return !stack.isEmpty(); }
}
```

---

## 33. Greedy
**Tier 3**

### Pattern
Make the locally optimal choice at each step. Works when the greedy choice property holds (local optimum leads to global optimum). Often needs sorting first.

### Java Skeleton
```java
Arrays.sort(arr, comparator); // sort by some greedy criterion
for (element : arr) {
    if (/* locally feasible */) {
        take(element);
    }
}
```

### Famous Problems

#### 1. Jump Game (LC 55)
> Read: 0 | Coded: 0

**Problem**: You are given an integer array nums . You are initially positioned at the array's first index , and each element in the array represents your maximum jump length at that position. Return true if you can reach the last index, or false otherwise .

**Approach**: Track the farthest reachable index. If `i > farthest`, return false.
```java
public boolean canJump(int[] nums) {
    int farthest = 0;
    for (int i = 0; i < nums.length; i++) {
        if (i > farthest) return false;
        farthest = Math.max(farthest, i + nums[i]);
    }
    return true;
}
```

#### 2. Gas Station (LC 134)
> Read: 0 | Coded: 0

**Problem**: There are n gas stations along a circular route, where the amount of gas at the i th station is gas[i] . You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the i th station to its next (i + 1) th station. You begin the journey with an empty tank at one of the gas stations. Given two integer arrays gas and cost , return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1 . If there exists a solution, it is guaranteed to be unique .

**Approach**: If total gas >= total cost, a solution exists. Start where the running tank sum hits its minimum (after that point, prefix sum only goes up).
```java
public int canCompleteCircuit(int[] gas, int[] cost) {
    int total = 0, tank = 0, start = 0;
    for (int i = 0; i < gas.length; i++) {
        int net = gas[i] - cost[i];
        total += net; tank += net;
        if (tank < 0) { start = i + 1; tank = 0; }
    }
    return total >= 0 ? start : -1;
}
```

#### 3. Task Scheduler (LC 621)
> Read: 0 | Coded: 0

**Problem**: You are given an array of CPU tasks , each labeled with a letter from A to Z, and a number n . Each CPU interval can be idle or allow the completion of one task. Tasks can be completed in any order, but there's a constraint: there has to be a gap of at least n intervals between two tasks with the same label. Return the minimum number of CPU intervals required to complete all tasks.

**Approach**: The most frequent task determines the minimum idle time. Result = `max(n * (maxFreq-1) + maxCount, tasks.length)`.
```java
public int leastInterval(char[] tasks, int n) {
    int[] freq = new int[26];
    for (char t : tasks) freq[t-'A']++;
    int maxFreq = Arrays.stream(freq).max().getAsInt();
    int maxCount = (int) Arrays.stream(freq).filter(f -> f == maxFreq).count();
    return Math.max(tasks.length, (maxFreq - 1) * (n + 1) + maxCount);
}
```

---

## 34. Merge Sort
**Tier 3**

### Pattern
Divide array in half, recursively sort each half, merge two sorted halves. Stable sort, O(n log n) guaranteed. Also enables counting inversions during the merge step.

### Java Skeleton
```java
void mergeSort(int[] arr, int lo, int hi) {
    if (lo >= hi) return;
    int mid = lo + (hi - lo) / 2;
    mergeSort(arr, lo, mid);
    mergeSort(arr, mid+1, hi);
    merge(arr, lo, mid, hi);
}
void merge(int[] arr, int lo, int mid, int hi) {
    int[] temp = Arrays.copyOfRange(arr, lo, hi+1);
    int i = 0, j = mid-lo+1, k = lo;
    while (i <= mid-lo && j <= hi-lo) {
        if (temp[i] <= temp[j]) arr[k++] = temp[i++];
        else arr[k++] = temp[j++];
    }
    while (i <= mid-lo) arr[k++] = temp[i++];
    while (j <= hi-lo) arr[k++] = temp[j++];
}
```

### Famous Problems

#### 1. Sort an Array (LC 912)
> Read: 0 | Coded: 0

**Problem**: Given an array of integers nums , sort the array in ascending order and return it. You must solve the problem without using any built-in functions in O(nlog(n)) time complexity and with the smallest space complexity possible.

**Approach**: Straightforward merge sort implementation.
```java
public int[] sortArray(int[] nums) {
    mergeSort(nums, 0, nums.length - 1);
    return nums;
}
private void mergeSort(int[] arr, int lo, int hi) {
    if (lo >= hi) return;
    int mid = lo + (hi - lo) / 2;
    mergeSort(arr, lo, mid); mergeSort(arr, mid+1, hi);
    merge(arr, lo, mid, hi);
}
private void merge(int[] arr, int lo, int mid, int hi) {
    int[] temp = Arrays.copyOfRange(arr, lo, hi+1);
    int i=0, j=mid-lo+1, k=lo;
    while (i<=mid-lo && j<=hi-lo) arr[k++] = temp[i]<=temp[j] ? temp[i++] : temp[j++];
    while (i<=mid-lo) arr[k++]=temp[i++];
    while (j<=hi-lo) arr[k++]=temp[j++];
}
```

#### 2. Reverse Pairs (LC 493)
> Read: 0 | Coded: 0

**Problem**: Given an integer array nums , return the number of reverse pairs in the array . A reverse pair is a pair (i, j) where: 0 <= i < j < nums.length and nums[i] > 2 * nums[j] .

**Approach**: Count inversions during merge. Before merging, count pairs `(i,j)` where `nums[i] > 2*nums[j]`.
```java
public int reversePairs(int[] nums) { return mergeCount(nums, 0, nums.length-1); }
private int mergeCount(int[] arr, int lo, int hi) {
    if (lo >= hi) return 0;
    int mid = lo + (hi-lo)/2;
    int count = mergeCount(arr, lo, mid) + mergeCount(arr, mid+1, hi);
    int j = mid+1;
    for (int i = lo; i <= mid; i++) {
        while (j <= hi && arr[i] > 2L * arr[j]) j++;
        count += j - (mid+1);
    }
    // standard merge
    int[] temp = Arrays.copyOfRange(arr, lo, hi+1);
    int a=0, b=mid-lo+1, k=lo;
    while (a<=mid-lo && b<=hi-lo) arr[k++] = temp[a]<=temp[b] ? temp[a++] : temp[b++];
    while (a<=mid-lo) arr[k++]=temp[a++];
    while (b<=hi-lo) arr[k++]=temp[b++];
    return count;
}
```

#### 3. Merge Sorted Array (LC 88)
> Read: 0 | Coded: 0

**Problem**: You are given two integer arrays nums1 and nums2 , sorted in non-decreasing order , and two integers m and n , representing the number of elements in nums1 and nums2 respectively. Merge nums1 and nums2 into a single array sorted in non-decreasing order . The final sorted array should not be returned by the function, but instead be stored inside the array nums1 . To accommodate this, nums1 has a length of m + n , where the first m elements denote the elements that should be merged, and the last n elements are set to 0 and should be ignored. nums2 has a length of n .

**Approach**: Merge from right to avoid overwriting. Three pointers: end of nums1, end of nums2, end of result.
```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int i = m-1, j = n-1, k = m+n-1;
    while (i >= 0 && j >= 0)
        nums1[k--] = nums1[i] >= nums2[j] ? nums1[i--] : nums2[j--];
    while (j >= 0) nums1[k--] = nums2[j--];
}
```

---

## 35. Bit Manipulation
**Tier 4**

### Pattern
Key operations: XOR (`^`), AND (`&`), OR (`|`), shift (`<<`, `>>`). XOR: `a^a=0`, `a^0=a`. Isolate lowest set bit: `n & (-n)`. Clear lowest set bit: `n & (n-1)`.

### Java Skeleton
```java
// Check bit i:       (n >> i) & 1
// Set bit i:         n | (1 << i)
// Clear bit i:       n & ~(1 << i)
// Count set bits:    Integer.bitCount(n)
// Isolate lowest:    n & (-n)
// Clear lowest:      n & (n-1)
```

### Famous Problems

#### 1. Single Number (LC 136)
> Read: 0 | Coded: 0

**Problem**: Given a non-empty array of integers nums , every element appears twice except for one. Find that single one. You must implement a solution with a linear runtime complexity and use only constant extra space.

**Approach**: XOR all elements. Pairs cancel out; only the unique number remains.
```java
public int singleNumber(int[] nums) {
    int result = 0;
    for (int n : nums) result ^= n;
    return result;
}
```

#### 2. Number of 1 Bits (LC 191)
> Read: 0 | Coded: 0

**Problem**: Given a positive integer n , write a function that returns the number of set bits in its binary representation (also known as the Hamming weight ).

**Approach**: Repeatedly clear the lowest set bit with `n & (n-1)`, counting iterations.
```java
public int hammingWeight(int n) {
    int count = 0;
    while (n != 0) { n &= (n - 1); count++; }
    return count;
}
```

#### 3. Missing Number (LC 268)
> Read: 0 | Coded: 0

**Problem**: Given an array nums containing n distinct numbers in the range [0, n] , return the only number in the range that is missing from the array.

**Approach**: XOR indices 0..n with all array values. Missing number is what remains (not cancelled).
```java
public int missingNumber(int[] nums) {
    int result = nums.length;
    for (int i = 0; i < nums.length; i++) result ^= i ^ nums[i];
    return result;
}
```

---

## 36. Matrix
**Tier 4**

### Pattern
2D array traversal with boundary tracking. Rotate: transpose then reverse rows. Spiral: maintain 4 boundaries shrinking inward.

### Java Skeleton
```java
int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
for (int[] d : dirs) {
    int nr = r + d[0], nc = c + d[1];
    if (nr >= 0 && nr < m && nc >= 0 && nc < n) {
        // process (nr, nc)
    }
}
```

### Famous Problems

#### 1. Spiral Matrix (LC 54)
> Read: 0 | Coded: 0

**Problem**: Given an m x n matrix , return all elements of the matrix in spiral order .

**Approach**: Maintain top/bottom/left/right boundaries, shrink after each direction pass.
```java
public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> res = new ArrayList<>();
    int top=0, bottom=matrix.length-1, left=0, right=matrix[0].length-1;
    while (top<=bottom && left<=right) {
        for (int c=left; c<=right; c++) res.add(matrix[top][c]); top++;
        for (int r=top; r<=bottom; r++) res.add(matrix[r][right]); right--;
        if (top<=bottom) { for (int c=right; c>=left; c--) res.add(matrix[bottom][c]); bottom--; }
        if (left<=right) { for (int r=bottom; r>=top; r--) res.add(matrix[r][left]); left++; }
    }
    return res;
}
```

#### 2. Rotate Image (LC 48)
> Read: 0 | Coded: 0

**Problem**: You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise). You have to rotate the image in-place , which means you have to modify the input 2D matrix directly. DO NOT allocate another 2D matrix and do the rotation.

**Approach**: Transpose (swap across main diagonal), then reverse each row.
```java
public void rotate(int[][] matrix) {
    int n = matrix.length;
    // Transpose
    for (int i = 0; i < n; i++)
        for (int j = i+1; j < n; j++) { int t=matrix[i][j]; matrix[i][j]=matrix[j][i]; matrix[j][i]=t; }
    // Reverse each row
    for (int[] row : matrix) {
        int lo=0, hi=n-1;
        while (lo<hi) { int t=row[lo]; row[lo++]=row[hi]; row[hi--]=t; }
    }
}
```

#### 3. Set Matrix Zeroes (LC 73)
> Read: 0 | Coded: 0

**Problem**: Given an m x n integer matrix matrix , if an element is 0 , set its entire row and column to 0 's. You must do it in place .

**Approach**: Use first row and first column as markers to avoid extra space. Handle first row/col separately.
```java
public void setZeroes(int[][] matrix) {
    int m=matrix.length, n=matrix[0].length;
    boolean firstRow=false, firstCol=false;
    for (int j=0; j<n; j++) if (matrix[0][j]==0) firstRow=true;
    for (int i=0; i<m; i++) if (matrix[i][0]==0) firstCol=true;
    for (int i=1; i<m; i++) for (int j=1; j<n; j++) if (matrix[i][j]==0) { matrix[i][0]=0; matrix[0][j]=0; }
    for (int i=1; i<m; i++) for (int j=1; j<n; j++) if (matrix[i][0]==0||matrix[0][j]==0) matrix[i][j]=0;
    if (firstRow) Arrays.fill(matrix[0], 0);
    if (firstCol) for (int i=0; i<m; i++) matrix[i][0]=0;
}
```

---

## 37. K-Way Merge
**Tier 4**

### Pattern
Min-heap initialized with one element from each sorted list. Each pop yields the global minimum; push the next element from the same source.

### Java Skeleton
```java
// heap entry: [value, listIndex, elementIndex]
PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0]-b[0]);
for (int i = 0; i < k; i++) if (lists[i] != null) pq.offer(new int[]{lists[i].val, i, 0});
while (!pq.isEmpty()) {
    int[] curr = pq.poll();
    // use curr[0]
    // push next element from same list if available
}
```

### Famous Problems

#### 1. Merge k Sorted Lists (LC 23)
> Read: 0 | Coded: 0

**Problem**: You are given an array of k linked-lists lists , each linked-list is sorted in ascending order. Merge all the linked-lists into one sorted linked-list and return it.

**Approach**: Min-heap of list nodes by value. Always extend the list that yielded the minimum.
```java
public ListNode mergeKLists(ListNode[] lists) {
    PriorityQueue<ListNode> pq = new PriorityQueue<>((a,b) -> a.val - b.val);
    for (ListNode node : lists) if (node != null) pq.offer(node);
    ListNode dummy = new ListNode(0), curr = dummy;
    while (!pq.isEmpty()) {
        curr.next = pq.poll(); curr = curr.next;
        if (curr.next != null) pq.offer(curr.next);
    }
    return dummy.next;
}
```

#### 2. Kth Smallest Element in a Sorted Matrix (LC 378)
> Read: 0 | Coded: 0

**Problem**: Given an n x n matrix where each of the rows and columns is sorted in ascending order, return the k th smallest element in the matrix . Note that it is the k th smallest element in the sorted order , not the k th distinct element. You must find a solution with a memory complexity better than O(n 2 ) .

**Approach**: Min-heap initialized with first column. Pop k times, pushing the element to the right each time.
```java
public int kthSmallest(int[][] matrix, int k) {
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> matrix[a[0]][a[1]] - matrix[b[0]][b[1]]);
    for (int i = 0; i < matrix.length; i++) pq.offer(new int[]{i, 0});
    int result = 0;
    while (k-- > 0) {
        int[] curr = pq.poll();
        result = matrix[curr[0]][curr[1]];
        if (curr[1]+1 < matrix[0].length) pq.offer(new int[]{curr[0], curr[1]+1});
    }
    return result;
}
```

#### 3. Smallest Range Covering Elements from K Lists (LC 632)
> Read: 0 | Coded: 0

**Problem**: You have k lists of sorted integers in non-decreasing order . Find the smallest range that includes at least one number from each of the k lists. We define the range [a, b] is smaller than range [c, d] if b - a < d - c or a < c if b - a == d - c .

**Approach**: Min-heap with one element from each list. Track current max. Range = [min, max]. Advance the list containing min.
```java
public int[] smallestRange(List<List<Integer>> nums) {
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[0]-b[0]); // [val, listIdx, elemIdx]
    int curMax = Integer.MIN_VALUE;
    for (int i = 0; i < nums.size(); i++) {
        pq.offer(new int[]{nums.get(i).get(0), i, 0});
        curMax = Math.max(curMax, nums.get(i).get(0));
    }
    int[] res = {0, Integer.MAX_VALUE};
    while (pq.size() == nums.size()) {
        int[] curr = pq.poll();
        if (curMax - curr[0] < res[1] - res[0]) res = new int[]{curr[0], curMax};
        if (curr[2]+1 < nums.get(curr[1]).size()) {
            int next = nums.get(curr[1]).get(curr[2]+1);
            pq.offer(new int[]{next, curr[1], curr[2]+1});
            curMax = Math.max(curMax, next);
        }
    }
    return res;
}
```

---

## 38. Shortest Path
**Tier 4**

### Pattern
- **Dijkstra** (non-negative weights): min-heap `(dist, node)`. Skip stale entries.
- **Bellman-Ford** (negative weights / limited hops): relax all edges `V-1` times (or `K` times for K-stop constraint).

### Java Skeleton
```java
// Dijkstra
int[] dist = new int[n]; Arrays.fill(dist, Integer.MAX_VALUE); dist[src]=0;
PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[0]-b[0]);
pq.offer(new int[]{0, src});
while (!pq.isEmpty()) {
    int[] cur = pq.poll(); int d=cur[0], u=cur[1];
    if (d > dist[u]) continue; // stale
    for (int[] e : graph.get(u)) { // e = [neighbor, weight]
        if (dist[u]+e[1] < dist[e[0]]) { dist[e[0]]=dist[u]+e[1]; pq.offer(new int[]{dist[e[0]],e[0]}); }
    }
}
```

### Famous Problems

#### 1. Network Delay Time (LC 743) — Dijkstra
> Read: 0 | Coded: 0

**Problem**: You are given a network of n nodes, labeled from 1 to n . You are also given times , a list of travel times as directed edges times[i] = (u i , v i , w i ) , where u i is the source node, v i is the target node, and w i is the time it takes for a signal to travel from source to target. We will send a signal from a given node k . Return the minimum time it takes for all the n nodes to receive the signal . If it is impossible for all the n nodes to receive the signal, return -1 .

**Approach**: Single-source shortest path; return max dist (time for signal to reach all nodes).
```java
public int networkDelayTime(int[][] times, int n, int k) {
    List<List<int[]>> graph = new ArrayList<>();
    for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());
    for (int[] t : times) graph.get(t[0]).add(new int[]{t[1], t[2]});
    int[] dist = new int[n+1]; Arrays.fill(dist, Integer.MAX_VALUE); dist[k]=0;
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[0]-b[0]);
    pq.offer(new int[]{0, k});
    while (!pq.isEmpty()) {
        int[] cur = pq.poll(); int d=cur[0], u=cur[1];
        if (d > dist[u]) continue;
        for (int[] e : graph.get(u)) if (dist[u]+e[1] < dist[e[0]]) { dist[e[0]]=dist[u]+e[1]; pq.offer(new int[]{dist[e[0]],e[0]}); }
    }
    int max = Arrays.stream(dist, 1, n+1).max().getAsInt();
    return max == Integer.MAX_VALUE ? -1 : max;
}
```

#### 2. Cheapest Flights Within K Stops (LC 787) — Bellman-Ford
> Read: 0 | Coded: 0

**Problem**: There are n cities connected by some number of flights. You are given an array flights where flights[i] = [from i , to i , price i ] indicates that there is a flight from city from i to city to i with cost price i . You are also given three integers src , dst , and k , return the cheapest price from src to dst with at most k stops. If there is no such route, return -1 .

**Approach**: Relax edges exactly `K+1` times (K stops = K+1 edges). Use previous round's distances to avoid chaining.
```java
public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    int[] dist = new int[n]; Arrays.fill(dist, Integer.MAX_VALUE); dist[src]=0;
    for (int i = 0; i <= k; i++) {
        int[] temp = dist.clone();
        for (int[] f : flights)
            if (dist[f[0]] != Integer.MAX_VALUE && dist[f[0]]+f[2] < temp[f[1]])
                temp[f[1]] = dist[f[0]] + f[2];
        dist = temp;
    }
    return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
}
```

#### 3. Path With Minimum Effort (LC 1631) — Dijkstra on grid
> Read: 0 | Coded: 0

**Problem**: You are a hiker preparing for an upcoming hike. You are given heights , a 2D array of size rows x columns , where heights[row][col] represents the height of cell (row, col) . You are situated in the top-left cell, (0, 0) , and you hope to travel to the bottom-right cell, (rows-1, columns-1) (i.e., 0-indexed ). You can move up , down , left , or right , and you wish to find a route that requires the minimum effort . A route's effort is the maximum absolute difference in heights between two consecutive cells of the route. Return the minimum effort required to travel from the top-left cell to the bottom-right cell.

**Approach**: Dijkstra where edge weight = absolute height difference. Minimize max edge on path.
```java
public int minimumEffortPath(int[][] heights) {
    int m=heights.length, n=heights[0].length;
    int[][] effort = new int[m][n];
    for (int[] row : effort) Arrays.fill(row, Integer.MAX_VALUE);
    effort[0][0]=0;
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[0]-b[0]);
    pq.offer(new int[]{0,0,0});
    int[][] dirs={{0,1},{0,-1},{1,0},{-1,0}};
    while (!pq.isEmpty()) {
        int[] cur=pq.poll(); int e=cur[0], r=cur[1], c=cur[2];
        if (r==m-1&&c==n-1) return e;
        if (e>effort[r][c]) continue;
        for (int[] d:dirs) {
            int nr=r+d[0], nc=c+d[1];
            if (nr>=0&&nr<m&&nc>=0&&nc<n) {
                int ne=Math.max(e, Math.abs(heights[nr][nc]-heights[r][c]));
                if (ne<effort[nr][nc]) { effort[nr][nc]=ne; pq.offer(new int[]{ne,nr,nc}); }
            }
        }
    }
    return 0;
}
```

---

## 39. Minimum Spanning Tree
**Tier 4**

### Pattern
- **Kruskal's**: Sort edges by weight, add if it doesn't form a cycle (Union Find). O(E log E).
- **Prim's**: Grow MST from any start node using a min-heap of frontier edges. O(E log V).

### Java Skeleton
```java
// Kruskal's
Arrays.sort(edges, (a,b) -> a[2]-b[2]); // sort by weight
int cost = 0, edgesUsed = 0;
for (int[] edge : edges) {
    if (union(edge[0], edge[1])) { // no cycle
        cost += edge[2]; edgesUsed++;
        if (edgesUsed == n-1) break;
    }
}
```

### Famous Problems

#### 1. Min Cost to Connect All Points (LC 1584) — Prim's
> Read: 0 | Coded: 0

**Problem**: You are given an array points representing integer coordinates of some points on a 2D-plane, where points[i] = [x i , y i ] . The cost of connecting two points [x i , y i ] and [x j , y j ] is the manhattan distance between them: |x i - x j | + |y i - y j | , where |val| denotes the absolute value of val . Return the minimum cost to make all points connected. All points are connected if there is exactly one simple path between any two points.

**Approach**: All pairs form edges (Manhattan distance). Prim's with a min-heap.
```java
public int minCostConnectPoints(int[][] points) {
    int n = points.length;
    boolean[] visited = new boolean[n];
    int[] minDist = new int[n]; Arrays.fill(minDist, Integer.MAX_VALUE); minDist[0]=0;
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[0]-b[0]);
    pq.offer(new int[]{0,0});
    int total=0, count=0;
    while (count < n) {
        int[] cur=pq.poll(); int d=cur[0], u=cur[1];
        if (visited[u]) continue;
        visited[u]=true; total+=d; count++;
        for (int v=0; v<n; v++) if (!visited[v]) {
            int dist=Math.abs(points[u][0]-points[v][0])+Math.abs(points[u][1]-points[v][1]);
            if (dist<minDist[v]) { minDist[v]=dist; pq.offer(new int[]{dist,v}); }
        }
    }
    return total;
}
```

#### 2. Optimize Water Distribution in a Village (LC 1168) — Kruskal's
> Read: 0 | Coded: 0

**Problem**: There are n houses in a village. We want to supply water for all the houses by building wells and laying pipes. For each house i, we can either build a well inside it directly with cost wells[i], or pipe in water from another well to it. The costs to lay pipes between houses are given by the array pipes where each pipes[j] = [house1j, house2j, costj] represents the cost to connect house1j and house2j together using a pipe. Connections are bidirectional, and there could be multiple valid connections between the same two houses with different costs. Return the minimum total cost to supply water to all houses.

**Approach**: Add virtual node 0 with edge (0, i, well[i]) for each village. Find MST of the augmented graph.
```java
public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
    List<int[]> edges = new ArrayList<>();
    for (int i = 0; i < wells.length; i++) edges.add(new int[]{0, i+1, wells[i]});
    for (int[] p : pipes) edges.add(new int[]{p[0], p[1], p[2]});
    edges.sort((a,b)->a[2]-b[2]);
    int[] parent = new int[n+1]; for (int i=0;i<=n;i++) parent[i]=i;
    int cost=0;
    for (int[] e : edges) {
        int pu=find(parent,e[0]), pv=find(parent,e[1]);
        if (pu!=pv) { parent[pu]=pv; cost+=e[2]; }
    }
    return cost;
}
private int find(int[] p, int x) { return p[x]==x?x:(p[x]=find(p,p[x])); }
```

---

## 40. LIS (Longest Increasing Subsequence)
**Tier 4**

### Pattern
**O(n²) DP**: `dp[i] = max(dp[j]+1)` for all `j < i` where `nums[j] < nums[i]`.
**O(n log n) Patience Sorting**: Maintain `tails` array. Binary search to replace or extend.

### Java Skeleton
```java
// O(n log n) via patience sort
List<Integer> tails = new ArrayList<>();
for (int num : nums) {
    int lo=0, hi=tails.size();
    while (lo < hi) { int mid=lo+(hi-lo)/2; if (tails.get(mid)<num) lo=mid+1; else hi=mid; }
    if (lo == tails.size()) tails.add(num);
    else tails.set(lo, num);
}
return tails.size();
```

### Famous Problems

#### 1. Longest Increasing Subsequence (LC 300)
> Read: 0 | Coded: 0

**Problem**: Given an integer array nums , return the length of the longest strictly increasing subsequence .

**Approach**: Patience sort — binary search insertion into `tails`.
```java
public int lengthOfLIS(int[] nums) {
    List<Integer> tails = new ArrayList<>();
    for (int num : nums) {
        int lo=0, hi=tails.size();
        while (lo<hi) { int mid=lo+(hi-lo)/2; if (tails.get(mid)<num) lo=mid+1; else hi=mid; }
        if (lo==tails.size()) tails.add(num); else tails.set(lo, num);
    }
    return tails.size();
}
```

#### 2. Russian Doll Envelopes (LC 354)
> Read: 0 | Coded: 0

**Problem**: You are given a 2D array of integers envelopes where envelopes[i] = [w i , h i ] represents the width and the height of an envelope. One envelope can fit into another if and only if both the width and height of one envelope are greater than the other envelope's width and height. Return the maximum number of envelopes you can Russian doll (i.e., put one inside the other) .

**Approach**: Sort by width ascending, then height **descending** (prevents width-ties from stacking). Find LIS of heights.
```java
public int maxEnvelopes(int[][] envelopes) {
    Arrays.sort(envelopes, (a,b) -> a[0]==b[0] ? b[1]-a[1] : a[0]-b[0]);
    List<Integer> tails = new ArrayList<>();
    for (int[] e : envelopes) {
        int h=e[1], lo=0, hi=tails.size();
        while (lo<hi) { int mid=lo+(hi-lo)/2; if (tails.get(mid)<h) lo=mid+1; else hi=mid; }
        if (lo==tails.size()) tails.add(h); else tails.set(lo,h);
    }
    return tails.size();
}
```

#### 3. Number of Longest Increasing Subsequence (LC 673)
> Read: 0 | Coded: 0

**Problem**: Given an integer array nums , return the number of longest increasing subsequences. Notice that the sequence has to be strictly increasing.

**Approach**: Track both `dp[i]` (LIS length ending at i) and `count[i]` (number of such LIS).
```java
public int findNumberOfLIS(int[] nums) {
    int n=nums.length, maxLen=0, result=0;
    int[] dp=new int[n], cnt=new int[n];
    Arrays.fill(dp,1); Arrays.fill(cnt,1);
    for (int i=0; i<n; i++) {
        for (int j=0; j<i; j++) if (nums[j]<nums[i]) {
            if (dp[j]+1 > dp[i]) { dp[i]=dp[j]+1; cnt[i]=cnt[j]; }
            else if (dp[j]+1==dp[i]) cnt[i]+=cnt[j];
        }
        maxLen=Math.max(maxLen,dp[i]);
    }
    for (int i=0; i<n; i++) if (dp[i]==maxLen) result+=cnt[i];
    return result;
}
```

---

## 41. Tree / Graph DP
**Tier 4**

### Pattern
Bottom-up DP on tree: compute results for children first, combine at the parent. Return an array `[take, skip]` or `[gain, loss]` from each node.

### Java Skeleton
```java
int[] dfs(TreeNode node) {
    if (node == null) return new int[]{0, 0}; // [rob, skip]
    int[] left = dfs(node.left);
    int[] right = dfs(node.right);
    int rob  = node.val + left[1] + right[1]; // take node, skip children
    int skip = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
    return new int[]{rob, skip};
}
```

### Famous Problems

#### 1. House Robber III (LC 337)
> Read: 0 | Coded: 0

**Problem**: The thief has found himself a new place for his thievery again. There is only one entrance to this area, called root . Besides the root , each house has one and only one parent house. After a tour, the smart thief realized that all houses in this place form a binary tree. It will automatically contact the police if two directly-linked houses were broken into on the same night . Given the root of the binary tree, return the maximum amount of money the thief can rob without alerting the police .

**Approach**: At each node return `[rob_this_node, skip_this_node]`. Parent picks the better.
```java
public int rob(TreeNode root) {
    int[] res = dfs(root);
    return Math.max(res[0], res[1]);
}
private int[] dfs(TreeNode node) {
    if (node == null) return new int[]{0,0};
    int[] l=dfs(node.left), r=dfs(node.right);
    int rob  = node.val + l[1] + r[1];
    int skip = Math.max(l[0],l[1]) + Math.max(r[0],r[1]);
    return new int[]{rob, skip};
}
```

#### 2. Binary Tree Maximum Path Sum (LC 124)
> Read: 0 | Coded: 0

**Problem**: A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node can only appear in the sequence at most once . Note that the path does not need to pass through the root. The path sum of a path is the sum of the node's values in the path. Given the root of a binary tree, return the maximum path sum of any non-empty path .

**Approach**: At each node, best path through it = node + max(left,0) + max(right,0). Return to parent only one arm.
```java
int maxSum = Integer.MIN_VALUE;
public int maxPathSum(TreeNode root) { gain(root); return maxSum; }
private int gain(TreeNode node) {
    if (node == null) return 0;
    int l = Math.max(gain(node.left), 0);
    int r = Math.max(gain(node.right), 0);
    maxSum = Math.max(maxSum, node.val + l + r);
    return node.val + Math.max(l, r); // return only one arm to parent
}
```

#### 3. Unique Binary Search Trees (LC 96)
> Read: 0 | Coded: 0

**Problem**: Given an integer n , return the number of structurally unique BST' s (binary search trees) which has exactly n nodes of unique values from 1 to n .

**Approach**: `dp[n]` = number of structurally unique BSTs with n nodes = sum of `dp[i-1] * dp[n-i]` for each root i.
```java
public int numTrees(int n) {
    int[] dp = new int[n+1];
    dp[0]=dp[1]=1;
    for (int nodes=2; nodes<=n; nodes++)
        for (int root=1; root<=nodes; root++)
            dp[nodes] += dp[root-1] * dp[nodes-root];
    return dp[n];
}
```

---

## 42. Data Structure Design
**Tier 4**

### Pattern
Combine two or more standard data structures to achieve the required time complexity. Common combos: HashMap + Doubly Linked List (LRU), HashMap + Heap (Task queues), Array + HashMap (O(1) random access + lookup).

### Famous Problems

#### 1. LRU Cache (LC 146)
> Read: 0 | Coded: 0

**Problem**: Design a data structure that follows the constraints of a Least Recently Used (LRU) cache . Implement the LRUCache class: LRUCache(int capacity) Initialize the LRU cache with positive size capacity . int get(int key) Return the value of the key if the key exists, otherwise return -1 . void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key. The functions get and put must each run in O(1) average time complexity.

**Approach**: HashMap for O(1) access + doubly linked list for O(1) move-to-front and eviction.
```java
class LRUCache {
    class Node { int key, val; Node prev, next; }
    Map<Integer, Node> map = new HashMap<>();
    Node head = new Node(), tail = new Node();
    int capacity;
    public LRUCache(int capacity) {
        this.capacity = capacity; head.next = tail; tail.prev = head;
    }
    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node n = map.get(key); remove(n); insertFront(n); return n.val;
    }
    public void put(int key, int value) {
        if (map.containsKey(key)) remove(map.get(key));
        Node n = new Node(); n.key=key; n.val=value;
        insertFront(n); map.put(key, n);
        if (map.size() > capacity) { Node lru=tail.prev; remove(lru); map.remove(lru.key); }
    }
    private void remove(Node n) { n.prev.next=n.next; n.next.prev=n.prev; }
    private void insertFront(Node n) { n.next=head.next; n.prev=head; head.next.prev=n; head.next=n; }
}
```

#### 2. Design Twitter (LC 355)
> Read: 0 | Coded: 0

**Problem**: Design a simplified version of Twitter where users can post tweets, follow/unfollow another user, and is able to see the 10 most recent tweets in the user's news feed. Implement the Twitter class: Twitter() Initializes your twitter object. void postTweet(int userId, int tweetId) Composes a new tweet with ID tweetId by the user userId . Each call to this function will be made with a unique tweetId . List<Integer> getNewsFeed(int userId) Retrieves the 10 most recent tweet IDs in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user themself. Tweets must be ordered from most recent to least recent . void follow(int followerId, int followeeId) The user with ID followerId started following the user with ID followeeId . void unfollow(int followerId, int followeeId) The user with ID followerId started unfollowing the user with ID followeeId .

**Approach**: HashMap for follows/tweets. Feed: collect recent tweets from followees + self into a max-heap by timestamp.
```java
class Twitter {
    int time = 0;
    Map<Integer, Set<Integer>> follows = new HashMap<>();
    Map<Integer, List<int[]>> tweets = new HashMap<>(); // userId -> [[time, tweetId]]
    public void postTweet(int userId, int tweetId) {
        tweets.computeIfAbsent(userId, k->new ArrayList<>()).add(new int[]{time++, tweetId});
    }
    public List<Integer> getNewsFeed(int userId) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->b[0]-a[0]); // max-heap by time
        Set<Integer> feed = new HashSet<>(follows.getOrDefault(userId, new HashSet<>()));
        feed.add(userId);
        for (int uid : feed) {
            List<int[]> ts = tweets.getOrDefault(uid, new ArrayList<>());
            if (!ts.isEmpty()) pq.offer(new int[]{ts.get(ts.size()-1)[0], ts.get(ts.size()-1)[1], uid, ts.size()-1});
        }
        List<Integer> res = new ArrayList<>();
        while (!pq.isEmpty() && res.size() < 10) {
            int[] cur = pq.poll(); res.add(cur[1]);
            if (cur[3] > 0) {
                List<int[]> ts = tweets.get(cur[2]);
                pq.offer(new int[]{ts.get(cur[3]-1)[0], ts.get(cur[3]-1)[1], cur[2], cur[3]-1});
            }
        }
        return res;
    }
    public void follow(int f, int e)   { follows.computeIfAbsent(f, k->new HashSet<>()).add(e); }
    public void unfollow(int f, int e) { follows.getOrDefault(f, new HashSet<>()).remove(e); }
}
```

#### 3. Design Hit Counter (LC 362)
> Read: 0 | Coded: 0

**Problem**: Design a hit counter which counts the number of hits received in the past 5 minutes (i.e., the past 300 seconds). Your system should accept a timestamp parameter (in seconds granularity), and you may assume that calls are being made to the system in chronological order (i.e., timestamp is monotonically increasing). Several hits may arrive roughly at the same time.

**Approach**: Queue of timestamps; expire hits older than 300 seconds on each call.
```java
class HitCounter {
    Deque<Integer> q = new ArrayDeque<>();
    public void hit(int timestamp) { q.offer(timestamp); }
    public int getHits(int timestamp) {
        while (!q.isEmpty() && timestamp - q.peekFirst() >= 300) q.pollFirst();
        return q.size();
    }
}
```

---

## 43. Bitmask DP
**Tier 4**

### Pattern
State = bitmask representing which elements have been processed. `dp[mask]` or `dp[mask][i]` stores the optimal value for the given subset state.

### Java Skeleton
```java
int n = items.length;
int[] dp = new int[1 << n];
Arrays.fill(dp, Integer.MAX_VALUE); dp[0] = 0;
for (int mask = 0; mask < (1 << n); mask++) {
    if (dp[mask] == Integer.MAX_VALUE) continue;
    for (int i = 0; i < n; i++) {
        if ((mask & (1 << i)) == 0) { // item i not yet in mask
            int newMask = mask | (1 << i);
            dp[newMask] = Math.min(dp[newMask], dp[mask] + cost(mask, i));
        }
    }
}
```

### Famous Problems

#### 1. Shortest Path Visiting All Nodes (LC 847)
> Read: 0 | Coded: 0

**Problem**: You have an undirected, connected graph of n nodes labeled from 0 to n - 1 . You are given an array graph where graph[i] is a list of all the nodes connected with node i by an edge. Return the length of the shortest path that visits every node . You may start and stop at any node, you may revisit nodes multiple times, and you may reuse edges.

**Approach**: BFS on states `(node, visitedMask)`. All-visited mask = `(1<<n)-1`.
```java
public int shortestPathLength(int[][] graph) {
    int n = graph.length, full = (1<<n)-1;
    Queue<int[]> q = new LinkedList<>();
    boolean[][] visited = new boolean[n][1<<n];
    for (int i=0; i<n; i++) { q.offer(new int[]{i, 1<<i, 0}); visited[i][1<<i]=true; }
    while (!q.isEmpty()) {
        int[] cur=q.poll(); int node=cur[0], mask=cur[1], dist=cur[2];
        if (mask==full) return dist;
        for (int nb : graph[node]) {
            int newMask=mask|(1<<nb);
            if (!visited[nb][newMask]) { visited[nb][newMask]=true; q.offer(new int[]{nb,newMask,dist+1}); }
        }
    }
    return -1;
}
```

#### 2. Minimum XOR Sum of Two Arrays (LC 2172)
> Read: 0 | Coded: 0

**Problem**: You are given two integer arrays nums1 and nums2 of length n . The XOR sum of the two integer arrays is (nums1[0] XOR nums2[0]) + (nums1[1] XOR nums2[1]) + ... + (nums1[n - 1] XOR nums2[n - 1]) ( 0-indexed ). For example, the XOR sum of [1,2,3] and [3,2,1] is equal to (1 XOR 3) + (2 XOR 2) + (3 XOR 1) = 2 + 0 + 2 = 4 . Rearrange the elements of nums2 such that the resulting XOR sum is minimized . Return the XOR sum after the rearrangement .

**Approach**: `dp[mask]` = min XOR sum when first `popcount(mask)` elements of `nums1` are matched to the elements indexed by `mask` in `nums2`.
```java
public int minimumXORSum(int[] nums1, int[] nums2) {
    int n=nums1.length;
    int[] dp=new int[1<<n]; Arrays.fill(dp,Integer.MAX_VALUE); dp[0]=0;
    for (int mask=0; mask<(1<<n); mask++) {
        if (dp[mask]==Integer.MAX_VALUE) continue;
        int i=Integer.bitCount(mask); // next index in nums1
        if (i==n) continue;
        for (int j=0; j<n; j++) if ((mask&(1<<j))==0) {
            int newMask=mask|(1<<j);
            dp[newMask]=Math.min(dp[newMask], dp[mask]+(nums1[i]^nums2[j]));
        }
    }
    return dp[(1<<n)-1];
}
```

#### 3. Partition to K Equal Sum Subsets (LC 698)
> Read: 0 | Coded: 0

**Problem**: Given an integer array nums and an integer k , return true if it is possible to divide this array into k non-empty subsets whose sums are all equal.

**Approach**: `dp[mask]` = sum accumulated in the current bucket for the subset `mask`. Use bitmask over elements.
```java
public boolean canPartitionKSubsets(int[] nums, int k) {
    int total=Arrays.stream(nums).sum();
    if (total%k!=0) return false;
    int target=total/k, n=nums.length;
    Arrays.sort(nums);
    if (nums[n-1]>target) return false;
    int[] dp=new int[1<<n]; Arrays.fill(dp,-1); dp[0]=0;
    for (int mask=0; mask<(1<<n); mask++) {
        if (dp[mask]==-1) continue;
        for (int i=0; i<n; i++) if ((mask&(1<<i))==0) {
            int next=(dp[mask]+nums[i])%target;
            if (dp[mask]+nums[i]<=target) {
                int newMask=mask|(1<<i);
                if (dp[newMask]==-1) dp[newMask]=next;
            }
        }
    }
    return dp[(1<<n)-1]==0;
}
```

---

## 44. Bucket Sort
**Tier 4**

### Pattern
Distribute elements into fixed buckets by value range. Process buckets in order. Useful when value range is known and bounded.

### Java Skeleton
```java
int[] buckets = new int[maxVal + 1];
for (int num : nums) buckets[num]++;
int idx = 0;
for (int val = 0; val < buckets.length; val++)
    while (buckets[val]-- > 0) nums[idx++] = val;
```

### Famous Problems

#### 1. Maximum Gap (LC 164)
> Read: 0 | Coded: 0

**Problem**: Given an integer array nums , return the maximum difference between two successive elements in its sorted form . If the array contains less than two elements, return 0 . You must write an algorithm that runs in linear time and uses linear extra space.

**Approach**: By pigeonhole, max gap >= `ceil((max-min)/(n-1))`. Create `n-1` buckets of that size. Max gap is between consecutive non-empty buckets.
```java
public int maximumGap(int[] nums) {
    if (nums.length < 2) return 0;
    int n=nums.length, min=Integer.MAX_VALUE, max=Integer.MIN_VALUE;
    for (int x:nums) { min=Math.min(min,x); max=Math.max(max,x); }
    if (min==max) return 0;
    int bucketSize=Math.max(1,(max-min)/(n-1));
    int bucketCount=(max-min)/bucketSize+1;
    int[] bucketMin=new int[bucketCount], bucketMax=new int[bucketCount];
    Arrays.fill(bucketMin,Integer.MAX_VALUE); Arrays.fill(bucketMax,Integer.MIN_VALUE);
    for (int x:nums) { int b=(x-min)/bucketSize; bucketMin[b]=Math.min(bucketMin[b],x); bucketMax[b]=Math.max(bucketMax[b],x); }
    int gap=0, prevMax=min;
    for (int i=0; i<bucketCount; i++) {
        if (bucketMin[i]==Integer.MAX_VALUE) continue;
        gap=Math.max(gap,bucketMin[i]-prevMax); prevMax=bucketMax[i];
    }
    return gap;
}
```

#### 2. Sort Characters By Frequency (LC 451)
> Read: 0 | Coded: 0

**Problem**: Given a string s , sort it in decreasing order based on the frequency of the characters. The frequency of a character is the number of times it appears in the string. Return the sorted string . If there are multiple answers, return any of them .

**Approach**: Count frequencies, bucket by frequency (bucket index = freq), iterate from highest bucket down.
```java
public String frequencySort(String s) {
    Map<Character, Integer> freq = new HashMap<>();
    for (char c : s.toCharArray()) freq.merge(c, 1, Integer::sum);
    List<List<Character>> buckets = new ArrayList<>();
    for (int i = 0; i <= s.length(); i++) buckets.add(new ArrayList<>());
    for (var e : freq.entrySet()) buckets.get(e.getValue()).add(e.getKey());
    StringBuilder sb = new StringBuilder();
    for (int i = s.length(); i >= 1; i--)
        for (char c : buckets.get(i))
            for (int j = 0; j < i; j++) sb.append(c);
    return sb.toString();
}
```

#### 3. Contains Duplicate III (LC 220)
> Read: 0 | Coded: 0

**Problem**: You are given an integer array nums and two integers indexDiff and valueDiff . Find a pair of indices (i, j) such that: i != j , abs(i - j) <= indexDiff . abs(nums[i] - nums[j]) <= valueDiff , and Return true if such pair exists or false otherwise .

**Approach**: Sliding window of size k; bucket each element into bucket of size `t+1`. Two elements in same bucket or adjacent buckets are close enough.
```java
public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
    if (t < 0) return false;
    Map<Long, Long> buckets = new HashMap<>();
    long w = (long)t + 1;
    for (int i = 0; i < nums.length; i++) {
        long b = getID(nums[i], w);
        if (buckets.containsKey(b)) return true;
        if (buckets.containsKey(b-1) && Math.abs(nums[i]-buckets.get(b-1)) < w) return true;
        if (buckets.containsKey(b+1) && Math.abs(nums[i]-buckets.get(b+1)) < w) return true;
        buckets.put(b, (long)nums[i]);
        if (i >= k) buckets.remove(getID(nums[i-k], w));
    }
    return false;
}
private long getID(long x, long w) { return x<0 ? (x+1)/w-1 : x/w; }
```

---

## 45. Unbounded Knapsack
**Tier 5**

### Pattern
Each item can be used unlimited times. Inner loop iterates `w` from low to high (opposite of 0/1 Knapsack).

### Java Skeleton
```java
int[] dp = new int[W + 1];
for (int w = 0; w <= W; w++)        // or: for each item, then for each weight
    for (int i = 0; i < n; i++)
        if (w >= weight[i])
            dp[w] = Math.max(dp[w], dp[w - weight[i]] + value[i]);
// Equivalently: process items in outer loop, weights forward in inner loop
for (int item : items)
    for (int w = item; w <= W; w++)
        dp[w] = Math.max(dp[w], dp[w - item] + val);
```

### Famous Problems

#### 1. Coin Change (LC 322)
> Read: 0 | Coded: 0

**Problem**: You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money. Return the fewest number of coins that you need to make up that amount . If that amount of money cannot be made up by any combination of the coins, return -1 . You may assume that you have an infinite number of each kind of coin.

**Approach**: `dp[i]` = fewest coins to make amount i. Try each coin: `dp[i] = min(dp[i], dp[i-coin]+1)`.
```java
public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount+1]; Arrays.fill(dp, amount+1); dp[0]=0;
    for (int i = 1; i <= amount; i++)
        for (int coin : coins)
            if (coin <= i) dp[i] = Math.min(dp[i], dp[i-coin]+1);
    return dp[amount] > amount ? -1 : dp[amount];
}
```

#### 2. Coin Change 2 (LC 518)
> Read: 0 | Coded: 0

**Problem**: You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money. Return the number of combinations that make up that amount . If that amount of money cannot be made up by any combination of the coins, return 0 . You may assume that you have an infinite number of each kind of coin. The answer is guaranteed to fit into a signed 32-bit integer.

**Approach**: Count combinations (not permutations). Outer loop = coins, inner loop = amounts forward.
```java
public int change(int amount, int[] coins) {
    int[] dp = new int[amount+1]; dp[0]=1;
    for (int coin : coins)
        for (int w = coin; w <= amount; w++)
            dp[w] += dp[w-coin];
    return dp[amount];
}
```

#### 3. Integer Break (LC 343)
> Read: 0 | Coded: 0

**Problem**: Given an integer n , break it into the sum of k positive integers , where k >= 2 , and maximize the product of those integers. Return the maximum product you can get .

**Approach**: `dp[i] = max(j*(i-j), j*dp[i-j])` for j in `1..i-1`. Break i into j and (i-j) or further recurse.
```java
public int integerBreak(int n) {
    int[] dp = new int[n+1]; dp[1]=1;
    for (int i=2; i<=n; i++)
        for (int j=1; j<i; j++)
            dp[i]=Math.max(dp[i], Math.max(j*(i-j), j*dp[i-j]));
    return dp[n];
}
```

---

## 46. State Machine DP
**Tier 5**

### Pattern
Define distinct states (e.g., holding stock, sold, resting). At each step, transition between states. Final answer = max over terminal states.

### Java Skeleton
```java
// States: hold, sold, rest
int hold = Integer.MIN_VALUE, sold = 0, rest = 0;
for (int price : prices) {
    int prevHold = hold, prevSold = sold, prevRest = rest;
    hold = Math.max(prevHold, prevRest - price); // buy
    sold = prevHold + price;                      // sell
    rest = Math.max(prevRest, prevSold);          // cooldown or continue resting
}
return Math.max(sold, rest);
```

### Famous Problems

#### 1. Best Time to Buy and Sell Stock III (LC 123) — at most 2 transactions
> Read: 0 | Coded: 0

**Problem**: You are given an array prices where prices[i] is the price of a given stock on the i th day. Find the maximum profit you can achieve. You may complete at most two transactions .

**Approach**: Four states: buy1, sell1, buy2, sell2. Each tracks best profit in that state.
```java
public int maxProfit(int[] prices) {
    int buy1=Integer.MIN_VALUE, sell1=0, buy2=Integer.MIN_VALUE, sell2=0;
    for (int p : prices) {
        buy1  = Math.max(buy1, -p);
        sell1 = Math.max(sell1, buy1+p);
        buy2  = Math.max(buy2, sell1-p);
        sell2 = Math.max(sell2, buy2+p);
    }
    return sell2;
}
```

#### 2. Best Time to Buy and Sell Stock with Cooldown (LC 309)
> Read: 0 | Coded: 0

**Problem**: You are given an array prices where prices[i] is the price of a given stock on the i th day. Find the maximum profit you can achieve. You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times) with the following restrictions: After you sell your stock, you cannot buy stock on the next day (i.e., cooldown one day).

**Approach**: Three states — hold (own stock), sold (just sold, in cooldown), rest (not holding).
```java
public int maxProfit(int[] prices) {
    int hold=Integer.MIN_VALUE, sold=0, rest=0;
    for (int p : prices) {
        int ph=hold, ps=sold, pr=rest;
        hold = Math.max(ph, pr-p);   // hold or buy from rest
        sold = ph+p;                  // sell from held
        rest = Math.max(pr, ps);      // rest or come off cooldown
    }
    return Math.max(sold, rest);
}
```

#### 3. Best Time to Buy and Sell Stock with Transaction Fee (LC 714)
> Read: 0 | Coded: 0

**Problem**: You are given an array prices where prices[i] is the price of a given stock on the i th day, and an integer fee representing a transaction fee. Find the maximum profit you can achieve. You may complete as many transactions as you like, but you need to pay the transaction fee for each transaction.

**Approach**: Two states — hold (own stock) and cash (not holding). Pay fee on sell.
```java
public int maxProfit(int[] prices, int fee) {
    int hold=Integer.MIN_VALUE, cash=0;
    for (int p : prices) {
        int ph=hold, pc=cash;
        hold = Math.max(ph, pc-p);       // hold or buy
        cash = Math.max(pc, ph+p-fee);   // stay or sell (minus fee)
    }
    return cash;
}
```

---

## 47. Digit DP
**Tier 5**

### Pattern
Build the answer digit by digit, tracking whether we are still bounded by the upper limit (`tight` flag). Memoize on `(position, tight, ...extra state)`.

### Java Skeleton
```java
String digits = String.valueOf(N);
Integer[][] memo = new Integer[digits.length()][2]; // [pos][tight]
int digitDP(int pos, boolean tight, /* extra state */) {
    if (pos == digits.length()) return /* base result */;
    if (memo[pos][tight?1:0] != null) return memo[pos][tight?1:0];
    int limit = tight ? (digits.charAt(pos)-'0') : 9;
    int result = 0;
    for (int d = 0; d <= limit; d++) {
        result += digitDP(pos+1, tight && d==limit, /* update state */);
    }
    return memo[pos][tight?1:0] = result;
}
```

### Famous Problems

#### 1. Count Numbers with Unique Digits (LC 357)
> Read: 0 | Coded: 0

**Problem**: Given an integer n , return the count of all numbers with unique digits, x , where 0 <= x < 10 n .

**Approach**: For each length, count arrangements of unique digits. DP on number of digits used.
```java
public int countNumbersWithUniqueDigits(int n) {
    if (n == 0) return 1;
    int result=10, uniqueDigits=9, availableNum=9;
    for (int i=2; i<=n && availableNum>0; i++) {
        uniqueDigits *= availableNum--;
        result += uniqueDigits;
    }
    return result;
}
```

#### 2. Numbers At Most N Given Digit Set (LC 902)
> Read: 0 | Coded: 0

**Problem**: Given an array of digits which is sorted in non-decreasing order. You can write numbers using each digits[i] as many times as we want. For example, if digits = ['1','3','5'] , we may write numbers such as '13' , '551' , and '1351315' . Return the number of positive integers that can be generated that are less than or equal to a given integer n .

**Approach**: Count numbers with fewer digits than N (all permutations), plus count of same-length numbers ≤ N digit by digit.
```java
public int atMostNGivenDigitSet(String[] digits, int n) {
    String s = String.valueOf(n);
    int k = s.length(), result = 0;
    // Numbers with fewer digits
    for (int i = 1; i < k; i++) {
        int power = 1;
        for (int j = 0; j < i-1; j++) power *= digits.length;
        result += digits.length * power;
    }
    // Numbers with exactly k digits, <= n
    for (int i = 0; i < k; i++) {
        int smaller = 0;
        for (String d : digits) if (d.charAt(0) < s.charAt(i)) smaller++;
        int power = 1;
        for (int j = 0; j < k-i-1; j++) power *= digits.length;
        result += smaller * power;
        boolean exactMatch = false;
        for (String d : digits) if (d.charAt(0) == s.charAt(i)) { exactMatch=true; break; }
        if (!exactMatch) return result;
    }
    return result + 1; // n itself is valid
}
```

---

## 48. Probability DP
**Tier 5**

### Pattern
`dp[state]` = probability of being in that state. Transitions distribute probability proportionally. Normalize by number of choices.

### Java Skeleton
```java
double[][] dp = new double[rows][cols];
dp[startR][startC] = 1.0;
for (int step = 0; step < K; step++) {
    double[][] next = new double[rows][cols];
    for (int r = 0; r < rows; r++) for (int c = 0; c < cols; c++) {
        if (dp[r][c] == 0) continue;
        for (int[] move : moves) {
            int nr = r+move[0], nc = c+move[1];
            if (inBounds(nr, nc)) next[nr][nc] += dp[r][c] / moves.length;
        }
    }
    dp = next;
}
```

### Famous Problems

#### 1. Knight Probability in Chessboard (LC 688)
> Read: 0 | Coded: 0

**Problem**: On an n x n chessboard, a knight starts at the cell (row, column) and attempts to make exactly k moves. The rows and columns are 0-indexed , so the top-left cell is (0, 0) , and the bottom-right cell is (n - 1, n - 1) . A chess knight has eight possible moves it can make, as illustrated below. Each move is two cells in a cardinal direction, then one cell in an orthogonal direction. Each time the knight is to move, it chooses one of eight possible moves uniformly at random (even if the piece would go off the chessboard) and moves there. The knight continues moving until it has made exactly k moves or has moved off the chessboard. Return the probability that the knight remains on the board after it has stopped moving .

**Approach**: DP over K steps. Distribute probability to valid moves each step.
```java
public double knightProbability(int n, int k, int row, int col) {
    int[][] moves = {{2,1},{2,-1},{-2,1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}};
    double[][] dp = new double[n][n];
    dp[row][col] = 1.0;
    for (int step = 0; step < k; step++) {
        double[][] next = new double[n][n];
        for (int r = 0; r < n; r++) for (int c = 0; c < n; c++) {
            if (dp[r][c] == 0) continue;
            for (int[] m : moves) {
                int nr=r+m[0], nc=c+m[1];
                if (nr>=0&&nr<n&&nc>=0&&nc<n) next[nr][nc] += dp[r][c] / 8.0;
            }
        }
        dp = next;
    }
    double prob = 0;
    for (double[] row2 : dp) for (double v : row2) prob += v;
    return prob;
}
```

#### 2. Soup Servings (LC 808)
> Read: 0 | Coded: 0

**Problem**: You have two soups, A and B , each starting with n mL. On every turn, one of the following four serving operations is chosen at random , each with probability 0.25 independent of all previous turns: pour 100 mL from type A and 0 mL from type B pour 75 mL from type A and 25 mL from type B pour 50 mL from type A and 50 mL from type B pour 25 mL from type A and 75 mL from type B

**Approach**: `dp[a][b]` = P(A finishes first) + 0.5*P(both finish simultaneously). For large n, answer converges to 1.
```java
public double soupServings(int n) {
    if (n > 4800) return 1.0; // converges
    int m = (n + 24) / 25;
    double[][] dp = new double[m+1][m+1];
    dp[0][0] = 0.5;
    for (int i = 1; i <= m; i++) dp[0][i] = 1.0; // A finishes first
    int[][] ops = {{4,0},{3,1},{2,2},{1,3}};
    for (int a = 1; a <= m; a++) for (int b = 1; b <= m; b++) {
        for (int[] op : ops)
            dp[a][b] += 0.25 * dp[Math.max(0,a-op[0])][Math.max(0,b-op[1])];
    }
    return dp[m][m];
}
```

---

## 49. BIT / Segment Tree
**Tier 5**

### Pattern
**BIT (Fenwick Tree)**: point update + prefix sum query in O(log n). **Segment Tree**: range query + range/point update in O(log n). Use when array changes dynamically.

### Java Skeleton
```java
// BIT (1-indexed)
int[] bit = new int[n + 1];
void update(int i, int delta) { for (i++; i<=n; i+=i&(-i)) bit[i]+=delta; }
int query(int i) { int s=0; for (i++; i>0; i-=i&(-i)) s+=bit[i]; return s; }
int rangeQuery(int l, int r) { return query(r) - (l>0?query(l-1):0); }
```

### Famous Problems

#### 1. Range Sum Query — Mutable (LC 307)
> Read: 0 | Coded: 0

**Problem**: Given an integer array nums , handle multiple queries of the following types: Update the value of an element in nums . Calculate the sum of the elements of nums between indices left and right inclusive where left <= right . Implement the NumArray class: NumArray(int[] nums) Initializes the object with the integer array nums . void update(int index, int val) Updates the value of nums[index] to be val . int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right] ).

**Approach**: BIT with point update and prefix sum query.
```java
class NumArray {
    int[] bit, nums; int n;
    public NumArray(int[] nums) {
        this.n=nums.length; this.nums=new int[n]; bit=new int[n+1];
        for (int i=0;i<n;i++) update(i, nums[i]);
    }
    public void update(int i, int val) {
        int delta=val-nums[i]; nums[i]=val;
        for (int j=i+1; j<=n; j+=j&(-j)) bit[j]+=delta;
    }
    public int sumRange(int l, int r) {
        return prefix(r+1)-prefix(l);
    }
    private int prefix(int i) { int s=0; for (;i>0;i-=i&(-i)) s+=bit[i]; return s; }
}
```

#### 2. Count of Smaller Numbers After Self (LC 315)
> Read: 0 | Coded: 0

**Problem**: Given an integer array nums , return an integer array counts where counts[i] is the number of smaller elements to the right of nums[i] .

**Approach**: Process right to left. Coordinate compress values, then query BIT for count of smaller elements already inserted.
```java
public List<Integer> countSmaller(int[] nums) {
    int n=nums.length;
    int[] sorted=nums.clone(); Arrays.sort(sorted);
    Map<Integer,Integer> rank=new HashMap<>();
    int r=1; for (int v:sorted) rank.putIfAbsent(v,r++);
    int[] bit=new int[r]; int[] res=new int[n];
    for (int i=n-1; i>=0; i--) {
        int pos=rank.get(nums[i]);
        res[i]=query(bit,pos-1);
        update(bit,pos,1,r-1);
    }
    List<Integer> ans=new ArrayList<>();
    for (int x:res) ans.add(x);
    return ans;
}
private int query(int[] bit,int i) { int s=0; for (;i>0;i-=i&(-i)) s+=bit[i]; return s; }
private void update(int[] bit,int i,int v,int n) { for (;i<=n;i+=i&(-i)) bit[i]+=v; }
```

---

## 50. String Matching (KMP)
**Tier 5**

### Pattern
Build LPS (Longest Proper Prefix which is also Suffix) array in O(m). Use it to skip redundant comparisons during search, achieving O(n+m) total.

### Java Skeleton
```java
int[] buildLPS(String pattern) {
    int[] lps = new int[pattern.length()];
    int len = 0, i = 1;
    while (i < pattern.length()) {
        if (pattern.charAt(i) == pattern.charAt(len)) { lps[i++] = ++len; }
        else if (len != 0) { len = lps[len-1]; }
        else { lps[i++] = 0; }
    }
    return lps;
}
// KMP search
int[] lps = buildLPS(pattern);
int i=0, j=0;
while (i < text.length()) {
    if (text.charAt(i) == pattern.charAt(j)) { i++; j++; }
    if (j == pattern.length()) { /* match at i-j */ j = lps[j-1]; }
    else if (i<text.length() && text.charAt(i)!=pattern.charAt(j)) {
        if (j!=0) j=lps[j-1]; else i++;
    }
}
```

### Famous Problems

#### 1. Implement strStr() / KMP (LC 28)
> Read: 0 | Coded: 0

**Problem**: Given two strings needle and haystack , return the index of the first occurrence of needle in haystack , or -1 if needle is not part of haystack .

**Approach**: Build LPS of needle, then KMP search in haystack.
```java
public int strStr(String haystack, String needle) {
    if (needle.isEmpty()) return 0;
    int[] lps = buildLPS(needle);
    int i=0, j=0;
    while (i < haystack.length()) {
        if (haystack.charAt(i)==needle.charAt(j)) { i++; j++; }
        if (j==needle.length()) return i-j;
        else if (i<haystack.length()&&haystack.charAt(i)!=needle.charAt(j))
            j = j!=0 ? lps[j-1] : (++i - i + (i=i));
    }
    return -1;
}
private int[] buildLPS(String p) {
    int[] lps=new int[p.length()]; int len=0, i=1;
    while (i<p.length()) {
        if (p.charAt(i)==p.charAt(len)) lps[i++]=++len;
        else if (len!=0) len=lps[len-1]; else lps[i++]=0;
    }
    return lps;
}
```

#### 2. Longest Happy Prefix (LC 1392)
> Read: 0 | Coded: 0

**Problem**: A string is called a happy prefix if is a non-empty prefix which is also a suffix (excluding itself). Given a string s , return the longest happy prefix of s . Return an empty string "" if no such prefix exists.

**Approach**: The answer is the LPS value at the last index of the string — that is exactly what KMP's failure function computes.
```java
public String longestPrefix(String s) {
    int[] lps = new int[s.length()];
    int len=0, i=1;
    while (i < s.length()) {
        if (s.charAt(i)==s.charAt(len)) lps[i++]=++len;
        else if (len!=0) len=lps[len-1]; else lps[i++]=0;
    }
    return s.substring(0, lps[s.length()-1]);
}
```

#### 3. Repeated Substring Pattern (LC 459)
> Read: 0 | Coded: 0

**Problem**: Given a string s , check if it can be constructed by taking a substring of it and appending multiple copies of the substring together.

**Approach**: If `s` has a repeating unit, then `(s+s).substring(1, 2n-1)` contains `s`. Alternatively, use LPS: if `n % (n - lps[n-1]) == 0`, then the pattern repeats.
```java
public boolean repeatedSubstringPattern(String s) {
    int n=s.length();
    int[] lps=new int[n]; int len=0, i=1;
    while (i<n) {
        if (s.charAt(i)==s.charAt(len)) lps[i++]=++len;
        else if (len!=0) len=lps[len-1]; else lps[i++]=0;
    }
    int period=n-lps[n-1];
    return period!=n && n%period==0;
}
```

---

## 51. Line Sweep
**Tier 5**

### Pattern
Create start and end events. Sort by coordinate. Sweep through, maintaining active set (e.g., heights, counts). Record changes when the active set changes.

### Java Skeleton
```java
List<int[]> events = new ArrayList<>();
for (int[] interval : intervals) {
    events.add(new int[]{interval[0], +1}); // start
    events.add(new int[]{interval[1], -1}); // end
}
events.sort((a,b) -> a[0]!=b[0] ? a[0]-b[0] : a[1]-b[1]);
int active = 0;
for (int[] e : events) { active += e[1]; /* process */ }
```

### Famous Problems

#### 1. The Skyline Problem (LC 218)
> Read: 0 | Coded: 0

**Problem**: A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance. Given the locations and heights of all the buildings, return the skyline formed by these buildings collectively . The geometric information of each building is given in the array buildings where buildings[i] = [left i , right i , height i ] : left i is the x coordinate of the left edge of the i th building. right i is the x coordinate of the right edge of the i th building. height i is the height of the i th building. You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0 . The skyline should be represented as a list of "key points" sorted by their x-coordinate in the form [[x 1 ,y 1 ],[x 2 ,y 2 ],...] . Each key point is the left endpoint of some horizontal segment in the skyline except the last point in the list, which always has a y-coordinate 0 and is used to mark the skyline's termination where the rightmost building ends. Any ground between the leftmost and rightmost buildings should be part of the skyline's contour.

**Approach**: Events at building edges. Max-heap of active heights. Record key point when max height changes.
```java
public List<List<Integer>> getSkyline(int[][] buildings) {
    List<int[]> events = new ArrayList<>();
    for (int[] b : buildings) { events.add(new int[]{b[0],-b[2]}); events.add(new int[]{b[1],b[2]}); }
    events.sort((a,b)->a[0]!=b[0]?a[0]-b[0]:a[1]-b[1]);
    TreeMap<Integer,Integer> heights = new TreeMap<>(Collections.reverseOrder());
    heights.put(0,1);
    List<List<Integer>> res = new ArrayList<>();
    int prevMax=0;
    for (int[] e : events) {
        if (e[1]<0) heights.merge(-e[1],1,Integer::sum);
        else { heights.merge(e[1],-1,Integer::sum); if (heights.get(e[1])==0) heights.remove(e[1]); }
        int curMax=heights.firstKey();
        if (curMax!=prevMax) { res.add(Arrays.asList(e[0],curMax)); prevMax=curMax; }
    }
    return res;
}
```

#### 2. Rectangle Area II (LC 850)
> Read: 0 | Coded: 0

**Problem**: You are given a 2D array of axis-aligned rectangles . Each rectangle[i] = [x i1 , y i1 , x i2 , y i2 ] denotes the i th rectangle where (x i1 , y i1 ) are the coordinates of the bottom-left corner , and (x i2 , y i2 ) are the coordinates of the top-right corner . Calculate the total area covered by all rectangles in the plane. Any area covered by two or more rectangles should only be counted once . Return the total area . Since the answer may be too large, return it modulo 10 9 + 7 .

**Approach**: Coordinate compress Y-axis. Sweep X-axis with events; at each X, sum lengths of active Y-ranges.
```java
public int rectangleArea(int[][] rectangles) {
    final int MOD = 1_000_000_007;
    TreeSet<Integer> yset = new TreeSet<>();
    for (int[] r : rectangles) { yset.add(r[1]); yset.add(r[3]); }
    List<Integer> ys = new ArrayList<>(yset);
    Map<Integer,Integer> yIdx = new HashMap<>();
    for (int i=0; i<ys.size(); i++) yIdx.put(ys.get(i),i);
    int yLen=ys.size()-1;
    int[] count=new int[yLen];
    List<int[]> events=new ArrayList<>();
    for (int[] r:rectangles) { events.add(new int[]{r[0],r[1],r[3],1}); events.add(new int[]{r[2],r[1],r[3],-1}); }
    events.sort((a,b)->a[0]-b[0]);
    long area=0, prevX=0;
    for (int[] e:events) {
        long activeY=0; for (int i=0;i<yLen;i++) if (count[i]>0) activeY+=ys.get(i+1)-ys.get(i);
        area=(area+activeY*(e[0]-prevX))%MOD;
        prevX=e[0];
        int lo=yIdx.get(e[1]),hi=yIdx.get(e[3]);
        for (int i=lo;i<hi;i++) count[i]+=e[3];
    }
    return (int)area;
}
```

#### 3. My Calendar II (LC 731)
> Read: 0 | Coded: 0

**Problem**: You are implementing a program to use as your calendar. We can add a new event if adding the event will not cause a triple booking . A triple booking happens when three events have some non-empty intersection (i.e., some moment is common to all the three events.). The event can be represented as a pair of integers startTime and endTime that represents a booking on the half-open interval [startTime, endTime) , the range of real numbers x such that startTime <= x < endTime . Implement the MyCalendarTwo class: MyCalendarTwo() Initializes the calendar object. boolean book(int startTime, int endTime) Returns true if the event can be added to the calendar successfully without causing a triple booking . Otherwise, return false and do not add the event to the calendar.

**Approach**: Use a difference array / event map. +1 at start, -1 at end. Running sum >= 3 means triple booking.
```java
class MyCalendarTwo {
    TreeMap<Integer, Integer> events = new TreeMap<>();
    public boolean book(int start, int end) {
        events.merge(start, 1, Integer::sum);
        events.merge(end, -1, Integer::sum);
        int active = 0;
        for (int v : events.values()) {
            active += v;
            if (active >= 3) { events.merge(start,-1,Integer::sum); events.merge(end,1,Integer::sum); return false; }
        }
        return true;
    }
}
```

---

## 52. Maths / Geometry
**Tier 5**

### Pattern
Fast exponentiation via repeated squaring. GCD via Euclidean algorithm. Slope = `dy/dx` as a fraction `(gcd-reduced)` for collinearity.

### Java Skeleton
```java
// Fast power: O(log n)
double fastPow(double x, long n) {
    if (n == 0) return 1;
    double half = fastPow(x, n/2);
    return n%2==0 ? half*half : half*half*x;
}
// GCD
int gcd(int a, int b) { return b==0 ? a : gcd(b, a%b); }
```

### Famous Problems

#### 1. Pow(x, n) (LC 50)
> Read: 0 | Coded: 0

**Problem**: Implement pow(x, n) , which calculates x raised to the power n (i.e., x n ).

**Approach**: Fast exponentiation. Handle negative `n` by computing `1/x^(-n)`.
```java
public double myPow(double x, int n) {
    long N = n;
    if (N < 0) { x = 1/x; N = -N; }
    return fastPow(x, N);
}
private double fastPow(double x, long n) {
    if (n==0) return 1;
    double half = fastPow(x, n/2);
    return n%2==0 ? half*half : half*half*x;
}
```

#### 2. Max Points on a Line (LC 149)
> Read: 0 | Coded: 0

**Problem**: Given an array of points where points[i] = [x i , y i ] represents a point on the X-Y plane, return the maximum number of points that lie on the same straight line .

**Approach**: For each point, count max collinear points by grouping by slope (as GCD-reduced fraction).
```java
public int maxPoints(int[][] points) {
    int n=points.length, max=1;
    for (int i=0; i<n; i++) {
        Map<String,Integer> map=new HashMap<>();
        for (int j=i+1; j<n; j++) {
            int dx=points[j][0]-points[i][0], dy=points[j][1]-points[i][1];
            int g=gcd(Math.abs(dx),Math.abs(dy));
            if (g>0){dx/=g; dy/=g;}
            if (dx<0){dx=-dx;dy=-dy;} else if(dx==0) dy=Math.abs(dy);
            String key=dx+","+dy;
            map.merge(key,1,Integer::sum);
            max=Math.max(max,map.get(key)+1);
        }
    }
    return max;
}
private int gcd(int a,int b){return b==0?a:gcd(b,a%b);}
```

#### 3. Integer to Roman (LC 12)
> Read: 0 | Coded: 0

**Problem**: Seven different symbols represent Roman numerals with the following values: Symbol Value I 1 V 5 X 10 L 50 C 100 D 500 M 1000 Roman numerals are formed by appending the conversions of decimal place values from highest to lowest. Converting a decimal place value into a Roman numeral has the following rules: If the value does not start with 4 or 9, select the symbol of the maximal value that can be subtracted from the input, append that symbol to the result, subtract its value, and convert the remainder to a Roman numeral. If the value starts with 4 or 9 use the subtractive form representing one symbol subtracted from the following symbol, for example, 4 is 1 ( I ) less than 5 ( V ): IV and 9 is 1 ( I ) less than 10 ( X ): IX . Only the following subtractive forms are used: 4 ( IV ), 9 ( IX ), 40 ( XL ), 90 ( XC ), 400 ( CD ) and 900 ( CM ). Only powers of 10 ( I , X , C , M ) can be appended consecutively at most 3 times to represent multiples of 10. You cannot append 5 ( V ), 50 ( L ), or 500 ( D ) multiple times. If you need to append a symbol 4 times use the subtractive form . Given an integer, convert it to a Roman numeral.

**Approach**: Greedy — subtract the largest symbol value that fits, repeat.
```java
public String intToRoman(int num) {
    int[] vals   = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
    String[] syms= {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
    StringBuilder sb=new StringBuilder();
    for (int i=0; i<vals.length; i++)
        while (num>=vals[i]) { sb.append(syms[i]); num-=vals[i]; }
    return sb.toString();
}
```

---

## 53. Eulerian Circuit
**Tier 5**

### Pattern
**Hierholzer's Algorithm**: DFS, but push a node to the result only when backtracking (no more outgoing edges). Works for both directed and undirected graphs. Use a min-heap or sorted structure for lexicographic order.

### Java Skeleton
```java
Map<String, PriorityQueue<String>> graph = new HashMap<>();
LinkedList<String> result = new LinkedList<>();
void dfs(String node) {
    while (graph.containsKey(node) && !graph.get(node).isEmpty())
        dfs(graph.get(node).poll()); // visit next (lexicographically smallest)
    result.addFirst(node);           // add to front when backtracking
}
```

### Famous Problems

#### 1. Reconstruct Itinerary (LC 332)
> Read: 0 | Coded: 0

**Problem**: You are given a list of airline tickets where tickets[i] = [from i , to i ] represent the departure and the arrival airports of one flight. Reconstruct the itinerary in order and return it. All of the tickets belong to a man who departs from "JFK" , thus, the itinerary must begin with "JFK" . If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string. For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"] . You may assume all tickets form at least one valid itinerary. You must use all the tickets once and only once.

**Approach**: Directed Eulerian path starting from "JFK". Min-heap for lexicographic order. Hierholzer's.
```java
public List<String> findItinerary(List<List<String>> tickets) {
    Map<String, PriorityQueue<String>> graph = new HashMap<>();
    for (List<String> t : tickets)
        graph.computeIfAbsent(t.get(0), k->new PriorityQueue<>()).offer(t.get(1));
    LinkedList<String> result = new LinkedList<>();
    dfs("JFK", graph, result);
    return result;
}
private void dfs(String airport, Map<String,PriorityQueue<String>> g, LinkedList<String> res) {
    while (g.containsKey(airport) && !g.get(airport).isEmpty())
        dfs(g.get(airport).poll(), g, res);
    res.addFirst(airport);
}
```

#### 2. Valid Arrangement of Pairs (LC 2097)
> Read: 0 | Coded: 0

**Problem**: You are given a 0-indexed 2D integer array pairs where pairs[i] = [start i , end i ] . An arrangement of pairs is valid if for every index i where 1 <= i < pairs.length , we have end i-1 == start i . Return any valid arrangement of pairs .

**Approach**: Find the Eulerian path start node (outDegree - inDegree == 1, or any node if all balanced). Hierholzer's on directed graph.
```java
public int[][] validArrangement(int[][] pairs) {
    Map<Integer,Deque<Integer>> graph=new HashMap<>();
    Map<Integer,Integer> outDeg=new HashMap<>(), inDeg=new HashMap<>();
    for (int[] p:pairs) {
        graph.computeIfAbsent(p[0],k->new ArrayDeque<>()).offer(p[1]);
        outDeg.merge(p[0],1,Integer::sum); inDeg.merge(p[1],1,Integer::sum);
    }
    int start=pairs[0][0];
    for (int node:outDeg.keySet()) if (outDeg.get(node)-inDeg.getOrDefault(node,0)==1) { start=node; break; }
    LinkedList<Integer> path=new LinkedList<>();
    Deque<Integer> stack=new ArrayDeque<>(); stack.push(start);
    while (!stack.isEmpty()) {
        int cur=stack.peek();
        if (graph.containsKey(cur)&&!graph.get(cur).isEmpty()) stack.push(graph.get(cur).poll());
        else path.addFirst(stack.pop());
    }
    List<Integer> order=new ArrayList<>(path);
    int[][] res=new int[order.size()-1][2];
    for (int i=0;i<res.length;i++) res[i]=new int[]{order.get(i),order.get(i+1)};
    return res;
}
```

---

*End of revision.md — 53 topics, ~155 unique problems, Java only.*
