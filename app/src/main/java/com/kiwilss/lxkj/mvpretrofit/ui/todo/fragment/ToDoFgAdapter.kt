/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ToDoFgAdapter
 * Author:   kiwilss
 * Date:     2019-06-29 14:52
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.todo.fragment

import android.view.View
import android.widget.Button
import android.widget.TextView
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.widget.TiltTextView

/**
 *@FileName: ToDoFgAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-29
 * @desc   : {DESCRIPTION}
 */
class ToDoFgAdapter(layoutId: Int,layHeaderId: Int,data: MutableList<TodoDataBean>)
    : BaseSectionQuickAdapter<TodoDataBean,BaseViewHolder>(layoutId,layHeaderId,data) {

    override fun convertHead(helper: BaseViewHolder?, item: TodoDataBean?) {
        helper ?: return
        item ?: return
        helper.setText(R.id.tv_fg_todo_header_header,item.header)
    }

    override fun convert(helper: BaseViewHolder?, item: TodoDataBean?) {
        val itemData = item?.t as Data
        helper ?: return
        itemData ?: return

        helper.setText(R.id.tv_item_fg_todo_title, itemData.title)
            .addOnClickListener(R.id.btn_item_fg_todo_delete)
            .addOnClickListener(R.id.btn_item_fg_todo_done)
            .addOnClickListener(R.id.rl_item_fg_todo_content)

        val tv_todo_desc = helper.getView<TextView>(R.id.tv_item_fg_todo_desc)
        tv_todo_desc.text = ""
        tv_todo_desc.visibility = View.INVISIBLE
        if (itemData.content.isNotEmpty()) {
            tv_todo_desc.visibility = View.VISIBLE
            tv_todo_desc.text = itemData.content
        }
        val btn_done = helper.getView<Button>(R.id.btn_item_fg_todo_done)
        if (itemData.status == 0) {//status 状态， 1-完成；0未完成; 默认全部展示；
            btn_done.text = mContext.resources.getString(R.string.mark_done)
        } else if (itemData.status == 1) {
            btn_done.text = mContext.resources.getString(R.string.restore)
        }
        val tv_tilt = helper.getView<TiltTextView>(R.id.tv_item_fg_todo_tilt)
        if (itemData.priority == 1) {
            tv_tilt.setText(mContext.resources.getString(R.string.priority_1))
            tv_tilt.visibility = View.VISIBLE
        } else {
            tv_tilt.visibility = View.GONE
        }


    }
}