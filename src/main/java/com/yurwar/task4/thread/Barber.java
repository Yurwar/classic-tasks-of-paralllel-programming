package com.yurwar.task4.thread;

import com.yurwar.task4.Barbershop;

import java.util.concurrent.atomic.AtomicInteger;

public class Barber extends Thread {

    private static final String BARBER_INTERRUPTED_MESSAGE_FORMAT = "%s is interrupted. Finish servicing%n";
    private static final String BARBER_FINISH_SERVICING_CLIENT_MESSAGE_FORMAT = "%s finish servicing for %s%n";
    private static final String BARBER_STARTED_SERVICING_CLIENT_MESSAGE_FORMAT = "%s started servicing for %s%n";
    private static final String SERVICED_AMOUNT_OF_CLIENTS_MESSAGE_FORMAT = "%s serviced %d clients%n";

    private final Barbershop barbershop;
    private final int clientServiceTime;
    private final AtomicInteger amountOfClients = new AtomicInteger();

    public Barber(String name, Barbershop barbershop, int clientServiceTime) {
        super(name);
        this.barbershop = barbershop;
        this.clientServiceTime = clientServiceTime;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                waitForClients();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            Client client;
            synchronized (barbershop.getClients()) {
                barbershop.getQueueSize().decrementAndGet();

                client = barbershop.getClients().poll();
            }

            synchronized (client.getClientSync()) {
                client.getClientSync().notify();
            }

            serviceClient(client);

            synchronized (client.getClientSync()) {
                client.getClientSync().notify();
            }
        }
        System.out.printf(SERVICED_AMOUNT_OF_CLIENTS_MESSAGE_FORMAT, getName(), amountOfClients.get());
    }

    private void waitForClients() throws InterruptedException {
        while (barbershop.getQueueSize().get() == 0) {
            synchronized (barbershop.getBarberSync()) {
                try {
                    barbershop.getBarberSync().wait();
                } catch (InterruptedException e) {
                    System.out.printf(BARBER_INTERRUPTED_MESSAGE_FORMAT, getName());
                    throw e;
                }
            }
        }
    }

    private void serviceClient(Client client) {
        try {
            System.out.printf(BARBER_STARTED_SERVICING_CLIENT_MESSAGE_FORMAT, getName(), client.getName());
            Thread.sleep(clientServiceTime);
            amountOfClients.incrementAndGet();
            System.out.printf(BARBER_FINISH_SERVICING_CLIENT_MESSAGE_FORMAT, getName(), client.getName());
        } catch (InterruptedException e) {
            System.out.printf(BARBER_INTERRUPTED_MESSAGE_FORMAT, getName());
            Thread.currentThread().interrupt();
        }
    }
}
