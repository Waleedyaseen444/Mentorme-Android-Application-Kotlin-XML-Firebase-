package com.muhammadwaleed.i210438

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import io.agora.rtc2.Constants
import io.agora.rtc2.Constants.LOG_FILTER_DEBUG
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.video.VideoCanvas
import io.agora.rtc2.video.VideoEncoderConfiguration

class Call : AppCompatActivity() {
    private lateinit var rtcEngine: RtcEngine
    private val agoraAppId = "68c966940e81407e904559670866f92d"
    private val agoraToken = "007eJxTYNi6XXX6ezPX5sJDZ/nnXj/lbfr25BklnYI/nZMvxVz58dtTgcHMItnSzMzSxCDVwtDEwDzV0sDE1NTSzNzAwswszdIoheHp19SGQEaG5xK8jIwMEAjiczKU5WcmpyYn5uQwMAAAnn4i/w=="
    private val channelName = "voicecall"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        rtcEngine = RtcEngine.create(this, agoraAppId, object : IRtcEngineEventHandler() {
            override fun onUserJoined(uid: Int, elapsed: Int) {
                runOnUiThread {
                    setupRemoteVideo(uid)
                }
            }
        })

        rtcEngine.setLogFilter(LOG_FILTER_DEBUG)
        rtcEngine.setLogFile("/path/to/logfile.log")

        val imageButton24 = findViewById<ImageButton>(R.id.imageButton24)
        imageButton24.setOnClickListener {
            joinChannel()
        }
    }

    private fun joinChannel() {
        rtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION)
        rtcEngine.enableVideo()

        rtcEngine.setVideoEncoderConfiguration(
            VideoEncoderConfiguration(
                VideoEncoderConfiguration.VD_640x360,
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE
            )
        )

        rtcEngine.joinChannel(agoraToken, channelName, "", 0)

        val localSurfaceView = RtcEngine.CreateRendererView(this)
        localSurfaceView.setZOrderMediaOverlay(true)
        val container = findViewById<FrameLayout>(R.id.local_video_view_container)
        container.addView(localSurfaceView)
        rtcEngine.setupLocalVideo(VideoCanvas(localSurfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0))

        val remoteSurfaceView = RtcEngine.CreateRendererView(this)
        remoteSurfaceView.setZOrderMediaOverlay(true)
        val remoteContainer = findViewById<FrameLayout>(R.id.remote_video_view_container)
        remoteContainer.addView(remoteSurfaceView)
        rtcEngine.setupRemoteVideo(VideoCanvas(remoteSurfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0))

        rtcEngine.setEnableSpeakerphone(true) // Enable speakerphone
    }

    private fun setupRemoteVideo(uid: Int) {
        val remoteSurfaceView = RtcEngine.CreateRendererView(this)
        remoteSurfaceView.setZOrderMediaOverlay(true)
        val remoteContainer = findViewById<FrameLayout>(R.id.remote_video_view_container)
        remoteContainer.addView(remoteSurfaceView)
        rtcEngine.setupRemoteVideo(VideoCanvas(remoteSurfaceView, VideoCanvas.RENDER_MODE_FIT, uid))
    }

    override fun onDestroy() {
        rtcEngine.leaveChannel()
        rtcEngine.destroy()
        super.onDestroy()
    }
}

private fun RtcEngine.destroy() {
    TODO("Not yet implemented")
}
