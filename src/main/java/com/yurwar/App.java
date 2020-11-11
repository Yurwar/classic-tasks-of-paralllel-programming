package com.yurwar;

import com.yurwar.task1.ProducerConsumerTaskController;
import com.yurwar.task2.ReaderPrioritizedWriterTaskController;
import com.yurwar.task3.DiningPhilosophersTaskController;
import com.yurwar.task4.SleepingBarberTaskController;

import java.util.Scanner;

public class App {

    public static final String DINING_PHILOSOPHERS_TASK_NAME = "Dining Philosophers";
    private static final String START_TASK_STRING_FORMAT = "======== START TASK #%d '%s' ========\n";
    private static final String FINISH_TASK_STRING_FORMAT = "======== FINISHED TASK #%d '%s' ========\n";
    private static final String PRODUCER_CONSUMER_TASK_NAME = "Producer-Consumer";
    private static final String SLEEPING_BARBER_TASK_NAME = "Sleeping Barber";
    private static final String READER_WRITER_TASK_NAME = "Reader-Writer";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printHelp();
        outer:
        while (true) {
            printEnterChar();
            String input = scanner.next();
            switch (input.toLowerCase()) {
                case "1":
                    executeFirstTask();
                    break;
                case "2":
                    executeSecondTask();
                    break;
                case "3":
                    executeThirdTask();
                    break;
                case "4":
                    executeFourthTask();
                    break;
                case "a":
                    executeAllTasks();
                    break;
                case "h":
                    printHelp();
                    break;
                case "e":
                    break outer;
                default:
                    printIncorrectInput();
            }
        }
    }

    private static void printIncorrectInput() {
        System.out.println("Incorrect input. Try again");
    }

    private static void printHelp() {
        System.out.println("Parallel programming. Lab #4. Yurii Matora. Variant #19");
        System.out.printf("Choose 1 - for %s task%n", PRODUCER_CONSUMER_TASK_NAME);
        System.out.printf("Choose 2 - for %s task%n", READER_WRITER_TASK_NAME);
        System.out.printf("Choose 3 - for %s task%n", DINING_PHILOSOPHERS_TASK_NAME);
        System.out.printf("Choose 4 - for %s task%n", SLEEPING_BARBER_TASK_NAME);
        System.out.printf("Choose a - for execute all tasks%n");
        System.out.printf("Choose h - for help%n");
        System.out.printf("Choose e - for exit%n");
    }

    private static void printEnterChar() {
        System.out.print("> ");
    }

    private static void executeFourthTask() {
        System.out.printf(START_TASK_STRING_FORMAT, 4, SLEEPING_BARBER_TASK_NAME);
        TaskController task4 = new SleepingBarberTaskController();
        task4.executeTask();
        System.out.printf(FINISH_TASK_STRING_FORMAT, 4, SLEEPING_BARBER_TASK_NAME);
    }

    private static void executeThirdTask() {
        System.out.printf(START_TASK_STRING_FORMAT, 3, DINING_PHILOSOPHERS_TASK_NAME);
        TaskController task3 = new DiningPhilosophersTaskController();
        task3.executeTask();
        System.out.printf(FINISH_TASK_STRING_FORMAT, 3, DINING_PHILOSOPHERS_TASK_NAME);
    }

    private static void executeSecondTask() {
        System.out.printf(START_TASK_STRING_FORMAT, 2, READER_WRITER_TASK_NAME);
        TaskController task2 = new ReaderPrioritizedWriterTaskController();
        task2.executeTask();
        System.out.printf(FINISH_TASK_STRING_FORMAT, 2, READER_WRITER_TASK_NAME);
    }

    private static void executeFirstTask() {
        System.out.printf(START_TASK_STRING_FORMAT, 1, PRODUCER_CONSUMER_TASK_NAME);
        TaskController task1 = new ProducerConsumerTaskController();
        task1.executeTask();
        System.out.printf(FINISH_TASK_STRING_FORMAT, 1, PRODUCER_CONSUMER_TASK_NAME);
    }

    private static void executeAllTasks() {
        executeFirstTask();
        executeSecondTask();
        executeThirdTask();
        executeFourthTask();
    }
}
