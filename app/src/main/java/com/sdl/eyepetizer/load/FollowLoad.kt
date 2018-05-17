package com.sdl.eyepetizer.load

import com.sdl.eyepetizer.model.HomeBean

interface FollowLoad : ILoad {

    fun setFollowInfo(issue: HomeBean.Issue)

    fun showError(errorMsg: String,errorCode: Int)
}