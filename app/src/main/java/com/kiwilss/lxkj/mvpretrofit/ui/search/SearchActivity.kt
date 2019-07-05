/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SearchActivity
 * Author:   kiwilss
 * Date:     2019-06-30 10:07
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.search

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.ImageView
import com.alibaba.fastjson.JSON
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.base.BaseActivity
import com.kiwilss.lxkj.mvpretrofit.config.Constant
import com.kiwilss.lxkj.mvpretrofit.config.KEY_SEARCH_ERROR
import com.kiwilss.lxkj.mvpretrofit.config.KEY_SEARCH_HISTORY
import com.kiwilss.lxkj.mvpretrofit.config.KEY_SEARCH_HOT
import com.kiwilss.lxkj.mvpretrofit.ui.search_list.SearchListActivity
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import com.kiwilss.lxkj.mylibrary.core.click
import com.kiwilss.lxkj.mylibrary.core.putString
import com.kiwilss.lxkj.mylibrary.core.sp
import com.kiwilss.lxkj.mylibrary.core.startActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

/**
 *@FileName: SearchActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-30
 * @desc   : {DESCRIPTION}
 */
class SearchActivity: BaseActivity<SearchPresenter>() {
    override fun initPresenter(): SearchPresenter = SearchPresenter()

    override fun initOnClick() {
        //获取搜索热词
        showLoadingDiloag()
        mPresenter.getSearchHot()
        //获取历史记录
        mPresenter.getSearchHistory(this)

    }

    override fun getErrorKey(): String = KEY_SEARCH_ERROR

    val mSearchHotList = ArrayList<SearchHotBean>()
    val mAdapter by lazy { SearchHistoryAdapter() }

    override fun initInterface(savedInstanceState: Bundle?) {

        toolbar.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }
        //热门搜索点击事件
        tfl_search_hot.setOnTagClickListener { view, position, parent ->
            if (mSearchHotList.isNotEmpty()){
                val searchHotBean = mSearchHotList[position]
                goToSearchList(searchHotBean.name)
            }
          true
        }

        initAdapter()

        //监听获取搜索热词
        getSearchHotListener()
        //获取搜索历史
        getSearchHistoryListener()
        //清空点击
        tv_search_clear.click {
            mAdapter.data.clear()
            mAdapter.notifyDataSetChanged()
            sp().putString(Constant.SP_SEARCH_HISTORY,"")
        }
    }

    private fun getSearchHistoryListener() {

        LiveDataBus.with<String>(KEY_SEARCH_HISTORY).observe(this, Observer {
            dismissLoadingDiloag()
            it?.run {
                val list: List<String> = JSON.parseArray(this,String::class.java)
                //list.reversed()
                mAdapter.replaceData(list)
            }
        })

    }

    private fun getSearchHotListener() {
        LiveDataBus.with<List<SearchHotBean>>(KEY_SEARCH_HOT).observe(this, Observer {
            dismissLoadingDiloag()
            //设置搜索热词
            it?.run {
                mSearchHotList.addAll(this)
                tfl_search_hot.adapter = SearchHotAdapter(this)
            }
        })

    }

    private fun initAdapter() {
        rv_search_search.run {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }

        mAdapter.run {
            bindToRecyclerView(rv_search_search)
            setEmptyView(R.layout.search_empty_view)
            setOnItemChildClickListener { adapter, view, position ->
                when(view.id){
                    R.id.iv_item_search_history_clear -> {
                        //清楚当前的搜索历史数据,更新数据库
                        mAdapter.data.removeAt(position)
                        sp().putString(Constant.SP_SEARCH_HISTORY,JSON.toJSONString(mAdapter.data))
                        mAdapter.notifyItemRemoved(position)
                    }
                    R.id.tv_item_search_history_key -> {
                        goToSearchList(mAdapter.data[position])
                    }
                }
            }
        }

    }

    override fun getLayoutId(): Int = R.layout.activity_search

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.onActionViewExpanded()
        searchView.queryHint = getString(R.string.search_tint)
        searchView.setOnQueryTextListener(queryTextListener)
        searchView.isSubmitButtonEnabled = true
        try {
            val field = searchView.javaClass.getDeclaredField("mGoButton")
            field.isAccessible = true
            val mGoButton = field.get(searchView) as ImageView
            mGoButton.setImageResource(R.drawable.ic_search_white_24dp)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return super.onCreateOptionsMenu(menu)
    }

    /**
     * OnQueryTextListener
     */
    private val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            goToSearchList(query.toString())
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    }

    private fun goToSearchList(key: String) {
        //记录搜索词,存入数据库
        val list = mAdapter.data
        if (!list.contains(key)){
            list.add(0,key)
        }
        if (list.size > 20){
            list.removeAt(list.size - 1)
        }
        sp().putString(Constant.SP_SEARCH_HISTORY,JSON.toJSONString(mAdapter.data))
        //进入搜索列表界面
            startActivity<SearchListActivity>(bundle = arrayOf(
                "title" to key
            ))
        mAdapter.replaceData(list)
    }
}