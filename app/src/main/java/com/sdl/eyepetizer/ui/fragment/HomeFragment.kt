package com.sdl.eyepetizer.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.scwang.smartrefresh.header.MaterialHeader
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.connertor.HomeLoad
import com.sdl.eyepetizer.model.HomeBean
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment: BaseFragment(),HomeLoad {
    override fun setHomeData(homeBean: HomeBean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(msg: String, errorCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var mTitle: String? = null

    private var num: Int = 1

    private var loadingMore = false

    private var isRefresh = false

    private var mMaterialHeader: MaterialHeader? = null

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
    }

    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        //内容跟随偏移
        smart_refresh_layout.setEnableHeaderTranslationContent(true)
        smart_refresh_layout.setOnRefreshListener{
            isRefresh = true

        }
    }

    override fun lazyLoad() {

    }

    fun showLoading() {
        if (!isRefresh) {
            isRefresh = false
            mLayoutStatusView?.showLoading()
        }
    }

    fun dismissLoading() {
        smart_refresh_layout.finishRefresh()
    }

}