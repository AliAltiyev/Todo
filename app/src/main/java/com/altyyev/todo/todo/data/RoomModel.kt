package com.altyyev.todo.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.altyyev.todo.todo.util.Constants.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class RoomModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val message: String,
    val priority: Priority
)
