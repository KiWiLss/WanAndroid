/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SearchListAdapter
 * Author:   kiwilss
 * Date:     2019-06-30 19:06
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.search_list

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.coorchice.library.SuperTextView
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.ui.navigation.Article
import com.kiwilss.lxkj.mylibrary.core.gone
import com.squareup.moshi.Json

/**
 *@FileName: SearchListAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-30
 * @desc   : {DESCRIPTION}
 */
class SearchListAdapter (layoutId: Int = R.layout.item_fg_home)
    : BaseQuickAdapter<Article,BaseViewHolder>(layoutId){
    override fun convert(helper: BaseViewHolder?, item: Article?) {
        helper?.getView<SuperTextView>(R.id.stv_item_fg_home_tag)?.gone()

        //来源

        item?.let {
            //收藏图片显示
            helper?.setText(R.id.tv_item_fg_home_auth, item.author)
                ?.setText(R.id.tv_item_fg_home_title, item.title)
                ?.setText(R.id.tv_item_fg_home_memo, item.chapterName)
                ?.setText(R.id.tv_item_fg_home_date, item.niceDate)
                ?.setImageResource(R.id.iv_item_fg_home_collect,
                    if(item.collect)  (R.drawable.ic_like) else (R.drawable.ic_like_not) )
                ?.addOnClickListener(R.id.iv_item_fg_home_collect)
        }

    }
}



//文章
data class SearchListBean(
    @Json(name = "curPage") val curPage: Int,
    @Json(name = "datas") var datas: MutableList<Article>,
    @Json(name = "offset") val offset: Int,
    @Json(name = "over") val over: Boolean,
    @Json(name = "pageCount") val pageCount: Int,
    @Json(name = "size") val size: Int,
    @Json(name = "total") val total: Int
)