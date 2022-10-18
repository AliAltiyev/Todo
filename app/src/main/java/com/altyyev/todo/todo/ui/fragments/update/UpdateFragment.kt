package com.altyyev.todo.todo.ui.fragments.update

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.altyyev.todo.R
import com.altyyev.todo.databinding.FragmentUpdateBinding
import com.altyyev.todo.todo.data.Priority
import com.altyyev.todo.todo.data.RoomModel
import com.altyyev.todo.todo.ui.fragments.add.AddNewViewModel
import com.altyyev.todo.todo.util.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment(R.layout.fragment_update) {
    private val navArgs by navArgs<UpdateFragmentArgs>()
    private val binding by viewBinding(FragmentUpdateBinding::bind)
    private val viewModel: UpdateViewModel by viewModels()
    private val addViewModel: AddNewViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        updateTodo()
        binding.run {
            messageText.setText(navArgs.item.message)
            titleText.setText(navArgs.item.title)
            spinner.setSelection(parsePriority(navArgs.item.priority))
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            (parent?.getChildAt(0) as TextView).setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red
                                )
                            )
                        }
                        1 -> {
                            (parent?.getChildAt(0) as TextView).setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.green
                                )
                            )
                        }
                        2 -> {
                            (parent?.getChildAt(0) as TextView).setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.orange
                                )
                            )
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            viewModel.deleteTodo(navArgs.item)
            findNavController().navigate(R.id.action_updateFragment2_to_mainFragment)
            Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun updateTodo() = with(binding) {
        updateButton.setOnClickListener {
            val validation = viewModel.checkUserValidation(
                title = titleText.text.toString(),
                message = messageText.text.toString()
            )
            if (validation) {
                viewModel.updateTodo(
                    RoomModel(
                        title = binding.titleText.text.toString(),
                        message = binding.messageText.text.toString(),
                        priority = addViewModel.parsePriority(spinner.selectedItem.toString()),
                        id = navArgs.item.id
                    )
                )
                findNavController().navigate(R.id.action_updateFragment2_to_mainFragment)
            } else {
                Snackbar.make(requireView(), "Validation is not successfully", Snackbar.LENGTH_LONG)
                    .show()
            }

        }
    }

    private fun parsePriority(priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 2
            Priority.LOW -> 1
        }
    }
}
