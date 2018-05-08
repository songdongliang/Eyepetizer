package com.sdl.eyepetizer.model

import com.flyco.tablayout.listener.CustomTabEntity

class TabEntity(var title: String, var selectedIcon: Int,var unSelectedIcon: Int) : CustomTabEntity {

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabTitle(): String {
        return title
    }
}