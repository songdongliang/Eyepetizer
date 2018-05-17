package com.sdl.eyepetizer.ui.activity

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sdl.eyepetizer.Constants
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.adapter.CategoryDetailAdapter
import com.sdl.eyepetizer.databinding.ActivityCategoryDetailBinding
import com.sdl.eyepetizer.load.CategoryDetailLoad
import com.sdl.eyepetizer.model.CategoryBean
import com.sdl.eyepetizer.model.HomeBean
import com.sdl.eyepetizer.presenter.CategoryDetailPresenter
import com.sdl.eyepetizer.util.StatusBarUtil
import kotlinx.android.synthetic.main.activity_category_detail.*

class CategoryDetailActivity : BaseActivity(),CategoryDetailLoad {

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private var category: CategoryBean? = null

    private var loadingMore = false

    private val mPresenter by lazy {
        CategoryDetailPresenter()
    }

    private val mAdapter by lazy {
        CategoryDetailAdapter(this,itemList)
    }

    override fun layoutId(): Int {
        return R.layout.activity_category_detail
    }

    override fun initData() {
        category = intent.getSerializableExtra(Constants.BUNDLE_CATEGORY_DATA) as? CategoryBean

        val binding: ActivityCategoryDetailBinding = DataBindingUtil.inflate(layoutInflater,R.layout.activity_category_detail,null,false)
        binding.category = category

        tv_category_desc.text = "#${category?.description}"

        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE)
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK)

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView.layoutManager.itemCount
                val lastVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                    loadingMore = true
                    mPresenter.loadMoreData()
                }
            }
        })

        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(toolbar)
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        mPresenter.attachLoad(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun start() {
        mPresenter.getCategoryDetailList(category?.id!!)
    }

    override fun setCategoryDetailList(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mAdapter.addList(itemList)
    }

    override fun showError(errorMsg: String) {
        mLayoutStatusView?.showError()
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachLoad()
    }
}
