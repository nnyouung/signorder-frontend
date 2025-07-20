package com.google.mediapipe.examples.handlandmarker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class AnswerActivity : AppCompatActivity() {

    private lateinit var signVideo: SignVideo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        signVideo = findViewById(R.id.signVideo)
        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        val urls = intent.getStringArrayListExtra("sign_urls")
        val videoType = intent.getStringExtra("videoType")

        when {
            urls != null -> {
                signVideo.setupWithUrls(urls)
            }
            videoType != null -> {
                when (videoType) {
                    "restroom" -> signVideo.setup(listOf(R.raw.video10_quickqna_restroom))
                    "wifi" -> signVideo.setup(listOf(R.raw.video11_quickqna_wifi))
                    else -> null
                }
            }
        }
    }
}