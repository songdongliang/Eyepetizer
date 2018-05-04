package com.sdl.eyepetizer.presenter

import com.sdl.eyepetizer.connertor.HomeLoad
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.net.NetClient

class HomePersenter: BasePresenter<HomeLoad>() {

    private var homeBean: HomeBean? = null

    private var nextPageUrl: String? = null

    fun requestHomeData(num: Int) {
        val disposable = NetClient.requestHomeData(num)
                .flatMap({homeBean ->
                    //过滤掉Banner2(包含广告,等不需要的type）具体查看接口
                    val bannerItemList = homeBean.issueList[0].itemList

                    bannerItemList.filter { item ->
                        item.type = "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        bannerItemList.remove(item)
                    }
                    this.homeBean = homeBean
                    //请求下一页数据
                    NetClient.loadMoreData(homeBean.nextPageUrl)
                }).subscribe { homeBean ->

                }
    }

    fun loadMoreData() {
        val disposable = nextPageUrl.let {

        }
    }
}