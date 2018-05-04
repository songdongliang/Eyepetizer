package com.sdl.eyepetizer.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v4.util.Pair
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sdl.eyepetizer.Constants
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.databinding.ItemHomeContentBinding
import com.sdl.eyepetizer.databinding.ItemHomeHeaderBinding
import com.sdl.eyepetizer.durationFormat
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.ui.activity.VideoDetailActivity

class HomeAdapter(): RecyclerView.Adapter<BindingViewHolder<ViewDataBinding>>() {

    private var items: ArrayList<HomeBean.Issue.Item>? = null

    private var inflater: LayoutInflater? = null

    var bannerItemSize = 0

    companion object {
        private val ITEM_TYPE_BANNER = 1 //Banner类型
        private val ITEM_TYPE_TEXT_HEADER = 2 //textHeader
        private val ITEM_TYPE_CONTENT = 3 //item
    }

    constructor(context: Context,items: ArrayList<HomeBean.Issue.Item>) : this() {
        this.inflater = LayoutInflater.from(context)
        this.items = items
    }

    fun setBannerSize(count: Int) {
        bannerItemSize = count
    }

    fun addItemData(itemList: ArrayList<HomeBean.Issue.Item>) {
        items!!.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> ITEM_TYPE_BANNER
            items!![position + bannerItemSize - 1].type == "textHeader" -> ITEM_TYPE_TEXT_HEADER
            else -> ITEM_TYPE_CONTENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ViewDataBinding> {
        var binding: ViewDataBinding? = null
        when (viewType) {
            ITEM_TYPE_BANNER -> binding = DataBindingUtil.inflate(inflater!!, R.layout.item_home_banner,parent,false)
            ITEM_TYPE_TEXT_HEADER -> binding = DataBindingUtil.inflate(inflater!!, R.layout.item_home_header,parent,false)
            ITEM_TYPE_CONTENT -> binding = DataBindingUtil.inflate(inflater!!, R.layout.item_home_content,parent,false)
        }
        return BindingViewHolder(binding!!)
    }

    /**
     * 得到RecyclerView Item的数量 (Banner作为一个item)
     */
    override fun getItemCount(): Int {
        return when {
            items!!.size > bannerItemSize -> items!!.size - bannerItemSize + 1
            items!!.isEmpty() -> 0
            else -> 1
        }
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ViewDataBinding>, position: Int) {
        var viewDataBinding: ViewDataBinding? = holder.binding
        when(getItemViewType(position)) {
            ITEM_TYPE_TEXT_HEADER -> {
                viewDataBinding as ItemHomeHeaderBinding;
                viewDataBinding.headTitle = items!![position + bannerItemSize - 1].data.text ?: ""
            }
            ITEM_TYPE_BANNER -> println()
            ITEM_TYPE_CONTENT -> {
                viewDataBinding as ItemHomeContentBinding
                setVideoItem(viewDataBinding,items!![position + bannerItemSize - 1])
            }
        }
    }


    private fun setVideoItem(itemHomeContentBinding: ItemHomeContentBinding,item: HomeBean.Issue.Item) {
        val itemData = item.data
        val defAvatar = R.mipmap.default_avatar
        val cover = itemData.cover.feed
        var avatar = itemData.author.icon
        var tagText: String? = "#"

        //作者出处为空，就显获取提供者的信息
        if (avatar.isNullOrEmpty()) {
            avatar = itemData.provider.icon
        }
        //如果提供者信息为空，就显示默认
        if (!avatar.isNullOrEmpty()) {
            itemHomeContentBinding.avatar = avatar
        }
        //加载封面
        itemHomeContentBinding.cover = cover

        //遍历标签
        itemData.tags.take(4).forEach {
            tagText += (it.name + "/")
        }
        //格式化时间
        val timeFormat = durationFormat(itemData.duration)

        tagText += timeFormat

        itemHomeContentBinding.textTag.text = tagText

        itemHomeContentBinding.tvCategory.text = itemData.category

        itemHomeContentBinding.root.setOnClickListener {
            goToVideoPlayer(it.context as Activity,itemHomeContentBinding.ivCoverFeed,item)
        }
    }

    /**
     * 跳转到视频详情页
     */
    fun goToVideoPlayer(activity: Activity, view: View,item: HomeBean.Issue.Item) {
        var intent = Intent(activity,VideoDetailActivity::class.java)
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