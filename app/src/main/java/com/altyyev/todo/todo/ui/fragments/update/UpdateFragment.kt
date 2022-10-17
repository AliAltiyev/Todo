package com.altyyev.todo.todo.ui.fragments.update

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.altyyev.todo.R
import com.altyyev.todo.databinding.FragmentUpdateBinding
import com.altyyev.todo.todo.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment(R.layout.fragment_update) {

    private val bidning by viewBinding(FragmentUpdateBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}