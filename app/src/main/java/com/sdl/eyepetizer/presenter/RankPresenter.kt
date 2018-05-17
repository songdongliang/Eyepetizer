package com.sdl.eyepetizer.presenter

import com.sdl.eyepetizer.load.RankLoad
import com.sdl.eyepetizer.exception.ExceptionHandle
import com.sdl.eyepetizer.net.NetClient

class RankPresenter: BasePresenter<RankLoad>() {

    /**
     * 请求排行榜数据
     */
    fun requestRankList(apiUrl: String) {
        loadController?.showLoading()
        val disposable = NetClient.getIssueData(apiUrl)
                .subscribe({ issue ->
                    loadController?.apply {
                        dismissLoading()
                        setRankList(issue.itemList)
                    }
                },{ throwable ->
                    loadController?.apply {
                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }
}