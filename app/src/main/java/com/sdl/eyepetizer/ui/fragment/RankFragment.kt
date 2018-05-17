package com.sdl.eyepetizer.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.adapter.CategoryDetailAdapter
import com.sdl.eyepetizer.load.RankLoad
import com.sdl.eyepetizer.exception.ErrorStatus
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.presenter.RankPresenter
import com.sdl.eyepetizer.showToast
import kotlinx.android.synthetic.main.layout_recyclerview.*

class RankFragment: BaseFragment(),RankLoad {

    private val mPresenter by lazy {
        RankPresenter()
    }

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private val mAdapter by lazy {
        CategoryDetailAdapter(context!!,itemList)
    }

    private var apiUrl: String? = null

    companion object {
        fun getInstance(apiUrl: String): RankFragment {
            val fragment = RankFragment()
            fragment.apiUrl = apiUrl
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_recyclerview
    }

    override fun initView() {
        mPresenter.attachLoad(this)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mAdapter
        mLayoutStatusView = multipleStatusView
    }

    override fun lazyLoad() {
        if (!apiUrl.isNullOrEmpty()) {
            mPresenter.requestRankList(apiUrl!!)
        }
    }

    override fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>) {
        mLayoutStatusView?.showContent()
        mAdapter.addList(itemList)
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

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachLoad()
    }
}