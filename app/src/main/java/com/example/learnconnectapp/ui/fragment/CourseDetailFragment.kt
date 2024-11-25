package com.example.learnconnectapp.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.learnconnectapp.R
import com.example.learnconnectapp.data.entity.Comments
import com.example.learnconnectapp.data.entity.ImageLinks
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.data.entity.Video
import com.example.learnconnectapp.databinding.FragmentCourseDetailBinding
import com.example.learnconnectapp.room.KurslarDao
import com.example.learnconnectapp.ui.adapter.VideoAdapter
import com.example.learnconnectapp.ui.viewmodel.CourseDetailViewModel
import com.example.learnconnectapp.ui.viewmodel.HomePageViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseDetailFragment : Fragment() {

    private lateinit var binding   : FragmentCourseDetailBinding
    private lateinit var viewModel : HomePageViewModel
    private lateinit var courseDetailViewModel: CourseDetailViewModel
    private lateinit var gelenCourse: Kurslar
    private lateinit var gelenGorsel: ImageLinks
    private lateinit var videoAdapter: VideoAdapter
    private  var courseId : Int = 1
    private var videoId : Int = 1
    private var userId : Int = 1


    private lateinit var firebaseAuth: FirebaseAuth

    private var isExpanded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: CourseDetailViewModel by viewModels()
        courseDetailViewModel = tempViewModel
        firebaseAuth = FirebaseAuth.getInstance()

    }

    @SuppressLint("StringFormatInvalid")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_course_detail,container,false)
        binding.viewModel = viewModel

        binding.coursedetailviewModel = courseDetailViewModel


        viewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)
        courseDetailViewModel = ViewModelProvider(this).get(CourseDetailViewModel::class.java)



        binding.bookDetailFragment = this

        videoAdapter = VideoAdapter(emptyList(), emptyList(),::onVideoSelected)

        binding.rvcourseDetail.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = videoAdapter
        }



        val bundle : CourseDetailFragmentArgs by navArgs()
        gelenCourse = bundle.kurs

        binding.downloadButton.setOnClickListener{
            downloadVideo(courseId, videoId)
        }


        binding.kursNesnesi = gelenCourse

        val bundle1:CourseDetailFragmentArgs by navArgs()
        gelenGorsel = bundle1.gorsel
        binding.gorselNesnesi = gelenGorsel

        val thumbnailUrl = gelenGorsel.thumbnail
        if (thumbnailUrl != null) {
            context?.let {
              Glide.with(it)
                    .load(thumbnailUrl)
                    .placeholder(R.drawable.baseline_list_24)
                    .error(R.drawable.baseline_list_24)
                    .into(binding.imageViewKitapDetayresim)


            }
        } else {
            binding.imageViewKitapDetayresim.setImageResource(R.drawable.baseline_menu_24)
        }


        observeViewModel()
        courseDetailViewModel.loadVideos(courseId,userId)




        return binding.root


}

    private fun downloadVideo(courseId: Int, videoId: Int){
        courseDetailViewModel.getVideoUrl(courseId, videoId) { videoUrl ->
            // İndirme işlemini buraya ekleyin
        }
    }

    private fun getVideoUrl(courseId: Int, videoId: Int): String {
        // Dummy veri veya veri kaynağından video URL'ini döndür
        return when (videoId) {
            1 -> "https://www.example.com/videos/kotlin_giris.mp4"
            2 -> "https://www.example.com/videos/kotlin_ileri.mp4"
            else -> "https://www.example.com/videos/default_video.mp4"
        }
    }



    private fun observeViewModel() {
        courseDetailViewModel.videos.observe(viewLifecycleOwner) {
            videos -> videos?.let {
                videoAdapter.updateVideos(it) } }
        courseDetailViewModel.videoProgress.observe(viewLifecycleOwner) { progress ->
            progress?.let {
                videoAdapter.updateVideoProgress(it)
            }
        }
    }
    private fun onVideoSelected(video: Video) {
        val action = CourseDetailFragmentDirections.actionCourseDetailFragmentToVideoPlayerFragment(
            userId = userId,
            courseId = video.courseId,
            videoId = video.videoId,
            videoUrl = video.videoUrl )
        Navigation.findNavController(requireView()).navigate(action)
    }

    private fun showCommentDialog(courseId: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_comment, null)
        val commentEditText = dialogView.findViewById<EditText>(R.id.commentEditText)
        val ratingBar = dialogView.findViewById<RatingBar>(R.id.ratingBar)

        AlertDialog.Builder(requireContext())
            .setTitle("Add Comment")
            .setView(dialogView)
            .setPositiveButton("Submit") { dialog, which ->
                val commentText = commentEditText.text.toString()
                val rating = ratingBar.rating.toInt()
                val newComment = Comments(
                    comment_id = 0,
                    courseTitle = "Course Title",
                    userComment = commentText,
                    courseImageUrl = null,
                    rating = rating
                )
                // Yorum ekleme işlevi
                addComment(newComment)
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun addComment(comments: Comments) {
        courseDetailViewModel.addComments(comments)
    }



    companion object {

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, imageUrl: String?) {
            if (!imageUrl.isNullOrEmpty()) {
                Glide.with(view.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.baseline_list_24) // Varsayılan görsel
                    .error(R.drawable.baseline_list_24) // Hata durumunda gösterilecek görsel
                    .into(view)
            } else {
                view.setImageResource(R.drawable.baseline_download_24) // Eğer resim yoksa varsayılan görseli göster
            }
        }
    }

}






