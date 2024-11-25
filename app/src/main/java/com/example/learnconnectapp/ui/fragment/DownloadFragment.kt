package com.example.learnconnectapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnconnectapp.R
import com.example.learnconnectapp.databinding.FragmentDownloadBinding
import com.example.learnconnectapp.ui.adapter.DownloadAdapter
import com.example.learnconnectapp.ui.viewmodel.DownloadViewModel
import com.example.learnconnectapp.ui.viewmodel.HomePageViewModel
import com.example.learnconnectapp.ui.viewmodel.VideoPlayerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DownloadFragment : Fragment() {

    private lateinit var binding: FragmentDownloadBinding

    private lateinit var downloadAdapter: DownloadAdapter
    private val downloadViewModel: DownloadViewModel by viewModels()
    private val homePageViewModel: HomePageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        downloadViewModel.loadDownloadedVideos()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDownloadBinding.inflate(inflater, container, false)
        downloadAdapter = DownloadAdapter(requireContext(), emptyList(), homePageViewModel, downloadViewModel)


        binding.rvDownloads.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = downloadAdapter
        }

        observeViewModel()

        return binding.root
    }

    private fun observeViewModel() {
        downloadViewModel.downloadedVideos.observe(viewLifecycleOwner) { downloads ->
            downloads?.let {
                downloadAdapter.updateDownloads(it)
            }
        }
    }
}
