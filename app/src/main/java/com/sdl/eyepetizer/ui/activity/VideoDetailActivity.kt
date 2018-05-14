package com.sdl.eyepetizer.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sdl.eyepetizer.R

class VideoDetailActivity : BaseActivity() {
    override fun layoutId(): Int {
        return R.layout.activity_video_detail
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val IMG_TRANSITION = "IMG_TRANSITION"
        val TRANSITION = "TRANSITION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)
    }
}
