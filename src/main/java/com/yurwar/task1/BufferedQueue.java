package com.yurwar.task1;

import java.util.LinkedList;
import java.util.Queue;

public class BufferedQueue<T> {
    private int size;
    private Queue<T> queue;

    public BufferedQueue(int size) {
        this.size = size;
        this.queue = new LinkedList<>();
    }

    public void add(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() >= size) {
                wait();
            }
            queue.add(value);
            notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.isEmpty()) {
                wait();
            }
            T curValue = queue.poll();
            notifyAll();
            return curValue;
        }
    }
}
