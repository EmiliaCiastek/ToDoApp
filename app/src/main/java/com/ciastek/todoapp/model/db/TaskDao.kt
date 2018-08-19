package com.ciastek.todoapp.model.db

import android.arch.persistence.room.*
import com.ciastek.todoapp.model.Task

@Dao
interface TaskDao {
    @Insert
    fun addTask(task: Task): Long

    @Query("Select * from tasks")
    fun getAllTasks(): List<Task>

    @Delete
    fun removeTask(task: Task)

    @Update
    fun updateTask(task: Task)
}