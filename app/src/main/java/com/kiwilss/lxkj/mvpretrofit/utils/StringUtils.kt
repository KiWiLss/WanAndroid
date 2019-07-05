/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: StringUtils
 * Author:   kiwilss
 * Date:     2019-06-11 15:32
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.utils

import android.app.Activity
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.coder.zzq.smartshow.toast.SmartToast
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.kiwilss.lxkj.mvpretrofit.R


/**
 *@FileName: StringUtils
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-11
 * @desc   : {DESCRIPTION}
 */

const val isLog = true

const val HINT_PHONE = "请输入手机号"
const val HINT_PHONE_RIGHT = "请输入合法的手机号"
const val HINT_PASSWORD = "请输入密码"
const val HINT_PLEASE_INPUT_TITLE = "请输入标题"
const val HINT_PLEASE_INPUT_DETAIL = "请输入详情"


object StringUtils{





}




/**
 * 简单判空提示
 * @receiver String?
 * @param type String
 * @return Any?
 */
fun String?.hint(type: String): Any?{
    return if (this.isNullOrEmpty()){
        SmartToast.show(type)
        null
    }else{
        ""
    }
}

/**
 * 判断是否是空
 * @receiver String?
 * @param type String
 * @return Boolean
 */
fun String?.hintIsEmpty(hint: String): Boolean{
    if (this.isNullOrEmpty()){
        SmartToast.show(hint)
        return false
    }
    return true
}

/**
 * getAgentWeb
 */
fun String.getAgentWeb(
    activity: Activity,
    webContent: ViewGroup,
    layoutParams: ViewGroup.LayoutParams,
    webView: WebView,
    webChromeClient: WebChromeClient?,
    webViewClient: WebViewClient
) = AgentWeb.with(activity)//传入Activity or Fragment
    .setAgentWebParent(webContent, 1, layoutParams)//传入AgentWeb 的父控件
    .useDefaultIndicator()// 使用默认进度条
    .setWebView(webView)
    .setWebChromeClient(webChromeClient)
    .setWebViewClient(webViewClient)
    .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
    .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
    .createAgentWeb()//
    .ready()
    .go(this)



