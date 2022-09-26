package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.ICollection;
import edu.caltech.cs2.interfaces.IDeque;
import edu.caltech.cs2.interfaces.IQueue;
import edu.caltech.cs2.interfaces.IStack;

import java.util.Iterator;

public class LinkedDeque<E> implements IDeque<E>, IQueue<E>, IStack<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    private static class Node<E> {
        public final E data;
        public Node<E> prev;
        public Node<E> next;

        public Node(E data) {
            this(data, null);
        }

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    @Override
    public void addFront(E e) {
        if (this.head == null) {
            this.head = new Node<>(e);
            this.tail = this.head;
        }
        else {
            Node<E> n = new Node<>(e, this.head);
            this.head.prev = n;
            this.head = n;
        }
        this.size++;
    }

    @Override
    public void addBack(E e) {
        if (this.head == null) {
            this.head = new Node<>(e);
            this.tail = this.head;
        }
        else {
            this.tail.next = new Node<>(e);
            this.tail.next.prev = this.tail;
            this.tail = this.tail.next;
        }
        this.size++;
    }

    @Override
    public E removeFront() {
        if (this.head == null) {
            return null;
        }
        E out = this.head.data;
        this.head = this.head.next;
        if (this.head == null){
            this.tail = null;
        } else {
            this.head.prev = null;
        }
        this.size--;
        return out;
    }

    @Override
    public E removeBack() {
        if (this.tail == null) {
            return null;
        }
        E out = this.tail.data;
        this.tail = this.tail.prev;
        if (this.tail == null) {
            this.head = null;
        } else {
            this.tail.next = null;
        }
        this.size--;
        return out;
    }

    @Override
    public boolean enqueue(E e) {
        addFront(e);
        return true;
    }

    @Override
    public E dequeue() {
        return removeBack();
    }

    @Override
    public boolean push(E e) {
        addBack(e);
        return true;
    }

    @Override
    public E pop() {
        return removeBack();
    }

    @Override
    public E peek() {
        if (this.tail == null){
            return null;
        }
        return this.tail.data;
    }

    @Override
    public E peekFront() {
        if (this.head == null){
            return null;
        }
        return this.head.data;
    }

    @Override
    public E peekBack() {
        return peek();
    }

    public class LinkedDequeIterator implements Iterator<E> {
        private Node<E> idx;

        public LinkedDequeIterator() {
            this.idx = LinkedDeque.this.head;
        }

        public boolean hasNext() {
            return this.idx != null;
        }

        public E next() {
            E out = this.idx.data;
            this.idx = this.idx.next;
            return out;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedDequeIterator();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        if (this.head == null) {
            return "[]";
        }
        Node<E> current = this.head;
        String result = "";
        while (current.next != null) {
            result += current.data + ", ";
            current = current.next;
        }
        return "[" + result + current.data + "]";
    }
}
