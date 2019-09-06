/**
 * Copyright (C), 2017-2018, XXX有限公司
 * FileName: BasePresenter
 * Author:   kiwilss
 * Date:     2018/12/12 09:09
 * Description: basepresenter
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.kiwilss.lxkj.mvpretrofit.base

import android.content.Context
import android.text.TextUtils
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.NetworkUtils
import com.kiwilss.lxkj.mvpretrofit.model.BaseBean
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import kotlinx.coroutines.*


/**
 *@FileName: BasePresenter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2018/12/12
 * @desc   : basepresenter
 */
abstract class BasePresenter {

     val presenterScope: CoroutineScope by lazy {
        CoroutineScope(Dispatchers.Main + Job())
    }

     private var mContext: Context? =null

    fun attech(context: Context?){
        mContext = context
    }

    @ExperimentalCoroutinesApi
    fun detach() {
        mContext = null
        presenterScope.cancel()
    }

    /**
     * 处理结果
     * @param bean BaseBean<T>?
     * @param key String发送信息的 key
     * @param isBean Boolean是否传回整个数据
     */
    suspend fun <T>handlerResult(key: String, errorKey: String  = "error", isBean: Boolean = false
    ,call: suspend () -> BaseBean<T>){
        try {
            val baseBean = call.invoke()
            LogUtils.e(JSON.toJSONString(baseBean))
            if (isBean){//返回完整数据
                LiveDataBus.with<BaseBean<T>>(key).setValue(baseBean)
            }else{
                if (baseBean.errorCode == 0) {//成功
                    LiveDataBus.with<T>(key).setValue(baseBean.data)
//                    if (baseBean.data != null){
//                        LiveDataBus.with<T>(key).postValue(baseBean.data)
//                    }else{
//                        LiveDataBus.with<String>(errorKey).postValue("服务器返回异常,请重试")
//                    }
                }else{
                    if (TextUtils.isEmpty(baseBean.errorMsg)){
                        LiveDataBus.with<String>(errorKey).setValue("服务器返回异常,请重试")
                    }else{
                        LiveDataBus.with<String>(errorKey).setValue(baseBean.errorMsg)
                    }
                }
            }
        }catch (e: Exception){
            LogUtils.e(e.toString())

            if (!NetworkUtils.isAvailableByPing()){//网络有问题
                LiveDataBus.with<String>(errorKey).setValue("网络异常")
            }else{//其他问题
                LiveDataBus.with<String>(errorKey).setValue("未知错误,请重试")
            }
        }finally {
            LogUtils.e("--------finally----------")
        }

    }


}