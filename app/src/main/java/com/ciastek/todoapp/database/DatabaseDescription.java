package com.ciastek.todoapp.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by emcia on 22.08.2017.
 */

public class DatabaseDescription {

    public static final String AUTHORITY = "com.ciastek.todoapp.database";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final class Task implements BaseColumns {
        public static final String TABLE_NAME = "tasks";

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String COLUMN_SUMMARY = "summary";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRIORITY = "priority";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_DUE_DATE = "due_date";


        public static Uri buildTaskUri (long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
