package com.sdl.eyepetizer.net

import com.sdl.eyepetizer.model.CategoryBean
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.model.TabInfoBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Observable<HomeBean>

    @GET
    fun getMoreHomeData(@Url url: String) : Observable<HomeBean>

    /**
     * 获取全部排行榜的Info (包括title和url)
     */
    @GET("v4/rankList")
    fun getRankList() : Observable<TabInfoBean>

    /**
     * 关注
     */
    @GET("v4/tabs/follow")
    fun getFollowInfo() : Observable<HomeBean.Issue>

    /**
     * 获取更多的Issue
     */
    @GET
    fun getIssueData(@Url url : String) : Observable<HomeBean.Issue>

    /**
     * 获取分类
     */
    @GET("v4/categories")
    fun getCategory() : Observable<ArrayList<CategoryBean>>

    /**
     * 根据item id获取相关视频
     */
    @GET("v4/video/related?")
    fun getRelatedData(@Query("id") id: Long): Observable<HomeBean.Issue>
}