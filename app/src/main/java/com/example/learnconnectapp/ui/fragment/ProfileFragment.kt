package com.example.learnconnectapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnconnectapp.R
import com.example.learnconnectapp.databinding.FragmentProfileBinding
import com.example.learnconnectapp.ui.adapter.CommentsAdapter
import com.example.learnconnectapp.ui.adapter.CurrentlyCourseAdapter
import com.example.learnconnectapp.ui.viewmodel.ProfileViewModel
import com.example.learnconnectapp.ui.viewmodel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val usersViewModel: UsersViewModel by viewModels()
    private lateinit var viewModel: ProfileViewModel
    private lateinit var commentsAdapter: CommentsAdapter
    private lateinit var currentlyCourseAdapter: CurrentlyCourseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        commentsAdapter = CommentsAdapter(requireContext(), emptyList(), emptyList(), viewModel)
        currentlyCourseAdapter = CurrentlyCourseAdapter(emptyList(), requireContext())

        binding.recyclerViewComments.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentsAdapter
        }

        binding.recyclerViewCurrently.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = currentlyCourseAdapter
        }

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.comments.observe(viewLifecycleOwner) { comments ->
            comments?.let {
                commentsAdapter.updateComments(it)
            }
        }

        viewModel.currentlyCourses.observe(viewLifecycleOwner) { courses ->
            courses?.let {
                currentlyCourseAdapter.updateCourses(it)
            }
        }
    }
}
