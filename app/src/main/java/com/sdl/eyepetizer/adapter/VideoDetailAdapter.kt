package com.sdl.eyepetizer.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sdl.eyepetizer.EyepetizerApplication
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.databinding.ItemVideoDetailInfoBinding
import com.sdl.eyepetizer.databinding.ItemVideoSmallCardBinding
import com.sdl.eyepetizer.databinding.ItemVideoTextCardBinding
import com.sdl.eyepetizer.durationFormat
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.showToast

class VideoDetailAdapter: RecyclerView.Adapter<BindingViewHolder<ViewDataBinding>> {

    private val DETEIL_INFO: Int = 0
    private val TEXT_CARD: Int = 1
    private val SMALL_CARD: Int = 2

    private var items: ArrayList<HomeBean.Issue.Item>

    private var mLayoutInflater: LayoutInflater

    private var textTypeface: Typeface

    init {
        textTypeface = Typeface.createFromAsset(EyepetizerApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    constructor(context: Context,items: ArrayList<HomeBean.Issue.Item>): super() {
        mLayoutInflater = LayoutInflater.from(context)
        this.items = items
    }

    private var mOnItemClickRelatedVideo: ((item: HomeBean.Issue.Item) -> Unit)? = null

    fun setOnItemDetailClick(mItemRelatedVideo: (item: HomeBean.Issue.Item) -> Unit) {
        this.mOnItemClickRelatedVideo = mItemRelatedVideo
    }

    fun addData(item: HomeBean.Issue.Item) {
        items.clear()
        notifyDataSetChanged()
        items.add(item)
        notifyItemInserted(0)
    }

    fun addList(items: ArrayList<HomeBean.Issue.Item>) {
        this.items.addAll(items)
        notifyItemRangeInserted(1,items.size)
    }

    override fun getItemViewType(position: Int): Int {
        return when  {
                    position == 0 -> DETEIL_INFO
                    items[position].type == "textCard" -> TEXT_CARD
                    items[position].type == "videoSmallCard" -> SMALL_CARD
                    else -> -1
               }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ViewDataBinding> {
        var layoutId = 0
        when (viewType) {
            DETEIL_INFO -> layoutId = R.layout.item_video_detail_info
            TEXT_CARD -> layoutId = R.layout.item_video_text_card
            SMALL_CARD -> layoutId = R.layout.item_video_small_card
        }
        val viewDataBinding: ViewDataBinding = DataBindingUtil.inflate(mLayoutInflater,layoutId,parent,false)
        return BindingViewHolder(viewDataBinding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ViewDataBinding>, position: Int) {
        var item = items[position]
        when (getItemViewType(position)) {
            DETEIL_INFO -> {
                setVideoDetailInfo(item,holder.binding)
            }
            TEXT_CARD -> {
                setTextCard(item,holder.binding)
            }
            SMALL_CARD -> {
                setSmallCard(item,holder.binding)
            }
        }
    }

    private fun setSmallCard(item: HomeBean.Issue.Item, binding: ViewDataBinding?) {
        var itemVideoSmallCard = binding as ItemVideoSmallCardBinding
        itemVideoSmallCard.textTitle.text = item.data.title
        itemVideoSmallCard.imageUrl = item.data.cover.detail
        itemVideoSmallCard.textTag.text = "#${item.data.category} / ${durationFormat(item.data.duration)}"
        itemVideoSmallCard.root.setOnClickListener {
            mOnItemClickRelatedVideo?.invoke(item)
        }
    }

    private fun setTextCard(item: HomeBean.Issue.Item, binding: ViewDataBinding?) {
        var itemVideoTextCard = binding as ItemVideoTextCardBinding
        itemVideoTextCard.textTextCard.typeface = textTypeface
        itemVideoTextCard.textTextCard.text = item.data.text
    }

    private fun setVideoDetailInfo(item: HomeBean.Issue.Item, binding: ViewDataBinding?) {
        var binding: ItemVideoDetailInfoBinding = binding as ItemVideoDetailInfoBinding
        binding.textTag.text = "#${item.data?.category} / ${durationFormat(item.data?.duration)}"
        binding.item = item
        with(binding) {
            var context = binding.root.context
            textActionFavorites.setOnClickListener {
                context.showToast(context.getString(R.string.like))
            }
            textActionShare.setOnClickListener {
                context.showToast(context.getString(R.string.share))
            }
            textActionReply.setOnClickListener {
                context.showToast(context.getString(R.string.reply))
            }
        }
    }


}