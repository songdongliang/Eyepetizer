package com.sdl.eyepetizer.load

import com.sdl.eyepetizer.model.HomeBean

interface RankLoad: ILoad {

    fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>)

    fun showError(errorMsg: String,errorCode: Int)
}