package com.sdl.eyepetizer.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.databinding.ItemSimpleTextBinding

class SimpleTextAdapter: RecyclerView.Adapter<BindingViewHolder<ItemSimpleTextBinding>> {

    private val context: Context

    constructor(context: Context) : super() {
        this.context = context
    }

    override fun getItemViewType(position: Int): Int {
        Logger.i("getItemViewType---------" + position.toString())
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ItemSimpleTextBinding> {
        val binding: ItemSimpleTextBinding = DataBindingUtil.inflate(LayoutInflater.from(context)
                , R.layout.item_simple_text,parent,false)
        return BindingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ItemSimpleTextBinding>, position: Int) {
        Logger.i("onBindViewHolder---" + position.toString())
    }

}