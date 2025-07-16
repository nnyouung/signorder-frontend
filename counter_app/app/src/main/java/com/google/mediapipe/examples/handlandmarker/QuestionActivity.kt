package com.google.mediapipe.examples.handlandmarker

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.grpc.GrpcClient
import com.google.mediapipe.examples.handlandmarker.databinding.ActivityQuestionBinding
import com.google.mediapipe.examples.handlandmarker.databinding.ActivityGeneralQuestionBinding
import com.google.mediapipe.examples.handlandmarker.fragment.CameraFragment

class QuestionActivity : AppCompatActivity() {
    // TODO: 해당 액티비티로 들어갈 때 카운트다운 기능
    private var countdownOverlay: FrameLayout? = null
    private var countdownText: android.widget.TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutType = intent.getStringExtra("layoutType")
        when (layoutType) {

            "order" -> {
                val binding = ActivityQuestionBinding.inflate(layoutInflater)
                setContentView(binding.root)

                countdownOverlay = binding.root.findViewById(R.id.countDownOverlay)
                countdownText = binding.root.findViewById(R.id.countDownText)
                startCountdown()


                val backButton = binding.root.findViewById<ImageButton>(R.id.backButton)
                backButton.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }

                setupSendButton(
                    sendButton = binding.sendButton,
                    containerId = R.id.order_fragment_container,
                    inquiryType = "order"
                )
            }

            "inquiry" -> {
                val binding = ActivityGeneralQuestionBinding.inflate(layoutInflater)
                setContentView(binding.root)

                countdownOverlay = binding.root.findViewById(R.id.countDownOverlay)
                countdownText = binding.root.findViewById(R.id.countDownText)
                startCountdown()

                val backButton = binding.root.findViewById<ImageButton>(R.id.backButton)
                backButton.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }

                setupSendButton(
                    sendButton = binding.sendButton,
                    containerId = R.id.inquiry_fragment_container,
                    inquiryType = "inquiry"
                )
            }

            else -> {
                Log.e("QuestionActivity", "layoutType이 지정되지 않았습니다.")
                finish()
            }
        }
    }

    private fun startCountdown(onFinish: () -> Unit = {}) {
        countdownOverlay?.visibility = View.VISIBLE
        var count = 3
        countdownText?.text = count.toString()
        val handler = Handler(Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                count--
                if (count > 0) {
                    countdownText?.text = count.toString()
                    handler.postDelayed(this, 1000)
                } else {
                    countdownOverlay?.visibility = View.GONE
                    onFinish() 
                }
            }
        }

        handler.postDelayed(runnable, 1000)
    }

    private fun setupSendButton(sendButton: View, containerId: Int, inquiryType: String) {
        startCountdown{
            sendButton.setOnClickListener {
                val navHostFragment =
                    supportFragmentManager.findFragmentById(containerId) as? NavHostFragment

                val cameraFragment = navHostFragment
                    ?.childFragmentManager
                    ?.fragments
                    ?.firstOrNull { it is CameraFragment } as? CameraFragment

                if (cameraFragment != null) {
                    val frameData = cameraFragment.getAllFrameVectors()
                    if (frameData.isNotEmpty()) {
                        val grpcClient = GrpcClient()
                        Log.d("GrpcLog", "gRPC 요청 시작")
                        Log.d("GrpcLog", "frameData 길이 = ${frameData.size}")
                        Log.d("GrpcLog", "frameData 일부 = ${frameData.take(10)}")
                        Log.d("GrpcLog", "inquiryType = $inquiryType")
                        grpcClient.sendAllFrameData(
                            frameData = frameData,
                            inquiryType = inquiryType,
                            num = 123, // TODO: 웹소켓에서 주문번호 받아오면 수정하기
                            onLog = { message -> Log.d("GrpcLog", message) }
                        )
                        grpcClient.shutdown()
                    } else {
                        Log.w("QuestionActivity", "아직 누적된 데이터가 없습니다.")
                    }
                } else {
                    Log.e("QuestionActivity", "CameraFragment를 찾을 수 없습니다.")
                }
            }
        }

    }
}
