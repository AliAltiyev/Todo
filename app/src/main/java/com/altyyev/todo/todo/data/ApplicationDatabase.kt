package com.altyyev.todo.todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.altyyev.todo.todo.util.Constants.Companion.DATABASE_VERSION
import com.altyyev.todo.todo.util.TodoTypeConverter

@Database(entities = [RoomModel::class], exportSchema = true, version = DATABASE_VERSION)
@TypeConverters(TodoTypeConverter::class)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun getDao(): Dao
}