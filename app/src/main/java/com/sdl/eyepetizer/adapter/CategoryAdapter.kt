package com.sdl.eyepetizer.adapter

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sdl.eyepetizer.Constants
import com.sdl.eyepetizer.EyepetizerApplication
import com.sdl.eyepetizer.R
import com.sdl.eyepetizer.databinding.ItemCategoryBinding
import com.sdl.eyepetizer.model.CategoryBean
import com.sdl.eyepetizer.ui.activity.CategoryDetailActivity

class CategoryAdapter: RecyclerView.Adapter<BindingViewHolder<ItemCategoryBinding>> {

    private var mLayoutInflater: LayoutInflater? = null

    private var mCategoryList: ArrayList<CategoryBean>? = null

    private var textTypeface: Typeface? = null

    constructor(context: Context,categoryList: ArrayList<CategoryBean>): super() {
        mLayoutInflater = LayoutInflater.from(context)
        this.mCategoryList = categoryList
    }

    init {
        textTypeface = Typeface.createFromAsset(EyepetizerApplication.context.assets,"fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }

    fun setData(categoryList: ArrayList<CategoryBean>) {
        mCategoryList?.clear()
        mCategoryList = categoryList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ItemCategoryBinding> {
        var binding: ItemCategoryBinding = DataBindingUtil.inflate(mLayoutInflater!!, R.layout.item_category,parent,false)
        return BindingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mCategoryList!!.size
    }

    override fun onBindViewHolder(holder: BindingViewHolder<ItemCategoryBinding>, position: Int) {
        val category = mCategoryList!![position]
        var binding = holder.binding
        binding?.textCategoryName?.text = "#${category.name}"
        binding?.textCategoryName?.typeface = textTypeface

        binding?.imageUrl = category.bgPicture

        binding?.root?.setOnClickListener {
            val intent = Intent(it.context,CategoryDetailActivity::class.java)
            intent.putExtra(Constants.BUNDLE_CATEGORY_DATA, category)
            it.context.startActivity(intent)
        }
    }


}