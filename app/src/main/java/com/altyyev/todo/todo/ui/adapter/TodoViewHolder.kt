package com.altyyev.todo.todo.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.altyyev.todo.databinding.TodoRecyclerRawBinding
import com.altyyev.todo.todo.data.RoomModel

class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = TodoRecyclerRawBinding.bind(view)

    fun bind(item: RoomModel) = with(binding) {
        titleText.text = item.title
        messageText.text = item.message
    }
}
