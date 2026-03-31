package linkedlist;

import java.util.HashMap;

public class LRUCache {
    public class ListNode{
        int key;
        int value;
        ListNode next;
        ListNode prev;
        public ListNode(int key, int x){
            this.key = key;
            this.value = x;
            next = null;
        }
    }

    private final int capacity;
    private HashMap<Integer, ListNode> mp;
    private ListNode head;
    private ListNode tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.mp = new HashMap<>();
        head = new ListNode(-1, -1);
        tail = new ListNode(-1, -1);
        head.next = tail;
        tail.prev = head;
        // head.prev = null;
        // tail.next = null;
    }

    public void remove(ListNode node){
        ListNode p = node.prev;
        ListNode n = node.next;
        p.next = n;
        n.prev = p;

        node.next = null;
        node.prev = null;
    }

    public void addFront(ListNode node){
        ListNode n = head.next;
        head.next = node;
        node.prev = head;

        node.next = n;
        n.prev = node;
    }

    public int get(int key) {
        if(!mp.containsKey(key)){
            return -1;
        }
        ListNode keyNode = mp.get(key);
        //to implement
        remove(keyNode);
        addFront(keyNode);
        return keyNode.value;
    }

    public void set(int key, int value) {
        if(!mp.containsKey(key)){
            //check capacity
            if(mp.size()==capacity){
                ListNode toRemove = tail.prev;
                remove(toRemove);
                mp.remove(toRemove.key);
            }
            ListNode newNode = new ListNode(key, value);
            addFront(newNode);
            mp.put(key, newNode);
        }
        else{
            ListNode keyNode = mp.get(key);
            keyNode.value = value;
            remove(keyNode);
            addFront(keyNode);
        }
    }
}
