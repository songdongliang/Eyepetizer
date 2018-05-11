package com.sdl.eyepetizer.ui.fragment

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.adapter.CategoryAdapter
import com.sdl.eyepetizer.connertor.CategoryLoad
import com.sdl.eyepetizer.exception.ErrorStatus
import com.sdl.eyepetizer.model.CategoryBean
import com.sdl.eyepetizer.presenter.CategoryPresenter
import com.sdl.eyepetizer.showToast
import com.sdl.eyepetizer.util.DisplayManager
import kotlinx.android.synthetic.main.layout_recyclerview.*

class CategoryFragment: BaseFragment(),CategoryLoad {

    private var mTitle: String? = null
    private var mCategoryList = ArrayList<CategoryBean>()

    private val mAdapter by lazy {
        CategoryAdapter(context!!,mCategoryList)
    }

    private val mPresenter by lazy {
        CategoryPresenter()
    }

    companion object {
        fun getInstance(title: String) : CategoryFragment {
            val fragment = CategoryFragment()
            fragment.mTitle = title
            return fragment
        }
    }

    override fun showCategory(categoryList: ArrayList<CategoryBean>) {
        mCategoryList = categoryList
        mAdapter.setData(categoryList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        context?.showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView?.showNoNetwork()
        } else {
            multipleStatusView?.showError()
        }
    }

    override fun showLoading() {
        multipleStatusView?.showLoading()
    }

    override fun dismissLoading() {
        multipleStatusView?.showContent()
    }

    override fun getLayoutId(): Int {
        return R.layout.layout_recyclerview
    }

    override fun initView() {
        mPresenter.attachLoad(this)
        mLayoutStatusView = multipleStatusView
        mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration(){
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                val position = parent?.getChildPosition(view)
                val offset = DisplayManager.dip2px(2f)!!
                outRect?.set(if (position?.rem(2) == 0) 0 else offset,offset,
                        if (position?.rem(2) == 0) offset else 0,offset)
            }
        })
    }

    override fun lazyLoad() {
        //获取分类信息
        mPresenter.getCategoryData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachLoad()
    }

}