package com.sdl.eyepetizer.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.classic.common.MultipleStatusView

abstract class BaseActivity: AppCompatActivity() {

    /**
     * 多种状态的View的切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        initView()
        initData()
        start()
        initListener()
    }

    private fun initListener() {
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        start()
    }

    abstract fun layoutId(): Int

    /**
     * 初始化数据
     */
    abstract fun initData()

    abstract fun initView()

    abstract fun start()
}