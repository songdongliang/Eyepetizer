package com.sdl.eyepetizer

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.orhanobut.logger.*
import com.sdl.eyepetizer.util.DisplayManager
import kotlin.properties.Delegates

class EyepetizerApplication: MultiDexApplication() {

    companion object {

        var context: Context by Delegates.notNull<Context>()
            private set
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initLoggerConfig()
        DisplayManager.init(this)
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