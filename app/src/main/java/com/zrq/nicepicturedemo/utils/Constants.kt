package com.zrq.nicepicturedemo.utils

object Constants {

    const val PIC_BASE_URL = "http://service.picasso.adesk.com"

    const val GET_PIC = "/v1/vertical/category/"

    const val GET_CATEGORY = "/v1/vertical/category?adult=false&first=1"

    fun getPicByCategory(category: String, limit: Int, num: Int): String {
        return "$PIC_BASE_URL$GET_PIC$category/vertical?limit=$limit&skip=${num * limit}&adult=false&first=0&order=hot\""
    }

    //数据库
    const val DATABASE_NAME = "nice_pic"

    const val DATABASE_VERSION = 1

    const val TABLE_NAME = "nice_pic_info"

    const val FIELD_ID = "_id"

    const val FIELD_PIC_URL = "pic_url"

    const val FIELD_THUMB_URL = "thumb_url"
}