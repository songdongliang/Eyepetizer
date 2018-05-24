package com.sdl.eyepetizer.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.util.Pair
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sdl.eyepetizer.Constants
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.databinding.ItemVideoSmallCardBinding
import com.sdl.eyepetizer.durationFormat
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.ui.activity.VideoDetailActivity

class WatchHistoryAdapter: RecyclerView.Adapter<BindingViewHolder<ItemVideoSmallCardBinding>> {

    private val mContext: Context
    private val mInflater: LayoutInflater
    private var itemList: ArrayList<HomeBean.Issue.Item>

    constructor(context: Context,itemList: ArrayList<HomeBean.Issue.Item>) : super() {
        mContext = context
        mInflater = LayoutInflater.from(context)
        this.itemList = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ItemVideoSmallCardBinding> {
        var binding: ItemVideoSmallCardBinding = DataBindingUtil
                .inflate(mInflater, R.layout.item_video_small_card,parent,false)
        return BindingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ItemVideoSmallCardBinding>, position: Int) {
        val binding = holder.binding
        val item = itemList[position]
        binding?.textTitle?.text = item.data.title
        binding?.textTag?.text = "#${item.data.category} / ${durationFormat(item.data.duration)}"
        binding?.imageUrl = item.data.cover.detail
        binding?.textTitle.let {
            it!!.setTextColor(ContextCompat.getColor(it.context,android.R.color.black))
        }
        binding?.root?.setOnClickListener {
            goToVideoPlayer(mContext as Activity,binding.imageVideoSmallCard,item)
        }
    }

    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.Companion.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair<View, String>(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }
}