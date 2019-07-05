/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ToDoAdapter
 * Author:   kiwilss
 * Date:     2019-06-29 14:31
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.todo

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 *@FileName: ToDoAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-29
 * @desc   : {DESCRIPTION}
 */
class ToDoAdapter(fm: FragmentManager,val data: List<Fragment>): FragmentPagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment = data[p0]

    override fun getCount(): Int = data.size
}