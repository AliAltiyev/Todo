package com.altyyev.todo.todo.ui.fragments.update

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altyyev.todo.todo.data.RoomModel
import com.altyyev.todo.todo.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun deleteTodo(model: RoomModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(model)
        }
    }

    fun updateTodo(model: RoomModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(model)
        }
    }

    fun checkUserValidation(title: String, message: String): Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(message))
    }

}