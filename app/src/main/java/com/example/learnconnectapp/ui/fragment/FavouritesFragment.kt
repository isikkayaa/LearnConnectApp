package com.example.learnconnectapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnconnectapp.databinding.FragmentFavouritesBinding
import com.example.learnconnectapp.ui.adapter.FavouritesAdapter
import com.example.learnconnectapp.ui.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var homePageViewModel: HomePageViewModel
    private lateinit var favoritesAdapter: FavouritesAdapter
    private var favKursId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: HomePageViewModel by viewModels()
        homePageViewModel = tempViewModel


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        homePageViewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)
        // RecyclerView Ayarı
        favoritesAdapter = FavouritesAdapter(requireContext(), emptyList(),homePageViewModel)
        binding.rvFavs.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = favoritesAdapter
        }

        observeFavorites()

        homePageViewModel.favoriteCourses.observe(viewLifecycleOwner){it->
            if(it != null) {
                favoritesAdapter.updateFavorites(it)
                homePageViewModel.getFavorites()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homePageViewModel.getFavorites()
    }

    override fun onResume() {
        super.onResume()
        homePageViewModel.getFavorites()
    }



    private fun observeFavorites() {
        homePageViewModel.favoriteCourses.observe(viewLifecycleOwner) { favCourses ->
            if (favCourses != null) {
                favoritesAdapter.updateFavorites(favCourses)
            }else {
                Log.d("FavouritesFragment", "Favori kitap listesi boş")
            }
        }
    }




    companion object {
        @JvmStatic
        @BindingAdapter("imageResource")
        fun bindImageResource(imageView: ImageView, resourceId: Int) {
            imageView.setImageResource(resourceId)
        }




    }
}
