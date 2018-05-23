package com.sdl.eyepetizer.binding

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sdl.eyepetizer.R

object SimpleBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun loadUrl(imageView: ImageView, avatar: String) {
        if (avatar.isNullOrEmpty()) {
            imageView.setImageResource(R.mipmap.placeholder_banner)
            return
        }
        val options = RequestOptions()
        options.placeholder(R.mipmap.placeholder_banner).error(R.mipmap.placeholder_banner)
        Glide.with(imageView).load(avatar).apply(options).into(imageView)
    }

    @JvmStatic
    @BindingConversion
    fun convertColorToDrawable(color: Int): ColorDrawable {
        return ColorDrawable(color)
    }

}