package com.yurwar.task4;

import com.yurwar.task4.thread.Client;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class Barbershop {
    private final int waitingPlaces;
    private final AtomicInteger queueSize;
    private final Object barberSync;
    private final Queue<Client> clients;

    public Barbershop(int waitingPlaces) {
        this.waitingPlaces = waitingPlaces;
        this.queueSize = new AtomicInteger();
        this.barberSync = new Object();
        this.clients = new LinkedList<>();
    }

    public int getWaitingPlaces() {
        return waitingPlaces;
    }

    public AtomicInteger getQueueSize() {
        return queueSize;
    }

    public Object getBarberSync() {
        return barberSync;
    }

    public Queue<Client> getClients() {
        return clients;
    }
}
