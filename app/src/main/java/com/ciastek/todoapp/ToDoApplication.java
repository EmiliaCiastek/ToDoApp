package com.ciastek.todoapp;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emcia on 19.08.2017.
 */

public class ToDoApplication extends Application {

    private static ToDoApplication application;
    private List<Task> tasks;

    @Override
    public void onCreate () {
        super.onCreate();
        application = this;
        tasks = new ArrayList<>();
    }

    public static ToDoApplication getApplication () {
        return application;
    }

    public List<Task> getTasks () {
        return tasks;
    }
}
