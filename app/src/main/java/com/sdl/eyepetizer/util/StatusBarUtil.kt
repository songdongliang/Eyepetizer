package com.sdl.eyepetizer.util

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.support.annotation.FloatRange
import android.support.annotation.RequiresApi
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.orhanobut.logger.Logger
import java.util.regex.Pattern

class StatusBarUtil {

    companion object {
        private var DEFAULT_COLOR = 0
        //Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? 0.2f : 0.3f
        private var DEFAULT_ALPHA = 0f
        private var MIN_API = 19

        private val isFlyme4Later: Boolean
            get() = (Build.FINGERPRINT.contains("Flyme_OS_4")
                    || Build.VERSION.INCREMENTAL.contains("Flyme_OS_4")
                    || Pattern.compile("Flyme OS [4|5]", Pattern.CASE_INSENSITIVE).matcher(Build.DISPLAY).find())

        private val isMIUI6Later: Boolean
            get() {
                var b = false
                try {
                    val clazz = Class.forName("android.os.SystemProperties")
                    val method = clazz.getMethod("get",String::class.java)
                    var versionName = method.invoke(null,"ro.miui.ui.version.name") as String
                    versionName = versionName.replace("[vV]".toRegex(),"")
                    val version = Integer.parseInt(versionName)
                    b = version > 6
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return b
            }

        @TargetApi(Build.VERSION_CODES.M)
        fun darkMode(window: Window,color: Int,@FloatRange(from = 0.0,to = 1.0) alpha: Float) {
            when {
                isFlyme4Later -> {
                    darkModeForFlyme4(window,true)
                    immersive(window,color,alpha)
                }
                isMIUI6Later -> {
                    darkModeForMIUI6(window,true)
                    immersive(window,color,alpha)
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    darkModeForM(window,true)
                    immersive(window,color, alpha)
                }
                Build.VERSION.SDK_INT >= 19 -> {
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                    setTranslucentView(window.decorView as ViewGroup,color,alpha)
                }
                else -> {
                    immersive(window,color,alpha)
                }
            }
        }

        /**
         * android6.0设置字体颜色
         */
        @RequiresApi(Build.VERSION_CODES.M)
        fun darkModeForM(window: Window,dark: Boolean) {
            var systemUiVisibility = window.decorView.systemUiVisibility
            systemUiVisibility = if (dark) {
                systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            window.decorView.systemUiVisibility = systemUiVisibility
        }

        /**
         * 设置Flyme4+的darkMode，darkMode时候字体颜色及icon变黑
         * http://open-wiki.flyme.cn/index.php?title=Flyme%E7%B3%BB%E7%BB%9FAPI
         */
        fun darkModeForFlyme4(window: Window?,dark: Boolean): Boolean {
            var result = false
            if (window != null) {
                try {
                    val attr = window.attributes
                    val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                    val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
                    darkFlag.isAccessible = true
                    meizuFlags.isAccessible = true
                    val bit = darkFlag.getInt(null)
                    var value = meizuFlags.getInt(attr)
                    if (dark) {
                        //按位或操作符
                        value = value or bit
                    } else {
                        value = value and bit.inv()
                    }
                    meizuFlags.setInt(attr,value)
                    window.attributes = attr
                    result = true
                } catch (e: Exception) {
                    Logger.e(e,"darkIcon: failed")
                }
            }
            return result
        }

        /**
         * 设置MIUI6+的状态栏是否为darkMode,darkMode时候字体颜色及icon变黑
         * http://dev.xiaomi.com/doc/p=4769/
         */
        fun darkModeForMIUI6(window: Window,darkmode: Boolean): Boolean {
            var result = false
            try {
                val clazz = window.javaClass
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                val darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags",Int::class.javaPrimitiveType,Int::class.javaPrimitiveType)
                extraFlagField.invoke(window,if (darkmode) darkModeFlag else 0,darkModeFlag)
                result = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return result
        }

        /** 设置状态栏darkMode,字体颜色及icon变黑(目前支持MIUI6以上,Flyme4以上,Android M以上)  */
        fun darkMode(activity: Activity) {
            darkMode(activity.window, DEFAULT_COLOR, DEFAULT_ALPHA)
        }

        fun darkMode(activity: Activity, color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float) {
            darkMode(activity.window, color, alpha)
        }

        @JvmOverloads
        fun immersive(activity: Activity,color: Int = DEFAULT_COLOR,@FloatRange(from = 0.0, to = 1.0) alpha: Float = DEFAULT_ALPHA) {
            immersive(activity.window,color,alpha)
        }

        @JvmOverloads
        fun immersive(window: Window,color: Int,@FloatRange(from = 0.0,to = 1.0) alpha: Float = 1f) {
            if (Build.VERSION.SDK_INT >= 21) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = mixtureColor(color,alpha)

                var systemUiVisibility = window.decorView.systemUiVisibility
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.decorView.systemUiVisibility = systemUiVisibility
            } else if (Build.VERSION.SDK_INT >= 19) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                setTranslucentView(window.decorView as ViewGroup,color,alpha)
            } else if (Build.VERSION.SDK_INT >= MIN_API && Build.VERSION.SDK_INT > 19) {
                var systemUiVisibility = window.decorView.systemUiVisibility
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                window.decorView.systemUiVisibility = systemUiVisibility
            }
        }

        private fun setTranslucentView(container: ViewGroup, color: Int, alpha: Float) {
            if (Build.VERSION.SDK_INT >= MIN_API) {
                val mixtureColor = mixtureColor(color,alpha)
                var translucentView: View? = container.findViewById(android.R.id.custom)
                if (translucentView == null && mixtureColor != 0) {
                    translucentView = View(container.context)
                    translucentView.id = android.R.id.custom
                    val lp = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(container.context))
                    container.addView(translucentView,lp)
                }
                if (translucentView != null) {
                    translucentView.setBackgroundColor(mixtureColor)
                }
            }
        }

        private fun mixtureColor(color: Int, alpha: Float): Int {
            val a = if (color and -0x1000000 == 0) 0xff else color.ushr(24)
            return color and 0x00ffffff or ((a * alpha).toInt() shl 24)
        }

        fun getStatusBarHeight(context: Context): Int {
            var result = 24
            val resId = context.resources.getIdentifier("status_bar_height","dimen","android")
            result = if (resId > 0) {
                context.resources.getDimensionPixelSize(resId)
            } else {
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,result.toFloat(),Resources.getSystem().displayMetrics)
                        .toInt()
            }
            return result
        }

        /**
         * 增加view的paddingTop,增加的值为状态栏高度(智能判断，并设置高度)
         */
        fun setPaddingSmart(view: View) {
            if (Build.VERSION.SDK_INT >= MIN_API) {
                val lp = view.layoutParams
                if (lp != null && lp.height > 0) {
                    lp.height += getStatusBarHeight(view.context)
                }
                view.setPadding(view.paddingLeft,view.paddingTop + getStatusBarHeight(view.context)
                        ,view.paddingRight,view.paddingBottom)
            }
        }
    }
}