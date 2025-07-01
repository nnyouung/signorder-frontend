package com.google.mediapipe.examples.handlandmarker

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
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

    init {
        playerView = PlayerView(context)
        playerView.useController = false
        addView(playerView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
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
            val mediaItem = MediaItem.fromUri(url)
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
        if (currentIndex < srcList.lastIndex) {
            currentIndex++
            playCurrent()
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
