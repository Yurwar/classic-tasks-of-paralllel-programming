package com.yurwar.task2.thread;

import com.yurwar.task2.ValueContainer;

public class Writer extends Thread {
    private static final String MESSAGE_FORMAT = "Message #%d from %s";
    private final ValueContainer<String> valueContainer;
    private final int messagesToWrite;

    public Writer(ValueContainer<String> valueContainer, int messagesToWrite) {
        this.valueContainer = valueContainer;
        this.messagesToWrite = messagesToWrite;
    }

    public Writer(String name, ValueContainer<String> valueContainer, int messagesToWrite) {
        super(name);
        this.valueContainer = valueContainer;
        this.messagesToWrite = messagesToWrite;
    }

    public Writer(ThreadGroup group, String name, ValueContainer<String> valueContainer, int messagesToWrite) {
        super(group, name);
        this.valueContainer = valueContainer;
        this.messagesToWrite = messagesToWrite;
    }

    @Override
    public void run() {
        for (int currMessageNum = 1; currMessageNum <= messagesToWrite && !isInterrupted(); currMessageNum++) {
            writeMessage(currMessageNum);
        }
    }

    private void writeMessage(int currMessageNum) {
        valueContainer.setMessage(createMessage(currMessageNum));
    }

    private String createMessage(int currMessageNum) {
        return String.format(MESSAGE_FORMAT, currMessageNum, getName());
    }
}
