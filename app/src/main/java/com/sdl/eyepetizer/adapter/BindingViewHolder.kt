package com.sdl.eyepetizer.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

class BindingViewHolder<T: ViewDataBinding>(t: T) : RecyclerView.ViewHolder(t.root) {

    var binding: T? = null
        get

}