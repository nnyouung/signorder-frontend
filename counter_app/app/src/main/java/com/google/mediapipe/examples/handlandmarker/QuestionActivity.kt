package com.google.mediapipe.examples.handlandmarker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.grpc.GrpcClient
import com.google.mediapipe.examples.handlandmarker.databinding.ActivityQuestionBinding
import com.google.mediapipe.examples.handlandmarker.databinding.ActivityGeneralQuestionBinding
import com.google.mediapipe.examples.handlandmarker.fragment.CameraFragment

class QuestionActivity : AppCompatActivity() {
    // TODO: 해당 액티비티로 들어갈 때 카운트다운 기능
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutType = intent.getStringExtra("layoutType")
        when (layoutType) {
            "order" -> {
                val binding = ActivityQuestionBinding.inflate(layoutInflater)
                setContentView(binding.root)

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

    private fun setupSendButton(sendButton: View, containerId: Int, inquiryType: String) {
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

                    // 전송 후 LoadingActivity로 이동
                    val intent = Intent(this@QuestionActivity, LoadingActivity::class.java)
                    startActivity(intent)

                } else {
                    Log.w("QuestionActivity", "아직 누적된 데이터가 없습니다.")
                }
            } else {
                Log.e("QuestionActivity", "CameraFragment를 찾을 수 없습니다.")
            }
        }
    }
}
