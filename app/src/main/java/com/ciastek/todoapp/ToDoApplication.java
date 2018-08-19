package com.ciastek.todoapp;

import android.app.Application;

import com.ciastek.todoapp.model.Task;

import java.util.ArrayList;
import java.util.List;

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
