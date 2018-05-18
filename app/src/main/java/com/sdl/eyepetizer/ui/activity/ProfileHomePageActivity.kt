package com.sdl.eyepetizer.ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.scwang.smartrefresh.layout.util.DensityUtil
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.util.CleanLeakUtil
import com.sdl.eyepetizer.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_profile_home_page.*
import java.util.*

/**
 * 个人主页
 */
class ProfileHomePageActivity : BaseActivity() {

    private var mOffset = 0
    private var mScrollY = 0

    override fun layoutId(): Int {
        return R.layout.activity_profile_home_page
    }

    override fun initData() {
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(toolbar)

        refreshLayout.setOnMultiPurposeListener(object : SimpleMultiPurposeListener(){
            override fun onHeaderPulling(header: RefreshHeader?, percent: Float, offset: Int, headerHeight: Int, extendHeight: Int) {
                mOffset = offset / 2
                image_parallax.translationY = (mOffset - mScrollY).toFloat()
                toolbar.alpha = Math.min(percent,1f)
            }

            override fun onHeaderReleasing(header: RefreshHeader?, percent: Float, offset: Int, footerHeight: Int, extendHeight: Int) {
                mOffset = offset / 2
                image_parallax.translationY = (mOffset - mScrollY).toFloat()
                toolbar.alpha = 1 - Math.min(percent,1f)
            }
        })

        scrollView.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener{

            private var lastScrollY = 0
            private val h = DensityUtil.dp2px(170f)
            private val color = ContextCompat.getColor(this@ProfileHomePageActivity,R.color.colorPrimary)

            override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                var tScrollY = scrollY
                if (lastScrollY < h) {
                    tScrollY = Math.min(h,tScrollY)
                    mScrollY = if (tScrollY > h) h else tScrollY
                    buttonBarLayout.alpha = 1f * mScrollY / h
                    toolbar.setBackgroundColor(255 * mScrollY / h shl 24 or color)
                    image_parallax.translationY = (mOffset - mScrollY).toFloat()
                }
                lastScrollY = tScrollY
            }
        })

        buttonBarLayout.alpha = 0f
        toolbar.setBackgroundColor(0)
        //返回
        toolbar.setNavigationOnClickListener { finish() }

        refreshLayout.setOnRefreshListener { mWebView.loadUrl("https://about.me/songdongliang") }
        refreshLayout.autoRefresh()

        mWebView.settings.javaScriptEnabled = true

        mWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view?.loadUrl(request?.url.toString())
                    return true
                }
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    view?.loadUrl(url)
                    return true
                }
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                refreshLayout.finishRefresh()
                view?.loadUrl(String.format(Locale.CHINA, "javascript:document.body.style.paddingTop='%fpx'; void 0", DensityUtil.px2dp(mWebView.paddingTop.toFloat())))
            }
        }
    }

    override fun start() {

    }

    override fun onDestroy() {
        CleanLeakUtil.fixInputMethodManagerLeak(this)
        super.onDestroy()
    }
}
