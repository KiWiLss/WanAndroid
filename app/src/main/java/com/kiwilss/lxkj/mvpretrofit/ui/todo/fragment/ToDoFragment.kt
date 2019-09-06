/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ToDoFragment
 * Author:   kiwilss
 * Date:     2019-06-29 13:40
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.todo.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.base.PagerLazyFragment
import com.kiwilss.lxkj.mvpretrofit.config.*
import com.kiwilss.lxkj.mvpretrofit.ui.add_todo.AddToDoActivity
import com.kiwilss.lxkj.mvpretrofit.ui.todo.ToDoActivity
import com.kiwilss.lxkj.mvpretrofit.ui.todo.ToDoPresenter
import com.kiwilss.lxkj.mvpretrofit.widget.SwipeItemLayout
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import com.kiwilss.lxkj.mylibrary.core.startActivity
import kotlinx.android.synthetic.main.fragment_todo.*

/**
 *@FileName: ToDoFragment
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-29
 * @desc   : {DESCRIPTION}
 */
class ToDoFragment : PagerLazyFragment<ToDoPresenter>()
    , SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    override fun onRefresh() {
        mCurrent = 1
        val map = mutableMapOf<String, Any>()
        val ac = activity as ToDoActivity
        map["status"] = mStatus
        map["type"] = ac.mType
        mPresenter.getToDoList(mCurrent, map, mStatus)
    }

    override fun onLoadMoreRequested() {
        if (mCurrent < mPageCount) {
            mCurrent++
            val map = mutableMapOf<String, Any>()
            val ac = activity as ToDoActivity
            map["status"] = mStatus
            map["type"] = ac.mType
            mPresenter.getToDoList(mCurrent, map, mStatus)
        } else {
            mAdapter.loadMoreEnd(true)
        }
    }


    override fun initPresenter(): ToDoPresenter = ToDoPresenter()

    override fun getLayoutId(): Int = R.layout.fragment_todo

    override fun getErrorKey(): String = KEY_TODO_FG_ERROR + mStatus.toString()

    companion object {
        fun newInstance(type: Int): ToDoFragment {
            val fg = ToDoFragment()
            val bundle = Bundle()
            bundle.putInt("status", type)
            fg.arguments = bundle
            return fg
        }
    }

    override fun initData() {
        LogUtils.e(mStatus)
        showLoadingDiloag()
        val map = mutableMapOf<String, Any>()
        val ac = activity as ToDoActivity
        map["status"] = mStatus
        map["type"] = ac.mType
        mPresenter.getToDoList(mCurrent, map, mStatus)
    }

    var mStatus = -1 //status: 0 // 0为未完成，1为完成
    var mCurrent = 1//当前页码
    var mPageCount = 0 //总的页码
    private val mData = mutableListOf<TodoDataBean>()

    var mDeletePos = 0
    var mDonePos = 0


    val mAdapter by lazy { ToDoFgAdapter(R.layout.item_fg_todo, R.layout.item_fg_todo_header, mData) }

    override fun initInterface() {
        arguments?.run {
            mStatus = getInt("status")
            LogUtils.e(mStatus)
        }
        //刷新图标设置
        srl_fg_todo_refresh.setColorSchemeColors(ContextCompat.getColor(context!!, R.color.colorPrimary))
        //刷新监听
        srl_fg_todo_refresh.setOnRefreshListener(this)
        initAdapter()
        //获取列表数据监听
        getListListener()
        //删除一条监听
        deleteOneListener()
        //更新一条状态监听
        updateOneListener()
        //监听是否刷新当前界面
        isRefreshListener()

    }

    private fun isRefreshListener() {
        if (mStatus == 0){//待办
            LiveDataBus.with<Boolean>(KEY_TODO_FG_REFRESH + "0").observe(this, Observer {
                onRefresh()
            })
            //新增一条监听刷新
            LiveDataBus.with<Boolean>(KEY_ADD_TODO_ADD_REFRESH).observe(this, Observer {
                onRefresh()
                LogUtils.e("-------add------$mStatus")
            })
        }else if (mStatus == 1){//完成
            LiveDataBus.with<Boolean>(KEY_TODO_FG_REFRESH + "1").observe(this, Observer {
                onRefresh()
            })
        }
        //监听左上角切换刷新界面
        LiveDataBus.with<Boolean>(KEY_TODO_REFRESH).observe(this, Observer {
            onRefresh()
        })
        //监听更新一条数据刷新
        LiveDataBus.with<Boolean>(KEY_ADD_TODO_ADD_REFRESH + mStatus.toString()).observe(this, Observer {
            onRefresh()
            LogUtils.e("-------update------$mStatus")
        })
    }

    private fun updateOneListener() {
        LiveDataBus.with<Any>(KEY_TODO_FG_UPDATE + mStatus.toString()).observe(this, Observer {
            dismissLoadingDiloag()
            //删掉这条数据,同时更新另一 fragment 数据
            mData.removeAt(mDonePos)
            mAdapter.notifyItemRemoved(mDonePos)
            if (mStatus == 0){//待办
                LiveDataBus.with<Boolean>(KEY_TODO_FG_REFRESH + "1").setValue(true)
            }else if (mStatus == 1){//完成
                LiveDataBus.with<Boolean>(KEY_TODO_FG_REFRESH + "0").setValue(true)
            }
        })
    }

    private fun deleteOneListener() {
        LiveDataBus.with<Any>(KEY_TODO_FG_DELETE + mStatus.toString()).observe(this, Observer {
            dismissLoadingDiloag()
            mData.removeAt(mDeletePos)
            mAdapter.notifyItemRemoved(mDeletePos)
        })
    }

    private fun getListListener() {
        LiveDataBus.with<ToDoFgBean>(KEY_TODO_FG_LIST + mStatus.toString())
            .observe(this, Observer {
                LogUtils.e(JSON.toJSONString(it))
                it?.run {
                    mPageCount = pageCount
                    //处理数据
                    if (mCurrent == 1) {
                        mData.clear()
                    }
                    var time = ""
                    for (i in 0 until datas.size) {
                        val data = datas[i]
                        //处理时间
                        if (time != data.dateStr) {
                            time = data.dateStr
                            val dataBean = TodoDataBean(true, time)
                            mData.add(dataBean)
                        }
                        val dataBean = TodoDataBean(data)
                        mData.add(dataBean)
                    }
                }
                dismissLoadingDiloag()
                mAdapter.notifyDataSetChanged()
                mAdapter.loadMoreComplete()
                srl_fg_todo_refresh.isRefreshing = false
                mAdapter.setEnableLoadMore(true)
            })
    }

    private fun initAdapter() {
        rv_fg_todo_list.run {
            layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
            adapter = mAdapter
            addOnItemTouchListener(SwipeItemLayout.OnSwipeItemTouchListener(activity))
        }
        mAdapter.openLoadAnimation()
        mAdapter.setOnLoadMoreListener(this, rv_fg_todo_list)

        //加空布局
        val emptyView = layoutInflater.inflate(R.layout.layout_empty_view, null)
        mAdapter.emptyView = emptyView

        //各类点击事件
//        mAdapter.setOnItemClickListener { adapter, view, position ->
//            //进入编辑或是新增 todo 界面
//
//        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.btn_item_fg_todo_delete -> {//删除
                    showLoadingDiloag(LOADING_HINT)
                    mDeletePos = position
                    mPresenter.deleteOneToDo(mAdapter.data[position].t.id,mStatus)
                }
                R.id.btn_item_fg_todo_done -> {//复原或完成
                    val t = mAdapter.data[position].t
                    val status = t.status//状态， 1-完成；0未完成; 默认全部展示
                    showLoadingDiloag(LOADING_HINT)
                    mDonePos = position
                    var ss = 0
                    if (status == 1){//复原
                        ss = 0
                    }else if (status == 0){//点击变为完成
                        ss = 1
                    }
                    mPresenter.updateOneToDo(t.id,ss,mStatus)
                }
                R.id.rl_item_fg_todo_content -> {
                    //进入编辑或是新增 todo 界面
                    val ac = activity as ToDoActivity
                    startActivity<AddToDoActivity>(bundle = arrayOf(
                        "from" to "update",
                        "type" to ac.mType,
                        "status" to mStatus,
                        "data" to mAdapter.data[position].t
                    ))
                }
            }
        }
    }

}