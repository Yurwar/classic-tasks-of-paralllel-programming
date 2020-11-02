package com.yurwar.task1;

public class Consumer extends Thread {
    private final BufferedQueue<Integer> bufferedQueue;
    private final int totalElements;

    public Consumer(int totalElements, BufferedQueue<Integer> bufferedQueue, String name) {
        super(name);
        this.bufferedQueue = bufferedQueue;
        this.totalElements = totalElements;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < totalElements; i++) {
                if (!isInterrupted()) {
                    int value = bufferedQueue.poll();

                    System.out.println("Consumed: " + value);

                    sleep(50);
                }
            }
            System.out.println("FINISHED: Consumed " + totalElements + " elements");
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
