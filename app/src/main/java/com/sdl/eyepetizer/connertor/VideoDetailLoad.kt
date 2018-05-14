package com.sdl.eyepetizer.connertor

import com.sdl.eyepetizer.model.HomeBean

interface VideoDetailLoad: ILoad {

    /**
     * 设置播放源
     */
    fun setVideo(url: String)

    /**
     * 设置视频信息
     */
    fun setVideoInfo(item: HomeBean.Issue.Item)

    /**
     * 设置背景
     */
    fun setBackground(url: String)

    /**
     * 设置最新相关视频
     */
    fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>)

    /**
     * 设置错误信息
     */
    fun setErrorMsg(errorMsg: String)
}