package com.sdl.eyepetizer.ui.activity

import android.support.v7.widget.LinearLayoutManager
import com.sdl.eyepetizer.Constants
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.adapter.WatchHistoryAdapter
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.util.StatusBarUtil
import com.sdl.eyepetizer.util.WatchHistoryUtil
import kotlinx.android.synthetic.main.activity_watch_history.*
import java.util.*

class WatchHistoryActivity : BaseActivity() {

    private var items = ArrayList<HomeBean.Issue.Item>()

    companion object {
        private val HISTORY_MAX = 20
    }

    override fun layoutId(): Int {
        return R.layout.activity_watch_history
    }

    override fun initData() {
        multipleStatusView.showLoading()
        items = queryWatchHistory()

        val adapter = WatchHistoryAdapter(this,items)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = adapter
        if (items.size > 1) {
            multipleStatusView.showContent()
        } else {
            multipleStatusView.showEmpty()
        }

    }

    override fun initView() {
        toolbar.setNavigationOnClickListener { finish() }

        //状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(toolbar)
        StatusBarUtil.setPaddingSmart(mRecyclerView)
    }

    override fun start() {

    }

    private fun queryWatchHistory(): ArrayList<HomeBean.Issue.Item> {
        val watchList = ArrayList<HomeBean.Issue.Item>()
        val histories = WatchHistoryUtil.getAll(Constants.FILE_WATCH_HISTORY_NAME,this)
        //将key排序升序
        val keys = histories.keys.toTypedArray()
        Arrays.sort(keys)
        val keyLength = keys.size
        val historyLength = if (keyLength > HISTORY_MAX) HISTORY_MAX else keyLength
        (1..historyLength).mapTo(watchList) {
            WatchHistoryUtil.getObject(Constants.FILE_WATCH_HISTORY_NAME,this,keys[keyLength - it])
                    as HomeBean.Issue.Item
        }
        return watchList
    }

}
