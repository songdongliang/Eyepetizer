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

fun Context.showToast(stringId: Int): Toast {
    return showToast(getString(stringId))
}

fun durationFormat(duration: Long): String {
    val minute = duration / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second"
        }
    }
}