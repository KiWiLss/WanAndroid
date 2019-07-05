/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: WeChatFg
 * Author:   kiwilss
 * Date:     2019-06-27 21:05
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.wechat

import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.base.PagerLazyFragment
import com.kiwilss.lxkj.mvpretrofit.config.KEY_FG_WECHAT_ERROR
import com.kiwilss.lxkj.mvpretrofit.config.KEY_FG_WECHAT_TITLE
import com.kiwilss.lxkj.mvpretrofit.config.KEY_FG_WECHAT_TOP
import com.kiwilss.lxkj.mvpretrofit.config.KEY_HOME_TOP
import com.kiwilss.lxkj.mvpretrofit.ui.wechat.detail.WeChatFgDetailFg
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import kotlinx.android.synthetic.main.fragment_wechat.*
import java.util.*

/**
 *@FileName: WeChatFg
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-27
 * @desc   : {DESCRIPTION}
 */
class WeChatFg: PagerLazyFragment<WeChatFgPresenter>() {

    override fun initPresenter(): WeChatFgPresenter = WeChatFgPresenter()

    override fun getLayoutId(): Int = R.layout.fragment_wechat

    override fun getErrorKey(): String = KEY_FG_WECHAT_ERROR

    override fun initData() {
        //获取标题数据
        showLoadingDiloag()
        mPresenter.getWeChatTitles()
    }

    override fun initInterface() {
        //获取返回的数据结果
        LiveDataBus.with<List<WeChatTitle>>(KEY_FG_WECHAT_TITLE).observe(this, Observer {
            dismissLoadingDiloag()
            it?.run {
                initFragment(this)
            }
        })
        //监听是否滚动到顶部
        isScrollToTop()
    }

    private fun isScrollToTop() {
        LiveDataBus.with<Boolean>(KEY_HOME_TOP + "2").observe(this, Observer {
            it?.run {
                if (this){
                    //通知当前的 fragment 滚动到顶
                    val currentItem = vp_fg_wechat_vp.currentItem
                    LiveDataBus.with<Boolean>(KEY_FG_WECHAT_TOP+currentItem.toString()).setValue(true)
                }
            }
        })
    }

    /**
     * 滚到顶
     */
    private fun scrollToTop(){
//        rv_fg_home_list?.run {
//            val layoutManager: LinearLayoutManager = this.layoutManager as LinearLayoutManager
//            if (layoutManager.findFirstVisibleItemPosition() > 20){
//                scrollToPosition(0)//直接滚动到顶
//            }else{
//                smoothScrollToPosition(0)//模拟滚动到顶
//            }
//        }
    }

    private fun initFragment(list: List<WeChatTitle>) {
        val dataFg = ArrayList<Fragment>()
        for (i in 0 until list.size){
            val id = list[i].id
            val fg = WeChatFgDetailFg.newInstance(i, id)
            dataFg.add(fg)
        }
        vp_fg_wechat_vp.offscreenPageLimit = dataFg.size
        vp_fg_wechat_vp.adapter = fragmentManager?.let { WeChatFgAdapter(it,dataFg,list) }
        tl_fg_wechat_tb.setupWithViewPager(vp_fg_wechat_vp)
    }
}

class WeChatFgAdapter(fm: FragmentManager,val data: List<Fragment>,val title: List<WeChatTitle>):
    FragmentPagerAdapter(fm){
    override fun getItem(p0: Int): Fragment = data[p0]

    override fun getCount(): Int = data.size

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position].name
    }
}