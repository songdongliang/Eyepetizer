package com.sdl.eyepetizer.load

import com.sdl.eyepetizer.model.CategoryBean

interface CategoryLoad: ILoad {

    fun showCategory(categoryList: ArrayList<CategoryBean>)

    fun showError(errorMsg: String,errorCode: Int)
}