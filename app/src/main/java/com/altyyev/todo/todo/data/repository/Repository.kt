package com.altyyev.todo.todo.data.repository

import com.altyyev.todo.todo.data.Dao
import com.altyyev.todo.todo.data.RoomModel
import javax.inject.Inject

class Repository @Inject constructor(private val dao: Dao) {
    suspend fun insertTodo(model: RoomModel) {
        dao.insertTodo(model)
    }

    suspend fun updateTodo(model: RoomModel) {
        dao.updateTodo(model)
    }

    fun deleteAllTodo() {
        dao.deleteAllTodos()
    }

    fun getAllTodos() = dao.getAllTodos()
}