/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: HomeAdapter
 * Author:   kiwilss
 * Date:     2019-06-25 16:01
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.ui.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 *@FileName: HomeAdapter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-25
 * @desc   : {DESCRIPTION}
 */
class HomeAdapter(val data: List<Fragment>,fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(p0: Int): Fragment = data[p0]

    override fun getCount(): Int = data.size
}