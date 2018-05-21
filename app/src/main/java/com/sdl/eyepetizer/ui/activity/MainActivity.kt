package com.sdl.eyepetizer.ui.activity

import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.model.TabEntity
import com.sdl.eyepetizer.showToast
import com.sdl.eyepetizer.ui.fragment.DiscoveryFragment
import com.sdl.eyepetizer.ui.fragment.HomeFragment
import com.sdl.eyepetizer.ui.fragment.HotFragment
import com.sdl.eyepetizer.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

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

    private var mHomeFragment: HomeFragment? = null
    private var mDiscoveryFragment: DiscoveryFragment? = null
    private var mHotFragment: HotFragment? = null
    private var mMineFragment: MineFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex")
        }
        super.onCreate(savedInstanceState)
        initTab()
        switchFragment(mIndex)
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    private fun initTab() {
        (0 until mTitles.size).mapTo(mTabEntities) {
            TabEntity(mTitles[it],mIconSelectIds[it],mIconUnSelectIds[it])
        }
        //为Tab赋值
        tab_layout.setTabData(mTabEntities)
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {

            }
        })
    }

    /**
     * 切换fragment
     * @param position 下标
     */
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            //首页
            0 -> mHomeFragment ?.let {
                transaction.show(it)
            } ?: HomeFragment.getInstance(mTitles[position]).let {
                mHomeFragment = it
                transaction.add(R.id.container,it,"home")
            }
            //发现
            1 -> mDiscoveryFragment?.let {
                transaction.show(it)
            } ?: DiscoveryFragment.getInstance(mTitles[position]).let {
                mDiscoveryFragment = it
                transaction.add(R.id.container,it,"discovery")
            }
            //热门
            2 -> mHotFragment?.let {
                transaction.show(it)
            } ?: HotFragment.getInstance(mTitles[position]).let {
                mHotFragment = it
                transaction.add(R.id.container,it,"hot")
            }
            //我的
            3 -> mMineFragment?.let {
                transaction.show(it)
            } ?: MineFragment.getInstance(mTitles[position]).let{
                mMineFragment = it
                transaction.add(R.id.container,it,"mine")
            }
            else -> {

            }
        }
        mIndex = position
        tab_layout.currentTab = mIndex
        transaction.commitAllowingStateLoss()
    }

    private fun hideFragments(transaction: FragmentTransaction?) {
        mHomeFragment?.let { transaction?.hide(it) }
        mDiscoveryFragment?.let { transaction?.hide(it) }
        mHotFragment?.let { transaction?.hide(it) }
        mMineFragment?.let { transaction?.hide(it) }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (tab_layout != null) {
            outState?.putInt("currTabIndex",mIndex)
        }
    }

    private var mExitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                showToast(getString(R.string.back_info))
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun start() {

    }

}
