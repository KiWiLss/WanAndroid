/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SearchHistoryAdapter
 * Author:   kiwilss
 * Date:     2019-06-30 18:18
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.search

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kiwilss.lxkj.mvpretrofit.R

/**
 *@FileName: SearchHistoryAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-30
 * @desc   : {DESCRIPTION}
 */
class SearchHistoryAdapter(layoutId: Int = R.layout.item_search_history)
    : BaseQuickAdapter<String,BaseViewHolder>(layoutId){
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.tv_item_search_history_key,item)
            ?.addOnClickListener(R.id.iv_item_search_history_clear)
            ?.addOnClickListener(R.id.tv_item_search_history_key)
    }
}