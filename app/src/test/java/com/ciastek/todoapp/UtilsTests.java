package com.ciastek.todoapp;

import com.ciastek.todoapp.utils.TaskUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsTests {

    @Test
    public void shouldReturnFalseIfSummaryIsEmpty (){
        String summary = "";

        assertEquals(false, TaskUtils.isSummaryCorrect(summary));
    }

    @Test
    public void shouldReturnTrueIfSummaryIsNotEmpty(){
        String summary = "Task summary";

        assertEquals(true, TaskUtils.isSummaryCorrect(summary));
    }

    @Test
    public void shouldReturnTrueIfTaskSummaryAlreadyExist(){
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("New Task", "Description", TaskPriority.MINOR));

        String newTaskSummary = "New Task";

        assertEquals(true, TaskUtils.summaryExist(newTaskSummary, tasks));
    }

    @Test
    public void shouldReturnFalseIfTaskSummaryNotExist(){
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("New Task", "Description", TaskPriority.MINOR));

        String newTaskSummary = "Task summary";

        assertEquals(false, TaskUtils.summaryExist(newTaskSummary, tasks));
    }
}