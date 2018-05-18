package com.sdl.eyepetizer.load

import com.sdl.eyepetizer.model.HomeBean

interface SearchLoad: ILoad {

    /**
     * 设置热门数据
     */
    fun setHotWordData(string: ArrayList<String>)

    /**
     * 设置搜索关键词返回的结果
     */
    fun setSearchResult(issue: HomeBean.Issue)

    /**
     * 关闭软键盘
     */
    fun closeSoftKeyboard()

    /**
     * 设置空View
     */
    fun setEmptyView()

    fun showError(errorMsg: String,errorCode: Int)
}