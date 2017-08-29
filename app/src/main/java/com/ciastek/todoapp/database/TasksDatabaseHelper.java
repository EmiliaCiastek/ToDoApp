package com.ciastek.todoapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by emcia on 22.08.2017.
 */

public class TasksDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ToDoTasks.db";
    private static final int DATABASE_VERSION = 1;

    public TasksDatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TASKS_TABLE =
                "CREATE TABLE " + DatabaseDescription.Task.TABLE_NAME + "(" +
                DatabaseDescription.Task._ID + " integer primary key, " +
                DatabaseDescription.Task.COLUMN_SUMMARY + " TEXT, " +
                DatabaseDescription.Task.COLUMN_DESCRIPTION + " TEXT, " +
                DatabaseDescription.Task.COLUMN_PRIORITY + " INTEGER, " +
                DatabaseDescription.Task.COLUMN_STATE + " INTEGER," +
                DatabaseDescription.Task.COLUMN_DUE_DATE + " DATE);";


        sqLiteDatabase.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
