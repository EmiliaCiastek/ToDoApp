package com.ciastek.todoapp.utils;

import com.ciastek.todoapp.Task;

import java.util.List;

/**
 * Created by emcia on 22.08.2017.
 */

public class TaskUtils {
    public static boolean isSummaryCorrect(String summary){
        if(summary.isEmpty())
            return false;

        return true;
    }

    public static boolean summaryExist (String summary, List<Task> tasks){
        for (Task task : tasks) {
            if (task.getSummary().equals(summary))
                return true;
        }

        return false;
    }
}
