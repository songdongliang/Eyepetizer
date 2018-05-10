package com.sdl.eyepetizer.connertor

import com.sdl.eyepetizer.model.HomeBean

interface FollowLoad : ILoad {

    fun setFollowInfo(issue: HomeBean.Issue)

    fun showError(errorMsg: String,errorCode: Int)
}