package com.sdl.eyepetizer.ui.fragment

import com.sdl.eyepetizer.adapter.CategoryAdapter
import com.sdl.eyepetizer.adapter.CategoryDetailAdapter
import com.sdl.eyepetizer.connertor.RankLoad
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.presenter.RankPresenter

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lazyLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}