package com.sdl.eyepetizer.net

import com.sdl.eyepetizer.model.HomeBean
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface ApiService {

    @GET("v2/feed?")
    fun getFirstHomeData(@Query("num") num: Int): Observable<HomeBean>
}