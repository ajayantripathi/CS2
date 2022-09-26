package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;

public class AVLTreeDictionary<K extends Comparable<? super K>, V>
        extends BSTDictionary<K, V> {

    /**
     * A subclass of BSTNode representing a node in the AVLTree
     */
    private static class AVLNode<K, V> extends BSTNode<K, V> {

        public int height;

        /**
         * Constructor invokes the BSTNode constructor and initializes the height
         */
        public AVLNode(K key, V value, int height) {
            super(key, value);
            this.height = height;
        }

    }

    /**
     * Overrides the remove method in BST
     *
     * @param key
     * @return The value of the removed BSTNode if it exists, null otherwise
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Overrides the put method in BST to create AVLNode<K, V> instances
     *
     * @param key
     * @param value
     * @return the previous value corresponding to key in the AVL tree
     */
    @Override
    public V put(K key, V value) {
        V val = get(key);
        this.root = put(key, value, (AVLNode<K, V>) this.root);
        if (val == null){
            this.size++;
            this.root = rebalance(key, (AVLNode<K, V>) this.root);
        }
        return val;
    }

    private AVLNode<K, V> put(K key, V value, AVLNode<K, V> curr){
        if (curr == null){
            return new AVLNode<>(key, value, 0);
        }
        if (curr.key.compareTo(key)>0){
            curr.left = put(key, value, (AVLNode<K, V>) curr.left);
        } else if (curr.key.compareTo(key)<0) {
            curr.right = put(key, value, (AVLNode<K, V>) curr.right);
        } else {
            curr.value = value;
        }
        return curr;
//        if (curr == null){
//            return new AVLNode<>(key, value, 0);
//        }
//        if (curr.key.compareTo(key)>0){
//            if (curr.left == null){
//                curr.left = new AVLNode<>(key, value, curr.height+1);
//            } else {
//                curr.left = put(key, value, (AVLNode<K, V>) curr.left);
//            }
//        } else if (curr.key.compareTo(key)<0) {
//            if (curr.right == null){
//                curr.right = new AVLNode<>(key, value, curr.height+1);
//            } else {
//                curr.right = put(key, value, (AVLNode<K, V>) curr.right);
//            }
//        } else {
//            curr.value = value;
//        }
//        return curr;
    }

    private AVLNode<K, V> rebalance(K key, AVLNode<K, V> curr) {
        int c = curr.key.compareTo(key);
        if (c > 0){
            curr.left = rebalance(key, (AVLNode<K, V>) curr.left);
        } else if (c < 0) {
            curr.right = rebalance(key, (AVLNode<K, V>) curr.right);
        }
        if (c != 0) {
            int balance = maxHeight((AVLNode<K, V>) curr.left) - maxHeight((AVLNode<K, V>) curr.right);
            if (balance > 1) {
                int balance2 = maxHeight((AVLNode<K, V>) curr.left.left) - maxHeight((AVLNode<K, V>) curr.left.right);
                if (balance2 < 0) {
                    curr.left = leftRotate((AVLNode<K, V>) curr.left);
                }
                return rightRotate(curr);
            }
            if (balance < -1) {
                int balance2 = maxHeight((AVLNode<K, V>) curr.right.left) - maxHeight((AVLNode<K, V>) curr.right.right);
                if (balance2 > 0) {
                    curr.right = rightRotate((AVLNode<K, V>) curr.right);
                }
                return leftRotate(curr);
            }
        }
        return curr;
    }

    private AVLNode<K, V> rightRotate(AVLNode<K, V> curr){
//        AVLNode<K, V> y = new AVLNode<>(curr.left.key, curr.left.value, curr.height);
//        AVLNode<K, V> z = new AVLNode<>(curr.key, curr.value, curr.height+1);
//        z.right = curr.right;
//        z.left = curr.left.right;
//        y.right = z;
//        y.left = curr.left.left;
//        y = resetHeights(y);
        AVLNode<K, V> y = (AVLNode<K, V>) curr.left;
        curr.left = y.right;
        y.right = curr;
        return y;
    }

    private AVLNode<K, V> leftRotate(AVLNode<K, V> curr){
//        AVLNode<K, V> y = new AVLNode<>(curr.right.key, curr.right.value, curr.height);
//        AVLNode<K, V> z = new AVLNode<>(curr.key, curr.value, curr.height+1);
//        z.left = curr.left;
//        z.right = curr.right.left;
//        y.right = curr.right.right;
//        y.left = z;
//        y = resetHeights(y);
        AVLNode<K, V> y = (AVLNode<K, V>) curr.right;
        curr.right = y.left;
        y.left = curr;
        return y;
    }

//    private AVLNode<K, V> resetHeights(AVLNode<K,V> y) {
//        if (y.left != null){
//            ((AVLNode<K, V>) y.left).height = y.height+1;
//            y.left = resetHeights((AVLNode<K, V>) y.left);
//        }
//        if (y.right != null){
//            ((AVLNode<K, V>) y.right).height = y.height+1;
//            y.right = resetHeights((AVLNode<K, V>) y.right);
//        }
//        return y;
//    }

    private int maxHeight(AVLNode<K, V> curr){
        if (curr == null) {
            return 0;
        }
        if (curr.isLeaf()){
            return 1;
        }
        return Math.max(maxHeight((AVLNode<K, V>) curr.left)+1, maxHeight((AVLNode<K, V>) curr.right)+1);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
