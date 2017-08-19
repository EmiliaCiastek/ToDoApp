package com.ciastek.todoapp;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

/**
 * Created by emcia on 19.08.2017.
 */

@RunWith(AndroidJUnit4.class)
public class EspressoTests {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void shouldStartNewTaskActivityAfterButtonClicked(){
        Espresso.onView(ViewMatchers.withId(R.id.addTaskButton)).perform(ViewActions.click());
        intended(hasComponent(NewTaskActivity.class.getName()));

    }
}
