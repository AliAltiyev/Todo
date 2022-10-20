package com.altyyev.todo.todo.data

import androidx.lifecycle.LiveData
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
    suspend fun deleteAllTodos()

    @Delete
    suspend fun deleteTodo(roomModel: RoomModel)

    @Update
    suspend fun updateTodo(model: RoomModel)

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery")
    fun searchQuery(searchQuery: String): Flow<List<RoomModel>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<RoomModel>>

    @Query("SELECT * FROM todo_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<RoomModel>>


}