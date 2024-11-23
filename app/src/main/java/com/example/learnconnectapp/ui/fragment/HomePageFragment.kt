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
                   // homePageViewModel.searchBooks(query, Constants.GOOGLEBOOKS_API_KEY)
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
        // homePageViewModel.fetchTrendingBooks()
    }

    override fun onResume() {
        super.onResume()

        //     homePageViewModel.fetchTrendingBooks()
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
        /* homePageViewModel.trendingBooks.observe(viewLifecycleOwner) { books ->
            books?.let {
                homePageAdapter.updateBooks(it)
            }
        }

        */
    }


    companion object {
        @JvmStatic
        @BindingAdapter("bindAuthors")
        fun bindAuthors(textView: TextView, authors: List<String>?) {
            textView.text = authors?.joinToString(", ") ?: "Bilinmeyen Yazar"
        }

        @JvmStatic
        @BindingAdapter("bindDescription")
        fun bindDescription(textView: TextView, description: String?) {
            textView.text = description ?: "Description not available"
        }


        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, imageUrl: String?) {
            if (!imageUrl.isNullOrEmpty()) {
                /* Glide.with(view.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.baseline_menu_book_24) // Varsayılan görsel
                    .error(R.drawable.baseline_list_24) // Hata durumunda gösterilecek görsel
                    .into(view)
            } else {
                view.setImageResource(R.drawable.baseline_menu_book_24) // Eğer resim yoksa varsayılan görseli göster
            }
        }

                */

            }
        }
    }
}