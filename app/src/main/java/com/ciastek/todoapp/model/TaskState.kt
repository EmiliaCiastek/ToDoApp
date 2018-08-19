package com.ciastek.todoapp.model

import android.support.annotation.IntDef
import com.ciastek.todoapp.model.STATE.DONE
import com.ciastek.todoapp.model.STATE.IN_PROGRESS
import com.ciastek.todoapp.model.STATE.NEW

@Retention(AnnotationRetention.SOURCE)
@IntDef(NEW, IN_PROGRESS, DONE)
annotation class TaskState