package com.yurwar.task2;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class ValueContainer<T> {
    T message;
    private final ReentrantLock storageLock = new ReentrantLock();
    private final Condition storageCondition = storageLock.newCondition();
    private final AtomicInteger readerCounter = new AtomicInteger();

    public T getMessage() {
        storageLock.lock();
        readerCounter.incrementAndGet();
        storageLock.unlock();

        T currentMessage = message;

        storageLock.lock();
        if (readerCounter.decrementAndGet() == 0) {
            storageCondition.signalAll();
        }
        storageLock.unlock();

        return currentMessage;
    }

    public void setMessage(T message) {
        storageLock.lock();
        while (readerCounter.get() != 0) {
            try {
                storageCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        this.message = message;

        storageCondition.signalAll();
        storageLock.unlock();
    }

    public boolean isEmpty() {
        return Objects.isNull(message);
    }
}
