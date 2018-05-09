package com.sdl.eyepetizer.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.showToast
import com.sdl.eyepetizer.ui.activity.AboutActivity
import com.sdl.eyepetizer.ui.activity.ProfileHomePageActivity
import com.sdl.eyepetizer.ui.activity.WatchHistoryActivity
import com.sdl.eyepetizer.util.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment: BaseFragment(), View.OnClickListener {

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): MineFragment {
            val fragment = MineFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView() {
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(activity!!)
        StatusBarUtil.setPaddingSmart(toolbar)

        text_view_homepage.setOnClickListener(this)
        image_avatar.setOnClickListener(this)
        image_about.setOnClickListener(this)

        text_collection.setOnClickListener(this)
        text_comment.setOnClickListener(this)

        text_mine_message.setOnClickListener(this)
        text_mine_attention.setOnClickListener(this)
        text_mine_cache.setOnClickListener(this)
        text_watch_history.setOnClickListener(this)
        text_feedback.setOnClickListener(this)
    }

    override fun lazyLoad() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.image_avatar,R.id.text_view_homepage -> {
                startActivity(Intent(context,ProfileHomePageActivity::class.java))
            }
            R.id.image_about -> startActivity(Intent(context,AboutActivity::class.java))
            R.id.text_collection -> context?.showToast(R.string.collection)
            R.id.text_comment -> context?.showToast(R.string.comment)
            R.id.text_mine_message -> context?.showToast(R.string.mine_message)
            R.id.text_mine_attention -> context?.showToast(R.string.mine_attention)
            R.id.text_mine_cache -> context?.showToast(R.string.mine_cache)
            R.id.text_feedback -> context?.showToast(R.string.feedback)
            R.id.text_watch_history -> startActivity(Intent(context,WatchHistoryActivity::class.java))
        }
    }
}