package com.sdl.eyepetizer.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.adapter.FollowAdapter
import com.sdl.eyepetizer.load.FollowLoad
import com.sdl.eyepetizer.exception.ErrorStatus
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.presenter.FollowPresenter
import com.sdl.eyepetizer.showToast
import kotlinx.android.synthetic.main.layout_recyclerview.*

class FollowFragment: BaseFragment(),FollowLoad {

    private var mTitle: String? = null

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private var loadingMore = false

    private val mPresenter by lazy {
        FollowPresenter()
    }

    private val mFollowAdapter by lazy {
        FollowAdapter(activity!!,itemList)
    }

    companion object {
        fun getInstance(title: String): FollowFragment {
            val fragment = FollowFragment()
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_recyclerview
    }

    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mFollowAdapter

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView.layoutManager.itemCount
                val lastVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                    loadingMore = true
                    mPresenter.loadMoreData()
                }
            }
        })

        mLayoutStatusView = multipleStatusView
    }

    override fun lazyLoad() {
        mPresenter.requestFollowList()
    }

    override fun setFollowInfo(issue: HomeBean.Issue) {
        loadingMore = false
        itemList = issue.itemList
        mFollowAdapter.addList(itemList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        context?.showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachLoad()
    }
}