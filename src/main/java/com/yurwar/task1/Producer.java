package com.yurwar.task1;

import java.util.concurrent.ThreadLocalRandom;

public class Producer extends Thread {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final BufferedQueue<Integer> bufferedQueue;
    private final int totalElements;

    public Producer(int totalElements, BufferedQueue<Integer> bufferedQueue, String name) {
        super(name);
        this.bufferedQueue = bufferedQueue;
        this.totalElements = totalElements;
    }

    @Override
    public void run() {
        try {
            int value;
            for (int i = 0; i < totalElements; i++) {
                if (!isInterrupted()) {
                    value = random.nextInt();

                    bufferedQueue.add(value);

                    System.out.println("Produced value: " + value);

                    sleep(30);
                }
            }
            System.out.println("FINISHED: Produced " + totalElements + " elements");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
