package com.ciastek.todoapp;

/**
 * Created by emcia on 19.08.2017.
 */

public enum TaskPriority {
    MINOR("Minor"),
    MAJOR("Major"),
    CRITICAL("Critical");


    private String priorityName;
    private int value;

    TaskPriority(String priorityName){
        this.priorityName = priorityName;
    }

    @Override public String toString(){
        return priorityName;
    }

    public int getValue(){
        switch (priorityName){
            case "Minor":
                return 0;
            case "Major":
                return 1;
            case "Critical":
                return 2;
            default:
                return 0;
        }
    }
}
