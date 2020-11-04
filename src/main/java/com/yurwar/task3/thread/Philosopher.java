package com.yurwar.task3.thread;

import com.yurwar.task3.ForkType;

import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
    private static final String EATING_ACTION_NAME = "Eating";
    private static final String PICK_UP_FORK_ACTION_FORMAT = "Pick up %s fork";
    private static final String THINKING_ACTION_FORMAT = "Thinking";
    private static final String INTERRUPTED_PHILOSOPHER_FORMAT = "%s has been interrupted. Finish dinner%n";
    private final Semaphore rightForkSemaphore;
    private final Semaphore leftForkSemaphore;

    public Philosopher(Semaphore rightForkSemaphore, Semaphore leftForkSemaphore) {
        this.rightForkSemaphore = rightForkSemaphore;
        this.leftForkSemaphore = leftForkSemaphore;
    }

    public Philosopher(String name, Semaphore rightForkSemaphore, Semaphore leftForkSemaphore) {
        super(name);
        this.rightForkSemaphore = rightForkSemaphore;
        this.leftForkSemaphore = leftForkSemaphore;
    }

    public Philosopher(ThreadGroup group, String name, Semaphore rightForkSemaphore, Semaphore leftForkSemaphore) {
        super(group, name);
        this.rightForkSemaphore = rightForkSemaphore;
        this.leftForkSemaphore = leftForkSemaphore;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                think();

                leftForkSemaphore.acquire();
                pickUpFork(ForkType.LEFT);

                rightForkSemaphore.acquire();
                pickUpFork(ForkType.RIGHT);

                eat();

                rightForkSemaphore.release();
                leftForkSemaphore.release();
            } catch (InterruptedException e) {
                System.out.printf(INTERRUPTED_PHILOSOPHER_FORMAT, getName());
                Thread.currentThread().interrupt();
            }
        }
    }

    private void eat() throws InterruptedException {
        doAction(EATING_ACTION_NAME);
    }

    private void pickUpFork(ForkType type) throws InterruptedException {
        doAction(String.format(PICK_UP_FORK_ACTION_FORMAT, type));
    }

    private void think() throws InterruptedException {
        doAction(THINKING_ACTION_FORMAT);
    }

    private void doAction(String action) throws InterruptedException {
        System.out.println(getName() + " - " + action);
        sleep(100);
    }
}
