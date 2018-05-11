package com.sdl.eyepetizer.connertor

import com.sdl.eyepetizer.model.HomeBean

interface RankLoad: ILoad {

    fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>)

    fun showError(errorMsg: String,errorCode: Int)
}