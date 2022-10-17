package com.altyyev.todo.todo.ui.fragments.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.altyyev.todo.R
import com.altyyev.todo.databinding.FragmentMainBinding
import com.altyyev.todo.todo.ui.adapter.TodoAdapter
import com.altyyev.todo.todo.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel : MainViewModel by viewModels()
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val adapter by lazy { TodoAdapter(requireContext()) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setHasOptionsMenu(true)
    }

    private fun setUpView() = with(binding) {
        recyclerView.adapter = adapter
        viewModel.getAllTodos.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            if (it.isEmpty()){
                emptyBox.visibility = View.VISIBLE
            }
        }

        binding.floatingActionBar.setOnClickListener {
            val navigation = MainFragmentDirections.actionMainFragmentToAddNewFragment()
            Navigation.findNavController(it).navigate(navigation)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}