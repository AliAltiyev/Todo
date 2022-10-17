package com.altyyev.todo.todo.util

import androidx.room.TypeConverter
import com.altyyev.todo.todo.data.Priority

class TodoTypeConverter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(value: String): Priority {
        return Priority.valueOf(value)
    }
}