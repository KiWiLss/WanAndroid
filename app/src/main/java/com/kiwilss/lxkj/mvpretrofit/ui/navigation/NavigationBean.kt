/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: NavigationBean
 * Author:   kiwilss
 * Date:     2019-06-28 08:45
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.ui.navigation
import com.squareup.moshi.Json


/**
 *@FileName: NavigationBean
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-28
 * @desc   : {DESCRIPTION}
 */
data class NavigationBean(
    @Json(name = "articles")
    val articles: List<Article>,
    @Json(name = "cid")
    val cid: Int,
    @Json(name = "name")
    val name: String
)

data class Article(
    @Json(name = "apkLink")
    val apkLink: String,
    @Json(name = "author")
    val author: String,
    @Json(name = "chapterId")
    val chapterId: Int,
    @Json(name = "chapterName")
    val chapterName: String,
    @Json(name = "collect")
    var collect: Boolean,
    @Json(name = "courseId")
    val courseId: Int,
    @Json(name = "desc")
    val desc: String,
    @Json(name = "envelopePic")
    val envelopePic: String,
    @Json(name = "fresh")
    val fresh: Boolean,
    @Json(name = "id")
    val id: Int,
    @Json(name = "link")
    val link: String,
    @Json(name = "niceDate")
    val niceDate: String,
    @Json(name = "origin")
    val origin: String,
    @Json(name = "prefix")
    val prefix: String,
    @Json(name = "projectLink")
    val projectLink: String,
    @Json(name = "publishTime")
    val publishTime: Long,
    @Json(name = "superChapterId")
    val superChapterId: Int,
    @Json(name = "superChapterName")
    val superChapterName: String,
    @Json(name = "tags")
    val tags: List<Any>,
    @Json(name = "title")
    val title: String,
    @Json(name = "type")
    val type: Int,
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "visible")
    val visible: Int,
    @Json(name = "zan")
    val zan: Int
)