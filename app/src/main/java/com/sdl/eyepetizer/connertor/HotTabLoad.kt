package com.sdl.eyepetizer.connertor

import com.sdl.eyepetizer.model.TabInfoBean

interface HotTabLoad: ILoad {

    fun setTabInfo(tabInfoBean: TabInfoBean)

    fun showError(errorMsg: String,errorCode: Int)
}