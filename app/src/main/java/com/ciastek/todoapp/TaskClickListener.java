package com.ciastek.todoapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

/**
 * Created by emcia on 22.08.2017.
 */

public class TaskClickListener implements View.OnClickListener {
    private static final String TASK_URI = "task_uri";


    private Uri taskUri;
    private Context context;

    public TaskClickListener(Context context, Uri taskUri){
        this.context = context;
        this.taskUri = taskUri;
    }

    @Override
    public void onClick (View view) {
        Intent detailsIntent = new Intent(context, DetailsActivity.class);
        detailsIntent.putExtra(TASK_URI, taskUri);
        context.startActivity(detailsIntent);
    }


    public Uri getTaskUri () {
        return taskUri;
    }

    public void setTaskUri (Uri taskUri) {
        this.taskUri = taskUri;
    }
}