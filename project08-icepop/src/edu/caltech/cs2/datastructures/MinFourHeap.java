package edu.caltech.cs2.datastructures;

import edu.caltech.cs2.interfaces.IDictionary;
import edu.caltech.cs2.interfaces.IPriorityQueue;

import java.util.Iterator;

public class MinFourHeap<E> implements IPriorityQueue<E> {

    private static final int DEFAULT_CAPACITY = 5;

    private int size;
    private PQElement<E>[] data;
    private IDictionary<E, Integer> keyToIndexMap;
    private static final int growthFactor = 2;

    /**
     * Creates a new empty heap with DEFAULT_CAPACITY.
     */
    public MinFourHeap() {
        this.size = 0;
        this.data = new PQElement[DEFAULT_CAPACITY];
        this.keyToIndexMap = new ChainingHashDictionary<>(MoveToFrontDictionary::new);
    }

    private void percolateDown(int idx) {
        if (this.size == 0) {
            return;
        }
        int currentIdx = idx;
        int childIdx = currentIdx * 4 + 1;
        int lowestChild = -1;
        for (int i = childIdx; i < childIdx + 4; i++) {
            if (i >= this.size) {
                break;
            }
            if (lowestChild == -1 || this.data[lowestChild].priority > this.data[i].priority) {
                lowestChild = i;
            }
        }
        if (lowestChild == -1){
            return;
        }
        while (this.data[currentIdx].priority > this.data[lowestChild].priority) {
            PQElement<E> temp = this.data[currentIdx];
            this.data[currentIdx] = this.data[lowestChild];
            this.data[lowestChild] = temp;
            this.keyToIndexMap.put(this.data[currentIdx].data, currentIdx);
            this.keyToIndexMap.put(this.data[lowestChild].data, lowestChild);
            currentIdx = lowestChild;
            if (currentIdx * 4 + 1 >= this.size) {
                break;
            }
            childIdx = currentIdx * 4 + 1;
            for (int i = childIdx; i < childIdx + 4; i++) {
                if (i >= this.size) {
                    continue;
                }
                if (this.data[lowestChild].priority > this.data[i].priority) {
                    lowestChild = i;
                }
            }
        }
    }

    private void percolateUp(int idx) {
        if (this.size == 0) {
            return;
        }
        int currentIdx = idx;
        int parentIdx = (currentIdx - 1) / 4;
        while (this.data[parentIdx].priority > this.data[currentIdx].priority) {
            PQElement<E> temp = this.data[parentIdx];
            this.data[parentIdx] = this.data[currentIdx];
            this.data[currentIdx] = temp;
            this.keyToIndexMap.put(this.data[parentIdx].data, parentIdx);
            this.keyToIndexMap.put(this.data[currentIdx].data, currentIdx);
            currentIdx = parentIdx;
            if (currentIdx == 0) {
                break;
            }
            parentIdx = (currentIdx - 1 )/ 4;
        }
    }

    public void increaseKey(PQElement<E> key) {
        if (!this.keyToIndexMap.containsKey(key.data)) {
            throw new IllegalArgumentException("Key is not present in keyToIndexMap");
        }
        int idx = this.keyToIndexMap.get(key.data);
        this.data[idx] = key;
        this.percolateDown(idx);
    }

    public void decreaseKey(PQElement<E> key) {
        if (!this.keyToIndexMap.containsKey(key.data)) {
            throw new IllegalArgumentException("Key is not present in keyToIndexMap");
        }
        int idx = this.keyToIndexMap.get(key.data);
        this.data[idx] = key;
        this.percolateUp(idx);
    }

    public boolean enqueue(PQElement<E> epqElement) {
        if (this.keyToIndexMap.containsKey(epqElement.data)){
            throw new IllegalArgumentException("Key already present in keyToIndexMap");
        }
        if (this.data.length == this.size){
            this.increaseSize();
        }
        this.data[this.size] = epqElement;
        this.keyToIndexMap.put(epqElement.data, this.size);
        this.percolateUp(this.size);
        this.size++;
        return true;
    }

    private void increaseSize() {
        PQElement<E>[] newData = new PQElement[this.size * growthFactor];
        for (int i = 0; i < this.size; i++) {
            newData[i] = this.data[i];
        }
        this.data = newData;
    }

    public PQElement<E> dequeue() {
        if (size == 0){
            return null;
        }
        PQElement<E> toReturn = this.data[0];
        this.keyToIndexMap.remove(this.data[0].data);
        this.data[0] = this.data[this.size - 1];
        this.data[this.size - 1] = null;
        this.size--;
        this.percolateDown(0);
        return toReturn;
    }

    public PQElement<E> peek() {
        return null;
    }

    public int size() {
        return this.size;
    }

    public Iterator<PQElement<E>> iterator() {
        return null;
    }
}