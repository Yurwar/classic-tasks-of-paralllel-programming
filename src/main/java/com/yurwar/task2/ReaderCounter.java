package com.yurwar.task2;

public class ReaderCounter implements Counter {
    private int currentReadersCount;

    public ReaderCounter(int currentReadersCount) {
        this.currentReadersCount = currentReadersCount;
    }

    public ReaderCounter() {
    }

    @Override
    public int increment() {
        return ++currentReadersCount;
    }

    @Override
    public int decrement() {
        return --currentReadersCount;
    }

    @Override
    public int currentValue() {
        return currentReadersCount;
    }
}
