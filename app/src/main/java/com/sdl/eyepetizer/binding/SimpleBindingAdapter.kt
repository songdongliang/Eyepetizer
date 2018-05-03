package com.sdl.eyepetizer.binding

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide

class SimpleBindingAdapter private constructor(){

    @BindingAdapter("app:imageUrl")
    fun loadUrl(imageView: ImageView,avatar: String) {
        Glide.with(imageView.context).load(avatar).into(imageView)
    }

    @BindingConversion
    fun convertColorToDrawable(color: Int): ColorDrawable {
        return ColorDrawable(color)
    }
}