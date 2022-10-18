package com.altyyev.todo.todo.ui.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.altyyev.todo.todo.data.RoomModel
import com.altyyev.todo.todo.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val getAllTodos: LiveData<List<RoomModel>> = repository.getAllTodos().asLiveData()

    val sortByHighPriority: LiveData<List<RoomModel>> = repository.sortByHighPriority().asLiveData()

    val sortByLowPriority: LiveData<List<RoomModel>> = repository.sortByLowPriority().asLiveData()


    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTodo()
        }
    }

    fun searchQuery(query: String) = repository.searchQuery(query)


}