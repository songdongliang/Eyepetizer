package com.sdl.eyepetizer

import android.content.Context
import android.widget.Toast

/**
 * 这个文件下的函数是扩展
 */

fun Context.showToast(content: String): Toast {
    var toast = Toast.makeText(this,content,Toast.LENGTH_SHORT)
    toast.show()
    return toast
}