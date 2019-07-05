/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: HomePresenter
 * Author:   kiwilss
 * Date:     2019-06-24 15:48
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.home

import com.kiwilss.lxkj.mvpretrofit.base.BasePresenter
import com.kiwilss.lxkj.mvpretrofit.config.*

import com.kiwilss.lxkj.mvpretrofit.model.api.RetrofitHelper
import kotlinx.coroutines.launch

/**
 *@FileName: HomePresenter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-24
 * @desc   : {DESCRIPTION}
 */
class HomePresenter: BasePresenter() {

    //--------------HomeFg----------------
    fun getHomeArticles(page: Int){
        presenterScope.launch {
            handlerResult(KEY_HOME_ARTICLE, KEY_HOME_FG_ERROR){
                RetrofitHelper.apiService.getHomeArticles(page)
            }
        }
    }

    fun getHomeFgBanner(){
        presenterScope.launch {
            handlerResult(KEY_HOME_BANNER, KEY_HOME_FG_ERROR){
                RetrofitHelper.apiService.getBanner()
            }
        }
    }

    fun collectArticle(id: Int){
        presenterScope.launch {
            handlerResult(KEY_HOME_FG_COLLECT, KEY_HOME_FG_ERROR){
                RetrofitHelper.apiService.addCollect(id)
            }
        }
    }

    fun cancelCollect(id: Int){
        presenterScope.launch {
            handlerResult(KEY_HOME_FG_CANCEL, KEY_HOME_FG_ERROR){
                RetrofitHelper.apiService.cancelCollect(id)
            }
        }
    }

    //---------------HomeActivity-----------
    fun exit(){
        presenterScope.launch {
            handlerResult(KEY_HOME_EXIT, KEY_HOME_ERROR,true){
                RetrofitHelper.apiService.exit()
            }
        }
    }

}