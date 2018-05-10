package com.sdl.eyepetizer.presenter

import com.sdl.eyepetizer.connertor.FollowLoad
import com.sdl.eyepetizer.exception.ExceptionHandle
import com.sdl.eyepetizer.net.NetClient

class FollowPresenter: BasePresenter<FollowLoad>() {

    private var nextPageUrl: String? = null

    fun requestFollowList() {
        loadController?.showLoading()
        var disposable = NetClient.getFollowInfo()
                .subscribe({ issue ->
                    loadController?.apply {
                        dismissLoading()
                        nextPageUrl = issue.nextPageUrl
                        setFollowInfo(issue)
                    }
                },{ throwable ->
                    loadController?.apply {
                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            NetClient.getIssueData(it)
                    .subscribe({ issue ->
                        loadController?.apply {
                            nextPageUrl = issue.nextPageUrl
                            setFollowInfo(issue)
                        }
                    },{
                        loadController?.apply {
                            showError(ExceptionHandle.handleException(it),ExceptionHandle.errorCode)
                        }
                    })
        }
        addSubscription(disposable!!)
    }
}