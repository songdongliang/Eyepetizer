package com.sdl.eyepetizer.ui.fragment

import android.support.v4.app.Fragment
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.connertor.HotTabLoad
import com.sdl.eyepetizer.exception.ErrorStatus
import com.sdl.eyepetizer.model.TabInfoBean
import com.sdl.eyepetizer.presenter.HotTabPresenter
import com.sdl.eyepetizer.showToast
import com.sdl.eyepetizer.util.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_hot.*

class HotFragment: BaseFragment(),HotTabLoad {

    private val mPresenter by lazy {
        HotTabPresenter()
    }

    private var mTitle: String? = null

    private val mTabTitleList = ArrayList<String>()

    private val mFragmentList = ArrayList<Fragment>()

    companion object {
        fun getInstance(title: String): HotFragment {
            val fragment = HotFragment()
            fragment.mTitle = title
            return fragment
        }
    }

    init {
        mPresenter.attachLoad(this)
    }

    override fun setTabInfo(tabInfoBean: TabInfoBean) {
        mLayoutStatusView?.showContent()

        tabInfoBean.tabInfo.tabList.mapTo(mTabTitleList) {it.name}
        tabInfoBean.tabInfo.tabList.mapTo(mFragmentList) {RankFragment.getInstance(it.apiUrl)}

        mTabLayout.setupWithViewPager(mViewPager)
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

    override fun getLayoutId(): Int {
        return R.layout.fragment_hot
    }

    override fun initView() {
        mLayoutStatusView = multipleStatusView
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(activity!!)
        StatusBarUtil.setPaddingSmart(toolbar)
    }

    override fun lazyLoad() {
        mPresenter.getTabInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachLoad()
    }
}