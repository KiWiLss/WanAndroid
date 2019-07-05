/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SearchHotAdapter
 * Author:   kiwilss
 * Date:     2019-06-30 17:49
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.search

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.utils.CommonUtil
import com.squareup.moshi.Json
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import q.rorbin.verticaltablayout.util.DisplayUtil


/**
 *@FileName: SearchHotAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-30
 * @desc   : {DESCRIPTION}
 */
class SearchHotAdapter(data: List<SearchHotBean>): TagAdapter<SearchHotBean>(data) {
    override fun getView(parent: FlowLayout?, position: Int, t: SearchHotBean?): View {


        val tv: TextView = LayoutInflater.from(parent?.context).inflate(R.layout.flow_layout_tv,
            null) as TextView
        //val padding: Int = DisplayManager.dip2px(10F)!!
        val padding: Int = DisplayUtil.dp2px(parent?.context,10F)
        tv.setPadding(padding, padding, padding, padding)
        tv.text = t?.name
        tv.setTextColor(CommonUtil.randomColor())
        return tv

    }
}



data class SearchHotBean(
    @Json(name = "id")
    val id: Int,
    @Json(name = "link")
    val link: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "order")
    val order: Int,
    @Json(name = "visible")
    val visible: Int
)