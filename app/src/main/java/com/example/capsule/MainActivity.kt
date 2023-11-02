package com.example.capsule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.capsule.adapter.Viewpager2Adapter
import com.example.capsule.databinding.ActivityMainBinding
import com.example.capsule.fragments.LinesFragment
import com.example.capsule.fragments.MemesFragment
import com.example.capsule.fragments.NotesFragment
import com.example.capsule.fragments.QuizFragment
import com.example.capsule.fragments.VideoFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fragment: ArrayList<Fragment>
    private lateinit var countdownTimer: CountDownTimer
    private val totalTimeInMillis: Long = 360000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTimer()
        addFragmentToArrayList()
        setupViewpager()

    }

    //setting the timer
    private fun setTimer() {
        // Start the countdown timer
        countdownTimer = object : CountDownTimer(totalTimeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Update the ActionBar title with the remaining time
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                binding.timer.text = "Remaining Time: ${minutes}:${remainingSeconds}"

            }

            override fun onFinish() {
                showRestartDialog()
            }
        }
        countdownTimer.start()
    }

    //setting the viewpager
    private fun setupViewpager() {

        val viewPager2Adapter = Viewpager2Adapter(this, fragment)
        binding.viewPager.adapter = viewPager2Adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Video"
                1 -> tab.text = "Notes"
                2 -> tab.text = "Quiz"
                3 -> tab.text = "Lines"
                4 -> tab.text = "Memes"

            }
        }.attach()
    }

    //adding the fragment to the list
    private fun addFragmentToArrayList() {
        fragment = arrayListOf<Fragment>(
            VideoFragment(),
            NotesFragment(),
            QuizFragment(),
            LinesFragment(),
            MemesFragment()
        )

    }


    //showing the dialog at the end of the timer
    private fun showRestartDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Time's Up")
        builder.setMessage("Restart the app?")
        builder.setPositiveButton("Restart") { dialog, _ ->
            // Restart the app
            dialog.dismiss()
            finish()
            startActivity(intent)
        }
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
    }

}