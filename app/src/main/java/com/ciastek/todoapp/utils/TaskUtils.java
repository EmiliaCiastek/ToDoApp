package com.ciastek.todoapp.utils;

import com.ciastek.todoapp.model.Task;

import java.util.List;

public class TaskUtils {
    public static boolean isSummaryCorrect (String summary) {
        return !summary.isEmpty();
    }

    public static boolean summaryExist (String summary, List<Task> tasks) {
        for (Task task : tasks) {
            if (task.getSummary().equals(summary))
                return true;
        }

        return false;
    }
}
