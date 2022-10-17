package com.altyyev.todo.todo.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.altyyev.todo.R
import com.altyyev.todo.todo.base.BaseAdapter
import com.altyyev.todo.todo.data.Priority
import com.altyyev.todo.todo.data.RoomModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.android.synthetic.main.todo_recycler_raw.view.*
import javax.inject.Inject


class TodoAdapter @Inject constructor(@ApplicationContext private val context: Context) :
    BaseAdapter<RoomModel>(
        itemsSame = { old, new -> old.id == new.id },
        contentsSame = { old, new -> old.id == new.id }) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo_recycler_raw, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodoViewHolder -> {
                getItem(position)?.let { item ->
                    holder.bind(item)
                    when (getItem(position).priority) {
                        Priority.HIGH -> holder.itemView.priority.setCardBackgroundColor(
                            ContextCompat.getColor(context, R.color.red)
                        )
                        Priority.MEDIUM -> holder.itemView.priority.setCardBackgroundColor(
                            ContextCompat.getColor(context, R.color.orange)
                        )
                        Priority.LOW -> holder.itemView.priority.setCardBackgroundColor(
                            ContextCompat.getColor(context, R.color.green)
                        )
                    }
//                    holder.itemView.setOnClickListener {
//                        val action =
//                            RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(item)
//                        it.findNavController().navigate(action)
//                    }
                }
            }
        }
    }
}

