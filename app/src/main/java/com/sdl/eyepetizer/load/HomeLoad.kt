package com.sdl.eyepetizer.load

import com.sdl.eyepetizer.model.HomeBean

interface HomeLoad: ILoad {

    /**
     * 设置第一次请求的数据
     */
    fun setHomeData(homeBean: HomeBean)

    /**
     * 设置加载更多的数据
     */
    fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>)

    /**
     * 显示错误信息
     */
    fun showError(msg: String,errorCode: Int)
}