package com.sdl.eyepetizer.model

import java.io.Serializable

class CategoryBean(val id: Long,val name: String,val description: String,val bgPicture: String,val bgColor: String,val headerImage: String) : Serializable {
}