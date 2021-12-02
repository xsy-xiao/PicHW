package com.domker.study.androidstudy

import android.graphics.PixelFormat
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.VideoView
import kotlinx.android.synthetic.main.layout_video.*

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_video)
        buttonPause.setOnClickListener { videoView.pause() }
        buttonPlay.setOnClickListener { videoView.start() }
        videoView.holder.setFormat(PixelFormat.TRANSPARENT)
        videoView.setZOrderOnTop(true)
        videoView.setVideoPath(getVideoPath(R.raw.big_buck_bunny))
    }

    private fun getVideoPath(resId: Int): String {
        return "android.resource://" + this.packageName + "/" + resId
    }
}