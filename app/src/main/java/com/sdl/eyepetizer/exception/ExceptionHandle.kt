package com.sdl.eyepetizer.exception

import com.google.gson.JsonParseException
import com.orhanobut.logger.Logger
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

class ExceptionHandle {

    companion object {
        var errorCode = ErrorStatus.UNKNOWN_ERROR
        var errorMsg = "请求失败，请稍后重试"

        var EXCEPTION = "Exception"

        fun handleException(e: Throwable): String {
            e.printStackTrace()
            when (e) {
                is SocketTimeoutException,is ConnectException,is UnknownHostException -> {
                    Logger.e(EXCEPTION,"网络连接异常: " + e.message)
                    errorMsg = "网络连接异常"
                    errorCode = ErrorStatus.NETWORK_ERROR
                }
                is JsonParseException,is JSONException,is ParseException -> {
                    Logger.e(EXCEPTION,"数据解析异常: " + e.message)
                    errorMsg = "数据解析异常"
                    errorCode = ErrorStatus.SERVER_ERROR
                }
                is IllegalArgumentException -> {
                    errorCode = ErrorStatus.SERVER_ERROR
                    errorMsg = "参数错误"
                }
                else -> {
                    errorMsg = "未知错误，可能是抛锚了吧~"
                    errorCode = ErrorStatus.UNKNOWN_ERROR
                }
            }
            return errorMsg
        }
    }
}