package com.ciastek.todoapp.model

import android.support.annotation.IntDef
import com.ciastek.todoapp.model.PRIORITY.CRITICAL
import com.ciastek.todoapp.model.PRIORITY.MAJOR
import com.ciastek.todoapp.model.PRIORITY.MINOR

@Retention(AnnotationRetention.SOURCE)
@IntDef(MINOR, MAJOR, CRITICAL)
annotation class TaskPriority

