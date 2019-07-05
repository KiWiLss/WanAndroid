/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SearchPresenter
 * Author:   kiwilss
 * Date:     2019-06-30 10:08
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.search

import android.content.Context
import com.kiwilss.lxkj.mvpretrofit.base.BasePresenter
import com.kiwilss.lxkj.mvpretrofit.config.Constant
import com.kiwilss.lxkj.mvpretrofit.config.KEY_SEARCH_ERROR
import com.kiwilss.lxkj.mvpretrofit.config.KEY_SEARCH_HISTORY
import com.kiwilss.lxkj.mvpretrofit.config.KEY_SEARCH_HOT
import com.kiwilss.lxkj.mvpretrofit.model.api.RetrofitHelper
import com.kiwilss.lxkj.mylibrary.bus.LiveDataBus
import com.kiwilss.lxkj.mylibrary.core.sp
import kotlinx.coroutines.launch

/**
 *@FileName: SearchPresenter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-30
 * @desc   : {DESCRIPTION}
 */
class SearchPresenter: BasePresenter() {

    fun getSearchHot(){
        presenterScope.launch {
            handlerResult(KEY_SEARCH_HOT, KEY_SEARCH_ERROR){
                RetrofitHelper.apiService.getSearchHot()
            }
        }
    }
    fun getSearchHistory(context: Context){
        presenterScope.launch {
            val history = context.sp().getString(Constant.SP_SEARCH_HISTORY,null)
            history?.run {
                if (history.isNotEmpty()){
                    LiveDataBus.with<String>(KEY_SEARCH_HISTORY).postValue(history)
                }
            }
        }
    }
}