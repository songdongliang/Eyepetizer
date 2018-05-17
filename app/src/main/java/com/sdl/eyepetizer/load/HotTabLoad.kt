package com.sdl.eyepetizer.load

import com.sdl.eyepetizer.model.TabInfoBean

interface HotTabLoad: ILoad {

    fun setTabInfo(tabInfoBean: TabInfoBean)

    fun showError(errorMsg: String,errorCode: Int)
}