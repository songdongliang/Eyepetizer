package com.sdl.eyepetizer.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sdl.eyepetizer.R

class VideoDetailActivity : AppCompatActivity() {

    companion object {
        val IMG_TRANSITION = "IMG_TRANSITION"
        val TRANSITION = "TRANSITION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
    }
}
