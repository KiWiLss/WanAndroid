/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: SearchListPresenter
 * Author:   kiwilss
 * Date:     2019-06-30 18:43
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.search_list

import com.kiwilss.lxkj.mvpretrofit.base.BasePresenter
import com.kiwilss.lxkj.mvpretrofit.config.KEY_SEARCH_LIST_ADD
import com.kiwilss.lxkj.mvpretrofit.config.KEY_SEARCH_LIST_CANCEL
import com.kiwilss.lxkj.mvpretrofit.config.KEY_SEARCH_LIST_DATA
import com.kiwilss.lxkj.mvpretrofit.config.KEY_SEARCH_LIST_ERROR
import com.kiwilss.lxkj.mvpretrofit.model.api.RetrofitHelper
import kotlinx.coroutines.launch

/**
 *@FileName: SearchListPresenter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-30
 * @desc   : {DESCRIPTION}
 */
class SearchListPresenter: BasePresenter() {

    fun getList(page: Int, key: String){
        presenterScope.launch {
            handlerResult(KEY_SEARCH_LIST_DATA, KEY_SEARCH_LIST_ERROR){
                RetrofitHelper.apiService.getSearchList(page,key)
            }
        }
    }


    fun collectArticle(id: Int){
        presenterScope.launch {
            handlerResult(KEY_SEARCH_LIST_ADD, KEY_SEARCH_LIST_ERROR){
                RetrofitHelper.apiService.addCollect(id)
            }
        }
    }

    fun cancelCollect(id: Int){
        presenterScope.launch {
            handlerResult(KEY_SEARCH_LIST_CANCEL, KEY_SEARCH_LIST_ERROR){
                RetrofitHelper.apiService.cancelCollect(id)
            }
        }
    }
}