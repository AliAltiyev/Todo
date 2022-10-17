package com.altyyev.todo.todo.ui.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.altyyev.todo.R
import com.altyyev.todo.databinding.FragmentAddNewBinding
import com.altyyev.todo.todo.data.Priority
import com.altyyev.todo.todo.data.RoomModel
import com.altyyev.todo.todo.util.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewFragment : Fragment(R.layout.fragment_add_new) {

    private val viewModel: AddNewViewModel by viewModels()
    private val binding by viewBinding(FragmentAddNewBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpinnerPriority()

        binding.run {
            saveButton.setOnClickListener {
                if (!TextUtils.isEmpty(titleText.text)
                    && !TextUtils.isEmpty(messageText.text)
                ) {
                    insertTodo(
                        RoomModel(
                            0,
                            title = titleText.text.toString(),
                            message = messageText.text.toString(),
                            priority = parsePriority(spinner.selectedItem.toString())
                        )
                    )
                    findNavController().navigate(R.id.action_addNewFragment_to_mainFragment)
                } else {
                    Snackbar.make(
                        requireView(),
                        "You have not entered title or message try again",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setSpinnerPriority() {
        binding.spinner.onItemSelectedListener = viewModel.listener
    }

    private fun insertTodo(model: RoomModel) {
        viewModel.insertTodo(model)
    }

    private fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High" -> Priority.HIGH
            "Medium" -> Priority.MEDIUM
            "Low" -> Priority.LOW
            else -> Priority.LOW
        }
    }
}