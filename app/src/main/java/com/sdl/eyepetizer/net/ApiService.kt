package com.sdl.eyepetizer.net

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
    fun getMoreHomeData(@Url url: String): Observable<HomeBean>

    /**
     * 获取全部排行榜的Info (包括title和url)
     */
    @GET("v4/rankList")
    fun getRankList():Observable<TabInfoBean>
}