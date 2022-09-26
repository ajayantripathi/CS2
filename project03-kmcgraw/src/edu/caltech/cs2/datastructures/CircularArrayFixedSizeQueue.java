package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IFixedSizeQueue;

import java.util.Iterator;

public class CircularArrayFixedSizeQueue<E> implements IFixedSizeQueue<E> {

    private E[] data;
    private int front;
    private int back;

    public CircularArrayFixedSizeQueue(int capacity){
        this.data = (E[])new Object[capacity];
        this.front = 0;
        this.back = -1;
    }

    @Override
    public boolean isFull() {
        if (this.back<0){
            return false;
        }
        return (this.back+1)%CircularArrayFixedSizeQueue.this.data.length == this.front;
    }

    @Override
    public int capacity() {
        return this.data.length;
    }

    @Override
    public boolean enqueue(E e) {
        if (this.isFull()){
            return false;
        }
        this.back = (this.back+1)%CircularArrayFixedSizeQueue.this.data.length;
        this.data[this.back] = e;
        return true;
    }

    @Override
    public E dequeue() {
        if (this.back<0){
            return null;
        }
        E out = this.data[this.front];
        if (this.front == this.back){
            this.front = 0;
            this.back = -1;
        } else {
            this.front = (this.front + 1) % CircularArrayFixedSizeQueue.this.data.length;
        }
        return out;
    }

    @Override
    public E peek() {
        if (this.back<0){
            return null;
        }
        return this.data[this.front];
    }

    @Override
    public int size() {
        if (this.back<0){
            return 0;
        }
        if (this.back < this.front){
            return this.back+this.data.length-this.front+1;
        }
        return this.back-this.front+1;
    }

    public class CircularIterator implements Iterator<E> {
        private int idx;

        public CircularIterator() {
            this.idx = CircularArrayFixedSizeQueue.this.front;
        }

        public boolean hasNext() {
            if (CircularArrayFixedSizeQueue.this.back<0){
                return false;
            }
            return this.idx != CircularArrayFixedSizeQueue.this.back;
        }

        public E next() {
            E out = CircularArrayFixedSizeQueue.this.data[this.idx];
            this.idx = (this.idx+1)%CircularArrayFixedSizeQueue.this.data.length;
            return out;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new CircularIterator();
    }


    @Override
    public String toString(){
        if (this.back<0) {
            return "[]";
        }
        String out = "[";
        for (int i = this.front; i != this.back; i++) {
            i = i%this.data.length;
            out += this.data[i] + ", ";
        }
        return out + this.data[this.back] + "]";
    }
}
