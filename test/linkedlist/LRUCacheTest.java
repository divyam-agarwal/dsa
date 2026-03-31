package linkedlist;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    // ─── InterviewBit example ────────────────────────────────────────────────

    @Test
    void interviewBitExample() {
        // capacity = 2
        // set(1, 10), set(5, 12)
        // get(5)  → 12
        // get(1)  → 10
        // get(10) → -1
        // set(6, 14)  — evicts key 5 (LRU after the two gets above made 1 MRU)
        // get(5)  → -1
        LRUCache cache = new LRUCache(2);
        cache.set(1, 10);
        cache.set(5, 12);
        assertEquals(12, cache.get(5));
        assertEquals(10, cache.get(1));
        assertEquals(-1, cache.get(10));
        cache.set(6, 14);
        assertEquals(-1, cache.get(5));
    }

    // ─── Basic get / set ─────────────────────────────────────────────────────

    @Test
    void getMissingKeyReturnsMinusOne() {
        LRUCache cache = new LRUCache(3);
        assertEquals(-1, cache.get(42));
    }

    @Test
    void setThenGet() {
        LRUCache cache = new LRUCache(3);
        cache.set(1, 100);
        assertEquals(100, cache.get(1));
    }

    @Test
    void updateExistingKey() {
        LRUCache cache = new LRUCache(3);
        cache.set(1, 10);
        cache.set(1, 99);
        assertEquals(99, cache.get(1));
    }

    // ─── Eviction ────────────────────────────────────────────────────────────

    @Test
    void evictsLeastRecentlyUsedOnInsert() {
        LRUCache cache = new LRUCache(2);
        cache.set(1, 1);
        cache.set(2, 2);
        cache.set(3, 3);   // evicts key 1 (LRU)
        assertEquals(-1, cache.get(1));
        assertEquals(2, cache.get(2));
        assertEquals(3, cache.get(3));
    }

    @Test
    void getRefreshesRecencyBeforeEviction() {
        LRUCache cache = new LRUCache(2);
        cache.set(1, 1);
        cache.set(2, 2);
        cache.get(1);      // key 1 is now MRU; key 2 becomes LRU
        cache.set(3, 3);   // evicts key 2
        assertEquals(1, cache.get(1));
        assertEquals(-1, cache.get(2));
        assertEquals(3, cache.get(3));
    }

    @Test
    void updateRefreshesRecencyBeforeEviction() {
        LRUCache cache = new LRUCache(2);
        cache.set(1, 1);
        cache.set(2, 2);
        cache.set(1, 10);  // update key 1 → now MRU; key 2 becomes LRU
        cache.set(3, 3);   // evicts key 2
        assertEquals(10, cache.get(1));
        assertEquals(-1, cache.get(2));
        assertEquals(3, cache.get(3));
    }

    // ─── Capacity edge cases ─────────────────────────────────────────────────

    @Test
    void capacityOne() {
        LRUCache cache = new LRUCache(1);
        cache.set(1, 10);
        assertEquals(10, cache.get(1));
        cache.set(2, 20);  // evicts key 1
        assertEquals(-1, cache.get(1));
        assertEquals(20, cache.get(2));
    }

    @Test
    void repeatedEvictions() {
        LRUCache cache = new LRUCache(2);
        cache.set(1, 1);
        cache.set(2, 2);
        cache.set(3, 3);   // evicts 1
        cache.set(4, 4);   // evicts 2
        assertEquals(-1, cache.get(1));
        assertEquals(-1, cache.get(2));
        assertEquals(3, cache.get(3));
        assertEquals(4, cache.get(4));
    }
}
