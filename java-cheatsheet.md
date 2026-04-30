# Java Interview Cheatsheet

## Table of Contents
1. [ArrayList](#1-arraylist)
2. [LinkedList](#2-linkedlist)
3. [HashMap](#3-hashmap)
4. [HashSet](#4-hashset)
5. [TreeMap / SortedMap](#5-treemap--sortedmap)
6. [PriorityQueue](#6-priorityqueue)
7. [Deque / ArrayDeque](#7-deque--arraydeque)
8. [String](#8-string)
9. [StringBuilder](#9-stringbuilder)
10. [LinkedHashMap](#10-linkedhashmap)
11. [Integer Overflow and Two's Complement](#11-integer-overflow-and-twos-complement)
12. [Big-O Quick Reference](#big-o-quick-reference)

---

## 1. ArrayList

### How it works under the hood
Backed by a plain `Object[]` array. Default initial capacity is **10**.

When you call `add()` and the array is full, it grows by **50%** (`newCapacity = oldCapacity * 3/2 + 1`).
Growth involves `Arrays.copyOf()` — allocating a new array and copying all elements — so occasional adds are O(n), but **amortized O(1)** over many adds.

```
index:  [ 0 | 1 | 2 | 3 | 4 | null | null | null | null | null ]
                              ^size=5        ^capacity=10
```

Random access is O(1) because it's just `array[index]`.
Insertion/deletion in the middle is O(n) because everything after the index must shift.

### Initialization
```java
List<Integer> list = new ArrayList<>();
List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3));
List<Integer> list = new ArrayList<>(initialCapacity);   // avoids resizing
List<Integer> list = Collections.emptyList();            // immutable empty
List<Integer> list = List.of(1, 2, 3);                  // immutable, Java 9+
```

### Key Methods

| Method | Description | Time |
|--------|-------------|------|
| `add(e)` | Append to end | O(1) amortized |
| `add(i, e)` | Insert at index | O(n) |
| `get(i)` | Read at index | O(1) |
| `set(i, e)` | Update at index | O(1) |
| `remove(i)` | Remove by index | O(n) |
| `remove(obj)` | Remove first occurrence | O(n) |
| `size()` | Number of elements | O(1) |
| `contains(e)` | Linear scan | O(n) |
| `indexOf(e)` | First index of element | O(n) |
| `isEmpty()` | Check if empty | O(1) |
| `clear()` | Remove all elements | O(n) |
| `Collections.sort(list)` | Sort in place | O(n log n) |
| `Collections.reverse(list)` | Reverse in place | O(n) |
| `subList(from, to)` | View of a range (exclusive to) | O(1) |

### Common Interview Patterns / Gotchas
```java
// Iterating while removing — use iterator or removeIf
list.removeIf(x -> x % 2 == 0);

// Remove by object vs by index (autoboxing trap)
list.remove(Integer.valueOf(5));  // removes the object 5
list.remove(5);                   // removes index 5
list.subList(fromIndex, toIndex).clear(); //O(n) removal efficient

// Convert array to list
String[] arr = {"a", "b"};
List<String> l = new ArrayList<>(Arrays.asList(arr));

// Convert list to array
Integer[] arr2 = list.toArray(new Integer[0]);

// Sort with comparator
list.sort((a, b) -> b - a);                          // descending
list.sort(Comparator.comparingInt(String::length));  // by length
```

---

## 2. LinkedList

### How it works under the hood
Java's `LinkedList` is a **doubly linked list**. Each node holds:
```
Node { E item; Node prev; Node next; }
```
The list keeps pointers to `first` (head) and `last` (tail).

```
null <-- [A] <--> [B] <--> [C] --> null
          ^                  ^
        first               last
```

- Head/tail insert/remove: **O(1)** — just pointer updates.
- Middle access/insert: **O(n)** — must traverse from nearest end.
- No random access in O(1) — `get(i)` walks the list.

Also implements `Deque`, so it can be used as a stack or queue.

### Initialization
```java
LinkedList<Integer> ll = new LinkedList<>();
Deque<Integer> deque = new LinkedList<>();   // used as deque/queue
Queue<Integer> queue = new LinkedList<>();   // used as queue
```

### Key Methods

| Method | Description | Time |
|--------|-------------|------|
| `addFirst(e)` / `offerFirst(e)` | Add to head | O(1) |
| `addLast(e)` / `offerLast(e)` | Add to tail | O(1) |
| `removeFirst()` / `pollFirst()` | Remove from head | O(1) |
| `removeLast()` / `pollLast()` | Remove from tail | O(1) |
| `peekFirst()` | View head (null if empty) | O(1) |
| `peekLast()` | View tail (null if empty) | O(1) |
| `get(i)` | Access by index | O(n) |
| `add(i, e)` | Insert at index | O(n) |
| `size()` | Number of elements | O(1) |
| `contains(e)` | Linear scan | O(n) |

### Common Interview Patterns / Gotchas
```java
// As a Queue (FIFO)
Queue<Integer> q = new LinkedList<>();
q.offer(1);      // enqueue
q.poll();        // dequeue — returns null if empty (vs remove() which throws)
q.peek();        // front — returns null if empty

// As a Stack (LIFO) — prefer ArrayDeque for performance
Deque<Integer> stack = new LinkedList<>();
stack.push(1);   // addFirst
stack.pop();     // removeFirst
stack.peek();    // peekFirst

// Prefer ArrayDeque over LinkedList for pure stack/queue needs
// LinkedList has per-node memory overhead; ArrayDeque is a circular buffer
```

---

## 3. HashMap

### How it works under the hood
Internally: an array of **buckets** (default 16). Each bucket is a linked list (or a **Red-Black Tree** when a bucket has ≥ 8 entries — added in Java 8).

**Put flow:**
1. Compute `hash(key)` — spreads bits to reduce collisions.
2. `bucketIndex = hash & (capacity - 1)` — fast modulo via bitwise AND.
3. Walk the bucket's linked list. If key exists, update. Else append.

**Load factor** (default 0.75): when `size > capacity * 0.75`, rehash — double capacity and redistribute all entries. This is O(n) but amortized to O(1) per put.

```
buckets:
 [0]  --> null
 [1]  --> ("apple", 1) --> ("grape", 3)    // collision, same bucket
 [2]  --> ("banana", 2)
 ...
```

**Key equality:** uses `hashCode()` first (cheap), then `equals()` to confirm. Always override both together.

### Initialization
```java
Map<String, Integer> map = new HashMap<>();
Map<String, Integer> map = new HashMap<>(initialCapacity, loadFactor);
Map<String, Integer> map = Map.of("a", 1, "b", 2);  // immutable, Java 9+
```

### Key Methods

| Method | Description | Time |
|--------|-------------|------|
| `put(k, v)` | Insert / update | O(1) avg |
| `get(k)` | Read value | O(1) avg |
| `remove(k)` | Delete by key | O(1) avg |
| `containsKey(k)` | Key exists? | O(1) avg |
| `containsValue(v)` | Value exists? | O(n) |
| `size()` | Number of entries | O(1) |
| `getOrDefault(k, def)` | Get or fallback | O(1) avg |
| `putIfAbsent(k, v)` | Put only if key missing | O(1) avg |
| `merge(k, v, fn)` | Update with function | O(1) avg |
| `computeIfAbsent(k, fn)` | Compute and store if absent | O(1) avg |
| `keySet()` | Set of all keys | O(1) |
| `values()` | Collection of all values | O(1) |
| `entrySet()` | Set of key-value pairs | O(1) |

### Common Interview Patterns / Gotchas
```java
// Frequency count
for (char c : s.toCharArray()) {
    map.put(c, map.getOrDefault(c, 0) + 1);
    // or
    map.merge(c, 1, Integer::sum);
}

// Group by
Map<Integer, List<String>> groups = new HashMap<>();
for (String word : words) {
    int key = word.length();
    groups.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
}

// Iteration
for (Map.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + " -> " + entry.getValue());
}

// GOTCHA: .equals() vs ==
// HashMap keys use .equals() for comparison, NOT ==
// Two different String objects "abc" and "abc" ARE considered the same key
// But two different Integer[] or int[] objects are NOT (arrays don't override equals)

// Sorted iteration — use TreeMap or sort entrySet
map.entrySet().stream()
   .sorted(Map.Entry.comparingByValue())
   .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
```

---

## 4. HashSet

### How it works under the hood
`HashSet<E>` is a thin wrapper over `HashMap<E, PRESENT>` where `PRESENT` is a static dummy `Object`. Every element you add becomes a **key** in the backing HashMap; the value is always the same dummy object.

All the internals — bucketing, load factor, tree-ification at 8 collisions — are identical to HashMap.

```
HashSet internally:
  map = { "apple" -> DUMMY, "banana" -> DUMMY, "grape" -> DUMMY }
```

### Initialization
```java
Set<Integer> set = new HashSet<>();
Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3));
Set<Integer> set = new HashSet<>(otherCollection);
Set<Integer> set = Set.of(1, 2, 3);     // immutable, Java 9+
```

### Key Methods

| Method | Description | Time |
|--------|-------------|------|
| `add(e)` | Insert element | O(1) avg |
| `remove(e)` | Delete element | O(1) avg |
| `contains(e)` | Membership check | O(1) avg |
| `size()` | Number of elements | O(1) |
| `isEmpty()` | Empty check | O(1) |
| `addAll(coll)` | Union in-place | O(n) |
| `retainAll(coll)` | Intersection in-place | O(n) |
| `removeAll(coll)` | Difference in-place | O(n) |

### Common Interview Patterns / Gotchas
```java
// Deduplicate a list
List<Integer> unique = new ArrayList<>(new HashSet<>(list));

// Check for duplicates
boolean hasDup = list.size() != new HashSet<>(list).size();

// Two-sum complement check
Set<Integer> seen = new HashSet<>();
for (int n : nums) {
    if (seen.contains(target - n)) return true;
    seen.add(n);
}

// Set operations (non-destructive — work on copies)
Set<Integer> union = new HashSet<>(setA);      union.addAll(setB);
Set<Integer> inter = new HashSet<>(setA);      inter.retainAll(setB);
Set<Integer> diff  = new HashSet<>(setA);      diff.removeAll(setB);

// GOTCHA: iteration order is undefined — use LinkedHashSet for insertion order
//         or TreeSet for sorted order
```

---

## 5. TreeMap / SortedMap

### How it works under the hood
`TreeMap` is backed by a **Red-Black Tree** — a self-balancing BST where every operation (insert, delete, lookup) is **O(log n)** guaranteed (not amortized).

Red-Black properties keep the tree height bounded at `2 * log2(n)`:
- Every node is red or black.
- Root is black; red nodes can't have red children.
- All paths from root to null have the same number of black nodes.

Keys must be **Comparable** or a `Comparator` must be provided. Keys are always iterated in **sorted order**.

```
        [5]B
       /    \
    [3]R    [7]R
   /   \   /   \
 [1]B [4]B [6]B [9]B
```

### Initialization
```java
TreeMap<Integer, String> map = new TreeMap<>();              // natural order
TreeMap<Integer, String> map = new TreeMap<>(Comparator.reverseOrder());  // reverse
```

### Key Methods

| Method | Description | Time |
|--------|-------------|------|
| `put(k, v)` | Insert / update | O(log n) |
| `get(k)` | Read value | O(log n) |
| `remove(k)` | Delete | O(log n) |
| `containsKey(k)` | Key exists? | O(log n) |
| `firstKey()` | Smallest key | O(log n) |
| `lastKey()` | Largest key | O(log n) |
| `floorKey(k)` | Largest key ≤ k | O(log n) |
| `ceilingKey(k)` | Smallest key ≥ k | O(log n) |
| `lowerKey(k)` | Largest key < k | O(log n) |
| `higherKey(k)` | Smallest key > k | O(log n) |
| `headMap(toKey)` | View of keys < toKey | O(log n) |
| `tailMap(fromKey)` | View of keys ≥ fromKey | O(log n) |
| `subMap(from, to)` | View of keys in [from, to) | O(log n) |
| `entrySet()` | Entries in sorted order | O(1) |

### Common Interview Patterns / Gotchas
```java
// Sorted frequency map
TreeMap<Integer, Integer> freq = new TreeMap<>();
for (int n : nums) freq.merge(n, 1, Integer::sum);

// Find closest value
int target = 7;
Integer floor = map.floorKey(target);    // largest key ≤ 7
Integer ceil  = map.ceilingKey(target);  // smallest key ≥ 7

// Sliding window maximum using TreeMap (maintains sorted order)
// Range queries — O(log n) per operation vs O(n) for unsorted

// GOTCHA: TreeMap uses compareTo() for equality, NOT equals()
// If compareTo() returns 0, keys are treated as the same — even if equals() disagrees
// Always ensure Comparable consistency: (a.compareTo(b)==0) ↔ (a.equals(b))
```

---

## 6. PriorityQueue

### How it works under the hood
Backed by a **binary min-heap** stored as an array. For a node at index `i`:
- Left child: `2i + 1`
- Right child: `2i + 2`
- Parent: `(i - 1) / 2`

The **heap property**: every parent ≤ both children. So `queue[0]` is always the minimum.

**Add (offer):** place at the end → **sift up** (swap with parent while smaller) → O(log n).  
**Remove min (poll):** swap root with last element → remove last → **sift down** (swap with smaller child while larger) → O(log n).  
**Peek:** just read `queue[0]` → O(1).

```
Heap array: [1, 3, 2, 7, 5, 9, 4]

Tree view:
        1
       / \
      3   2
     / \ / \
    7  5 9  4
```

### Initialization
```java
PriorityQueue<Integer> minPQ = new PriorityQueue<>();                        // min-heap
PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Comparator.reverseOrder()); // max-heap
PriorityQueue<int[]>   pq    = new PriorityQueue<>((a, b) -> a[0] - b[0]);  // by first element
PriorityQueue<Integer> pq    = new PriorityQueue<>(collection);              // heapify O(n)
```

### Key Methods

| Method | Description | Time |
|--------|-------------|------|
| `offer(e)` / `add(e)` | Insert element | O(log n) |
| `poll()` | Remove and return min | O(log n) |
| `peek()` | View min without removing | O(1) |
| `remove(e)` | Remove specific element | O(n) |
| `contains(e)` | Check membership | O(n) |
| `size()` | Number of elements | O(1) |
| `isEmpty()` | Empty check | O(1) |

### Common Interview Patterns / Gotchas
```java
// K largest elements
PriorityQueue<Integer> minPQ = new PriorityQueue<>();
for (int n : nums) {
    minPQ.offer(n);
    if (minPQ.size() > k) minPQ.poll();
}
// minPQ now contains k largest; top is the kth largest

// K smallest elements
PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Comparator.reverseOrder());
for (int n : nums) {
    maxPQ.offer(n);
    if (maxPQ.size() > k) maxPQ.poll();
}

// Dijkstra — min-heap on (distance, node)
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
pq.offer(new int[]{0, src});

// Merge k sorted lists — push (value, listIndex, elementIndex)

// GOTCHA: PriorityQueue does NOT iterate in sorted order
// Only poll() guarantees order. To drain in order, repeatedly poll().
// GOTCHA: contains() and remove(object) are O(n) — no index structure.
// GOTCHA: (a - b) comparator can overflow for large negative values.
//         Use Integer.compare(a, b) for safety.
```

---

## 7. Deque / ArrayDeque

### How it works under the hood
`ArrayDeque` is backed by a **circular buffer** — a fixed-size array where `head` and `tail` are indices that move forward and wrap around via modulo. `head` points to the first live element; `tail` points to the next empty slot where the next element will be written.

Slots outside `[head, tail)` are not empty — they hold stale values from previous operations, but are treated as dead space and will be overwritten on the next write to that slot.

The deque is full when `(tail + 1) % capacity == head`. At that point it **doubles** in size and copies elements into a fresh array.

#### Step-by-step example (capacity = 8)

```
Start: empty
  idx:  [ 0 ][ 1 ][ 2 ][ 3 ][ 4 ][ 5 ][ 6 ][ 7 ]
  val:  [   ][   ][   ][   ][   ][   ][   ][   ]
         ^H
         ^T
  live: (none)    H=0, T=0

addLast(A)  →  write A at tail(0), tail = (0+1)%8 = 1
  idx:  [ 0 ][ 1 ][ 2 ][ 3 ][ 4 ][ 5 ][ 6 ][ 7 ]
  val:  [ A ][   ][   ][   ][   ][   ][   ][   ]
         ^H   ^T
  live: A     H=0, T=1

addLast(B), addLast(C)  →  write at T, advance T each time
  idx:  [ 0 ][ 1 ][ 2 ][ 3 ][ 4 ][ 5 ][ 6 ][ 7 ]
  val:  [ A ][ B ][ C ][   ][   ][   ][   ][   ]
         ^H             ^T
  live: A B C     H=0, T=3

addFirst(Z)  →  head = (0-1+8)%8 = 7, write Z there
  idx:  [ 0 ][ 1 ][ 2 ][ 3 ][ 4 ][ 5 ][ 6 ][ 7 ]
  val:  [ A ][ B ][ C ][   ][   ][   ][   ][ Z ]
                    ^T                      ^H
  live: Z A B C     H=7, T=3

pollFirst()  →  read head(7)=Z, head = (7+1)%8 = 0
               slot 7 still holds Z but is now dead (will be overwritten later)
  idx:  [ 0 ][ 1 ][ 2 ][ 3 ][ 4 ][ 5 ][ 6 ][ 7 ]
  val:  [ A ][ B ][ C ][   ][   ][   ][   ][ Z* ]   (* = stale, ignored)
         ^H             ^T
  live: A B C     H=0, T=3

addLast(D), addLast(E), addLast(F), addLast(G)
  idx:  [ 0 ][ 1 ][ 2 ][ 3 ][ 4 ][ 5 ][ 6 ][ 7 ]
  val:  [ A ][ B ][ C ][ D ][ E ][ F ][ G ][ Z* ]
         ^H                                  ^T
  live: A B C D E F G     H=0, T=7
  (one slot before T wrapping to H means full — 7 live elements in cap-8 array)

addLast(H)  →  FULL: (T+1)%8 == H  →  RESIZE to capacity 16, copy live elements
  idx:  [ 0 ][ 1 ][ 2 ][ 3 ][ 4 ][ 5 ][ 6 ][ 7 ][ 8 ]...[ 15 ]
  val:  [ A ][ B ][ C ][ D ][ E ][ F ][ G ][ H ][   ]...[    ]
         ^H                                       ^T
  live: A B C D E F G H     H=0, T=8
```

**Key rules:**
- `addLast` → write at `tail`, then `tail = (tail + 1) % capacity`
- `addFirst` → `head = (head - 1 + capacity) % capacity`, then write at `head`
- `pollFirst` → read `array[head]`, then `head = (head + 1) % capacity`
- `pollLast` → `tail = (tail - 1 + capacity) % capacity`, then read `array[tail]`
- Resize when `(tail + 1) % capacity == head` (one slot always kept empty as sentinel)

Adding to either end just moves a pointer — **O(1)**. The array grows (doubles) when full.

No null elements allowed. Faster than `LinkedList` for stack/queue because no per-node allocation overhead.

### Initialization
```java
Deque<Integer> deque  = new ArrayDeque<>();    // general double-ended queue
Deque<Integer> stack  = new ArrayDeque<>();    // use as stack (LIFO)
Queue<Integer> queue  = new ArrayDeque<>();    // use as queue (FIFO)
```

### Key Methods

| Method | Throws if empty? | Description | Time |
|--------|-----------------|-------------|------|
| `offerFirst(e)` / `addFirst(e)` | addFirst throws | Add to front | O(1) |
| `offerLast(e)` / `addLast(e)` / `offer(e)` | addLast throws | Add to back | O(1) |
| `pollFirst()` / `removeFirst()` | removeFirst throws | Remove from front | O(1) |
| `pollLast()` / `removeLast()` | removeLast throws | Remove from back | O(1) |
| `peekFirst()` / `getFirst()` | getFirst throws | View front | O(1) |
| `peekLast()` / `getLast()` | getLast throws | View back | O(1) |
| `push(e)` | — | Stack push (addFirst) | O(1) |
| `pop()` | throws | Stack pop (removeFirst) | O(1) |
| `peek()` | — | Stack peek (peekFirst) | O(1) |
| `size()` | — | Number of elements | O(1) |
| `contains(e)` | — | Linear scan | O(n) |

### Common Interview Patterns / Gotchas
```java
// Monotonic decreasing deque (sliding window maximum)
Deque<Integer> dq = new ArrayDeque<>();   // stores indices
for (int i = 0; i < n; i++) {
    while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i])
        dq.pollLast();   // pop smaller elements from back
    dq.offerLast(i);
    if (dq.peekFirst() <= i - k)
        dq.pollFirst();  // remove elements out of window
    if (i >= k - 1)
        result[i - k + 1] = nums[dq.peekFirst()];
}

// BFS queue
Queue<Integer> bfs = new ArrayDeque<>();
bfs.offer(start);
while (!bfs.isEmpty()) {
    int node = bfs.poll();
    // process node
}

// GOTCHA: ArrayDeque does NOT allow null elements (use LinkedList if you need null)
// GOTCHA: prefer ArrayDeque over Stack class (Stack is legacy, synchronized overhead)
// GOTCHA: prefer ArrayDeque over LinkedList for pure stack/queue (better cache locality)
```

---

## 8. String

### How it works under the hood
`String` in Java is an **immutable** sequence of `char` values backed by a `char[]` (or `byte[]` in Java 9+ with compact strings). Immutability means every "modification" creates a **new** String object.

**String Pool (interning):** String literals are stored in a special heap region. Two literal `"hello"` references point to the **same** object. `new String("hello")` bypasses the pool — creates a new object on the heap.

```
String pool:      heap:
  "hello" <---+-- str1 = "hello"     (same object)
              +-- str2 = "hello"     (same object)
                  str3 = new String("hello")   (different object!)
```

`str1 == str2` → `true` (same pool object)  
`str1 == str3` → `false` (different heap objects)  
`str1.equals(str3)` → `true` (same content)

### Initialization
```java
String s = "hello";
String s = new String("hello");          // avoid — bypasses pool
String s = new String(charArray);
String s = String.valueOf(42);           // int → String
String s = String.format("x=%d", x);
```

### Key Methods

| Method | Description | Time |
|--------|-------------|------|
| `s.length()` | Length | O(1) |
| `s.charAt(i)` | Char at index | O(1) |
| `s.substring(i, j)` | Substring [i, j) | O(m), m = j-i |
| `s.indexOf(sub)` | First occurrence index (-1 if not found) | O(n*m) |
| `s.contains(sub)` | Contains substring? | O(n*m) |
| `s.startsWith(pre)` | Starts with prefix? | O(n) |
| `s.endsWith(suf)` | Ends with suffix? | O(n) |
| `s.equals(t)` | Content equality | O(n) |
| `s.equalsIgnoreCase(t)` | Case-insensitive equality | O(n) |
| `s.compareTo(t)` | Lexicographic comparison | O(n) |
| `s.toLowerCase()` / `toUpperCase()` | Case conversion | O(n) |
| `s.trim()` | Remove leading/trailing spaces | O(n) |
| `s.strip()` | Unicode-aware trim (Java 11+) | O(n) |
| `s.replace(old, new)` | Replace all occurrences | O(n) |
| `s.split(regex)` | Split into array | O(n) |
| `s.toCharArray()` | Convert to char[] | O(n) |
| `s.isEmpty()` | Length == 0? | O(1) |
| `s.isBlank()` | Only whitespace? (Java 11+) | O(n) |
| `String.join(delim, parts)` | Join with delimiter | O(n) |
| `String.valueOf(x)` | Convert to String | O(1)/O(n) |

### Common Interview Patterns / Gotchas
```java
// GOTCHA: ALWAYS use .equals() for string comparison, never ==
if (s.equals("hello")) { }        // correct
if (s == "hello") { }             // WRONG — may fail for non-pooled strings

// Char operations
char c = s.charAt(i);
int digit = c - '0';              // char to digit
int idx   = c - 'a';             // char to 0-based alphabet index
char back = (char)('a' + idx);   // back to char

// Palindrome check
String rev = new StringBuilder(s).reverse().toString();
boolean isPalin = s.equals(rev);

// Anagram check — sort and compare
char[] a = s.toCharArray(); Arrays.sort(a);
char[] b = t.toCharArray(); Arrays.sort(b);
boolean isAnagram = Arrays.equals(a, b);

// Split gotchas
"a,b,,c".split(",")     // ["a", "b", "", "c"]  — keeps empty strings
"a,b,,c".split(",", -1) // same, but trailing empty strings also kept

// String concatenation in a loop is O(n^2) — use StringBuilder
// "a" + "b" + "c" in a loop creates new objects each iteration

// Comparing with null
Objects.equals(s1, s2);   // null-safe equals
```

---

## 9. StringBuilder

### How it works under the hood
`StringBuilder` wraps a `char[]` with a `count` (current length). Default capacity is **16**. When appending would exceed capacity, it grows: `newCapacity = (old + 1) * 2`.

Because the array is mutable, appending to an existing builder doesn't create a new object — it writes directly into the buffer. This makes repeated appends **amortized O(1)** vs String's O(n) per concat.

`StringBuffer` is the thread-safe (synchronized) version — avoid it unless you actually need thread safety.

### Initialization
```java
StringBuilder sb = new StringBuilder();
StringBuilder sb = new StringBuilder("initial");
StringBuilder sb = new StringBuilder(capacity);   // avoids resizing
```

### Key Methods

| Method | Description | Time |
|--------|-------------|------|
| `sb.append(x)` | Append any type | O(1) amortized |
| `sb.insert(i, x)` | Insert at index | O(n) |
| `sb.delete(i, j)` | Delete [i, j) | O(n) |
| `sb.deleteCharAt(i)` | Delete single char | O(n) |
| `sb.replace(i, j, str)` | Replace [i, j) with str | O(n) |
| `sb.reverse()` | Reverse in place | O(n) |
| `sb.charAt(i)` | Read char at index | O(1) |
| `sb.setCharAt(i, c)` | Update char at index | O(1) |
| `sb.indexOf(str)` | Find substring | O(n*m) |
| `sb.length()` | Current length | O(1) |
| `sb.toString()` | Convert to String | O(n) |

### Common Interview Patterns / Gotchas
```java
// Build string in loop
StringBuilder sb = new StringBuilder();
for (String word : words) {
    if (sb.length() > 0) sb.append(", ");
    sb.append(word);
}
String result = sb.toString();

// Reverse a string
String rev = new StringBuilder(s).reverse().toString();

// Build result character by character
StringBuilder sb = new StringBuilder();
for (char c : s.toCharArray()) {
    if (c != ' ') sb.append(c);
}

// GOTCHA: sb.toString() creates a new String — only call once at the end
// GOTCHA: StringBuilder is NOT thread-safe; use StringBuffer only if needed
```

---

## 10. LinkedHashMap

### How it works under the hood
`LinkedHashMap` extends `HashMap` and adds a **doubly linked list** threaded through all entries in insertion order (or access order if configured). Each node is `LinkedHashMap.Entry` which extends `HashMap.Node` with two extra pointers:

```java
static class Entry<K,V> extends HashMap.Node<K,V> {
    Entry<K,V> before, after;   // prev/next in insertion-order linked list
}
```

The HashMap bucket array handles O(1) lookup exactly as before. The linked list is maintained in parallel purely for **iteration order** — it does not affect hashing or bucket placement.

```
Bucket array (for O(1) lookup):        Insertion-order linked list (for iteration):
  [0] → ("b", 2)                         head ↔ ("a",1) ↔ ("b",2) ↔ ("c",3) ↔ tail
  [1] → ("a", 1)
  [2] → ("c", 3)

  Iteration always yields: a, b, c  (insertion order, regardless of bucket layout)
```

**Access-order mode** (used for LRU cache): on every `get()` or `put()`, the accessed entry is unlinked and moved to the **tail** of the linked list. Oldest-accessed entry is always at the head — evict it to implement LRU.

### Initialization
```java
// Insertion-order (default)
LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
LinkedHashMap<String, Integer> map = new LinkedHashMap<>(initialCapacity, loadFactor);

// Access-order — every get/put moves entry to tail
LinkedHashMap<String, Integer> map = new LinkedHashMap<>(16, 0.75f, true);

// LRU Cache — override removeEldestEntry
LinkedHashMap<Integer, Integer> lru = new LinkedHashMap<>(capacity, 0.75f, true) {
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;   // auto-evict when over capacity
    }
};
```

### Key Methods

| Method | Description | Time |
|--------|-------------|------|
| `put(k, v)` | Insert / update | O(1) avg |
| `get(k)` | Read value (moves to tail in access-order) | O(1) avg |
| `remove(k)` | Delete, unlinks from list | O(1) avg |
| `containsKey(k)` | Key exists? | O(1) avg |
| `size()` | Number of entries | O(1) |
| `entrySet()` | Entries in insertion/access order | O(1) |
| `keySet()` | Keys in insertion/access order | O(1) |

All operations are identical to `HashMap` in complexity — the linked list maintenance is O(1) pointer updates.

### Common Interview Patterns / Gotchas
```java
// LRU Cache (classic interview problem)
class LRUCache {
    private final int capacity;
    private final LinkedHashMap<Integer, Integer> map;

    LRUCache(int capacity) {
        this.capacity = capacity;
        // accessOrder=true: get() moves entry to tail; head = least recently used
        this.map = new LinkedHashMap<>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
    }

    public int get(int key) {
        return map.getOrDefault(key, -1);   // moves to tail automatically
    }

    public void put(int key, int value) {
        map.put(key, value);                // evicts eldest if over capacity
    }
}

// Deduplicate while preserving insertion order
Set<Integer> seen = new LinkedHashSet<>();   // LinkedHashSet = same idea, keys only
for (int n : nums) seen.add(n);

// Predictable iteration order (unlike HashMap)
// Use LinkedHashMap whenever you need both O(1) lookup AND stable iteration order

// GOTCHA: In access-order mode, get() is a structural modification —
//         do NOT use forEach + get() together, it will throw ConcurrentModificationException
//         Iterate via entrySet() instead.

// GOTCHA: LinkedHashMap uses slightly more memory than HashMap per entry
//         (two extra pointers: before, after per node)
```

---

## 11. Integer Overflow and Two's Complement

### How two's complement works
Java `int` is a **32-bit signed integer**. It uses **two's complement** representation, meaning the most significant bit (MSB) is the **sign bit** (0 = positive, 1 = negative).

```
 bit 31  bits 30–0
  [sign] [magnitude in two's complement]
```

**Why two's complement?** It lets the CPU use the same addition circuit for both positive and negative numbers — no special cases needed.

**To negate a number in two's complement:** flip all bits, then add 1.
```
 5  = 0000 0000 0000 0000 0000 0000 0000 0101
~5  = 1111 1111 1111 1111 1111 1111 1111 1010   (flip all bits)
-5  = 1111 1111 1111 1111 1111 1111 1111 1011   (add 1)
```

**Limits:**
```
Integer.MAX_VALUE =  2^31 - 1 =  2,147,483,647  = 0x7FFFFFFF
Integer.MIN_VALUE = -2^31     = -2,147,483,648  = 0x80000000
```

Notice `MIN_VALUE` has no positive counterpart — there are 2^32 bit patterns, split as 2^31 negatives, 1 zero, and 2^31 - 1 positives.

### What overflow looks like
When arithmetic exceeds the 32-bit range, the result **wraps around silently** — no exception is thrown.

```
MAX_VALUE + 1  →  MIN_VALUE   (wraps to most negative)
MIN_VALUE - 1  →  MAX_VALUE   (wraps to most positive)
-MIN_VALUE     →  MIN_VALUE   (no positive counterpart — negating MIN stays MIN!)
```

```java
int x = Integer.MAX_VALUE;
System.out.println(x + 1);   // -2147483648  (wrapped!)

int y = Integer.MIN_VALUE;
System.out.println(-y);       // -2147483648  (still MIN_VALUE!)
System.out.println(Math.abs(Integer.MIN_VALUE));  // -2147483648  (trap!)
```

### Common overflow traps in interviews

**1. Comparator subtraction trick**
```java
// WRONG — overflows when a is large positive and b is large negative
pq = new PriorityQueue<>((a, b) -> a - b);

// CORRECT
pq = new PriorityQueue<>((a, b) -> Integer.compare(a, b));
pq = new PriorityQueue<>(Comparator.naturalOrder());
```

**2. Sum of two ints**
```java
int mid = (lo + hi) / 2;        // WRONG — lo + hi can overflow
int mid = lo + (hi - lo) / 2;   // CORRECT
```

**3. Multiplication**
```java
int area = width * height;       // overflows if both are ~50000
long area = (long) width * height;  // cast ONE operand before multiply
```

**4. mod on negative numbers**
```java
int r = -7 % 3;   // r = -1 in Java (result takes sign of dividend)
// To always get a non-negative remainder:
int r = ((n % mod) + mod) % mod;
```

### How to avoid overflow
```java
// Use long for intermediate calculations
long result = (long) a * b;

// Checked arithmetic (Java 8+) — throws ArithmeticException on overflow
int safe = Math.addExact(a, b);
int safe = Math.multiplyExact(a, b);
int safe = Math.subtractExact(a, b);

// Constants
Integer.MAX_VALUE   //  2_147_483_647
Integer.MIN_VALUE   // -2_147_483_648
Long.MAX_VALUE      //  9_223_372_036_854_775_807
Long.MIN_VALUE      // -9_223_372_036_854_775_808

// int vs long capacity
int  → safe up to ~2.1 × 10^9
long → safe up to ~9.2 × 10^18
```

### Bit manipulation with two's complement
```java
// Check if power of 2 (works because powers of 2 have exactly one bit set)
boolean isPow2 = n > 0 && (n & (n - 1)) == 0;

// n & (n-1) clears the lowest set bit
// n & (-n) isolates the lowest set bit  (-n is ~n + 1 in two's complement)

// Arithmetic right shift (>>) preserves sign bit — fills with sign bit
-8 >> 1   // = -4  (11111000 >> 1 = 11111100)

// Logical right shift (>>>) fills with 0 always
-8 >>> 1  // = 2147483644  (fills MSB with 0)

// XOR trick: a ^ a = 0, a ^ 0 = a  — find single non-duplicate element
int single = 0;
for (int n : nums) single ^= n;
```

---

## 12. Big-O Quick Reference

| Structure | Access | Search | Insert | Delete | Notes |
|-----------|--------|--------|--------|--------|-------|
| ArrayList | O(1) | O(n) | O(1)* / O(n) | O(n) | *amortized at tail |
| LinkedList | O(n) | O(n) | O(1) head/tail | O(1) head/tail | O(n) in middle |
| HashMap | — | O(1)* | O(1)* | O(1)* | *average; O(n) worst |
| HashSet | — | O(1)* | O(1)* | O(1)* | *average; O(n) worst |
| TreeMap | — | O(log n) | O(log n) | O(log n) | Sorted; Red-Black tree |
| TreeSet | — | O(log n) | O(log n) | O(log n) | Sorted; backed by TreeMap |
| PriorityQueue | O(1) peek | O(n) | O(log n) | O(log n) poll / O(n) arbitrary |
| ArrayDeque | O(1) ends | O(n) | O(1) ends | O(1) ends | Circular buffer |
| String | O(1) | O(n) | — | — | Immutable |
| StringBuilder | O(1) | O(n) | O(1)* | O(n) | *amortized append |
