/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: CollectListAdapter
 * Author:   kiwilss
 * Date:     2019-06-26 22:30
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.ui.collect

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.coorchice.library.SuperTextView
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mylibrary.core.gone

/**
 *@FileName: CollectListAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-26
 * @desc   : {DESCRIPTION}
 */
class CollectListAdapter (layoutRes: Int = R.layout.item_fg_home)
    : BaseQuickAdapter<CollectBean,BaseViewHolder>(layoutRes){


    override fun convert(helper: BaseViewHolder?, item: CollectBean?) {

        helper?.getView<SuperTextView>(R.id.stv_item_fg_home_tag)?.gone()

        //来源

        item?.let {
            //收藏图片显示
            helper?.setText(R.id.tv_item_fg_home_auth, item.author)
                ?.setText(R.id.tv_item_fg_home_title, item.title)
                ?.setText(R.id.tv_item_fg_home_memo, item.chapterName)
                ?.setText(R.id.tv_item_fg_home_date, item.niceDate)
                ?.setImageResource(R.id.iv_item_fg_home_collect,
                    R.drawable.ic_like )
                ?.addOnClickListener(R.id.iv_item_fg_home_collect)
        }


    }

}