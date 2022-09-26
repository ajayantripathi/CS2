package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IDictionary;

import java.util.Iterator;

public class MoveToFrontDictionary<K, V> implements IDictionary<K,V> {
    private ListNode<K,V> head;
    private int size;

    private static class ListNode<K,V> {
        public K key;
        public V value;
        public ListNode<K,V> prev;
        public ListNode<K, V> next;

        public ListNode(K key, V value) {
            this(key, value, null, null);
        }

        public ListNode(K key, V value, ListNode<K, V> next, ListNode<K,V> prev) {
            this.key = key;
            this.next = next;
            this.value = value;
            this.prev = prev;
        }
    }


    public MoveToFrontDictionary() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public V remove(K key) {
        if (this.size == 0) {
            return null;
        }
        if(this.head.key.equals(key)){
            V val = this.head.value;
            this.head = this.head.next;
            this.size--;
            return val;
        }
        V val = remove(key, this.head);
        if(val != null){
            this.size--;
        }
        return val;


    }

    private V remove(K key, ListNode<K, V> curr){
        if(curr.next == null){
            return null;
        }
        if(curr.next.key.equals(key)){
            V val = curr.next.value;
            curr.next = curr.next.next;
            return val;
        }
        return remove(key, curr.next);
    }

    @Override
    public V put(K key, V value) {
        if (this.containsKey(key)) {
            V val = this.get(key);
            this.head.value = value;
            return val;
        } else {
            ListNode<K, V> curr = new ListNode<>(key, value);
            if (this.size == 0) {
                curr.next = null;
            } else {
                curr.next = this.head;
            }
            this.head = curr;
            this.size++;
            return null;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        return values().contains(value);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ICollection<K> keys() {
        ICollection<K> keys = new LinkedDeque<K>();
        keys = keys(this.head, keys);
        return keys;
    }

    private ICollection<K> keys(ListNode<K,V> curr, ICollection<K> keys){
        if(curr == null){
            return keys;
        }
        keys.add(curr.key);
        return keys(curr.next, keys);
    }

    @Override
    public ICollection<V> values() {
        ICollection<V> vals = new LinkedDeque<V>();
        vals = values(this.head, vals);
        return vals;
    }

    private ICollection<V> values(ListNode<K,V> curr, ICollection<V> vals){
        if(curr == null){
            return vals;
        }
        vals.add(curr.value);
        return values(curr.next, vals);
    }


    public V get(K key) {
        if(this.size ==0){
            return null;
        }
        ListNode<K, V> prev = this.head;
        ListNode<K, V> curr = this.head.next;
        if(prev.key.equals(key)){
            return prev.value;
        }
        for(int i = 0; i < this.size; i++){
            if(curr != null && curr.key.equals(key)){
                prev.next = curr.next;
                curr.next = this.head;
                this.head = curr;
                return curr.value;
            }
            prev = curr;
            if(curr != null){
                curr = curr.next;
            }
        }
        return null;
    }

    public class MoveToFrontIterator implements Iterator<K> {
        private ListNode<K, V> idx;

        public MoveToFrontIterator(){
            this.idx = MoveToFrontDictionary.this.head;
        }

        @Override
        public boolean hasNext() {
            return this.idx != null;
        }

        @Override
        public K next() {
            if (hasNext()){
                K key = this.idx.key;
                this.idx = this.idx.next;
                return key;
            }
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new MoveToFrontIterator();
    }
}



