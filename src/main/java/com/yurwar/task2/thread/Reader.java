package com.yurwar.task2.thread;

import com.yurwar.task2.Counter;
import com.yurwar.task2.ValueContainer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Reader extends Thread {
    private final ReentrantLock readerLock;
    private final ReentrantLock writerLock;
    private final Condition readCondition;
    private final Condition writeCondition;
    private final Counter readerCounter;
    private final ValueContainer<String> messageContainer;

    public Reader(ReentrantLock readerLock, ReentrantLock writerLock, Condition readCondition, Condition writeCondition, Counter readerCounter,
                  ValueContainer<String> messageContainer) {
        this.readerLock = readerLock;
        this.writerLock = writerLock;
        this.readCondition = readCondition;
        this.writeCondition = writeCondition;
        this.readerCounter = readerCounter;
        this.messageContainer = messageContainer;
    }

    public Reader(String name, ReentrantLock readerLock, ReentrantLock writerLock, Condition readCondition, Condition writeCondition, Counter readerCounter,
                  ValueContainer<String> messageContainer) {
        super(name);
        this.readerLock = readerLock;
        this.writerLock = writerLock;
        this.readCondition = readCondition;
        this.writeCondition = writeCondition;
        this.readerCounter = readerCounter;
        this.messageContainer = messageContainer;
    }

    public Reader(ThreadGroup group, String name, ReentrantLock readerLock, ReentrantLock writerLock,
                  Condition readCondition, Condition writeCondition, Counter readerCounter, ValueContainer<String> messageContainer) {
        super(group, name);
        this.readerLock = readerLock;
        this.writerLock = writerLock;
        this.readCondition = readCondition;
        this.writeCondition = writeCondition;
        this.readerCounter = readerCounter;
        this.messageContainer = messageContainer;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            readerLock.lock();
            try {
                if (readerCounter.currentValue() == 0) {
                    writerLock.lock();
                    writeCondition.await();
                }
                readerCounter.increment();
            } catch (InterruptedException e) {
                e.printStackTrace();
                interrupt();
            } finally {
                readerLock.unlock();
            }

            readMessage(messageContainer.getMessage());

            readerLock.lock();
            try {
                readerCounter.decrement();
                if (readerCounter.currentValue() == 0) {
                    writeCondition.signalAll();
                    writerLock.unlock();
                }
            } finally {
                readerLock.unlock();
            }
        }
    }

    private void readMessage(String message) {
        System.out.printf("Read message '%s' in %s%n", message, getName());
    }
}
