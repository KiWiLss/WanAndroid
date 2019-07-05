/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: WeChatFgDetailFgAdapter
 * Author:   kiwilss
 * Date:     2019-06-27 22:00
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.wechat.detail

import android.support.v4.content.ContextCompat
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.coorchice.library.SuperTextView
import com.kiwilss.lxkj.mvpretrofit.R

/**
 *@FileName: WeChatFgDetailFgAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-27
 * @desc   : {DESCRIPTION}
 */
class WeChatFgDetailFgAdapter(layoutId: Int = R.layout.item_fg_home)
    : BaseQuickAdapter<Data,BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder?, item: Data?) {

        item?.run {
            val stvTag = helper?.getView<SuperTextView>(R.id.stv_item_fg_home_tag)
            val tagList = item.tags
            if (tagList.isNotEmpty()){
                stvTag?.visibility= View.VISIBLE
                stvTag?.text = item.tags[0].name
                stvTag?.strokeColor = ContextCompat.getColor(mContext, R.color.colorAccent)
                stvTag?.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent))
            }else{
                stvTag?.visibility= View.GONE
            }

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