package com.ciastek.todoapp;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.ciastek.todoapp.database.DatabaseDescription;
import com.ciastek.todoapp.utils.TaskUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity {

    private TextInputLayout taskSummaryLayout;
    private TextInputLayout taskDescriptionLayout;
    private Spinner taskPriority;
    private Calendar calendar = Calendar.getInstance();
    private TextView newTaskDueDate;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        taskSummaryLayout = (TextInputLayout) findViewById(R.id.newTaskSummary);
        taskDescriptionLayout = (TextInputLayout) findViewById(R.id.newTaskDescription);
        newTaskDueDate = (TextView) findViewById(R.id.newTaskDueDate);


        newTaskDueDate.setOnClickListener(dateClickListener);
        findViewById(R.id.newTaskDueDate).setOnClickListener(dateClickListener);

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

    private void saveTask () {
        String newSummary = taskSummaryLayout.getEditText().getText().toString();

        if (TaskUtils.summaryExist(newSummary, ToDoApplication.getApplication().getTasks())) {
            displayWarningDialog(R.string.summary_exist_message);
        } else if (TaskUtils.isSummaryCorrect(newSummary)) {
            String taskDescription = taskDescriptionLayout.getEditText().getText().toString();
            String taskDueDate = newTaskDueDate.getText().toString();
            TaskPriority priority = (TaskPriority) taskPriority.getSelectedItem();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseDescription.Task.COLUMN_SUMMARY, newSummary);
            contentValues.put(DatabaseDescription.Task.COLUMN_DESCRIPTION, taskDescription);
            contentValues.put(DatabaseDescription.Task.COLUMN_PRIORITY, priority.getValue());
            contentValues.put(DatabaseDescription.Task.COLUMN_STATE, 0);
            contentValues.put(DatabaseDescription.Task.COLUMN_DUE_DATE, taskDueDate);


            Uri newContactUri = getContentResolver().insert(
                    DatabaseDescription.Task.CONTENT_URI, contentValues);

            finish();

        } else {
            displayWarningDialog(R.string.summary_empty_message);
        }
    }

    private void displayWarningDialog (int messageId) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(R.string.summary_info_title)
                .setMessage(messageId)
                .setNeutralButton(R.string.ok_button, null)
                .create()
                .show();
    }

    private View.OnClickListener dateClickListener = new View.OnClickListener() {

        @Override
        public void onClick (View v) {
            new DatePickerDialog(NewTaskActivity.this, date, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    };

    private DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet (DatePicker view, int year, int monthOfYear,
                               int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel () {
        String dateFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        newTaskDueDate.setText(sdf.format(calendar.getTime()));
    }
}
