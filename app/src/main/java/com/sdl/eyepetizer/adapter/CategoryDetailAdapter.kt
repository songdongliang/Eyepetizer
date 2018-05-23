package com.sdl.eyepetizer.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sdl.eyepetizer.Constants
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.databinding.ItemCategoryDetailBinding
import com.sdl.eyepetizer.durationFormat
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.ui.activity.VideoDetailActivity
import kotlinx.android.synthetic.main.item_category_detail.view.*

class CategoryDetailAdapter: RecyclerView.Adapter<BindingViewHolder<ItemCategoryDetailBinding>> {

    private val context: Context
    private var mLayoutInflater: LayoutInflater? = null
    private var itemList: ArrayList<HomeBean.Issue.Item>? = null

    constructor(context: Context,itemList: ArrayList<HomeBean.Issue.Item>) {
        this.context = context
        mLayoutInflater = LayoutInflater.from(context)
        this.itemList = itemList
    }

    fun addList(itemList: ArrayList<HomeBean.Issue.Item>) {
        this.itemList?.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ItemCategoryDetailBinding> {
        var binding: ItemCategoryDetailBinding = DataBindingUtil
                .inflate(mLayoutInflater!!, R.layout.item_category_detail,parent,false)
        return BindingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList!!.size
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ItemCategoryDetailBinding>, position: Int) {
        val itemData: HomeBean.Issue.Item.Data = itemList!![position].data
        var binding = holder.binding
        binding?.coverUrl = itemData.cover.feed
        binding?.title = itemData.title
        //格式化时间
        val timeFormat = durationFormat(itemData.duration)
        binding?.textTag?.text = "#${itemData.category}/$timeFormat"
        binding?.root?.setOnClickListener {
            goToVideoPlayer(context as Activity,binding.imageCategoryDetail,itemList!![position])
        }
    }

    private fun goToVideoPlayer(activity: Activity, view: View,item: HomeBean.Issue.Item ) {
        val intent = Intent(activity,VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA,item)
        intent.putExtra(VideoDetailActivity.TRANSITION,true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair<View,String>(view,VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,pair)
            ActivityCompat.startActivity(activity,intent,activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in,R.anim.anim_out)
        }
    }
}