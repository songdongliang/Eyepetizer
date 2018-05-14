package com.sdl.eyepetizer.presenter

import com.sdl.eyepetizer.EyepetizerApplication
import com.sdl.eyepetizer.connertor.VideoDetailLoad
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.util.NetUtil

class VideoDetailPresenter: BasePresenter<VideoDetailLoad>() {

    fun loadVideoInfo(item: HomeBean.Issue.Item) {

        val playInfos = item.data.playInfo
        val netType = NetUtil.isWifi(EyepetizerApplication.context)
        if (playInfos!!.size > 1) {
            //当前网络是Wifi环境下选择高清的视频
            if (netType) {
                
            }
        }
    }
}