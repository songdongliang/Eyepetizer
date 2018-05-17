package com.sdl.eyepetizer.load

import com.sdl.eyepetizer.model.HomeBean

interface CategoryDetailLoad: ILoad {

    fun setCategoryDetailList(itemList: ArrayList<HomeBean.Issue.Item>)

    fun showError(errorMsg: String)
}