/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: WebViewActivity
 * Author:   kiwilss
 * Date:     2019-06-25 21:26
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.webview

import android.arch.lifecycle.Observer
import android.net.http.SslError
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.webkit.*
import com.just.agentweb.AgentWeb
import com.just.agentweb.NestedScrollAgentWebView
import com.kiwilss.lxkj.mvpretrofit.R
import com.kiwilss.lxkj.mvpretrofit.base.BaseActivity
import com.kiwilss.lxkj.mvpretrofit.config.*
import com.kiwilss.lxkj.mvpretrofit.ui.login.LoginActivity
import com.kiwilss.lxkj.mvpretrofit.utils.browse
import com.kiwilss.lxkj.mvpretrofit.utils.share
import com.kiwilss.lxkj.mvpretrofit.utils.toastS
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import com.kiwilss.lxkj.mylibrary.core.sp
import com.kiwilss.lxkj.mylibrary.core.startActivity
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


/**
 *@FileName: WebViewActivity
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-25
 * @desc   : {DESCRIPTION}
 */
class WebViewActivity: BaseActivity<WebViewPresenter>() {
    override fun getErrorKey(): String {
        return KEY_WEBVIEW_ERROR
    }

    override fun initPresenter(): WebViewPresenter = WebViewPresenter()

    override fun initOnClick() {

        //收藏监听
        LiveDataBus.with<Any>(KEY_WEBVIEW_ADD).observe(this, Observer {
            //toast("已加入收藏")
            toastS("已加入收藏")
            //SmartToast.success("已加入收藏")
            dismissLoadingDiloag()
            //更新进入的界面
            LiveDataBus.with<Boolean>(KEY_HOME_IS_REFRESH).setValue(true)
        })


    }

    private lateinit var mAgentWeb: AgentWeb
     var mId: Int = 0
    lateinit var mTitle: String
    lateinit var murl: String

    private val mWebView: NestedScrollAgentWebView by lazy {
        NestedScrollAgentWebView(this)
    }


    override fun initInterface(savedInstanceState: Bundle?) {

        //获取传入的参数
        mId = intent.getIntExtra("id",0)
        mTitle = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")
        murl = url


        val layoutParams = CoordinatorLayout.LayoutParams(-1, -1)
        layoutParams.behavior = AppBarLayout.ScrollingViewBehavior()

        mAgentWeb =  AgentWeb.with(this)
            .setAgentWebParent(cl_webview_cl , layoutParams)
            .useDefaultIndicator()
            .setWebView(mWebView)
            .setWebChromeClient(webChromeClient)
            .createAgentWeb()
            .ready()
            .go(url)

//        mAgentWeb = url.getAgentWeb(this,
//            cl_webview_cl,
//            layoutParams,
//            mWebView,
//            webChromeClient,
//            webViewClient)


//        mAgentWeb.webCreator?.webView?.let {
//            it.settings.domStorageEnabled = true
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                it.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
//            }
//        }

        //处理标题
        tb_webview_tl.run {
            title = "正在加载中..."
            setSupportActionBar(this)
            //设置可见返回图标
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            //设置返回图片可用
            //supportActionBar?.setHomeButtonEnabled(true)
            setNavigationOnClickListener {
              finish()
            }
        }

    }



    /**
     * webChromeClient
     */
    private val webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            title.let {
                tb_webview_tl.title = it
            }
        }
    }

    /**
     * webViewClient
     */
    private val webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            super.onReceivedSslError(view, handler, error)
        }

//        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
//            // super.onReceivedSslError(view, handler, error)
//            handler?.proceed()
//        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.webview_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_share -> {
                share(murl,mTitle)
            }
            R.id.action_like -> {
                //判断是否登录
               val  isLogin = sp().getBoolean(Constant.SP_IS_LOGIN,false)
                if (isLogin){
                    //调用收藏,判断是否已经收藏
                    showLoadingDiloag(LOADING_HINT)
                 mPresenter.collectArticle(mId)
                }else{
                    startActivity<LoginActivity>()
                }
            }
            R.id.action_browser -> {
                browse(murl)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutId(): Int = com.kiwilss.lxkj.mvpretrofit.R.layout.activity_webview

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        //toast("hello")
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()

    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    @ExperimentalCoroutinesApi
    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

//    override fun onBackPressed() {
//        mAgentWeb.let {
//            if (!it.back()) {
//                super.onBackPressed()
//            }
//        }
//    }

}