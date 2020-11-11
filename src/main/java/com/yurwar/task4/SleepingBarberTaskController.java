package com.yurwar.task4;

import com.yurwar.TaskController;
import com.yurwar.task4.thread.Barber;
import com.yurwar.task4.thread.Client;

public class SleepingBarberTaskController implements TaskController {

    private static final String BARBER_NAME_FORMAT = "Barber #%d";
    private static final String CLIENT_NAME_FORMAT = "Client #%d";
    private static final int DEFAULT_WAITING_PLACES = 3;
    private static final int DEFAULT_CLIENT_SERVICE_TIME = 60;
    private static final int DEFAULT_AMOUNT_OF_CUSTOMERS = 20;
    private static final int GENERATION_INTERVAL = 50;
    private static final int TIME_TO_WAIT_FOR_TASK = 2000;

    @Override
    public void executeTask() {
        Barbershop barbershop = new Barbershop(DEFAULT_WAITING_PLACES);
        Barber barber = new Barber(String.format(BARBER_NAME_FORMAT, 1), barbershop, DEFAULT_CLIENT_SERVICE_TIME);
        barber.start();

        generateNewCustomers(DEFAULT_AMOUNT_OF_CUSTOMERS, barbershop);
        waitForTaskFinishing(TIME_TO_WAIT_FOR_TASK, barber);
    }

    private void waitForTaskFinishing(int timeToWait, Barber barber) {
        try {
            Thread.sleep(timeToWait);
            barber.interrupt();
            barber.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private void generateNewCustomers(int amountOfCustomers, Barbershop barbershop) {
        for (int customerNumber = 1; customerNumber <= amountOfCustomers; customerNumber++) {
            Client client = new Client(String.format(CLIENT_NAME_FORMAT, customerNumber), barbershop);
            client.start();
            try {
                Thread.sleep(GENERATION_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
