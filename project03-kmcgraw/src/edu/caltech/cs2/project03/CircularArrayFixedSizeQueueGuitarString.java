package edu.caltech.cs2.project03;

import edu.caltech.cs2.datastructures.CircularArrayFixedSizeQueue;
import edu.caltech.cs2.interfaces.IFixedSizeQueue;
import edu.caltech.cs2.interfaces.IQueue;

import java.util.Random;


public class CircularArrayFixedSizeQueueGuitarString {

    private IFixedSizeQueue<Double> guitar;
    private static final double rate = 44100;
    private static final double decay = 0.996;
    private static Random r = new Random();

    public CircularArrayFixedSizeQueueGuitarString(double frequency) {
        this.guitar = new CircularArrayFixedSizeQueue<>((int) Math.ceil(rate/frequency));
        while (!this.guitar.isFull()){
            this.guitar.enqueue(0.0);
        }
    }

    public int length() {
        return this.guitar.size();
    }

    public void pluck() {
        for (int i = 0; i < this.length(); i++){
            this.guitar.dequeue();
            this.guitar.enqueue(r.nextDouble()-0.5);
        }
    }

    public void tic() {
        double one = this.guitar.dequeue();
        double two = this.guitar.peek();
        this.guitar.enqueue(decay*(one+two)/2);
    }

    public double sample() {
        return this.guitar.peek();
    }
}
