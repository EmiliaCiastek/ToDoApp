package com.ciastek.todoapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NewTaskActivity extends AppCompatActivity {

    private TextInputLayout taskSummaryLayout;
    private TextInputLayout taskDescriptionLayout;
    private Spinner taskPriority;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        taskSummaryLayout = (TextInputLayout) findViewById(R.id.summaryTextLayout);
        taskDescriptionLayout = (TextInputLayout) findViewById(R.id.descriptionTextLayout);

        taskPriority = (Spinner) findViewById(R.id.taskPriority);
        ArrayAdapter<TaskPriority> spinnerAdapter = new ArrayAdapter<TaskPriority>(this, android.R.layout.simple_spinner_item, TaskPriority.values());
        taskPriority.setAdapter(spinnerAdapter);

        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                String taskSummary = taskSummaryLayout.getEditText().getText().toString();
                String taskDescription = taskDescriptionLayout.getEditText().getText().toString();
                TaskPriority priority = (TaskPriority) taskPriority.getSelectedItem();
                Task newTask = new Task(taskSummary, taskDescription, priority);

                ToDoApplication.getApplication().getTasks().add(newTask);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
