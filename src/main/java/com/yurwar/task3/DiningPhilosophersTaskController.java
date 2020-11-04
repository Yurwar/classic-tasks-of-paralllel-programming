package com.yurwar.task3;

import com.yurwar.TaskController;
import com.yurwar.task3.thread.Philosopher;

import java.util.concurrent.Semaphore;

public class DiningPhilosophersTaskController implements TaskController {

    private static final int AMOUNT_OF_PHILOSOPHERS = 5;
    public static final int LAST_PHILOSOPHER_INDEX = AMOUNT_OF_PHILOSOPHERS - 1;
    private static final int AMOUNT_OF_FORKS = 5;
    private static final int INITIAL_SEMAPHORE_VALUE = 1;
    private static final String PHILOSOPHER_NAME_FORMAT = "Philosopher #%d";
    public static final int TASK_EXECUTION_TIME = 5000;

    @Override
    public void executeTask() {
        Philosopher[] philosophers = new Philosopher[AMOUNT_OF_PHILOSOPHERS];
        Semaphore[] forkSemaphores = new Semaphore[AMOUNT_OF_FORKS];

        for (int i = 0; i < AMOUNT_OF_FORKS; i++) {
            forkSemaphores[i] = new Semaphore(INITIAL_SEMAPHORE_VALUE);
        }

        for (int i = 0; i < AMOUNT_OF_PHILOSOPHERS; i++) {
            Semaphore leftForkSemaphore = getFork(forkSemaphores, ForkType.LEFT, i);
            Semaphore rightForkSemaphore = getFork(forkSemaphores, ForkType.RIGHT, i);

            String name = String.format(PHILOSOPHER_NAME_FORMAT, i + 1);
            if (i == LAST_PHILOSOPHER_INDEX) {
                philosophers[i] =
                        new Philosopher(name, rightForkSemaphore, leftForkSemaphore);
            } else {
                philosophers[i] =
                        new Philosopher(name, leftForkSemaphore, rightForkSemaphore);
            }

            philosophers[i].start();
        }

        waitForPhilosophers();
        finishTask(philosophers);
    }

    private Semaphore getFork(Semaphore[] forkSemaphores, ForkType type, int index) {
        switch (type) {
            case LEFT:
                return forkSemaphores[index];
            case RIGHT:
                return forkSemaphores[(index + 1) % AMOUNT_OF_FORKS];
            default:
                return null;
        }
    }

    private void finishTask(Philosopher[] philosophers) {
        try {
            for (int i = 0; i < AMOUNT_OF_PHILOSOPHERS; i++) {
                philosophers[i].interrupt();
                philosophers[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private void waitForPhilosophers() {
        try {
            Thread.sleep(TASK_EXECUTION_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
