package com.sdl.eyepetizer.presenter

import com.sdl.eyepetizer.exception.ExceptionHandle
import com.sdl.eyepetizer.load.SearchLoad
import com.sdl.eyepetizer.net.NetClient

class SearchPresenter: BasePresenter<SearchLoad>() {

    private var nextPageUrl: String? = null

    /**
     * 获取热门关键词
     */
    fun requestHotWordData() {
        loadController?.apply {
            closeSoftKeyboard()
            showLoading()
        }
        val disposable = NetClient.getHotWord()
                .subscribe({
                    loadController?.apply {
                        setHotWordData(it)
                    }
                },{
                    loadController?.apply {
                        showError(ExceptionHandle.handleException(it),ExceptionHandle.errorCode)
                    }
                })
    }

    /**
     * 查询关键词
     */
    fun querySearchData(words: String) {
        loadController?.apply {
            closeSoftKeyboard()
            showLoading()
        }
        addSubscription(disposable = NetClient.getSearchData(words)
                .subscribe({ issue ->
                    loadController?.apply {
                        dismissLoading()
                        if (issue.count > 0 && issue.itemList.size > 0) {
                            nextPageUrl = issue.nextPageUrl
                            setSearchResult(issue)
                        }
                    }
                },{
                    loadController?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(it),ExceptionHandle.errorCode)
                    }
                }))
    }

    /**
     * 加载更多数据
     */
    fun loadMoreData() {
        nextPageUrl?.let {
            addSubscription(disposable = NetClient.getIssueData(it)
                    .subscribe({
                        loadController?.apply {
                            nextPageUrl = it.nextPageUrl
                            setSearchResult(it)
                        }
                    },{
                        loadController?.apply {
                            showError(ExceptionHandle.handleException(it),ExceptionHandle.errorCode)
                        }
                    }))
        }
    }
}