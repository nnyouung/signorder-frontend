package com.google.mediapipe.examples.handlandmarker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout

class HomeActivity : AppCompatActivity() {
    // TODO: 버튼 클릭 시 색상 변화 기능

    private lateinit var signVideo: SignVideo
    private lateinit var inquiryButton: FrameLayout

    private val videoUrls = listOf(
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%82%E1%85%A7%E1%86%BC%E1%84%92%E1%85%A1%E1%84%89%E1%85%A6%E1%84%8B%E1%85%AD%2C%20%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%82%E1%85%A7%E1%86%BC%E1%84%92%E1%85%B5%20%E1%84%80%E1%85%A1%E1%84%89%E1%85%A6%E1%84%8B%E1%85%AD.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%8C%E1%85%B5%E1%86%A8%E1%84%8B%E1%85%AF%E1%86%AB.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%86%AB%E1%84%8B%E1%85%B4.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%B5%E1%86%BB%E1%84%83%E1%85%A1.mp4",
        "https://signordermenu.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%86%AB%E1%84%8B%E1%85%B4%E1%84%92%E1%85%A1%E1%84%80%E1%85%B5+%E1%84%87%E1%85%A5%E1%84%90%E1%85%B3%E1%86%AB.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%82%E1%85%AE%E1%84%85%E1%85%B3%E1%84%83%E1%85%A1.mp4",

        "https://signordermenu.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%86%AB%E1%84%8B%E1%85%B4%E1%84%92%E1%85%A1%E1%84%80%E1%85%B5+%E1%84%87%E1%85%A5%E1%84%90%E1%85%B3%E1%86%AB.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%82%E1%85%AE%E1%84%85%E1%85%B3%E1%84%83%E1%85%A1.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%8F%E1%85%A1%E1%84%86%E1%85%A6%E1%84%85%E1%85%A1.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%8F%E1%85%A7%E1%84%83%E1%85%A1%2C%20%E1%84%8F%E1%85%A7%E1%84%8C%E1%85%B5%E1%84%83%E1%85%A1.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%92%E1%85%A1%E1%84%83%E1%85%A1%2C%20%E1%84%87%E1%85%A1%E1%84%85%E1%85%A1%E1%84%83%E1%85%A1.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%86%AB%E1%84%8B%E1%85%B4.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%AE%E1%84%8B%E1%85%A5.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%87%E1%85%A9%E1%84%8B%E1%85%A7%E1%84%8C%E1%85%AE%E1%84%83%E1%85%A1.mp4",

        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%AE%E1%84%8B%E1%85%A5.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%86%AB%E1%84%8B%E1%85%B4.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%8C%E1%85%B5%E1%86%A8%E1%84%8B%E1%85%AF%E1%86%AB.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%92%E1%85%A1%E1%86%AB%E1%84%80%E1%85%B3%E1%86%AF.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%87%E1%85%A7%E1%86%AB%E1%84%92%E1%85%A1%E1%84%83%E1%85%A1%2C%20%E1%84%87%E1%85%A7%E1%86%AB%E1%84%92%E1%85%AA%E1%86%AB.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%8C%E1%85%A5%E1%86%AB%E1%84%83%E1%85%A1%E1%86%AF.mp4",

        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%AE%E1%84%8B%E1%85%A5.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%86%AB%E1%84%8B%E1%85%B4.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%8C%E1%85%A5%E1%86%AB%E1%84%87%E1%85%AE.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%87%E1%85%A9%E1%84%8B%E1%85%A7%E1%84%8C%E1%85%AE%E1%84%83%E1%85%A1.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%86%E1%85%AE%E1%86%AB%E1%84%8B%E1%85%B4.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%8C%E1%85%A5%E1%86%AB%E1%84%83%E1%85%A1%E1%86%AF.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%B1.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%8B%E1%85%A9%E1%84%85%E1%85%B3%E1%86%AB%E1%84%8D%E1%85%A9%E1%86%A8.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%87%E1%85%A5%E1%84%90%E1%85%B3%E1%86%AB.mp4",
        "https://signorderavatarvideo.s3.ap-northeast-2.amazonaws.com/%E1%84%82%E1%85%AE%E1%84%85%E1%85%B3%E1%84%83%E1%85%A1.mp4",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        signVideo = findViewById(R.id.signVideo)
        inquiryButton = findViewById(R.id.inquiryButton)

        signVideo.setup(videoUrls)

        // TODO : 수어 영상이 오면 보여주기
        // 수어 영상 수신 -> LoadActivity 에서 AnswerActivity 로 연결
        WebSocketService.onSignUrlsReceived = {urls ->
            runOnUiThread{
                val intent = Intent(this, LoadingActivity::class.java)
                intent.putStringArrayListExtra("sign_urls", ArrayList(urls))
                startActivity(intent)
            }
        }

        // "문의사항 있으신가요?" 알림 수신 시 OX 선택 화면으로 전환
        WebSocketService.onSignOrderReceived = { number ->
            runOnUiThread {
                val intent = Intent(this, OxSelectionAnswerActivity::class.java)
                intent.putExtra("inquiry_number", number)
                startActivity(intent)
            }
        }

        // WebSocket 연결
        WebSocketService.connect()

        inquiryButton.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("layoutType", "inquiry")
            startActivity(intent)
        }
    }
}
