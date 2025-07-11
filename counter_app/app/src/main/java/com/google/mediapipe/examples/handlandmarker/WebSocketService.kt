package com.google.mediapipe.examples.handlandmarker

import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import android.util.Log

private const val TAG = "WebSocketDebug"

object WebSocketService {
    private var webSocket: WebSocket? = null
    private val client = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()

    private val storeCode = "5fjVwE8z"
    private val clientType = "counter_app"

//     private val URL = "${BuildConfig.WS_URL}?store_code=$storeCode&client_type=$clientType&api-key=${BuildConfig.WS_API_KEY}"

    // 테스트용 URL (로컬 서버 기준)
    private val URL = "ws://10.0.2.2:8001/ws?store_code=$storeCode&client_type=$clientType&api-key=${BuildConfig.WS_API_KEY}"

    var isConnected = false
    var signUrls: List<String> = emptyList()

    // 외부에서 수신 반응 설정 가능
    var onSignUrlsReceived: ((List<String>) -> Unit)? = null
    var onSignOrderReceived: ((Int) -> Unit)? = null  // num 값 기반 요청 대응용

    fun connect() {
        Log.d(TAG, "WebSocket 연결 시도: $URL")

        if (isConnected) return

        val request = Request.Builder().url(URL).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(ws: WebSocket, response: Response) {
                isConnected = true
                Log.d(TAG, "WebSocket 연결 성공")
            }

            override fun onMessage(ws: WebSocket, text: String) {
                Log.d(TAG, "메시지 수신: $text")
                handleMessage(text)
            }

            override fun onFailure(ws: WebSocket, t: Throwable, response: Response?) {
                isConnected = false
                Log.e(TAG, "WebSocket 오류: ${t.message}", t)
                t.printStackTrace()
            }

            override fun onClosing(ws: WebSocket, code: Int, reason: String) {
                isConnected = false
                Log.w(TAG, "WebSocket 종료: $reason")
            }
        })
    }

    fun disconnect() {
        webSocket?.close(1000, null)
        isConnected = false
    }

    private fun handleMessage(message: String) {
        try {
            Log.d(TAG, "handleMessage 실행됨")
            val json = JSONObject(message)
            val type = json.getString("type")
            val data = json.getJSONObject("data")

            when (type) {
                "signMessage" -> {
                    // 수어 응답용 (sign_urls 있을 경우)
                    if (data.has("sign_urls")) {
                        val urlsArray: JSONArray = data.getJSONArray("sign_urls")
                        signUrls = (0 until urlsArray.length()).map { i ->
                            urlsArray.getString(i)
                        }
                        Log.d(TAG, "sign_urls 수신 완료:\n${signUrls.joinToString("\n")}")
                        onSignUrlsReceived?.invoke(signUrls)
                    }

                    // 문의사항 알림용 (title=order & num 있을 경우)
                    else if (data.has("title") && data.has("num")) {
                        val title = data.getString("title")
                        val num = data.getInt("num")

                        if (title == "order") {
                            Log.d(TAG, "문의사항 메시지 수신됨 (num=$num)")
                            onSignOrderReceived?.invoke(num)
                        } else {
                            Log.d(TAG, "알 수 없는 title 수신됨: $title")
                        }
                    }
                }

                else -> {
                    Log.w(TAG, "처리되지 않은 type: $type")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "메시지 파싱 실패: ${e.message}", e)
        }
    }
}