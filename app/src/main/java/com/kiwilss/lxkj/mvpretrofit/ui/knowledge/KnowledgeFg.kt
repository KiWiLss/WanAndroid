/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: KnowledgeFg
 * Author:   kiwilss
 * Date:     2019-06-26 23:00
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.knowledge

import android.arch.lifecycle.Observer
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.base.PagerLazyFragment
import com.kiwilss.lxkj.mvpretrofit.config.KEY_HOME_TOP
import com.kiwilss.lxkj.mvpretrofit.config.KEY_KNOWLEDGE_FG_DATA
import com.kiwilss.lxkj.mvpretrofit.config.KEY_KNOWLEDGE_FG_ERROR
import com.kiwilss.lxkj.mvpretrofit.ui.knowledge.detail.KnowledgeListActivity
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import kotlinx.android.synthetic.main.fragment_knowledge.*

/**
 *@FileName: KnowledgeFg
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-26
 * @desc   : {DESCRIPTION}
 */
class KnowledgeFg: PagerLazyFragment<KnowledgeFgPresenter>()
    ,SwipeRefreshLayout.OnRefreshListener{

    private val mAdapter by lazy { KnowledgeFgAdapter() }


    override fun onRefresh() {
        mPresenter.getKnowledgeData()
    }


    override fun initPresenter(): KnowledgeFgPresenter = KnowledgeFgPresenter()

    override fun getLayoutId(): Int = R.layout.fragment_knowledge

    override fun getErrorKey(): String = KEY_KNOWLEDGE_FG_ERROR

    override fun initData() {
        showLoadingDiloag()
        mPresenter.getKnowledgeData()

    }

    override fun initInterface() {

        //刷新图标设置
        srl_fg_knowledge_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        //刷新监听
        srl_fg_knowledge_refresh.setOnRefreshListener(this)

        //srl_collect_list_refresh.isEnabled = false
        initAdapter()
        //获取数据结果处理
        LiveDataBus.with<List<KnowledgeBean>>(KEY_KNOWLEDGE_FG_DATA).observe(this, Observer {
            dismissLoadingDiloag()
            srl_fg_knowledge_refresh.isRefreshing = false
            it?.run {
                mAdapter.replaceData(this)
            }
        })
        // //监听是否滚动到顶部
                isScrollToTop()
    }

    private fun isScrollToTop() {
        LiveDataBus.with<Boolean>(KEY_HOME_TOP + "1").observe(this, Observer {
            it?.run {
                if (this){
                    scrollToTop()
                }
            }
        })
    }

    /**
     * 滚到顶
     */
    private fun scrollToTop(){
        rv_fg_knowledge_list?.run {
            val layoutManager: LinearLayoutManager = this.layoutManager as LinearLayoutManager
            if (layoutManager.findFirstVisibleItemPosition() > 20){
                scrollToPosition(0)//直接滚动到顶
            }else{
                smoothScrollToPosition(0)//模拟滚动到顶
            }
        }
    }

    private fun initAdapter() {
        rv_fg_knowledge_list.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val knowledgeBean = mAdapter.data[position]
//            startActivity<KnowledgeListActivity>(bundle = arrayOf(
//                "title" to knowledgeBean.name
//            , "data" to knowledgeBean
//            ))
            Intent(context,KnowledgeListActivity::class.java).run {
                putExtra("title",knowledgeBean.name)
                putExtra("data",knowledgeBean)
                startActivity(this)
            }

        }
    }
}