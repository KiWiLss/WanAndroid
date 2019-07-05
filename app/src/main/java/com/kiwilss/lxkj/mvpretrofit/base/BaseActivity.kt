/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: BaseActivity
 * Author:   kiwilss
 * Date:     2019-06-24 14:57
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.base

import android.arch.lifecycle.Observer
import android.content.res.Resources
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.LogUtils
import com.coder.zzq.smartshow.toast.SmartToast
import com.gyf.barlibrary.ImmersionBar
import com.kiwilss.lxkj.mvpretrofit.dialog.loading.LoadingPopupTwo
import com.kiwilss.lxkj.mvpretrofit.manager.ActivityCollector
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupAnimation
import kotlinx.android.synthetic.main.currency_top.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.ref.WeakReference

/**
 *@FileName: BaseActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-24
 * @desc   : {DESCRIPTION}
 */
abstract class BaseActivity<T: BasePresenter>: AppCompatActivity(){
    val mPresenter: T by lazy {
        initPresenter()
    }

    abstract fun initPresenter(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置布局前操作
        doBeforeSetContentView()
        //设置布局
        setContentView(getLayoutId())
        //设置状态栏
        setStatusBar()
        //当前活动加入活动管理器
        ActivityCollector.instance().addActivity(this)
        //关联presenter
        mPresenter.attech(this)
        //初始化主界面
        initInterface(savedInstanceState)
        //初始化点击事件
        initOnClick()
        //初始化数据
        initData()
        //初始化标题
        initToolbarTitle()
        //设置返回监听
        setBackListener()
        //处理各种网络请求失败的结果
        handlerError()
    }


    abstract fun initOnClick()

    open fun handlerError() {
        LiveDataBus.with<String>(getErrorKey()).observe(this,observer = Observer {
            LogUtils.e("activity$it")
            it?.run {
                //toast(it)
                dismissLoadingDiloag()
                SmartToast.error(it)
                //PostUtil.postCallbackDelayed(mLoadSir, ErrorCallback::class.java)
            }
        })
    }

    abstract fun getErrorKey(): String

    open fun setBackListener() {
        //设置返回点击
        iv_currency_top_back?.let { it ->
            it.setOnClickListener { onBackPressed() }
        }
    }

    open fun initToolbarTitle() {
        //设置标题
        tv_currency_top_title?.let {
            it.text = title
        }
    }

    override fun getResources(): Resources {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 375)
    }

//    private val mLoadingDialog by lazy {
//        SmartDialog.newInstance(
//            DialogCreatorFactory.loading().middle().message(mLoadingMessage))
//    }



    var mLoadingMessage = "加载中..."

    var mBasePopup: BasePopupView? = null

    val weakReference by lazy { WeakReference(this) }

    fun showLoadingDiloag(){
        showLoadingDiloag(mLoadingMessage)
    }

    fun showLoadingDiloag(message: String){
        mBasePopup?.apply {
                dismiss()
        }
        //XPopup.setAnimationDuration(200)
        //val contextweakReference.get()
        mBasePopup = XPopup.Builder(this)
            .popupAnimation(PopupAnimation.NoAnimation)
            .hasShadowBg(false)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .asCustom( LoadingPopupTwo(this).setTitle(message) )
            .show()

    }

    fun dismissLoadingDiloag(){
        mBasePopup?.apply {
            dismiss()
        }
        //mBasePopup = null
    }

    @ExperimentalCoroutinesApi
    override fun onDestroy() {
        dismissLoadingDiloag()
        mPresenter.detach()
        ImmersionBar.with(this).destroy()
        ActivityCollector.instance().removeActivity2(this)
        super.onDestroy()
    }

    open fun initData(){}

    abstract fun initInterface(savedInstanceState: Bundle?)

    open fun setStatusBar() {
        //设置默认样式
//        ImmersionBar.with(this)
//            //.transparentBar()
//            //.statusBarColor(R.color.white)
//            .fitsSystemWindows(true)
//            .statusBarDarkFont(true, 0f)
//            //.navigationBarAlpha(1)
//            .init()
    }

    abstract fun getLayoutId(): Int

    open fun doBeforeSetContentView() {
    }
}