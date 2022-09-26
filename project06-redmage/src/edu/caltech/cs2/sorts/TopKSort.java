package edu.caltech.cs2.sorts;

import edu.caltech.cs2.datastructures.MinFourHeap;
import edu.caltech.cs2.interfaces.IPriorityQueue;

public class TopKSort {
    /**
     * Sorts the largest K elements in the array in descending order.
     * @param array - the array to be sorted; will be manipulated.
     * @param K - the number of values to sort
     * @param <E> - the type of values in the array
     */
    public static <E> void sort(IPriorityQueue.PQElement<E>[] array, int K) {
        if (K < 0) {
            throw new IllegalArgumentException("K cannot be negative!");
        }
        int size = 0;
        MinFourHeap<E> heap = new MinFourHeap<>();
        for (IPriorityQueue.PQElement<E> epqElement : array) {
            heap.enqueue(epqElement);
            size++;
            if (size > K) {
                heap.dequeue();
                size--;
            }
        }
        for (int i = array.length - 1; i >= 0; i--){
            if (i < K) {
                array[i] = heap.dequeue();
            } else {
                array[i] = null;
            }
        }
    }
}
