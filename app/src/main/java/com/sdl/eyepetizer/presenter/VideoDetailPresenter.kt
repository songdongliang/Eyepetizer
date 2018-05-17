package com.sdl.eyepetizer.presenter

import android.app.Activity
import com.sdl.eyepetizer.EyepetizerApplication
import com.sdl.eyepetizer.load.VideoDetailLoad
import com.sdl.eyepetizer.dataFormat
import com.sdl.eyepetizer.exception.ExceptionHandle
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.net.NetClient
import com.sdl.eyepetizer.showToast
import com.sdl.eyepetizer.util.DisplayManager
import com.sdl.eyepetizer.util.NetUtil

class VideoDetailPresenter: BasePresenter<VideoDetailLoad>() {

    fun loadVideoInfo(item: HomeBean.Issue.Item) {

        val playInfos = item.data.playInfo
        val netType = NetUtil.isWifi(EyepetizerApplication.context)
        if (playInfos!!.size > 1) {
            //当前网络是Wifi环境下选择高清的视频
            if (netType) {
                for (i in playInfos) {
                    if (i.type == "high") {
                        val playUrl = i.url
                        loadController?.setVideo(playUrl)
                        break
                    }
                }
            } else {
                //否则就选标清的视频
                for (i in playInfos) {
                    if (i.type == "normal") {
                        val playUrl = i.url
                        loadController?.setVideo(playUrl)
                        (loadController as Activity).showToast("本次消耗${(loadController as Activity)
                                .dataFormat(i.urlList[0].size)}流量")
                        break
                    }
                }
            }
        } else {
            loadController?.setVideo(item.data.playUrl)
        }

        //设置背景
        val backgroundUrl = item.data.cover.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! - DisplayManager.dip2px(250f)!!}x${DisplayManager.getScreenWidth()}"
        backgroundUrl.let {
            loadController?.setBackground(it)
        }
        loadController?.setVideoInfo(item)
    }

    /**
     * 请求相关的视频数据
     */
    fun requestRelatedVideo(id: Long) {
        loadController?.showLoading()
        val disposable = NetClient.getRelatedData(id)
                .subscribe({ issue ->
                    loadController?.apply {
                        dismissLoading()
                        setRecentRelatedVideo(issue.itemList)
                    }
                },{ t ->
                    loadController?.apply {
                        dismissLoading()
                        setErrorMsg(ExceptionHandle.handleException(t))
                    }
                })
        addSubscription(disposable)
    }
}