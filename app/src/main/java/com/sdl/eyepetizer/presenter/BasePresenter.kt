package com.sdl.eyepetizer.presenter

import com.sdl.eyepetizer.load.ILoad
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<T: ILoad> {

    var loadController: T? = null
        private set

    private var compositeDisposable = CompositeDisposable()

    fun attachLoad(loadController: T) {
        this.loadController = loadController
    }

    fun detachLoad() {
        loadController = null

        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }
    }

    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}