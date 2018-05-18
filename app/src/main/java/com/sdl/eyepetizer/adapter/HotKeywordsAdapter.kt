package com.sdl.eyepetizer.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.databinding.ItemFlowTextBinding

class HotKeywordsAdapter:  RecyclerView.Adapter<BindingViewHolder<ItemFlowTextBinding>>{

    private val tags: ArrayList<String>
    private val mInflater: LayoutInflater

    private var mOnTagItemClick: ((tag: String) -> Unit)? = null

    constructor(context: Context,tags: ArrayList<String>): super() {
        mInflater = LayoutInflater.from(context)
        this.tags = tags
    }

    fun setOnTagItemClickListener(onTagItemClickListener: (tag:String) -> Unit) {
        this.mOnTagItemClick = onTagItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ItemFlowTextBinding> {
        val binding: ItemFlowTextBinding = DataBindingUtil.inflate(mInflater, R.layout.item_flow_text,parent,false)
        return BindingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ItemFlowTextBinding>, position: Int) {
        holder.binding?.tag = tags[position]
        holder.binding?.root?.setOnClickListener {
            mOnTagItemClick?.invoke(tags[position])
        }
    }

}