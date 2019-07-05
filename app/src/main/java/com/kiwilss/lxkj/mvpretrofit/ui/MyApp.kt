/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: MyApp
 * Author:   kiwilss
 * Date:     2019/3/12 14:37
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.ui

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.Utils
import com.coder.zzq.smartshow.core.SmartShow
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.kiwilss.lxkj.mylibrary.AndroidKtxConfig
import com.kiwilss.lxkj.mylibrary.okhttp.HttpLogInterceptor
import com.lxj.androidktx.okhttp.OkWrapper
import com.squareup.leakcanary.LeakCanary
import com.tencent.bugly.Bugly
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


/**
 *@FileName: MyApp
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019/3/12
 * @desc   : {DESCRIPTION}
 */
class MyApp: Application(){

    companion object {
        var CONTEXT: Context by Delegates.notNull()

        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        CONTEXT = applicationContext
        instance = this

        initAll()
        initOkhttp()
        initBugly()
    }

    private fun initBugly() {
        Bugly.init(applicationContext, "212fa58f53", false);
    }

    private fun initOkhttp() {
        // Cookie 持久化
        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(this))
// 指定缓存路径,缓存大小 50Mb
        val cache = Cache(
            File(this.cacheDir, "HttpCache"),
            (1024 * 1024 * 50).toLong()
        )
        var builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .cookieJar(cookieJar)
            //.sslSocketFactory(SSLHelper.getSSLCertifcation(context))
            .cache(cache)
            .addInterceptor(cacheControlInterceptor)//有网策略
           // .addNetworkInterceptor(cacheControlInterceptor)//无网策略
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(25, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
             //.addNetworkInterceptor(new StethoInterceptor())
            .retryOnConnectionFailure(true)

        OkWrapper.setClient(builder.build())
        .interceptors(HttpLogInterceptor())

    }


    private fun initAll() {
        //工具类初始化
        Utils.init(this)
        LogUtils.getConfig().setLogSwitch(true).globalTag = "MMM"
        //ktx初始化
        AndroidKtxConfig.init(this,defaultLogTag = "MMM")
        //toast,topbar,snackbar初始化
        SmartShow.init(this)
        //分包
        MultiDex.install(this)
        //初始化内存检测
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

    /**
     * 自定义拦截器,缓存设置
     */
    private val cacheControlInterceptor = Interceptor { chain ->
        var request = chain.request()

        LogUtils.e("--------------interceptor---------------")
        if (!NetworkUtils.isConnected()) {//网络未连接
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }
        val originalResponse = chain.proceed(request)
//        //获取cookie
//        val cookies = originalResponse.headers("Set-Cookie")
//        LogUtils.e(JSON.toJSONString(cookies))
//        // ["SESSION=ec42c395-69e2-4563-b20b-c01386f27561; Path=/vm.open/; HttpOnly","SESSION=ec42c395-69e2-4563-b20b-c01386f27561; Path=/msf.open/; HttpOnly"]
//        if (cookies != null && cookies.size > 0) {
//            SPKUtils.saveS("cookie", cookies[0])
//            sp().putString("","")
//        }
        if (NetworkUtils.isConnected()) {
            // 有网络时 设置缓存为默认值
            val cacheControl = request.cacheControl().toString()
            originalResponse.newBuilder()
                .header("Cache-Control", cacheControl)
                //.addHeader("Cookie", "SESSION=84663fd9-9931-4e3f-b616-1b80b2216710")
                .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                .build()
        } else {
            // 无网络时 设置超时为1周
            val maxStale = 60 * 60 * 24 * 7
            originalResponse.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                .removeHeader("Pragma")
                .build()
        }
    }
}