package com.sdl.eyepetizer.net

import com.alibaba.fastjson.JSON
import com.google.gson.JsonSyntaxException
import com.orhanobut.logger.Logger
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import retrofit2.adapter.rxjava2.HttpException
import java.net.SocketTimeoutException

class NetSubscriber<T>: Subscriber<T> {
    override fun onSubscribe(s: Subscription?) {

    }

    private var type: Int = -1

    private var subscriberListener: SubscriberListener<T>? = null

    constructor(subscriberListener: SubscriberListener<T>,type: Int) {
        this.type = type
        this.subscriberListener = subscriberListener
    }

    override fun onNext(t: T) {
        var jsonString: String = JSON.toJSONString(t)
        Logger.d(jsonString)
        subscriberListener!!.onNext(t,type)
    }

    override fun onComplete() {
        Logger.d("onCompleted %d",type)
        subscriberListener!!.onCompleted(type)
    }

    override fun onError(e: Throwable?) {
        e?.printStackTrace()
        when (e) {
            is SocketTimeoutException -> Logger.e("请求超时")
            is HttpException -> Logger.e("没有该接口")
            is JsonSyntaxException -> Logger.e("类型转换错误")
        }
        subscriberListener!!.onError(e!!,type)
    }


}