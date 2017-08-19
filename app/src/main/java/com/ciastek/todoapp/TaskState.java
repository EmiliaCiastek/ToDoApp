package com.ciastek.todoapp;

/**
 * Created by emcia on 19.08.2017.
 */

public enum TaskState {
    NEW("New"),
    IN_PROGRESS("In progress"),
    DONE("Done");


    private String stateName;

    TaskState(String stateName){
        this.stateName = stateName;
    }

    @Override public String toString(){
        return stateName;
    }
}
