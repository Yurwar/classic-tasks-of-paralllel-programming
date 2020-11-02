package com.yurwar.task1;

import com.yurwar.TaskController;

public class ProducerConsumerTaskController implements TaskController {
    private static final int TOTAL_ELEMENTS = 15;
    private static final String PRODUCER_THREAD_NAME = "producer-thread";
    private static final String CONSUMER_THREAD_NAME = "consumer-thread";

    @Override
    public void executeTask() {
        BufferedQueue<Integer> queue = new BufferedQueue<>(3);
        Consumer consumer = new Consumer(TOTAL_ELEMENTS, queue, CONSUMER_THREAD_NAME);
        Producer producer = new Producer(TOTAL_ELEMENTS, queue, PRODUCER_THREAD_NAME);

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
