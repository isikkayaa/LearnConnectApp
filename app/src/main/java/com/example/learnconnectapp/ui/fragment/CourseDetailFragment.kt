package com.example.learnconnectapp.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnconnectapp.R
import com.example.learnconnectapp.data.entity.Comments
import com.example.learnconnectapp.data.entity.CurrentlyCourseList
import com.example.learnconnectapp.data.entity.DownloadKurslar
import com.example.learnconnectapp.data.entity.Kurslar
import com.example.learnconnectapp.data.entity.Video
import com.example.learnconnectapp.data.entity.VideoProgress
import com.example.learnconnectapp.databinding.FragmentCourseDetailBinding
import com.example.learnconnectapp.ui.adapter.CommentsAdapter
import com.example.learnconnectapp.ui.adapter.VideoAdapter
import com.example.learnconnectapp.ui.viewmodel.CourseDetailViewModel
import com.example.learnconnectapp.ui.viewmodel.DownloadViewModel
import com.example.learnconnectapp.ui.viewmodel.HomePageViewModel
import com.example.learnconnectapp.ui.viewmodel.ProfileViewModel
import com.example.learnconnectapp.util.SharedPreferencesHelper
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseDetailFragment : Fragment() {

    private lateinit var binding   : FragmentCourseDetailBinding
    private val courseDetailViewModel : CourseDetailViewModel by viewModels()
    private val viewModel : HomePageViewModel by viewModels()
    private val profileViewModel : ProfileViewModel by viewModels()
    private val downloadViewModel : DownloadViewModel by viewModels()
    private lateinit var videoAdapter: VideoAdapter
    private lateinit var gelenCourse : Kurslar
    private  var courseId : Int = 1
    private var videoId : Int = 1
    private var userId : Int = 1
    private lateinit var adapter: CommentsAdapter

    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences("LearnConnectApp", Context.MODE_PRIVATE)
    }

    private lateinit var firebaseAuth: FirebaseAuth

    private var isExpanded = false
    private var isEnrolled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

    }

    @SuppressLint("StringFormatInvalid")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_course_detail,container,false)
        binding.viewModel = viewModel
        binding.coursedetailviewModel = courseDetailViewModel


        binding.courseDetailFragment = this

        binding.lifecycleOwner = viewLifecycleOwner


        adapter = CommentsAdapter(requireContext(), emptyList(), emptyList(),profileViewModel,
            emptyList())


        val dummyVideos = listOf(
            Video(1, 1, 1, "Introduction to Kotlin","https://example.com/kotlin_intro.mp4"),
            Video(2, 1, 2,"Advanced Kotlin", "https://example.com/kotlin_advanced.mp4"),
            Video(3, 2, 3,"Java Basics", "https://example.com/java_basics.mp4"),
            Video(4, 2, 4,"Java Advanced Topics", "https://example.com/java_advanced.mp4")
        )

        val dummyVideoProgress = listOf(
            VideoProgress(1, userId = 1, courseId = 1, videoId = 1, progress = 5000),
            VideoProgress(2, userId = 1, courseId = 1, videoId = 2, progress = 10000)
        )


        val bundle : CourseDetailFragmentArgs by navArgs()
        gelenCourse = bundle.kurs
        courseId = gelenCourse.kurs_id


        checkEnrollmentStatus(gelenCourse.kurs_id)



        binding.imageView6.setOnClickListener{
            downloadVideo(courseId, videoId)
        }


        binding.kursNesnesi = gelenCourse

        val bundle1:CourseDetailFragmentArgs by navArgs()
        gelenCourse = bundle1.kurs


        setupRecyclerView()
        observeViewModel()

        courseDetailViewModel.loadVideos(courseId,userId)


        binding.imageView5.setOnClickListener {
            showCommentDialog(courseId)
        }

        binding.button.setOnClickListener {
            onApplyButtonClick()
        }


        return binding.root


}

    override fun onResume() {
        super.onResume()

    }


    fun downloadVideo(courseId: Int, videoId: Int) {
        courseDetailViewModel.getVideoUrl(courseId, videoId) { videoUrl ->
            val downloadKurs = DownloadKurslar(
                download_kurs_id = courseId,
                download_kurs_isim = gelenCourse.kurs_isim,
                imageLinks = gelenCourse.kurs_gorsel,

            )

            downloadViewModel.saveDownloadedCourse(downloadKurs)
        }
    }


    private fun getVideoUrl(courseId: Int, videoId: Int): String {
        return when (videoId) {
            1 -> "https://www.example.com/videos/kotlin_giris.mp4"
            2 -> "https://www.example.com/videos/kotlin_ileri.mp4"
            else -> "https://www.example.com/videos/default_video.mp4"
        }
    }

    private fun setupRecyclerView() {
        videoAdapter = VideoAdapter(courseDetailViewModel) { video ->
            navigateToVideoPlayer(video)
        }

        binding.rvcourseDetail.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = videoAdapter
            setHasFixedSize(true)
        }

        courseDetailViewModel.videos.value?.let { videoAdapter.submitList(it) }
    }


    private fun observeViewModel() {
        courseDetailViewModel.videos.observe(viewLifecycleOwner) { videoList ->
            Log.d("CourseDetailFragment", "Videos loaded: $videoList")
            if (!videoList.isNullOrEmpty()) {
                videoAdapter.submitList(videoList)
                Log.d("CourseDetailFragment", "Videos submitted to adapter: $videoList")
            } else {
                Log.d("CourseDetailFragment", "No videos available")
                videoAdapter.submitList(emptyList())
            }
        }

        courseDetailViewModel.videoProgress.observe(viewLifecycleOwner) { progressList ->
            videoAdapter.updateProgress(progressList) // İlerlemeleri adapter'a güncelle
        }
    }


    fun onApplyButtonClick() {
        isEnrolled = !isEnrolled
        updateApplyButtonState()

        saveEnrollmentStatus(gelenCourse.kurs_id, isEnrolled)

        if (isEnrolled) {
            courseDetailViewModel.insertCurrentlyCourse(
                CurrentlyCourseList(
                    currently_kurs_id = gelenCourse.kurs_id,
                    currently_kurs_isim = gelenCourse.kurs_isim,
                    imageLinks = gelenCourse.kurs_gorsel
                )
            )
        } else {
            courseDetailViewModel.deleteCurrentlyCourse(gelenCourse.kurs_id)
        }
    }


    private fun navigateToVideoPlayer(video: Video) {
        val action = CourseDetailFragmentDirections.actionCourseDetailFragmentToVideoPlayerFragment(
            videoId = video.videoId,
            videoUrl = video.videoUrl,
            courseId = video.courseId,
            userId = userId
        )
        findNavController().navigate(action)
    }
    private fun updateApplyButtonState() {
        if (isEnrolled) {
            binding.button.apply {
                text = "ENROLLED"
                setBackgroundColor(ContextCompat.getColor(context, R.color.green))
            }

        } else { binding.button.apply {
            text = "APPLY"
            setBackgroundColor(ContextCompat.getColor(context, R.color.anaRenkKoyu))
        }

        }
    }

    private fun checkEnrollmentStatus(courseId: Int) {
        isEnrolled = getEnrollmentStatus(courseId)
        updateApplyButtonState()
    }


    private fun saveEnrollmentStatus(courseId: Int, isEnrolled: Boolean) {
        sharedPreferences.edit()
            .putBoolean("enrollment_status_$courseId", isEnrolled)
            .apply()
    }

    private fun getEnrollmentStatus(courseId: Int): Boolean {
        return sharedPreferences.getBoolean("enrollment_status_$courseId", false)
    }



    fun showCommentDialog(courseId: Int) {
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
                    courseTitle = gelenCourse.kurs_isim,
                    userComment = commentText,
                    courseImageUrl = gelenCourse.kurs_gorsel,
                    rating = rating
                )

                val currentComments = SharedPreferencesHelper.getComments(requireContext()).toMutableList()
                currentComments.add(newComment)
                SharedPreferencesHelper.saveComments(requireContext(), currentComments)

                updateCommentsUI(currentComments)
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    fun updateCommentsUI(comments: List<Comments>) {
        adapter.submitList(comments)
    }




    companion object {

        @JvmStatic
        @BindingAdapter("imageResource")
        fun bindImageResource(imageView: ImageView, resourceId: Int) {
            imageView.setImageResource(resourceId)
        }
    }

}






