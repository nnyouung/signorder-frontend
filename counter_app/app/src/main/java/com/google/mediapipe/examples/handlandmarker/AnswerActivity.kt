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

        val urls = intent.getStringArrayListExtra("sign_urls") ?: return

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        // println("받은 sign_urls: ${urls.joinToString("\n")}")
        signVideo = findViewById(R.id.signVideo)

        signVideo.setup(urls)
    }
}