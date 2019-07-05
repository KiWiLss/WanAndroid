package com.kiwilss.lxkj.mvpretrofit.model.api

import com.lxj.androidktx.okhttp.OkWrapper
import retrofit2.Retrofit

import retrofit2.converter.moshi.MoshiConverterFactory

/**
 *@FileName: RetrofitHelper
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-24
 * @desc   : {DESCRIPTION}
 */
object RetrofitHelper {

    val apiService by lazy {
        getService(ApiService::class.java, ApiService.BASE_URL)
    }


    private fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .client(OkWrapper.okHttpClient)
            //.addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .build().create(serviceClass)

    }

}