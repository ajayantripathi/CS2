package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;

import java.util.Iterator;

public class ArrayDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {
    private int size;
    private E[] arrayDeque;
    private static final int growFactor = 2;
    private static final int capacity = 10;

    public ArrayDeque() {
        this(capacity);
    }

    public ArrayDeque(int initialCapacity) {
        this.arrayDeque = (E[]) new Object[initialCapacity];
        this.size = 0;
    }

    private void checkIndex(int idx){
        if (idx < 0 || idx > this.arrayDeque.length) {
            throw new IllegalArgumentException("Invalid index");
        }
    }

    public void addBack(E e) {
        int currentCapacity = this.arrayDeque.length;
        if (this.size + 1 >= currentCapacity) {
            currentCapacity = currentCapacity * 2;
        }
        E[] newDeque = (E[]) new Object[currentCapacity + 1];
        newDeque[0] = e;
        System.arraycopy(arrayDeque, 0, newDeque, 1, this.arrayDeque.length);
        this.arrayDeque = newDeque;
        this.size ++;
    }

    public void addFront(E e) {
        if (this.size >= this.arrayDeque.length) {
            E[] newDeque = (E[]) new Object[this.arrayDeque.length * 2];
            System.arraycopy(arrayDeque, 0, newDeque, 0, this.arrayDeque.length);
            this.arrayDeque = newDeque;
        }
        this.arrayDeque[this.size] = e;
        this.size ++;
    }

    public E removeBack() {
        if (this.size == 0) {
            return null;
        }
        E front = this.arrayDeque[0];
        System.arraycopy(this.arrayDeque, 1, this.arrayDeque, 0, this.arrayDeque.length - 1);
        this.size --;
        return front;
    }

    public E removeFront() {
        if (this.size == 0) {
            return null;
        }
        E back = this.arrayDeque[this.size - 1];
        this.size --;
        return back;
    }

    public boolean enqueue(E e) {
        this.addBack(e);
        return true;
    }

    public E dequeue() {
        return this.removeFront();
    }

    public boolean push(E e) {
        this.addFront(e);
        return true;
    }

    public E pop() {
        return this.removeFront();
    }

    public E peek() {
        if (this.size == 0) {
            return null;
        }
        return this.arrayDeque[this.size - 1];
    }

    public E peekFront() {
        if (this.size == 0) {
            return null;
        }
        return this.arrayDeque[this.size - 1];
    }

    public E peekBack() {
        if (size == 0) {
            return null;
        }
        return this.arrayDeque[0];
    }

    public class ArrayDequeIterator implements Iterator<E> {
        private int idx;

        public ArrayDequeIterator() {
            this.idx = ArrayDeque.this.size - 1;
        }

        public boolean hasNext() {
            return this.idx >= 0;
        }

        public E next() {
            E e = ArrayDeque.this.arrayDeque[this.idx];
            this.idx --;
            return e;
        }
    }

    public Iterator<E> iterator() {
        return new ArrayDequeIterator();
    }

    public int size() {
        return this.size;
    }

    public String toString() {
        if (this.size == 0) {
            return "[]";
        }
        String result = "";
        for (int i = this.size - 1; i >= 0; i--) {
            result += this.arrayDeque[i] + ", ";
        }
        result = result.substring(0, result.length() - 2);
        return "[" + result + "]";
    }
}

