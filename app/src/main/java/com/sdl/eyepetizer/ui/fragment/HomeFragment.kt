package com.sdl.eyepetizer.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.adapter.HomeAdapter
import com.sdl.eyepetizer.connertor.HomeLoad
import com.sdl.eyepetizer.exception.ErrorStatus
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.presenter.HomePersenter
import com.sdl.eyepetizer.showToast
import com.sdl.eyepetizer.ui.activity.SearchActivity
import com.sdl.eyepetizer.util.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment: BaseFragment(),HomeLoad {

    private val mPresenter by lazy {
        HomePersenter()
    }

    private var mTitle: String? = null

    private var num: Int = 1

    private var mHomeAdapter: HomeAdapter? = null

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
        mPresenter.attachLoad(this)
        //内容跟随偏移
        smart_refresh_layout.setEnableHeaderTranslationContent(true)
        smart_refresh_layout.setOnRefreshListener{
            isRefresh = true
            mPresenter.requestHomeData(num)
        }
        mMaterialHeader = smart_refresh_layout.refreshHeader as MaterialHeader
        //打开下拉刷新区域块背景
        mMaterialHeader?.setShowBezierWave(true)
        //设置下拉刷新主题颜色
        smart_refresh_layout.setPrimaryColorsId(R.color.color_light_black,R.color.color_item_title)

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = mRecyclerView.childCount
                    val itemCount = mRecyclerView.layoutManager.itemCount
                    val firstVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (firstVisibleItem + childCount == itemCount) {
                        if (!loadingMore) {
                            loadingMore = true
                            mPresenter.loadMoreData()
                        }
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    //背景设置为透明
                    toolbar.setBackgroundColor(ContextCompat.getColor(context!!,R.color.color_translucent))
                    image_search.setImageResource(R.mipmap.ic_action_search_white)
                    text_header_title.text = ""
                } else {
                    if (mHomeAdapter?.items!!.size > 1) {
                        toolbar.setBackgroundColor(ContextCompat.getColor(context!!,R.color.color_title_bg))
                        image_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList = mHomeAdapter?.items
                        val item = itemList!![currentVisibleItemPosition + mHomeAdapter!!.bannerItemSize - 1]
                        if (item.type == "textHeader") {
                            text_header_title.text = item.data!!.text
                        } else {
                            text_header_title.text = simpleDateFormat.format(item.data!!.date)
                        }
                    }
                }
            }
        })

        image_search.setOnClickListener {
            openSerarchActivity()
        }

        mLayoutStatusView = multipleStatusView

        //状态栏透明和间距处理
        StatusBarUtil.darkMode(activity as Activity)
        StatusBarUtil.setPaddingSmart(toolbar)
    }

    override fun lazyLoad() {
        mPresenter.requestHomeData(num)
    }

    override fun setHomeData(homeBean: HomeBean) {
        mLayoutStatusView?.showContent()
        Logger.d(homeBean)
        mHomeAdapter = HomeAdapter(activity as Context,homeBean.issueList[0].itemList)
        mHomeAdapter?.setBannerSize(homeBean.issueList[0].count)

        mRecyclerView.adapter = mHomeAdapter
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mHomeAdapter?.addItemData(itemList)
    }

    override fun showError(msg: String, errorCode: Int) {
        context?.showToast(msg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun showLoading() {
        if (!isRefresh) {
            isRefresh = false
            mLayoutStatusView?.showLoading()
        }
    }

    override fun dismissLoading() {
        smart_refresh_layout.finishRefresh()
    }

    fun openSerarchActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!,image_search,image_search.transitionName)
            startActivity(Intent(activity,SearchActivity::class.java),options.toBundle())
        } else {
            startActivity(Intent(activity,SearchActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachLoad()
    }
}