package com.google.mediapipe.examples.handlandmarker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import com.example.grpc.GrpcClient

class OxSelectionAnswerActivity : AppCompatActivity() {
    private val grpcClient = GrpcClient()
    private lateinit var signVideo: SignVideo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ox_selection_answer)

        signVideo = findViewById(R.id.signVideo)

        signVideo.setup(
            listOf(
                R.raw.video1_qna_additional,
                R.raw.video2_guide_additional_qna,
                R.raw.video3_guide_korean_signlang,
                R.raw.video4_guide_signlang_complete
            )
        )

        val inquiryNumber = intent.getIntExtra("inquiry_number", -1)
        val layoutType = intent.getStringExtra("layoutType")?: "inquiry"
        println("전달받은 문의 번호: $inquiryNumber")

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        val yesButton = findViewById<LinearLayout>(R.id.yesButton)
        val noButton = findViewById<LinearLayout>(R.id.noButton)

        // TODO: 버튼 클릭 시 색상 변화 기능 및 아이콘 변경
        yesButton.setOnClickListener{
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("layoutType", layoutType)
            intent.putExtra("num", inquiryNumber)
            startActivity(intent)
        }

        noButton.setOnClickListener{
            grpcClient.sendFastInquiry(title = layoutType, num = inquiryNumber) { success ->
                runOnUiThread {
                    if (success) {
                        println("FastInquiry 성공")
                    } else {
                        println("FastInquiry 오류")
                    }
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

}