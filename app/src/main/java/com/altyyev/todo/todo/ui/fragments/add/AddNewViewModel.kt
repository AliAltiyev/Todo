package com.altyyev.todo.todo.ui.fragments.add

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.altyyev.todo.R
import com.altyyev.todo.todo.data.Priority
import com.altyyev.todo.todo.data.RoomModel
import com.altyyev.todo.todo.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewViewModel @Inject constructor(private val repository: Repository,@ApplicationContext context : Context) : ViewModel() {

    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position) {
                0 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(context, R.color.red))}
                1 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(context, R.color.green))}
                2 -> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(context, R.color.orange))}
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

    }

    fun insertTodo(model: RoomModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTodo(model)
        }
    }

    fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High" -> Priority.HIGH
            "Medium" -> Priority.MEDIUM
            "Low" -> Priority.LOW
            else -> Priority.LOW
        }
    }

}