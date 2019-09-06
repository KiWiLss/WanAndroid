package com.kiwilss.lxkj.mvpretrofit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import com.kiwilss.lxkj.mvpretrofit.ui.home.HomeActivity
import com.kiwilss.lxkj.mylibrary.core.startActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //透明状态栏
        ImmersionBar.with(this)
            .transparentStatusBar()
            //.transparentBar()
            //.statusBarColor(R.color.colorAccent)
            //.fitsSystemWindows(true)
            .statusBarDarkFont(true, 0f)
            //.navigationBarAlpha(1)
            .init()


        window.decorView.postDelayed({

            startActivity<HomeActivity>()
            finish()
            //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        },2000)





    }

    override fun onDestroy() {
        ImmersionBar.with(this).destroy()
        super.onDestroy()
    }
}
//        //自带的方法获取省份
//        tv_main_hello.click {
//
//            RetrofitHelper.apiService.getProvince()
//                .enqueue(object: Callback<List<Province>> {
//                    override fun onFailure(call: Call<List<Province>>, t: Throwable) {
//                        LogUtils.e(t.message)
//                    }
//
//                    override fun onResponse(call: Call<List<Province>>, response: Response<List<Province>>) {
//                        val responseString = response.body().toString()
//                        LogUtils.e(responseString)
//                        tv_main_hello.text = responseString
//                    }
//
//                })
//
//        }
