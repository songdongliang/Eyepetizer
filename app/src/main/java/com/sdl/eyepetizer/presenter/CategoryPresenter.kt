package com.sdl.eyepetizer.presenter

import com.sdl.eyepetizer.connertor.CategoryLoad
import com.sdl.eyepetizer.exception.ExceptionHandle
import com.sdl.eyepetizer.net.NetClient

class CategoryPresenter : BasePresenter<CategoryLoad>() {

    fun getCategoryData() {
        loadController?.showLoading()
        val disposable = NetClient.getCategory()
                .subscribe({ categoryList ->
                    loadController?.apply {
                        dismissLoading()
                        showCategory(categoryList)
                    }
                },{ throwable ->
                    loadController?.apply {
                        showError(ExceptionHandle.handleException(throwable),ExceptionHandle.errorCode)
                    }
                })

        addSubscription(disposable)
    }
}