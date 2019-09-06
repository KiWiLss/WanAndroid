/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ProjectFg
 * Author:   kiwilss
 * Date:     2019-06-28 14:15
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.project

import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.base.PagerLazyFragment
import com.kiwilss.lxkj.mvpretrofit.config.KEY_FG_PROJECT_DATA
import com.kiwilss.lxkj.mvpretrofit.config.KEY_FG_PROJECT_ERROR
import com.kiwilss.lxkj.mvpretrofit.config.KEY_FG_PROJECT_TOP
import com.kiwilss.lxkj.mvpretrofit.config.KEY_HOME_TOP
import com.kiwilss.lxkj.mvpretrofit.ui.project.detail.ProjectFgDetail
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import kotlinx.android.synthetic.main.fragment_project.*
import java.util.*

/**
 *@FileName: ProjectFg
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-28
 * @desc   : {DESCRIPTION}
 */
class ProjectFg: PagerLazyFragment<ProjectFgPresenter>() {
    override fun initPresenter(): ProjectFgPresenter = ProjectFgPresenter()

    override fun getLayoutId(): Int = R.layout.fragment_project

    override fun getErrorKey(): String = KEY_FG_PROJECT_ERROR

    override fun initData() {
        showLoadingDiloag()
        mPresenter.getProjectTitles()

    }

    override fun initInterface() {
        //获取返回的数据结果
        LiveDataBus.with<List<ProjectFgBean>>(KEY_FG_PROJECT_DATA).observe(this,
            Observer {
            dismissLoadingDiloag()
            it?.run {
                initFragment(this)
            }
        })
        //监听是否滚动到顶部
        isScrollToTop()
    }

    private fun isScrollToTop() {
        LiveDataBus.with<Boolean>(KEY_HOME_TOP + "4").observe(this, Observer {
            it?.run {
                if (this){
                    //通知当前的 fragment 滚动到顶
                    val currentItem = vp_fg_project_vp.currentItem
                    LiveDataBus.with<Boolean>(KEY_FG_PROJECT_TOP +currentItem.toString()).setValue(true)
                }
            }
        })
    }

    private fun initFragment(list: List<ProjectFgBean>) {
        val dataFg = ArrayList<Fragment>()
        for (i in 0 until list.size){
            val id = list[i].id
            val fg = ProjectFgDetail.newInstance(i, id)
            dataFg.add(fg)
        }
        vp_fg_project_vp.offscreenPageLimit = dataFg.size
        vp_fg_project_vp.adapter = fragmentManager?.let { ProjectFgAdapter(it,dataFg,list) }
        tl_fg_project_tb.setupWithViewPager(vp_fg_project_vp)
    }
}


class ProjectFgAdapter(fm: FragmentManager, val data: List<Fragment>, val title: List<ProjectFgBean>):
    FragmentPagerAdapter(fm){
    override fun getItem(p0: Int): Fragment = data[p0]

    override fun getCount(): Int = data.size

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position].name
    }
}