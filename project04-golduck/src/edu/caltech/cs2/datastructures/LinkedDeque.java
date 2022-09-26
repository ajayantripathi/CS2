package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;

import java.util.Iterator;

public class LinkedDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {
    private ListNode<E> head;
    private ListNode<E> tail;
    private int size;

    private static class ListNode<E> {
        public E data;
        public ListNode<E> next;
        public ListNode<E> previous;
        
        public ListNode(E data) {
            this(data, null, null);
        }

        public ListNode(E data, ListNode<E> next, ListNode<E> previous) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }
    }

    public LinkedDeque() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addFront(E e) {
        if (this.size == 0) {
            this.head = new ListNode<>(e, null, null);
            this.tail = this.head;
        }
        else {
            ListNode<E> newNode = new ListNode<>(e, this.head, null);
            this.head.previous = newNode;
            this.head = newNode;
        }
        this.size ++;
    }

    public void addBack(E e) {
        if (this.size == 0) {
            this.head = new ListNode<>(e);
            this.tail = this.head;
        }
        else {
            ListNode<E> newNode = new ListNode<>(e, null, this.tail);
            this.tail.next = newNode;
            this.tail = newNode;
        }
        this.size ++;
    }

    public E removeFront() {
        if (this.size == 0) {
            return null;
        }
        E toRemove = this.head.data;
        if (this.size == 1) {
            this.tail = null;
            this.head = null;
        }
        else {
            this.head = this.head.next;
            this.head.previous = null;
        }
        this.size --;
        return toRemove;
    }

    public E removeBack() {
        if (this.size == 0) {
            return null;
        }
        E toRemove = this.tail.data;
        if (this.size == 1) {
            this.tail = null;
            this.head = null;
        }
        else {
            this.tail = this.tail.previous;
            this.tail.next = null;
        }
        this.size --;
        return toRemove;
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
        return this.head.data;
    }

    public E peekFront() {
        if (this.size == 0) {
            return null;
        }
        return this.head.data;
    }

    public E peekBack() {
        if (this.size == 0) {
            return null;
        }
        return this.tail.data;
    }

    public class LinkedDequeIterator implements Iterator<E> {
        private int idx;
        private ListNode<E> node = LinkedDeque.this.head;

        public boolean hasNext() {
            return idx < LinkedDeque.this.size;
        }

        public E next() {
            if (this.hasNext()) {
                E e = this.node.data;
                this.node = this.node.next;
                this.idx++;
                return e;
            }
            return null;
        }
    }


    public Iterator<E> iterator() {
        return new LinkedDequeIterator();
    }


    public int size() {
        return this.size;
    }

    public String toString() {
        if (this.size == 0) {
            return "[]";
        }
        ListNode<E> curr = this.head;
        String result = "";
        while(curr != null && curr.next != null) {
            result += curr.data + ", ";
            curr = curr.next;
        }
        return "[" + result + curr.data + "]";
    }
}
