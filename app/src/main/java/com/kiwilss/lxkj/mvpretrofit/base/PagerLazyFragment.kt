/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: PagerLazyFragment
 * Author:   kiwilss
 * Date:     2019-06-11 16:13
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.base

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.LogUtils
import com.coder.zzq.smartshow.toast.SmartToast
import com.kiwilss.lxkj.mvpretrofit.dialog.loading.LoadingPopupTwo
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BasePopupView
import com.lxj.xpopup.enums.PopupAnimation
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 *@FileName: PagerLazyFragment
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-11
 * @desc   : {DESCRIPTION}
 */
abstract class PagerLazyFragment<T: BasePresenter>: Fragment(){

     var isInit = false

    var mRootView: View? = null

     val mPresenter: T by lazy {
        initPresenter()
    }

    abstract fun initPresenter(): T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null){
            mRootView = inflater.inflate(getLayoutId(),container,false)
        }
        mPresenter.attech(context)

        return mRootView
    }

    abstract fun getLayoutId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lazyInit()

    }


    open fun handlerError() {
        LiveDataBus.with<String>(getErrorKey()).observe(this,observer = Observer {
            LogUtils.e("fragment$it")
            it?.run {
                //toast(it)
                dismissLoadingDiloag()
                SmartToast.error(it)
                //PostUtil.postCallbackDelayed(mLoadSir, ErrorCallback::class.java)
            }
        })
    }

    abstract fun getErrorKey(): String

    private fun lazyInit() {
        if (mRootView != null && userVisibleHint && !isInit ) {
            isInit = true
            initInterface()
            initData()
            //处理各种网络请求失败的结果
            handlerError()
        }
    }



    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        lazyInit()
    }

    abstract fun initData()

    abstract fun initInterface()


     var mLoadingMessage = "加载中..."

    private var mBasePopup: BasePopupView? = null

    fun showLoadingDiloag(){
        showLoadingDiloag(mLoadingMessage)
    }

    fun showLoadingDiloag(message: String){
        mBasePopup?.apply {
            if (isShow) {
                dismiss()
            }
        }
        mBasePopup = XPopup.Builder(context)
            .hasShadowBg(false)
            .popupAnimation(PopupAnimation.NoAnimation)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .asCustom(LoadingPopupTwo(context!!).setTitle(message))
            .show()
    }

    fun dismissLoadingDiloag(){
        mBasePopup?.apply {
                dismiss()
        }
    }

    @ExperimentalCoroutinesApi
    override fun onDestroyView() {
        dismissLoadingDiloag()
        mPresenter.detach()
        super.onDestroyView()
    }
}