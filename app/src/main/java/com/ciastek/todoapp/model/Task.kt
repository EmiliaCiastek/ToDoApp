package com.ciastek.todoapp.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task constructor(val summary: String,
                            val description: String,
                            @TaskPriority val priority: Int,
                            @PrimaryKey(autoGenerate = true) val id: Long? = null,
                            @TaskState var taskState: Int = STATE.NEW)

object PRIORITY {
    const val MINOR: Int = 0
    const val MAJOR: Int = 1
    const val CRITICAL: Int = 2
}

object STATE {
    const val NEW: Int = 0
    const val IN_PROGRESS: Int = 1
    const val DONE: Int = 2
}
