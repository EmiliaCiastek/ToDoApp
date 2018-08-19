package com.ciastek.todoapp;

public class Task {

    private String summary;
    private String description;
    private TaskPriority priority;
    private TaskState state;
    private int taskId;

    public Task (String summary, String description, TaskPriority priority) {
        this.summary = summary;
        this.description = description;
        this.state = TaskState.NEW;
        this.priority = priority;
    }

    public String getSummary () {
        return summary;
    }

    public void setSummary (String summary) {
        this.summary = summary;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public TaskPriority getPriority () {
        return priority;
    }

    public void setPriority (TaskPriority priority) {
        this.priority = priority;
    }

    public TaskState getState () {
        return state;
    }

    public void setState (TaskState state) {
        this.state = state;
    }
}
