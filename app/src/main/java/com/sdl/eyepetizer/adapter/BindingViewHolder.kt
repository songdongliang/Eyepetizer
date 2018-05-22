package com.sdl.eyepetizer.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

class BindingViewHolder<T: ViewDataBinding>(binding: T) : RecyclerView.ViewHolder(binding.root) {
    val binding: T? = binding
}