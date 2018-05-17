package com.sdl.eyepetizer.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.lang.reflect.Field

object CleanLeakUtil {

    fun fixInputMethodManagerLeak(context: Context?) {
        if (context == null) {
            return
        }
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val viewArray = arrayOf("mCurRootView", "mServedView", "mNextServedView")
        var field: Field
        var fieldObject: Any?

        for (viewName in viewArray) {
            try {
                field = inputMethodManager.javaClass.getDeclaredField(viewName)
                if (!field.isAccessible) {
                    field.isAccessible = true
                }
                fieldObject = field.get(inputMethodManager)
                if (fieldObject != null && fieldObject is View) {
                    val fieldView = fieldObject as View?
                    if (fieldView?.context == context) {
                        field.set(inputMethodManager,null) //置空，破坏掉path to gc节点
                    } else {
                        break
                    }
                }
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }
}