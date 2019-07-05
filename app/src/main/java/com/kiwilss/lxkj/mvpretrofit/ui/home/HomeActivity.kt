/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: HomeActivity
 * Author:   kiwilss
 * Date:     2019-06-24 15:46
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.home

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.coder.zzq.smartshow.toast.SmartToast
import com.gyf.barlibrary.ImmersionBar
import com.iammert.library.readablebottombar.ReadableBottomBar
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.base.BaseActivity
import com.kiwilss.lxkj.mvpretrofit.config.*
import com.kiwilss.lxkj.mvpretrofit.manager.ActivityCollector
import com.kiwilss.lxkj.mvpretrofit.model.BaseBean
import com.kiwilss.lxkj.mvpretrofit.ui.collect.CollectListActivity
import com.kiwilss.lxkj.mvpretrofit.ui.knowledge.KnowledgeFg
import com.kiwilss.lxkj.mvpretrofit.ui.login.LoginActivity
import com.kiwilss.lxkj.mvpretrofit.ui.navigation.NavigationFg
import com.kiwilss.lxkj.mvpretrofit.ui.project.ProjectFg
import com.kiwilss.lxkj.mvpretrofit.ui.search.SearchActivity
import com.kiwilss.lxkj.mvpretrofit.ui.todo.ToDoActivity
import com.kiwilss.lxkj.mvpretrofit.ui.wechat.WeChatFg
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import com.kiwilss.lxkj.mylibrary.core.clear
import com.kiwilss.lxkj.mylibrary.core.click
import com.kiwilss.lxkj.mylibrary.core.sp
import com.kiwilss.lxkj.mylibrary.core.startActivity
import com.lxj.xpopup.XPopup
import kotlinx.android.synthetic.main.activity_home.*

/**
 *@FileName: HomeActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-24
 * @desc   : {DESCRIPTION}
 */
class HomeActivity: BaseActivity<HomePresenter>() {


    override fun getErrorKey(): String = KEY_HOME_ERROR

    private val mFragmentList = arrayListOf<Fragment>()
    private var isLogin = false

    val mTitle = arrayListOf<String>("首页","知识体系","公众号","导航","项目")

    override fun initPresenter(): HomePresenter = HomePresenter()

    override fun initOnClick() {
        //底部导航监听
        rb_home_bottom.setOnItemSelectListener(object:ReadableBottomBar.ItemSelectListener{
            override fun onItemSelected(index: Int) {
                //切换界面
                vp_home_vp.currentItem = index
                //切换标题
                tb_home_tb?.run {
                    title = mTitle[index]
                }
            }
        })

        //悬浮图标点击监听
        fab_home_up.click {
            //当前显示的 fragment界面滚动到顶部
            LiveDataBus.with<Boolean>(KEY_HOME_TOP+vp_home_vp.currentItem.toString()).setValue(true)
        }

    }

    override fun initInterface(savedInstanceState: Bundle?) {

        initToolbarAndDrawer()
        initFragment()
        initViewPager()

        //抽屉内点击事件
        initDrawerClick()
        initDrawerHeader()
        //监听是否登录退出
        initLoginOrExit()
    }

    private fun initDrawerHeader() {
        isLogin = sp().getBoolean(Constant.SP_IS_LOGIN,false)
        val tvHomeName = nv_home_drawer.getHeaderView(0).findViewById<TextView>(R.id.tv_home_name)
        //如果登录状态,显示姓名,显示退出按钮
        if (isLogin){
            val username = sp().getString(Constant.SP_USERNAME,"")
            tvHomeName.text = username
        }else{
            tvHomeName.text = resources.getString(R.string.login)
            tvHomeName.click {
                startActivity<LoginActivity>()
            }
        }
        nv_home_drawer.menu.findItem(R.id.main_logout).isVisible = isLogin
    }

    @SuppressLint("ResourceType")
    private fun initDrawerClick() {
        isLogin = sp().getBoolean(Constant.SP_IS_LOGIN,false)

        nv_home_drawer?.run {
            //设置每个文字和图片颜色变化
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                itemTextColor = resources.getColorStateList(R.drawable.nav_menu_text_color, null)
                itemIconTintList = resources.getColorStateList(R.drawable.nav_menu_text_color, null)
            }

            //各点击事件
            setNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.main_collect -> {
                        if (isLogin){
                            startActivity<CollectListActivity>()
                        }else{
                            startActivity<LoginActivity>()
                        }
                    }
                    R.id.main_todo -> {
                        if (isLogin){
                            startActivity<ToDoActivity>()
                        }else{
                            startActivity<LoginActivity>()
                        }
                    }
                    //日间,夜间模式切换
//                    R.id.main_mode -> {
//                        toast("暂无")
//                    }
                    R.id.main_setting -> {

                    }
                    //about us
                    R.id.main_about -> {
                        //startActivity<AboutActivity>()
                    }
                    //exit
                    R.id.main_logout -> {
                        //toast("exit")
                        //提示退出对话框
                        hintExitPw()
                    }
                }

                true
            }

        }

    }

    private fun hintExitPw() {
        XPopup.Builder(this).asConfirm("退出","是否确认退出?") {
            //调用退出接口
            showLoadingDiloag(LOADING_HINT)
            mPresenter.exit()
        }.show()
    }

    private fun initLoginOrExit() {
        LiveDataBus.with<Boolean>(KEY_HOME_IS_LOGIN).observe(this, Observer {
            if (it!!){//未登录变为登录
                //刷新所有带收藏的界面
                //刷新左侧抽屉界面
                initDrawerHeader()
                refreshAllFragment()
            }
        })
        LiveDataBus.with<BaseBean<Any>>(KEY_HOME_EXIT).observe(this, Observer {
            dismissLoadingDiloag()
            //清除所有
            sp().clear()
            //刷新所有带收藏的界面
            //刷新左侧抽屉界面
            initDrawerHeader()
            refreshAllFragment()
        })

    }

    /**
     * 所有 fragment 界面,有登录相关刷新
     */
    private fun refreshAllFragment() {
        LiveDataBus.with<Boolean>(KEY_HOME_IS_REFRESH).setValue(true)
    }

    private fun initFragment() {
        mFragmentList.add(HomeFg())
        mFragmentList.add(KnowledgeFg())
        mFragmentList.add(WeChatFg())
        mFragmentList.add(NavigationFg())
        mFragmentList.add(ProjectFg())
    }

    private fun initViewPager() {
        vp_home_vp.offscreenPageLimit = mFragmentList.size

        vp_home_vp.adapter = HomeAdapter(mFragmentList,supportFragmentManager)
    }

    private fun initToolbarAndDrawer() {
        //设置 toolbar
        tb_home_tb.run {
            title = getString(R.string.home)
            setSupportActionBar(this)
        }
        //和抽屉联动
        dl_home_drawer.run {
            val actionBarDrawerToggle = ActionBarDrawerToggle(
                this@HomeActivity, dl_home_drawer, tb_home_tb
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close
            )
            addDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.syncState()
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun setStatusBar() {
        //透明状态栏
        ImmersionBar.with(this)
            .transparentStatusBar()
            .init()
    }
    private var mExitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                ActivityCollector.instance().finishAll()
            } else {
                mExitTime = System.currentTimeMillis()
                //toast(R.string.exit_tip)
                SmartToast.info(R.string.exit_tip)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                startActivity<SearchActivity>()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}