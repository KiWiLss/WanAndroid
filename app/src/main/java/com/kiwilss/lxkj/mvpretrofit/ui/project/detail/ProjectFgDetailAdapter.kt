/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ProjectFgDetailAdapter
 * Author:   kiwilss
 * Date:     2019-06-28 14:52
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.project.detail

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mylibrary.core.load

/**
 *@FileName: ProjectFgDetailAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-28
 * @desc   : {DESCRIPTION}
 */
class ProjectFgDetailAdapter(layoutId: Int = R.layout.item_fg_project_detail): BaseQuickAdapter<Data,BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder?, item: Data?) {
        helper ?: return
        item ?: return

        val ivIcon:ImageView = helper.getView(R.id.iv_item_fg_project_detail_icon)
        ivIcon.load(item.envelopePic)
//        GlideManager.getInstance().loadImg(mContext,item.envelopePic,
//            helper.getView(R.id.iv_item_fg_project_detail_icon))

        helper.setText(R.id.tv_item_fg_project_detail_title,item.title)
            .setText(R.id.tv_item_fg_project_detail_content,item.desc)
            .setText(R.id.tv_item_fg_project_detail_auth,item.author)
            .setText(R.id.tv_item_fg_project_detail_date,item.niceDate)
            .setImageResource(R.id.iv_item_fg_project_detail_collect,
                if (item.collect) R.drawable.ic_like else R.drawable.ic_like_not)
            .addOnClickListener(R.id.iv_item_fg_project_detail_collect)

    }
}