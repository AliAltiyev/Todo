package com.altyyev.todo.todo.ui.fragments.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.altyyev.todo.R
import com.altyyev.todo.databinding.FragmentMainBinding
import com.altyyev.todo.todo.data.RoomModel
import com.altyyev.todo.todo.ui.adapter.BaseItemTouchHelper
import com.altyyev.todo.todo.ui.adapter.TodoAdapter
import com.altyyev.todo.todo.ui.fragments.add.AddNewViewModel
import com.altyyev.todo.todo.ui.fragments.update.UpdateViewModel
import com.altyyev.todo.todo.util.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), SearchView.OnQueryTextListener {

    private val viewModel: MainViewModel by viewModels()
    private val updateViewModel: UpdateViewModel by viewModels()
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val addViewModel: AddNewViewModel by viewModels()
    private val adapter by lazy { TodoAdapter(requireContext()) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setHasOptionsMenu(true)
    }

    private fun setUpView() = with(binding) {
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = ScaleInBottomAnimator().apply {
            addDuration = 300
            removeDuration = 300
            moveDuration = 300
            changeDuration = 300

        }
        viewModel.getAllTodos.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            if (list.isEmpty()) {
                emptyBox.visibility = View.VISIBLE
            }
        }
        binding.floatingActionBar.setOnClickListener {
            val navigation = MainFragmentDirections.actionMainFragmentToAddNewFragment()
            Navigation.findNavController(it).navigate(navigation)
        }
        swipeToDelete(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val item = menu.findItem(R.id.serch_menu)
        val actionView = item.actionView as? SearchView
        actionView?.setOnQueryTextListener(this)
        actionView?.isSubmitButtonEnabled = true
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all -> {
                val builder = MaterialAlertDialogBuilder(requireContext())
                builder.setTitle(R.string.delete_all)
                builder.setIcon(R.drawable.delete_icon)
                builder.setPositiveButton(R.string.delete_all) { _, _ ->
                    viewModel.deleteAll()
                }
                builder.setNegativeButton(getString(R.string.cancel)) { _, _ -> }
                builder.setMessage(getString(R.string.are_you_sure_to_delete))
                builder.show()
            }
            R.id.high -> {
                viewModel.sortByHighPriority.observe(viewLifecycleOwner) { list ->
                    adapter.submitList(list)
                }
            }
            R.id.low -> {
                viewModel.sortByLowPriority.observe(viewLifecycleOwner) { list ->
                    adapter.submitList(list)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val baseItemTouchHelper = object : BaseItemTouchHelper() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //Delete
                val model = adapter.currentList[viewHolder.bindingAdapterPosition]
                updateViewModel.deleteTodo(model)
                //Undo deleted item
                undoDeletedItem(viewHolder.itemView, model)

            }
        }
        val helper = ItemTouchHelper(baseItemTouchHelper)
        helper.attachToRecyclerView(recyclerView)
    }

    private fun undoDeletedItem(view: View, model: RoomModel) =
        Snackbar.make(view, getString(R.string.undo_deleted_item), Snackbar.LENGTH_LONG).setAction(
            "undo"
        ) {
            addViewModel.insertTodo(model)
        }.show()

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            viewModel.searchQuery("%$query%").asLiveData().observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            viewModel.searchQuery("%$query%").asLiveData().observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
        return true
    }

}
