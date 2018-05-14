package com.sdl.eyepetizer.util

import android.content.Context
import android.net.ConnectivityManager
import java.net.NetworkInterface
import java.net.SocketException

class NetUtil {

    companion object {

        var NET_OK = 1 //NetworkAvailable
        var NET_TIMEOUT = 2 //no NetworkAvailable
        var NET_NOT_PREPARE = 3 //Net no ready
        var NET_ERROR = 4 //net error

        private val TIMEOUT = 3000

        @JvmStatic
        fun isNetworkAvaiable(context: Context): Boolean {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
            val info = manager.activeNetworkInfo
            return info != null && info.isAvailable
        }

        @JvmStatic
        fun getLocalIpAddress(): String {
            var ret = ""
            try {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val enumIpAddress = en.nextElement().inetAddresses
                    while (enumIpAddress.hasMoreElements()) {
                        val netAddress = enumIpAddress.nextElement()
                        if (!netAddress.isLoopbackAddress) {
                            ret = netAddress.hostAddress.toString()
                        }
                    }
                }
            } catch (e: SocketException) {
                e.printStackTrace()
            }
            return ret
        }

        /**
         * 判断是否是wifi
         */
        @JvmStatic
        fun isWifi(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetInfo = connectivityManager.activeNetworkInfo
            return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_WIFI
        }
    }
}