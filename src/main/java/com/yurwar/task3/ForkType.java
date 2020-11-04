package com.yurwar.task3;

public enum ForkType {
    LEFT, RIGHT;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
