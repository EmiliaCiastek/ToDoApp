package com.ciastek.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.ciastek.todoapp.database.DatabaseDescription;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TASK_URI = "task_uri";
    private static final int TASKS_LOADER = 0;

    private List<Task> tasks;
    private TasksAdapter tasksAdapter;
    private FloatingActionButton addFloatingButton;
    private RecyclerView tasksView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tasks = ToDoApplication.getApplication().getTasks();

        tasksView = (RecyclerView) findViewById(R.id.tasksRecyclerView);
        tasksView.setLayoutManager(new LinearLayoutManager(this));

        tasksAdapter = new TasksAdapter();
        tasksView.setAdapter(tasksAdapter);

        addFloatingButton = (FloatingActionButton) findViewById(R.id.addTaskButton);
        addFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent newTaskIntent = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivity(newTaskIntent);
            }
        });

        getSupportLoaderManager().initLoader(TASKS_LOADER, null, this);
    }

    @Override
    protected void onResume () {
        super.onResume();

        tasksAdapter.notifyDataSetChanged();
    }

    @Override
    public Loader<Cursor> onCreateLoader (int id, Bundle args) {
        switch (id) {
            case TASKS_LOADER:
                return new CursorLoader(this,
                        DatabaseDescription.Task.CONTENT_URI,
                        null,
                        null,
                        null,
                        null); // TODO (1): add sorting and filtering
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished (Loader<Cursor> loader, Cursor data) {
        tasksAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset (Loader<Cursor> loader) {
        tasksAdapter.swapCursor(null);
    }


}
