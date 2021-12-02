package com.domker.study.androidstudy

import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import android.widget.SeekBar
import kotlinx.android.synthetic.main.layout_media_player.*
import java.io.IOException

class MediaPlayerActivity : AppCompatActivity() {
    private val player: MediaPlayer by lazy {
        MediaPlayer()
    }
    private lateinit var runnable:Runnable
    private var handler: Handler = Handler()
    private var holder: SurfaceHolder? = null
    private var flag = VIDEO_PAUSE
    private var length = 0L

    companion object{
         val VIDEO_ON = true
        val VIDEO_PAUSE = false
        val PROGRESS_CHANGED = 0
    }
   
    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "MediaPlayer"
        setContentView(R.layout.layout_media_player)
        try {
            player.setDataSource(resources.openRawResourceFd(R.raw.big_buck_bunny))
            length = resources.openRawResourceFd(R.raw.big_buck_bunny).declaredLength
            holder = surfaceView.holder
            holder?.setFormat(PixelFormat.TRANSPARENT)
            holder?.addCallback(PlayerCallBack())
            player.prepare()
            player.setOnPreparedListener { // 自动播放
                player.start()
                player.isLooping = true
            }
            player.setOnBufferingUpdateListener { mp, percent -> println(percent) }

        } catch (e: IOException) {
            e.printStackTrace()
        }
        buttonPlay.setOnClickListener { startOrPause() }
        initializeSeekBar()

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                player.seekTo(seekBar!!.progress * 1000)
            }
        })


    }
    private fun initializeSeekBar(){
        seekBar.max = player.duration/1000

        runnable = Runnable {
            seekBar.progress = player.currentPosition/1000


           handler.postDelayed(runnable, 1000)
        }

        handler.postDelayed(runnable, 1000)
    }


    private fun startOrPause() {
        if (flag) {
            player.pause()
            flag = VIDEO_PAUSE
            buttonPlay.text = "||"
        } else {
            player.start()
            flag = VIDEO_ON
            buttonPlay.text = "▶"
        }
    }

    override fun onPause() {
        super.onPause()
        player.stop()
        player.release()
    }

    private inner class PlayerCallBack : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            player.setDisplay(holder)
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
        override fun surfaceDestroyed(holder: SurfaceHolder) {}
    }


}

