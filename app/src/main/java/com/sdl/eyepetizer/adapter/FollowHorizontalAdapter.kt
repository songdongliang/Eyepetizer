package com.sdl.eyepetizer.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.orhanobut.logger.Logger
import com.sdl.eyepetizer.Constants
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.databinding.ItemFollowHorizontalBinding
import com.sdl.eyepetizer.durationFormat
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.ui.activity.VideoDetailActivity

class FollowHorizontalAdapter() : RecyclerView.Adapter<BindingViewHolder<ItemFollowHorizontalBinding>>() {

    private var items: ArrayList<HomeBean.Issue.Item>? = null

    private var mLayoutInflater: LayoutInflater? = null

    constructor(context: Context,items: ArrayList<HomeBean.Issue.Item>) : this(){
        mLayoutInflater = LayoutInflater.from(context)
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ItemFollowHorizontalBinding> {
        var binding: ItemFollowHorizontalBinding = DataBindingUtil
                .inflate(mLayoutInflater!!, R.layout.item_follow_horizontal,parent,false)
        return BindingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ItemFollowHorizontalBinding>, position: Int) {
        val itemData = items!![position].data

        holder.binding!!.itemData = itemData
        //格式化时间
        val timeFormat = durationFormat(itemData.duration)
        with(holder.binding!!) {
            Logger.d("horizontalItemData===title:${itemData.title}tag:${itemData.tags.size}")
            if (itemData.tags.size > 0) {
                textTag.text = "#${itemData.tags[0].name} / $timeFormat"
            } else {
                textTag.text = "#$timeFormat"
            }
            root.setOnClickListener {
                goToVideoPlayer(root.context as AppCompatActivity,imageCoverFeed,items!![position])
            }
        }
    }

    private fun goToVideoPlayer(activity: AppCompatActivity, imageView: ImageView, item: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA,item)
        intent.putExtra(VideoDetailActivity.TRANSITION,true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair<View,String>(imageView,VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,pair)
            ActivityCompat.startActivity(activity,intent,activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in,R.anim.anim_out)
        }
    }
}