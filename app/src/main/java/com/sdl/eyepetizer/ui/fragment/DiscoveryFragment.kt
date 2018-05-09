package com.sdl.eyepetizer.ui.fragment

import android.support.v4.app.Fragment
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.util.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_hot.*

/**
 * 与热门fragment布局一致
 */
class DiscoveryFragment: BaseFragment() {

    private val tabList = ArrayList<String>()

    private val fragments = ArrayList<Fragment>()

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_hot
    }

    override fun initView() {
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(activity!!)
        StatusBarUtil.setPaddingSmart(toolbar)
        text_header_title.text = mTitle
        tabList.add(getString(R.string.follow))
        tabList.add(getString(R.string.category))


        TODO()
    }

    override fun lazyLoad() {

    }

}