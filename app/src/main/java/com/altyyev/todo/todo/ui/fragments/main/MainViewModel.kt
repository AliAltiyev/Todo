package com.altyyev.todo.todo.ui.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.altyyev.todo.todo.data.RoomModel
import com.altyyev.todo.todo.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val getAllTodos: LiveData<List<RoomModel>> = repository.getAllTodos().asLiveData()
}