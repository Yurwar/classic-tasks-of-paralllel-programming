package com.yurwar;

import com.yurwar.task1.ProducerConsumerTaskController;
import com.yurwar.task2.PrioritizedReaderWriterTaskController;

public class App {

    private static final String START_TASK_STRING_FORMAT = "======== START TASK #%d '%s' ========\n";
    private static final String FINISH_TASK_STRING_FORMAT = "======== FINISHED TASK #%d '%s' ========\n";
    private static final String PRODUCER_CONSUMER_TASK_NAME = "Producer-Consumer";
    private static final String PRIORITIZED_READER_WRITER_TASK_NAME = "Prioritized Reader - Writer";

    public static void main(String[] args) {
//        System.out.printf(START_TASK_STRING_FORMAT, 1, PRODUCER_CONSUMER_TASK_NAME);
//        TaskController task1 = new ProducerConsumerTaskController();
//        task1.executeTask();
//        System.out.printf(FINISH_TASK_STRING_FORMAT, 1, PRODUCER_CONSUMER_TASK_NAME);

        System.out.printf(START_TASK_STRING_FORMAT, 2, PRIORITIZED_READER_WRITER_TASK_NAME);
        TaskController task2 = new PrioritizedReaderWriterTaskController();
        task2.executeTask();
        System.out.printf(FINISH_TASK_STRING_FORMAT, 2, PRIORITIZED_READER_WRITER_TASK_NAME);

    }
}
