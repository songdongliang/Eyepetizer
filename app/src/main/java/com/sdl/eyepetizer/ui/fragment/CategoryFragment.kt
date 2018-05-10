package com.sdl.eyepetizer.ui.fragment

import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.connertor.CategoryLoad
import com.sdl.eyepetizer.model.CategoryBean
import com.sdl.eyepetizer.presenter.CategoryPresenter

class CategoryFragment: BaseFragment(),CategoryLoad {

    override fun showCategory(categoryList: ArrayList<CategoryBean>) {

    }

    override fun showError(errorMsg: String, errorCode: Int) {

    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }

    override fun getLayoutId(): Int {
        return R.layout.layout_recyclerview
    }

    override fun initView() {

    }

    override fun lazyLoad() {

    }

}