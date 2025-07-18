package com.google.mediapipe.examples.handlandmarker

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class SignVideo @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    // TODO: 영상 끝나면 재실행 버튼 기능

    private var player: ExoPlayer? = null
    private var playerView: PlayerView
    private var srcList: List<String> = emptyList()
    private var currentIndex = 0
    private var onCompleted: (() -> Unit)? = null
    private var replayButton: ImageButton


    init {
        playerView = PlayerView(context)
        playerView.useController = false
        replayButton = ImageButton(context).apply{
            setImageResource(R.drawable.ic_replay)
            background = ColorDrawable(Color.parseColor("#88000000"))
            visibility = INVISIBLE
        }
        addView(playerView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(replayButton)

        playerView.post{
            replayButton.layoutParams = LayoutParams(
                playerView.width,
                playerView.height,
                Gravity.CENTER
            )
        }
    }

    fun setup(
        videoUrls: List<String>,
        onCompleted: (() -> Unit)? = null
    ) {
        this.srcList = videoUrls
        this.onCompleted = onCompleted
        currentIndex = 0

        playCurrent()
    }

    private fun playCurrent() {
        releasePlayer()

        val url = srcList.getOrNull(currentIndex)
        if (url.isNullOrEmpty()) return

        player = ExoPlayer.Builder(context).build().also { exoPlayer ->
            playerView.player = exoPlayer
//            val mediaItem = MediaItem.fromUri(url)
            val mediaItem = MediaItem.fromUri("android.resource://com.google.mediapipe.examples.handlandmarker/${R.raw.test_avatar_video}")
            // 위 코드는 테스트용으로, 짧은 수어영상을 넣어 테스트했음.

            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true

            exoPlayer.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_ENDED) {
                        onVideoEnded()
                    }
                }
            })
        }
    }

    private fun onVideoEnded() {
        replayButton.visibility = VISIBLE // 비디오 재생 끝났을 때 재생버튼이 보이게
        if (currentIndex < srcList.lastIndex) {
            replayButton.setOnClickListener {
                replayButton.visibility = INVISIBLE // 다시재생 버튼 클릭 시 재생버튼 감추기
                currentIndex++
                playCurrent()
            }
        } else {
            onCompleted?.invoke()
        }
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        releasePlayer()
    }
}
