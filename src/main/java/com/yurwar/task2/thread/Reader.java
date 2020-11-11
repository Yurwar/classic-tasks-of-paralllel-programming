package com.yurwar.task2.thread;

import com.yurwar.task2.ValueContainer;

public class Reader extends Thread {
    private static final String READ_MESSAGE_FORMAT = "Read message '%s' in %s%n";
    private final ValueContainer<?> messageContainer;

    public Reader(ValueContainer<?> messageContainer) {
        this.messageContainer = messageContainer;
    }

    public Reader(String name, ValueContainer<?> messageContainer) {
        super(name);
        this.messageContainer = messageContainer;
    }

    public Reader(ThreadGroup group, String name, ValueContainer<?> messageContainer) {
        super(group, name);
        this.messageContainer = messageContainer;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            readMessage(messageContainer.getMessage());
        }
    }

    private void readMessage(Object message) {
        System.out.printf(READ_MESSAGE_FORMAT, message, getName());
    }
}
