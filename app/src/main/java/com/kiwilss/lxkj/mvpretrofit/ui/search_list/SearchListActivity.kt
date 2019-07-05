/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SearchListActivity
 * Author:   kiwilss
 * Date:     2019-06-30 18:43
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.search_list

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
import com.kiwilss.lxkj.mvpretrofit.config.*
import com.kiwilss.lxkj.mvpretrofit.ui.login.LoginActivity
import com.kiwilss.lxkj.mvpretrofit.ui.webview.WebViewActivity
import com.kiwilss.lxkj.mvpretrofit.utils.toastS
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import com.kiwilss.lxkj.mylibrary.core.click
import com.kiwilss.lxkj.mylibrary.core.sp
import com.kiwilss.lxkj.mylibrary.core.startActivity
import kotlinx.android.synthetic.main.activity_search_list.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 *@FileName: SearchListActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-30
 * @desc   : {DESCRIPTION}
 */
class SearchListActivity: BaseActivity<SearchListPresenter>()
    ,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
    override fun onRefresh() {
        mCurrentPage = 0
        mAdapter.setEnableLoadMore(false)
        mPresenter.getList(mCurrentPage,mTitle)
    }

    override fun onLoadMoreRequested() {
        if (mCurrentPage < mPageCount){
            mCurrentPage ++
            mPresenter.getList(mCurrentPage,mTitle)
        }else{
            mAdapter.loadMoreEnd()
        }
    }


    override fun initPresenter(): SearchListPresenter = SearchListPresenter()

    override fun initOnClick() {
        showLoadingDiloag()
        mPresenter.getList(mCurrentPage,mTitle)

        //滚动到顶点击监听
        fab_search_list_scroll.click {
            scrollToTop()
        }
    }
     private fun scrollToTop() {
        rv_search_list_list.run {
            val layoutManager: LinearLayoutManager = this.layoutManager as LinearLayoutManager
            if (layoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    private fun getAddCollectResult() {
        //加入收藏结果
        LiveDataBus.with<Any>(KEY_SEARCH_LIST_ADD).observe(this, Observer {
            LogUtils.e(JSON.toJSONString(it))
            dismissLoadingDiloag()
            LogUtils.e("cancel--$mAddPosition")
//            toast("已加入收藏")
            toastS("已加入收藏")
            //处理界面
            val itemBean = mAdapter.data[mAddPosition]
            itemBean.collect = true
            mAdapter.setData(mAddPosition,itemBean)
        })
        //取消收藏结果
        LiveDataBus.with<Any>(KEY_SEARCH_LIST_CANCEL).observe(this, Observer {
            dismissLoadingDiloag()
            LogUtils.e("cancel--$mCancelPosition")
            //toast("已取消收藏")
            toastS("已取消收藏")
            //处理界面
            val itemBean = mAdapter.data[mCancelPosition]
            itemBean.collect = false
            mAdapter.setData(mCancelPosition,itemBean)
            //mAdapter.notifyItemRangeChanged(mCancelPosition,1)
        })
    }

    override fun getErrorKey(): String = KEY_SEARCH_LIST_ERROR

    lateinit var mTitle: String
    var mCurrentPage = 0
    var mPageCount = 0
    var mAddPosition = 0
    var mCancelPosition = 0
    val mAdapter by lazy { SearchListAdapter() }

    override fun initInterface(savedInstanceState: Bundle?) {

        //获取传入的搜索关键词
        mTitle = intent.getStringExtra("title")

        //刷新图标设置
        srl_search_list_refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
        //刷新监听
        srl_search_list_refresh.setOnRefreshListener(this)

        toolbar.run {
            title = mTitle
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }
        initAdapter()
        //列表结果
        listResultListener()
        //收藏结果
        getAddCollectResult()

    }

    private fun initAdapter() {
        rv_search_list_list.run {
            layoutManager = LinearLayoutManager(this@SearchListActivity)
            adapter = mAdapter
        }
        mAdapter.run {
            setPreLoadNumber(5)
            openLoadAnimation()
            setOnLoadMoreListener(this@SearchListActivity,rv_search_list_list)
            setOnItemClickListener { adapter, view, position ->
                //点击进入网页
                startActivity<WebViewActivity>(bundle = arrayOf(
                    "url" to mAdapter.data[position].link,
                    "title" to mAdapter.data[position].title,
                    "id" to mAdapter.data[position].id
                ))
            }
            //收藏点击
            setOnItemChildClickListener { adapter, view, position ->
               val isLogin = sp().getBoolean(Constant.SP_IS_LOGIN,false)
                if (isLogin){
                    //调用收藏,判断是否已经收藏
                    val homeData = mAdapter.data[position]
                    val id = homeData.id
                    showLoadingDiloag(LOADING_HINT)
                    if (homeData.collect) {//已经收藏
                        //调用取消收藏
                        mCancelPosition = position
                        mPresenter.cancelCollect(id)
                    }else{//没有收藏
                        mAddPosition = position
                        mPresenter.collectArticle(id)
                    }
                }else{
                    startActivity<LoginActivity>()
                }
            }
        }
    }

    private fun listResultListener() {
        LiveDataBus.with<SearchListBean>(KEY_SEARCH_LIST_DATA).observe(this, Observer {
            dismissLoadingDiloag()
            LogUtils.e(it)
            it?.run {
                mPageCount = pageCount
                if (mCurrentPage == 0){
                    mAdapter.replaceData(datas)
                }else{
                    mAdapter.addData(datas)
                }
            }
            mAdapter.loadMoreComplete()
            mAdapter.setEnableLoadMore(true)
            srl_search_list_refresh.isRefreshing = false
        })
    }

    override fun getLayoutId(): Int = R.layout.activity_search_list
}