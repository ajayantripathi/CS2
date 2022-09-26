package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.*;
import edu.caltech.cs2.textgenerator.NGram;

import java.util.Iterator;

public class BSTDictionary<K extends Comparable<? super K>, V>
        implements IDictionary<K, V> {

    protected BSTNode<K, V> root;
    protected int size;

    /**
     * Class representing an individual node in the Binary Search Tree
     */
    protected static class BSTNode<K, V> {
        public final K key;
        public V value;

        public BSTNode<K, V> left;
        public BSTNode<K, V> right;

        /**
         * Constructor initializes this node's key, value, and children
         */
        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }

        public BSTNode(BSTNode<K, V> o) {
            this.key = o.key;
            this.value = o.value;
            this.left = o.left;
            this.right = o.right;
        }

        public boolean isLeaf() {
            return this.left == null && this.right == null;
        }

        public boolean hasBothChildren() {
            return this.left != null && this.right != null;
        }
    }

    /**
     * Initializes an empty Binary Search Tree
     */
    public BSTDictionary() {
        this.root = null;
        this.size = 0;
    }


    @Override
    public V get(K key) {
        return get(key, this.root);
    }

    private V get(K key, BSTNode<K, V> curr){
        if (curr == null){
            return null;
        }
        if (curr.key.equals(key)){
            return curr.value;
        }
        if (curr.key.compareTo(key)>0){
            return get(key, curr.left);
        }
        return get(key, curr.right);
    }

    @Override
    public V remove(K key) {
        V val = get(key);
        this.root = remove(key, this.root);
        if (val != null){
            this.size--;
        }
        return val;
    }

    private BSTNode<K, V> remove(K key, BSTNode<K, V> curr){
        if (curr == null){
            return null;
        }
        if (curr.key == key){
            if (curr.isLeaf()){
                return null;
            }
            if (curr.hasBothChildren()) {
                K newKey = findMin(curr.right);
                V newValue = get(newKey);
                BSTNode<K, V> newCurr = remove(newKey, curr);
                BSTNode<K, V> n = new BSTNode<>(newKey, newValue);
                n.left = newCurr.left;
                n.right = newCurr.right;
                return n;
            }
            if (curr.right == null){
                return curr.left;
            }
            return curr.right;
        }
        if (curr.key.compareTo(key)>0) {
            curr.left = remove(key, curr.left);
            return curr;
        }
        curr.right = remove(key, curr.right);
        return curr;
    }

    private K findMin(BSTNode<K, V> curr){
        if (curr.isLeaf()){
            return curr.key;
        }
        if (curr.left == null){
            return curr.key;
        }
        return findMin(curr.left);
    }

    @Override
    public V put(K key, V value) {
        V val = get(key, this.root);
        this.root = put(key, value, this.root);
        if (val == null){
            this.size++;
        }
        return val;
    }

    private BSTNode<K, V> put(K key, V value, BSTNode<K, V> curr){
        if (curr == null){
            return new BSTNode<>(key, value);
        }
        if (curr.key.compareTo(key)>0){
            curr.left = put(key, value, curr.left);
        } else if (curr.key.compareTo(key)<0) {
            curr.right = put(key, value, curr.right);
        } else {
            curr.value = value;
        }
        return curr;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key)!=null;
    }

    @Override
    public boolean containsValue(V value) {
        return containsValue(value, this.root);
    }

    private boolean containsValue(V value, BSTNode<K, V> curr){
        if (curr == null){
            return false;
        }
        if (curr.value.equals(value)){
            return true;
        }
        return containsValue(value, curr.left) || containsValue(value, curr.right);
    }

    /**
     * @return number of nodes in the BST
     */
    @Override
    public int size() {
        return this.size;
    }

    @Override
    public ICollection<K> keySet() {
        ICollection<K> keys = new LinkedDeque<>();
        return keySet(keys, this.root);
    }

    private ICollection<K> keySet(ICollection<K> keys, BSTNode<K, V> curr){
        if (curr == null){
            return keys;
        }
        keys.add(curr.key);
        keys = keySet(keys, curr.left);
        keys = keySet(keys, curr.right);
        return keys;
    }

    @Override
    public ICollection<V> values() {
        ICollection<V> vals = new LinkedDeque<>();
        return values(vals, this.root);
    }

    private ICollection<V> values(ICollection<V> vals, BSTNode<K, V> curr){
        if (curr == null){
            return vals;
        }
        vals.add(curr.value);
        vals = values(vals, curr.left);
        vals = values(vals, curr.right);
        return vals;
    }

    /**
     * Implementation of an iterator over the BST
     */

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    @Override
    public String toString() {
        if (this.root == null) {
            return "{}";
        }

        StringBuilder contents = new StringBuilder();

        IQueue<BSTNode<K, V>> nodes = new ArrayDeque<>();
        BSTNode<K, V> current = this.root;
        while (current != null) {
            contents.append(current.key + ": " + current.value + ", ");

            if (current.left != null) {
                nodes.enqueue(current.left);
            }
            if (current.right != null) {
                nodes.enqueue(current.right);
            }

            current = nodes.dequeue();
        }

        return "{" + contents.toString().substring(0, contents.length() - 2) + "}";
    }
}
