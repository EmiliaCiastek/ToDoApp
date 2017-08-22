package com.ciastek.todoapp;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ciastek.todoapp.database.DatabaseDescription;

import java.util.List;

/**
 * Created by emcia on 19.08.2017.
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private View.OnClickListener clickListener;
    private Cursor cursor = null;

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item, parent, false);

        return (new ViewHolder(view));
    }

    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        long rowID  = cursor.getLong(cursor.getColumnIndex(DatabaseDescription.Task._ID));
        holder.setRowID(rowID);
        holder.getClickListener().setTaskUri(DatabaseDescription.Task.buildTaskUri(rowID));
        holder.taskTextView.setText(cursor.getString(cursor.getColumnIndex(
                DatabaseDescription.Task.COLUMN_SUMMARY)));
    }

    public void swapCursor(Cursor cursor){
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount () {
        return (cursor != null) ? cursor.getCount() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView taskTextView;
        private long rowID;
        private View itemView;
        private TaskClickListener clickListener;

        public ViewHolder (View itemView) {
            super(itemView);
            taskTextView = (TextView) itemView.findViewById(R.id.taskTextView);

            clickListener = new TaskClickListener(itemView.getContext(), DatabaseDescription.Task.buildTaskUri(rowID));
            itemView.setOnClickListener(clickListener);
        }

        public void setRowID(long rowID) {
            this.rowID = rowID;
        }

        public TaskClickListener getClickListener () {
            return clickListener;
        }
    }
}

