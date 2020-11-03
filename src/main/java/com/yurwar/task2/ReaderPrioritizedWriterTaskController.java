package com.yurwar.task2;

import com.yurwar.TaskController;
import com.yurwar.task2.thread.Reader;
import com.yurwar.task2.thread.Writer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReaderPrioritizedWriterTaskController implements TaskController {
    @Override
    public void executeTask() {
        ReentrantLock writerLock = new ReentrantLock();
        Condition writeCondition = writerLock.newCondition();
        ReentrantLock readerLock = new ReentrantLock();
        Condition readCondition = readerLock.newCondition();
        ReentrantLock syncLock = new ReentrantLock();
        Counter readerCounter = new ReaderCounter();
        ValueContainer<String> messageContainer = new ValueContainer<>();
        Reader reader1 = new Reader(readerLock, writerLock, readCondition, writeCondition, readerCounter, messageContainer);
        Reader reader2 = new Reader(readerLock, writerLock, readCondition, writeCondition, readerCounter, messageContainer);
        Reader reader3 = new Reader(readerLock, writerLock, readCondition, writeCondition, readerCounter, messageContainer);

        Writer writer1 = new Writer(messageContainer, writerLock, syncLock, writeCondition, 10);

        writer1.start();

        reader1.start();
        reader2.start();

        try {
            writer1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        reader1.interrupt();
        reader2.interrupt();
    }
}
