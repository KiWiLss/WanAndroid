/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: NavigationFgTabAdapter
 * Author:   kiwilss
 * Date:     2019-06-28 10:41
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.navigation

import android.content.Context
import android.graphics.Color
import com.blankj.utilcode.util.LogUtils
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView

/**
 *@FileName: NavigationFgTabAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-28
 * @desc   : {DESCRIPTION}
 */
class NavigationFgTabAdapter(val context: Context, val data: List<NavigationBean>) : TabAdapter{
    override fun getIcon(position: Int): ITabView.TabIcon? = null

    override fun getBadge(position: Int): ITabView.TabBadge? = null

    override fun getBackground(position: Int): Int = 0


    override fun getTitle(position: Int): ITabView.TabTitle {
        LogUtils.e(position)
        return ITabView.TabTitle.Builder()
            .setContent(data!![position].name)
            .setTextColor(Color.RED, Color.BLUE)
//            .setTextColor(
//                ContextCompat.getColor(context, R.color.colorAccent),
//                ContextCompat.getColor(context, R.color.gray75))
            .build()
    }

    override fun getCount(): Int = data.size
}