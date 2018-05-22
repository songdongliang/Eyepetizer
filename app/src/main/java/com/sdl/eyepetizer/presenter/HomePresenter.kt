package com.sdl.eyepetizer.presenter

import com.sdl.eyepetizer.load.HomeLoad
import com.sdl.eyepetizer.exception.ExceptionHandle
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.net.NetClient

class HomePresenter: BasePresenter<HomeLoad>() {

    private var homeBean: HomeBean? = null

    private var nextPageUrl: String? = null

    fun requestHomeData(num: Int) {
        //显示加载
        loadController?.showLoading()
        val disposable = NetClient.requestHomeData(num)
                .flatMap({homeBean ->
                    //过滤掉Banner2(包含广告,等不需要的type）具体查看接口
                    val bannerItemList = homeBean.issueList[0].itemList

                    bannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        bannerItemList.remove(item)
                    }
                    this.homeBean = homeBean
                    //请求下一页数据
                    NetClient.loadMoreData(homeBean.nextPageUrl)
                })
                .subscribe ({ homeBean ->
                    loadController?.apply {
                        dismissLoading()
                        nextPageUrl = homeBean.nextPageUrl
                        //过滤掉Banner2(包含广告,等不需要的Type)
                        val newBannerItemList = homeBean.issueList[0].itemList

                        newBannerItemList.filter { item ->
                            item.type == "banner2" || item.type == "horizontalScrollCard"
                        }.forEach { item ->
                            //移除item
                            newBannerItemList.remove(item)
                        }
                        //重新赋值Banner长度
//                        this@HomePresenter.homeBean!!.issueList[0].count = homeBean.issueList[0].itemList.size
                        //赋值过滤后的数据 + banner数据
                        this@HomePresenter.homeBean!!.issueList[0].itemList.addAll(newBannerItemList)
                        setHomeData(this@HomePresenter.homeBean!!)
                    }
                }, { t ->
                    loadController?.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    fun loadMoreData() {
        val disposable = nextPageUrl.let {
            NetClient.loadMoreData(it!!)
                    .subscribe({ homeBean ->
                        loadController?.apply {
                            //过滤掉Banner2（包含广告，等不需要的Type)
                            val newItemList = homeBean.issueList[0].itemList

                            newItemList.filter {item ->
                                item.type == "banner2" || item.type == "horizontalScrollCard"
                            }.forEach { item ->
                                newItemList.remove(item)
                            }

                            nextPageUrl = homeBean.nextPageUrl
                            setMoreData(newItemList)
                        }
                    },{ t ->
                        loadController?.apply {
                            showError(ExceptionHandle.handleException(t),ExceptionHandle.errorCode)
                        }
                    })
        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }
}