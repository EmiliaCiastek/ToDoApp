package com.ciastek.todoapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by emcia on 19.08.2017.
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private final List<Task> tasks;
    private final View.OnClickListener clickListener;

    public TasksAdapter (List<Task> tasks, View.OnClickListener clickListener){
        this.tasks = tasks;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item, parent, false);

        return (new ViewHolder(view, clickListener));
    }

    @Override
    public void onBindViewHolder (ViewHolder holder, int position) {
        holder.taskTextView.setText(tasks.get(position).getSummary());
    }

    @Override
    public int getItemCount () {
        return tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView taskTextView;

        public ViewHolder (View itemView, View.OnClickListener clickListener) {
            super(itemView);
            taskTextView = (TextView) itemView.findViewById(R.id.taskTextView);

            itemView.setOnClickListener(clickListener);
        }
    }
}

