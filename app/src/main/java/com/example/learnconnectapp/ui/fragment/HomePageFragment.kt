package com.example.learnconnectapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.learnconnectapp.R
import com.example.learnconnectapp.databinding.FragmentHomePageBinding
import com.example.learnconnectapp.room.KurslarDao
import com.example.learnconnectapp.ui.adapter.HomePageAdapter
import com.example.learnconnectapp.ui.viewmodel.HomePageViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var homePageViewModel: HomePageViewModel
    private lateinit var homePageAdapter: HomePageAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var kurslarDao: KurslarDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: HomePageViewModel by viewModels()
        homePageViewModel = tempViewModel
        firebaseAuth = FirebaseAuth.getInstance()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false)

        homePageAdapter = HomePageAdapter(
            requireContext(), emptyList(), emptyList(), homePageViewModel, "HomePageFragment"
        )

        binding.homePageFragment = this
        binding.viewModel = homePageViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()



        binding.adapter = homePageAdapter

        observeViewModel()


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    homePageViewModel.searchKurslar(query)
                    Log.d("HomePageFragment", "Arama yapılıyor: $query")
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false


            }


    })
        return binding.root}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       homePageViewModel.kurslariYukle()
    }

    override fun onResume() {
        super.onResume()
        homePageViewModel.kurslariYukle()
    }

    private fun setupRecyclerView() {

        homePageAdapter = HomePageAdapter(
            requireContext(), emptyList(), emptyList(),
            homePageViewModel, "HomePageFragment"
        )
        binding.rvHomePage.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = homePageAdapter
        }


    }


    private fun observeViewModel() {
        homePageViewModel.kurslarListesi.observe(viewLifecycleOwner) { kurslar ->
            kurslar?.let {
                homePageAdapter.updateKurslar(it) } }

        homePageViewModel.searchResults.observe(viewLifecycleOwner) { results ->
            results?.let {
                homePageAdapter.updateKurslar(it) } }


    }


    companion object {
        @JvmStatic
        @BindingAdapter("imageResource")
        fun bindImageResource(imageView: ImageView, resourceId: Int) {
            imageView.setImageResource(resourceId)
        }




    }
}
