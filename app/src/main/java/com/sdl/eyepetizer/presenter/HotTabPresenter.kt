package com.sdl.eyepetizer.presenter

import com.sdl.eyepetizer.connertor.HotTabLoad
import com.sdl.eyepetizer.exception.ExceptionHandle
import com.sdl.eyepetizer.net.NetClient

class HotTabPresenter: BasePresenter<HotTabLoad>() {

    fun getTabInfo() {
        loadController?.showLoading()
        val disposble = NetClient.getRankList()
                .subscribe({ tabInfoBean ->
                    loadController?.setTabInfo(tabInfoBean)
                },{ throwable ->
                    loadController?.showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                })
    }
}