package com.sdl.eyepetizer.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.databinding.ItemFollowBinding
import com.sdl.eyepetizer.model.HomeBean

class FollowAdapter: RecyclerView.Adapter<BindingViewHolder<ItemFollowBinding>> {

    private var items: ArrayList<HomeBean.Issue.Item>? = null

    private var mInflater: LayoutInflater? = null

    constructor(context: Context, items: ArrayList<HomeBean.Issue.Item>) {
        this.mInflater = LayoutInflater.from(context)
        this.items = items
    }

    fun addList(dataList: ArrayList<HomeBean.Issue.Item>) {
        this.items!!.addAll(dataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ItemFollowBinding> {
        var binding: ItemFollowBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_follow,parent,false)
        return BindingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ItemFollowBinding>, position: Int) {
        var item = items!![position]
        when {
            item.type == "videoCollectionWithBrief" -> setAuthorInfo(item,holder)
        }
    }

    private fun setAuthorInfo(item: HomeBean.Issue.Item, holder: BindingViewHolder<ItemFollowBinding>) {
        val headerData = item.data.header
        val binding = holder.binding
        //加载头像，标题，描述
        binding!!.avatar = headerData.icon
        binding.title = headerData.title
        binding.description = headerData.description

        val recyclerView = binding.mRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context,LinearLayoutManager.HORIZONTAL,false)
        recyclerView.adapter = FollowHorizontalAdapter(binding.root.context,item.data.itemList)
    }

}