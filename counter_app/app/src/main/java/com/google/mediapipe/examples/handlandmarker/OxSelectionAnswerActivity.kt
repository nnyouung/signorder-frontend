package com.google.mediapipe.examples.handlandmarker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class OxSelectionAnswerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ox_selection_answer)

        // TODO : "문의사항 있으신가요?" 수어 아바타 영상 띄우기

        val inquiryNumber = intent.getIntExtra("inquiry_number", -1)
        println("전달받은 문의 번호: $inquiryNumber")

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        // TODO 1 : xml에서 OX 버튼으로 바뀌면 id 수정 하기
        // TODO 2 : No 버튼 클릭 시 화면 전환 추가하기
        val yesButton = findViewById<ImageButton>(R.id.restRoomButton) // O 버튼 id로 바꿔야 함
        val noButton = findViewById<ImageButton>(R.id.wifiButton)   // X 버튼 id로 바꿔야 함

        yesButton.setOnClickListener{
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("inquiry_number", inquiryNumber)
            startActivity(intent)
        }

    }

}