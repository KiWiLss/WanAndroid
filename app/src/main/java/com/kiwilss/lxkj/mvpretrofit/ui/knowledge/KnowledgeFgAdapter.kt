/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: KnowledgeFgAdapter
 * Author:   kiwilss
 * Date:     2019-06-27 08:58
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.knowledge

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kiwilss.lxkj.mvpretrofit.R

/**
 *@FileName: KnowledgeFgAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-27
 * @desc   : {DESCRIPTION}
 */
class KnowledgeFgAdapter(layourRes: Int = R.layout.item_fg_knowledge): BaseQuickAdapter<KnowledgeBean,BaseViewHolder>(layourRes) {
    override fun convert(helper: BaseViewHolder?, item: KnowledgeBean?) {

        if(helper == null) return

        item?.run {
            helper.setText(R.id.tv_item_fg_knowledge_title,name)
                .setText(R.id.tv_item_fg_knowledge_content,children.joinToString ("     "){
                    it.name
                })
        }


    }
}