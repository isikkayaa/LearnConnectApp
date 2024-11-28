package com.example.learnconnectapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.learnconnectapp.R
import com.example.learnconnectapp.databinding.FragmentVideoPlayerBinding
import com.example.learnconnectapp.ui.viewmodel.VideoPlayerViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoPlayerFragment : Fragment() {
    private lateinit var binding: FragmentVideoPlayerBinding
    private lateinit var player: SimpleExoPlayer
    private val viewModel: VideoPlayerViewModel by viewModels()
    private var userId: Int = 1
    private var courseId: Int = 1
    private var videoId: Int = 1
    private lateinit var videoUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getInt("userId")
            courseId = it.getInt("courseId")
            videoId = it.getInt("videoId")
            videoUrl = it.getString("videoUrl", "")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_player, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        player = SimpleExoPlayer.Builder(requireContext()).build()
        binding.playerView.player = player

        viewModel.getProgress(userId, courseId, videoId) { progress ->
            player.setMediaItem(MediaItem.fromUri(videoUrl))
            player.prepare()
            player.seekTo(progress.toLong())
            player.playWhenReady = true
        }
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (!isPlaying) {
                    val progress = player.currentPosition
                    viewModel.saveProgress(userId, courseId, videoId, progress)
                }
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                binding.progressBar.visibility = if (playbackState == Player.STATE_BUFFERING) View.VISIBLE else View.GONE
            }

            override fun onPlayerError(error: PlaybackException) {
                Toast.makeText(requireContext(), "Video oynatma hatası: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        player.release()
    }
}


