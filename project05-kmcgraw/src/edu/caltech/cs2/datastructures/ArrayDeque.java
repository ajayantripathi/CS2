package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;

import java.util.Iterator;

public class ArrayDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {

    private E[] data;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;
    private static final int GROW_FACTOR = 2;

    public ArrayDeque(){
        this(DEFAULT_CAPACITY);
    }

    public ArrayDeque(int initialCapacity){
        this.data = (E[])new Object[initialCapacity];
    }

    @Override
    public void addFront(E e) {
        this.checkCapacity();
        if (this.size >= 0) System.arraycopy(this.data, 0, this.data, 1, this.size);
        this.data[0] = e;
        this.size++;
    }

    @Override
    public void addBack(E e) {
        this.checkCapacity();
        this.data[size] = e;
        this.size++;
    }

    @Override
    public E removeFront() {
        if (size > 0) {
            E remove = this.data[0];
            this.size--;
            System.arraycopy(this.data, 1, this.data, 0, this.size);
            return remove;
        }
        return null;
    }

    @Override
    public E removeBack() {
        if (size > 0) {
            this.size--;
            return this.data[size];
        }
        return null;
    }

    @Override
    public boolean enqueue(E e) {
        this.checkCapacity();
        if (this.size >= 0) System.arraycopy(this.data, 0, this.data, 1, this.size);
        this.data[0] = e;
        this.size++;
        return true;
    }

    @Override
    public E dequeue() {
        if (size > 0) {
            this.size--;
            return this.data[size];
        }
        return null;
    }

    @Override
    public boolean push(E e) {
        this.checkCapacity();
        this.data[size] = e;
        this.size++;
        return true;
    }

    @Override
    public E pop() {
        if (size > 0) {
            this.size--;
            return this.data[size];
        }
        return null;
    }

    @Override
    public E peek() {
        if (size > 0){
            return this.data[size - 1];
        }
        return null;
    }

    @Override
    public E peekFront() {
        if (size > 0){
            return this.data[0];
        }
        return null;
    }

    @Override
    public E peekBack() {
        if (size > 0){
            return this.data[size - 1];
        }
        return null;
    }

    public class ArrayDequeIterator implements Iterator<E> {
        private int idx;

        public ArrayDequeIterator() {
            this.idx = 0;
        }

        public boolean hasNext() {
            return this.idx < ArrayDeque.this.size;
        }

        public E next() {
            this.idx++;
            return ArrayDeque.this.data[this.idx - 1];
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String toString(){
        if (this.size == 0) {
            return "[]";
        }

        String out = "[";
        for (int i = 0; i < this.size; i++) {
            out += this.data[i] + ", ";
        }

        out = out.substring(0, out.length() - 2);
        return out + "]";
    }

    private void checkCapacity(){
        if (this.size == this.data.length){
            E[] data2 = (E[])new Object[this.data.length * GROW_FACTOR];
            System.arraycopy(this.data, 0, data2, 0, this.size);
            this.data = data2;
        }
    }
}

