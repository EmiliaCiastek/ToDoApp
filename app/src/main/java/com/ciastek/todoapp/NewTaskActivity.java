package com.ciastek.todoapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ciastek.todoapp.utils.TaskUtils;

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

        taskSummaryLayout = (TextInputLayout) findViewById(R.id.newTaskSummary);
        taskDescriptionLayout = (TextInputLayout) findViewById(R.id.newTaskDescription);

        taskPriority = (Spinner) findViewById(R.id.newTaskPriority);
        ArrayAdapter<TaskPriority> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TaskPriority.values());
        taskPriority.setAdapter(spinnerAdapter);

        FloatingActionButton saveButton = (FloatingActionButton) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                saveTask();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void saveTask(){
        String newSummary = taskSummaryLayout.getEditText().getText().toString();

        if(TaskUtils.summaryExist(newSummary, ToDoApplication.getApplication().getTasks())){
            displayWarningDialog(R.string.summary_exist_message);
        } else if (TaskUtils.isSummaryCorrect(newSummary)){
            String taskDescription = taskDescriptionLayout.getEditText().getText().toString();
            TaskPriority priority = (TaskPriority) taskPriority.getSelectedItem();
            Task newTask = new Task(newSummary, taskDescription, priority);

            ToDoApplication.getApplication().getTasks().add(newTask);
            finish();
        } else {
            displayWarningDialog(R.string.summary_empty_message);
        }
    }

    private void displayWarningDialog(int messageId){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.summary_info_title)
                .setMessage(messageId)
                .setNeutralButton(R.string.ok_button, null)
                .create()
                .show();
    }

}
