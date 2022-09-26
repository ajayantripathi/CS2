package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.ITrieMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

public class TrieMap<A, K extends Iterable<A>, V> implements ITrieMap<A, K, V> {
    private TrieNode<A, V> root;
    private Function<IDeque<A>, K> collector;
    private int size;

    public TrieMap(Function<IDeque<A>, K> collector) {
        this.root = new TrieNode<>();
        this.collector = collector;
        this.size = 0;
    }
    

    public boolean isPrefix(K key) {
        TrieNode<A, V> curr = this.root;
        for (A a : key) {
            if (curr.pointers.containsKey(a)) {
                curr = curr.pointers.get(a);
            }
            else {
                return false;
            }
        }
        return true;
    }

    public IDeque<V> getCompletions(K prefix) {
        TrieNode<A, V> curr = this.root;
        IDeque<V> values = new LinkedDeque<>();
        if (!isPrefix(prefix)) {
            return values;
        }
        for (A elem : prefix) {
            curr = curr.pointers.get(elem);
        }
        values(curr, values);
        return values;
    }

    public void clear() {
        this.root = new TrieNode<>();
        this.size = 0;
    }

    public V get(K key) {
        TrieNode<A, V> curr = this.root;
        for (A part : key){
            if (!curr.pointers.containsKey(part)){
                return null;
            }
            curr = curr.pointers.get(part);
        }
        return curr.value;
    }


    public V remove(K key) {
        if (!keySet().contains(key)) {
            return null;
        }
        V val = get(key);
        TrieNode<A, V> curr = this.root;
        remove(key.iterator(), curr);
        size --;
        return val;
    }

    private boolean remove(Iterator<A> iter, TrieNode<A, V> curr) {
        if (iter.hasNext()) {
            A keyPart = iter.next();
            if (remove(iter, curr.pointers.get(keyPart))) {
                curr.pointers.remove(keyPart);
                return curr.value == null && curr.pointers.size() == 0;
            }
            return false;
        }
        else {
            if (curr.pointers.size() != 0) {
                curr.value = null;
                return false;
            }
            else {
                return true;
            }
        }
    }

    public V put(K key, V value) {
        TrieNode<A, V> curr = this.root;
        for (A part : key){
            if (!curr.pointers.containsKey(part)){
                curr.pointers.put(part, new TrieNode<>());
            }
            curr = curr.pointers.get(part);
        }
        V val = curr.value;
        if (curr.value == null || !curr.value.equals(value)) {
            if (curr.value == null) {
                this.size++;
            }
            curr.value = value;
        }
        return val;
    }

    public boolean containsKey(K key) {
        TrieNode<A, V> curr = this.root;
        for (A part : key){
            if (!curr.pointers.containsKey(part)){
                return false;
            }
            curr = curr.pointers.get(part);
        }
        return curr.value != null;
    }

    public boolean containsValue(V value) {
        TrieNode<A, V> curr = this.root;
        for (V elem : values()) {
            if (elem == value) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public ICollection<K> keys() {
        ICollection<K> keys = new LinkedDeque<>();
        IDeque<A> acc = new LinkedDeque<>();
        keys = keySet(this.root, keys, acc);
        return keys;
    }

    private ICollection<K> keySet(TrieNode<A, V> curr, ICollection<K> keys, IDeque<A> acc){
        if (curr == null){
            return keys;
        }
        for (A key : curr.pointers.keySet()){
            acc.addBack(key);
            keys = keySet(curr.pointers.get(key), keys, acc);
            acc.removeBack();
        }
        K val = this.collector.apply(acc);
        if (curr.value != null){
            keys.add(val);
        }
        return keys;
    }

    public ICollection<V> values() {
        ICollection<V> values = new LinkedDeque<>();
        values = values(this.root, values);
        return values;
    }

    private ICollection<V> values (TrieNode<A, V> curr, ICollection<V> values) {
        if (curr == null) {
            return values;
        }
        if (curr.value != null) {
            values.add(curr.value);
        }
        for (A key : curr.pointers.keySet()) {
            values = values(curr.pointers.get(key), values);
        }
        return values;
    }

    public Iterator<K> iterator() {
        return keySet().iterator();
    }
    
    private static class TrieNode<A, V> {
        public final Map<A, TrieNode<A, V>> pointers;
        public V value;

        public TrieNode() {
            this(null);
        }

        public TrieNode(V value) {
            this.pointers = new HashMap<>();
            this.value = value;
        }

        public String toString() {
            StringBuilder b = new StringBuilder();
            if (this.value != null) {
                b.append("[" + this.value + "]-> {\n");
                this.toString(b, 1);
                b.append("}");
            }
            else {
                this.toString(b, 0);
            }
            return b.toString();
        }

        private String spaces(int i) {
            StringBuilder sp = new StringBuilder();
            for (int x = 0; x < i; x++) {
                sp.append(" ");
            }
            return sp.toString();
        }

        protected boolean toString(StringBuilder s, int indent) {
            boolean isSmall = this.pointers.entrySet().size() == 0;

            for (Map.Entry<A, TrieNode<A, V>> entry : this.pointers.entrySet()) {
                A idx = entry.getKey();
                TrieNode<A, V> node = entry.getValue();

                if (node == null) {
                    continue;
                }

                V value = node.value;
                s.append(spaces(indent) + idx + (value != null ? "[" + value + "]" : ""));
                s.append("-> {\n");
                boolean bc = node.toString(s, indent + 2);
                if (!bc) {
                    s.append(spaces(indent) + "},\n");
                }
                else if (s.charAt(s.length() - 5) == '-') {
                    s.delete(s.length() - 5, s.length());
                    s.append(",\n");
                }
            }
            if (!isSmall) {
                s.deleteCharAt(s.length() - 2);
            }
            return isSmall;
        }
    }
}
