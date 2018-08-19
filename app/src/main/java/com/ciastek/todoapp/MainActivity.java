package com.ciastek.todoapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.ciastek.todoapp.database.DatabaseDescription;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, NavigationView.OnNavigationItemSelectedListener {

    public static final String TASK_URI = "task_uri";
    private static final int TASKS_LOADER = 0;

    private List<Task> tasks;
    private TasksAdapter tasksAdapter;
    private FloatingActionButton addFloatingButton;
    private RecyclerView tasksView;
    private String selection = null;
    private String sortOrder = DatabaseDescription.Task.COLUMN_PRIORITY + " DESC, " + DatabaseDescription.Task.COLUMN_SUMMARY;

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
                        selection,
                        null,
                        sortOrder);
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


    @Override
    public boolean onNavigationItemSelected (@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.show_all) {
            selection = null;
        } else if (id == R.id.show_done) {
            selection = DatabaseDescription.Task.COLUMN_STATE + " = " + TaskState.DONE.getValue();
        } else if (id == R.id.show_open) {
            selection = DatabaseDescription.Task.COLUMN_STATE + " != " + TaskState.DONE.getValue();
        } else if (id == R.id.show_today) {
            Calendar todayCal = Calendar.getInstance();
            Log.d("MainActivity", "date calendar: " + todayCal);
            String dateFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            String todayDate = sdf.format(todayCal.getTime());
            Log.d("MainActivity", "date formatted: " + todayDate);

            //selection = DatabaseDescription.Task.COLUMN_DUE_DATE + " = " + todayDate;
            selection = DatabaseDescription.Task.COLUMN_DUE_DATE + " = '" + todayDate + "'";
            Log.d("MainActivity", "selection: " + selection);

        } else if (id == R.id.sort_date) {
            sortOrder = DatabaseDescription.Task.COLUMN_DUE_DATE + ", " + DatabaseDescription.Task.COLUMN_SUMMARY;
        } else if (id == R.id.sort_priority) {
            sortOrder = DatabaseDescription.Task.COLUMN_PRIORITY + " DESC, " + DatabaseDescription.Task.COLUMN_SUMMARY;
        }

        getSupportLoaderManager().restartLoader(TASKS_LOADER, null, this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
