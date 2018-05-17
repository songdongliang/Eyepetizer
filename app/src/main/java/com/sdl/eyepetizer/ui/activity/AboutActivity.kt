package com.sdl.eyepetizer.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.util.AppUtil
import com.sdl.eyepetizer.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_about
    }

    override fun initData() {

    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(toolbar)
        text_version_name.text = "v${AppUtil.getVersionName(this)}"
        //返回监听
        toolbar.setNavigationOnClickListener { finish() }
        //访问github
        layout_gitHub.setOnClickListener {
            val uri = Uri.parse("https://github.com/songdongliang/Eyepetizer")
            val intent = Intent(Intent.ACTION_VIEW,uri)
            startActivity(intent)
        }
    }

    override fun start() {

    }


}
