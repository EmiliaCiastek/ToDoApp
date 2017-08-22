package com.ciastek.todoapp;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ciastek.todoapp.database.DatabaseDescription;
import com.ciastek.todoapp.utils.TaskUtils;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TASKS_LOADER = 0;
    private static final String TASK_URI = "task_uri";

    private Task currentTask;
    private int taskId;
    private TextInputLayout summaryTextLayout;
    private TextInputLayout descriptionTextLayout;
    private Spinner prioritySpinner;
    private Spinner stateSpinner;
    private Uri taskUri;
    private ArrayAdapter<TaskPriority> prioritySpinnerAdapter;
    ArrayAdapter<TaskState> stateSpinnerAdapter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        taskUri = getIntent().getExtras().getParcelable(TASK_URI);


        prioritySpinner = (Spinner) findViewById(R.id.taskDetailsPriority);
        prioritySpinnerAdapter = new ArrayAdapter<TaskPriority>(this, android.R.layout.simple_spinner_item, TaskPriority.values());
        prioritySpinner.setAdapter(prioritySpinnerAdapter);

        stateSpinner = (Spinner) findViewById(R.id.taskDetailsState);
        stateSpinnerAdapter = new ArrayAdapter<TaskState>(this, android.R.layout.simple_spinner_item, TaskState.values());
        stateSpinner.setAdapter(stateSpinnerAdapter);

        summaryTextLayout = (TextInputLayout) findViewById(R.id.taskSummaryTextLayout);
        descriptionTextLayout = (TextInputLayout) findViewById(R.id.taskDescriptionTextLayout);




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportLoaderManager().initLoader(TASKS_LOADER, null, this);

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
                getContentResolver().delete(taskUri, null, null);
                finish();
            }
        });

        builder.create().show();
    }

    private void saveTask(){
        String newSummary = summaryTextLayout.getEditText().getText().toString();

        if(TaskUtils.summaryExist(newSummary, ToDoApplication.getApplication().getTasks())){
            displayWarningDialog(R.string.summary_exist_message);
        } else if (TaskUtils.isSummaryCorrect(newSummary)){
            String newDescription = descriptionTextLayout.getEditText().getText().toString();
            TaskPriority newPriority = (TaskPriority) prioritySpinner.getSelectedItem();
            TaskState newState = (TaskState) stateSpinner.getSelectedItem();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseDescription.Task.COLUMN_SUMMARY, newSummary);
            contentValues.put(DatabaseDescription.Task.COLUMN_DESCRIPTION, newDescription);
            contentValues.put(DatabaseDescription.Task.COLUMN_PRIORITY, newPriority.getValue());
            contentValues.put(DatabaseDescription.Task.COLUMN_STATE, newState.getValue());

            int updatedRows = getContentResolver().update(taskUri, contentValues, null, null);

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

    @Override
    public Loader<Cursor> onCreateLoader (int id, Bundle args) {
        // na podstawie argumentu określającego identyfikator utwórz właściwy obiekt CursorLoader;
        //  w tym fragmencie znajduje się tylko jeden obiekt Loader, a więc instrukcja switch jest zbędna
        CursorLoader cursorLoader;

        switch (id) {
            case TASKS_LOADER:
                cursorLoader = new CursorLoader(this,
                        taskUri, // adres Uri kontaktu, który ma zostać wyświetlony
                        null, // rzutowanie wartości null zwraca wszystkie kolumny
                        null, // wybranie wartości null zwraca wszystkie rzędy
                        null, // brak argumentów selekcji
                        null); // kolejność sortowania
                break;
            default:
                cursorLoader = null;
                break;
        }

        return cursorLoader;
    }

    @Override
    public void onLoadFinished (Loader<Cursor> loader, Cursor data) {

        if (data != null && data.moveToFirst()) {

            int summaryIndex = data.getColumnIndex(DatabaseDescription.Task.COLUMN_SUMMARY);
            int descriptionIndex = data.getColumnIndex(DatabaseDescription.Task.COLUMN_DESCRIPTION);
            int priorityIndex = data.getColumnIndex(DatabaseDescription.Task.COLUMN_PRIORITY);
            int stateIndex = data.getColumnIndex(DatabaseDescription.Task.COLUMN_STATE);

            summaryTextLayout.getEditText().setText(data.getString(summaryIndex));

            descriptionTextLayout.getEditText().setText(data.getString(descriptionIndex));

            int prioritySpinnerPosition = prioritySpinnerAdapter.getPosition(TaskPriority.values()[data.getInt(priorityIndex)]);
            prioritySpinner.setSelection(prioritySpinnerPosition);

            int stateSpinnerPosition = stateSpinnerAdapter.getPosition(TaskState.values()[data.getInt(stateIndex)]);
            stateSpinner.setSelection(stateSpinnerPosition);
        }
    }

    @Override
    public void onLoaderReset (Loader<Cursor> loader) {

    }
}
