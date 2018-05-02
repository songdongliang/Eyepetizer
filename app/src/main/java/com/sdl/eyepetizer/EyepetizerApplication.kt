package com.sdl.eyepetizer

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

class EyepetizerApplication: Application() {



    override fun onCreate() {
        super.onCreate()
        initLoggerConfig()
    }

    fun initLoggerConfig() {
        val formatStrategy: FormatStrategy =
                PrettyFormatStrategy.newBuilder()
                        .showThreadInfo(false) //隐藏线程信息，默认显示
                        .methodCount(0) //决定打印多少行(每一行代表一个方法)
                        .methodOffset(7) //
                        .tag("eyepetizer_tag")
                        .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}