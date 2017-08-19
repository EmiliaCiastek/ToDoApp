package com.ciastek.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Task> tasks;
    private TasksAdapter adapter;
    private FloatingActionButton addFloatingButton;
    private RecyclerView tasksView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //tasks = new ArrayList<>();

        tasks = ToDoApplication.getApplication().getTasks();
        //tasks.add(new Task("1 task"));

        tasksView = (RecyclerView) findViewById(R.id.tasksRecyclerView);
        tasksView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TasksAdapter(tasks, itemClickListener);
        tasksView.setAdapter(adapter);

        addFloatingButton = (FloatingActionButton) findViewById(R.id.addTaskButton);
        addFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent newTaskIntent = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivity(newTaskIntent);
            }
        });
    }

    @Override
    protected void onResume () {
        super.onResume();

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick (View view) {
            Intent detailsIntent = new Intent(MainActivity.this, DetailsActivity.class);
            int itemPosition = tasksView.getChildLayoutPosition(view);
            detailsIntent.putExtra("ITEM_ID", itemPosition);
            startActivity(detailsIntent);
        }
    };
}
