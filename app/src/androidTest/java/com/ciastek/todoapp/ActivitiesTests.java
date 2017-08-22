package com.ciastek.todoapp;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;


/**
 * Created by emcia on 19.08.2017.
 */

@RunWith(AndroidJUnit4.class)
public class ActivitiesTests {

    private final String NEW_TASK_DESCRIPTION = "Test task description .....";
    private final TaskPriority NEW_TASK_PRIORITY = TaskPriority.MAJOR;
    private final TaskPriority TASK_PRIORITY_CHANGED = TaskPriority.CRITICAL;
    private final TaskState NEW_TASK_STATE = TaskState.NEW;
    private final TaskState TASK_STATE_CHANGED = TaskState.DONE;


    @Rule
    public ActivityTestRule<MainActivity> mainActicityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void shouldStartNewTaskActivityAfterButtonClicked(){
        onView(withId(R.id.addTaskButton)).perform(click());
        onView(withId(R.id.newTaskSummary)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldShowNewTaskInRecyclerViewAfterAdding() throws Exception {

        String taskSummary = "Test Task";
        createNewTask(taskSummary);

        onView(withId(R.id.tasksRecyclerView)).perform(scrollTo(withText(taskSummary)));
        onView(withText(taskSummary)).check(matches(isDisplayed()));

    }

    @Test
    public void shouldDisplayNewTaskDetailsActivity(){
        String taskSummary = "New Task";

        createNewTask(taskSummary);

        onView(withId(R.id.tasksRecyclerView))
                .perform(RecyclerViewActions.actionOnItem(
                        withText(taskSummary), click()));

        onView(withId(R.id.taskDetailsSummary)).check(matches(isDisplayed()));

        onView(withId(R.id.taskDetailsSummary)).check(matches(withText(taskSummary)));
        onView(withId(R.id.taskDetailsDescription)).check(matches(withText(NEW_TASK_DESCRIPTION)));
        onView(withId(R.id.taskDetailsPriority)).check(matches(withSpinnerText(NEW_TASK_PRIORITY.toString())));
        onView(withId(R.id.taskDetailsState)).check(matches(withSpinnerText(NEW_TASK_STATE.toString())));
    }

    private void createNewTask(String taskSummary){
        onView(withId(R.id.addTaskButton)).perform(click());

        onView(withId(R.id.newTaskEditSummary)).perform(typeText(taskSummary), closeSoftKeyboard());
        onView(withId(R.id.newTaskEditDescription)).perform(typeText(NEW_TASK_DESCRIPTION), closeSoftKeyboard());

        onView(withId(R.id.newTaskPriority)).perform(click());
        onData(allOf(is(instanceOf(TaskPriority.class)), is(NEW_TASK_PRIORITY))).perform(click());

        onView(withId(R.id.saveButton)).perform(click());
    }



}
