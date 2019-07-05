/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: ProjectFgPresenter
 * Author:   kiwilss
 * Date:     2019-06-28 14:16
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.project

import com.kiwilss.lxkj.mvpretrofit.base.BasePresenter
import com.kiwilss.lxkj.mvpretrofit.config.*
import com.kiwilss.lxkj.mvpretrofit.model.api.RetrofitHelper
import kotlinx.coroutines.launch

/**
 *@FileName: ProjectFgPresenter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-28
 * @desc   : {DESCRIPTION}
 */
class ProjectFgPresenter: BasePresenter() {

    //-----------------project fg------------------------


    fun getProjectTitles(){
        presenterScope.launch {
            handlerResult(KEY_FG_PROJECT_DATA, KEY_FG_PROJECT_ERROR){
                RetrofitHelper.apiService.getProjectData()
            }
        }
    }

    //---------------project fg detail -------------

    fun getProjectDetail(cid: Int, page: Int, type: Int){
        presenterScope.launch {
            handlerResult(KEY_FG_PROJECT__DETAIL_DATA + type.toString(),
                KEY_FG_PROJECT_DETAIL_ERROR + type.toString()){
                RetrofitHelper.apiService.getProjectList(page,cid)
            }
        }
    }


    fun collectArticle(id: Int,type: Int){
        val key = KEY_FG_PROJECT_DETAIL_ADD + type.toString()
        val keyError = KEY_FG_PROJECT_DETAIL_ERROR  + type.toString()
        presenterScope.launch {
            handlerResult(key, keyError){
                RetrofitHelper.apiService.addCollect(id)
            }
        }
    }

    fun cancelCollect(id: Int,type: Int){
        val key = KEY_FG_PROJECT_DETAIL_CANCEL + type.toString()
        val keyError = KEY_FG_PROJECT_DETAIL_ERROR  + type.toString()
        presenterScope.launch {
            handlerResult(key, keyError){
                RetrofitHelper.apiService.cancelCollect(id)
            }
        }
    }

}