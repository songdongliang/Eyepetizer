package com.sdl.eyepetizer.net

import com.sdl.eyepetizer.model.CategoryBean
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.model.TabInfoBean
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
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
        return threadTransform(apiService.getFirstHomeData(num))
    }

    fun loadMoreData(url: String): Observable<HomeBean> {
        return threadTransform(apiService.getMoreHomeData(url))
    }

    fun getRankList():Observable<TabInfoBean> {
        return threadTransform(apiService.getRankList())
    }

    fun getIssueData(url: String) : Observable<HomeBean.Issue> {
        return threadTransform(apiService.getIssueData(url))
    }

    fun getFollowInfo() : Observable<HomeBean.Issue> {
        return threadTransform(apiService.getFollowInfo())
    }

    fun getCategory() : Observable<ArrayList<CategoryBean>> {
        return threadTransform(apiService.getCategory())
    }

    fun getRelatedData(id: Long): Observable<HomeBean.Issue> {
        return threadTransform(apiService.getRelatedData(id))
    }

    fun getCategoryDetailList(id: Long): Observable<HomeBean.Issue> {
        return threadTransform(apiService.getCategoryDetailList(id))
    }

    fun getHotWord(): Observable<ArrayList<String>> {
        return threadTransform(apiService.getHotWord())
    }

    fun getSearchData(query: String) : Observable<HomeBean.Issue> {
        return threadTransform(apiService.getSearchData(query))
    }

    private fun <T> threadTransform(observable: Observable<T>) : Observable<T> {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

//    private fun <T> toSubscribe(o: Observable<T>,s: Subscriber<Any>) {
//        o.subscribeOn(Schedulers.io())
//                .debounce(1L,TimeUnit.SECONDS)
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(s)
//    }

}