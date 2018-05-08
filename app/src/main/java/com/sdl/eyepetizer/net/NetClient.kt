package com.sdl.eyepetizer.net

import com.sdl.eyepetizer.model.HomeBean
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object NetClient {

    private val TAG: String = "NetClient"

    private val DEFAULT_TIMEOUT = 20L

    private var apiService: ApiService

    init {
        //创建一个OkHttpClient并设置超时时间
        var builder = OkHttpClient.Builder()
        builder.connectTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
        var retrofit = Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                //TODO 这里baseUrl要替换成开眼的
                .baseUrl("http://baobab.kaiyanapp.com/api/")
                .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    fun requestHomeData(num: Int): Observable<HomeBean> {
        return apiService.getFirstHomeData(num)
    }

    fun loadMoreData(url: String): Observable<HomeBean> {
        return apiService.getMoreHomeData(url)
    }

//    private fun <T> toSubscribe(o: Observable<T>,s: Subscriber<Any>) {
//        o.subscribeOn(Schedulers.io())
//                .debounce(1L,TimeUnit.SECONDS)
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(s)
//    }

}