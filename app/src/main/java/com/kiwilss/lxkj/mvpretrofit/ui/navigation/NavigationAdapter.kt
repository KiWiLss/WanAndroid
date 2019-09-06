/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: NavigationAdapter
 * Author:   kiwilss
 * Date:     2019-06-28 08:49
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.navigation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.ui.webview.WebViewActivity
import com.kiwilss.lxkj.mvpretrofit.utils.CommonUtil
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import q.rorbin.verticaltablayout.util.DisplayUtil

/**
 *@FileName: NavigationAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-28
 * @desc   : {DESCRIPTION}
 */
class NavigationAdapter(layoutId: Int = R.layout.item_navigation)
    : BaseQuickAdapter<NavigationBean,BaseViewHolder>(layoutId) {
    override fun convert(helper: BaseViewHolder?, item: NavigationBean?) {
        helper?.setText(R.id.tv_item_navigation_title,item!!.name)
        //处理流式布局
        val tfl = helper?.getView<TagFlowLayout>(R.id.tfl_item_navigation_flow)

        val artData = item?.articles
        if (artData.isNullOrEmpty()) return

        val navigationTagAdapter = NavigationTagAdapter(mContext, artData)
        tfl!!.adapter = navigationTagAdapter
        tfl.setOnTagClickListener { view, position, parent ->

            Intent(mContext, WebViewActivity::class.java).run {
                putExtra("url",artData[position].link)
                putExtra("title",artData[position].title)
                putExtra("id",artData[position].id)
                mContext.startActivity(this)
            }
            true
        }

    }
}

class NavigationTagAdapter(val context: Context, val data: List<Article>): TagAdapter<Article>(data){
    override fun getView(parent: FlowLayout?, position: Int, t: Article?): View? {

        val view = LayoutInflater.from(context).inflate(R.layout.item_fg_navigation_flow, null)

        t ?: return null

        val tv = view as TextView
        val padding = DisplayUtil.dp2px(context, 10f)

        tv.run {
            text = t.title
            setTextColor(CommonUtil.randomColor())
            setPadding(padding, padding, padding, padding)
        }

        return view
    }

}