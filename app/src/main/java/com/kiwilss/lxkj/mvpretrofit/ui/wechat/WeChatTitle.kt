package com.kiwilss.lxkj.mvpretrofit.ui.wechat

import com.squareup.moshi.Json

data class WeChatTitle(
    @Json(name = "children")
    val children: List<Any>,
    @Json(name = "courseId")
    val courseId: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "order")
    val order: Int,
    @Json(name = "parentChapterId")
    val parentChapterId: Int,
    @Json(name = "userControlSetTop")
    val userControlSetTop: Boolean,
    @Json(name = "visible")
    val visible: Int
)