package com.kiwilss.lxkj.mvpretrofit.ui.login

import com.squareup.moshi.Json




data class LoginInfo(
    @Json(name = "chapterTops")
    val chapterTops: List<Any>,
    @Json(name = "collectIds")
    val collectIds: List<Int>,
    @Json(name = "email")
    val email: String,
    @Json(name = "icon")
    val icon: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "password")
    val password: String,
    @Json(name = "token")
    val token: String,
    @Json(name = "type")
    val type: Int,
    @Json(name = "username")
    val username: String
)