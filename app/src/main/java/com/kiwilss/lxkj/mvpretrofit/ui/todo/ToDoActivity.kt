/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ToDoActivity
 * Author:   kiwilss
 * Date:     2019-06-28 21:51
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.todo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.base.BaseActivity
import com.kiwilss.lxkj.mvpretrofit.config.KEY_TODO_ERROR
import com.kiwilss.lxkj.mvpretrofit.config.KEY_TODO_REFRESH
import com.kiwilss.lxkj.mvpretrofit.ui.add_todo.AddToDoActivity
import com.kiwilss.lxkj.mvpretrofit.ui.todo.fragment.ToDoFragment
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import com.kiwilss.lxkj.mylibrary.core.click
import com.kiwilss.lxkj.mylibrary.core.startActivity
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnSelectListener
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

/**
 *@FileName: ToDoActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-28
 * @desc   : {DESCRIPTION}
 */
class ToDoActivity: BaseActivity<ToDoPresenter>() {
    override fun initPresenter(): ToDoPresenter = ToDoPresenter()

    override fun initOnClick() {
        //底部点击切换
        bnv_todo_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.action_notodo -> {
                    vp_todo_vp.currentItem = 0
                    true
                }
                R.id.action_completed ->{
                    vp_todo_vp.currentItem = 1
                    true
                }
                else -> {
                    false
                }
            }
        }

        //新增按钮监听
        fab_todo_btn.click {
            //进入新增界面
            startActivity<AddToDoActivity>(bundle = arrayOf(
                "from" to "add",
                "type" to mType
            ))
        }
    }

    val datas = getTypeData()

    private fun getTypeData(): MutableList<TodoTypeBean> {
        val list = mutableListOf<TodoTypeBean>()
        list.add(TodoTypeBean(0, "只用这一个", true))
        list.add(TodoTypeBean(1, "工作", false))
        list.add(TodoTypeBean(2, "学习", false))
        list.add(TodoTypeBean(3, "生活", false))
        return list
    }
    override fun initToolbarTitle() {

        toolbar.run {
            title = datas[0].name // getString(R.string.nav_todo)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener {
                finish()
            }
        }

    }

    override fun getErrorKey(): String = KEY_TODO_ERROR

    val mFragmentList  = ArrayList<Fragment>()

    var mType = 0  //type 创建时传入的类型, 默认全部展示



    override fun initInterface(savedInstanceState: Bundle?) {

        initFragment()

    }


    private fun initFragment() {
        //status: 0 // 0为未完成，1为完成
        val toDoFg = ToDoFragment.newInstance(0)
        val hasDoFg = ToDoFragment.newInstance(1)
        mFragmentList.add(toDoFg)
        mFragmentList.add(hasDoFg)

        vp_todo_vp.adapter = ToDoAdapter(supportFragmentManager,mFragmentList)
        vp_todo_vp.offscreenPageLimit = mFragmentList.size

    }

    override fun getLayoutId(): Int = R.layout.activity_todo

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        menuInflater.inflate(R.menu.menu_todo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_todo_type -> {
                showPopupWindow(datas)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showPopupWindow(datas: MutableList<TodoTypeBean>) {
        val view = findViewById<View>(R.id.action_todo_type)
        val data = arrayOfNulls<String>(datas.size)
        for (i in 0 until datas.size){
            data[i] = datas[i].name
        }

        XPopup.Builder(this).atView(view)
            .hasShadowBg(false)
            .asAttachList(data,
            intArrayOf(),
            OnSelectListener { position, text ->
                mType = position
                toolbar.title = text
                //更新当前 fragment 界面数据
                LiveDataBus.with<Boolean>(KEY_TODO_REFRESH).setValue(true)
            })
            .show()
    }
}