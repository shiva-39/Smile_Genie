package com.example.rtrp_project
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rtrp_project.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val videoPath = "android.resource://" + packageName + "/" + R.raw.splsh
        binding.videoView.setVideoURI(Uri.parse(videoPath))
        binding.videoView.setOnClickListener {
            navigateToNextScreen()
        }
        binding.videoView.start()

    }

    private fun navigateToNextScreen() {
        val intent = Intent(this, Capture::class.java)
        startActivity(intent)
        finish()
    }
}