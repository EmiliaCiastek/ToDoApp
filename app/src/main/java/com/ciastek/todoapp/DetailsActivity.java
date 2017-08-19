package com.ciastek.todoapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DetailsActivity extends AppCompatActivity {

    private Task currentTask;
    private int taskId;
    private TextInputLayout summaryTextLayout;
    private TextInputLayout descriptionTextLayout;
    private Spinner prioritySpinner;
    private Spinner stateSpinner;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prioritySpinner = (Spinner) findViewById(R.id.taskPriority);
        ArrayAdapter<TaskPriority> prioritySpinnerAdapter = new ArrayAdapter<TaskPriority>(this, android.R.layout.simple_spinner_item, TaskPriority.values());
        prioritySpinner.setAdapter(prioritySpinnerAdapter);

        stateSpinner = (Spinner) findViewById(R.id.taskState);
        ArrayAdapter<TaskState> stateSpinnerAdapter = new ArrayAdapter<TaskState>(this, android.R.layout.simple_spinner_item, TaskState.values());
        stateSpinner.setAdapter(stateSpinnerAdapter);

        taskId = getIntent().getExtras().getInt("ITEM_ID");
        currentTask = ToDoApplication.getApplication().getTasks().get(taskId);

        summaryTextLayout = (TextInputLayout) findViewById(R.id.taskSummaryTextLayout);
        summaryTextLayout.getEditText().setText(currentTask.getSummary());

        descriptionTextLayout = (TextInputLayout) findViewById(R.id.taskDescriptionTextLayout);
        descriptionTextLayout.getEditText().setText(currentTask.getDescription());

        int prioritySpinnerPosition = prioritySpinnerAdapter.getPosition(currentTask.getPriority());
        prioritySpinner.setSelection(prioritySpinnerPosition);

        int stateSpinnerPosition = stateSpinnerAdapter.getPosition(currentTask.getState());
        stateSpinner.setSelection(stateSpinnerPosition);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.delete_task:
                deleteTask(taskId);
                return true;
            case R.id.save_task:
                saveTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteTask(final int taskId){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.confirm_delete_tittle);
        builder.setMessage(R.string.confirm_delete_message);
        builder.setNegativeButton(R.string.button_cancel, null);
        builder.setPositiveButton(R.string.button_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialogInterface, int i) {
                ToDoApplication.getApplication().getTasks().remove(taskId);
                finish();
            }
        });

        builder.create().show();
    }

    private void saveTask(){
        currentTask.setSummary(summaryTextLayout.getEditText().getText().toString());
        currentTask.setDescription(descriptionTextLayout.getEditText().getText().toString());
        currentTask.setPriority((TaskPriority) prioritySpinner.getSelectedItem());
        currentTask.setState((TaskState) stateSpinner.getSelectedItem());

        finish();
    }
}
