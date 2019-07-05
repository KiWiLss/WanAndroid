/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: CollectListPresenter
 * Author:   kiwilss
 * Date:     2019-06-26 22:03
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.collect

import com.kiwilss.lxkj.mvpretrofit.base.BasePresenter
import com.kiwilss.lxkj.mvpretrofit.config.KEY_COLLECT_LIST_CANCEL
import com.kiwilss.lxkj.mvpretrofit.config.KEY_COLLECT_LIST_DATA
import com.kiwilss.lxkj.mvpretrofit.config.KEY_COLLECT_LIST_ERROR
import com.kiwilss.lxkj.mvpretrofit.model.api.RetrofitHelper
import kotlinx.coroutines.launch

/**
 *@FileName: CollectListPresenter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-26
 * @desc   : {DESCRIPTION}
 */
class CollectListPresenter: BasePresenter() {

    fun getCollectList(page: Int){
        presenterScope.launch {
            handlerResult(KEY_COLLECT_LIST_DATA, KEY_COLLECT_LIST_ERROR){
                RetrofitHelper.apiService.getCollectList(page)
            }
        }
    }


    fun cancelCollect(id: Int, originId: Int){
        presenterScope.launch {
            handlerResult(KEY_COLLECT_LIST_CANCEL, KEY_COLLECT_LIST_ERROR){
                RetrofitHelper.apiService.cancelCollect(id,originId)
            }
        }
    }
}