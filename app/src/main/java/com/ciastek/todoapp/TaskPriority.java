package com.ciastek.todoapp;

/**
 * Created by emcia on 19.08.2017.
 */

public enum TaskPriority {
    MINOR("Minor"),
    MAJOR("Major"),
    CRITICAL("Critical");


    private String prorityName;

    TaskPriority(String prorityName){
        this.prorityName = prorityName;
    }

    @Override public String toString(){
        return prorityName;
    }
}
