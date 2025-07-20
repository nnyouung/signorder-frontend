package com.google.mediapipe.examples.handlandmarker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class LoadingActivity : AppCompatActivity() {

    private lateinit var signVideo: SignVideo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        signVideo = findViewById(R.id.signVideo)

        signVideo.setup(
            listOf(
                R.raw.video9_guide_staff_response,
            )
        )

        // sign_urls 수신 시 AnswerActivity로 전환
        // LoadingActivity는 중간 대기 화면으로 활용
        WebSocketService.onSignUrlsReceived = { urls ->
            runOnUiThread {
                val intent = Intent(this, AnswerActivity::class.java)
                intent.putStringArrayListExtra("sign_urls", ArrayList(urls))
                startActivity(intent)
                finish()
            }
        }

    }
}