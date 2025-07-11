package com.google.mediapipe.examples.handlandmarker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class OxSelectionAnswerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ox_selection_answer)

        val inquiryNumber = intent.getIntExtra("inquiry_number", -1)
        println("전달받은 문의 번호: $inquiryNumber")

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        // To-do : OX 버튼 이동 흐름
        val yesButton = findViewById<ImageButton>(R.id.restRoomButton) // O 버튼 id로 바꿔야 함
        val noButton = findViewById<ImageButton>(R.id.wifiButton)   // X 버튼 id로 바꿔야 함

        yesButton.setOnClickListener{
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("inquiry_number", inquiryNumber)
            startActivity(intent)
        }

    }

}