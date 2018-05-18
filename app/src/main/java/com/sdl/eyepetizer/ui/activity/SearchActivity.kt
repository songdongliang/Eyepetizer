package com.sdl.eyepetizer.ui.activity

import android.annotation.TargetApi
import android.graphics.Typeface
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import com.google.android.flexbox.*
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.adapter.CategoryDetailAdapter
import com.sdl.eyepetizer.adapter.HotKeywordsAdapter
import com.sdl.eyepetizer.exception.ErrorStatus
import com.sdl.eyepetizer.load.SearchLoad
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.presenter.SearchPresenter
import com.sdl.eyepetizer.showToast
import com.sdl.eyepetizer.util.CleanLeakUtil
import com.sdl.eyepetizer.util.StatusBarUtil
import com.sdl.eyepetizer.util.ViewAnimUtil
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(),SearchLoad {

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private var mHotKeywordsAdapter: HotKeywordsAdapter? = null

    private var keyWords: String? = null
    private var mTextTypeface: Typeface? = null
    private var loadingMore = false

    private val mPresenter by lazy {
        SearchPresenter()
    }

    private val mResultAdapter by lazy {
        CategoryDetailAdapter(this,itemList)
    }

    init {
        mPresenter.attachLoad(this)
        mTextTypeface = Typeface.createFromAsset(assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun setHotWordData(string: ArrayList<String>) {
        showHotWordView()

        mHotKeywordsAdapter = HotKeywordsAdapter(this,string)
        val flexBoxLayoutManager = FlexboxLayoutManager(this)
        //按正常方面换行
        flexBoxLayoutManager.flexWrap = FlexWrap.WRAP
        //主轴为水平方向
        flexBoxLayoutManager.flexDirection = FlexDirection.ROW
        //副轴上如何对齐
        flexBoxLayoutManager.alignItems = AlignItems.CENTER
        //多个轴对齐方式
        flexBoxLayoutManager.justifyContent = JustifyContent.FLEX_START
        mRecyclerView_hot.layoutManager = flexBoxLayoutManager
        mRecyclerView_hot.adapter = mHotKeywordsAdapter
        //设置tag的点击事件
        mHotKeywordsAdapter?.setOnTagItemClickListener {
            closeSoftKeyboard()
            keyWords = it
            mPresenter.querySearchData(it)
        }
    }

    private fun showHotWordView() {
        layout_content_result.visibility = View.GONE
        layout_hot_words.visibility = View.VISIBLE
    }

    private fun hideHotWordView(){
        layout_hot_words.visibility = View.GONE
        layout_content_result.visibility = View.VISIBLE
    }

    override fun setSearchResult(issue: HomeBean.Issue) {
        loadingMore = false
        hideHotWordView()
        tv_search_count.visibility = View.VISIBLE
        tv_search_count.text = String.format(getString(R.string.search_result_count), keyWords, issue.total)

        itemList = issue.itemList
        mResultAdapter.addList(itemList)
    }

    override fun closeSoftKeyboard() {
        closeKeyBord(et_search_view,this)
    }

    /**
     * 没有找到匹配的内容
     */
    override fun setEmptyView() {
        showToast("抱歉，没有找到相匹配的内容")
        hideHotWordView()
        tv_search_count.visibility = View.GONE
        mLayoutStatusView?.showEmpty()
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun layoutId(): Int {
        return R.layout.activity_search
    }

    override fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpEnterAnimation()
            setUpExitAnimation()
        } else {
            setUpView()
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpExitAnimation() {
        val fade = Fade()
        fade.duration = 300
        window.returnTransition = fade
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpEnterAnimation() {
        val transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion)
        window.sharedElementEnterTransition = transition
        transition.addListener(object : Transition.TransitionListener{
            override fun onTransitionEnd(transition: Transition?) {
                transition?.removeListener(this)
                animateRevealShow()
            }

            override fun onTransitionResume(transition: Transition?) {

            }

            override fun onTransitionPause(transition: Transition?) {

            }

            override fun onTransitionCancel(transition: Transition?) {

            }

            override fun onTransitionStart(transition: Transition?) {

            }

        })
    }

    /**
     * 展示动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun animateRevealShow() {
        ViewAnimUtil.animateRevealShow(this,rel_frame,
                fab_circle.width / 2,R.color.backgroundColor,
                object : ViewAnimUtil.OnRevealAnimationListener{
                    override fun onRevealHide() {

                    }

                    override fun onRevealShow() {
                        setUpView()
                    }

                })
    }

    private fun setUpView() {
        val animation = AnimationUtils.loadAnimation(this,android.R.anim.fade_in)
        animation.duration = 300
        rel_container.startAnimation(animation)
        rel_container.visibility = View.VISIBLE
        //打开软键盘
        openKeyBord(et_search_view,this)
    }

    override fun initView() {
        tv_title_tip.typeface = mTextTypeface
        tv_hot_search_words.typeface = mTextTypeface

        mRecyclerView_result.layoutManager = LinearLayoutManager(this)
        mRecyclerView_result.adapter = mResultAdapter

        //实现自动加载
        mRecyclerView_result.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView_result.layoutManager.itemCount
                val lastVisibleItem = (mRecyclerView_result.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                    loadingMore = true
                    mPresenter.loadMoreData()
                }
            }
        })

        //取消
        tv_cancel.setOnClickListener { onBackPressed() }
        //键盘的搜索按钮
        et_search_view.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                closeSoftKeyboard()
                keyWords = et_search_view.text.toString().trim()
                if (keyWords.isNullOrBlank()) {
                    showToast(getString(R.string.input_interest_word))
                } else {
                    mPresenter.querySearchData(keyWords!!)
                }
            }
            false
        }

        mLayoutStatusView = multipleStatusView

        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(toolbar)
    }

    override fun start() {
        mPresenter.requestHotWordData()
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimUtil.animateRevealHide(this,rel_frame,
                    fab_circle.width / 2,R.color.backgroundColor,
                    object : ViewAnimUtil.OnRevealAnimationListener{
                        override fun onRevealHide() {
                            defaultBackPressed()
                        }

                        override fun onRevealShow() {

                        }

                    })
        } else {
            defaultBackPressed()
        }
    }

    private fun defaultBackPressed() {
        closeSoftKeyboard()
        super.onBackPressed()
    }

    override fun onDestroy() {
        CleanLeakUtil.fixInputMethodManagerLeak(this)
        super.onDestroy()
        mPresenter.detachLoad()
        mTextTypeface = null
    }
}
