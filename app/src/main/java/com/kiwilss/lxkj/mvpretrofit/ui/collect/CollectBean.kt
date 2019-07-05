/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: CollectBean
 * Author:   kiwilss
 * Date:     2019-06-26 22:28
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.ui.collect
import com.squareup.moshi.Json


/**
 *@FileName: CollectBean
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-26
 * @desc   : {DESCRIPTION}
 */


data class CollectData(
    @Json(name = "curPage") val curPage: Int,
    @Json(name = "datas") val datas: List<CollectBean>,
    @Json(name = "offset") val offset: Int,
    @Json(name = "over") val over: Boolean,
    @Json(name = "pageCount") val pageCount: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "total") val total: Int
)

//data class CollectionArticle(
//    @Json(name = "author") val author: String,
//    @Json(name = "chapterId") val chapterId: Int,
//    @Json(name = "chapterName") val chapterName: String,
//    @Json(name = "courseId") val courseId: Int,
//    @Json(name = "desc") val desc: String,
//    @Json(name = "envelopePic") val envelopePic: String,
//    @Json(name = "id") val id: Int,
//    @Json(name = "link") val link: String,
//    @Json(name = "niceDate") val niceDate: String,
//    @Json(name = "origin") val origin: String,
//    @Json(name = "originId") val originId: Int,
//    @Json(name = "publishTime") val publishTime: Long,
//    @Json(name = "title") val title: String,
//    @Json(name = "userId") val userId: Int,
//    @Json(name = "visible") val visible: Int,
//    @Json(name = "zan") val zan: Int
//)

data class CollectBean(
    @Json(name = "author")
    val author: String,
    @Json(name = "chapterId")
    val chapterId: Int,
    @Json(name = "chapterName")
    val chapterName: String,
    @Json(name = "courseId")
    val courseId: Int,
    @Json(name = "desc")
    val desc: String,
    @Json(name = "envelopePic")
    val envelopePic: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "link")
    val link: String,
    @Json(name = "niceDate")
    val niceDate: String,
    @Json(name = "origin")
    val origin: String,
    @Json(name = "originId")
    val originId: Int,
    @Json(name = "publishTime")
    val publishTime: Long,
    @Json(name = "title")
    val title: String,
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "visible")
    val visible: Int,
    @Json(name = "zan")
    val zan: Int
)