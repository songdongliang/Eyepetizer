package com.sdl.eyepetizer.util

import android.content.Context
import android.content.pm.PackageManager

class AppUtil private constructor(){

    init {
        throw Error("Do not need instantiate!")
    }

    companion object {

        /**
         * 得到软件版本号
         */
        fun getVersionCode(context: Context): Int {
            var versionCode = -1
            try {
                var packageName = context.packageName
                versionCode = context.packageManager.getPackageInfo(packageName,0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return versionCode
        }

        /**
         * 得到软件显示版本信息
         */
        fun getVersionName(context: Context): String {
            var versionName = ""
            try {
                val packageName = context.packageName
                versionName = context.packageManager
                        .getPackageInfo(packageName,0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return versionName
        }

        /**
         * 获取运行的最大内存
         */
        val maxMemory: Long
            get() = Runtime.getRuntime().maxMemory() / 1024


    }

}