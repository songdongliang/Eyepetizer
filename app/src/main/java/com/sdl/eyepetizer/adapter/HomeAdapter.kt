package com.sdl.eyepetizer.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.model.HomeBean

class HomeAdapter(): RecyclerView.Adapter<BindingViewHolder<ViewDataBinding>>() {

    private var items: ArrayList<HomeBean.Issue.Item>? = null

    private var inflater: LayoutInflater? = null

    constructor(context: Context,items: ArrayList<HomeBean.Issue.Item>) : this() {
        this.inflater = LayoutInflater.from(context)
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ViewDataBinding> {
        return BindingViewHolder(DataBindingUtil.inflate(inflater!!, R.layout.item_home,parent,false))
    }

    override fun getItemCount(): Int {
        return items!!.size
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ViewDataBinding>, position: Int) {

    }

}