package com.sdl.eyepetizer.ui.activity

import android.os.Bundle
import com.flyco.tablayout.listener.CustomTabEntity
import com.sdl.eyepetizer.R

class MainActivity : BaseActivity() {

    private val mTitles = arrayOf("每日精选","发现","热门","我的")

    //未选中时的图标
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_home_normal,R.mipmap.ic_discovery_normal,R.mipmap.ic_hot_normal,R.mipmap.ic_mine_normal)
    //被选中的图标
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_home_selected,R.mipmap.ic_discovery_selected,R.mipmap.ic_hot_selected,R.mipmap.ic_mine_selected)

    private var mTabEntities = ArrayList<CustomTabEntity>()

    /**
     * fragments索引 默认为0
     */
    private var mIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex")
        }
        super.onCreate(savedInstanceState)
        initTab()
    }

    private fun initTab() {

    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
