package com.example.learnconnectapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.learnconnectapp.R
import com.example.learnconnectapp.data.entity.ImageLinks
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.databinding.FragmentCourseDetailBinding
import com.example.learnconnectapp.room.KurslarDao
import com.example.learnconnectapp.ui.viewmodel.HomePageViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseDetailFragment : Fragment() {
    private lateinit var binding   : FragmentCourseDetailBinding
    private lateinit var gelenCourse: Kurslar
    private lateinit var gelenGorsel: ImageLinks

    private lateinit var viewModel : HomePageViewModel
    private lateinit var firebaseAuth: FirebaseAuth

    private var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: HomePageViewModel by viewModels()
        viewModel = tempViewModel
        firebaseAuth = FirebaseAuth.getInstance()

    }

    @SuppressLint("StringFormatInvalid")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_course_detail,container,false)



        binding.bookDetailFragment = this
        binding.viewModel = viewModel

        val bundle : CourseDetailFragmentArgs by navArgs()
        gelenCourse = bundle.kurs


        binding.kursNesnesi = gelenCourse

        val bundle1:CourseDetailFragmentArgs by navArgs()
        gelenGorsel = bundle1.gorsel
        binding.gorselNesnesi = gelenGorsel

        val thumbnailUrl = gelenGorsel.thumbnail
        if (thumbnailUrl != null) {
            context?.let {
              /*  Glide.with(it)
                    .load(thumbnailUrl)
                    .placeholder(R.drawable.baseline_menu_book_24)
                    .error(R.drawable.baseline_list_24)
                    .into(binding.imageViewKitapDetayresim)

               */
            }
        } else {
            binding.imageViewKitapDetayresim.setImageResource(R.drawable.baseline_menu_24)
        }






        return binding.root


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
    }

}




