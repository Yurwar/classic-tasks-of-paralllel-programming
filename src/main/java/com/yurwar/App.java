package com.yurwar;

import com.yurwar.task1.ProducerConsumerTaskController;

public class App {

    private static final String START_TASK_STRING_FORMAT = "======== START TASK #%d '%s' ========\n";
    private static final String FINISH_TASK_STRING_FORMAT = "======== FINISHED TASK #%d '%s' ========\n";
    private static final String PRODUCER_CONSUMER_TASK_NAME = "Producer-Consumer";

    public static void main(String[] args) {
        System.out.printf(START_TASK_STRING_FORMAT, 1, PRODUCER_CONSUMER_TASK_NAME);
        TaskController task1 = new ProducerConsumerTaskController();
        task1.executeTask();
        System.out.printf(FINISH_TASK_STRING_FORMAT, 1, PRODUCER_CONSUMER_TASK_NAME);

    }
}
