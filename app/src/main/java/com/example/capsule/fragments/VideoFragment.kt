package com.example.capsule.fragments

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.capsule.databinding.FragmentVideoBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoFragment : Fragment() {
    private lateinit var binding: FragmentVideoBinding
    private var playbackPosition: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupVideoView()
        binding.goToBook.setOnClickListener {
            Toast.makeText(requireContext(), "Clicked on go to Book", Toast.LENGTH_SHORT).show()
        }




    }

    private fun setupVideoView() {
        val mediaController = MediaController(requireContext())
        val videoUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/kotlin-project-aef9a.appspot.com/o/The%20Components%20of%20Blood%20and%20Their%20Importance.mp4?alt=media&token=16949b8b-d25a-4a42-9fdb-66ae4688b5d4&_gl=1*1e2ejdj*_ga*MTc1NDY2MTkyNi4xNjgwMjYwMjgw*_ga_CW55HF8NVT*MTY5ODg2MDMwMC4xMjYuMS4xNjk4ODYwNTMzLjYwLjAuMA..")
        binding.videoView.apply {
            binding.progress.visibility = View.VISIBLE
            // Set an OnPreparedListener to detect when the video is prepared
            setOnPreparedListener { mediaPlayer ->
                mediaPlayer.seekTo(playbackPosition) // Seek to the last saved playback position
                mediaController.setAnchorView(binding.videoView)
                setMediaController(mediaController)
                // Show the progress bar while the video is loading
                binding.progress.visibility = View.VISIBLE

                // Set an OnInfoListener to detect when the video is buffered
                setOnInfoListener { _, what, extra ->
                    if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                        binding.progress.visibility = View.VISIBLE
                    } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                        binding.progress.visibility = View.GONE
                    }
                    true
                }

                mediaPlayer.start() // Start video playback after seeking to the saved position
            }

            setVideoURI(videoUri)
        }
    }




}