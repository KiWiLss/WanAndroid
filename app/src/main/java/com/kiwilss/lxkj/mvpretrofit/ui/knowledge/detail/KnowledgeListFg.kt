/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: KnowledgeListFg
 * Author:   kiwilss
 * Date:     2019-06-27 14:17
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.knowledge.detail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.base.PagerLazyFragment
import com.kiwilss.lxkj.mvpretrofit.config.*
import com.kiwilss.lxkj.mvpretrofit.model.api.RetrofitHelper
import com.kiwilss.lxkj.mvpretrofit.ui.knowledge.KnowledgeFgPresenter
import com.kiwilss.lxkj.mvpretrofit.ui.login.LoginActivity
import com.kiwilss.lxkj.mvpretrofit.ui.webview.WebViewActivity
import com.kiwilss.lxkj.mvpretrofit.utils.toast
import com.kiwilss.lxkj.mvpretrofit.utils.toastE
import com.kiwilss.lxkj.mvpretrofit.utils.toastS
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import com.kiwilss.lxkj.mylibrary.core.sp
import com.kiwilss.lxkj.mylibrary.core.startActivity
import kotlinx.android.synthetic.main.fragment_knowledge_detail_fg.*
import kotlinx.coroutines.*

/**
 *@FileName: KnowledgeListFg
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-27
 * @desc   : {DESCRIPTION}
 */
class KnowledgeListFg: PagerLazyFragment<KnowledgeFgPresenter>()
    ,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{


    override fun onLoadMoreRequested() {
        if (mCurrentPage < mPageCount){
            if (mCid == 0) return
            mCurrentPage ++
            mPresenter.getKnowledgeListData(mCurrentPage,mCid,mType)
            //getKnowledgeListData()
        }else{
            mAdapter.loadMoreEnd()
        }
    }

    override fun onRefresh() {
        mCurrentPage = 0
        mAdapter.setEnableLoadMore(false)
        if (mCid == 0) return
        mPresenter.getKnowledgeListData(mCurrentPage,mCid,mType)
        //getKnowledgeListData()
    }

    companion object{

        fun newInstence(cid: Int,type: Int): KnowledgeListFg{
            val fragment = KnowledgeListFg()
            val bundle = Bundle()
            bundle.putInt("cid",cid)
            bundle.putInt("type",type)
            fragment.arguments = bundle
            return fragment
        }

    }

    override fun initPresenter(): KnowledgeFgPresenter = KnowledgeFgPresenter()

    override fun getLayoutId(): Int = R.layout.fragment_knowledge_detail_fg

    override fun getErrorKey(): String = KEY_KNOWLEDGE_List_FG_ERROR + mType.toString()

    override fun initData() {

        if (mCid == 0) return
        showLoadingDiloag()
        mPresenter.getKnowledgeListData(mCurrentPage,mCid,mType)
        //getKnowledgeListData()
    }
    private val presenterScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.Main + Job())
    }

    @ExperimentalCoroutinesApi
    override fun onDestroyView() {
        //presenterScope.cancel()
        super.onDestroyView()
    }
    private fun getKnowledgeListData() {
        presenterScope.launch {
            try {
                val knowledgeList = RetrofitHelper.apiService.getKnowledgeList(mCurrentPage, mCid)
                if (knowledgeList.errorCode == 0) {
                        handlerListData(knowledgeList.data)
                }else{
                    toastE(knowledgeList.errorMsg)
                    LogUtils.e(knowledgeList.errorMsg)
                }
            }catch (e: Exception){
                toast(e.message)
                LogUtils.e(e.message)
            }finally {
                dismissLoadingDiloag()
                LogUtils.e("----finally---------")
            }
        }
    }

    private fun handlerListData(data: KnowledgeListFgBean?) {
        data?.run {
            mPageCount = pageCount
            if (mCurrentPage == 0){
                mAdapter.replaceData(datas)
            }else{
                mAdapter.addData(datas)
            }
        }
        dismissLoadingDiloag()
        srl_fg_knowledge_fg_refresh.isRefreshing = false
        mAdapter.setEnableLoadMore(true)
        mAdapter.loadMoreComplete()
    }

    var mType = -1
    var mCid = 0
    var mCurrentPage = 0
    var mPageCount = 0
    val mAdapter by lazy { KnowledgeListFgAdapter() }
    var mAddPosition = 0
    var mCancelPosition = 0



    override fun initInterface() {
        arguments?.run {
            mCid = getInt("cid")
            mType = getInt("type")
        }
        //刷新图标设置
        srl_fg_knowledge_fg_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        //刷新监听
        srl_fg_knowledge_fg_refresh.setOnRefreshListener(this)

        initAdapter()

        val key = KEY_KNOWLEDGE_List_FG_DATA+mType.toString()
        LogUtils.e(key)
        //处理获取的数据结果
        LiveDataBus.with<KnowledgeListFgBean>(key).observe(this, Observer {
            LogUtils.e("------------")
            LogUtils.e(JSON.toJSONString(it))
            it?.run {
                mPageCount = pageCount
                if (mCurrentPage == 0){
                    mAdapter.replaceData(datas)
                }else{
                    mAdapter.addData(datas)
                }
            }
            dismissLoadingDiloag()
            srl_fg_knowledge_fg_refresh.isRefreshing = false
            mAdapter.setEnableLoadMore(true)
            mAdapter.loadMoreComplete()
        })

        //收藏结果
        addCollectResult()
        //取消收藏结果
        cancelCollectResult()
        //滚动点击监听
        scrollTopListener()
    }

    private fun scrollTopListener() {
        LiveDataBus.with<Boolean>(KEY_KNOWLEDGE_List_TOP + mType).observe(this, Observer {
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
    fun scrollToTop(){
        rv_fg_knowledge_fg_list?.run {
            val layoutManager: LinearLayoutManager = this.layoutManager as LinearLayoutManager
            if (layoutManager.findFirstVisibleItemPosition() > 20){
                scrollToPosition(0)//直接滚动到顶
            }else{
                smoothScrollToPosition(0)//模拟滚动到顶
            }
        }
    }

    private fun cancelCollectResult() {
        val key = KEY_KNOWLEDGE_List_FG_CANCEL + mType.toString()
        LiveDataBus.with<Any>(key).observe(this, Observer {
            dismissLoadingDiloag()
            toastS("已取消收藏")
            //处理界面
            val itemBean = mAdapter.data[mAddPosition]
            itemBean.collect = false
            mAdapter.setData(mCancelPosition,itemBean)
        })
    }

    private fun addCollectResult() {
        val key = KEY_KNOWLEDGE_List_FG_ADD + mType.toString()
        LiveDataBus.with<Any>(key).observe(this, Observer {
            dismissLoadingDiloag()
            toastS("已加入收藏")
            //处理界面
            val itemBean = mAdapter.data[mAddPosition]
            itemBean.collect = true
            mAdapter.setData(mAddPosition,itemBean)
        })
    }

    private fun initAdapter() {
        rv_fg_knowledge_fg_list.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        mAdapter.setPreLoadNumber(5)
        mAdapter.setOnLoadMoreListener(this,rv_fg_knowledge_fg_list)

        //点击监听
        mAdapter.setOnItemClickListener { adapter, view, position ->
            //点击进入网页
            startActivity<WebViewActivity>(bundle = arrayOf(
                "url" to mAdapter.data[position].link,
                "title" to mAdapter.data[position].title,
                "id" to mAdapter.data[position].id
            ))
        }
        //收藏监听
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val isLogin = context!!.sp().getBoolean(Constant.SP_IS_LOGIN,false)
            if (isLogin){
                //调用收藏,判断是否已经收藏
                val homeData = mAdapter.data[position]
                val id = homeData.id
                showLoadingDiloag(LOADING_HINT)
                if (homeData.collect) {//已经收藏
                    //调用取消收藏
                    mCancelPosition = position
                    mPresenter.cancelCollect(id, mType)
                }else{//没有收藏
                    mAddPosition = position
                    mPresenter.collectArticle(id , mType)
                }
            }else{
                startActivity<LoginActivity>()
            }
        }
    }


}