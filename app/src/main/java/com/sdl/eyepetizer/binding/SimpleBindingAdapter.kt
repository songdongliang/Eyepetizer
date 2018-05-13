package com.sdl.eyepetizer.binding

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide

object SimpleBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun loadUrl(imageView: ImageView, avatar: String) {
        Glide.with(imageView.context).load(avatar).into(imageView)
    }

    @JvmStatic
    @BindingConversion
    fun convertColorToDrawable(color: Int): ColorDrawable {
        return ColorDrawable(color)
    }

}