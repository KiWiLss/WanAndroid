/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: KnowledgeListFgAdapter
 * Author:   kiwilss
 * Date:     2019-06-27 14:39
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.knowledge.detail
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.coorchice.library.SuperTextView
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mylibrary.core.gone
import com.squareup.moshi.Json


/**
 *@FileName: KnowledgeListFgAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-27
 * @desc   : {DESCRIPTION}
 */
class KnowledgeListFgAdapter(layout: Int = R.layout.item_fg_home):
        BaseQuickAdapter<Data,BaseViewHolder>(layout){

    override fun convert(helper: BaseViewHolder?, item: Data?) {

        helper?.getView<SuperTextView>(R.id.stv_item_fg_home_tag)?.gone()

        item?.run {
            //来源
            val chapterName = when {
                item.superChapterName.isNotEmpty() and item.chapterName.isNotEmpty() ->
                    "${item.superChapterName}/${item.chapterName}"
                item.superChapterName.isNotEmpty() -> item.superChapterName
                item.chapterName.isNotEmpty() -> item.chapterName
                else -> ""
            }

            helper?.run {
                setText(R.id.tv_item_fg_home_title,item.title)
                    .setText(R.id.tv_item_fg_home_auth,item.author)
                    .setText(R.id.tv_item_fg_home_date,item.niceDate)
                    .setText(R.id.tv_item_fg_home_memo,chapterName)
                    .setBackgroundRes(R.id.iv_item_fg_home_collect,
                        if(item.collect)  (R.drawable.ic_like) else (R.drawable.ic_like_not))
                    .addOnClickListener(R.id.iv_item_fg_home_collect)
            }

        }
    }
}

data class KnowledgeListFgBean(
    @Json(name = "curPage")
    val curPage: Int,
    @Json(name = "datas")
    val datas: List<Data>,
    @Json(name = "offset")
    val offset: Int,
    @Json(name = "over")
    val over: Boolean,
    @Json(name = "pageCount")
    val pageCount: Int,
    @Json(name = "size")
    val size: Int,
    @Json(name = "total")
    val total: Int
)

data class Data(
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