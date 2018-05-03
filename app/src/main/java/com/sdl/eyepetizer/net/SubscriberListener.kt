package com.sdl.eyepetizer.net

interface SubscriberListener<T> {

    fun onNext(t: T,type: Int)

    fun onCompleted(type: Int)

    fun onError(e: Throwable, type: Int)
}