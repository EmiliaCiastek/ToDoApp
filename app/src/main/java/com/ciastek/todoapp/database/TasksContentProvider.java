package com.ciastek.todoapp.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.ciastek.todoapp.R;

public class TasksContentProvider extends ContentProvider {

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int ONE_TASK = 1;
    private static final int TASKS = 2;

    private TasksDatabaseHelper dbHelper;

    static {
        uriMatcher.addURI(DatabaseDescription.AUTHORITY, DatabaseDescription.Task.TABLE_NAME + "/#", ONE_TASK);
        uriMatcher.addURI(DatabaseDescription.AUTHORITY, DatabaseDescription.Task.TABLE_NAME, TASKS);
    }

    @Override
    public int delete (Uri uri, String selection, String[] selectionArgs) {
        int numberOfRowsDeleted;

        String id = uri.getLastPathSegment();

        numberOfRowsDeleted = dbHelper.getWritableDatabase().delete(
                DatabaseDescription.Task.TABLE_NAME, DatabaseDescription.Task._ID + "=" + id, selectionArgs);

        if (numberOfRowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberOfRowsDeleted;
    }

    @Override
    public String getType (Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert (Uri uri, ContentValues values) {
        Uri newContactUri;

        long rowId = dbHelper.getWritableDatabase().insert(
                DatabaseDescription.Task.TABLE_NAME, null, values);

        if(rowId > 0) {
            newContactUri = DatabaseDescription.Task.buildTaskUri(rowId);

            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            throw new SQLException(getContext().getString(R.string.invalid_query_uri) + uri);
        }

        return newContactUri;
    }

    @Override
    public boolean onCreate () {
        dbHelper = new TasksDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query (Uri uri, String[] projection, String selection,
                         String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DatabaseDescription.Task.TABLE_NAME);

        switch (uriMatcher.match(uri)){
            case ONE_TASK:
                queryBuilder.appendWhere(DatabaseDescription.Task._ID + "=" + uri.getLastPathSegment());
                break;
            case TASKS:
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.invalid_query_uri));
        }

        Cursor cursor = queryBuilder.query(dbHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public int update (Uri uri, ContentValues values, String selection,
                       String[] selectionArgs) {

        int numberOfRowsUpdated;

        String id = uri.getLastPathSegment();

        numberOfRowsUpdated = dbHelper.getWritableDatabase().update(
                DatabaseDescription.Task.TABLE_NAME, values, DatabaseDescription.Task._ID + "=" + id, selectionArgs);

        if(numberOfRowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numberOfRowsUpdated;
    }
}
