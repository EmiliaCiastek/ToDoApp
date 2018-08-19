package com.ciastek.todoapp;

public enum TaskState {
    NEW("New"),
    IN_PROGRESS("In progress"),
    DONE("Done");

    private String stateName;
    private int value;

    TaskState (String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String toString () {
        return stateName;
    }

    public int getValue () {
        switch (stateName) {
            case "New":
                return 0;
            case "In progress":
                return 1;
            case "Done":
                return 2;
            default:
                return 0;
        }
    }
}
