package com.ciastek.todoapp.model.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.ciastek.todoapp.model.Task

@Database(entities = [Task::class], version = 1)
abstract class TasksDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private var instance: TasksDatabase? = null

        fun get(context: Context): TasksDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        TasksDatabase::class.java, "Tasks.db")
                        .build()
            }

            return instance!!
        }
    }
}