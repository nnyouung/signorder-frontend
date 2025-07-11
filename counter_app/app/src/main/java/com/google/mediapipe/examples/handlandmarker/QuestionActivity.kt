/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.mediapipe.examples.handlandmarker

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.mediapipe.examples.handlandmarker.databinding.ActivityQuestionBinding

class QuestionActivity : AppCompatActivity() {
    // TODO: 해당 액티비티로 들어갈 때 카운트다운 기능
    private lateinit var activityMainBinding: ActivityQuestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val backButton = activityMainBinding.root.findViewById<ImageButton>(R.id.backButton)

        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // TODO: 화면 전환 테스트용으로 작성됨, gRC 로직 구현해야 함
        // sendButton 클릭 시 LoadingActivity로 바로 이동
        val sendButton = activityMainBinding.sendButton
        sendButton.setOnClickListener {
            val intent = Intent(this, LoadingActivity::class.java)
            startActivity(intent)
        }
    }
}
