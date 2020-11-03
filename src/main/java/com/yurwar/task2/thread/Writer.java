package com.yurwar.task2.thread;

import com.yurwar.task2.ValueContainer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Writer extends Thread {
    private final ValueContainer<String> valueContainer;
    private final ReentrantLock writerLock;
    private final ReentrantLock syncLock;
    private final Condition writeCondition;
    private final int messagesToWrite;

    public Writer(ValueContainer<String> valueContainer, ReentrantLock writerLock,
                  ReentrantLock syncLock, Condition writeCondition, int messagesToWrite) {
        this.valueContainer = valueContainer;
        this.writerLock = writerLock;
        this.syncLock = syncLock;
        this.writeCondition = writeCondition;
        this.messagesToWrite = messagesToWrite;
    }

    public Writer(String name, ValueContainer<String> valueContainer, ReentrantLock writerLock,
                  ReentrantLock syncLock, Condition writeCondition, int messagesToWrite) {
        super(name);
        this.valueContainer = valueContainer;
        this.writerLock = writerLock;
        this.syncLock = syncLock;
        this.writeCondition = writeCondition;
        this.messagesToWrite = messagesToWrite;
    }

    public Writer(ThreadGroup group, String name, ValueContainer<String> valueContainer,
                  ReentrantLock writerLock, ReentrantLock syncLock, Condition writeCondition, int messagesToWrite) {
        super(group, name);
        this.valueContainer = valueContainer;
        this.writerLock = writerLock;
        this.syncLock = syncLock;
        this.writeCondition = writeCondition;
        this.messagesToWrite = messagesToWrite;
    }

    @Override
    public void run() {
        for (int currMessageNum = 1; currMessageNum <= messagesToWrite && !isInterrupted(); currMessageNum++) {
            writerLock.lock();
            try {
                writeMessage(currMessageNum);
                writeCondition.signalAll();
                writeCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                interrupt();
            } finally {
                writerLock.unlock();
            }
        }
    }

    private void writeMessage(int currMessageNum) {
        valueContainer.setMessage(createMessage(currMessageNum));
    }

    private String createMessage(int currMessageNum) {
        return String.format("Message #%d from %s", currMessageNum, getName());
    }
}
