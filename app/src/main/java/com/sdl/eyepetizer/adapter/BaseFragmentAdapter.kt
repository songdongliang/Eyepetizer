package com.sdl.eyepetizer.adapter

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class BaseFragmentAdapter : FragmentPagerAdapter {

    private var fragmentList: List<Fragment>? = ArrayList()
    private var mTitles: List<String>? = null

    constructor(fm: FragmentManager,fragmentList: List<Fragment>) : super(fm) {
        this.fragmentList = fragmentList
    }

    constructor(fm: FragmentManager,fragmentList: List<Fragment>,mTitles: List<String>) : super(fm) {
        this.mTitles = mTitles
        setFragments(fm,fragmentList)
    }

    @SuppressLint("CommitTransaction")
    private fun setFragments(fm: FragmentManager, fragments: List<Fragment>) {
        if (this.fragmentList != null) {
            val ft = fm.beginTransaction()
            for (f in this.fragmentList!!) {
                ft!!.remove(f)
            }
            ft!!.commitAllowingStateLoss()
            fm.executePendingTransactions()
        }
        this.fragmentList = fragments
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList!![position]
    }

    override fun getCount(): Int {
        return fragmentList!!.size
    }


}