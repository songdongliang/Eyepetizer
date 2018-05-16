package com.sdl.eyepetizer.ui.activity

import android.annotation.TargetApi
import android.os.Build
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.transition.Transition
import android.view.View
import android.widget.ImageView
import com.orhanobut.logger.Logger
import com.scwang.smartrefresh.header.MaterialHeader
import com.sdl.eyepetizer.Constants
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.SimpleVideoAllCallBack
import com.sdl.eyepetizer.adapter.VideoDetailAdapter
import com.sdl.eyepetizer.binding.SimpleBindingAdapter
import com.sdl.eyepetizer.connertor.VideoDetailLoad
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.presenter.VideoDetailPresenter
import com.sdl.eyepetizer.showToast
import com.sdl.eyepetizer.util.StatusBarUtil
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.activity_video_detail.*

class VideoDetailActivity : BaseActivity(),VideoDetailLoad {

    private lateinit var item: HomeBean.Issue.Item
    private var items = ArrayList<HomeBean.Issue.Item>()

    private lateinit var orientationUtils: OrientationUtils
    private var isPlay: Boolean = false
    private var isPause: Boolean = false

    private var isTransiton: Boolean = false

    private var transition: Transition? = null
    private var mMaterialHeader: MaterialHeader? = null

    private val mPresenter by lazy {
        VideoDetailPresenter()
    }

    private val mAdapter by lazy {
        VideoDetailAdapter(this,items)
    }

    private val mFormat by lazy {
        SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA)
    }

    companion object {
        val IMG_TRANSITION = "IMG_TRANSITION"
        val TRANSITION = "TRANSITION"
    }

    override fun layoutId(): Int {
        return R.layout.activity_video_detail
    }

    override fun setVideo(url: String) {

    }

    override fun setVideoInfo(item: HomeBean.Issue.Item) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setBackground(url: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setErrorMsg(errorMsg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData() {
        item = intent.getSerializableExtra(Constants.BUNDLE_VIDEO_DATA) as HomeBean.Issue.Item
        isTransiton = intent.getBooleanExtra(TRANSITION,false)
        saveWatchVideoHistoryInfo(item)
    }

    /**
     * 保存观看记录
     */
    private fun saveWatchVideoHistoryInfo(item: HomeBean.Issue.Item) {
        val historyMap =
    }

    override fun initView() {
        mPresenter.attachLoad(this)
        //过渡动画
        initTransition()
        initVideoViewConfig()

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter
        //设置相关视频Item的点击事件
        mAdapter.setOnItemDetailClick { mPresenter.loadVideoInfo(it) }
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(mVideoView)

        //下拉刷新
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener {
            loadVideoInfo()
        }
        mMaterialHeader = mRefreshLayout.refreshHeader as MaterialHeader
        //打开下拉刷新区域块背景
        mMaterialHeader?.setShowBezierWave(true)
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)
    }

    private fun initVideoViewConfig() {
        orientationUtils = OrientationUtils(this,mVideoView)
        //是否旋转
        mVideoView.isRotateViewAuto = false
        //是否可以滑动调整
        mVideoView.setIsTouchWiget(true)

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        SimpleBindingAdapter.loadUrl(imageView,item.data.cover.feed)
        mVideoView.thumbImageView = imageView

        mVideoView.setVideoAllCallBack(object : SimpleVideoAllCallBack(){
            override fun onEnterFullscreen(url: String?, vararg objects: Any?) {
                Logger.d("***** onEnterFullscreen **** ")
            }

            override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                Logger.d("***** onQuitFullscreen **** ")
                orientationUtils.backToProtVideo()
            }

            override fun onPrepared(url: String?, vararg objects: Any?) {
                orientationUtils.isEnable = true
            }

            override fun onAutoComplete(url: String?, vararg objects: Any?) {
                Logger.d("*** onAutoPlayComplete *** ")
            }

            override fun onPlayError(url: String?, vararg objects: Any?) {
                showToast(getString(R.string.play_error))
            }

        })
        //设置返回按键功能
        mVideoView.backButton.setOnClickListener { onBackPressed() }
        //设置全屏按键功能
        mVideoView.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils.resolveByClick()
            //第一个true是否需要隐藏ActionBar,第二个true是否要隐藏StatusBar
            mVideoView.startWindowFullscreen(this,true,true)
        }
        //锁屏事件
        mVideoView.setLockClickListener(object : LockClickListener {
            override fun onClick(view: View?, lock: Boolean) {
                orientationUtils.isEnable = lock
            }
        })
    }

    private fun initTransition() {
        if (isTransiton && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //延迟执行transition动画，直到页面data已经被load,该方法一般被执行在onCreate()中
            postponeEnterTransition()
            ViewCompat.setTransitionName(mVideoView, IMG_TRANSITION)
            addTransitionListener()
            startPostponedEnterTransition()
        } else {
            loadVideoInfo()
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addTransitionListener() {
        transition = window.sharedElementEnterTransition
        transition?.addListener(object : Transition.TransitionListener{
            override fun onTransitionEnd(transition: Transition?) {

            }

            override fun onTransitionResume(transition: Transition?) {

            }

            override fun onTransitionPause(transition: Transition?) {

            }

            override fun onTransitionCancel(transition: Transition?) {

            }

            override fun onTransitionStart(transition: Transition?) {
                Logger.d("onTransitionEnd()")
                loadVideoInfo()
                transition?.removeListener(this)
            }

        })
    }

    private fun loadVideoInfo() {
        mPresenter.loadVideoInfo(item)
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
