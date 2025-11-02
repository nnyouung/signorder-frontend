package com.google.mediapipe.examples.handlandmarker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar

class LoadingActivity : AppCompatActivity() {

    private var signVideo: SignVideo? = null
    private var progressBar: ProgressBar? = null
    private var isReceiverRegistered = false

    private val grpcResultReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val success = intent?.getBooleanExtra("success", true) ?: true
            Log.d("LoadingActivity", "Broadcast 수신: success=$success")

            if (!success) {
                runOnUiThread {
                    try {
                        signVideo?.let {
                            Handler(Looper.getMainLooper()).postDelayed({
                                it.setup(listOf(R.raw.video12_signlang_error_1, R.raw.video12_signlang_error_2))
                                progressBar?.visibility = View.GONE
                            }, 300) // Surface 초기화 대기
                        }
                    } catch (e: Exception) {
                        Log.e("LoadingActivity", "Broadcast 처리 중 오류: ${e.message}", e)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        signVideo = findViewById(R.id.signVideo)
        progressBar = findViewById(R.id.progressBar)

        signVideo?.setup(listOf(R.raw.video9_guide_staff_response))

        // Receiver 등록 (중복 방지)
        if (!isReceiverRegistered) {
            registerReceiver(grpcResultReceiver, IntentFilter("GRPC_RESULT"))
            isReceiverRegistered = true
        }

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

        WebSocketService.onSignMessageReceived = { message, num ->
            runOnUiThread {
                val intent = Intent(this, AnswerActivity::class.java)
                intent.putExtra("fast_message", message)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isReceiverRegistered) {
            try {
                unregisterReceiver(grpcResultReceiver)
                isReceiverRegistered = false
            } catch (e: Exception) {
                Log.w("LoadingActivity", "Receiver 해제 중 오류: ${e.message}")
            }
        }
    }
}
