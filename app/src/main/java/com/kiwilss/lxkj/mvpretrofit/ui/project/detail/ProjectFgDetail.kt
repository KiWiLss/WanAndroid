/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ProjectFgDetail
 * Author:   kiwilss
 * Date:     2019-06-28 14:37
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.project.detail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.base.PagerLazyFragment
import com.kiwilss.lxkj.mvpretrofit.config.*
import com.kiwilss.lxkj.mvpretrofit.ui.login.LoginActivity
import com.kiwilss.lxkj.mvpretrofit.ui.project.ProjectFgPresenter
import com.kiwilss.lxkj.mvpretrofit.ui.webview.WebViewActivity
import com.kiwilss.lxkj.mvpretrofit.utils.toastS
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import com.kiwilss.lxkj.mylibrary.core.sp
import com.kiwilss.lxkj.mylibrary.core.startActivity
import kotlinx.android.synthetic.main.fragment_project_detail.*

/**
 *@FileName: ProjectFgDetail
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-28
 * @desc   : {DESCRIPTION}
 */
class ProjectFgDetail: PagerLazyFragment<ProjectFgPresenter>()
    ,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{
    override fun onRefresh() {
        mCurrentPage = 0
        mAdapter.setEnableLoadMore(false)
        mPresenter.getProjectDetail(mId,mCurrentPage,mType)
    }

    override fun onLoadMoreRequested() {
        if (mCurrentPage < mPageCount){
            mCurrentPage++
            mPresenter.getProjectDetail(mId,mCurrentPage,mType)
        }else{
            mAdapter.loadMoreEnd(true)
        }
    }

    companion object{
        fun newInstance(type: Int,id: Int): ProjectFgDetail {
            val fragment = ProjectFgDetail()
            val bundle = Bundle()
            bundle.putInt("id",id)
            bundle.putInt("type",type)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun initPresenter(): ProjectFgPresenter = ProjectFgPresenter()

    override fun getLayoutId(): Int = R.layout.fragment_project_detail

    override fun getErrorKey(): String = KEY_FG_PROJECT_DETAIL_ERROR + mType.toString()

    override fun initData() {
        showLoadingDiloag()
        mPresenter.getProjectDetail(mId,mCurrentPage,mType)
    }

    var mType = -1
    var mId: Int = 0

    var mCurrentPage = 0
    var mPageCount = 0
    val mAdapter by lazy { ProjectFgDetailAdapter() }
    var mAddPosition = 0
    var mCancelPosition = 0
    override fun initInterface() {

        arguments?.run {
            mId = getInt("id")
            mType = getInt("type")
        }

        //刷新图标设置
        srl_fg_project_detail_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        //刷新监听
        srl_fg_project_detail_refresh.setOnRefreshListener(this)

        initAdapter()
        //监听数据返回结果
        LiveDataBus.with<ProjectListInfo>(KEY_FG_PROJECT__DETAIL_DATA +mType.toString())
            .observe(this, Observer {
                it?.run {
                    mPageCount = pageCount
                    if (mCurrentPage == 0){
                        mAdapter.replaceData(datas)
                    }else{
                        mAdapter.addData(datas)
                    }
                }
                dismissLoadingDiloag()
                mAdapter.loadMoreComplete()
                mAdapter.setEnableLoadMore(true)
                srl_fg_project_detail_refresh.isRefreshing = false

            })
        //收藏结果
        addCollectResult()
        //取消收藏结果
        cancelCollectResult()
        //滚动点击监听
        scrollTopListener()
        //  //监听是否刷新界面
        isRefreshInterface()
    }

    private fun isRefreshInterface() {
        LiveDataBus.with<Boolean>(KEY_HOME_IS_REFRESH).observe(this, Observer {
            if (it!!){//刷新界面
                onRefresh()
            }
        })
    }

    private fun scrollTopListener() {
        LiveDataBus.with<Boolean>(KEY_FG_PROJECT_TOP + mType).observe(this, Observer {
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
        rv_fg_project_detail_list?.run {
            val layoutManager: LinearLayoutManager = this.layoutManager as LinearLayoutManager
            if (layoutManager.findFirstVisibleItemPosition() > 20){
                scrollToPosition(0)//直接滚动到顶
            }else{
                smoothScrollToPosition(0)//模拟滚动到顶
            }
        }
    }

    private fun cancelCollectResult() {
        val key = KEY_FG_PROJECT_DETAIL_CANCEL + mType.toString()
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
        val key = KEY_FG_PROJECT_DETAIL_ADD + mType.toString()
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
        rv_fg_project_detail_list.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        mAdapter.setPreLoadNumber(5)
        mAdapter.setOnLoadMoreListener(this,rv_fg_project_detail_list)
        //点击事件监听
        mAdapter.setOnItemClickListener { adapter, view, position ->
            //点击进入网页
            startActivity<WebViewActivity>(bundle = arrayOf(
                "url" to mAdapter.data[position].link,
                "title" to mAdapter.data[position].title,
                "id" to mAdapter.data[position].id
            ))
        }
        mAdapter.setOnLoadMoreListener(this,rv_fg_project_detail_list)
//        //收藏点击监听
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