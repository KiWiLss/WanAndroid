/**
 * Copyright (C), 2017-2019, XXX有限公司
 * FileName: AddToDoPresenter
 * Author:   kiwilss
 * Date:     2019-06-29 20:30
 * Description: {DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

package com.kiwilss.lxkj.mvpretrofit.ui.add_todo

import com.kiwilss.lxkj.mvpretrofit.base.BasePresenter
import com.kiwilss.lxkj.mvpretrofit.config.KEY_ADD_TODO_ADD
import com.kiwilss.lxkj.mvpretrofit.config.KEY_ADD_TODO_ERROR
import com.kiwilss.lxkj.mvpretrofit.config.KEY_ADD_TODO_UPDATE
import com.kiwilss.lxkj.mvpretrofit.model.api.RetrofitHelper
import kotlinx.coroutines.launch

/**
 *@FileName: AddToDoPresenter
 *@author : Lss kiwilss
 * @e-mail : kiwilss@163.com
 * @time   : 2019-06-29
 * @desc   : {DESCRIPTION}
 */
class AddToDoPresenter: BasePresenter() {

    fun addOneToDo(map: HashMap<String,Any>){
        presenterScope.launch {
            handlerResult(KEY_ADD_TODO_ADD, KEY_ADD_TODO_ERROR){
                RetrofitHelper.apiService.addOneToDo(map)
            }
        }
    }

    fun updateOneToDo(id: Int,map: HashMap<String,Any>){
        presenterScope.launch {
            handlerResult(KEY_ADD_TODO_UPDATE, KEY_ADD_TODO_ERROR){
                RetrofitHelper.apiService.updateOneToDo(id, map)
            }
        }
    }
}