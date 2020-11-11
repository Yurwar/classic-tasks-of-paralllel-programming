package com.yurwar.task4.thread;

import com.yurwar.task4.Barbershop;

public class Client extends Thread {
    public static final String CLIENT_FINISHED_HAIRCUT_ACTION_FORMAT = "%s got up from barber chair%n";
    public static final String CLIENT_STARTED_HAIRCUT_ACTION_FORMAT = "%s sat down on a barber chair%n";
    public static final String CLIENT_HAS_NO_PLACE_TO_WAIT_FOR_BARBER =
            "%s has no place for wait. Leaving barbershop%n";

    private final Barbershop barbershop;
    private final Object clientSync;

    public Client(String name, Barbershop barbershop) {
        super(name);
        this.barbershop = barbershop;
        this.clientSync = new Object();
    }

    @Override
    public void run() {
        int currentNumberOfClients;
        do {
            currentNumberOfClients = barbershop.getQueueSize().get();

            if (currentNumberOfClients >= barbershop.getWaitingPlaces()) {
                System.out.printf(CLIENT_HAS_NO_PLACE_TO_WAIT_FOR_BARBER, getName());
                return;
            }
        } while (!barbershop.getQueueSize().compareAndSet(currentNumberOfClients, currentNumberOfClients + 1));

        synchronized (barbershop.getClients()) {
            barbershop.getClients().add(this);
        }

        synchronized (barbershop.getBarberSync()) {
            barbershop.getBarberSync().notifyAll();
        }

        waitForAction(CLIENT_STARTED_HAIRCUT_ACTION_FORMAT);

        waitForAction(CLIENT_FINISHED_HAIRCUT_ACTION_FORMAT);
    }

    private void waitForAction(String actionFormat) {
        synchronized (clientSync) {
            try {
                clientSync.wait();
            } catch (InterruptedException e) {
                System.err.printf("%s was interrupted. Leaving barbershop%n", getName());
                Thread.currentThread().interrupt();
                return;
            }
            System.out.printf(actionFormat, getName());
        }
    }

    public Object getClientSync() {
        return clientSync;
    }
}
