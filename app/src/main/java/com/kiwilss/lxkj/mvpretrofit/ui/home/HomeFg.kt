/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: HomeFg
 * Author:   kiwilss
 * Date:     2019-06-24 17:41
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.ui.home

import android.arch.lifecycle.Observer
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.base.PagerLazyFragment
import com.kiwilss.lxkj.mvpretrofit.config.*
import com.kiwilss.lxkj.mvpretrofit.ui.login.LoginActivity
import com.kiwilss.lxkj.mvpretrofit.ui.webview.WebViewActivity
import com.kiwilss.lxkj.mvpretrofit.utils.GlideImageLoader
import com.kiwilss.lxkj.mvpretrofit.utils.toastS
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import com.kiwilss.lxkj.mylibrary.core.dp2px
import com.kiwilss.lxkj.mylibrary.core.sp
import com.kiwilss.lxkj.mylibrary.core.startActivity
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import kotlinx.android.synthetic.main.fragment_home.*

/**
 *@FileName: HomeFg
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-24
 * @desc   : {DESCRIPTION}
 */
class HomeFg: PagerLazyFragment<HomePresenter>()
    ,BaseQuickAdapter.RequestLoadMoreListener,SwipeRefreshLayout.OnRefreshListener{
    override fun getErrorKey(): String {
        return KEY_HOME_FG_ERROR
    }


    /**
     * 刷新监听
     */
    override fun onRefresh() {
        //上拉限制
        mAdapter.setEnableLoadMore(false)
        mCurrentPage = 0
        mPresenter.getHomeArticles(mCurrentPage)
    }


    private var isLogin = false

    /**
     * 加载更多监听
     */
    override fun onLoadMoreRequested() {
        if (mCurrentPage < mPageCount){
            srl_fg_home_refresh.isEnabled  = false
            mCurrentPage ++
            //initData()//不想显示对话框,可以直接调用获取数据方法
            mPresenter.getHomeArticles(mCurrentPage)
        }else{
            mAdapter.loadMoreEnd()
        }
    }

    private var mCurrentPage = 0

    //private val banner by lazy { com.youth.banner.Banner(activity) }

    private val mTitleList  = mutableListOf<String>()
    private val mUrlList = mutableListOf<String>()
    private val mImaglList = mutableListOf<String>()

    private val mAdapter by lazy { HomeFgAdapter() }

    override fun initPresenter(): HomePresenter = HomePresenter()

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initData() {
        showLoadingDiloag()
        mPresenter.getHomeArticles(mCurrentPage)
        //获取轮播图
        mPresenter.getHomeFgBanner()
    }

    override fun initInterface() {

        initAdapter()

        //获取请求的数据
        getAricleData()

        //刷新设置
        //刷新图标设置
        srl_fg_home_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        //刷新监听
        srl_fg_home_refresh.setOnRefreshListener(this)

        //监听是否刷新界面
        isRefreshInterface()
        //收藏结果
        getAddCollectResult()
        //监听是否滚动到顶部
        isScrollToTop()

    }

    private fun isScrollToTop() {
        LiveDataBus.with<Boolean>(KEY_HOME_TOP + "0").observe(this, Observer {
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
        rv_fg_home_list?.run {
            val layoutManager: LinearLayoutManager = this.layoutManager as LinearLayoutManager
            if (layoutManager.findFirstVisibleItemPosition() > 20){
                scrollToPosition(0)//直接滚动到顶
            }else{
                smoothScrollToPosition(0)//模拟滚动到顶
            }
        }
    }

    private fun getAddCollectResult() {
        //加入收藏结果
        LiveDataBus.with<Any>(KEY_HOME_FG_COLLECT).observe(this, Observer {
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
        LiveDataBus.with<Any>(KEY_HOME_FG_CANCEL).observe(this, Observer {
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

    private fun isRefreshInterface() {
        LiveDataBus.with<Boolean>(KEY_HOME_IS_REFRESH).observe(this, Observer {
            if (it!!){//刷新界面
                onRefresh()
            }
        })
    }

    var mPageCount  = 0
    private fun getAricleData() {
        LiveDataBus.with<HomeBean>(KEY_HOME_ARTICLE).observe(this, Observer {
            dismissLoadingDiloag()
            it?.run {
                mPageCount = pageCount
                LogUtils.e("mpage=$mPageCount+pagecount=$pageCount")
                if (mCurrentPage == 0){
                        mAdapter.replaceData(datas)
                }else{
                        mAdapter.addData(datas)
                }
            }
            mAdapter.loadMoreComplete()
            srl_fg_home_refresh.isRefreshing = false
            mAdapter.setEnableLoadMore(true)
            srl_fg_home_refresh.isEnabled = true
        })
        //获取轮播图数据
        LiveDataBus.with<List<HomeBanner>>(KEY_HOME_BANNER).observe(this, Observer {
            it?.run {
                forEach {
                    mTitleList.add(it.title)
                    mImaglList.add(it.imagePath)
                    mUrlList.add(it.url)
                }
                banner!!.setImageLoader(GlideImageLoader())
                    .setImages(mImaglList)
                    .setBannerTitles(mTitleList)
                    .setIndicatorGravity(BannerConfig.RIGHT)
                    .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                    .setDelayTime(3000).setOnBannerListener {
                        //点击进入网页
                        startActivity<WebViewActivity>(bundle = arrayOf(
                            "url" to mUrlList[it],
                            "title" to mTitleList[it],
                            "id" to this[it].id
                        ))
                    }
                banner!!.start()
            }
        })
    }
     var banner: Banner? = null

     var mAddPosition = 0
     var mCancelPosition = 0

    private fun initAdapter() {
        rv_fg_home_list.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        //设置预加载
        mAdapter.setPreLoadNumber(5)
        //加头
        //mAdapter.addHeaderView(banner)
        val headerView = layoutInflater.inflate(R.layout.header_fg_home, null)
         banner = headerView.findViewById<View>(R.id.banner_header_fg_home_banner) as Banner
        mAdapter.addHeaderView(headerView)

        mAdapter.setOnLoadMoreListener(this,rv_fg_home_list)
        //点击事件
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
            //判断是否登录
            LogUtils.e("$isLogin---")
            isLogin = context!!.sp().getBoolean(Constant.SP_IS_LOGIN,false)
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

    private fun initBanner() {
        banner?.run {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                banner!!.dp2px(200f))
            setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)

            setImageLoader(GlideImageLoader())
            //banner 点击
            setOnBannerListener { position ->
                run {
                    //                    val intent = Intent(activity, BrowserNormalActivity::class.java)
//                    intent.putExtra(BrowserNormalActivity.URL, bannerUrls[position])
//                    startActivity(intent)
                }
            }

        }

    }

    override fun onStart() {
        super.onStart()
        banner?.run {
            startAutoPlay()
        }
    }

    override fun onStop() {
        super.onStop()
        banner?.run {
            stopAutoPlay()
        }
    }

}