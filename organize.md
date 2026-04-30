1. cheatsheet for basic operations of basic classes like ArrayList, String, Deque, PriorityQueue, List, LinkedList, HashMap, HashSet
2. first solve all questions, then come back to solved question to identify important patterns/concepts and note them
3. basics of each data structure and how it works(hashmap, arraylist,sortedmap, TreeMap, linkedlist, deque, etc) and gotchas of each like .equals() comparision
4. Nodes at Distance K, we can make pointers to parent and do bfs on the given node
5. “Java passes copies of variables. If the variable holds a reference, the reference is copied — not the object.”

⚠️ Trick Interviewers Use

They try to confuse you with:

String → immutable → behaves like primitive
Long → immutable → same
List / Map → mutable → behaves differently

6. apply mod on every calculation else overflow and wrong ans even with long in some cases
7. for dp after making the dp solution if not able to find atleast do a dry run of around 10 elements to find optimization
eg. https://www.interviewbit.com/problems/longest-increasing-subsequence/
8. in binary search it is not mandatory to return the element as soon as a match is found, can make it equal to result and return result at the function end
see eg. https://www.interviewbit.com/problems/longest-increasing-subsequence/
9. String manipulation tricks and functions used in java frequently
10. matrix multiplication dp or ways to evaluate true https://www.interviewbit.com/problems/evaluate-expression-to-true/
basically we need to place brackets
11. https://www.interviewbit.com/problems/minimum-difference-subsets/ keeping sum as a parameter in dp, similar to knapsack
12. kadane' algo to find maximum subarray sum within an array, 1d, 2d
13. monotonic stack, monotonic queue(basically maintaining increasing/decreasing order in stack/queue by keep popping till order is restored)
14. see filters of https://algomaster.io/practice/dsa-patterns, it has all patterns of questions
15. monotonic stack https://www.interviewbit.com/problems/largest-rectangle-in-histogram/. using 2 stacks, or even using 1
16. https://www.interviewbit.com/problems/max-rectangle-in-binary-matrix/ transition from O(n6) to O(n2)
17. https://www.geeksforgeeks.org/dsa/find-if-there-is-a-subarray-with-0-sum/ good example of prefix and hashing
18. https://www.interviewbit.com/problems/sub-matrices-with-sum-zero/ using 17. also. for matrix dp, try making some type of prefix/suffix and
then solving the 1d array smartly(generally O(n))
19. remove duplicates in array most efficiently
20. for graphs traversal , try reverse traversal/ traversal from edge also for more efficient dfs/bfs
eg. water flow problem
21. declaring adjacency list in graph problems
    List<List<Integer>> adj = new ArrayList<>();
for weighted graph, 
List<List<int[]>> adj = new ArrayList<>(); 
adj.get(u).add(new int[]{v, w});
for non-continuos nodes,
    Map<Integer, List<Integer>> adj = new HashMap<>();
22. disjoint set union with all optimizations
23. topological sort(use dfs with a stack, put a node in stack when it is fully processed)
24. topological sort(use bfs along with indegree array. now only insert indegree 0 nodes in array, if none
available && not all nodes visited, that means there is a cycle. KAHN's ALGO)
25. dikstra algo for shortest path in a weighted graph from a source to all nodes
distance array -> init to INT_MAX
see all neighbours and when an entry in distance array is changed, put entry (distance, node) in 
priority queue. pop minimum element and repeat
26. kruskal's algo for minimum spanning tree(use DSU, arrange all edges in an increasing order and process each edge,
if edge already in same component, do nothing, else merge both components)
27. for O(n2) to go to O(n), generally try to do hashing, monotonic stack/queue, prefix/suffix calculation, subtree calculation,etc
28. generative bfs https://www.interviewbit.com/problems/smallest-multiple-with-0-and-1/, start from a seed and explore options
29. we can use deque(double ended queue in case of dikstras if edge weights are 0 and 1 only. also 
called 0-1BFS) https://www.interviewbit.com/problems/min-cost-path/
30. check cycle in a DAG using back edge in dfs(visited[i] = 0,1,2)
31. cycle detection using dfs/bfs
32. Strongly Connected Components - Kosaraju's Algorithm
do topological sort(best approximation as we could have a cycle)
now reverse all edges and do dfs in the topological order. 
whenever a single call finishes, we have a SCC.

mandatory
1. DP(held-karp algorithm) https://www.interviewbit.com/problems/shortest-common-superstring/ we are basically state of i-1 as to what affects i
2. KMP(isSubstring on O(L) by processing pattern as LSP(longest suffix prefix))
3. binary lifting(reach LCA in tree in log(n). needs processing first)