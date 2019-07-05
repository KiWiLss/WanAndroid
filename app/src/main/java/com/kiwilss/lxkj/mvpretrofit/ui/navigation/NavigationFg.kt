/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: NavigationFg
 * Author:   kiwilss
 * Date:     2019-06-28 08:17
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.navigation

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.base.PagerLazyFragment
import com.kiwilss.lxkj.mvpretrofit.config.KEY_FG_NAVIGATION_DATA
import com.kiwilss.lxkj.mvpretrofit.config.KEY_FG_NAVIGATION_ERROR
import com.kiwilss.lxkj.mvpretrofit.config.KEY_HOME_TOP
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import kotlinx.android.synthetic.main.fragment_navigation.*
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.widget.TabView

/**
 *@FileName: NavigationFg
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-28
 * @desc   : {DESCRIPTION}
 */


class NavigationFg: PagerLazyFragment<NavigationFgPresenter>() {
    override fun initPresenter(): NavigationFgPresenter = NavigationFgPresenter()

    override fun getLayoutId(): Int = R.layout.fragment_navigation

    override fun getErrorKey(): String = KEY_FG_NAVIGATION_ERROR

    override fun initData() {
        showLoadingDiloag()
        mPresenter.getNavigationList()
    }

    val mAdapter by lazy { NavigationAdapter() }
    val mLinearLayoutManager by lazy { LinearLayoutManager(context) }
    private var bScroll: Boolean = false//滚动停止
    private var currentIndex: Int = 0
    private var bClickTab: Boolean = false



    override fun initInterface() {

        initAdapter()
        //处理数据结果
        handlerDataResult()
        //滚动到顶监听
        scrollToTopListener()
    }

    private fun scrollToTopListener() {
        LiveDataBus.with<Boolean>(KEY_HOME_TOP + "3").observe(this, Observer {
            it?.run {
                if (this){
                    //通知当前 滚动到顶
                  scrollToTop()
                }
            }
        })
    }

    fun scrollToTop() {
        vtl_fg_navigation_tab.setTabSelected(0)
    }

    private fun handlerDataResult() {
        LiveDataBus.with<List<NavigationBean>>(KEY_FG_NAVIGATION_DATA).observe(this, Observer {
           dismissLoadingDiloag()
            it?.run {
                //设置左侧列表数据
                setLeftTabData(this)
                //设置右侧列表
                setRightData(this)
            }
        })
    }

    private fun setRightData(list: List<NavigationBean>) {
        vtl_fg_navigation_tab.setTabAdapter(NavigationFgTabAdapter(context!!,list))
    }

    private fun setLeftTabData(list: List<NavigationBean>) {
        mAdapter.replaceData(list)
    }

    private fun initAdapter() {
        rv_fg_navigation_list.run {
            layoutManager = mLinearLayoutManager
            adapter = mAdapter
        }
        //左侧点击监听
        vtl_fg_navigation_tab.addOnTabSelectedListener(object :VerticalTabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabView?, position: Int) {
                bClickTab = true
                selectTab(position)
                //toast("---$position")
            }

            override fun onTabReselected(tab: TabView?, position: Int) {
            }

        })
        //右侧滚动监听
        rv_fg_navigation_list.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                LogUtils.e("--newState--$newState")
                if (bScroll && (newState == RecyclerView.SCROLL_STATE_IDLE)) {
                    //滚动状态,停止后显示最近的标题
                    scrollRecyclerView()
                }
                //联动左侧,显示对应的标题
                rightLinkLeft(newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                LogUtils.e("-----dx=$dx,---dy= $dy------")
//                if (bScroll) {
//                    scrollRecyclerView()
//                }
            }
        })
    }

    private fun rightLinkLeft(newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (bClickTab) {
                bClickTab = false
                return
            }
            val firstPosition: Int = mLinearLayoutManager.findFirstVisibleItemPosition()
            if (firstPosition != currentIndex) {
                currentIndex = firstPosition
                setChecked(currentIndex)
            }
        }
    }

    private fun setChecked(position: Int) {
        if (bClickTab) {
            bClickTab = false
        } else {
            vtl_fg_navigation_tab.setTabSelected(currentIndex)
        }
        currentIndex = position
    }

    private fun scrollRecyclerView() {
        bScroll = false
        val indexDistance = currentIndex - mLinearLayoutManager.findFirstVisibleItemPosition()
        if (indexDistance > 0 && indexDistance < rv_fg_navigation_list!!.childCount) {
            val top: Int = rv_fg_navigation_list.getChildAt(indexDistance).top
            rv_fg_navigation_list.smoothScrollBy(0, top)
        }
    }

    private fun selectTab(position: Int) {
        currentIndex = position
        rv_fg_navigation_list.stopScroll()
        smoothScrollToPosition(position)
    }
    private fun smoothScrollToPosition(position: Int) {
        //val linearLayoutManager = rv_fg_navigation_list.layoutManager as LinearLayoutManager
        val firstPosition: Int = mLinearLayoutManager.findFirstVisibleItemPosition()
        val lastPosition: Int = mLinearLayoutManager.findLastVisibleItemPosition()
        when {
            position <= firstPosition -> {
                rv_fg_navigation_list.smoothScrollToPosition(position)
            }
            position <= lastPosition -> {
                val top: Int = rv_fg_navigation_list.getChildAt(position - firstPosition).top
                rv_fg_navigation_list.smoothScrollBy(0, top)
            }
            else -> {
                rv_fg_navigation_list.smoothScrollToPosition(position)
            }
        }
        bScroll = true
    }
}