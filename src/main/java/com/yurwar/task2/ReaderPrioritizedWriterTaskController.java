package com.yurwar.task2;

import com.yurwar.TaskController;
import com.yurwar.task2.thread.Reader;
import com.yurwar.task2.thread.Writer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReaderPrioritizedWriterTaskController implements TaskController {

    public static final int MESSAGES_TO_WRITE = 10;
    private static final String READER_NAME_FORMAT = "Reader #%d";
    private static final String WRITER_NAME_FORMAT = "Writer #%d";
    private static final String READERS_THREAD_GROUP = "Readers";
    private static final String WRITERS_THREAD_GROUP = "Writers";

    private final ThreadGroup readerGroup = new ThreadGroup(READERS_THREAD_GROUP);
    private final ThreadGroup writerGroup = new ThreadGroup(WRITERS_THREAD_GROUP);


    @Override
    public void executeTask() {
        ValueContainer<String> messageContainer = new ValueContainer<>();
        List<Reader> readers = createReaderThreads(readerGroup, messageContainer);
        startThreads(readers);

        List<Writer> writers = createWritersThreads(writerGroup, messageContainer);
        startThreads(writers);

        waitForThreads(writers);

        readerGroup.interrupt();

        waitForThreads(readers);
    }

    private List<Writer> createWritersThreads(ThreadGroup group, ValueContainer<String> valueContainer) {
        return IntStream.range(0, 1)
                .mapToObj(i -> new Writer(group, String.format(WRITER_NAME_FORMAT, 1), valueContainer,
                        ReaderPrioritizedWriterTaskController.MESSAGES_TO_WRITE))
                .collect(Collectors.toList());
    }

    private List<Reader> createReaderThreads(ThreadGroup group, ValueContainer<?> valueContainer) {
        return IntStream.range(0, 3)
                .mapToObj(i -> new Reader(group, String.format(READER_NAME_FORMAT, i + 1), valueContainer))
                .collect(Collectors.toList());
    }

    private void startThreads(List<? extends Thread> threads) {
        for (Thread t : threads) {
            t.start();
        }
    }

    private void waitForThreads(List<? extends Thread> threads) {
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
