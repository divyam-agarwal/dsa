# DSA Interview Cheatsheet

## ArrayList

| Operation | Method | Time |
|-----------|--------|------|
| Init | `List<Integer> list = new ArrayList<>();` | — |
| Add end | `list.add(val)` | O(1) amort |
| Add index | `list.add(i, val)` | O(n) |
| Get | `list.get(i)` | O(1) |
| Set | `list.set(i, val)` | O(1) |
| Remove by index | `list.remove(i)` | O(n) |
| Remove by value | `list.remove(Integer.valueOf(val))` | O(n) |
| Size | `list.size()` | O(1) |
| Contains | `list.contains(val)` | O(n) |
| SubList | `list.subList(from, to)` | O(1) |
| Sort ascending | `Collections.sort(list)` | O(n log n) |
| Sort custom | `list.sort((a, b) -> a - b)` | O(n log n) |
| Reverse | `Collections.reverse(list)` | O(n) |

```java
// Sort descending
list.sort((a, b) -> b - a);

// Sort list of arrays by first element
List<int[]> intervals = new ArrayList<>();
intervals.sort((a, b) -> a[0] - b[0]);

// Iterate
for (int x : list) { ... }
for (int i = 0; i < list.size(); i++) { ... }
```

---

## String

| Operation | Method | Time |
|-----------|--------|------|
| Init | `String s = "hello";` | — |
| Length | `s.length()` | O(1) |
| Char at | `s.charAt(i)` | O(1) |
| Substring | `s.substring(from, to)` | O(n) |
| Index of | `s.indexOf(ch)` / `s.indexOf(str)` | O(n) |
| Contains | `s.contains("sub")` | O(n) |
| Split | `s.split(" ")` | O(n) |
| To char array | `s.toCharArray()` | O(n) |
| Compare | `s.compareTo(t)` | O(n) |
| To int | `Integer.parseInt(s)` | O(n) |
| From int | `String.valueOf(n)` | O(n) |
| Trim | `s.trim()` | O(n) |
| To lower/upper | `s.toLowerCase()` / `s.toUpperCase()` | O(n) |

```java
// Char arithmetic
char c = 'a';
int idx = c - 'a';           // 0
char back = (char)('a' + idx);

// Check digit / letter
Character.isDigit(c);
Character.isLetter(c);
Character.isLetterOrDigit(c);

// Split on multiple delimiters
String[] parts = s.split("[,\\s]+");
```

---

## StringBuilder

| Operation | Method | Time |
|-----------|--------|------|
| Init | `StringBuilder sb = new StringBuilder();` | — |
| Append | `sb.append(val)` | O(1) amort |
| Insert | `sb.insert(i, val)` | O(n) |
| Delete | `sb.delete(from, to)` | O(n) |
| Delete char | `sb.deleteCharAt(i)` | O(n) |
| Reverse | `sb.reverse()` | O(n) |
| Char at | `sb.charAt(i)` | O(1) |
| Set char | `sb.setCharAt(i, c)` | O(1) |
| Length | `sb.length()` | O(1) |
| To string | `sb.toString()` | O(n) |

```java
// Build string in a loop
StringBuilder sb = new StringBuilder();
for (char c : chars) sb.append(c);
String result = sb.toString();

// Reverse a string
String rev = new StringBuilder(s).reverse().toString();
```

---

## HashMap

| Operation | Method | Time |
|-----------|--------|------|
| Init | `Map<K, V> map = new HashMap<>();` | — |
| Put | `map.put(key, val)` | O(1) amort |
| Get | `map.get(key)` | O(1) amort |
| Get or default | `map.getOrDefault(key, def)` | O(1) amort |
| Contains key | `map.containsKey(key)` | O(1) amort |
| Contains value | `map.containsValue(val)` | O(n) |
| Remove | `map.remove(key)` | O(1) amort |
| Size | `map.size()` | O(1) |
| Key set | `map.keySet()` | O(1) |
| Values | `map.values()` | O(1) |
| Entry set | `map.entrySet()` | O(1) |
| Merge | `map.merge(key, 1, Integer::sum)` | O(1) amort |

```java
// Frequency count
for (char c : s.toCharArray())
    map.put(c, map.getOrDefault(c, 0) + 1);

// Iterate entries
for (Map.Entry<K, V> e : map.entrySet())
    System.out.println(e.getKey() + " -> " + e.getValue());

// Compute if absent (e.g. adjacency list)
map.computeIfAbsent(key, k -> new ArrayList<>()).add(val);

// Merge shorthand
map.merge(key, 1, Integer::sum);
```

---

## HashSet

| Operation | Method | Time |
|-----------|--------|------|
| Init | `Set<T> set = new HashSet<>();` | — |
| Add | `set.add(val)` | O(1) amort |
| Contains | `set.contains(val)` | O(1) amort |
| Remove | `set.remove(val)` | O(1) amort |
| Size | `set.size()` | O(1) |
| Union | `set.addAll(other)` | O(n) |
| Intersection | `set.retainAll(other)` | O(n) |
| Difference | `set.removeAll(other)` | O(n) |

```java
// Init from array / list
Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3));

// Detect duplicate
boolean hasDup = arr.length != new HashSet<>(Arrays.asList(arr)).size();

// Sorted set
Set<Integer> sorted = new TreeSet<>(set); // O(n log n)
```

---

## Deque / ArrayDeque

| Operation | As Stack | As Queue | Time |
|-----------|----------|----------|------|
| Init | `Deque<T> dq = new ArrayDeque<>();` | same | — |
| Push front | `dq.push(val)` / `dq.addFirst(val)` | `dq.offerFirst(val)` | O(1) |
| Push back | `dq.addLast(val)` | `dq.offer(val)` | O(1) |
| Pop front | `dq.pop()` / `dq.removeFirst()` | `dq.poll()` | O(1) |
| Pop back | `dq.removeLast()` | `dq.pollLast()` | O(1) |
| Peek front | `dq.peek()` / `dq.peekFirst()` | `dq.peek()` | O(1) |
| Peek back | `dq.peekLast()` | `dq.peekLast()` | O(1) |
| Size | `dq.size()` | `dq.size()` | O(1) |
| Is empty | `dq.isEmpty()` | `dq.isEmpty()` | O(1) |

```java
// Use as stack (LIFO)
Deque<Integer> stack = new ArrayDeque<>();
stack.push(1); stack.push(2);
int top = stack.pop(); // 2

// Use as queue (FIFO)
Deque<Integer> queue = new ArrayDeque<>();
queue.offer(1); queue.offer(2);
int front = queue.poll(); // 1

// Monotonic decreasing stack
for (int x : arr) {
    while (!stack.isEmpty() && stack.peek() < x) stack.pop();
    stack.push(x);
}
```

---

## PriorityQueue

| Operation | Method | Time |
|-----------|--------|------|
| Init min-heap | `PriorityQueue<Integer> pq = new PriorityQueue<>();` | — |
| Init max-heap | `new PriorityQueue<>(Collections.reverseOrder())` | — |
| Offer / add | `pq.offer(val)` | O(log n) |
| Poll (remove min) | `pq.poll()` | O(log n) |
| Peek (min) | `pq.peek()` | O(1) |
| Size | `pq.size()` | O(1) |
| Contains | `pq.contains(val)` | O(n) |

```java
// Min-heap (default)
PriorityQueue<Integer> minPQ = new PriorityQueue<>();

// Max-heap
PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Collections.reverseOrder());

// Custom comparator (e.g. sort int[] by second element)
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);

// K largest elements — use min-heap of size k
PriorityQueue<Integer> kLargest = new PriorityQueue<>();
for (int x : arr) {
    kLargest.offer(x);
    if (kLargest.size() > k) kLargest.poll();
}
```

---

## Tree (TreeNode)

```java
// Standard definition
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}
```

| Traversal | Order | Notes |
|-----------|-------|-------|
| In-order | left → root → right | BST gives sorted order |
| Pre-order | root → left → right | copy / serialize |
| Post-order | left → right → root | delete / evaluate |
| Level-order | BFS level by level | shortest path in tree |

```java
// Recursive in-order
void inorder(TreeNode node) {
    if (node == null) return;
    inorder(node.left);
    // process node.val
    inorder(node.right);
}

// Iterative in-order
Deque<TreeNode> stack = new ArrayDeque<>();
TreeNode cur = root;
while (cur != null || !stack.isEmpty()) {
    while (cur != null) { stack.push(cur); cur = cur.left; }
    cur = stack.pop();
    // process cur.val
    cur = cur.right;
}

// Level-order (BFS)
Queue<TreeNode> queue = new ArrayDeque<>();
queue.offer(root);
while (!queue.isEmpty()) {
    int size = queue.size();          // nodes at current level
    for (int i = 0; i < size; i++) {
        TreeNode node = queue.poll();
        // process node.val
        if (node.left  != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
    }
}
```

---

## Graph (Adjacency List)

```java
// Build adjacency list
Map<Integer, List<Integer>> graph = new HashMap<>();
for (int[] edge : edges) {
    graph.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
    graph.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]); // undirected
}
```

```java
// BFS — shortest path / level traversal
boolean[] visited = new boolean[n];
Queue<Integer> queue = new ArrayDeque<>();
queue.offer(start);
visited[start] = true;
while (!queue.isEmpty()) {
    int node = queue.poll();
    for (int neighbor : graph.getOrDefault(node, Collections.emptyList())) {
        if (!visited[neighbor]) {
            visited[neighbor] = true;
            queue.offer(neighbor);
        }
    }
}
```

```java
// DFS — recursive
boolean[] visited = new boolean[n];
void dfs(int node) {
    visited[node] = true;
    for (int neighbor : graph.getOrDefault(node, Collections.emptyList())) {
        if (!visited[neighbor]) dfs(neighbor);
    }
}

// DFS — iterative
Deque<Integer> stack = new ArrayDeque<>();
stack.push(start);
visited[start] = true;
while (!stack.isEmpty()) {
    int node = stack.pop();
    for (int neighbor : graph.getOrDefault(node, Collections.emptyList())) {
        if (!visited[neighbor]) {
            visited[neighbor] = true;
            stack.push(neighbor);
        }
    }
}
```
