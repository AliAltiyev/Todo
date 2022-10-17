package com.altyyev.todo.todo.data

import androidx.room.*
import androidx.room.Dao
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTodos(): Flow<List<RoomModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(model: RoomModel)

    @Query("DELETE FROM todo_table")
    fun deleteAllTodos()

    @Update
    suspend fun updateTodo(model: RoomModel)

}