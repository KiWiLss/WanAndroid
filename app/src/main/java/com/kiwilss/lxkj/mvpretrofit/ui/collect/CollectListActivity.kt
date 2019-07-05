/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: CollectListActivity
 * Author:   kiwilss
 * Date:     2019-06-26 22:03
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.collect

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.base.BaseActivity
import com.kiwilss.lxkj.mvpretrofit.config.KEY_COLLECT_LIST_CANCEL
import com.kiwilss.lxkj.mvpretrofit.config.KEY_COLLECT_LIST_DATA
import com.kiwilss.lxkj.mvpretrofit.config.KEY_COLLECT_LIST_ERROR
import com.kiwilss.lxkj.mvpretrofit.config.LOADING_HINT
import com.kiwilss.lxkj.mvpretrofit.ui.webview.WebViewActivity
import com.kiwilss.lxkj.mvpretrofit.utils.toastS
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import com.kiwilss.lxkj.mylibrary.core.startActivity
import kotlinx.android.synthetic.main.activity_collect_list.*

/**
 *@FileName: CollectListActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-26
 * @desc   : {DESCRIPTION}
 */
class CollectListActivity: BaseActivity<CollectListPresenter>()
    ,SwipeRefreshLayout.OnRefreshListener
    ,BaseQuickAdapter.RequestLoadMoreListener{

    var mCurrentPage = 0
    val mAdapter by lazy {
        CollectListAdapter()
    }
    var mPageCount = 0
    var mCancelPos = 0

    override fun onRefresh() {
        mAdapter.setEnableLoadMore(false)
        mCurrentPage = 0
        mPresenter.getCollectList(mCurrentPage)
    }

    override fun onLoadMoreRequested() {
        if (mCurrentPage < mPageCount){
            mCurrentPage++
            mPresenter.getCollectList(mCurrentPage)
        }else{
            mAdapter.loadMoreEnd()
        }

    }


    override fun initPresenter(): CollectListPresenter = CollectListPresenter()

    override fun initOnClick() {

        //收藏列表处理
        LiveDataBus.with<CollectData>(KEY_COLLECT_LIST_DATA).observe(this, Observer {
            LogUtils.e(JSON.toJSONString(it))
            dismissLoadingDiloag()
            it?.run {
                mPageCount = pageCount
                if (!datas.isNullOrEmpty()){
                    if (mCurrentPage == 0){
                        mAdapter.replaceData(datas)
                    }else{
                        mAdapter.addData(datas)
                    }
                }
            }
            mAdapter.run {
                loadMoreComplete()
                setEnableLoadMore(true)
            }
            srl_collect_list_refresh.isRefreshing = false
            srl_collect_list_refresh.isEnabled = true
        })

        //取消结果处理
        cancelResult()
    }

    private fun cancelResult() {
        LiveDataBus.with<Any>(KEY_COLLECT_LIST_CANCEL).observe(this, Observer {
            dismissLoadingDiloag()
            toastS("已取消收藏")
            //处理界面
            mAdapter.data.removeAt(mCancelPos)
            mAdapter.notifyItemRemoved(mCancelPos)
        })
    }

    override fun getErrorKey(): String = KEY_COLLECT_LIST_ERROR

    override fun initInterface(savedInstanceState: Bundle?) {

        //刷新图标设置
        srl_collect_list_refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
        //刷新监听
        srl_collect_list_refresh.setOnRefreshListener(this)

        //srl_collect_list_refresh.isEnabled = false
        initAdapter()
    }

    override fun initData() {
        super.initData()
        showLoadingDiloag()
        mPresenter.getCollectList(mCurrentPage)
    }

    private fun initAdapter() {
        rv_collect_list_list.run {
            layoutManager = LinearLayoutManager(this@CollectListActivity)
            adapter = mAdapter
        }
        mAdapter.setOnLoadMoreListener(this,rv_collect_list_list)
        //预加载
        mAdapter.setPreLoadNumber(5)
        //取消收藏点击
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            //点击取消收藏
            mCancelPos = position
            val collectBean = mAdapter.data[position]
            showLoadingDiloag(LOADING_HINT)
            mPresenter.cancelCollect(collectBean.id,collectBean.originId)
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            //点击进入网页
            startActivity<WebViewActivity>(bundle = arrayOf(
                "url" to mAdapter.data[position].link,
                "title" to mAdapter.data[position].title,
                "id" to mAdapter.data[position].id
            ))
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_collect_list
}