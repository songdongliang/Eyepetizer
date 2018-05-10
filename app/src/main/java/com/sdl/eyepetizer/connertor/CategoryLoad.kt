package com.sdl.eyepetizer.connertor

import com.sdl.eyepetizer.model.CategoryBean

interface CategoryLoad: ILoad {

    fun showCategory(categoryList: ArrayList<CategoryBean>)

    fun showError(errorMsg: String,errorCode: Int)
}