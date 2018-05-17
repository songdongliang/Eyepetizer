package com.sdl.eyepetizer.presenter

import com.sdl.eyepetizer.load.CategoryDetailLoad
import com.sdl.eyepetizer.net.NetClient

class CategoryDetailPresenter: BasePresenter<CategoryDetailLoad>() {

    private var nextPageUrl: String? = null

    fun getCategoryDetailList(id: Long) {
        val disposable = NetClient.getCategoryDetailList(id)
                .subscribe({ issue ->
                    loadController?.apply {
                        nextPageUrl = issue.nextPageUrl
                        setCategoryDetailList(issue.itemList)
                    }
                },{ t ->
                    loadController?.apply {
                        showError(t.toString())
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
                            setCategoryDetailList(issue.itemList)
                        }
                    },{ t ->
                        loadController?.apply {
                            showError(t.toString())
                        }
                    })
        }
        disposable?.let { addSubscription(it) }
    }
}